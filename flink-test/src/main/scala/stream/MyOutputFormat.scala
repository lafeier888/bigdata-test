package stream

import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}

/**
 * 自定义sink 不完善
 */
class MyOutputFormat() extends RichSinkFunction[String] {


  var connection: Int = -1

  override def open(parameters: Configuration): Unit = {
    if (connection < 0) connection = 10086
    println("open method:" + connection)
  }

  override def invoke(value: String, context: SinkFunction.Context[_]): Unit = {

    println("invoke method:" + connection)
  }


}
