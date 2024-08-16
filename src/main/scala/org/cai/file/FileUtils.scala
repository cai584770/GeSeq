package org.cai.file

import java.io.{FileInputStream, FileOutputStream, IOException}

/**
 * @author cai584770
 * @date 2024/4/23 11:00
 * @Version
 */
object FileUtils {

  def writeBytesToFile(byteArray: Array[Byte], fileName: String): Unit = {
    try {
      val fos = new FileOutputStream(fileName)
      fos.write(byteArray)
      fos.close()
    } catch {
      case e: IOException => e.printStackTrace()
    }
  }

  def readBytesFromFile(fileName: String): Array[Byte] = {
    var fileBytes: Array[Byte] = Array[Byte]()
    try {
      val fis = new FileInputStream(fileName)
      fileBytes = Stream.continually(fis.read).takeWhile(_ != -1).map(_.toByte).toArray
      fis.close()
    } catch {
      case e: IOException =>
        e.printStackTrace()
    }
    fileBytes
  }

}
