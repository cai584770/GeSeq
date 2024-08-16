package org.cai.tools.translate

import scala.collection.mutable

/**
 * @author cai584770
 * @date 2024/8/8 7:08
 * @Version
 */
object Decoding {

  def convertFromBinaryArray(bytes: Array[Byte]): StringBuilder = {
    val conversionMap = Map(Encoding.a -> 'A', Encoding.c -> 'C', Encoding.g -> 'G', Encoding.t -> 'T')

    val resultStringBuilder = new StringBuilder(bytes.length * 4)
    bytes.foreach { byte =>
      resultStringBuilder.append(conversionMap.getOrElse(((byte >> 6) & 0x03).toByte,"_")).append(conversionMap.getOrElse(((byte >> 4) & 0x03).toByte,"_")).append(conversionMap.getOrElse(((byte >> 2) & 0x03).toByte,"_")).append(conversionMap.getOrElse((byte & 0x03).toByte,"_"))
    }

    resultStringBuilder
  }
}
