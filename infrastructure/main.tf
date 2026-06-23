# Google Cloud Storage for event logs
resource "google_storage_bucket" "event_logs" {
  name          = var.bucket_name
  location      = var.region
  force_destroy = true
  
  uniform_bucket_level_access = true
}

# BigQuery Dataset
resource "google_bigquery_dataset" "sparksight_dataset" {
  dataset_id                  = var.bq_dataset_id
  friendly_name               = "SparkSight Dataset"
  description                 = "Dataset for SparkSight analysis results"
  location                    = var.region
  delete_contents_on_destroy  = true
}

# Cloud Run Service (Backend)
resource "google_cloud_run_service" "sparksight_backend" {
  name     = "sparksight-backend"
  location = var.region

  template {
    spec {
      containers {
        image = "gcr.io/${var.project_id}/sparksight-backend:latest"
        
        env {
          name  = "PROJECT_ID"
          value = var.project_id
        }
        env {
          name  = "DATASET_ID"
          value = google_bigquery_dataset.sparksight_dataset.dataset_id
        }
      }
      service_account_name = google_service_account.sparksight_sa.email
    }
  }

  traffic {
    percent         = 100
    latest_revision = true
  }
}

# IAM Service Account
resource "google_service_account" "sparksight_sa" {
  account_id   = "sparksight-service-account"
  display_name = "SparkSight Service Account"
}

# IAM Roles
resource "google_project_iam_member" "gcs_reader" {
  project = var.project_id
  role    = "roles/storage.objectViewer"
  member  = "serviceAccount:${google_service_account.sparksight_sa.email}"
}

resource "google_project_iam_member" "bq_editor" {
  project = var.project_id
  role    = "roles/bigquery.dataEditor"
  member  = "serviceAccount:${google_service_account.sparksight_sa.email}"
}

# Allow public access to Cloud Run (for dashboard)
resource "google_cloud_run_service_iam_member" "public_access" {
  location = google_cloud_run_service.sparksight_backend.location
  project  = google_cloud_run_service.sparksight_backend.project
  service  = google_cloud_run_service.sparksight_backend.name
  role     = "roles/run.invoker"
  member   = "allUsers"
}

# Monitoring Dashboard
resource "google_monitoring_dashboard" "sparksight_dashboard" {
  dashboard_json = <<EOF
{
  "displayName": "SparkSight Health Dashboard",
  "gridLayout": {
    "columns": "2",
    "widgets": [
      {
        "title": "Cloud Run Request Count",
        "xyChart": {
          "dataSets": [{
            "timeSeriesQuery": {
              "timeSeriesFilter": {
                "filter": "metric.type=\"run.googleapis.com/request_count\" resource.type=\"cloud_run_revision\" resource.label.\"service_name\"=\"sparksight-backend\""
              }
            }
          }]
        }
      }
    ]
  }
}
EOF
}
