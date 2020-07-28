package stream.window.processtime

import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import pojo.PersonInfo
import stream.StreamDataSource
import stream.window.evictor.MyEvictor

object SlidingWindow {
  private val env: StreamExecutionEnvironment = StreamDataSource.env

  def main(args: Array[String]): Unit = {

    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime) //时间语义，默认就是处理时间

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

    var ds3 = ds2
      .timeWindowAll(Time.seconds(25), Time.seconds(15))
      .evictor(new MyEvictor())

    ds3.sum("money").print()

    env.execute()
  }
}
