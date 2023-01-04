/**
 * ----------------------------------
 * Root Project
 * ----------------------------------
 * */
lazy val root = (project in file("."))
  .settings(name := "binance-api")
  .settings(ProjectSettings.root: _*)
  .aggregate(core, request, examples)

/**
 * ----------------------------------
 * Modules
 * ----------------------------------
 * */
lazy val core = (project in file("binance-api-core"))
  .settings(name := "core")
  .withId("core")
  .settings(ProjectSettings.core: _*)

//lazy val coreOld = (project in file("binance-api-core-old"))
//  .settings(name := "core-old")
//  .withId("core-old")
//  .settings(ProjectSettings.core: _*)

lazy val request = (project in file("binance-api-request"))
  .withId("request")
  .settings(name := "request")
  .settings(ProjectSettings.request: _*)
  .dependsOn(core % Dependencies.classDependencyCompileTest)

lazy val examples = (project in file("binance-api-examples"))
  .withId("examples")
  .settings(name := "examples")
  .settings(ProjectSettings.examples: _*)
  .dependsOn(
    core % Dependencies.classDependencyCompileTest,
    request % Dependencies.classDependencyCompileTest
  )

//lazy val module2 = (project in file("module-2"))
//  .withId("module-2")
//  .settings(
//    name := "spark-sbt-module-2"
//  )
//  .settings(ProjectSettings.module2: _*)
//  .dependsOn(core % Dependencies.classDependencyCompileTest)
