package streaming

import org.apache.spark.SparkContext
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SockerSource {
  def main(args: Array[String]): Unit = {
    val sc = SparkContext.getOrCreate()


    val ssc = new StreamingContext(sc, Seconds(1))
    val socketSourceRdd = ssc.socketStream[Int]("", 11, in => Iterator(1), StorageLevel.MEMORY_AND_DISK)
    val kvrdd = socketSourceRdd.map((_, 1))
    ssc.start()
  }
}
