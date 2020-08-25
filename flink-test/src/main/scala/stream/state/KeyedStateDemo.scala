package stream.state

import java.util.Optional
import java.util.function.BiConsumer

import org.apache.flink.api.common.functions.{RichFlatMapFunction, RichMapFunction}
import org.apache.flink.api.common.state.StateTtlConfig.TtlTimeCharacteristic
import org.apache.flink.api.common.state.{StateTtlConfig, ValueState, ValueStateDescriptor}
import org.apache.flink.api.common.time.Time
import org.apache.flink.api.java.functions.KeySelector
import org.apache.flink.api.scala._
import org.apache.flink.configuration.{ConfigOption, Configuration}
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.environment.CheckpointConfig.ExternalizedCheckpointCleanup
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.functions.co.KeyedBroadcastProcessFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.types.IntValue
import org.apache.flink.util.Collector
import pojo.PersonInfo
import stream.StreamDataSource
import stream.transform.functions.MyRichMapFunction

object KeyedStateDemo {

  private val env: StreamExecutionEnvironment = StreamDataSource.env


  def main(args: Array[String]): Unit = {
    val ds = StreamDataSource.readFromSocket

    val keyedDs = ds.keyBy(new KeySelector[PersonInfo, String] {
      override def getKey(value: PersonInfo): String = {
        value.city
      }
    })

    //    mapwithstate算子

    //    keyedDs.mapWithState[(String,Int),Int]((p,state)=>{
    //      var count = state match {
    //        case Some(s)=> s+1
    //        case None=>1
    //      }
    //      ((p.city,count),Option(count))
    //    }).print()


    keyedDs.map(new RichMapFunction[PersonInfo, (String, Int)] {

      var total: ValueState[Int] = _

      override def open(parameters: Configuration): Unit = {
        val valueStateDescriptor = new ValueStateDescriptor[Int]("total", classOf[Int])
        valueStateDescriptor.setQueryable("mystate")

        //        ttl配置
        //        val ttlConfig = StateTtlConfig
        //          .newBuilder(Time.seconds(2))
        //          .setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite)
        //          .setStateVisibility(StateTtlConfig.StateVisibility.ReturnExpiredIfNotCleanedUp)
        //          .setTtlTimeCharacteristic(TtlTimeCharacteristic.ProcessingTime)
        //          .build
        //
        //        valueStateDescriptor.enableTimeToLive(ttlConfig)


        total = getRuntimeContext.getState(valueStateDescriptor)

      }

      override def map(p: PersonInfo): (String, Int) = {

        //        total.update(total.value() + 1)
        (p.city, total.value())
      }
    }).print()


    //    env.enableCheckpointing(1000)
    //    env.getCheckpointConfig.enableExternalizedCheckpoints(ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION)

    env.execute()
  }


}
