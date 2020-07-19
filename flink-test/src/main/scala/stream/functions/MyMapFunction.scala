package stream.functions

import org.apache.flink.api.common.functions.MapFunction
import pojo.PersonInfo

class MyMapFunction extends MapFunction[PersonInfo,(String,Int)] {
  override def map(value: PersonInfo): (String, Int) = {
    (value.name,value.age)
  }

}
