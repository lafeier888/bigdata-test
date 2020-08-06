package stream

import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

object StreamEnvironmentAPI {

  var config: Configuration = new Configuration()

  config.setBoolean("queryable-state.enable", true)


  def createEnv = {
    //创建env
    StreamExecutionEnvironment.getExecutionEnvironment
  }


  def createLocalEnv = {
    //    StreamExecutionEnvironment.createLocalEnvironment(1)

    StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(config)
  }

  def createRemoteEnv = {
    StreamExecutionEnvironment.createRemoteEnvironment("192.168.1.101", 8081, "D:\\code\\bigdata-test\\flink-test\\target\\flink-test-1.0-SNAPSHOT.jar")
  }
}
