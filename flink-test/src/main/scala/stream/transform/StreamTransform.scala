package stream.transform

import java.util.function.Consumer

import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import stream.StreamDataSource
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.datastream.DataStreamUtils
import pojo.PersonInfo

object StreamTransform {
  def main(args: Array[String]): Unit = {

    val ds = StreamDataSource.readFromSocket


    //    自定义source
    //    val ds = StreamDataSource.readFromMySource()
    //    ds.print()


    //    基本算子 map flatMap fliter
    //    val ds1 = ds.map(_.split(","))
    //    val ds2 = ds.flatMap(_.split(","))
    //    val ds3 = ds.filter(_.contains("prefix_x"))


    // keyed stream ，相当于group by

    val ds4 = ds.keyBy(_.city)
    //    ds4.print()


    //    聚合操作 sum/min/max
    //    ds4.sum("money").print()
    //
    //    ds4.min("money").print()
    //    ds4.minBy("money").print()
    //
    //    ds4.max("money").print()
    //    ds4.maxBy("money")

    //     split/select
    //    val ds5 = ds.split(item =>
    //      if (item.city.equals("北京")
    //        || item.city.equals("上海")
    //        || item.city.equals("广州")
    //        || item.city.equals("深圳")
    //      )
    //        Set("一线城市")
    //      else Seq("二线城市")
    //    )
    //
    //    val city1 = ds5.select("一线城市")
    //    city1.print()
    //
    //    val city2 = ds5.select("二线城市")
    //    city2.print()
    //
    //    val allCity = ds5.select("一线城市", "二线城市")
    //    allCity.print()

    //    process function

    //    val mianStreamDS = ds.process(new ProcessFunction[PersonInfo, String] {
    //      override def processElement(value: PersonInfo, ctx: ProcessFunction[PersonInfo, String]#Context, out: Collector[String]): Unit = {
    //        if (value.city == "北京")
    //          out.collect(value.name)
    //        else
    //          ctx.output(new OutputTag[String]("非北京"), value.name)
    //      }
    //    })
    //    mianStreamDS.print
    //
    //    //    side stream
    //    val sideStreamDS = mianStreamDS.getSideOutput(new OutputTag[String]("非北京"))
    //    sideStreamDS.print


    //    合流， connect操作后 变成了ConnectedStreams
    //    val connectDs = city1.connect(city2)

    //    coMap coFlatMap 操作 ，注意这俩并不是算子，而是map,flatMap的参数变成了多个函数,有几条流就几个函数
    //    connectDs.map(
    //      x => (x.name, x.age),
    //      y => (y.name, y.age)
    //    ).print()


    //    reduce 操作

    ds4.reduce((x, y) => {
      //      PersonInfo(x.id, x.name, x.city, x.age, x.sex, x.tel, x.addr, x.email, x.money + y.money)

      val p = new PersonInfo()
      p.city = x.city
      p.money = x.money + y.money
      p
    }).print()

    //    union 操作
    //    city1.union(city2).print()

    //    mapFunction richMapFunction
    //    ds.map(new MyMapFunction).print()
    //    ds.map(new MyRichMapFunction).print()


    //    iterate
//        val value = ds.iterate(ds => {
//          (ds.map(p => PersonInfo(0, "null", "null", p.age + 1, "null", "null", "null", "null", 0, 0L)), ds.filter(_.age > 20))
//        })
    //
    //    value.map(p => (p.id, p.age)).print()

    //    DataStreamUtils
    //    DataStreamUtils.collect(ds.javaStream).forEachRemaining(new Consumer[PersonInfo] {
    //      override def accept(t: PersonInfo): Unit = {
    //        println(t)
    //      }
    //    })



    StreamDataSource.env.execute()

  }
}
