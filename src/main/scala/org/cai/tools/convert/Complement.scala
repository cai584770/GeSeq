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

  def complementString(sequence: String): String = {
    sequence
      .replace('A', 'X')
      .replace('T', 'A')
      .replace('X', 'T')
      .replace('G', 'Y')
      .replace('C', 'G')
      .replace('Y', 'C')
  }
}
