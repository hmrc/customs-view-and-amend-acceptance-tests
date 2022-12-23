import sbt._

object Dependencies {

  val test = Seq(
    "uk.gov.hmrc"         %% "webdriver-factory"       % "0.39.0"   % Test,
    "org.scalatest"       %% "scalatest"               % "3.2.13"   % Test,
    "org.scalatestplus"   %% "selenium-4-2"            % "3.2.13.0" % Test,
    "com.vladsch.flexmark" % "flexmark-all"            % "0.62.2"   % Test,
    "io.cucumber"         %% "cucumber-scala"          % "8.7.0"    % Test,
    "io.cucumber"          % "cucumber-junit"          % "7.6.0"    % Test,
    "junit"                % "junit"                   % "4.13.2"   % Test,
    "com.novocode"         % "junit-interface"         % "0.11"     % Test,
    "com.typesafe.play"   %% "play-ahc-ws-standalone"  % "2.1.3",
    "com.typesafe.play"   %% "play-ws-standalone-json" % "2.1.3",
    "com.typesafe.play"   %% "play-json"               % "2.9.2",
    "com.typesafe.akka"   %% "akka-http"               % "10.0.10",
    "org.mongodb.scala"   %% "mongo-scala-driver"      % "2.4.0",
    "com.typesafe"         % "config"                  % "1.4.2"    % Test
  )

}