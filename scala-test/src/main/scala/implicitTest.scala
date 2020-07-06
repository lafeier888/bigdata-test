

object implicitTest {
  def main(args: Array[String]): Unit = {

    def test(n:String) = n
    implicit def add(a:Int) =a+""
//    implicit def add2(a:Int)=a+1+""

    print(test(1))
//
//    def printVal(x:A)=print(1)
//
//    implicit def transformStringToInt(s:B):A=new A
//    implicit def transformStringToInt2(s:AnyRef):A=new A
//
//
//    printVal(new A)
//    printVal(new B)
//
//     val set:Iterable[String] = Set()


  }
}
