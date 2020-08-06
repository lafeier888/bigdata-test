package stream.state.queryablestate

import java.util.function.Consumer

import org.apache.flink.api.common.{ExecutionConfig, JobID}
import org.apache.flink.api.common.state.{StateDescriptor, ValueState, ValueStateDescriptor}
import org.apache.flink.api.common.typeinfo.{BasicTypeInfo, TypeHint, TypeInformation}
import org.apache.flink.queryablestate.client.QueryableStateClient
import org.apache.flink.types.IntValue

object MyQueryableState {




  def main(args: Array[String]): Unit = {
    val client = new QueryableStateClient("localhost", 9069)

    val jobId = JobID.fromHexString("f248882b5bdff6bd7a38738454e4dc4d")
    val queryStateName = "mystate"
    val stateDesc= new ValueStateDescriptor[Int]("total",TypeInformation.of(classOf[Int]))
    val state = client.getKvState(jobId, queryStateName, "广州", BasicTypeInfo.STRING_TYPE_INFO, stateDesc)
    val value = state.get
    println(value.value())


  }
}
