package stream.state

import org.apache.flink.api.common.functions.RichMapFunction
import pojo.PersonInfo

class MyRichMapFunction extends RichMapFunction[PersonInfo,PersonInfo]{
  override def map(value: PersonInfo): PersonInfo = {
     value
  }
}
