package s_mach.sbtdefaults

import sbt._
import sbt.Keys._

trait TestSettings extends ProjectSettings {
  override def projectSettings : Seq[Setting[_]] = super.projectSettings ++ Seq(
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.1" % "test"
    )
  )
}
