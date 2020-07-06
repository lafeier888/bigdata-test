object ArrayTest {
  def main(args: Array[String]): Unit = {
    val str = new java.lang.String
    val strings = str.split("")
    Array
    def fn(s:TraversableOnce[String])={
      println(s.getClass.getName)
    }

    fn(strings)
    println(str.getClass.getName)
    println(strings.getClass.getName)
  }
}
