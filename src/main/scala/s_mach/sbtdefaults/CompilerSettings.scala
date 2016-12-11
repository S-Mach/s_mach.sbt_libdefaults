package s_mach.sbtdefaults

import sbt.Keys._
import sbt._

trait CompilerSettings extends ProjectSettings {
  override def projectSettings : Seq[Setting[_]] = super.projectSettings ++ Seq(
    scalaVersion := "2.11.8",
    scalacOptions ++= Seq(
      // target default jvm for each scalaVersion
      "-encoding", "UTF-8",
      "-feature",
      "-unchecked",
      "-deprecation",
      "-Yno-adapted-args",
      "-Ywarn-numeric-widen",
      "-Ywarn-value-discard",
      "-Ywarn-dead-code",
      "-Xfuture",
      "-Ywarn-unused",
      "-Ywarn-unused-import",
      "-Xlint",
      // some projects may need to turn this off
      "-Xfatal-warnings"
    ),
    crossScalaVersions := Seq("2.11.8","2.12.1")
  )
}
