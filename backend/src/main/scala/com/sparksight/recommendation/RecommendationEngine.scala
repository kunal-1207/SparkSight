package com.sparksight.recommendation

import com.sparksight.common.{Finding, Recommendation}

trait RecommendationRule {
  def appliesTo(finding: Finding): Boolean
  def generate(finding: Finding): Recommendation
}

class DataSkewRule extends RecommendationRule {
  override def appliesTo(finding: Finding): Boolean = finding.findingType == "DATA_SKEW"
  
  override def generate(finding: Finding): Recommendation = {
    Recommendation(
      findingId = finding.findingId,
      ruleName = "DataSkewRule",
      recommendationText = "Apply salting strategy to distribute keys evenly.\n\nReason:\nMax task duration was " + finding.score + "x average duration.",
      potentialSavingsUsd = 120.0
    )
  }
}

class ShuffleRule extends RecommendationRule {
  override def appliesTo(finding: Finding): Boolean = finding.findingType == "HEAVY_SHUFFLE"
  
  override def generate(finding: Finding): Recommendation = {
    Recommendation(
      findingId = finding.findingId,
      ruleName = "ShuffleRule",
      recommendationText = "Increase spark.sql.shuffle.partitions or filter data earlier.",
      potentialSavingsUsd = 85.0
    )
  }
}

class MissingBroadcastJoinRule extends RecommendationRule {
  override def appliesTo(finding: Finding): Boolean = finding.findingType == "MISSING_BROADCAST_JOIN"
  
  override def generate(finding: Finding): Recommendation = {
    Recommendation(
      findingId = finding.findingId,
      ruleName = "MissingBroadcastJoinRule",
      recommendationText = "Use Broadcast Join.\n\nReason:\nDimension Table < 100MB\nThreshold = 100MB\nPotential Savings = 27%",
      potentialSavingsUsd = 230.0
    )
  }
}

object RecommendationEngine {
  private val rules: Seq[RecommendationRule] = Seq(
    new DataSkewRule,
    new ShuffleRule,
    new MissingBroadcastJoinRule
  )
  
  def generateRecommendations(findings: Seq[Finding]): Seq[Recommendation] = {
    findings.flatMap { finding =>
      rules.filter(_.appliesTo(finding)).map(_.generate(finding))
    }
  }
}
