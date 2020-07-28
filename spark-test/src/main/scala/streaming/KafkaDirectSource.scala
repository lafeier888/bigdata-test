package streaming


import java.util.{Calendar, Date}

import kafka.serializer.StringDecoder
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Duration, Milliseconds, Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object KafkaDirectSource {

  def main(args: Array[String]): Unit = {

    val session = SparkSession
      .builder()
      .master("local[2]")
      .appName("local test")
      .getOrCreate()
    val sc = session.sparkContext
    val ssc = new StreamingContext(sc, Seconds(5))


    // kafka配置
    val kafkaParameters = Map[String, String](
      "bootstrap.servers" -> "vm01:9092,vm02:9092,vm03:9092"
    )

    //kafka topic
    val topicSet = Set("test")

    //获取源数据
    val directStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParameters, topicSet)

    val ds = directStream.map(row => {
      val fields = row._2.split(",")
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
    ds
        .window(Seconds(20),Seconds(15))
//      .window(Duration(15000), Duration(10000))
      .foreachRDD((rdd, time) => {
        println("--------start------------")
        rdd.collect().map(println(_))
        println(time)
        println("---------end-----------")
      })


    ssc.start()
    ssc.awaitTermination()
  }
}
