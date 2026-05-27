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
import play.api.libs.json.Json
import uk.gov.hmrc.pages.ChooseFilesPage.waitForPageToLoad
import uk.gov.hmrc.pages.generic.PageObjectFinder
import uk.gov.hmrc.utils.DropMongo.dropMongo
import uk.gov.hmrc.utils.{Configuration, WSClient}

import java.time.Duration
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.jdk.CollectionConverters._
import scala.language.postfixOps

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

  def userClicksSearchButton(): Unit =
    clickOnSearchButton().click()

  def userIsPresentedWith(page: String): Unit = {
    val p = PageObjectFinder.page(page)
    p.waitForPageHeader
    p.checkURL
    p.checkPageHeader()
    p.checkPageTitle()
  }

  def userIsPresentedWith(page: String, specificPage: String): Unit = {
    val p = PageObjectFinder.page(page)
    p.waitForPageHeader
    p.checkURL
    p.checkPageTitle(specificPage)
  }

  def userIsPresentedWithErrorPage(page: String): Unit = {
    val p = PageObjectFinder.page(page)
    p.waitForPageHeader
    p.checkPageErrorTitle()
  }

  def userIsPresentedWithErrorPage(page: String, duty: String): Unit = {
    val p = PageObjectFinder.page(page)
    p.waitForPageHeader
    p.checkPageErrorTitle(duty)
  }

  def userClicksContinueOn(page: String): Unit = {
    val p = PageObjectFinder.page(page)
    p.waitForPageHeader
    p.clickContinueButton()
  }

  def userClicksContinueIfOn(page: String): Unit =
    PageObjectFinder.page(page).continuouslyClickContinue()

  def userSelectsRadioButton(choice: String, page: String): Unit = {
    val p = PageObjectFinder.page(page)
    p.waitForPageHeader
    p.clickRadioButton(choice)
  }

  def userUploadsFile(fileNumber: Int, file: String, page: String): Unit = {
    PageObjectFinder.page(page).uploadDocument(fileNumber, file)
    waitForPageToLoad()
  }

  def errorSummaryTitleAndMessage(errorSummaryTitle: String, errorMessage: String): Unit = {
    PageObjectFinder.checkPageErrorSummaryTitle(errorSummaryTitle)
    PageObjectFinder.checkPageErrorMessage(errorMessage)
  }

  def userShouldSeeBackLink(): Unit = {
    val actual   = backLink().getText.trim
    val expected = "Back"
    actual should be(expected) withClue s"$actual is not same as $expected"
  }

  def userClicksBackLink(): Unit =
    backLink().click()

  def userShouldSeeCookieConsentBanner(expectedText: Seq[String]): Unit =
    cookieBannerText() should be(expectedText)

  def userShouldSeeBannerElements(bannerElement: String, expectedText: Seq[String]): Unit = {
    val tagName = bannerElement match {
      case "links"   =>
        expectedText.foreach(link => cookieBannerLinkUrl(link) should include("/tracking-consent/cookie-settings"))
        "a"
      case "buttons" => "button"
    }
    cookieBannerLinksButtonsText(tagName) should be(expectedText)
  }

  def userClicksButton(buttonName: String): Unit =
    button(buttonName).click()

  def userIsRedirectedToPage(expectedPageTitle: String): Unit =
    pageTitle should be(expectedPageTitle)

  def pageTitleShouldBe(expectedPageTitle: String): Unit =
    pageTitle should be(expectedPageTitle)

  def theUrlIsCorrect(linkName: String): Unit =
    linkName match {
      case "Economic Operator and Registration Identification (EORI) number (opens in a new window or tab)" =>
        NotSubscribedToCDSPage.registerForCdsLinkUrl should endWith("/customs/register-for-cds")
      case "get access to CDS (opens in a new window or tab)"                                               =>
        NotSubscribedToCDSPage.subscribeToCdsLinkUrl should startWith(
          "https://www.gov.uk/guidance/get-access-to-the-customs-declaration-service"
        )
      case "Verify or change email address"                                                                 =>
        VerifyYourEmailPage.verifyMyEmailLinkUrl(linkName) should endWith("/manage-email-cds/service/customs-finance")
      case _                                                                                                =>
        throw new IllegalArgumentException(s"$linkName link is not available on the page")
    }

  def userShouldSeeStaticText(staticOrhintWord: String, expectedText: Seq[String]): Unit = {
    val actualText         = staticOrhintWord match {
      case "static"         => elementTextAll("#main-content p.govuk-body")
      case "static-content" => elementTextAll("#main-content > h2")
      case "hint"           => List(ClaimSearchPage.hintText)
      case "label"          => List(ClaimSearchPage.labelText)
    }
    val expectedTextList   = expectedText.flatMap(t => Option(t).map(_.trim).filter(_.nonEmpty)).toList
    val filteredActualText = actualText.flatMap(_.split("\n").map(_.trim)).filter(_.nonEmpty)
    filteredActualText shouldEqual expectedTextList
  }

  def userShouldSeeTextInBulletPoints(expectedText: Seq[String]): Unit = {
    val actualText = elementTextAll(".govuk-list--bullet").head.split("\n").toSeq
    actualText should be(expectedText)
  }

  def userShouldSeeLinkTo(notWord: String, expectedLinkText: String): Unit =
    assertElementInPageWithText("a", notWord.isEmpty, expectedLinkText)

  def thePageUrlIsCorrect(linkText: String): Unit = {
    val expectedUrl = linkText match {
      case "Accessibility statement"            => "/accessibility-statement/customs-financials"
      case "Is this page not working properly?" =>
        "http://localhost:9250/contact/report-technical-problem?service=CDSRC&referrerUrl=%2Fclaim-back-import-duty-vat%2Fclaims-status"
    }
    val actualUrl   = getLinkUrl(linkText)
    actualUrl should include(expectedUrl)
  }

  def userShouldSeeBannerWithInfo(expectedText: Seq[String]): Unit =
    recruitmentBannerText() should be(expectedText)

  def customsDataStoreIsEmpty(): Unit =
    Configuration.environment.toString match {
      case "Local" => dropMongo("customs-data-store")
      case _       => ()
    }

  def userHasVerifiedEmailInDataStore(): Unit = {
    val url      = "http://localhost:9893/customs-data-store/eori/verified-email-third-party"
    val body     = Json.obj("eori" -> "GB123456789012")
    val response = WSClient.httpPost(url, body)
    val result   = Await.result(response.map(_.status), 5 seconds)
    assert(result == 200, s"$result is received")
  }

  def emailIsUndeliverable(): Unit = {
    val json     =
      """{
        |    "subject": "bounced-email",
        |    "eventId" : "77ed39b7-d5d8-46ed-abab-a5a8ff416dae",
        |    "groupId": "20180622211249.1.2A6098970A380E12@example.org",
        |    "timestamp" : "2021-05-18T11:32:30.624Z",
        |    "event" : {
        |        "id": "L4XgfOuWSpCJVjF8T9ipRw",
        |        "event": "bounced",
        |        "emailAddress": "hmrc-customer@some-domain.org",
        |        "detected": "2021-05-18T11:32:30.624Z",
        |        "code": 12,
        |        "reason": "unknown reason",
        |        "enrolment": "HMRC-CUS-ORG~EORINumber~GB123456789012"
        |    }
        |}""".stripMargin
    val url      = "http://localhost:9893/customs-data-store/update-undeliverable-email"
    val response = WSClient.httpPost(url, Json.parse(json))
    val result   = Await.result(response.map(_.status), 5 seconds)
    assert(result == 204, s"Update to mongo failed with error code $result")
  }

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

  def continue(buttonName: String): WebElement =
    webDriver.findElements(By.tagName("button")).asScala.filter(_.getText == buttonName).head

  def getLinkUrl(linkText: String): String = webDriver.findElement(By.partialLinkText(linkText)).getAttribute("href")

  private def cookieBanner() = webDriver.findElement(By.cssSelector(".cbanner-govuk-cookie-banner"))

  def cookieBannerText(): List[String] = cookieBanner().getText.split("\n").toList

  def cookieBannerLinksButtonsText(tag: String): List[String] = cookieBanner()
    .findElements(By.tagName(tag))
    .asScala
    .map(_.getText.trim)
    .toList

  def cookieBannerLinkUrl(linkText: String): String = cookieBanner()
    .findElement(By.partialLinkText(linkText))
    .getAttribute("href")
    .trim

  def button(buttonName: String): WebElement =
    cookieBanner().findElements(By.tagName("button")).asScala.filter(_.getText == buttonName).head

  def cookieBannerPresence(): Boolean = !webDriver.findElements(By.cssSelector(".cbanner-govuk-cookie-banner")).isEmpty

  def recruitmentBannerTextIsNotVisible(): Boolean = !recruitmentBanner().isDisplayed

  def recruitmentBannerSignUpHref(): String =
    webDriver.findElement(By.cssSelector(".hmrc-user-research-banner__link")).getAttribute("href")

  def recruitmentBannerIsNotPresent(): Boolean =
    webDriver.findElements(By.cssSelector(".hmrc-user-research-banner--show")).isEmpty()

  private def recruitmentBanner(): WebElement = webDriver.findElement(By.cssSelector(".govuk-phase-banner"))

  def recruitmentBannerText() = List(recruitmentBannerHeadingText(), recruitmentBannerNoThanksButtonText())

  private def recruitmentBannerHeadingText() =
    recruitmentBanner().findElement(By.cssSelector(".govuk-tag.govuk-phase-banner__content__tag")).getText.trim

  private def recruitmentBannerNoThanksButtonText() =
    recruitmentBanner().findElement(By.cssSelector(".govuk-phase-banner__text")).getText.trim

  def clickNoThanksButtonOnBanner(): Unit =
    recruitmentBanner().findElement(By.cssSelector(".hmrc-user-research-banner__close")).click()

  def clickOnSearchButton(): WebElement = webDriver.findElement(By.cssSelector(".govuk-button"))

}
