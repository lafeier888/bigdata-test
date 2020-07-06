package stream
import org.apache.flink.api.scala._
object StreamTransform {
  def main(args: Array[String]): Unit = {
    val ds = StreamDataSource.readFromSocket
    val ds1 = ds.map(_.split(" "))
    val ds2 = ds.flatMap(_.split(" "))
    val ds3 = ds.filter(_.contains("prefix_x"))


    val ds4 = ds.keyBy("word")
    ds4.sum("n")
    ds4.min("n")
    ds4.max("n")
    ds4.minBy("n")
    ds4.maxBy("n")
  }
}
