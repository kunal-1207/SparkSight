variable "project_id" {
  description = "The GCP project ID"
  type        = string
}

variable "region" {
  description = "The region to deploy resources in"
  type        = string
  default     = "us-central1"
}

variable "bucket_name" {
  description = "Name of the GCS bucket for Spark event logs"
  type        = string
}

variable "bq_dataset_id" {
  description = "BigQuery dataset ID"
  type        = string
  default     = "sparksight"
}
