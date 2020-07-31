package stream.queryablestate

import java.util.function.Consumer

import org.apache.flink.api.common.{ExecutionConfig, JobID}
import org.apache.flink.api.common.state.{StateDescriptor, ValueState, ValueStateDescriptor}
import org.apache.flink.api.common.typeinfo.{BasicTypeInfo, TypeInformation}
import org.apache.flink.queryablestate.client.QueryableStateClient
import org.apache.flink.types.IntValue

object MyQueryableState {




  def main(args: Array[String]): Unit = {
    val client = new QueryableStateClient("192.168.1.101", 9069)

    val jobId = JobID.fromHexString("f735bccb941af6f0a70ba0536b5ab292")
    val queryStateName = "mystate"
    val stateDesc= new ValueStateDescriptor[Int]("total",TypeInformation.of(classOf[Int]))
    val state = client.getKvState(jobId, queryStateName, "广州", BasicTypeInfo.STRING_TYPE_INFO, stateDesc)
    state.thenAccept(new Consumer[ValueState[Int]] {
      override def accept(t: ValueState[Int]): Unit = {
        println(t.value())
      }
    }).get

    client.shutdownAndWait()


  }
}
