package com.sparksight.common

import java.time.Instant

case class SparkApplication(
  appId: String,
  appName: String,
  startTime: Instant,
  endTime: Instant
)
