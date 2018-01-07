lazy val root = (project in file(".")).enablePlugins(JavaAppPackaging)

name := "akka-http-standalone"

version := "1.0"

scalaVersion := "2.12.4"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-Xlint")

assemblyOutputPath in assembly := file("./akka-http-standalone.jar")

val akkaV = "2.5.8"
val akkaHttpV = "10.1.0-RC1"

packageName in Docker := "akka-http-standalone"

libraryDependencies ++= {
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-stream" % akkaV,
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-xml" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV,
    "org.scalikejdbc"   %% "scalikejdbc"       % "3.1.0",
    "com.h2database"    %  "h2"                % "1.4.196",
    "ch.qos.logback"    %  "logback-classic"   % "1.2.3"
  )
}

Revolver.settings


