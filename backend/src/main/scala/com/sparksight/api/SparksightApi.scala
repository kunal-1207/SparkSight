package com.sparksight.api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object SparksightApi {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("sparksight-system")
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    val route =
      pathPrefix("api" / "v1") {
        concat(
          path("logs") {
            post {
              // Stub for handling file upload, parsing with EventParser, and storing findings
              complete(HttpEntity(ContentTypes.`application/json`, """{"status": "Log received and processing started"}"""))
            }
          },
          path("applications") {
            get {
              // Stub for fetching applications from BigQuery
              complete(HttpEntity(ContentTypes.`application/json`, """[{"appId": "app-2026", "appName": "SparkJob1"}]"""))
            }
          },
          path("findings") {
            get {
              // Stub for fetching findings from BigQuery
              complete(HttpEntity(ContentTypes.`application/json`, """[{"findingType": "DATA_SKEW", "severity": "CRITICAL"}]"""))
            }
          },
          path("recommendations") {
            get {
              // Stub for fetching recommendations from BigQuery
              complete(HttpEntity(ContentTypes.`application/json`, """[{"ruleName": "DataSkewRule", "potentialSavingsUsd": 120.0}]"""))
            }
          },
          path("report" / Segment) { id =>
            get {
              // Stub for report summary
              complete(HttpEntity(ContentTypes.`application/json`, s"""{"reportId": "$id", "totalSavingsUsd": 120.0}"""))
            }
          }
        )
      }

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

    println(s"Server now online. Please navigate to http://localhost:8080/api/v1/applications")
    // Keep application running
  }
}
