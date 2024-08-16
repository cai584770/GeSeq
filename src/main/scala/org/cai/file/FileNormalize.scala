package org.cai.file


object FileNormalize {

  def normalize(data: String): String = {
    val sequence: String = data.grouped(63).mkString("\n")
    sequence
  }

  def remove(data: String): String = {
    val sequence: String = data.replaceAll("\\s", "")
    sequence
  }

  def insertNewlines(text: String, interval: Int): String = {
    text.grouped(interval).mkString("\n")
  }

}
