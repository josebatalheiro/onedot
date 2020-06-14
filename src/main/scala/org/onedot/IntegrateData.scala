package org.onedot

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.onedot.commons.Loggable
import org.onedot.utils.DataframeUtils.writeDfAsCsv
import org.onedot.utils.CarNormUtils._
import org.onedot.utils.FileUtils

object IntegrateData extends Loggable {

  def preProcessData(df: DataFrame): DataFrame = {
    df.groupBy("ID", "MakeText", "ModelText", "ModelTypeText", "TypeName")
      .pivot("Attribute Names").agg(first("Attribute Values"))
  }

  def normaliseData(df: DataFrame): DataFrame = {
    df.withColumn("BodyColorText", translateColorUDF(col("BodyColorText")))
      .withColumn("MakeText", normBrandUDF(col("MakeText")))
  }

  def extractData(df: DataFrame): DataFrame = {
    df.withColumn("extracted-value-ConsumptionTotalText", getConsumptionValueUDF(col("ConsumptionTotalText")))
      .withColumn("extracted-unit-ConsumptionTotalText", getConsumptionUnitUDF(col("ConsumptionTotalText")))
  }

  def integrateData(df: DataFrame): DataFrame = {
    df.select("BodyColorText", "MakeText", "ModelText", "TypeName", "City")
      .withColumnRenamed("BodyColorText", "color")
      .withColumnRenamed("MakeText", "make")
      .withColumnRenamed("ModelText", "model")
      .withColumnRenamed("TypeName", "model_variant")
      .withColumnRenamed("City", "city")
  }

  def main(args: Array[String]) {
    val spark = SparkSession.builder.appName("Integrate Data").master("local[*]").config("driver-memory", "1g").getOrCreate()
    val path = FileUtils.getFilePathFromClasspath("supplier_car.json")
    val read_df = spark.read.option("charset", "UTF-8").json(path)

    // Get same granularity
    INFO("Pre processing json file...")
    val pivoted_df = preProcessData(read_df)
    pivoted_df.cache()
    writeDfAsCsv(pivoted_df, "output/pre-processing.csv")
    INFO("Wrote pre processed csv file!")

    // Normalize data
    INFO("Normalising processed dataframe...")
    val norm_df = normaliseData(pivoted_df)
    norm_df.cache()
    writeDfAsCsv(norm_df,"output/normalisation.csv")
    INFO("Wrote normalised csv file!")

    // Extract data
    INFO("Extracting data from dataframe...")
    val extracted_df = extractData(norm_df)
    extracted_df.cache()
    writeDfAsCsv(extracted_df,"output/extraction.csv")
    INFO("Wrote extracted csv file!")

    // Integrate data
    INFO("Final integration for dataframe...")
    val integrated_df = integrateData(extracted_df)
    writeDfAsCsv(integrated_df,"output/integration.csv")
    INFO("Wrote integrated csv file!")

    INFO("Finished data integration. Stopping spark context and exiting...")
    spark.stop()
  }

}
