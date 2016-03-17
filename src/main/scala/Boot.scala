import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import Configuration._

object Boot extends App with Route {
  implicit lazy val system = ActorSystem("my-system")
  implicit lazy val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher
  val logger = Logging(system, getClass)
  val binding = Http().bindAndHandle(routes, interface, port)

  info()

  binding.onFailure {
    case err: Exception =>
      logger.error(err, s"Failed to bind to $interface $port")
  }

  private def info(): Unit = {
    println(
      """
        |   ___    __     __                __ __ ______ ______   ___
        |  / _ |  / /__  / /__ ___ _ ____  / // //_  __//_  __/  / _ \
        | / __ | /  '_/ /  '_// _ `//___/ / _  /  / /    / /    / ___/
        |/_/ |_|/_/\_\ /_/\_\ \_,_/      /_//_/  /_/    /_/    /_/
      """.stripMargin)
    logger.info(s"  - Configuration: Start server at $interface $port on ActorSystem(${system.name})")
  }
}
