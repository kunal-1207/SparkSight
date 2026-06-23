package com.sparksight.ai

import com.sparksight.common.Finding

object AiExplanationLayer {
  /**
   * Explains deterministic findings using human-readable language, simulating an AI explanation layer
   * as per the constitution constraints (LLMs may only explain findings, not make decisions).
   */
  def explainFinding(finding: Finding): String = {
    finding.findingType match {
      case "DATA_SKEW" =>
        s"Data Skew detected with a severity of ${finding.severity}. " +
        s"The longest running task took ${finding.score}x longer than the average. " +
        "This uneven data distribution across partitions severely degrades overall stage performance as other executors sit idle waiting for the skewed task to finish."
      
      case "HEAVY_SHUFFLE" =>
        s"Heavy Shuffle detected (${(finding.score * 100).toInt}% of runtime). " +
        "Moving large amounts of data across the network between stages is expensive and often indicates missing pre-aggregation or suboptimal join strategies."
      
      case "MISSING_BROADCAST_JOIN" =>
        s"Missing Broadcast Join detected. A small dimension table was shuffled across the cluster. " +
        "Since the table is under the broadcast threshold, broadcasting it would eliminate the shuffle entirely and significantly speed up the job."
        
      case "MEMORY_SPILL" =>
        s"Memory Spill of ${finding.score / (1024 * 1024 * 1024)} GB detected. " +
        "Data could not fit into memory and was spilled to disk, drastically slowing down processing due to disk I/O. Consider increasing executor memory or reducing partition sizes."
      
      case _ =>
        s"A finding of type ${finding.findingType} was detected with ${finding.severity} severity and ${finding.confidence * 100}% confidence."
    }
  }
}
