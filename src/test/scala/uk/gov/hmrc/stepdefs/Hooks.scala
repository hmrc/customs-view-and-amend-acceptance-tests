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

package uk.gov.hmrc.stepdefs

import io.cucumber.scala.{EN, ScalaDsl, Scenario}
import org.openqa.selenium.{OutputType, TakesScreenshot, WebDriverException}
import uk.gov.hmrc.utils.StartUpTearDown


class Hooks extends ScalaDsl with EN with StartUpTearDown{

  After {
    (result: Scenario) =>
      if (result.isFailed) {
        webDriver match {
          case screenshot1: TakesScreenshot =>
            try {
              val screenshotName = result.getName.replaceAll(" ", "_")
              val screenshot = screenshot1.getScreenshotAs(OutputType.BYTES)
              result.attach(screenshot, "image/png", screenshotName)
            } catch {
              case somePlatformsDontSupportScreenshots: WebDriverException => System.err.println(somePlatformsDontSupportScreenshots.getMessage)
            }
          case _ =>
        }
      }
  }
}
