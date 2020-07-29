package stream.window

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows
import org.apache.flink.streaming.api.windowing.evictors.Evictor
import org.apache.flink.streaming.api.windowing.time.Time
import stream.window.evictor.MyEvictor

object WindowJoinAndCoGroup {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val socket_ds1 = env.socketTextStream("vm01", 7777)
    val socket_ds2 = env.socketTextStream("vm01", 7778)
    val ds1 = socket_ds1.map(row => {
      val fields = row.split(",")
      fields match {
        case Array(f1, f2, f3) => (f1, f2, f3)
      }
    })
    val ds2 = socket_ds2.map(row => {
      val fields = row.split(",")
      fields match {
        case Array(f1, f2, f3) => (f1, f2, f3)
      }
    })

    //    val win = ds1.coGroup(ds2).where(_._1).equalTo(_._1)
    //      .window(TumblingProcessingTimeWindows.of(Time.seconds(10)))
    //      .apply((x, y) => {
    //        x.length + y.length
    //      }).print()


//    val win = ds1.join(ds2).where(_._1).equalTo(_._1)
//      .window(TumblingProcessingTimeWindows.of(Time.seconds(10)))
//      .apply((x, y) => {
//        (x._1, x._2 + y._2, x._3 + y._3)
//      }).print()




      env.execute()
  }
}
