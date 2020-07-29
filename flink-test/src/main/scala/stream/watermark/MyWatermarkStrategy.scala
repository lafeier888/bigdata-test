package stream.watermark

import org.apache.flink.api.common.eventtime._
import pojo.PersonInfo

class MyWatermarkStrategy extends WatermarkStrategy[PersonInfo] {
  override def createWatermarkGenerator(context: WatermarkGeneratorSupplier.Context): WatermarkGenerator[PersonInfo] =
    new MyWatermarkGenerator

  override def createTimestampAssigner(context: TimestampAssignerSupplier.Context): TimestampAssigner[PersonInfo] = new SerializableTimestampAssigner[PersonInfo] {
    override def extractTimestamp(element: PersonInfo, recordTimestamp: Long): Long = {
      element.createTime
    }
  }
}
