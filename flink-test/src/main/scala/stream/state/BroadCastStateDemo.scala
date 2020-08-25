package stream.state

import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.streaming.api.functions.co.KeyedBroadcastProcessFunction
import org.apache.flink.util.Collector
import pojo.PersonInfo
import stream.StreamDataSource
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.CheckpointingMode
object BroadCastStateDemo {
  def main(args: Array[String]): Unit = {

    val ds = StreamDataSource.readFromSocket

    val keyByDS = ds.keyBy("city")
    val broadcastDs = ds.broadcast()
    val broadAndConnectDs = keyByDS.connect(broadcastDs)


    val result = broadAndConnectDs.process(new KeyedBroadcastProcessFunction[String, PersonInfo, PersonInfo, (String)] {
      var mystate:ValueState[Int] = _

      override def processElement(value: PersonInfo, ctx: KeyedBroadcastProcessFunction[String, PersonInfo, PersonInfo, String]#ReadOnlyContext, out: Collector[String]): Unit = {
        val value = getRuntimeContext.getState(new ValueStateDescriptor[Int]("mystate", classOf[Int])).value()
        out.collect(value.toString)

      }

      override def processBroadcastElement(value: PersonInfo, ctx: KeyedBroadcastProcessFunction[String, PersonInfo, PersonInfo, String]#Context, out: Collector[String]): Unit = {

      }
    }).print()

//    StreamDataSource.env.enableCheckpointing(2,CheckpointingMode.AT_LEAST_ONCE)

    StreamDataSource.env.execute()
  }
}
