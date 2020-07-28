package stream.window

import java.lang
import java.text.SimpleDateFormat
import java.util.Date
import java.util.function.Consumer

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala.{OutputTag, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.evictors.Evictor
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.streaming.runtime.operators.windowing.TimestampedValue
import org.apache.flink.util.Collector
import pojo.PersonInfo
import stream.StreamDataSource
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.windowing.triggers.EventTimeTrigger

object NoKeyedWindow {
  private val env: StreamExecutionEnvironment = StreamDataSource.env

  def main(args: Array[String]): Unit = {

    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)
    env.getConfig.setAutoWatermarkInterval(100L)
    env.setParallelism(1)

    val ds = StreamDataSource.readFromSocket
      .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[PersonInfo](Time.milliseconds(2000)) {
        override def extractTimestamp(element: PersonInfo): Long = {
          println("process-time",System.currentTimeMillis(),"event-time",element.createTime * 1000, getCurrentWatermark)
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
    var ds2 = ds
      .timeWindowAll(Time.seconds(10))
      .evictor(new Evictor[PersonInfo, TimeWindow] {
        override def evictBefore(elements: lang.Iterable[TimestampedValue[PersonInfo]], size: Int, window: TimeWindow, evictorContext: Evictor.EvictorContext): Unit = {
          print("==w-before", window, evictorContext)
          elements.forEach(new Consumer[TimestampedValue[PersonInfo]] {
            override def accept(t: TimestampedValue[PersonInfo]): Unit = {
              println("process-time",System.currentTimeMillis(), "event-time",parseTimestamp(t.getTimestamp), t.getValue)
            }
          })
        }

        override def evictAfter(elements: lang.Iterable[TimestampedValue[PersonInfo]], size: Int, window: TimeWindow, evictorContext: Evictor.EvictorContext): Unit = {
          print("w-end", window, evictorContext)
        }

        def print(t: String, window: TimeWindow, evictorContext: Evictor.EvictorContext): Unit = {
          println("triger-time",System.currentTimeMillis(),t, "win-start", parseTimestamp(window.getStart), "win-end", parseTimestamp(window.getEnd), "watermark", parseTimestamp(evictorContext.getCurrentWatermark))
        }

        def parseTimestamp(timestamp: Long): String = {
          //          val sdf = new SimpleDateFormat("HH:mm:ss.SSS")
          //          sdf.format(timestamp)
          timestamp.toString
        }
      })
      .sideOutputLateData(OutputTag("later"))
      .allowedLateness(Time.seconds(5))

    ds2.sum("money").print()

    //    ds.getSideOutput(OutputTag[String]("later")).print()

    env.execute()
  }
}
