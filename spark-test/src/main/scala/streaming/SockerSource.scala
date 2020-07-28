package streaming

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}

object SockerSource {
  def main(args: Array[String]): Unit = {
    val session = SparkSession.builder().master("local[2]").getOrCreate()
    val sc = session.sparkContext


    val ssc = new StreamingContext(sc, Seconds(5))
    val socketDS = ssc.socketTextStream("vm01", 7777)
    val ds = socketDS.map(row=>{
      val fields = row.split(",")
      val id = fields(0).toInt
      val name = fields(1)
      val city = fields(2)
      val age = fields(3).toInt
      val sex = fields(4)
      val tel = fields(5)
      val addr = fields(6)
      val email = fields(7)
      val money = fields(8).toInt
      val createTime = fields(9).toLong
      PersonInfo(id, name, city, age, sex, tel, addr, email, money, createTime)
    })
    ds.window(Duration(15000),Duration(10000)).foreachRDD((rdd,time)=>{
      println("--------start------------")
      println(rdd)
      println(time)
      println("---------end-----------")
    })


    ssc.start()
    ssc.awaitTermination()
  }
}
