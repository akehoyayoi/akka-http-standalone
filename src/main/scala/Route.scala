import akka.http.scaladsl.model.{HttpEntity, HttpResponse}
import akka.http.scaladsl.model.ContentTypes.`text/html(UTF-8)`
import akka.http.scaladsl.server.Directives._

trait Route {
  val routes =
    get {
      pathSingleSlash {
        complete(index)
      } ~
      path("unknown") {
        complete(notFound)
      } ~
      path("ping") {
        complete("pong")
      }
    }

  private lazy val index = HttpResponse(
    entity = HttpEntity(`text/html(UTF-8)`,
      <html>
        <body>
          <h1>Welcome to <i>akka-http</i>!</h1>
          <p>Defined resources:</p>
          <ul>
            <li><a href="/ping">/ping</a></li>
            <li><a href="/unknown">/unknown</a></li>
          </ul>
        </body>
      </html>.toString
    )
  )

  private lazy val notFound = HttpResponse(
    status = 404,
    entity = "Not Found"
  )
}
