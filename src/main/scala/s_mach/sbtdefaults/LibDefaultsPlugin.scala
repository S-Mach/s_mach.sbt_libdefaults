package s_mach.sbtdefaults

import sbt._

object LibDefaultsPlugin extends
  AutoPlugin with
  CompilerSettings with
  MiscSettings with
  PublishSettings with
  TestSettings with
  ApiMappingSettings
{
  object autoImport

  override def trigger: PluginTrigger = allRequirements

  override def projectSettings : Seq[Setting[_]] = super.projectSettings
}
