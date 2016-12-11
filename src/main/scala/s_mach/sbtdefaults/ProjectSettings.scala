package s_mach.sbtdefaults

import sbt._

trait ProjectSettings {
  def projectSettings : Seq[Setting[_]] = Seq.empty
}
