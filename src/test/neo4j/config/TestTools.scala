package neo4j.config

import java.io.File

/**
 * @author cai584770
 * @date 2024/8/16 19:00
 * @Version
 */
object TestTools {


  def listAllFiles(dir: File): List[File] = {
    val files = dir.listFiles
    val folders = files.filter(_.isDirectory).toList
    val folderFiles = folders.flatMap(listAllFiles)
    files.filter(_.isFile).toList ++ folderFiles
  }

}
