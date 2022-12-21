import sbt.Keys._
import sbt.{Def, _}

object Dependencies {

  lazy val classDependencyCompile = "compile->compile"
  lazy val classDependencyTest = "test->test"
  lazy val classDependencyCompileTest = "test->test;compile->compile"

  /** Modules dependencies */
  lazy val common = depends(Cats.core, Cats.effects, typeSafeConfig, hasher)
  lazy val request = depends(Http4s.client, Http4s.server, Http4s.dsl)
  lazy val examples = common // empty

  /** Common dependencies */
  lazy val typeSafeConfig: ModuleID = "com.typesafe" % "config" % Versions.typeSafeConfig
  lazy val hasher: ModuleID = "com.outr" %% "hasher" % Versions.hasher
//  lazy val lihaoyiRequests: ModuleID = "com.lihaoyi" %% "requests" % Versions.lihaoyiRequests

  /** Testing dependencies */
  object Testing {
    lazy val scalaTest = "org.scalatest" %% "scalatest" % Versions.scalaTest % Test
    lazy val scalaTestFlatspec = "org.scalatest" %% "scalatest-flatspec" % Versions.scalaTest % Test
  }

  /** Cats dependencies */
  object Cats {
    lazy val core: ModuleID = "org.typelevel" %% "cats-core" % Versions.cats
    lazy val effects: ModuleID = "org.typelevel" %% "cats-effect" % Versions.catsEffects
  }

  object Http4s {
    lazy val client: ModuleID = "org.http4s" %% "http4s-ember-client" % Versions.http4s
    lazy val server: ModuleID = "org.http4s" %% "http4s-ember-server" % Versions.http4s
    lazy val dsl: ModuleID = "org.http4s" %% "http4s-dsl" % Versions.http4s
//    lazy val clientCurl: ModuleID = "org.http4s" %% "http4s-curl" % Versions.http4s
  }

  private def depends(modules: ModuleID*): Seq[Def.Setting[Seq[ModuleID]]] = Seq(libraryDependencies ++= modules)
}