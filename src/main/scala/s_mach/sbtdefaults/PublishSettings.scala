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

import sbt._
import sbt.Keys._

trait PublishSettings extends ProjectSettings {
  override def projectSettings : Seq[Setting[_]] = super.projectSettings ++ Seq(
    publishMavenStyle := true,
    pomIncludeRepository := { _ => false },
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    pomExtra := (
      <url>https://github.com/S-Mach/s_mach.{name.value}</url>
        <licenses>
          <license>
            <name>MIT</name>
            <url>http://opensource.org/licenses/MIT</url>
            <distribution>repo</distribution>
          </license>
        </licenses>
        <scm>
          <url>git@github.com:S-Mach/s_mach.{name.value}.git</url>
          <connection>scm:git:git@github.com:S-Mach/s_mach.{name.value}.git</connection>
          <developerConnection>scm:git:git@github.com:S-Mach/s_mach.{name.value}.git</developerConnection>
        </scm>
        <developers>
          <developer>
            <id>lancegatlin</id>
            <name>Lance Gatlin</name>
            <email>lance.gatlin@gmail.com</email>
            <organization>S-Mach</organization>
            <organizationUrl>http://s-mach.net</organizationUrl>
          </developer>
        </developers>
    )
  )
}
