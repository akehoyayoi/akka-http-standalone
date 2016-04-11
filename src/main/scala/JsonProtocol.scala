import MyData._
import spray.json.DefaultJsonProtocol

object JsonProtocol extends DefaultJsonProtocol {
  implicit lazy val userFormat = jsonFormat2(User)
  implicit lazy val createUserRequestFormat = jsonFormat1(CreateUserRequest)
}