package org.cai.serialize

import java.nio.ByteBuffer

/**
 * @author cai584770
 * @date 2024/10/22 10:28
 * @Version
 */
object SerializeBase {

  def serializeListOfPairs(list: List[(Int, Int)]): Array[Byte] = {
    val buffer = ByteBuffer.allocate(list.size * 8)
    list.foreach { case (a, b) =>
      buffer.putInt(a).putInt(b)
    }
    buffer.array()
  }

  def serializeListOfPairWithString(list: List[(Int, String)]): Array[Byte] = {
    val buffer = ByteBuffer.allocate(list.size * (4 + 4 + list.map(_._2.getBytes.length).sum))
    list.foreach { case (a, str) =>
      buffer.putInt(a).putInt(str.getBytes.length).put(str.getBytes)
    }
    buffer.array()
  }


}
