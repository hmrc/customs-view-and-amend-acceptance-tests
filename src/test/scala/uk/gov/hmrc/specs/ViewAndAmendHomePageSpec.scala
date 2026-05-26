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
import uk.gov.hmrc.pages.CommonPage.{navigateToPage, pageTitleShouldBe, thePageUrlIsCorrect, userClicksOn, userShouldSeeBannerWithInfo, userShouldSeeHeading, userShouldSeeLinkTo, userShouldSeeStaticText}
import uk.gov.hmrc.pages.SignInPage.userIsSignedIn
import uk.gov.hmrc.pages.ViewAndAmendHomePage.{userShouldSeeCards, userShouldSeeEoriDetails, userShouldSeeNotificationBarLinks}
import uk.gov.hmrc.selenium.webdriver.{Browser, ScreenshotOnFailure}
import uk.gov.hmrc.specs.tags.AcceptanceTest

class ViewAndAmendHomePageSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  Feature("View customs view and amend home page details") {

    Scenario("Gov.uk link points to correct page", AcceptanceTest) {
      Given("User is signed in as a pagination user")
      userIsSignedIn("", "pagination")
      When("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      And("User clicks on 'GOV.UK'")
      userClicksOn("GOV.UK")
      Then("The page title should be 'Welcome to GOV.UK'")
      pageTitleShouldBe("Welcome to GOV.UK")
    }

    Scenario("Verify content on view and amend home page", AcceptanceTest) {
      Given("User is signed in as a pagination user")
      userIsSignedIn("", "pagination")
      When("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      Then("User should see the heading 'Claim dashboard'")
      userShouldSeeHeading("", "", "Claim dashboard")
      And("The page title should be 'Claim dashboard - Claim back import duty and VAT - GOV.UK'")
      pageTitleShouldBe("Claim dashboard - Claim back import duty and VAT - GOV.UK")
      And("User should see the following notification bar links")
      userShouldSeeNotificationBarLinks(Seq("Claim dashboard", "Search for a claim", "Start a new claim"))
      And("User should see the eori details Tony Stark - GB744638982001")
      userShouldSeeEoriDetails("Tony Stark - GB744638982001")
    }

    ignore("Display claims summary cards with claims for all status", AcceptanceTest) {
      Given("User is signed in as a pagination user")
      userIsSignedIn("", "pagination")
      When("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      Then("User should see the following cards")
      userShouldSeeCards(
        Seq(
          Seq(
            "40 claims need more information",
            "This section lists claims where a caseworker has contacted you by email to request more information. You can upload the supporting documents they asked for by clicking into each claim.",
            "View claims needing more information"
          ),
          Seq(
            "40 claims are in progress",
            "HMRC is reviewing these claims. We aim to make a decision within 30 working days from the date you submitted your claim. If you did not include all the supporting documents, it may take longer.",
            "View claims in progress"
          ),
          Seq(
            "40 claims have been closed",
            "A claim is closed once HMRC has fully reviewed it, and approved or rejected it. If you withdraw your claim, it will also appear here.,You‘ll see closed claims for 30 days. After that, they‘re removed from the list, but you can still find an older claim by searching with your claim reference number.",
            "View closed claims"
          )
        )
      )
    }

    Scenario("Display no claims to view message", AcceptanceTest) {
      Given("User is signed in as a noClaims user")
      userIsSignedIn("", "noClaims")
      When("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      Then("User should see the heading 'Claim dashboard'")
      userShouldSeeHeading("", "", "Claim dashboard")
      And("User should see the following static text")
      userShouldSeeStaticText(
        "static",
        Seq(
          "You have no claims to view.",
          "It can take up to 24 hours for new claims to appear."
        )
      )
    }

    Scenario("Display error page when hods returned an error", AcceptanceTest) {
      Given("User is signed in as a TPI01error user")
      userIsSignedIn("", "TPI01error")
      When("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      Then("User should see the heading 'Sorry, there is a problem with the service'")
      userShouldSeeHeading("", "", "Sorry, there is a problem with the service")
      And("User should see the following static text")
      userShouldSeeStaticText("static", Seq("Try again later."))
    }

    Scenario("View Deskpro link on homepage", AcceptanceTest) {
      Given("User is signed in as a pagination user")
      userIsSignedIn("", "pagination")
      When("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      Then("User should see a link to 'Is this page not working properly? (opens in new tab)'")
      userShouldSeeLinkTo("", "Is this page not working properly? (opens in new tab)")
      And("The Is this page not working properly? page url is correct")
      thePageUrlIsCorrect("Is this page not working properly?")
    }

    Scenario("UR Banner is displayed on homepage", AcceptanceTest) {
      Given("User is signed in as a pagination user")
      userIsSignedIn("", "pagination")
      When("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      Then("User should see a banner with the following information")
      userShouldSeeBannerWithInfo(
        Seq(
          "BETA",
          "This is a new service – your feedback (opens in a new tab) will help us to improve it."
        )
      )
    }
  }
}
