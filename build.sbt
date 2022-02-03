name := "customs-view-and-amend-acceptance-tests"

version := "0.0.1"

scalaVersion := "2.12.12"

credentials += Credentials(Path.userHome / ".sbt" / ".credentials")

val hmrcRepoHost = java.lang.System.getProperty("hmrc.repo.host", "https://nexus-preview.tax.service.gov.uk")

scalacOptions ++= Seq("-unchecked", "-deprecation")

resolvers ++= Seq(
    "hmrc-snapshots" at hmrcRepoHost + "/content/repositories/hmrc-snapshots",
    "hmrc-releases" at hmrcRepoHost + "/content/repositories/hmrc-releases",
    "typesafe-releases" at hmrcRepoHost + "/content/repositories/typesafe-releases")

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += "hmrc-releases" at "https://artefacts.tax.service.gov.uk/artifactory/hmrc-releases/"


libraryDependencies ++= Seq(
    "org.seleniumhq.selenium" % "selenium-java" % "3.141.59" % Test,
    "com.google.guava" % "guava" % "25.0-jre",
    "org.pegdown" % "pegdown" % "1.6.0" % Test,
    "org.jsoup" % "jsoup" % "1.7.3" % Test,
    "com.novocode" % "junit-interface" % "0.11" % Test,
    "com.typesafe.play" %% "play-ahc-ws-standalone" % "2.1.3",
    "com.typesafe.play" %% "play-ws-standalone-json" % "2.1.3",
    "com.typesafe.play" %% "play-json" % "2.9.2",
    "ch.qos.logback" % "logback-classic" % "1.1.7",
    "uk.gov.hmrc" %% "webdriver-factory" % "0.25.0",
    "com.typesafe.akka" %% "akka-http" % "10.0.10",
    "org.mongodb.scala" %% "mongo-scala-driver" % "4.2.3" % Test,
    "net.lightbody.bmp" % "browsermob-core" % "2.1.5",
    "org.scalatest" %% "scalatest" % "3.2.0" % Test,
    "org.scalatestplus" %% "selenium-3-141" % "3.2.0.0" % Test,
    "io.cucumber" %% "cucumber-scala" % "6.1.1" % Test,
    "io.cucumber" % "cucumber-junit" % "6.1.1" % Test,
    "junit" % "junit" % "4.12" % Test
)
