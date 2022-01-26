package uk.gov.hmrc.pages

import io.cucumber.scala.{EN, ScalaDsl}
import org.openqa.selenium.{By, WebElement}
import org.scalatest.AppendedClues
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import uk.gov.hmrc.utils.StartUpTearDown

trait CustomsFinancialsWebPage extends WebPage with ScalaDsl with EN with Matchers with StartUpTearDown with ScalaFutures with AppendedClues {

  var bookmarkUrl: String = ""

  def helpText(): String = {
    findElementById("help-message").getText
  }

  def backLink(): WebElement = {
    webDriver.findElement(By.cssSelector(".govuk-back-link"))
  }

  def getTextById(id: String) = {
    findElementById(id).getText.trim
  }

  def clearFieldById(id: String) = {
    findElementById(id).clear()
  }
}


