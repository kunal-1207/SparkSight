package com.sparksight.common

import java.time.Instant
import java.util.UUID

case class Finding(
  findingId: String = UUID.randomUUID().toString,
  applicationId: String,
  findingType: String,
  severity: String,
  score: Double,
  confidence: Double,
  createdAt: Instant = Instant.now()
)
