import MyData._
import spray.json.DefaultJsonProtocol

object JsonProtocol extends DefaultJsonProtocol {
  implicit lazy val userFormat = jsonFormat2(User)
  implicit lazy val errorResponse = jsonFormat1(ErrorResponse)
  implicit lazy val createUserRequestFormat = jsonFormat1(CreateUserRequest)
  implicit lazy val updateUserRequest = jsonFormat1(UpdateUserRequest)
}
