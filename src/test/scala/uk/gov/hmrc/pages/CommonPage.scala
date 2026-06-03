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
import uk.gov.hmrc.pages.Constants.StaticTextKind
import uk.gov.hmrc.utils.DropMongo.dropMongo
import uk.gov.hmrc.utils.{Configuration, Environment, WSClient}

import java.time.Duration
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.jdk.CollectionConverters.*

object CommonPage extends CustomsFinancialsWebPage {

  def navigateToViewAndAmendHome(): Unit      = ViewAndAmendHomePage.goToPage()
  def navigateToCustomsFinancialsHome(): Unit = goTo(financialsBaseUrl + "/customs/payment-records")
  def navigateToInProgressClaimsList(): Unit  = goTo(ClaimsListPages.inProgressClaimsPageUrl)
  def navigateToFindAClaim(): Unit            = ClaimSearchPage.goToPage()

  def userClicksOn(selectedLink: String): Unit =
    click on partialLinkText(selectedLink)

  def userClicksSearchButton(): Unit =
    clickOnSearchButton().click()

  def userIsPresentedWith(p: BasePage): Unit = {
    p.waitForPageHeader
    p.checkURL
    p.checkPageHeader()
    p.checkPageTitle()
  }

  def userIsPresentedWith(p: BasePage, specificPage: String): Unit = {
    p.waitForPageHeader
    p.checkURL
    p.checkPageTitle(specificPage)
  }

  def userIsPresentedWithErrorPage(p: BasePage): Unit = {
    p.waitForPageHeader
    p.checkPageErrorTitle()
  }

  def userIsPresentedWithErrorPage(p: BasePage, duty: String): Unit = {
    p.waitForPageHeader
    p.checkPageErrorTitle(duty)
  }

  def userClicksContinueOn(p: BasePage): Unit = {
    p.waitForPageHeader
    p.clickContinueButton()
  }

  def userClicksContinueIfOn(p: BasePage): Unit =
    p.continuouslyClickContinue()

  def userSelectsRadioButton(fileType: Constants.FileType): Unit = {
    ChooseFileTypePage.waitForPageHeader
    ChooseFileTypePage.clickRadioButton(fileType)
  }

  def userSelectsRadioButton(yesNo: Constants.YesNo): Unit = {
    ChooseFilesPage.waitForPageHeader
    ChooseFilesPage.clickRadioButton(yesNo)
  }

  def userUploadsFile(fileNumber: Int, file: String, p: BasePage): Unit = {
    p.uploadDocument(fileNumber, file)
    waitForPageToLoad()
  }

  def errorSummaryTitleAndMessage(errorSummaryTitle: String, errorMessage: String): Unit = {
    val actualErrorSummaryTitle = webDriver.findElement(By.cssSelector(".govuk-error-summary__title")).getText
    actualErrorSummaryTitle should be(errorSummaryTitle)
    val actualErrorMessage = webDriver.findElement(By.cssSelector(".govuk-error-summary__body")).getText
    assert(actualErrorMessage.contains(errorMessage))
  }

  def userShouldSeeBackLink(): Unit = {
    val actual   = backLink().getText.trim
    val expected = "Back"
    actual should be(expected) withClue s"$actual is not same as $expected"
  }

  def userClicksBackLink(): Unit =
    backLink().click()

  def userIsRedirectedToPage(expectedPageTitle: String): Unit =
    pageTitle should be(expectedPageTitle)

  def pageTitleShouldBe(expectedPageTitle: String): Unit =
    pageTitle should be(expectedPageTitle)

  def userShouldSeeStaticText(kind: StaticTextKind, expectedText: Seq[String]): Unit = {
    val actualText         = kind match {
      case StaticTextKind.Static        => elementTextAll("#main-content p.govuk-body")
      case StaticTextKind.StaticContent => elementTextAll("#main-content > h2")
      case StaticTextKind.Hint          => List(ClaimSearchPage.hintText)
      case StaticTextKind.Label         => List(ClaimSearchPage.labelText)
    }
    val expectedTextList   = expectedText.flatMap(t => Option(t).map(_.trim).filter(_.nonEmpty)).toList
    val filteredActualText = actualText.flatMap(_.split("\n").map(_.trim)).filter(_.nonEmpty)
    filteredActualText shouldEqual expectedTextList
  }

  def userShouldSeeTextInBulletPoints(expectedText: Seq[String]): Unit = {
    val actualText = elementTextAll(".govuk-list--bullet").head.split("\n").toSeq
    actualText should be(expectedText)
  }

  def userShouldSeeLinkTo(expectedLinkText: String): Unit =
    assertElementInPageWithText("a", exists = true, expectedLinkText)

  def reportProblemLinkUrlIsCorrect(): Unit = {
    val linkText    = "Is this page not working properly?"
    val expectedUrl =
      "http://localhost:9250/contact/report-technical-problem?service=CDSRC&referrerUrl=%2Fclaim-back-import-duty-vat%2Fclaims-status"
    getLinkUrl(linkText) should include(expectedUrl)
  }

  def userShouldSeeBannerWithInfo(expectedText: Seq[String]): Unit =
    recruitmentBannerText() should be(expectedText)

  def customsDataStoreIsEmpty(): Unit =
    Configuration.environment match {
      case Environment.Local => dropMongo("customs-data-store")
      case _                 => ()
    }

  def userHasVerifiedEmailInDataStore(): Unit = {
    val url      = "http://localhost:9893/customs-data-store/eori/verified-email-third-party"
    val body     = Json.obj("eori" -> "GB123456789012")
    val response = WSClient.httpPost(url, body)
    val result   = Await.result(response.map(_.status), 5.seconds)
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
    val result   = Await.result(response.map(_.status), 5.seconds)
    assert(result == 204, s"Update to mongo failed with error code $result")
  }

  def userShouldSeeHeading(expectedHeadingText: String): Unit = {
    val wait = new WebDriverWait(webDriver, Duration.ofSeconds(5))
    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("h1")))
    assertElementInPageWithText("h1", exists = true, expectedHeadingText)
  }

  def continue(buttonName: String): WebElement =
    webDriver.findElements(By.tagName("button")).asScala.filter(_.getText == buttonName).head

  def getLinkUrl(linkText: String): String = webDriver.findElement(By.partialLinkText(linkText)).getAttribute("href")

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
