package org.onedot.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

class JsonMapping(configFile: String, classLoader: ClassLoader) {
  private val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)
  private val json = FileUtils.readFileFromClasspath("brand_mapping.json")
  private val map = mapper.readValue(json, classOf[Map[String,String]])

  def get(key: String): Option[String] = {
    map.get(key)
  }

  def getOrElse(key: String, default: String): String = {
    map.getOrElse(key, default)
  }
}
