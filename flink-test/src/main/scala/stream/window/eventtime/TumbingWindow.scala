package stream.window.eventtime

import java.lang
import java.util.function.Consumer

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala.{OutputTag, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.evictors.Evictor
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.streaming.runtime.operators.windowing.TimestampedValue
import pojo.PersonInfo
import stream.StreamDataSource
import org.apache.flink.api.scala._
import stream.window.evictor.MyEvictor

object TumbingWindow {
  private val env: StreamExecutionEnvironment = StreamDataSource.env

  def main(args: Array[String]): Unit = {

    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.getConfig.setAutoWatermarkInterval(100L)
    env.setParallelism(1)

    val ds = StreamDataSource.readFromKafka
    val ds2 = ds.map(row => {
      val fields = row.split(",")
      val id = fields(0).toInt
      val name = fields(1)
      val city = fields(2)
      val age = fields(3).toInt
      val sex = fields(4)
      val tel = fields(5)
      val addr = fields(6)
      val email = fields(7)
      val money = fields(8).toInt
      val createTime = fields(9).toLong
      PersonInfo(id, name, city, age, sex, tel, addr, email, money, createTime)
    })


    val ds3 = ds2.assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[PersonInfo](Time.seconds(2)) {
      override def extractTimestamp(element: PersonInfo): Long = {
        //        println(element.createTime * 1000)
        element.createTime * 1000
      }
    })

    //      .assignAscendingTimestamps(_.createTime*1000)
    //        .assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks[PersonInfo] {
    //          var maxEventTime:Long=0
    //          var delay:Long = 2000
    //          override def getCurrentWatermark: Watermark = {
    ////            println("watermark",maxEventTime-delay)
    //            new Watermark(maxEventTime-delay)
    //          }
    //
    //          override def extractTimestamp(element: PersonInfo, previousElementTimestamp: Long): Long = {
    //            maxEventTime = maxEventTime.max(element.createTime*1000)
    ////            println("maxEventTime",maxEventTime)
    //            element.createTime*1000
    //          }
    //        })
    var ds4 = ds3
      .timeWindowAll(Time.seconds(10))
      .evictor(new MyEvictor)
      .allowedLateness(Time.seconds(5))
      .sideOutputLateData(new OutputTag[PersonInfo]("later"))
      .sum("money")

    ds4.getSideOutput(new OutputTag[PersonInfo]("later")).map(data => (data.createTime, "laterdata")).print()

    ds4.print()

    env.execute()
  }
}
