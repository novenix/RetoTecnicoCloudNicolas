variable "project_id" {
  description = "GCP Project ID"
  type        = string
}

variable "region" {
  description = "GCP Region"
  type        = string
  default     = "us-central1"
}

variable "service_name" {
  description = "Cloud Run Service Name"
  type        = string
}

variable "container_image" {
  description = "Docker image URL"
  type        = string
}

variable "application_port" {
  description = "Container listening port"
  type        = number
  default     = 8080
}

variable "spring_profile" {
  description = "Spring profile active (dev/prod)"
  type        = string
  default     = "dev"
}
