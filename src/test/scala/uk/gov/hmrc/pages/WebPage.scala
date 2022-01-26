package uk.gov.hmrc.pages

import org.openqa.selenium.{By, WebDriver, WebElement}
import org.scalatest.Assertions
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.selenium.WebBrowser
import play.api.libs.ws.{DefaultWSCookie, WSCookie}
import uk.gov.hmrc.utils.{Configuration, StartUpTearDown}

import java.util.NoSuchElementException
import scala.collection.JavaConverters._


trait WebPage extends WebBrowser with Assertions with Matchers with StartUpTearDown{

  lazy val url = ""

  def financialsBaseUrl: String = if (envBaseUrl.startsWith("http://local")) s"$envBaseUrl:$financialsPort" else envBaseUrl
  def baseUrl: String = if (envBaseUrl.startsWith("http://local")) s"$envBaseUrl:$viewAndAmendPort" else envBaseUrl

  def goToPage(): Unit = {
    go to url
  }

  def getCurrentUrl() = webDriver.getCurrentUrl

  def elementText(selector: String): String = {
    try {
      find(cssSelector(selector)).get.text.trim
    } catch {
      case _: NoSuchElementException => fail(s"Selector $selector not found in page")
    }
  }

  def elementTextAll(selector: String): List[String] = {
    try {
      findAll(cssSelector(selector)).map(_.underlying.getText.trim).toList
    } catch {
      case _: NoSuchElementException => fail(s"Selector $selector not found in page")
    }
  }

  def elementsByCss(selector: String) = {
    try {
      webDriver.findElements(By.className(selector))
    } catch {
      case _: NoSuchElementException => fail(s"Selector $selector not found in page")
    }
  }

  def elementsByPartialLinkText(element: WebElement, partialLinkText: String) = {
    try {
      element.findElements(By.partialLinkText(partialLinkText))
    } catch {
      case _: NoSuchElementException => fail(s"Link Text $partialLinkText not found in page")
    }
  }

  protected def findElementById(id: String)(implicit webDriver: WebDriver): WebElement = {
    webDriver.findElement(By.id(id))
  }

  def getCookies() = webDriver.manage().getCookies.asScala
    .map(cookie => DefaultWSCookie(cookie.getName, cookie.getValue).asInstanceOf[WSCookie]).toSet

  def assertElementInPageWithText(element: String, exists: Boolean, expectedParagraphText: String) = {
    val allPageLinks = elementTextAll(element)
    if (exists) {
      allPageLinks should contain(expectedParagraphText)
    }
    else {
      allPageLinks should not contain expectedParagraphText
    }
  }

  private lazy val envBaseUrl : String = Configuration.settings.baseUrl
  private val financialsPort = 9876
  private val viewAndAmendPort = 9399

}
