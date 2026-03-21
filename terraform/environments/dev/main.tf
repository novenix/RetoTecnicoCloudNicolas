provider "google" {
  project = var.project_id
  region  = var.region
}

module "customers_api" {
  source = "../../modules/cloud_run_service"

  project_id       = var.project_id
  region           = var.region
  service_name     = "customers-dev"
  container_image  = var.container_image
  application_port = 8080
  spring_profile   = "dev"
}

output "dev_api_url" {
  value = module.customers_api.service_url
}

variable "project_id" {}
variable "region" { default = "us-central1" }
variable "container_image" {}
