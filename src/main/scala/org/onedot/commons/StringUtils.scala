package org.onedot.commons

import scala.collection.immutable.HashMap


object StringUtils {
  private val stringMapping: Map[String, String] = HashMap[String, String](
    "ü"-> "u",
    "ß"-> "ss",
    "ä"-> "a",
    "ö"-> "o")
  private val regexFilter: String = "[^a-zA-Z0-9_() \\-]+"


  def replaceGerman(initialString: String): String = {
    var replacementString: String = initialString
    import scala.collection.JavaConversions._
    for (entry <- stringMapping.entrySet) {
      replacementString = replacementString.replace(entry.getKey, entry.getValue)
    }
    replacementString
  }


  def replaceSpecialCharacters(initialString: String): String = {
    initialString.replaceAll(regexFilter, "_")
  }

  def getLoweredString(initialString:String): String = {
    val finalString = new StringBuilder()
    var needToUpper = true

    for (c <- initialString) {
      if (needToUpper) {
        finalString.append(c.toUpper)
        needToUpper = false
      }
      else
        finalString.append(c.toLower)

      if (c == ' ' || c == '-' || c == '.')
        needToUpper = true
    }

    finalString.mkString
  }
}
