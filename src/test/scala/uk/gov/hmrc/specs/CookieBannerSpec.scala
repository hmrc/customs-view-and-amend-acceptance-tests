/*
 * Copyright 2026 HM Revenue & Customs
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

package uk.gov.hmrc.specs

import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.verbs.ShouldVerb
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, GivenWhenThen}
import uk.gov.hmrc.pages.CommonPage
import uk.gov.hmrc.pages.CommonPage.{navigateToPage, userClicksButton, userShouldSeeBannerElements, userShouldSeeCookieConsentBanner}
import uk.gov.hmrc.pages.SignInPage.{userIsSignedIn, userNotSignedIn}
import uk.gov.hmrc.selenium.webdriver.{Browser, ScreenshotOnFailure}
import uk.gov.hmrc.specs.tags.AcceptanceTest

class CookieBannerSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  Feature("Tracking consent banner on View and amend pages") {

    def background(): Unit = {
      Given("User is not signed in")
      userNotSignedIn()
      And("User is signed in as a registered user")
      userIsSignedIn("", "registered")
    }

    Scenario("Cookie consent banner is displayed on view and amend pages", AcceptanceTest) {
      background()
      When(s"User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      Then("User should see a cookie consent banner with the following details")
      val cookieBannerDetails = Seq(
        "Cookies on HMRC services",
        "We use some essential cookies to make our services work.",
        "We would like to set additional cookies so we can remember your settings, understand how people use our services and make improvements.",
        "Accept additional cookies",
        "Reject additional cookies",
        "View cookie preferences"
      )
      userShouldSeeCookieConsentBanner(cookieBannerDetails)
      And("User should see the following links on the cookie banner")
      userShouldSeeBannerElements("links", Seq("View cookie preferences"))
      And("User should see the following buttons on the cookie banner")
      userShouldSeeBannerElements("buttons", Seq("Accept additional cookies", "Reject additional cookies"))
    }

    Scenario("Accept cookies on cookie consent banner", AcceptanceTest) {
      background()
      When("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      And("User clicks on Accept additional cookies button")
      userClicksButton("Accept additional cookies")
      Then("User should see a cookie consent banner with the following details")
      userShouldSeeCookieConsentBanner(
        Seq(
          "You have accepted additional cookies. You can change your cookie settings at any time.",
          "Hide cookies message"
        )
      )
      And("User should see the following links on the cookie banner")
      userShouldSeeBannerElements("links", Seq("change your cookie settings"))
      And("User should see the following buttons on the cookie banner")
      userShouldSeeBannerElements("buttons", Seq("Hide cookies message"))
      When("User clicks on Hide cookies message button")
      userClicksButton("Hide cookies message")
      Then("User should not see cookie consent banner")
      CommonPage.cookieBannerPresence() should be(false)
    }

    Scenario("Reject cookies on cookie consent banner", AcceptanceTest) {
      background()
      When("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      And("User clicks on Reject additional cookies button")
      userClicksButton("Reject additional cookies")
      Then("User should see a cookie consent banner with the following details")
      userShouldSeeCookieConsentBanner(
        Seq(
          "You have rejected additional cookies. You can change your cookie settings at any time.",
          "Hide cookies message"
        )
      )
      And("User should see the following links on the cookie banner")
      userShouldSeeBannerElements("links", Seq("change your cookie settings"))
      And("User should see the following buttons on the cookie banner")
      userShouldSeeBannerElements("buttons", Seq("Hide cookies message"))
      When("User clicks on Hide cookies message button")
      userClicksButton("Hide cookies message")
      Then("User should not see cookie consent banner")
      CommonPage.cookieBannerPresence() should be(false)
    }

    Scenario("Cookie consent banner is not displayed once it is accepted", AcceptanceTest) {
      background()
      When("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      And("User clicks on Accept additional cookies button")
      userClicksButton("Accept additional cookies")
      And("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      Then("User should not see cookie consent banner")
      CommonPage.cookieBannerPresence() should be(false)
    }

    Scenario("Cookie consent banner is not displayed once it is rejected", AcceptanceTest) {
      background()
      When("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      And("User clicks on Reject additional cookies button")
      userClicksButton("Reject additional cookies")
      And("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      Then("User should not see cookie consent banner")
      CommonPage.cookieBannerPresence() should be(false)
    }
  }
}
