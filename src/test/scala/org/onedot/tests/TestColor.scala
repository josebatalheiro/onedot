package org.onedot.tests

import org.onedot.utils.CarNormUtils._
import org.scalatest.FlatSpec

class TestColor extends FlatSpec {
  "grun" should "be green" in {
    assert(translateColor("grun") == Color.Green.toString)
    assert(translateColor("grun mét.") == Color.Green.toString)
    assert(translateColor("grün") == Color.Green.toString)
  }

  "beige" should "be beige" in {
    assert(translateColor("beige") == Color.Beige.toString)
    assert(translateColor("beige mét.") == Color.Beige.toString)
  }

  "schwarz" should "be black" in {
    assert(translateColor("schwarz") == Color.Black.toString)
    assert(translateColor("schwarz mét.") == Color.Black.toString)
    assert(translateColor("schwärz") == Color.Black.toString)
  }

  "blau" should "be blue" in {
    assert(translateColor("blau") == Color.Blue.toString)
    assert(translateColor("blau mét.") == Color.Blue.toString)
    assert(translateColor("blaü") == Color.Blue.toString)
  }

  "braun" should "be brown" in {
    assert(translateColor("braun") == Color.Brown.toString)
    assert(translateColor("braun mét.") == Color.Brown.toString)
  }

  "gold" should "be gold" in {
    assert(translateColor("gold") == Color.Gold.toString)
    assert(translateColor("gold mét.") == Color.Gold.toString)
  }

  "grau" should "be gray" in {
    assert(translateColor("grau") == Color.Gray.toString)
    assert(translateColor("grau mét.") == Color.Gray.toString)
    assert(translateColor("anthrazit") == Color.Gray.toString)
    assert(translateColor("anthrazit mét.") == Color.Gray.toString)
  }

  "weiss" should "be white" in {
    assert(translateColor("weiss") == Color.White.toString)
    assert(translateColor("weiss mét.") == Color.White.toString)
    assert(translateColor("weiß") == Color.White.toString)
  }

  "violett" should "be purple" in {
    assert(translateColor("violett") == Color.Purple.toString)
    assert(translateColor("violett mét.") == Color.Purple.toString)
  }

  "rot" should "be red" in {
    assert(translateColor("rot") == Color.Red.toString)
    assert(translateColor("rot mét.") == Color.Red.toString)
    assert(translateColor("röt mét.") == Color.Red.toString)
  }

  "silber" should "be silver" in {
    assert(translateColor("silber") == Color.Silver.toString)
    assert(translateColor("silber mét.") == Color.Silver.toString)
  }

  "orange" should "be orange" in {
    assert(translateColor("orange") == Color.Orange.toString)
    assert(translateColor("orange mét.") == Color.Orange.toString)
  }

  "gelb" should "be yellow" in {
    assert(translateColor("gelb") == Color.Yellow.toString)
    assert(translateColor("gelb mét.") == Color.Yellow.toString)
  }

  "pink" should "be other" in {
    assert(translateColor("pink") == Color.Other.toString)
  }

  "null" should "be null" in {
    assert(translateColor(null) == null)
  }

}
