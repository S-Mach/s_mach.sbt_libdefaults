name := "sbt_libdefaults"

scalaVersion := "2.10.6"

// Remove options not supported in 2.10
scalacOptions := scalacOptions.value.filter {
  case "-Ywarn-unused" | "-Ywarn-unused-import" => false
  case _ => true
}

// todo: this doesn't work and sbt-unidoc isn't present
// todo: at https://repo1.maven.org/maven2/com/eed3si9n/
//libraryDependencies ++= Seq(
//  "com.eed3si9n" % "sbt-unidoc_2.10_0.13" % "0.3.3"
//)

sbtPlugin := true

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.0")

addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.1.0")

addSbtPlugin("com.codacy" % "sbt-codacy-coverage" % "1.3.7")

addSbtPlugin("com.typesafe.sbt" % "sbt-pgp" % "0.8.3")

// Fix api mappings for common scala libs
addSbtPlugin("com.thoughtworks.sbt-api-mappings" %% "sbt-api-mappings" % "1.0.0")