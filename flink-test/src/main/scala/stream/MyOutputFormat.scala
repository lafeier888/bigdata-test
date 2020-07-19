package stream

import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}
import pojo.PersonInfo

/**
 * 自定义sink 不完善
 */
class MyOutputFormat() extends RichSinkFunction[PersonInfo] {


  var connection: Int = -1

  override def open(parameters: Configuration): Unit = {
    if (connection < 0) connection = 10086
    println("open method:" + connection)
  }

  override def invoke(value: PersonInfo, context: SinkFunction.Context[_]): Unit = {

    println("invoke method:" + connection)
  }


}
