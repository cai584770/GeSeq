package org.cai.tools.convert

import scala.collection.mutable.ArrayBuffer

/**
 * @author cai584770
 * @date 2024/8/7 13:53
 * @Version
 */
object Convert {

  def convert(byte: Byte): Byte = {
    var reversed: Int = 0
    for (i <- 0 until 4) {
      val pair = (byte >> (2 * i)) & 0x03
      reversed |= (pair << (2 * (3 - i)))
    }
    reversed.toByte
  }

  def convert(byteList: Array[Byte]): Array[Byte] = {
    val result = ArrayBuffer[Byte]()
    byteList.foreach { byte =>
      result += convert(byte)
    }

    result.reverse.toArray
  }

  def convertDirect(byte: Byte): Byte = {
    ((byte & 0x03) << 6 |
      (byte & 0x0C) << 2 |
      (byte & 0x30) >> 2 |
      (byte & 0xC0) >> 6).toByte
  }

  def convertDirect(byteList: Array[Byte]): Array[Byte] = {
    val result = new Array[Byte](byteList.length)
    for (i <- byteList.indices) {
      result(i) = convert(byteList(i))
    }
    result.reverse
  }


}
