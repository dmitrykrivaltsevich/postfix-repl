// TODO: check for latest versions and upgrade plugins

// style plugins
addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.5.1")
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.7.0")

// docker plugin
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.3")

// code coverage
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.0")