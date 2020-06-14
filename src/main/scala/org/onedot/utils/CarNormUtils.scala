package org.onedot.utils

import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf
import org.onedot.commons.StringUtils

object CarNormUtils {
  // Get brand mapping from JSON file
  private final val brand_mapping = new JsonMapping("brand_mapping.json", this.getClass.getClassLoader)

  object Color extends Enumeration {
    type Color = Value
    val Beige, Black, Blue, Brown, Gold, Gray, Green, Orange, Other, Purple, Red, Silver, White, Yellow = Value
  }

  /**
   * Get normalized english color from a german color attribute
   * @param german_color string containing color attribute
   * @return string of normalized english color, 'Other' if mapping is not present
   */
  def translateColor(german_color: String): String = {
    if (german_color == null)
      return null

    val norm_color = StringUtils.replaceGerman(german_color.toLowerCase())
    if (norm_color.contains("beige"))
      Color.Beige.toString
    else if (norm_color.contains("schwarz"))
      Color.Black.toString
    else if (norm_color.contains("blau"))
      Color.Blue.toString
    else if (norm_color.contains("braun"))
      Color.Brown.toString
    else if (norm_color.contains("gold"))
      Color.Gold.toString
    else if (norm_color.contains("grau") || norm_color.contains("anthrazit"))
      Color.Gray.toString
    else if (norm_color.contains("grun"))
      Color.Green.toString
    else if (norm_color.contains("orange"))
      Color.Orange.toString
    else if (norm_color.contains("violett"))
      Color.Purple.toString
    else if (norm_color.contains("rot"))
      Color.Red.toString
    else if (norm_color.contains("silber"))
      Color.Silver.toString
    else if (norm_color.contains("weiss"))
      Color.White.toString
    else if (norm_color.contains("gelb"))
      Color.Yellow.toString
    else Color.Other.toString
  }

  /**
   * Get normalized brand, based on json mapping file
   *
   * @param brand non-normalized string containing brand attribute
   * @return mapped string from json config file, or treated string based on original attribute
   */
  def normBrand(brand: String): String = {
    if (brand == null)
      return null

    brand_mapping.getOrElse(brand.toUpperCase, StringUtils.getLoweredString(brand))
  }

  /**
   * Get value from consumption attribute
   * @param consumption non-normalized string containing consumption attribute
   * @return Some(Float) containing consumption value, or None if attribute is not parsable
   */
  def getConsumptionValue(consumption: String): Option[Float] = {
    if (consumption == null)
      return None

    val space_pos = consumption.indexOf(" ")
    if (space_pos == -1)
      return None

    val init_unit = consumption.substring(0, space_pos)
    try {
      Some(init_unit.toFloat)
    }
    catch {
      case _: java.lang.NumberFormatException => None
    }
  }

  /**
   * Get unit from consumption attribute
   * @param consumption non-normalized string containing consumption attribute
   * @return Some(String) containing normalized consumption unit, or None if attribute is not parsable
   */
  def getConsumptionUnit(consumption: String): Option[String] = {
    if (consumption == null)
      return None

    val space_pos = consumption.indexOf(" ")
    if (space_pos == -1)
      return None

    val norm_unit = StringUtils.replaceSpecialCharacters(consumption.substring(space_pos+1))
    if (norm_unit.toLowerCase.matches("l.*km"))
      return Some("l_km_consumption")
    None
  }

  val translateColorUDF: UserDefinedFunction = udf((german_color: String) => {
    translateColor(german_color)
  })

  val normBrandUDF: UserDefinedFunction = udf((brand: String) => {
    normBrand(brand)
  })

  val getConsumptionValueUDF: UserDefinedFunction = udf((consumption: String) => {
    getConsumptionValue(consumption)
  })

  val getConsumptionUnitUDF: UserDefinedFunction = udf((consumption: String) => {
    getConsumptionUnit(consumption)
  })
}
