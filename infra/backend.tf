terraform {
  backend "s3" {
    bucket = "lanchonete-cezar-bucket"
    key    = "lanchonete-ecs-produto/terraform.tfstate"
    region = "us-east-1"
  }
}