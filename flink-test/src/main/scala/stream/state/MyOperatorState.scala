package stream.state

import org.apache.flink.runtime.state.{FunctionInitializationContext, FunctionSnapshotContext}
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction

class MyOperatorState extends CheckpointedFunction {
  override def snapshotState(functionSnapshotContext: FunctionSnapshotContext): Unit = {
    ???
  }

  override def initializeState(functionInitializationContext: FunctionInitializationContext): Unit = {

  }
}
