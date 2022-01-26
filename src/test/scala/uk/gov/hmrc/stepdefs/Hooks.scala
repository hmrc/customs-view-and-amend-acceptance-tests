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
