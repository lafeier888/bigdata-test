import pojo.PersonInfo

object CaseClassTest {
  def main(args: Array[String]): Unit = {
    val clazz = classOf[PersonInfo]
    clazz.getDeclaredConstructor()
//    clazz.getConstructors

  }
}
