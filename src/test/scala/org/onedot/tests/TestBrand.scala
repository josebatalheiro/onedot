package org.onedot.tests

import org.onedot.utils.CarNormUtils._
import org.scalatest.FlatSpec

class TestBrand extends FlatSpec {
  "bmwalpina" should "turn to Alpina" in {
    assert(normBrand("BMW-ALPINA") == "Alpina")
  }

  "bmw" should "turn to BMW" in {
    assert(normBrand("BMW") == "BMW")
  }

  "Non norm brands" should "be normalized" in {
    assert(normBrand("ALFA ROMEO") == "Alfa Romeo")
    assert(normBrand("ISOTTA-FRASCHINI") == "Isotta-Fraschini")
  }

}
