package org.cai.tools.translate

import scala.collection.mutable

/**
 * @author cai584770
 * @date 2024/8/6 12:46
 * @Version
 */
object TranslateTools {

//  def translate(ba: Array[Byte]): String = {
//    var cur = 0
//    var temp1: Byte = 0
//    var temp2: Byte = 0
//
//    val protein: mutable.StringBuilder = new mutable.StringBuilder()
//    ba.foreach { byte =>
//      val unit1 = ((byte >> 6) & 0x03).toByte
//      val unit2 = ((byte >> 4) & 0x03).toByte
//      val unit3 = ((byte >> 2) & 0x03).toByte
//      val unit4 = (byte & 0x03).toByte
//
//      if (cur == 0) {
//        protein.append(Encoding.encodingProtein(unit1, unit2, unit3))
//        cur = 1
//        temp1 = unit4
//      } else if (cur == 1) {
//        protein.append(Encoding.encodingProtein(temp1, unit1, unit2))
//        cur = 2
//        temp1 = unit3
//        temp2 = unit4
//      } else if (cur == 2) {
//        protein.append(Encoding.encodingProtein(temp1, temp2, unit1))
//        protein.append(Encoding.encodingProtein(unit2, unit3, unit4))
//
//        cur = 0
//      }
//
//    }
//
//    if (cur != 2) {
//      protein.setLength(protein.length - 1)
//    }
//
//    protein.toString()
//  }


//  def translate(ba: Array[Byte]): String = {
//    var cur = 0
//    var temp1: Byte = 0
//    var temp2: Byte = 0
//
//    val proteinBuilder = new StringBuilder()
//    for (byte <- ba) {
//      val unit1 = (byte >> 6) & 0x03
//      val unit2 = (byte >> 4) & 0x03
//      val unit3 = (byte >> 2) & 0x03
//      val unit4 = byte & 0x03
//
//      if (cur == 0) {
//        proteinBuilder.append(Encoding.encodingProtein(unit1.toByte, unit2.toByte, unit3.toByte))
//        cur = 1
//        temp1 = unit4.toByte
//      } else if (cur == 1) {
//        proteinBuilder.append(Encoding.encodingProtein(temp1, unit1.toByte, unit2.toByte))
//        cur = 2
//        temp1 = unit3.toByte
//        temp2 = unit4.toByte
//      } else if (cur == 2) {
//        proteinBuilder.append(Encoding.encodingProtein(temp1, temp2, unit1.toByte))
//        proteinBuilder.append(Encoding.encodingProtein(unit2.toByte, unit3.toByte, unit4.toByte))
//        cur = 0
//      }
//    }
//
//    if (cur != 2) {
//      proteinBuilder.setLength(proteinBuilder.length - 1)
//    }
//
//    proteinBuilder.toString()
//  }

  def translate(ba: Array[Byte]):mutable.StringBuilder  = {
    var cur = 0
    var temp1: Byte = 0
    var temp2: Byte = 0

    val proteinBuilder = new mutable.StringBuilder()

    // Process byte array
    for (byte <- ba) {
      val unit1 = (byte >> 6).toByte & 0x03
      val unit2 = (byte >> 4).toByte & 0x03
      val unit3 = (byte >> 2).toByte & 0x03
      val unit4 = byte.toByte & 0x03

      cur match {
        case 0 =>
          // First codon
          proteinBuilder.append(Encoding.encodingProtein(unit1.toByte, unit2.toByte, unit3.toByte))
          cur = 1
          temp1 = unit4.toByte

        case 1 =>
          // Second codon
          proteinBuilder.append(Encoding.encodingProtein(temp1, unit1.toByte, unit2.toByte))
          cur = 2
          temp1 = unit3.toByte
          temp2 = unit4.toByte

        case 2 =>
          // Third and fourth codons
          proteinBuilder.append(Encoding.encodingProtein(temp1, temp2, unit1.toByte))
          proteinBuilder.append(Encoding.encodingProtein(unit2.toByte, unit3.toByte, unit4.toByte))
          cur = 0
      }
    }

    // Handle case where partial codon exists (incomplete codon)
    if (cur != 2) {
      proteinBuilder.setLength(proteinBuilder.length - 1) // Trim last incomplete protein
    }

    proteinBuilder
  }

//  def translate(ba: Array[Byte]): StringBuilder = {
//    var cur = 0
//    var temp1: Byte = 0
//    var temp2: Byte = 0
//
//    val protein: mutable.StringBuilder = new mutable.StringBuilder()
//    ba.foreach { byte =>
//      val unit1 = ((byte >> 6) & 0x03).toByte
//      val unit2 = ((byte >> 4) & 0x03).toByte
//      val unit3 = ((byte >> 2) & 0x03).toByte
//      val unit4 = (byte & 0x03).toByte
//
//      if (cur == 0) {
//        protein.append(Encoding.encodingProtein(unit1, unit2, unit3))
//        cur = 1
//        temp1 = unit4
//      } else if (cur == 1) {
//        protein.append(Encoding.encodingProtein(temp1, unit1, unit2))
//        cur = 2
//        temp1 = unit3
//        temp2 = unit4
//      } else if (cur == 2) {
//        protein.append(Encoding.encodingProtein(temp1, temp2, unit1))
//        protein.append(Encoding.encodingProtein(unit2, unit3, unit4))
//
//        cur = 0
//      }
//
//    }
//
//    if (cur != 2) {
//      protein.setLength(protein.length - 1)
//    }
//
//    protein
//  }

}
