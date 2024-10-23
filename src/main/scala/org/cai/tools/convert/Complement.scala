package org.cai.tools.convert

import scala.collection.mutable.ArrayBuffer

/**
 * @author cai584770
 * @date 2024/8/7 13:53
 * @Version
 */
object Complement {

  def complement(byte: Byte): Byte = {
    (~byte).toByte
  }

  def complement(bytes: Array[Byte]): Array[Byte] = {
    val result = new Array[Byte](bytes.length)
    for (i <- bytes.indices) {
      result(i) = complement(bytes(i))
    }
    result
  }

  def reverseLong(value: Long): Long = {
    ~value
  }
  def reverseLongArray(arr: Array[Long]): Array[Long] = {
    arr.map(~_)
  }

  def complementString(data: String): String = {
    data.map {
      case 'A' => 'T'
      case 'T' => 'A'
      case 'G' => 'C'
      case 'C' => 'G'
      case other => other
    }
  }

}
