package org.cai.tools.translate

import scala.collection.Seq

/**
 * @author cai584770
 * @date 2024/8/6 12:41
 * @Version
 */
object Encoding {
  val t: Byte = 3
  val a: Byte = 0
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

  val codonTable: Map[(Byte, Byte, Byte), String] = Map(
    // UUU -> F, UUC -> F
    (0x00.toByte, 0x00.toByte, 0x00.toByte) -> "F", // UUU
    (0x00.toByte, 0x00.toByte, 0x01.toByte) -> "F", // UUC
    // UUA -> L, UUG -> L
    (0x00.toByte, 0x00.toByte, 0x02.toByte) -> "L", // UUA
    (0x00.toByte, 0x00.toByte, 0x03.toByte) -> "L", // UUG
    // CUU -> L, CUC -> L, CUA -> L, CUG -> L
    (0x01.toByte, 0x00.toByte, 0x00.toByte) -> "L", // CUU
    (0x01.toByte, 0x00.toByte, 0x01.toByte) -> "L", // CUC
    (0x01.toByte, 0x00.toByte, 0x02.toByte) -> "L", // CUA
    (0x01.toByte, 0x00.toByte, 0x03.toByte) -> "L", // CUG
    // AUU -> I, AUC -> I, AUA -> I
    (0x02.toByte, 0x00.toByte, 0x00.toByte) -> "I", // AUU
    (0x02.toByte, 0x00.toByte, 0x01.toByte) -> "I", // AUC
    (0x02.toByte, 0x00.toByte, 0x02.toByte) -> "I", // AUA
    // AUG -> M
    (0x02.toByte, 0x00.toByte, 0x03.toByte) -> "M", // AUG
    // GUU -> V, GUC -> V, GUA -> V, GUG -> V
    (0x03.toByte, 0x00.toByte, 0x00.toByte) -> "V", // GUU
    (0x03.toByte, 0x00.toByte, 0x01.toByte) -> "V", // GUC
    (0x03.toByte, 0x00.toByte, 0x02.toByte) -> "V", // GUA
    (0x03.toByte, 0x00.toByte, 0x03.toByte) -> "V", // GUG
    // UCU -> S, UCC -> S, UCA -> S, UCG -> S
    (0x00.toByte, 0x01.toByte, 0x00.toByte) -> "S", // UCU
    (0x00.toByte, 0x01.toByte, 0x01.toByte) -> "S", // UCC
    (0x00.toByte, 0x01.toByte, 0x02.toByte) -> "S", // UCA
    (0x00.toByte, 0x01.toByte, 0x03.toByte) -> "S", // UCG
    // CCU -> P, CCC -> P, CCA -> P, CCG -> P
    (0x01.toByte, 0x01.toByte, 0x00.toByte) -> "P", // CCU
    (0x01.toByte, 0x01.toByte, 0x01.toByte) -> "P", // CCC
    (0x01.toByte, 0x01.toByte, 0x02.toByte) -> "P", // CCA
    (0x01.toByte, 0x01.toByte, 0x03.toByte) -> "P", // CCG
    // ACU -> T, ACC -> T, ACA -> T, ACG -> T
    (0x02.toByte, 0x01.toByte, 0x00.toByte) -> "T", // ACU
    (0x02.toByte, 0x01.toByte, 0x01.toByte) -> "T", // ACC
    (0x02.toByte, 0x01.toByte, 0x02.toByte) -> "T", // ACA
    (0x02.toByte, 0x01.toByte, 0x03.toByte) -> "T", // ACG
    // GCU -> A, GCC -> A, GCA -> A, GCG -> A
    (0x03.toByte, 0x01.toByte, 0x00.toByte) -> "A", // GCU
    (0x03.toByte, 0x01.toByte, 0x01.toByte) -> "A", // GCC
    (0x03.toByte, 0x01.toByte, 0x02.toByte) -> "A", // GCA
    (0x03.toByte, 0x01.toByte, 0x03.toByte) -> "A", // GCG
    // UAU -> Y, UAC -> Y
    (0x00.toByte, 0x02.toByte, 0x00.toByte) -> "Y", // UAU
    (0x00.toByte, 0x02.toByte, 0x01.toByte) -> "Y", // UAC
    // UAA -> Stop, UAG -> Stop
    (0x00.toByte, 0x02.toByte, 0x02.toByte) -> "*", // UAA
    (0x00.toByte, 0x02.toByte, 0x03.toByte) -> "*", // UAG
    // CAU -> H, CAC -> H
    (0x01.toByte, 0x02.toByte, 0x00.toByte) -> "H", // CAU
    (0x01.toByte, 0x02.toByte, 0x01.toByte) -> "H", // CAC
    // CAA -> Q, CAG -> Q
    (0x01.toByte, 0x02.toByte, 0x02.toByte) -> "Q", // CAA
    (0x01.toByte, 0x02.toByte, 0x03.toByte) -> "Q", // CAG
    // AAU -> N, AAC -> N
    (0x02.toByte, 0x02.toByte, 0x00.toByte) -> "N", // AAU
    (0x02.toByte, 0x02.toByte, 0x01.toByte) -> "N", // AAC
    // AAA -> K, AAG -> K
    (0x02.toByte, 0x02.toByte, 0x02.toByte) -> "K", // AAA
    (0x02.toByte, 0x02.toByte, 0x03.toByte) -> "K", // AAG
    // GAU -> D, GAC -> D
    (0x03.toByte, 0x02.toByte, 0x00.toByte) -> "D", // GAU
    (0x03.toByte, 0x02.toByte, 0x01.toByte) -> "D", // GAC
    // GAA -> E, GAG -> E
    (0x03.toByte, 0x02.toByte, 0x02.toByte) -> "E", // GAA
    (0x03.toByte, 0x02.toByte, 0x03.toByte) -> "E", // GAG
    // UGU -> C, UGC -> C
    (0x00.toByte, 0x03.toByte, 0x00.toByte) -> "C", // UGU
    (0x00.toByte, 0x03.toByte, 0x01.toByte) -> "C", // UGC
    // UGA -> Stop, UGG -> W
    (0x00.toByte, 0x03.toByte, 0x02.toByte) -> "*", // UGA
    (0x00.toByte, 0x03.toByte, 0x03.toByte) -> "W", // UGG
    // CGU -> R, CGC -> R, CGA -> R, CGG -> R
    (0x01.toByte, 0x03.toByte, 0x00.toByte) -> "R", // CGU
    (0x01.toByte, 0x03.toByte, 0x01.toByte) -> "R", // CGC
    (0x01.toByte, 0x03.toByte, 0x02.toByte) -> "R", // CGA
    (0x01.toByte, 0x03.toByte, 0x03.toByte) -> "R", // CGG
    // AGU -> S, AGC -> S
    (0x02.toByte, 0x03.toByte, 0x00.toByte) -> "S", // AGU
    (0x02.toByte, 0x03.toByte, 0x01.toByte) -> "S", // AGC
    // AGA -> R, AGG -> R
    (0x02.toByte, 0x03.toByte, 0x02.toByte) -> "R", // AGA
    (0x02.toByte, 0x03.toByte, 0x03.toByte) -> "R", // AGG
    // GGU -> G, GGC -> G, GGA -> G, GGG -> G
    (0x03.toByte, 0x03.toByte, 0x00.toByte) -> "G", // GGU
    (0x03.toByte, 0x03.toByte, 0x01.toByte) -> "G", // GGC
    (0x03.toByte, 0x03.toByte, 0x02.toByte) -> "G", // GGA
    (0x03.toByte, 0x03.toByte, 0x03.toByte) -> "G" // GGG
  )

  def encodingProtein(d1: Byte, d2: Byte, d3: Byte): String = {
    codonTable.getOrElse((d1, d2, d3), "")
  }

  def encodingProteinFirst(d1: Any, d2: Any, d3: Any): String = {
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

  def encodingProtein2(d1: Any, d2: Any, d3: Any): String = {
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
