lazy val root = (project in file(".")).enablePlugins(JavaAppPackaging)

name := "akka-http-standalone"

version := "1.0"

scalaVersion := "2.12.1"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-Xlint")

assemblyOutputPath in assembly := file("./akka-http-standalone.jar")

val akkaV = "2.4.16"
val akkaHttpV = "10.0.1"

packageName in Docker := "akka-http-standalone"
dockerExposedPorts := Seq(5000)

libraryDependencies ++= {
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-stream" % akkaV,
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-xml" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV
  )
}

Revolver.settings


