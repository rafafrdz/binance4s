
name := "binance-api-scala"
version := "0.2.0"
scalaVersion := "2.13.8"
organization := "com.github.rafafrdz"

// configs for sbt-github-packages plugin
githubOwner := "rafafrdz"
githubRepository := "binance-api-scala"
githubTokenSource := TokenSource.GitConfig("github.token").some.getOrElse(TokenSource.Environment("GITHUB_TOKEN"))



lazy val root = project in file(".")

lazy val extraDependencies =
  Seq(
    "com.lihaoyi" %% "requests" % "0.6.9"
     , "com.outr" %% "hasher" % "1.2.2"
    , "com.typesafe" % "config" % "1.4.2"
  )


libraryDependencies ++= extraDependencies

credentials +=
  Credentials(
    "GitHub Package Registry",
    "maven.pkg.github.com",
    "rafafrdz",
    sys.env.getOrElse("GITHUB_TOKEN", "N/A")
  )

credentials += Credentials(Path.userHome / ".sbt" / "sonatype_credentials")