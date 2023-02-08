lazy val testSuite = (project in file("."))
  .disablePlugins(JUnitXmlReportPlugin)
  .settings(
      name := "customs-view-and-amend-acceptance-tests",
      version := "0.0.1",
      scalaVersion := "2.13.8",
      scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation"),
      libraryDependencies ++= Dependencies.test,
      Test / testOptions := Seq.empty
  )
