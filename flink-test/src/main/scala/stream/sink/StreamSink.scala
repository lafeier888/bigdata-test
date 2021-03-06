package stream.sink

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import stream.StreamDataSource

object StreamSink {

  private val env: StreamExecutionEnvironment = StreamDataSource.env

  def main(args: Array[String]): Unit = {

    val ds = StreamDataSource.readFromTextFile


    val path = "F:\\code\\bigdata-test\\flink-test\\src\\main\\resources\\stream_out"
    //    ds.print()
    //    ds.printToErr()
    //    ds.writeAsText(path)
    //    ds.writeAsCsv(path)
    //    ds.writeToSocket("vm01", 7777, new SimpleStringSchema())
    //    ds.writeUsingOutputFormat(new TextOutputFormat[String](new Path(path)))
    //    ds.addSink(new FlinkKafkaProducer011[String]("vm01:9092,vm02:9092,vm03:9092", "test", new SimpleStringSchema()))
    val format = new MySink() //自定义sink
    ds.addSink(format)
    env.execute()
  }
}
