import MyData._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.ContentTypes
import akka.http.scaladsl.model.{HttpEntity, HttpResponse}
import akka.http.scaladsl.server.Directives._

trait Route extends SprayJsonSupport {
  import JsonProtocol._

  implicit val db: DB

  val routes =
    pathSingleSlash {
      get {
        index()
      }
    } ~
      path("ping") {
        get {
          complete("pong")
        }
      } ~
      pathPrefix("users") {
        pathEndOrSingleSlash {
          get {
            getUsers()
          } ~
            post {
              entity(as[CreateUserRequest]) { request =>
                createUser(request.name)
              }
            }
        } ~
          path(IntNumber) { id =>
            get {
              getUser(id)
            }
          }
      }

  private def index() = complete(
    HttpResponse(
      entity = HttpEntity(
        ContentTypes.`text/html(UTF-8)`,
        <html>
          <body>
            <h1>Welcome to <i>akka-http</i>!</h1>
          </body>
        </html>.toString
      )
    )
  )

  private def createUser(name: String)(implicit db: DB) =
    db.createUser(name) match {
      case Left(err) => failWith(err)
      case Right(user) => complete(user)
    }

  private def getUsers()(implicit db: DB) =
    db.getUsers match {
      case Left(err) => failWith(err)
      case Right(users) => complete(users)
    }

  private def getUser(id: Int)(implicit db: DB) =
    db.getUser(id) match {
      case Left(err) => failWith(err)
      case Right(None) => complete("user_not_found")
      case Right(Some(user)) => complete(user)
    }
}
