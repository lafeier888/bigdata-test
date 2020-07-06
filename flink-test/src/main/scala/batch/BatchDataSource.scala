package batch

import org.apache.flink.api.scala._

case class Person(id: Int, name: String)
object BatchDataSource {



  val env = BatchEnvironmentAPI.createEnv

  def main(args: Array[String]): Unit = {
        readTextfile
        readCsv
        readCollection

  }

   def readTextfile = {
    val path = this.getClass.getResource("/words.txt").getPath
    val ds = env.readTextFile(path)
    ds.print()
    ds
  }

   def readCollection = {
    val ds = env.fromCollection(Set("hello", "word"))
    ds.print()
    ds
  }

   def readCsv = {

    val path = this.getClass.getResource("/words.csv").getPath
    val ds = env.readCsvFile[Person](path, ignoreFirstLine = true)
    ds.print()
    ds
  }
}
