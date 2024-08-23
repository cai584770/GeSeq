package org.cai.tools.translate

import scala.collection.mutable

/**
 * @author cai584770
 * @date 2024/8/6 12:46
 * @Version
 */
object TranslateTools {

  def translate(ba: Array[Byte]): String = {
    var cur = 0
    var temp1: Byte = 0
    var temp2: Byte = 0

    val protein: mutable.StringBuilder = new mutable.StringBuilder()
    ba.foreach { byte =>
      val unit1 = ((byte >> 6) & 0x03).toByte
      val unit2 = ((byte >> 4) & 0x03).toByte
      val unit3 = ((byte >> 2) & 0x03).toByte
      val unit4 = (byte & 0x03).toByte

      if (cur == 0) {
        protein.append(Encoding.encodingProtein(unit1, unit2, unit3))
        cur = 1
        temp1 = unit4
      } else if (cur == 1) {
        protein.append(Encoding.encodingProtein(temp1, unit1, unit2))
        cur = 2
        temp1 = unit3
        temp2 = unit4
      } else if (cur == 2) {
        protein.append(Encoding.encodingProtein(temp1, temp2, unit1))
        protein.append(Encoding.encodingProtein(unit2, unit3, unit4))

        cur = 0
      }

    }

    if (cur != 2) {
      protein.setLength(protein.length - 1)
    }

    protein.toString()
  }


}
