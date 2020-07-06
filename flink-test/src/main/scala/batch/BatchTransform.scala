package batch
import org.apache.flink.api.scala._
object BatchTransform {

  def main(args: Array[String]): Unit = {
    val ds = BatchDataSource.readTextfile
    val ds1 = ds.map(_.split(" "))
    val ds2 = ds.flatMap(_.split(" "))
    val ds3 = ds.filter(_.contains("prefix_x"))

  }
}
