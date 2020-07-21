import pojo.PersonInfo


class PersonTest(name:String){
//   def this()=this("")
}

object CaseClassTest {
  def main(args: Array[String]): Unit = {
    val clazz = classOf[PersonTest]
    clazz.getDeclaredConstructors.map(x=>{println(x.getParameterCount)})
    clazz.getDeclaredConstructor()
//    clazz.getConstructors

  }
}
