package com.sparksight.persistence

object BigQuerySchema {
  
  val createApplicationsTable = """
    CREATE TABLE IF NOT EXISTS sparksight.applications (
      app_id STRING,
      app_name STRING,
      start_time TIMESTAMP,
      end_time TIMESTAMP
    )
  """

  val createJobsTable = """
    CREATE TABLE IF NOT EXISTS sparksight.jobs (
      job_id INT64,
      application_id STRING
    )
  """

  val createStagesTable = """
    CREATE TABLE IF NOT EXISTS sparksight.stages (
      stage_id INT64,
      duration_ms INT64
    )
  """

  val createTasksTable = """
    CREATE TABLE IF NOT EXISTS sparksight.tasks (
      task_id INT64,
      stage_id INT64,
      duration_ms INT64,
      shuffle_read_bytes INT64,
      shuffle_write_bytes INT64,
      memory_spill_bytes INT64,
      disk_spill_bytes INT64
    )
  """

  val createFindingsTable = """
    CREATE TABLE IF NOT EXISTS sparksight.findings (
      finding_id STRING,
      application_id STRING,
      finding_type STRING,
      severity STRING,
      confidence FLOAT64,
      created_at TIMESTAMP
    )
  """

  val createRecommendationsTable = """
    CREATE TABLE IF NOT EXISTS sparksight.recommendations (
      recommendation_id STRING,
      finding_id STRING,
      rule_name STRING,
      recommendation_text STRING,
      potential_savings_usd FLOAT64,
      created_at TIMESTAMP
    )
  """

  val createReportsTable = """
    CREATE TABLE IF NOT EXISTS sparksight.reports (
      report_id STRING,
      application_id STRING,
      total_savings_usd FLOAT64,
      created_at TIMESTAMP
    )
  """
}
