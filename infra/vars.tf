variable "aws_region" {
  description = "Região da AWS"
  type        = string
  default     = "us-east-1"
}

variable "subnet_ids" {
  description = "Lista de IDs das subnets"
  type        = list(string)
}

variable "vpc_id" {
  description = "VPC ID"
  type        = string
}

variable "ecs_cluster_arn" {
  description = "ID do cluster ECS"
  type        = string
}

variable "load_balancer_arn" {
  description = "ARN do Load Balancer"
  type        = string
}

variable "api_listener_arn" {
  description = "O ARN do listener do Load Balancer"
  type        = string
}

variable "aws_access_key_id" {
  description = "Chave de acesso da AWS"
  type        = string
  sensitive   = true
}

variable "aws_secret_access_key" {
  description = "Chave AWS"
  type        = string
  sensitive   = true
}

variable "webhook_path" {
  description = "Path do webhook"
  type        = string
}

variable "pedido_path" {
  description = "Path atualização pedido após pagamento"
  type        = string
}