resource "google_cloud_run_v2_service" "customers_service" {
  name     = var.service_name
  location = var.region
  project  = var.project_id

  template {
    # Limitar el escalado para control de costos (Free Tier Safe)
    scaling {
      max_instance_count = 1
    }

    containers {
      image = var.container_image

      ports {
        container_port = var.application_port
      }

      env {
        name  = "SPRING_PROFILES_ACTIVE"
        value = var.spring_profile
      }

      # For Cloud Run, PORT env var is also expected by Spring Boot
      env {
        name  = "PORT"
        value = tostring(var.application_port)
      }
    }
  }

  traffic {
    type    = "TRAFFIC_TARGET_ALLOCATION_TYPE_LATEST"
    percent = 100
  }
}

# Habilitar acceso público (No autenticado) para la demo
resource "google_cloud_run_v2_service_iam_member" "public_access" {
  project  = google_cloud_run_v2_service.customers_service.project
  location = google_cloud_run_v2_service.customers_service.location
  name     = google_cloud_run_v2_service.customers_service.name
  role     = "roles/run.invoker"
  member   = "allUsers"
}
