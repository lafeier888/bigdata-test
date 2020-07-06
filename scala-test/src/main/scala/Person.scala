import scala.reflect.ClassTag

class A[T]{
  def Asay(msg:T) ={
    print(msg)
  }
}
class BB extends A[Int]

object Person {
  def main(args: Array[String]): Unit = {

    new BB().Asay(1)


  }

}
