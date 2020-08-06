object FoldAndReduce {
  def main(args: Array[String]): Unit = {
    val list = List("a", "a", "c", "d")

    val result = list.foldLeft(1)((res, x) => {
      if (x.length > 0)
        res + 1
      else
        res
    })
    println(result)


    val result2 = list.reduceLeft((x, y) => {
      if (x==y)
        s"2$x"
      else
        x+y
    })
    println(result2)

  }

}
