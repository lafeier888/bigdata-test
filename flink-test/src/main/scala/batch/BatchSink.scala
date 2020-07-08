package batch

object BatchSink {
  val env = BatchEnvironmentAPI.createEnv

  def main(args: Array[String]): Unit = {

    val ds = BatchDataSource.readTextfile

  }
}
