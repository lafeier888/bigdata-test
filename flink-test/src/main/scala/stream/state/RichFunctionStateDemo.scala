package stream.state

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.common.state.ValueStateDescriptor
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import pojo.PersonInfo
import stream.StreamDataSource
import stream.transform.functions.MyRichMapFunction

object RichFunctionStateDemo {

  private val env: StreamExecutionEnvironment = StreamDataSource.env


  def main(args: Array[String]): Unit = {
    val ds = StreamDataSource.readFromSocket

    ds.map(new RichMapFunction[PersonInfo, (Int, String, Int, Int)] {
      val total = getRuntimeContext.getState(new ValueStateDescriptor[Int]("total", classOf[Int]))

      override def map(p: PersonInfo): (Int, String, Int, Int) = {
        total.update(total.value() + p.money)
        (p.id, p.name, p.money, total.value())
      }
    })


    ds.print()
    env.execute()
  }

}
