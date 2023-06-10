ThisBuild / scalaVersion := "2.13.8"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "misis"
ThisBuild / organizationName := "misis"

val akkaVersion = "2.6.18"
val akkaHttpVersion = "10.2.7"
val circeVersion = "0.14.1"
val AkkaHttpJsonVersion = "1.39.2"
lazy val slickVersion = "3.3.3"
lazy val postgresVersion = "42.3.1"

lazy val root = (project in file("."))
    .settings(
        name := "kafka-demo",
        libraryDependencies ++= Seq(
            "com.typesafe.akka" %% "akka-actor" % akkaVersion,
            "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
            "com.typesafe.akka" %% "akka-stream" % akkaVersion,
            "com.typesafe.akka" %% "akka-stream-kafka" % "3.0.0",

            "io.circe" %% "circe-core" % circeVersion,
            "io.circe" %% "circe-generic" % circeVersion,
            "io.circe" %% "circe-parser" % circeVersion,

            "ch.qos.logback"     % "logback-classic"       % "1.2.3"
        )
    )


enablePlugins(JavaAppPackaging)