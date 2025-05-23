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

package uk.gov.hmrc.stepdefs

import io.cucumber.datatable.DataTable

import java.util.{List => JList}
import scala.jdk.CollectionConverters._
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import play.api.libs.json.Json
import uk.gov.hmrc.conf.TestConfiguration
import uk.gov.hmrc.pages.ChooseFilesPage.waitForPageToLoad
import uk.gov.hmrc.pages.CommonPage.getLinkUrl
import uk.gov.hmrc.pages._
import uk.gov.hmrc.pages.auth.AuthorityLoginStubPage
import uk.gov.hmrc.pages.generic.PageObjectFinder
import uk.gov.hmrc.utils.DropMongo.dropMongo
import uk.gov.hmrc.utils.{Configuration, WSClient}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import java.time.Duration
import scala.language.postfixOps

class CommonSteps extends CustomsFinancialsWebPage {

  When("""^I navigate to the (bookmarked |)(.*) page$""") { (bookmarkWord: String, page: String) =>
    if (bookmarkWord.isEmpty) {
      page match {
        case "View and amend home"     => ViewAndAmendHomePage.goToPage()
        case "Customs Financials Home" => goTo(financialsBaseUrl + "/customs/payment-records")
        case "in progress claims list" => goTo(ClaimsListPages.inProgressClaimsPageUrl)
        case "Find a claim"            => ClaimSearchPage.goToPage()
        //        case "Sign out" => LogoutPage.goToPage()
        case _                         => throw new IllegalArgumentException(s"No such page - $page")
      }
    } else {
      webDriver.get(bookmarkUrl)
    }
  }

  Then("""^the page title (should be|is) "([^"]*)"$""") { (_: String, expectedPageTitle: String) =>
    pageTitle should be(expectedPageTitle)
  }

  Then("""^I (should |)(not |)see the (|sub-|h[1-9] |small )heading "([^"]*)"$""") {
    (shouldWord: String, notWord: String, subWord: String, expectedHeadingText: String) =>
      val headingTag = {
        subWord match {
          case "sub-"   => "h2"
          case ""       => "h1"
          case "small " => "h3"
          case _        => subWord.trim
        }
      }
      val wait = new WebDriverWait(webDriver, Duration.ofSeconds(5))
      wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName(headingTag)))
      assertElementInPageWithText(headingTag, notWord.isEmpty, expectedHeadingText)
  }

  Then("""^I should (not |)see a link to "([^"]*)"$""") { (notWord: String, expectedLinkText: String) =>
    assertElementInPageWithText("a", notWord.isEmpty, expectedLinkText)
  }

  When("""^I click on '(.*)'$""") { selectedLink: String =>
    click on partialLinkText(selectedLink)
  }

  When("""^I click on Search button on the Find a claim page$""") { () =>
    CommonPage.clickOnSearchButton().click()
  }

  When("""^I click on back link to previous page$""") { () =>
    backLink().click()
  }

  Then("""^I should see the back link to previous page$""") { () =>
    val actual   = backLink().getText.trim
    val expected = "Back"
    actual should be(expected) withClue s"$actual is not same as $expected"
  }

  Then("""^I should see the Help and support text""") { data: DataTable =>
    val text = data.asScalaListOfStrings.mkString("\n")
    helpText() should be(text)
  }

  Then("""^I should see the following (content|errors) with identifiers""") { (_: String, dataTable: DataTable) =>
    dataTable.asScalaListOfUtilMaps.map { a =>
      val actualText   = getTextById(a.get("id"))
      val expectedText = a.get("text").trim
      actualText should be(expectedText) withClue s"$actualText is not same as $expectedText"
    }
  }

  Then("""^I am redirected to the "([^"]*)" page$""") { expectedPageTitle: String =>
    pageTitle should be(expectedPageTitle)
  }

  Then("""^I should see a cookie consent banner with the following details$""") { data: DataTable =>
    val expectedText = data.asScalaListOfStrings
    CommonPage.cookieBannerText() should be(expectedText)
  }

  And("""^I should see the following (links|buttons) on the cookie banner$""") {
    (bannerElement: String, data: DataTable) =>
      val expectedText = data.asScalaListOfStrings
      val tagName      = bannerElement match {
        case "links"   =>
          expectedText.map(link =>
            CommonPage.cookieBannerLinkUrl(link) should endWith("/tracking-consent/cookie-settings")
          )
          "a"
        case "buttons" => "button"
      }
      CommonPage.cookieBannerLinksButtonsText(tagName) should be(expectedText)
  }

  When("""^I click on (.*) button$""") { buttonName: String =>
    CommonPage.button(buttonName).click()
  }

  Then("""^I should not see cookie consent banner$""") { () =>
    CommonPage.cookieBannerPresence() should be(false)
  }

  Then("""^the (.*) page url is correct$""") { (linkText: String) =>
    val expectedUrl = linkText match {
      case "Accessibility statement"            => "/accessibility-statement/customs-financials"
      case "Is this page not working properly?" =>
        s"http://localhost:9250/contact/report-technical-problem?service=CDSRC&referrerUrl=%2Fclaim-back-import-duty-vat%2Fclaims-status"
    }
    val actualUrl   = getLinkUrl(linkText)
    actualUrl should include(expectedUrl)
  }

  Then("""^I should see a banner with the following information$""") { data: DataTable =>
    val expectedText = data.asScalaListOfStrings
    val actualText   = CommonPage.recruitmentBannerText()
    actualText should be(expectedText)
  }

  Then("""^the signup url is correct$""") { () =>
    val expectedText =
      "https://signup.take-part-in-research.service.gov.uk/?utm_campaign=CDSRC&utm_source=Other&utm_medium=other&t=HMRC&id=249"
    val actualText   = CommonPage.recruitmentBannerSignUpHref()
    actualText should be(expectedText)
  }

  Then("""^the "(.*)" url is correct$""") { linkName: String =>
    linkName match {
      case "Economic Operator and Registration Identification (EORI) number (opens in a new window or tab)" =>
        NotSubscribedToCDSPage.registerForCdsLinkUrl should endWith("/customs/register-for-cds")
      case "get access to CDS (opens in a new window or tab)"                                               =>
        NotSubscribedToCDSPage.subscribeToCdsLinkUrl should startWith(
          "https://www.gov.uk/guidance/get-access-to-the-customs-declaration-service"
        )
      case "Verify or change email address"                                                                 =>
        VerifyYourEmailPage.verifyMyEmailLinkUrl(linkName) should endWith("/manage-email-cds/service/customs-finance")
      case _                                                                                                => throw new IllegalArgumentException(s"$linkName link is not available on the page")
    }
  }

  Then("""^I should see the following (static|hint|static-content|label|) text$""") {
    (staticOrhintWord: String, expectedText: DataTable) =>
      val actualText = staticOrhintWord match {
        case "static"         => elementTextAll("#main-content p.govuk-body")
        case "static-content" => elementTextAll("#main-content > h2")
        case "hint"           => List(ClaimSearchPage.hintText)
        case "label"          => List(ClaimSearchPage.labelText)
      }
      val expectedTextList: List[String] = expectedText.asList(classOf[String]).asInstanceOf[JList[String]].asScala
        .flatMap(element => Option(element).map(_.trim).filter(_.nonEmpty))
        .toList
      val filteredActualText = actualText.flatMap(_.split("\n").map(_.trim)).filter(_.nonEmpty).toList
      filteredActualText shouldEqual expectedTextList


  }

  Then("""^I should see the following text in bullet points$""") { expectedText: DataTable =>
    val actualText = elementTextAll(".govuk-list--bullet").head.split("\n")
    actualText should be(expectedText.asScalaListOfStrings)
  }

  Given("""^the customs data store is empty""") { () =>
    Configuration.environment.toString match {
      case "Local" => dropMongo("customs-data-store")
      case _       => None
    }
  }

  And("""^the user has a verified email address in the data store$""") { () =>
    val url      = "http://localhost:9893/customs-data-store/eori/GB123456789012/verified-email"
    val response = WSClient.httpGet(url, Set.empty)
    val result   = Await.result(response.map(_.status), 5 seconds)
    assert(result == 200, s"$result is received")
  }

  And("""^the email is undeliverable$""") { () =>
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

  When("""I select Welsh translation on {string}""") { (page: String) =>
    PageObjectFinder.page(page).waitForPageHeader
    PageObjectFinder.page(page).enableWelsh()
  }

  When("""I enter redirectURL on {string}""") { (page: String) =>
    page match {
      case "Authority Login Stub Page" =>
        AuthorityLoginStubPage.enterRedirectURL(TestConfiguration.url("cds-frontend") + "/start")
    }
  }

  When("""I enter Enrollment Key {string}, ID Name {string} and ID Value {string} on {string}""") {
    (eKey: String, IDName: String, IDValue: String, _: String) =>
      AuthorityLoginStubPage.enrolments(eKey, IDName, IDValue)
  }

  When("""I click continue on {string}""") { (page: String) =>
    PageObjectFinder.page(page).waitForPageHeader
    PageObjectFinder.page(page).clickContinueButton()
  }

  Then("""I am presented with the {string}""") { page: String =>
    PageObjectFinder.page(page).waitForPageHeader
    PageObjectFinder.page(page).checkURL
    PageObjectFinder.page(page).checkPageHeader()
    PageObjectFinder.page(page).checkPageTitle()
  }

  Then("""I am presented with the {string} {string}""") { (page: String, specificPage: String) =>
    PageObjectFinder.page(page).waitForPageHeader
    PageObjectFinder.page(page).checkURL
    PageObjectFinder.page(page).checkPageTitle(specificPage)
  }

  When("""I upload a {int} {string} file on {string}""") { (fileNumber: Int, file: String, page: String) =>
    PageObjectFinder.page(page).uploadDocument(fileNumber, file)
    waitForPageToLoad()
  }

  When("""I click continue if I'm on {string}""") { (page: String) =>
    PageObjectFinder.page(page).continuouslyClickContinue()
  }

  When("""I select radio button {string} on {string}""") { (choice: String, page: String) =>
    PageObjectFinder.page(page).waitForPageHeader
    PageObjectFinder.page(page).clickRadioButton(choice)
  }

  Then("""I am presented with the {string} error page""") { page: String =>
    PageObjectFinder.page(page).waitForPageHeader
    PageObjectFinder.page(page).checkPageErrorTitle()
  }

  Then("""I am presented with the {string} {string} error page""") { (page: String, duty: String) =>
    PageObjectFinder.page(page).waitForPageHeader
    PageObjectFinder.page(page).checkPageErrorTitle(duty)
  }

  Then("""The error summary title is {string} and the error message is {string}""") { (errorSummaryTitle: String, errorMessage: String) =>
    PageObjectFinder.checkPageErrorSummaryTitle(errorSummaryTitle)
    PageObjectFinder.checkPageErrorMessage(errorMessage)
  }
}
