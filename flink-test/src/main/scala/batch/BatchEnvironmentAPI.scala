package batch

import org.apache.flink.api.scala.ExecutionEnvironment

object BatchEnvironmentAPI {
  def createEnv = {
    //创建env
    ExecutionEnvironment.getExecutionEnvironment
  }

  def createLocalEnv = {
    ExecutionEnvironment.createLocalEnvironment(1)
  }

  def createRemoteEnv = {
    ExecutionEnvironment.createRemoteEnvironment("host", 6123)
  }
}
