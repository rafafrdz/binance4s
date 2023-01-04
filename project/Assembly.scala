import sbt.Keys._
import sbtassembly.AssemblyKeys._

object Assembly {
  lazy val settings = Seq(
    assemblyJarName := s"${name.value.toLowerCase}_${version.value.toLowerCase}.jar"
  )
}
