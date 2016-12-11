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

import scala.util.matching.Regex
import scala.util.matching.Regex.Match

// from https://stackoverflow.com/questions/23399495/how-to-link-java-api-classes-using-apimappings
// (second answer!)
trait ApiMappingSettings extends ProjectSettings {
  val externalJavadocMap : Map[String,String] = Map(
//    "owlapi" -> "http://owlcs.github.io/owlapi/apidocs_4_0_2/index.html"
  )

  /*
   * The rt.jar file is located in the path stored in the sun.boot.class.path system property.
   * See the Oracle documentation at http://docs.oracle.com/javase/6/docs/technotes/tools/findingclasses.html.
   */
  val rtJar: String = System.getProperty("sun.boot.class.path").split(java.io.File.pathSeparator).collectFirst {
    case str: String if str.endsWith(java.io.File.separator + "rt.jar") => str
  }.getOrElse(throw new RuntimeException("Failed to find rt.jar")) // fail hard if not found

  val javaApiUrl: String = "http://docs.oracle.com/javase/8/docs/api/index.html"

  val allExternalJavadocLinks: Seq[String] = javaApiUrl +: externalJavadocMap.values.toSeq

  def javadocLinkRegex(javadocURL: String): Regex = ("""\"(\Q""" + javadocURL + """\E)#([^"]*)\"""").r

  def hasJavadocLink(f: File): Boolean = allExternalJavadocLinks exists {
    javadocURL: String =>
      (javadocLinkRegex(javadocURL) findFirstIn IO.read(f)).nonEmpty
  }

  val fixJavaLinks: Match => String = m =>
    m.group(1) + "?" + m.group(2).replace(".", "/") + ".html"

  /* You can print the classpath with `show compile:fullClasspath` in the SBT REPL.
  * From that list you can find the name of the jar for the managed dependency.
  */
  override def projectSettings : Seq[Setting[_]] = super.projectSettings ++ Seq(
    apiMappings ++= {
      // Lookup the path to jar from the classpath
      val classpath = (fullClasspath in Compile).value
      def findJar(nameBeginsWith: String): File = {
        classpath.find { attributed: Attributed[File] =>
          (attributed.data ** s"$nameBeginsWith*.jar").get.nonEmpty
        }.getOrElse(throw new RuntimeException(s"Failed to find jar '$nameBeginsWith'")).data // fail hard if not found
      }
      // Define external documentation paths
      (externalJavadocMap map {
        case (name, javadocURL) => findJar(name) -> url(javadocURL)
      }) + (file(rtJar) -> url(javaApiUrl))
    },
    // Override the task to fix the links to JavaDoc
    doc in Compile <<= (doc in Compile) map {
      target: File =>
        (target ** "*.html").get.filter(hasJavadocLink).foreach { f =>
          //println(s"Fixing $f.")
          val newContent: String = allExternalJavadocLinks.foldLeft(IO.read(f)) {
            case (oldContent: String, javadocURL: String) =>
              javadocLinkRegex(javadocURL).replaceAllIn(oldContent, fixJavaLinks)
          }
          IO.write(f, newContent)
        }
        target
    }
  )

}
