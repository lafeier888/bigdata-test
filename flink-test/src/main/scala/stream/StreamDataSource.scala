package stream

import java.lang.reflect.Field
import java.util
import java.util.Properties

import org.apache.flink.api.common.serialization.{SimpleStringSchema, TypeInformationSerializationSchema}
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.common.typeutils.{TypeSerializer, TypeSerializerSnapshot}
import org.apache.flink.api.java.io.{PojoCsvInputFormat, RowCsvInputFormat}
import org.apache.flink.api.java.typeutils.{PojoField, PojoTypeInfo, TypeExtractor}
import org.apache.flink.api.scala._
import org.apache.flink.api.scala.typeutils.Types
import org.apache.flink.core.fs.Path
import org.apache.flink.core.memory.{DataInputView, DataOutputView}
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011


case class Word(word: String, n: Int)

class WordPojo {
  var word: String = _
  var n: Int = _
}

class MyTypeSerializer extends TypeSerializer[Word] {
  override def isImmutableType: Boolean = false

  override def hashCode(): Int = hashCode()

  override def equals(obj: Any): Boolean = equals()

  override def duplicate(): TypeSerializer[Word] = null

  override def createInstance(): Word = null

  override def copy(from: Word): Word = null

  override def copy(from: Word, reuse: Word): Word = null

  override def getLength: Int = 6

  override def serialize(record: Word, target: DataOutputView): Unit = null

  override def deserialize(source: DataInputView): Word = {
    val bytearr = new Array[Byte](7)

    source.read(bytearr)
    val line = new String(bytearr)
    val strings = line.split(",")

    Word(strings(0), Integer.parseInt(strings(1)))
  }

  override def deserialize(reuse: Word, source: DataInputView): Word = deserialize(source)

  override def copy(source: DataInputView, target: DataOutputView): Unit = null

  override def canEqual(obj: Any): Boolean = false

  override def snapshotConfiguration(): TypeSerializerSnapshot[Word] = null
}


object StreamDataSource {

  //创建env
  val env = StreamExecutionEnvironment.getExecutionEnvironment

  def main(args: Array[String]): Unit = {
    readFromKafka
    readFromCsvFile
    readFromTextFile
    readFromSocket

    env.execute() //流程序必须有这个

  }

  def readFromSocket = {
    val ds = env.socketTextStream("vm01", 7777)
    ds.print()
    ds
  }

  def readFromTextFile = {
    val path = this.getClass.getResource("/words.txt").getPath
    val ds = env.readTextFile(path)
    ds.print()
    ds
  }

  def readFromCsvFile = {

    //这个csv首行会报错,但是batch里有csv的那个就不会，因为那个有忽略首行

    val path = this.getClass.getResource("/words.csv").getPath
    //    pojo不能用样例类


    //方法1
    //    var fields: util.List[PojoField] = new util.ArrayList[PojoField]()
    //    val word = classOf[WordPojo].getDeclaredField("word")
    //    val pojoFieldWord = new PojoField(word, Types.STRING)
    //
    //    val n = classOf[WordPojo].getDeclaredField("n")
    //    val pojoFieldN = new PojoField(n, Types.INT)
    //
    //
    //    fields.add(pojoFieldWord)
    //    fields.add(pojoFieldN)
    //    val ds = env.createInput(new PojoCsvInputFormat[WordPojo](new Path(path), new PojoTypeInfo[WordPojo](classOf[WordPojo], fields)))


    //方法 2
    val pojoTypeInfo = TypeExtractor.createTypeInfo(classOf[WordPojo]).asInstanceOf[PojoTypeInfo[WordPojo]]
    val inputformat = new PojoCsvInputFormat[WordPojo](new Path(path), pojoTypeInfo)
    val ds = env.createInput(inputformat)
    ds.print()

    ds
  }

  def readFromKafka = {
    //    kafka配置
    var properties: Properties = new Properties()
    properties.setProperty("bootstrap.servers", "vm01:9092,vm02:9092,vm03:9092")
    properties.setProperty("group.id", "consumer-group")
    properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    properties.setProperty("auto.offset.reset", "latest")


    //  1用了自定义的序列化---kafka中的内容当做序列化的东西
    def useMySerialize(properties: Properties) = {
      val schema = new TypeInformationSerializationSchema[Word](createTypeInformation[Word], new MyTypeSerializer())
      //对接kafka
      val flinkKafkaConsumer011 = new FlinkKafkaConsumer011[Word](
        "test", //消费的主题
        schema, //数据格式
        properties)

      val ds = env.addSource(flinkKafkaConsumer011)
      ds.print()
      ds
    }

    //  2用string序列化----kafka的内容当成字符串
    def useStringSerialize(properties: Properties) = {
      //对接kafka
      val flinkKafkaConsumer011 =
        new FlinkKafkaConsumer011[String](
          "test", //消费的主题
          new SimpleStringSchema(), //数据格式
          properties)
      val ds = env.addSource(flinkKafkaConsumer011)
      ds.print()
      ds
    }

    //    useStringSerialize(properties)
    useStringSerialize(properties)
  }


}
