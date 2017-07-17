lazy val root = (project in file("."))
  .settings(
    name         := "postfix-repl",
    organization := "com.dmitrykrivaltsevich",
    scalaVersion := "2.12.2"
  )