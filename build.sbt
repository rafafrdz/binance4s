
name := "binance-api"
version := "0.2.0"
scalaVersion := "2.13.8"


lazy val root = project in file(".")

lazy val extraDependencies =
  Seq(
    "com.lihaoyi" %% "requests" % "0.6.9"
     , "com.outr" %% "hasher" % "1.2.2"
    , "com.typesafe" % "config" % "1.4.2"
    , "org.typelevel" %% "cats-effect" % "3.3.14"
    , "org.typelevel" %% "cats-core" % "2.7.0"

  )


libraryDependencies ++= extraDependencies

credentials += Credentials(Path.userHome / ".sbt" / "sonatype_credentials")