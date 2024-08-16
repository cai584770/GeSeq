package org.cai.file

import java.io.{File, PrintWriter}
import java.nio.file.{Files, Paths, StandardOpenOption}
import scala.io.Source

object FileProcess {

  def getFASTQ(filePath: String): Unit ={

  }

  def getInformationAndSequence(filePath: String): (String,String) = {
    var information = ""
    var sequence = ""
    try {
      val source = Source.fromFile(filePath)
      val data = source.mkString
      source.close()

      val newlineIndex = data.indexOf('\n')
      if (newlineIndex != -1) {
        information = data.substring(0, newlineIndex)
        sequence = data.substring(newlineIndex + 1)
      } else {
        information = data
      }
      sequence = FileNormalize.remove(sequence)

    } catch {
      case e: Exception => e.printStackTrace()
    }
    (information.tail,sequence)
  }

  def processFile(filePath: String): String = {
    var information = ""
    var sequence = ""

    try {
      val source = Source.fromFile(filePath)
      val fileContent = source.mkString
      source.close()

      information = fileContent.linesIterator.next()
      sequence = fileContent.linesIterator.drop(1).mkString("\n")
      sequence = FileNormalize.normalize(FileNormalize.remove(sequence))

      val writer = new PrintWriter(new File(filePath))
      writer.write(information + "\n" + sequence)
      writer.close()
    } catch {
      case e: Exception => e.printStackTrace()
    }

    if (information.length > 1)
      information.substring(1)
    else
      information
  }



  def processFile(filePath: String, folderPath: String): Unit = {
    createFolder(folderPath)
    val delimiter = '>'
    val source = Source.fromFile(filePath)
    var buffer = new StringBuilder

    var information = new String
    var sequence = new String
    var chromosomeName = new String

    var tempPath = new String

    try {
      for (char <- source) {
        if (char == delimiter && buffer.nonEmpty) {
          var result = buffer.toString()
          information = getInformation(result)
          sequence = result.replace(information,"")
          sequence = FileNormalize.normalize(FileNormalize.remove(sequence))

          chromosomeName = getChromosomeName(information)
          tempPath = folderPath+"/"+chromosomeName+".fa"
          writeStringToFile(">"+information,tempPath)
          writeStringToFile(sequence,tempPath)

          buffer.clear()
        } else {
          buffer += char
        }
      }

      val result = buffer.toString()
      if (result.nonEmpty) {
        information = getInformation(result)
        sequence = result.replace(information, "")
        sequence = FileNormalize.normalize(FileNormalize.remove(sequence))

        chromosomeName = getChromosomeName(information)
        tempPath = folderPath + "/" + chromosomeName + ".fa"
        writeStringToFile(">" + information + "\n", tempPath)
        writeStringToFile(sequence, tempPath)
      }
    } finally {
      source.close()
    }
  }

  def getInformation(data: String): String = {
    val newlineIndex = data.indexOf('\n')
    val resultString = if (newlineIndex != -1) {
      val deletedContent = data.substring(0, newlineIndex + 1)
      deletedContent
    } else {
      val deletedContent = data
      deletedContent
    }

    resultString
  }

  def getChromosomeName(data: String): String = {
    val regex = "(?i)chr(\\w*)".r
    val result = regex.findFirstMatchIn(data).fold("") { matchResult =>
      "chr" + matchResult.group(1)
    }
    result
  }


  def writeStringToFile(content: String, filePath: String, append: Boolean = true): Unit = {
    try {
      val options = if (append) {
        Seq(StandardOpenOption.CREATE, StandardOpenOption.APPEND)
      } else {
        Seq(StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
      }

      Files.write(Paths.get(filePath), content.getBytes, options: _*)
    } catch {
      case e: Exception =>
        println(s"Error writing to file: $e")
    }
  }

  def createFolder(folderPath: String): Unit = {
    val path = Paths.get(folderPath)

    if (!Files.exists(path) || !Files.isDirectory(path)) {
      try {
        Files.createDirectories(path)
      } catch {
        case e: Exception => println(s"Error creating directory: $e")
      }
    } else {
      println(s"Directory '$folderPath' already exists.")
    }
  }


  def readToString(filePath:String):String = {
    val datasources = Source.fromFile(filePath)
    val datas = datasources.mkString
    datasources.close()
    datas
  }




}

