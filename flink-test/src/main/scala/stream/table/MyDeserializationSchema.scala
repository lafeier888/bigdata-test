package stream.table

import org.apache.commons.lang3.reflect.TypeUtils
import org.apache.flink.api.common.serialization.DeserializationSchema
import org.apache.flink.api.common.typeinfo.{TypeInformation, Types}
import org.apache.flink.types.Row

import org.apache.flink.api.java.typeutils.RowTypeInfo

class MyDeserializationSchema extends DeserializationSchema[Row] {
  override def deserialize(message: Array[Byte]): Row = {
    val row = new Row(1)
    row.setField(0,new String(message))
    row
  }

  override def isEndOfStream(nextElement: Row): Boolean = false

  private val fieldtypes: Array[TypeInformation[_]] = Array(Types.STRING)

  private val fieldnames: Array[String] = Array("value")

  override def getProducedType: TypeInformation[Row] = new RowTypeInfo(fieldtypes,fieldnames)
}
