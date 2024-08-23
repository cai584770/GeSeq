package org.cai.tools.convert

import scala.collection.mutable.ArrayBuffer

/**
 * @author cai584770
 * @date 2024/8/7 13:53
 * @Version
 */
object Reverse {

  def reverse(byte: Byte): Byte = {
    var reversed: Int = 0
    for (i <- 0 until 4) {
      val pair = (byte >> (2 * i)) & 0x03
      reversed |= (pair << (2 * (3 - i)))
    }
    reversed.toByte
  }

  def reverse(byteList: Array[Byte]): Array[Byte] = {
    val result = ArrayBuffer[Byte]()
    byteList.foreach { byte =>
      result += reverse(byte)
    }

    result.reverse.toArray
  }

  def reverseLong(longValue: Long): Long = {
    (0 until 32).map { i =>
      val shift = (31 - i * 2) * 2
      (longValue & (3L << (i * 2))) << shift
    }.reduce(_ | _) | (0 until 32).map { i =>
      val shift = (i * 2) * 2
      (longValue & (3L << (62 - i * 2))) >> shift
    }.reduce(_ | _)
  }

  def reverseLong(longArray: Array[Long]): Array[Long] = {
    val result = new Array[Long](longArray.length)
    for (i <- longArray.indices) {
      result(i) = reverseLong(longArray(i))
    }
    result.reverse
  }

  def reverseDirect(byte: Byte): Byte = {
    ((byte & 0x03) << 6 |
      (byte & 0x0C) << 2 |
      (byte & 0x30) >> 2 |
      (byte & 0xC0) >> 6).toByte
  }

  def reverseDirect(byteList: Array[Byte]): Array[Byte] = {
    val result = new Array[Byte](byteList.length)
    for (i <- byteList.indices) {
      result(i) = reverse(byteList(i))
    }
    result.reverse
  }


}
