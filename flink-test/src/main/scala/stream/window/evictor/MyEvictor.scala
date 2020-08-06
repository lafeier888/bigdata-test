package stream.window.evictor

import java.lang
import java.util.function.Consumer

import org.apache.flink.streaming.api.windowing.evictors.Evictor
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.streaming.runtime.operators.windowing.TimestampedValue
import pojo.PersonInfo

class MyEvictor extends Evictor[PersonInfo, TimeWindow] {

  override def evictBefore(elements: lang.Iterable[TimestampedValue[PersonInfo]], size: Int, window: TimeWindow, evictorContext: Evictor.EvictorContext): Unit = {
    println(s"窗口触发时间：${System.currentTimeMillis() / 1000}", s"窗口：[${window.getStart.toString.substring(0, 10)},${window.getEnd.toString.substring(0, 10)}]")
    //打印窗口所有元素,秒级别
    elements.forEach(new Consumer[TimestampedValue[PersonInfo]] {
      override def accept(t: TimestampedValue[PersonInfo]): Unit = {
        println(t.getTimestamp, t.getValue)
      }
    })
  }

  override def evictAfter(elements: lang.Iterable[TimestampedValue[PersonInfo]], size: Int, window: TimeWindow, evictorContext: Evictor.EvictorContext): Unit = {

  }
}
