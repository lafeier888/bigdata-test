package stream.watermark

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import stream.StreamDataSource

object EventTimeAndWaterMark {

  private val env: StreamExecutionEnvironment = StreamDataSource.env

  def main(args: Array[String]): Unit = {
    val socket = StreamDataSource.readFromSocket

    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

//    val watermarkStrategy = WatermarkStrategy
//      .forBoundedOutOfOrderness(Duration.ofSeconds(2))
//      .withTimestampAssigner(new SerializableTimestampAssigner[PersonInfo] {
//        override def extractTimestamp(element: PersonInfo, recordTimestamp: Long): Long = {
//          element.createTime
//        }
//      })
//
//    socket.assignTimestampsAndWatermarks(watermarkStrategy)
//      .print()

    socket.assignTimestampsAndWatermarks(new MyWatermarkStrategy).print()




    env.execute()
  }
}
