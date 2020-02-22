name := "PostDownloader"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "com.softwaremill.sttp.client" %% "spray-json" % "2.0.0-RC11",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "com.typesafe" % "config" % "1.4.0",
  "org.scalactic" %% "scalactic" % "3.1.1",
  "org.scalatest" %% "scalatest" % "3.1.1" % Test,
  "org.scalamock" %% "scalamock" % "4.4.0" % Test

)
