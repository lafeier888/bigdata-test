import java.text.SimpleDateFormat
import java.util.Date

object DateDemo {
  def main(args: Array[String]): Unit = {
    val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
    val str = sdf.format("1595516923000".toLong)
    println(str)
  }
}
