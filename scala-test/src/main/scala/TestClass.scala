/**
 *
 * @author liguobin@growingio.com
 * @version 1.0,2020/7/1
 */
class TestClass[+X, -CC[X]] {

  def test[U >: X](args: U): U = {
    println("hello")
    args
  }

  def test2[U >: X, Y <: CC[U]](args: U, cc: Y): Unit = {
    println(args)
    println(cc)
  }


  def test3[U >: X, Y <: CC[U]](args: U, cc: Y): Y = {
    println(args)
    println(cc)
    cc
  }


}

class TestClass2[-X, +CC[X]] {

  def test[U <: X](args: U): U = {
    println("hello")
    args
  }

  def test2[U <: X, Y >: CC[U]](args: U, cc: Y): Unit = {
    println(args)
    println(cc)
  }


  def test3[U <: X, Y >: CC[U]](args: U, cc: Y): Y = {
    println(args)
    println(cc)
    cc
  }


}

object TestClass extends App {
  val s = new TestClass[Int, List]
  s.test2("hello", List("hello", "hello"))
  s.test("hello")
  s.test3("hello", List("hello", "hello"))
}
