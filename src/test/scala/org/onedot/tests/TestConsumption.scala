package org.onedot.tests

import org.onedot.utils.CarNormUtils.{getConsumptionUnit, _}
import org.scalatest.FlatSpec

class TestConsumption extends FlatSpec {
  "Correct consumption" should "have float value" in {
    assert(getConsumptionValue("8.7 l/100km").get == 8.7f)
  }

  "Incorrect consumption" should "have empty value" in {
    assert(getConsumptionValue("8.7").isEmpty)
    assert(getConsumptionValue("").isEmpty)
    assert(getConsumptionValue(null).isEmpty)
    assert(getConsumptionValue("eight.seven l/km").isEmpty)
  }

  "Correct consumption" should "have km unit" in {
    assert(getConsumptionUnit("8.7 l/100km").get == "l_km_consumption")
  }

  "Incorrect consumption" should "have empty unit" in {
    assert(getConsumptionUnit("8.7 km").isEmpty)
    assert(getConsumptionUnit("").isEmpty)
    assert(getConsumptionUnit(null).isEmpty)
    assert(getConsumptionUnit("l/km").isEmpty)
  }


}
