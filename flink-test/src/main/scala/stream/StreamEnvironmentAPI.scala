package stream

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

object StreamEnvironmentAPI {
  def createEnv = {
    //创建env
    StreamExecutionEnvironment.getExecutionEnvironment
  }

  def createLocalEnv = {
//    StreamExecutionEnvironment.createLocalEnvironment(1)
    StreamExecutionEnvironment.createLocalEnvironmentWithWebUI()
  }

  def createRemoteEnv = {
    StreamExecutionEnvironment.createRemoteEnvironment("192.168.1.101", 8081,"D:\\code\\bigdata-test\\flink-test\\target\\flink-test-1.0-SNAPSHOT.jar")
  }
}
