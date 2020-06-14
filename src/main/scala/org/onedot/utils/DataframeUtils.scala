package org.onedot.utils

import java.io.{BufferedWriter, FileOutputStream, OutputStreamWriter}
import java.nio.charset.StandardCharsets

import org.apache.spark.sql.DataFrame

object DataframeUtils {
  /**
   * Writes out a CSV with header based on the given dataframe. The rows are written as a comma separated array of values
   * @param df any dataframe
   * @param path string representing full or relative path of the intended file to be overwritten
   */
  def writeDfAsCsv(df: DataFrame, path: String): Unit = {
    val bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8))
    try {
      val columns = df.columns
      bw.write(columns.mkString(","))

      val rows = df.collect()

      var first = true
      for (row <- rows) {
        if (!first) bw.newLine() else first = false

        bw.write(row.mkString(","))
      }
    } finally bw.close()
  }
}
