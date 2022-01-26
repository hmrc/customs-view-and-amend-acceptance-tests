package uk.gov.hmrc.pages

import org.openqa.selenium.{By, WebElement}

import scala.collection.JavaConverters._

object CommonPage extends CustomsFinancialsWebPage {

  def continue(buttonName: String) = webDriver.findElements(By.tagName("button"))
    .asScala.filter(_.getText == buttonName).head

  def getLinkUrl(linkText: String): String = webDriver.findElement(By.partialLinkText(linkText)).getAttribute("href")

  private def cookieBanner() = webDriver.findElement(By.cssSelector(".cbanner-govuk-cookie-banner"))

  def cookieBannerText() = cookieBanner().getText.split("\n").toList

  def cookieBannerLinksButtonsText(tag: String) = cookieBanner().findElements(By.tagName(tag)).asScala
    .map(_.getText.trim).toList

  def cookieBannerLinkUrl(linkText: String) = cookieBanner().findElement(By.partialLinkText(linkText))
    .getAttribute("href").trim

  def button(buttonName: String) = cookieBanner().findElements(By.tagName("button"))
    .asScala.filter(_.getText == buttonName).head

  def cookieBannerPresence() = !webDriver.findElements(By.cssSelector(".cbanner-govuk-cookie-banner"))
    .isEmpty

  def recruitmentBannerTextIsNotVisible(): Boolean = !recruitmentBanner().isDisplayed

  def recruitmentBannerSignUpHref(): String = webDriver.findElement(By.cssSelector(".hmrc-user-research-banner__link")).getAttribute("href")

  def recruitmentBannerIsNotPresent(): Boolean = webDriver.findElements(By.cssSelector(".hmrc-user-research-banner--show")).isEmpty()

  private def recruitmentBanner(): WebElement = webDriver.findElement(By.cssSelector(".hmrc-user-research-banner--show"))

  def recruitmentBannerText() = List(recruitmentBannerHeadingText(), recruitmentBannerSignUpText(), recruitmentBannerNoThanksButtonText())

  private def recruitmentBannerHeadingText() = recruitmentBanner().findElement(By.cssSelector(".hmrc-user-research-banner__title")).getText.trim

  private def recruitmentBannerNoThanksButtonText() = {
    recruitmentBanner().findElement(By.cssSelector(".hmrc-user-research-banner__close")).getText.trim
  }

  private def recruitmentBannerSignUpText() = recruitmentBanner().findElement(By.cssSelector(".hmrc-user-research-banner__link")).getText.trim

  def clickNoThanksButtonOnBanner() = recruitmentBanner().findElement(By.cssSelector(".hmrc-user-research-banner__close")).click()


}
