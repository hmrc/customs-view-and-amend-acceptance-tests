import sbt.{Test, *}

object Dependencies {

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"       %% "ui-test-runner"          % "0.54.0"   % Test,
    "org.scalatestplus" %% "selenium-4-12"           % "3.2.17.0" % Test,
    "org.playframework" %% "play-ahc-ws-standalone"  % "3.0.8"    % Test,
    "org.playframework" %% "play-ws-standalone-json" % "3.0.8",
    "org.playframework" %% "play-json"               % "3.0.6",
    "org.mongodb.scala" %% "mongo-scala-driver"      % "5.7.1" cross CrossVersion.for3Use2_13
  )

}
