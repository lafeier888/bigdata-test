
import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.util.Random

object producerStreaming {

  def main(args: Array[String]): Unit = {
    val brokeys = "vm01:9092,vm02:9092,vm03:9092"
    val topics = "test"

    val porp = new Properties()
    porp.put("metadata.broker.list",brokeys)
    porp.put("bootstrap.servers",brokeys)
    porp.put("serializer.class", "kafka.serializer.StringEncoder")
    porp.put("request.required.acks", "1")
    porp.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    porp.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    porp.put("producer.type", "async")

    val producer1 = new KafkaProducer[String,String](porp)


    while (true){
      var teststr = "testdata" + Random.nextDouble()
      producer1.send(new ProducerRecord[String,String](topics,teststr.toString()))
    }
  }
}
