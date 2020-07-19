package streaming


import java.util.{Calendar, Date}

import kafka.serializer.StringDecoder
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Milliseconds, Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object KafkaDirectSource {

  def main(args: Array[String]): Unit = {

    val session = SparkSession
      .builder()
      .master("local[1]")
      .appName("local test")
      .getOrCreate()
    val sc = session.sparkContext
    val ssc = new StreamingContext(sc, Seconds(120))


    // kafka配置
    val kafkaParameters = Map[String, String](
      "bootstrap.servers" -> "vm01:9092,vm02:9092,vm03:9092"
    )

    //kafka topic
    val topicSet = Set("test")
    val name = "lafeier"
    val bc_name = sc.broadcast(name)

    //获取源数据
    val directStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParameters, topicSet)

    val ds1 = directStream.transform(rdd => {
      rdd.map(line => {
        val strings = line._2.split(",")
        (strings(0), strings(1))
      })
    })
    ds1.foreachRDD(rdd => {
      import session.implicits._
      rdd.toDF("name", "age").show()
    })


    ssc.start()
    ssc.awaitTermination()
  }
}
