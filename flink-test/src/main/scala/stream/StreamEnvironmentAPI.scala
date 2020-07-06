package stream

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

object StreamEnvironmentAPI {
  def createEnv = {
    //创建env
    StreamExecutionEnvironment.getExecutionEnvironment
  }

  def createLocalEnv = {
    StreamExecutionEnvironment.createLocalEnvironment(1)
  }

  def createRemoteEnv = {
    StreamExecutionEnvironment.createRemoteEnvironment("host", 6123)
  }
}
