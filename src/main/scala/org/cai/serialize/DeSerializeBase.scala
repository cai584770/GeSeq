package org.cai.serialize

import java.nio.ByteBuffer

/**
 * @author cai584770
 * @date 2024/10/22 10:29
 * @Version
 */
object DeSerializeBase {

  def deserializeListOfPairs(bytes: Array[Byte]): List[(Int, Int)] = {
    if (bytes.length % 8 != 0) {
      throw new IllegalArgumentException("Invalid byte array for deserializing list of pairs.")
    }
    val result = new collection.mutable.ListBuffer[(Int, Int)]
    val buffer = ByteBuffer.wrap(bytes)
    for (_ <- 0 until bytes.length / 8) {
      val a = buffer.getInt
      val b = buffer.getInt
      result += ((a, b))
    }
    result.toList
  }

  def deserializeListOfPairWithString(bytes: Array[Byte]): List[(Int, String)] = {
    val result = new collection.mutable.ListBuffer[(Int, String)]
    val buffer = ByteBuffer.wrap(bytes)
    while (buffer.hasRemaining) {
      val a = buffer.getInt
      val strLength = buffer.getInt
      val strBytes = new Array[Byte](strLength)
      buffer.get(strBytes)
      result += ((a, new String(strBytes)))
    }
    result.toList
  }

}
