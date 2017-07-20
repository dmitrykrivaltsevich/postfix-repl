import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import scalariform.formatter.preferences._

// copied from https://raw.githubusercontent.com/paypal/scala-style-guide/develop/scalastyle-config.xml
scalastyleConfig := file("scalastyle-config.xml")

scalastyleFailOnError := true

// Create a default Scala style task to run with tests
lazy val compileScalastyle = taskKey[Unit]("compileScalastyle")

compileScalastyle := org.scalastyle.sbt.ScalastylePlugin.scalastyle.in(Compile).toTask("").value

(compileInputs in(Compile, compile)) <<= (compileInputs in(Compile, compile)) dependsOn compileScalastyle

scalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(PlaceScaladocAsterisksBeneathSecondAsterisk, false)
  .setPreference(IndentLocalDefs, true)
  .setPreference(IndentPackageBlocks, true)
  .setPreference(IndentWithTabs, false)
  .setPreference(IndentSpaces, 2)
  .setPreference(MultilineScaladocCommentsStartOnFirstLine, false)
  .setPreference(SpacesAroundMultiImports, false)