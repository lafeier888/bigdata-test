package stream

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.streaming.api.scala.OutputTag
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011
import org.apache.flink.util.Collector

import scala.collection.immutable.Set


object StreamTransform {
  def main(args: Array[String]): Unit = {

    val ds = StreamDataSource.readFromTextFile

    //    基本算子 map flatMap fliter
    //    val ds1 = ds.map(_.split(","))
    //    val ds2 = ds.flatMap(_.split(","))
    //    val ds3 = ds.filter(_.contains("prefix_x"))


    // keyed stream才有的算子 sum/min/max
    //    val tupleDs = ds.map(_.split(",")).map(arr => (arr(0), arr(1), arr(2)))
    //    val ds4 = tupleDs.keyBy(0)

    //    ds4.sum(1).print()
    //    ds4.min(2).print()
    //    ds4.minBy(2).print()
    //
    //    ds4.max(1).print()
    //    ds4.maxBy("n")

    //    分流（就是打个标签，然后在挑出来）
    val ds5 = ds.split(item => if (item.contains("spark")) Set("spark", "spark2") else Seq("non-spark"))

    val sparkDs = ds5.select("spark")
    sparkDs.print()

    val nonsparkDs = ds5.select("non-spark")
    nonsparkDs.print()

    val allDs = ds5.select("spark", "nonspark")
    allDs.print()

    //    合流， connect操作后 变成了ConnectedStreams
    //    val connectDs = sparkDs.connect(nonsparkDs)

    //    coMap coFlatMap 操作 ，注意这俩并不是算子，而是map,flatMap的参数变成了多个函数,有几条流就几个函数
    //    connectDs.map(x => (x._1, "stream1"), y => (y._1, "stream2")).print()

    //    side stream
    //    val mianStreamDS = ds.process(new ProcessFunction[String, String] {
    //      override def processElement(value: String, ctx: ProcessFunction[String, String]#Context, out: Collector[String]): Unit = {
    //        if (value.contains("spark"))
    //          out.collect(value)
    //        else
    //          ctx.output(new OutputTag[String]("nonspark"), value)
    //      }
    //    })
    //    mianStreamDS.print

    //    val sideStreamDS = mianStreamDS.getSideOutput(new OutputTag[String]("nonspark"))
    //    sideStreamDS.print


    //    reduce 操作
    //    ds4.reduce((x, y) => (x._1, x._2 + y._2, x._3 + y._3)).print()

    //    union 操作
    //    sparkDs.union(nonsparkDs).print()


    StreamDataSource.env.execute()
  }
}
