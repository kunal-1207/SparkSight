package com.sparksight.parser

import io.circe.parser._
import com.sparksight.common._
import com.sparksight.ingestion.EventReader
import java.time.Instant

object EventParser {

  /**
   * Parses the Spark event log in a memory-efficient streaming manner.
   * Instead of returning a massive Seq, it accepts callbacks to process entities
   * (e.g. streaming them directly to BigQuery).
   */
  def parseEventLog(
    path: String,
    applicationId: String,
    onApp: SparkApplication => Unit,
    onJob: SparkJob => Unit,
    onStage: SparkStage => Unit,
    onTask: SparkTask => Unit
  ): Unit = {
    EventReader.processFile(path) { lines =>
      var currentApp: Option[SparkApplication] = None

      lines.foreach { line =>
        parse(line) match {
          case Right(json) =>
            val eventType = json.hcursor.downField("Event").as[String].getOrElse("")
            eventType match {
              case "SparkListenerApplicationStart" =>
                val app = SparkApplication(
                  appId = applicationId,
                  appName = json.hcursor.downField("App Name").as[String].getOrElse("Unknown"),
                  startTime = Instant.ofEpochMilli(json.hcursor.downField("Timestamp").as[Long].getOrElse(0L)),
                  endTime = Instant.EPOCH
                )
                currentApp = Some(app)
                onApp(app)

              case "SparkListenerApplicationEnd" =>
                currentApp.foreach { app =>
                  val endTime = Instant.ofEpochMilli(json.hcursor.downField("Timestamp").as[Long].getOrElse(Instant.now().toEpochMilli))
                  onApp(app.copy(endTime = endTime))
                }

              case "SparkListenerJobStart" =>
                json.hcursor.downField("Job ID").as[Long].toOption.foreach { jId =>
                  onJob(SparkJob(jId, applicationId))
                }

              case "SparkListenerStageCompleted" =>
                val stageInfo = json.hcursor.downField("Stage Info")
                for {
                  sId <- stageInfo.downField("Stage ID").as[Int].toOption
                  submitTime = stageInfo.downField("Submission Time").as[Long].getOrElse(0L)
                  completionTime = stageInfo.downField("Completion Time").as[Long].getOrElse(0L)
                  duration = if (completionTime > submitTime) completionTime - submitTime else 0L
                } {
                  onStage(SparkStage(sId, duration))
                }

              case "SparkListenerTaskEnd" =>
                val taskInfo = json.hcursor.downField("Task Info")
                val metrics = json.hcursor.downField("Task Metrics")
                
                for {
                  tId <- taskInfo.downField("Task ID").as[Long].toOption
                  stageId <- json.hcursor.downField("Stage ID").as[Int].toOption
                  launchTime = taskInfo.downField("Launch Time").as[Long].getOrElse(0L)
                  finishTime = taskInfo.downField("Finish Time").as[Long].getOrElse(0L)
                  duration = finishTime - launchTime
                  
                  // Extract metrics safely
                  shuffleRead = metrics.downField("Shuffle Read Metrics").downField("Local Bytes Read").as[Long].getOrElse(0L) + 
                                metrics.downField("Shuffle Read Metrics").downField("Remote Bytes Read").as[Long].getOrElse(0L)
                  shuffleWrite = metrics.downField("Shuffle Write Metrics").downField("Shuffle Bytes Written").as[Long].getOrElse(0L)
                  memorySpill = metrics.downField("Memory Bytes Spilled").as[Long].getOrElse(0L)
                  diskSpill = metrics.downField("Disk Bytes Spilled").as[Long].getOrElse(0L)
                } {
                  onTask(SparkTask(tId, stageId, duration, shuffleRead, shuffleWrite, memorySpill, diskSpill))
                }
              
              case _ => // Ignore unhandled events
            }
          case Left(_) => // Ignore parse errors to be resilient
        }
      }
    }
  }
}
