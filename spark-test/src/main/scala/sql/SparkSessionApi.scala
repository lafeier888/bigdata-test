package sql

import org.apache.spark.sql.SparkSession

object SparkSessionApi {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[2]")
      .enableHiveSupport()
      .getOrCreate()
    spark.sql(
      """
        |insert into
        |    table test.score
        |values
        |    (1, "语文", 90),
        |    (1, "数学", 80),
        |    (2, "语文", 85),
        |    (2, "数学", 95),
        |    (3, "语文", 90),
        |    (3, "数学", 75)
        |""".stripMargin).cache()
  }
}
