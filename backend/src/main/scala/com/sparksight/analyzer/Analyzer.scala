package com.sparksight.analyzer

import com.sparksight.common.{Finding, SparkTask, SparkStage}

trait Analyzer {
  def analyze(applicationId: String, stages: Seq[SparkStage], tasks: Seq[SparkTask]): Seq[Finding]
}
