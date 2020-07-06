import scala.language.higherKinds
class MyCollection[X,Y]{
  def test1(x:X,y:Y)={}
}
class MyCollection2[X] extends MyCollection[X,Int]

class MyCollectionImpl{

  def fn[Y,CC[X]<:MyCollection[X,Y]](p:CC[Int]): Unit ={}


}


object HigerType {
  def main(args: Array[String]): Unit = {

    val impl = new MyCollectionImpl()

    impl.fn[Int,MyCollection2](new MyCollection2[Int])

 



  }
}
