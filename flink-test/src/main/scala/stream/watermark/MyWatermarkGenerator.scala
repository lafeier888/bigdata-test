package stream.watermark

import org.apache.flink.api.common.eventtime.{Watermark, WatermarkGenerator, WatermarkOutput}
import pojo.PersonInfo

class MyWatermarkGenerator extends WatermarkGenerator[PersonInfo] {

  val maxOutOfOrderness = 3500L // 3.5 seconds

  var currentMaxTimestamp: Long = _

  override def onEvent(t: PersonInfo, l: Long, watermarkOutput: WatermarkOutput): Unit = currentMaxTimestamp = currentMaxTimestamp.max(l)

  override def onPeriodicEmit(watermarkOutput: WatermarkOutput): Unit = {
    watermarkOutput.emitWatermark(new Watermark(currentMaxTimestamp - maxOutOfOrderness - 1))
  }

}
