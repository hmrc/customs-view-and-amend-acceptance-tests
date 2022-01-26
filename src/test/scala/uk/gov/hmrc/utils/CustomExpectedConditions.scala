package uk.gov.hmrc.utils

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedCondition

object CustomExpectedConditions {


  def urlEndsWith(suffix: String)(implicit webDriver: WebDriver): ExpectedCondition[Boolean] = {
    new ExpectedCondition[Boolean] {
      override def apply(wd: WebDriver): Boolean = {
        val u = wd.getCurrentUrl
        u.endsWith(suffix)
      }
    }
  }

  def pageTitleIs(title: String)(implicit webDriver: WebDriver): ExpectedCondition[Boolean] = {
    new ExpectedCondition[Boolean] {
      override def apply(wd: WebDriver): Boolean = {
        val u = wd.getTitle
        u.equals(title)
      }
    }
  }

}
