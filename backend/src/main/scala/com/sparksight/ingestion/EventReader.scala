package com.sparksight.ingestion

import scala.io.Source
import scala.util.Using

object EventReader {
  /**
   * Processes a file line by line without loading the entire file into memory.
   * Closes the file automatically when the processor function completes.
   */
  def processFile[T](path: String)(processor: Iterator[String] => T): T = {
    Using.resource(Source.fromFile(path)) { source =>
      processor(source.getLines())
    }
  }
}
