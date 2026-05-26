import sbt.{Test, *}

object Dependencies {

  val test = Seq(
    "uk.gov.hmrc"         %% "ui-test-runner"          % "0.54.0"   % Test,
    "org.scalatest"       %% "scalatest"               % "3.2.19"   % Test,
    "org.scalatestplus"   %% "selenium-4-12"           % "3.2.17.0" % Test,
    "com.vladsch.flexmark" % "flexmark-all"            % "0.64.8"   % Test,
    "com.typesafe.play"   %% "play-ahc-ws-standalone"  % "2.2.11"   % Test,
    "com.typesafe.play"   %% "play-ws-standalone-json" % "2.2.11",
    "com.typesafe.play"   %% "play-json"               % "2.10.6",
    "com.typesafe.akka"   %% "akka-http"               % "10.2.0"   % Test,
    "org.mongodb.scala"   %% "mongo-scala-driver"      % "5.5.0",
    "com.typesafe"         % "config"                  % "1.4.3"    % Test
  )

}
