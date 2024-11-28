terraform {
  backend "s3" {
    bucket = "lanchonete-cezar-bucket"
    key    = "lanchonete-ecs-pagamento/terraform.tfstate"
    region = "us-east-1"
  }
}