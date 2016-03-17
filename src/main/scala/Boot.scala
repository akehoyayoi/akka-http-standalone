import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import Configuration._

object Boot extends App with Route {
  implicit lazy val system = ActorSystem("my-system")
  implicit lazy val materializer = ActorMaterializer()

  info()

  implicit val ec = system.dispatcher
  val logger = Logging(system, getClass)

  Http().bindAndHandle(routes, interface, port)

  private def info(): Unit = {
    println(
      """
        |   ___    __     __                __ __ ______ ______   ___
        |  / _ |  / /__  / /__ ___ _ ____  / // //_  __//_  __/  / _ \
        | / __ | /  '_/ /  '_// _ `//___/ / _  /  / /    / /    / ___/
        |/_/ |_|/_/\_\ /_/\_\ \_,_/      /_//_/  /_/    /_/    /_/
      """.stripMargin)
    println(s"  - Configuration: Start server at $interface $port on ActorSystem(${system.name})")
  }
}
