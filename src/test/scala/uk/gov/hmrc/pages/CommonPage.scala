/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.pages

import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import org.openqa.selenium.{By, WebElement}

import java.time.Duration
import scala.jdk.CollectionConverters._

object CommonPage extends CustomsFinancialsWebPage {

  def navigateToPage(bookmarkWord: String, page: String): Unit =
    if (bookmarkWord.isEmpty) {
      page match {
        case "View and amend home"     => ViewAndAmendHomePage.goToPage()
        case "Customs Financials Home" => goTo(financialsBaseUrl + "/customs/payment-records")
        case "in progress claims list" => goTo(ClaimsListPages.inProgressClaimsPageUrl)
        case "Find a claim"            => ClaimSearchPage.goToPage()
        case _                         => throw new IllegalArgumentException(s"No such page - $page")
      }
    } else {
      webDriver.get(bookmarkUrl)
    }

  def userClicksOn(selectedLink: String): Unit =
    click on partialLinkText(selectedLink)

  def userShouldSeeCookieConsentBanner(expectedText: Seq[String]): Unit =
    cookieBannerText() should be(expectedText)

  def userShouldSeeBannerElements(bannerElement: String, expectedText: Seq[String]): Unit = {
    val tagName = bannerElement match {
      case "links"   =>
        expectedText.foreach(link =>
          cookieBannerLinkUrl(link) should endWith("/tracking-consent/cookie-settings")
        )
        "a"
      case "buttons" => "button"
    }
    cookieBannerLinksButtonsText(tagName) should be(expectedText)
  }

  def userClicksButton(buttonName: String): Unit =
    button(buttonName).click()

  def userShouldSeeHeading(notWord: String, subWord: String, expectedHeadingText: String): Unit = {
    val headingTag = subWord match {
      case "sub-"   => "h2"
      case ""       => "h1"
      case "small " => "h3"
      case _        => subWord.trim
    }
    val wait       = new WebDriverWait(webDriver, Duration.ofSeconds(5))
    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName(headingTag)))
    assertElementInPageWithText(headingTag, notWord.isEmpty, expectedHeadingText)
  }

  def continue(buttonName: String): WebElement = webDriver.findElements(By.tagName("button"))
    .asScala.filter(_.getText == buttonName).head

  def getLinkUrl(linkText: String): String = webDriver.findElement(By.partialLinkText(linkText)).getAttribute("href")

  private def cookieBanner() = webDriver.findElement(By.cssSelector(".cbanner-govuk-cookie-banner"))

  def cookieBannerText(): List[String] = cookieBanner().getText.split("\n").toList

  def cookieBannerLinksButtonsText(tag: String): List[String] = cookieBanner().findElements(By.tagName(tag)).asScala
    .map(_.getText.trim).toList

  def cookieBannerLinkUrl(linkText: String): String = cookieBanner().findElement(By.partialLinkText(linkText))
    .getAttribute("href").trim

  def button(buttonName: String): WebElement = cookieBanner().findElements(By.tagName("button"))
    .asScala.filter(_.getText == buttonName).head

  def cookieBannerPresence(): Boolean = !webDriver.findElements(By.cssSelector(".cbanner-govuk-cookie-banner"))
    .isEmpty

  def recruitmentBannerTextIsNotVisible(): Boolean = !recruitmentBanner().isDisplayed

  def recruitmentBannerSignUpHref(): String = webDriver.findElement(By.cssSelector(".hmrc-user-research-banner__link")).getAttribute("href")

  def recruitmentBannerIsNotPresent(): Boolean = webDriver.findElements(By.cssSelector(".hmrc-user-research-banner--show")).isEmpty()

  private def recruitmentBanner(): WebElement = webDriver.findElement(By.cssSelector(".govuk-phase-banner"))

  def recruitmentBannerText() = List(recruitmentBannerHeadingText(), recruitmentBannerNoThanksButtonText())

  private def recruitmentBannerHeadingText() = recruitmentBanner().findElement(By.cssSelector(".govuk-tag.govuk-phase-banner__content__tag")).getText.trim

  private def recruitmentBannerNoThanksButtonText() = {
    recruitmentBanner().findElement(By.cssSelector(".govuk-phase-banner__text")).getText.trim
  }

  def clickNoThanksButtonOnBanner(): Unit = recruitmentBanner().findElement(By.cssSelector(".hmrc-user-research-banner__close")).click()

  def clickOnSearchButton(): WebElement = webDriver.findElement(By.cssSelector(".govuk-button"))

}
