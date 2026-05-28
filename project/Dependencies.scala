import sbt.{Test, *}

object Dependencies {

  val test = Seq(
    "uk.gov.hmrc"       %% "ui-test-runner"          % "0.54.0"   % Test,
    "org.scalatestplus" %% "selenium-4-12"           % "3.2.17.0" % Test,
    "com.typesafe.play" %% "play-ahc-ws-standalone"  % "2.2.16"   % Test,
    "com.typesafe.play" %% "play-ws-standalone-json" % "2.2.16",
    "com.typesafe.play" %% "play-json"               % "2.10.8",
    "org.mongodb.scala" %% "mongo-scala-driver"      % "5.7.1"
  )

}
