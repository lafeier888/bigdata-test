package stream.source.mysource

import org.apache.flink.streaming.api.functions.source.{ParallelSourceFunction, SourceFunction}

import scala.util.Random

class MyParallelSource extends ParallelSourceFunction[String]{
  var flag: Boolean = true

  override def run(ctx: SourceFunction.SourceContext[String]): Unit = {

    val random = new Random()
    while (flag) {
      (1 to 10).map(_.toString + "@" + random.nextInt()).foreach(ctx.collect(_))
    }
  }

  override def cancel(): Unit = false
}
