package stream.window.eventtime

import java.lang
import java.util.function.Consumer

import org.apache.flink.api.common.functions.{ReduceFunction, RichReduceFunction}
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.api.java.tuple
import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala.{OutputTag, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.evictors.{CountEvictor, Evictor}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.streaming.runtime.operators.windowing.TimestampedValue
import pojo.PersonInfo
import stream.StreamDataSource
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala.function.{ProcessWindowFunction, RichWindowFunction, WindowFunction}
import org.apache.flink.streaming.api.windowing.triggers.{Trigger, TriggerResult}
import org.apache.flink.util.Collector
import stream.window.evictor.MyEvictor

object TumbingWindow {
  private val env: StreamExecutionEnvironment = StreamDataSource.env

  def main(args: Array[String]): Unit = {

    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.getConfig.setAutoWatermarkInterval(100L)
    env.setParallelism(1)

    val ds2 = StreamDataSource.readFromSocket
    //    val ds2 = ds.map(row => {
    //      val fields = row.split(",")
    //      val id = fields(0).toInt
    //      val name = fields(1)
    //      val city = fields(2)
    //      val age = fields(3).toInt
    //      val sex = fields(4)
    //      val tel = fields(5)
    //      val addr = fields(6)
    //      val email = fields(7)
    //      val money = fields(8).toInt
    //      val createTime = fields(9).toLong
    //      PersonInfo(id, name, city, age, sex, tel, addr, email, money, createTime)
    //    })

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
    //        }
    val ds4 = ds3.keyBy("city")

    val ds5 = ds4
      .timeWindow(Time.seconds(10))
      .evictor(CountEvictor.of[TimeWindow](2))
      .allowedLateness(Time.seconds(5))
      .sideOutputLateData(new OutputTag[PersonInfo]("later"))
      .trigger(new Trigger[PersonInfo, TimeWindow] {
        var count = 0

        override def onElement(element: PersonInfo, timestamp: Long, window: TimeWindow, ctx: Trigger.TriggerContext): TriggerResult = {
          ctx.registerEventTimeTimer(window.maxTimestamp())
          count = count + 1
          //          println(count)
          //          if (window.maxTimestamp <= ctx.getCurrentWatermark) {
          //            // 水印大于window-end的(延迟的)，直接触发
          //            return TriggerResult.FIRE
          //          } else {
          //            //水印小于win-end的（还没到触发时机）
          //            ctx.registerEventTimeTimer(window.maxTimestamp())
          //            TriggerResult.CONTINUE
          //          }
          TriggerResult.CONTINUE
        }

        override def onProcessingTime(time: Long, window: TimeWindow, ctx: Trigger.TriggerContext): TriggerResult = TriggerResult.CONTINUE

        override def onEventTime(time: Long, window: TimeWindow, ctx: Trigger.TriggerContext): TriggerResult = {
          println("----")
          TriggerResult.FIRE
        }

        override def clear(window: TimeWindow, ctx: Trigger.TriggerContext): Unit = {
          ctx.deleteEventTimeTimer(window.maxTimestamp())
        }
      })
      .reduce(
        new ReduceFunction[PersonInfo] {
          override def reduce(value1: PersonInfo, value2: PersonInfo): PersonInfo = {
            val p = new PersonInfo()
            p.city = value1.city
            p.money = value1.money + value2.money
            p
          }
        }
        // process 增量  不知道有啥用
        , new ProcessWindowFunction[PersonInfo, (String, PersonInfo), org.apache.flink.api.java.tuple.Tuple, TimeWindow] {

          override def process(key: Tuple, context: Context, elements: Iterable[PersonInfo], out: Collector[(String, PersonInfo)]): Unit = {

            //如果是keyed stream,由于第一个参数（reduce,,arrgrate,fold）的效果，相同key只会有一个值输出，因此这里的集合只会有一个值
            out.collect((key.getField(0), elements.iterator.next()))
          }

        }
      ).print()


    // ds5.getSideOutput(new OutputTag[PersonInfo]("later")).map(data => (data, "laterdata")).print()


    env.execute()
  }
}
