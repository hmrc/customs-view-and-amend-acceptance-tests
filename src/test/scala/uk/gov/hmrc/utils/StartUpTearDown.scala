package uk.gov.hmrc.utils

import org.openqa.selenium.WebDriver

trait StartUpTearDown {
  implicit val webDriver: WebDriver = Driver.instance
}
