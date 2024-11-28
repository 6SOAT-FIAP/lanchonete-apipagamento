resource "aws_cloudwatch_log_group" "ecs_log_group" {
  name              = "/ecs/api-container"
  retention_in_days = 7
}

resource "aws_ecs_cluster" "api_cluster" {
  name = "api-cluster"
}

resource "aws_ecs_task_definition" "api_task" {
  family                   = "api-task"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = "256"
  memory                   = "512"

  container_definitions = jsonencode([{
    name      = "api-container"
    image     = "733005211464.dkr.ecr.us-east-1.amazonaws.com/lanchonete-apipagamento:latest"
    portMappings = [
      {
        containerPort = 8080
        protocol      = "tcp"
      }
    ]
    essential = true
    environment = [
      {
        name  = "AWS_ACCESS_KEY_ID"
        value = var.aws_access_key_id
      },
      {
        name  = "AWS_SECRET_ACCESS_KEY"
        value = var.aws_secret_access_key
      }
    ]
    logConfiguration = {
      logDriver = "awslogs"
      options = {
        awslogs-group         = "/ecs/api-container"
        awslogs-region        = "us-east-1"
        awslogs-stream-prefix = "ecs"
      }
    }
  }])

  execution_role_arn = "arn:aws:iam::733005211464:role/LabRole"
  task_role_arn      = "arn:aws:iam::733005211464:role/LabRole"
}

resource "aws_security_group" "ecs_service_sg" {
  name   = "ecs-service-sg"
  vpc_id = var.vpc_id

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_security_group" "alb_sg" {
  name   = "alb-sg"
  vpc_id = var.vpc_id

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_lb" "api_alb" {
  name               = "api-alb"
  internal           = false
  load_balancer_type = "application"
  security_groups    = [aws_security_group.alb_sg.id]
  subnets            = var.subnet_ids
  enable_deletion_protection = false

  enable_cross_zone_load_balancing = true
}

resource "aws_lb_target_group" "api_target_group" {
  name     = "api-target-group"
  port     = 8080
  protocol = "HTTP"
  vpc_id   = var.vpc_id
  target_type = "ip"

  health_check {
    path                = "/health"
    interval            = 60
    timeout             = 10
    healthy_threshold   = 3
    unhealthy_threshold = 3
    matcher             = "200"
  }
}

resource "aws_lb_listener" "api_listener" {
  load_balancer_arn = aws_lb.api_alb.arn
  port              = "80"
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.api_target_group.arn
  }
}

resource "aws_ecs_service" "api_service" {
  name            = "api-service"
  cluster         = aws_ecs_cluster.api_cluster.id
  task_definition = aws_ecs_task_definition.api_task.arn
  launch_type     = "FARGATE"
  desired_count   = 1

  network_configuration {
    subnets          = var.subnet_ids
    security_groups  = [aws_security_group.ecs_service_sg.id]
    assign_public_ip = true
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.api_target_group.arn
    container_name   = "api-container"
    container_port   = 8080
  }
}
