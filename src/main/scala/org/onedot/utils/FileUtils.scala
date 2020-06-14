package org.onedot.utils

import java.io.{IOException, InputStream, InputStreamReader}
import java.nio.charset.StandardCharsets

object FileUtils {
  /**
   * Gets full file path to a file included in the classpath
   * @param file string with filename
   * @return full path pointing to the file
   */
  def getFilePathFromClasspath(file: String): String = {
    var resource = getClass.getResource(file)

    if (resource == null)
      resource = getClass.getResource("/" + file)

    if (resource == null) throw new RuntimeException("File '" + file + "' not found in classpath.")

    resource.getPath
  }

  /**
   * Returns string representation of a file's content.
   *
   * @param filePath    the absolute or relative path to the file
   * @return File's contents as a String
   */
  def readFileFromClasspath(filePath: String): String = try {
    val input = getFileInputStreamFromClasspath(filePath)
    try streamToString(input)
    catch {
      case e: IOException =>
        throw new RuntimeException(e)
    } finally if (input != null) input.close()
  }

  /**
   * Returns the InputStream from of a file's content.
   *
   * @param filePath    the absolute or relative path to the file
   * @return File's contents as an InputStream
   */
  def getFileInputStreamFromClasspath(filePath: String): InputStream = {
    var input = getClass.getResourceAsStream(filePath)
    if (input == null) {
      input = getClass.getResourceAsStream("/" + filePath)
      if (input == null) throw new RuntimeException("File '" + filePath + "' not found in classpath.")
    }
    input
  }

  /**
   * Converts an InputStream into a String, delimited with breaklines.
   *
   * @param input the InputStream with the content
   * @return the String representation of the stream's content
   */
  def streamToString(input: InputStream): String = try {
    val source = scala.io.Source.fromInputStream(input)
    try source.mkString finally {
      source.close()
    }
  }
}
