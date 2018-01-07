import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import scala.util.{Success, Failure}

object Boot extends App with Route {
  implicit lazy val system = ActorSystem("my-system")
  implicit lazy val materializer = ActorMaterializer()
  override implicit val db = H2DB
  implicit val ec = system.dispatcher
  val interface = Configuration.interface
  val port = Configuration.port
  val logger = Logging(system, getClass)
  val binding = Http().bindAndHandle(routes, interface, port)

  info()

  binding.onComplete {
    case Success(_) => logger.info(s"Succeeded to bind to $interface $port")
    case Failure(e) =>
      logger.error(e.getMessage, s"Failed to bind to $interface $port")
  }

  private def info(): Unit = {
    logger.info(s"  - Configuration: Start server at $interface $port on ActorSystem(${system.name})")
  }
}
