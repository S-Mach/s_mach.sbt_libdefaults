/*
                    ,i::,
               :;;;;;;;
              ;:,,::;.
            1ft1;::;1tL
              t1;::;1,
               :;::;               _____       __  ___              __
          fCLff ;:: tfLLC         / ___/      /  |/  /____ _ _____ / /_
         CLft11 :,, i1tffLi       \__ \ ____ / /|_/ // __ `// ___// __ \
         1t1i   .;;   .1tf       ___/ //___// /  / // /_/ // /__ / / / /
       CLt1i    :,:    .1tfL.   /____/     /_/  /_/ \__,_/ \___//_/ /_/
       Lft1,:;:       , 1tfL:
       ;it1i ,,,:::;;;::1tti      s_mach.sbt_libdefaults
         .t1i .,::;;; ;1tt        Copyright (c) 2016 S-Mach, Inc.
         Lft11ii;::;ii1tfL:       Author: lance.gatlin@gmail.com
          .L1 1tt1ttt,,Li
            ...1LLLL...
*/
package s_mach.sbtdefaults

import sbt.Keys._
import sbt._

trait CompilerSettings extends ProjectSettings {
  val consoleDisabledScalacOpts = Set(
    "-Ywarn-value-discard",
    "-Ywarn-dead-code",
    "-Ywarn-unused",
    "-Ywarn-unused-import",
    "-Xlint",
    "-Xfatal-warnings"
  )

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
    ) :+ { scalaVersion.value.split('.').take(2).mkString(".") match {
      case "2.10" => "-target:jvm-1.6"
      case "2.11" => "-target:jvm-1.6"
      case "2.12" => "-target:jvm-1.8"
    }},
    // Relax certain settings for tests
    scalacOptions in Test ~= (_ filterNot Set(
      "-Ywarn-value-discard"
    )),
    // Turn off a bunch of options when in console
    scalacOptions in (Compile,console) ~= (_ filterNot consoleDisabledScalacOpts ),
    scalacOptions in (Test,console) ~= (_ filterNot consoleDisabledScalacOpts ),
    crossScalaVersions := Seq("2.11.8","2.12.1")
  )
}
