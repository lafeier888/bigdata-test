package pojo

case class PersonInfo(var id: Int,
                      var name: String,
                      var city: String,
                      var age: Int,
                      var sex: String,
                      var tel: String,
                      var addr: String,
                      var email: String,
                      var money: Int) {
  def this() = {
    this(0, "", "", 0, "", "", "", "", 0)
  }
}

