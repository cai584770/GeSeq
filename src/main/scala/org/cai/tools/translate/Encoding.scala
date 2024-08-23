package org.cai.tools.translate

import scala.collection.Seq

/**
 * @author cai584770
 * @date 2024/8/6 12:41
 * @Version
 */
object Encoding {
  val a: Byte = 0
  val t: Byte = 3
  val c: Byte = 1
  val g: Byte = 2
  val r002 = Seq(t, 'u', c, 'y')
  val r021 = Seq(a, g, c)
  val r003 = Seq(c, t)
  val r004 = Seq(t, c, 'u', 'y')
  val r005 = Seq(a, c, g, t, 'u', 'r', 'y', 's', 'w', 'k', 'm', 'b', 'd', 'h', 'v', 'n')
  val r006 = Seq(a, t, c, 'u', 'w', 'm', 'h', 'y')
  val r1 = Seq(c, a, 'm')
  val r11 = Seq(t, 'u')
  val r207 = Seq(c, t, 'u', 'y')

  def encodingDNA(dnaSequence: String): Array[Byte] = {
    val nucleotideToBinary = Map('a' -> "00", 't' -> "11", 'u' -> "11", 'c' -> "01", 'g' -> "10")
    val binaryString = dnaSequence.map(nucleotideToBinary(_)).mkString
    val paddingLength = (8 - binaryString.length % 8) % 8
    val paddedBinaryString = binaryString + "0" * paddingLength
    paddedBinaryString.grouped(8).map(Integer.parseInt(_, 2).toByte).toArray
  }



  def encodingProtein(d1: Any, d2: Any, d3: Any): String = {
    (d1, d2, d3) match {
      case (`a`, `a`, d) if r002.contains(d) => "N"
      case (`a`, `a`, d) if r021.contains(d) => "K"
      case (`a`, `c`, d) if r005.contains(d) => "T"
      case (`a`, `g`, d) if r021.contains(d) => "R"
      case (`a`, `g`, d) if r003.contains(d) => "S"
      case (`a`, d2, d) if r11.contains(d2) && r006.contains(d) => "I"
      case (`a`, d2, `g`) if r11.contains(d2) => "M"

      case (`c`, `a`, d) if r004.contains(d) => "H"
      case (`c`, `a`, d) if r021.contains(d) => "Q"
      case (`c`, `c`, d) if r005.contains(d) => "P"
      case (`c`, `g`, d) if r005.contains(d) => "R"
      case (`c`, d2, d) if r11.contains(d2) && r005.contains(d) => "L"

      case (`g`, `a`, d) if r004.contains(d) => "D"
      case (`g`, `a`, d) if r021.contains(d) => "E"
      case (`g`, `c`, d) if r005.contains(d) => "A"
      case (`g`, `g`, d) if r005.contains(d) => "G"
      case (`g`, d2, d) if r11.contains(d2) && r005.contains(d) => "V"

      case (d1, `a`, d) if r11.contains(d1) && r207.contains(d) => "Y"
      case (d1, `a`, d) if r11.contains(d1) && r021.contains(d) => "*"
      case (d1, `c`, d) if r11.contains(d1) && r005.contains(d) => "S"
      case (d1, `g`, `a`) if r11.contains(d1) => "*"
      case (d1, `g`, `g`) if r11.contains(d1) => "W"
      case (d1, `g`, d) if r11.contains(d1) && r207.contains(d) => "C"
      case (d1, d2, d) if r11.contains(d1) && r11.contains(d2) && r004.contains(d) => "F"
      case (d1, d2, d) if r11.contains(d1) && r11.contains(d2) && r021.contains(d) => "L"
      case (d1, d2, `a`) if r11.contains(d1) && r021.contains(d2) => "*"

      case (d1, `g`, d) if r1.contains(d1) && r021.contains(d) => "R"
      case (d1, d2, d) if r207.contains(d1) && r11.contains(d2) && r021.contains(d) => "L"

      case _ => ""
    }
  }

  def encodingProtein1(d1: Any, d2: Any, d3: Any): String = {
    if (d1 == a) {
      if (d2 == a) {
        if (r002.contains(d3)) "N"
        else if (r021.contains(d3)) "K"
        else ""
      } else if (d2 == c) {
        if (r005.contains(d3)) "T"
        else ""
      } else if (d2 == g) {
        if (r021.contains(d3)) "R"
        else if (r003.contains(d3)) "S"
        else ""
      } else if (r11.contains(d2)) {
        if (r006.contains(d3)) "I"
        else if (d3 == g) "M"
        else ""
      } else ""
    } else if (d1 == c) {
      if (d2 == a) {
        if (r004.contains(d3)) "H"
        else if (r021.contains(d3)) "Q"
        else ""
      } else if (d2 == c) {
        if (r005.contains(d3)) "P"
        else ""
      } else if (d2 == g) {
        if (r005.contains(d3)) "R"
        else ""
      } else if (r11.contains(d2)) {
        if (r005.contains(d3)) "L"
        else ""
      } else ""
    } else if (d1 == g) {
      if (d2 == a) {
        if (r004.contains(d3)) "D"
        else if (r021.contains(d3)) "E"
        else ""
      } else if (d2 == c) {
        if (r005.contains(d3)) "A"
        else ""
      } else if (d2 == g) {
        if (r005.contains(d3)) "G"
        else ""
      } else if (r11.contains(d2)) {
        if (r005.contains(d3)) "V"
        else ""
      } else ""
    } else if (r11.contains(d1)) {
      if (d2 == a) {
        if (r207.contains(d3)) "Y"
        else if (r021.contains(d3)) "*"
        else ""
      } else if (d2 == c) {
        if (r005.contains(d3)) "S"
        else ""
      } else if (d2 == g) {
        if (d3 == a) "*"
        else if (d3 == g) "W"
        else if (r207.contains(d3)) "C"
        else ""
      } else if (r11.contains(d2)) {
        if (r004.contains(d3)) "F"
        else if (r021.contains(d3)) "L"
        else ""
      } else if (r021.contains(d2)) {
        if (d3 == a) "*"
        else ""
      } else ""
    } else if (r1.contains(d1)) {
      if (d2 == g) {
        if (r021.contains(d3)) "R"
        else ""
      } else ""
    } else if (r207.contains(d1)) {
      if (r11.contains(d2)) {
        if (r021.contains(d3)) "L"
        else ""
      } else ""
    } else ""
  }



}
