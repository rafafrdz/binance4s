organization := "com.github.rafafrdz"
organizationName := "rafafrdz"
organizationHomepage := Some(url("http://rafaelfernandez.dev/"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/rafafrdz/binance-api-scala"),
    "scm:git@github.com:rafafrdz/binance-api-scala.git"
  )
)
developers := List(
  Developer(
    id    = "io.github.rafafrdz ",
    name  = "rafael",
    email = "your@email",
    url   = url("https://github.com/rafafrdz")
  )
)

ThisBuild / description := "A functional Binance API built on Scala language."
ThisBuild / licenses := List("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))
ThisBuild / homepage := Some(url("https://github.com/rafafrdz/binance-api-scala"))

// Remove all additional repository other than Maven Central from POM
ThisBuild / pomIncludeRepository := { _ => false }
ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
ThisBuild / publishMavenStyle := true