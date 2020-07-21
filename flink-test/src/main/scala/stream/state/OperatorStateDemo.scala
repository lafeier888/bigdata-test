package stream.state

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import stream.StreamDataSource
import org.apache.flink.api.scala._

object OperatorStateDemo {

  private val env: StreamExecutionEnvironment = StreamDataSource.env


  def main(args: Array[String]): Unit = {
    val ds = StreamDataSource.readFromSocket
    val kvDs = ds.keyBy("city")
    //    xxxxWithState这种方法只有在keyedStream才有
    val mapDS = kvDs.mapWithState((p, state: Option[Int]) => {
      var total = state match {
        case None => p.money
        case Some(x) => x + p.money
      }

      (
        (p.id, p.name, p.money, total),
        Option(total)
      )
    })
    mapDS.print()
    env.execute()
  }

}
