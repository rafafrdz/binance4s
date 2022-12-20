import sbt.Keys._
import sbt._

object ProjectSettings {

  /** Modules settings */
  lazy val root = commonSettings
  lazy val examples = commonSettings ++ Dependencies.examples
  lazy val request = commonSettings ++ Dependencies.request
  lazy val core = commonSettings ++ Dependencies.common

  private lazy val general = Seq(
    version := version.value,
    scalaVersion := Versions.scala,
    organization := "io.github.rafafrdz",
    organizationName := "rafafrdz",
    organizationHomepage := Some(url("https://github.com/rafafrdz")),
    developers := List(Developer(
      id = "io.github.rafafrdz ",
      name = "rafael",
      email = "your@email",
      url = url("https://github.com/rafafrdz")
    )),
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfuture", "-Xlint"),
    javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled"),
    cancelable in Global := true //allow to use Ctrl + C in sbt prompt
  )
  private lazy val commonSettings = general ++ Testing.settings ++ Publish.settings ++ Assembly.settings
}
