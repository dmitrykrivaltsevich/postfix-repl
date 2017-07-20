import sbt.ConflictManager

lazy val root = (project in file("."))
  .settings(
    name          := "postfix-repl",
    organization  := "com.dmitrykrivaltsevich",
    scalaVersion  := "2.12.2",
    scalacOptions := Seq(
      "-unchecked", "-deprecation", "-target:jvm-1.8", "-encoding", "utf8", "-Ywarn-dead-code", "-Ywarn-unused-import",
      "-Ywarn-unused", "-Xfatal-warnings"
    ),
    conflictManager := ConflictManager.strict,
    libraryDependencies ++= testDependencies
  )

lazy val testDependencies = Seq(
  "org.specs2" %% "specs2-core" % "3.9.4" % "test"
)