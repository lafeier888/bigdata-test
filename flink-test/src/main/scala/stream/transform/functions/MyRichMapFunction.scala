package stream.transform.functions

import org.apache.flink.api.common.functions.RichMapFunction
import pojo.PersonInfo

class MyRichMapFunction extends RichMapFunction[PersonInfo,(String,Int)]{
  override def map(value: PersonInfo): (String, Int) = {
    val context = getRuntimeContext
    (value.name,value.age)
  }
}
