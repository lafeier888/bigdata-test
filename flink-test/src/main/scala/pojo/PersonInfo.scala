package pojo

/**
 *
 * productName  网课名
 * lectorName   教师
 * Price        价格
 * learnerCount 学习人数
 */
class PersonInfo {
  var id: Int = _
  var username: String = _
  var sex: String = _
  var email: String = _
  var registertime: String = _
  var street: String = _
  var city: String = _
  var country: String = _
  var money: Float = _
  var age: Int = _

  override def toString: String = {
    return s"$id,$username,$sex,$email,$registertime,$street,$street,$city,$country,$money,$age"
  }
}
