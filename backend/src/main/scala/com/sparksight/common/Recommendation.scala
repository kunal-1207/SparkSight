package com.sparksight.common

import java.time.Instant
import java.util.UUID

case class Recommendation(
  recommendationId: String = UUID.randomUUID().toString,
  findingId: String,
  ruleName: String,
  recommendationText: String,
  potentialSavingsUsd: Double,
  createdAt: Instant = Instant.now()
)
