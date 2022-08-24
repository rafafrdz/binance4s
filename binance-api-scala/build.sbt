ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(name := "binance-api-scala")

lazy val extraDependencies =
  Seq(
    "com.lihaoyi" %% "requests" % "0.6.9"
     , "com.outr" %% "hasher" % "1.2.2"
    , "com.typesafe" % "config" % "1.4.2"
  )


libraryDependencies ++= extraDependencies