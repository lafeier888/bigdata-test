
import java.util.{Date, Properties}

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.util.Random

object producerStreaming {

  def main(args: Array[String]): Unit = {
    val brokeys = "vm01:9092,vm02:9092,vm03:9092"
    val topics = "test"

    val porp = new Properties()
    porp.put("metadata.broker.list", brokeys)
    porp.put("bootstrap.servers", brokeys)
    porp.put("serializer.class", "kafka.serializer.StringEncoder")
    porp.put("request.required.acks", "1")
    porp.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    porp.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    porp.put("producer.type", "async")

    val producer1 = new KafkaProducer[String, String](porp)

    val random = new Random()
    while (true) {

      val row = 0 + "," +
        "name" + "," + //name
        "city" + "," + //city
        Random.nextInt(80) + "," + //age
        "男" + "," +
        "tel" + "," +
        "addr" + "," +
        "email" + "," +
        "100" + "," +
        //(System.currentTimeMillis()/1000-5) //createTime 延迟5秒
        (System.currentTimeMillis()/1000) //createTime
      println(row)
      producer1.send(new ProducerRecord[String, String](topics, row))
      Thread.sleep(1000) //一秒一条
    }
  }
}
