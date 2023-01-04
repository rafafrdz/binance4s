import sbt.Keys._
import sbt._
import sbt.librarymanagement.Developer


object Publish {

  lazy val ghUsername: String = "rafafrdz"
  lazy val ghRepo: String = "binance-api-scala"

  lazy val settings = Seq(
    description := "A functional Binance API built on Scala language",
    licenses := List("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt")),
    homepage := Some(url(s"https://github.com/$ghUsername/$ghRepo")),
    developers := List(
      Developer(
        id = ghUsername,
        name = "Rafael",
        email = "",
        url = url("https://github.com/rafafrdz")
      )
    ),
    scmInfo := Some(
      ScmInfo(
        url(s"https://github.com/$ghUsername/$ghRepo"),
        s"scm:git@github.com:$ghUsername/$ghRepo.git"
      )
    ),
    pomIncludeRepository := { _ => false },

    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
      else Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    publishMavenStyle := true
  )
}
