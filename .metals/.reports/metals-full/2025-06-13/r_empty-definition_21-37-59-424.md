error id: file:///C:/Users/eser_/Downloads/weather_agregator/weather_agregator/build.sbt:
file:///C:/Users/eser_/Downloads/weather_agregator/weather_agregator/build.sbt
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 637
uri: file:///C:/Users/eser_/Downloads/weather_agregator/weather_agregator/build.sbt
text:
```scala
name := """weather_agregator"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "3.3.6"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
libraryDependencies += "org.postgresql" % "postgresql" % "42.7.3"
libraryDependencies +=  "com.typesafe.play" %% "play-jdbc" % "2.9.4"
libraryDependencies += "com.typesafe.play" %% "play-ws" % "2.9.4"
libraryDependencies += "com.typesafe.play" %% "play-ahc-ws" % "2.9.4"
libraryDependencies += "com.typesafe.play" %% "play-akka-http-server@@" % "2.9.4"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.6.20"
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.15.2"
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.15.2"
libraryDependencies += "org.flywaydb" % "flyway-core" % "9.22.3"
libraryDependencies += "org.typelevel" %% "cats-core" % "2.10.0"
libraryDependencies += "org.typelevel" %% "cats-effect" % "3.5.4"


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"

```


#### Short summary: 

empty definition using pc, found symbol in pc: 