import sbt._

name := "scala_template_typeflow"

version := "0.0.1"

isSnapshot := true

organization := "com.github.notyy"

// set the Scala version used for the project
scalaVersion := "2.13.0"

resolvers ++= Seq(
  "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases",
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
)

libraryDependencies ++= Seq(
  "org.pegdown" % "pegdown" % "1.6.0" % "test", //used in html report
  "org.scalactic" %% "scalactic" % "3.0.8",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test",
  "org.slf4j" % "slf4j-api" % "1.7.7",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "org.seleniumhq.selenium" % "selenium-java" % "2.35.0" % "test",
  "org.json4s" %% "json4s-native" % "3.6.7",
  "org.typelevel" %% "cats-core" % "2.0.0",
  "com.aliyun" % "aliyun-java-sdk-fc" % "1.4.0",
  "com.aliyun.fc.runtime" % "fc-java-core" % "1.3.0",
  "com.aliyun.oss" % "aliyun-sdk-oss" % "3.6.0",
  "io.vavr" % "vavr" % "0.9.3"
)

logBuffered := false

// reduce the maximum number of errors shown by the Scala compiler
maxErrors := 20

// increase the time between polling for file changes when using continuous execution
pollInterval := scala.concurrent.duration.FiniteDuration(1000L, "ms")

// append several options to the list of options passed to the Java compiler
javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

// append -deprecation to the options passed to the Scala compiler
scalacOptions += "-deprecation"

// disable updating dynamic revisions (including -SNAPSHOT versions)
offline := true

// set the prompt (for this build) to include the project id.
shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }

// set the prompt (for the current project) to include the username
shellPrompt := { state => System.getProperty("user.name") + "> " }

// disable printing timing information, but still print [success]
showTiming := false

// disable printing a message indicating the success or failure of running a task
showSuccess := false

// change the format used for printing task completion time
timingFormat := {
    import java.text.DateFormat
    DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
}

// only use a single thread for building
parallelExecution := false


// Use Scala from a directory on the filesystem instead of retrieving from a repository
//scalaHome := Some(file("/home/user/scala/trunk/"))

// don't aggregate clean (See FullConfiguration for aggregation details)
aggregate in clean := false

// only show warnings and errors on the screen for compilations.
//  this applies to both test:compile and compile and is Info by default
logLevel in compile := Level.Info

// only show warnings and errors on the screen for all tasks (the default is Info)
//  individual tasks can then be more verbose using the previous setting
logLevel := Level.Info

// only store messages at info and above (the default is Debug)
//   this is the logging level for replaying logging with 'last'
persistLogLevel := Level.Info

// only show 10 lines of stack traces
traceLevel := 10

exportJars := true

// only show stack traces up to the first sbt stack frame
traceLevel := 0

// add SWT to the unmanaged classpath
// unmanagedJars in Compile += file("/usr/share/java/swt.jar")

// seq(oneJarSettings: _*)

// libraryDependencies += "commons-lang" % "commons-lang" % "2.6"

// jacoco.settings

// Execute tests in the current project serially
//   Tests from other projects may still run concurrently.
parallelExecution in Test := false

// create beautiful scala test report
testOptions in Test ++= Seq(
  Tests.Argument(TestFrameworks.ScalaTest,"-h","target/html-unit-test-report"),
  Tests.Argument(TestFrameworks.ScalaTest,"-u","target/unit-test-reports"),
  Tests.Argument(TestFrameworks.ScalaTest,"-o"),
  Tests.Argument(TestFrameworks.ScalaTest,"-l","FunctionTest")
)

// testOptions in jacoco.Config ++= Seq(
//   Tests.Argument(TestFrameworks.ScalaTest,"-h","target/html-unit-test-report"),
//   Tests.Argument(TestFrameworks.ScalaTest,"-u","target/unit-test-reports"),
//   Tests.Argument(TestFrameworks.ScalaTest,"-o"),
//   Tests.Argument(TestFrameworks.ScalaTest,"-l","FunctionTest")
// )

testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")

// packAutoSettings

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature","-language:higherKinds","-language:implicitConversions")

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}