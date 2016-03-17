import com.typesafe.config.ConfigFactory

object Configuration {
  private lazy val conf = ConfigFactory.load()
  val interface = conf.getString("http.interface")
  val port = conf.getInt("http.port")
}
