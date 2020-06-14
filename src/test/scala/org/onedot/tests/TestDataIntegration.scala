package org.onedot.tests

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.onedot.IntegrateData._
import org.scalatest.FlatSpec


class TestDataIntegration extends FlatSpec {
  case class DatasetSchemaMismatch(smth: String)  extends Exception(smth)
  case class DatasetContentMismatch(smth: String) extends Exception(smth)
  case class DatasetCountMismatch(smth: String)   extends Exception(smth)
  val spark: SparkSession = SparkSession.builder.appName("Integrate Data Test").master("local[*]").config("driver-memory", "500m").getOrCreate()

  import spark.implicits._
  val iniDF: DataFrame = Seq(
    ("Seats","2","976.0","MERCEDES-BENZ","SLR","SLR McLaren","McLaren","MERCEDES-BENZ SLR 500","0"),
    ("Properties","\"Ab MFK\"","976.0","MERCEDES-BENZ","SLR","SLR McLaren","McLaren","MERCEDES-BENZ SLR 500","0"),
    ("FuelTypeText","Benzin","976.0","MERCEDES-BENZ","SLR","SLR McLaren","McLaren","MERCEDES-BENZ SLR 500","0"),
    ("Hp","626","976.0","MERCEDES-BENZ","SLR","SLR McLaren","McLaren","MERCEDES-BENZ SLR 500","0"),
    ("City","Zuzwil","976.0","MERCEDES-BENZ","SLR","SLR McLaren","McLaren","MERCEDES-BENZ SLR 500","0"),
    ("FirstRegYear","2007","976.0","MERCEDES-BENZ","SLR","SLR McLaren","McLaren","MERCEDES-BENZ SLR 500","0"),
    ("Doors","2","976.0","MERCEDES-BENZ","SLR","SLR McLaren","McLaren","MERCEDES-BENZ SLR 500","0"),
    ("ConsumptionTotalText",null,"976.0","MERCEDES-BENZ","SLR","SLR McLaren","McLaren","MERCEDES-BENZ SLR 500","0"),
    ("ConditionTypeText","Occasion","976.0","MERCEDES-BENZ","SLR","SLR McLaren","McLaren","MERCEDES-BENZ SLR 500","0"),
    ("FirstRegMonth","10","976.0","MERCEDES-BENZ","SLR","SLR McLaren","McLaren","MERCEDES-BENZ SLR 500","0"),
    ("InteriorColorText","schwarz","976.0","MERCEDES-BENZ","SLR","SLR McLaren","McLaren","MERCEDES-BENZ SLR 500","0"),
    ("BodyTypeText","Cabriolet","976.0","MERCEDES-BENZ","SLR","SLR McLaren","McLaren","MERCEDES-BENZ SLR 500","0"),
    ("Seats","6","56.0","BMW","Test","Test G7","G7","BMW Test 500","0"),
    ("Properties","\"Ab MFK\"","56.0","BMW","Test","Test G7","G7","BMW Test 500","0"),
    ("FuelTypeText","Benzin","56.0","BMW","Test","Test G7","G7","BMW Test 500","0"),
    ("Hp","626","56.0","BMW","Test","Test G7","G7","BMW Test 500","0"),
    ("City","Zuzwil","56.0","BMW","Test","Test G7","G7","BMW Test 500","0"),
    ("FirstRegYear","2007","56.0","BMW","Test","Test G7","G7","BMW Test 500","0"),
    ("Doors","5","56.0","BMW","Test","Test G7","G7","BMW Test 500","0"),
    ("ConsumptionTotalText","12.3 l/100km","56.0","BMW","Test","Test G7","G7","BMW Test 500","0"),
    ("ConditionTypeText","Occasion","56.0","BMW","Test","Test G7","G7","BMW Test 500","0"),
    ("FirstRegMonth","10","56.0","BMW","Test","Test G7","G7","BMW Test 500","0"),
    ("InteriorColorText","schwarz","56.0","BMW","Test","Test G7","G7","BMW Test 500","0"),
    ("BodyTypeText","Cabriolet","56.0","BMW","Test","Test G7","G7","BMW Test 500","0")
  ).toDF("Attribute Names", "Attribute Values", "ID", "MakeText", "ModelText", "ModelTypeText", "TypeName",
    "TypeNameFull", "entity_id")

  def assertDataFrameEquality(actualDF: DataFrame, expectedDF: DataFrame): Unit = {
    assert(actualDF.schema.equals(expectedDF.schema))
    assert(actualDF.collect().sameElements(expectedDF.collect()))
  }

  "initial DF" should "turn to pivoted dataframe" in {
    val procDF: DataFrame = Seq(
      ("976.0","MERCEDES-BENZ","SLR","SLR McLaren","McLaren","Cabriolet","Zuzwil","Occasion",null,"2","10","2007","Benzin","626","schwarz","\"Ab MFK\"","2"),
      ("56.0","BMW","Test","Test G7","G7","Cabriolet","Zuzwil","Occasion","12.3 l/100km","5","10","2007","Benzin","626","schwarz","\"Ab MFK\"","6"))
      .toDF("ID","MakeText","ModelText","ModelTypeText","TypeName","BodyTypeText","City","ConditionTypeText",
        "ConsumptionTotalText","Doors","FirstRegMonth","FirstRegYear","FuelTypeText","Hp","InteriorColorText",
        "Properties","Seats")
    assertDataFrameEquality(preProcessData(iniDF), procDF)
  }
}
