import sbt.Keys._
import sbt.{Def, _}
import scoverage.ScoverageKeys._

object Testing {
  private lazy val testSettings = Seq(
    Test / fork := true,
    Test / parallelExecution := false
  )
  private lazy val itSettings = inConfig(IntegrationTest)(Defaults.testSettings) ++ Seq(
    IntegrationTest / fork := true,
    IntegrationTest / parallelExecution := false,
    IntegrationTest / scalaSource := baseDirectory.value / "src/it/scala"
  )
  private lazy val coverageSettings = Seq(
    coverageEnabled := true,
    coverageFailOnMinimum := true,
    coverageMinimumBranchTotal := 90
  )

  lazy val settings: Seq[Def.Setting[_]] = testSettings ++ itSettings ++ coverageSettings
}
