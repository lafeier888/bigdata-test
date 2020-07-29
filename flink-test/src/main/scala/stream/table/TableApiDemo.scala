package stream.table

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.scala.typeutils.Types
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.Kafka011TableSource
//import org.apache.flink.table.api.scala.StreamTableEnvironment
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment
import org.apache.flink.table.api.{EnvironmentSettings, TableSchema}
import pojo.PersonInfo
import stream.StreamDataSource
//import org.apache.flink.table.api.scala._
//import org.apache.flink.streaming.api.scala._
import org.apache.flink.api.scala._
import org.apache.flink.types.Row


object TableApiDemo {
  private val env: StreamExecutionEnvironment = StreamDataSource.env


  def main(args: Array[String]): Unit = {

    //    1 创建env
    //    使用flink或者blink的planner
    //    val settings = EnvironmentSettings.newInstance().useOldPlanner().inStreamingMode().build()
    val settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build()
    val tableEnv = StreamTableEnvironment.create(env, settings)

    val ds = StreamDataSource.readFromSocket

    //    2注册表
    //    tableEnv.registerDataStream("person", ds)

    //tables source
    val field_names: Array[String] = Array("value")
    val field_data_types: Array[TypeInformation[_]] = Array(Types.STRING)
    val properties: Properties = new Properties()
    properties.setProperty("bootstrap.servers", "vm01:9092,vm02:9092,vm03:9092")
    properties.setProperty("group.id", "consumer-group")
    properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    properties.setProperty("auto.offset.reset", "latest")
    val table_schema = new TableSchema(field_names, field_data_types)
    val kafka_table_source = new Kafka011TableSource(table_schema, "test", properties, new MyDeserializationSchema())
    tableEnv.registerTableSource("person", kafka_table_source)


    //    Table对象
    //    val table = tableEnv.fromDataStream(ds, 'id,'name)
    val table = tableEnv.scan("person")
    //    val table = tableEnv.sqlQuery("select * from person")

    table.printSchema()

    val stream = tableEnv.toAppendStream[Row](table)
    stream.print()
    env.execute()

  }
}
