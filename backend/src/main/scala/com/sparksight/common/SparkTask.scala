package com.sparksight.common

case class SparkTask(
  taskId: Long,
  stageId: Int,
  durationMs: Long,
  shuffleReadBytes: Long,
  shuffleWriteBytes: Long,
  memorySpillBytes: Long,
  diskSpillBytes: Long
)
