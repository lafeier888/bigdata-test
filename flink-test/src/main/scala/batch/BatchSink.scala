package batch

import java.net.URI

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.java.io.{CsvOutputFormat, TextOutputFormat}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011
import org.apache.flink.api.scala._
import org.apache.flink.core.fs.FileSystem.WriteMode
import org.apache.flink.core.fs.Path

object BatchSink {
  val env = BatchDataSource.env

  def main(args: Array[String]): Unit = {

    val ds = BatchDataSource.readTextfile


    val path = "F:\\code\\bigdata-test\\flink-test\\src\\main\\resources\\out_csv"

    //    ds.printOnTaskManager("ds output:")
    //    ds.printToErr()
//    ds.output(new TextOutputFormat[String](new Path(path)))

    ds.write(new TextOutputFormat[String](new Path(path)),path,WriteMode.OVERWRITE)
//    ds.writeAsText(path)
//    ds.writeAsCsv(path)

    env.execute()

  }
}
