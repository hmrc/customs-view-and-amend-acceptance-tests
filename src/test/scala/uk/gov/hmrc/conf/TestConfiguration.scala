/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.conf

import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.duration.{Duration, DurationInt}
import scala.jdk.DurationConverters._


object TestConfiguration {
  val config: Config        = ConfigFactory.load()
  val env: String           = config.getString("environment")
  val defaultConfig: Config = config.getConfig("local")
  val envConfig: Config     = config.getConfig(env).withFallback(defaultConfig)

  // Everything is a `def`` so that tests can invalidate the config
  private def configuration: Config =
    ConfigFactory.load()

  private def environment: String              = configuration.getString("environment")
  private def defaultConfiguration: Config     = configuration.getConfig("local")
  private def environmentConfiguration: Config = configuration.getConfig(environment).withFallback(defaultConfiguration)

  def url(service: String): String = {
    val host = env match {
      case "local" => s"$environmentHost:${servicePort(service)}"
      case _       => s"${envConfig.getString(s"services.host")}"
    }
    s"$host${serviceRoute(service)}"
  }

  def securityAssessmentEnabled: Boolean =
    configuration.getBoolean("security.assessment")

  def zapHost: String =
    configuration.getString("zap.host")

  def accessibilityAssessmentEnabled: Boolean =
    configuration.getBoolean("accessibility.assessment")

  def accessibilityTimeout: Duration =
    // For backward compatibility, fallback to Int definition
    scala.util
      .Try(configuration.getDuration("accessibility.timeout").toScala)
      .getOrElse(configuration.getInt("accessibility.timeout").millis)

  // Since there is a system property "browser" which is a String
  // this is incompatible with HOCON where browser.logger etc mean browser is an Object
  // For now stick with system properties only

  def browserType: Option[String] =
    sys.props
      .get("browser")
      .map(_.toLowerCase)

  def browserLoggingEnabled: Boolean =
    sys.props.getOrElse("browser.logging", "false").toBoolean

  def browserOptionHeadLessEnabled: Boolean =
    sys.props.getOrElse("browser.option.headless", "true").toBoolean

  def browserVersion: String =
    sys.props.getOrElse("browser.version", "126")

  private def servicePort(service: String): String =
    environmentConfiguration.getString(s"services.$service.port")

  private def serviceRoute(service: String): String =
    environmentConfiguration.getString(s"services.$service.productionRoute")
  def environmentHost: String = envConfig.getString("services.host")



}
