package stream.source.mysource

import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}

class MyRichParallelSource extends RichParallelSourceFunction[String]{
  override def run(ctx: SourceFunction.SourceContext[String]): Unit = {

  }

  override def cancel(): Unit = ???
}
