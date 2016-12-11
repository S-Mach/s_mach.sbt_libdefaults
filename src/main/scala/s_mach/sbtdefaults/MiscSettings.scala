package s_mach.sbtdefaults

import sbt.Keys._
import sbt._

trait MiscSettings extends ProjectSettings {
  override def projectSettings : Seq[Setting[_]] = super.projectSettings ++ Seq(
    organization := "net.s_mach"
  )
}
