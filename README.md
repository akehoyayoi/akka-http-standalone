# Akka-Http server example

How to build fat jar by sbt-assembly

```
git clone git@github.com:t-hiroyoshi/akka-http-standalone.git
cd akka-http-standalone
sbt assembly
```

and you get akka-http-standalone.jar.

How to run your akka-http server and open on the browser.

```
java -jar akka-http-standalone.jar
open http://localhost:8080
```
