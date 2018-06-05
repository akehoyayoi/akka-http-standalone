sbt docker:publishLocal
docker run -it -p 80:8080 --rm akka-http-standalone:1.0

