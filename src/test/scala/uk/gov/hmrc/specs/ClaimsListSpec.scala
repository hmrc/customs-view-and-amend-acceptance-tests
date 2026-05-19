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

package uk.gov.hmrc.specs

import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.prop.TableDrivenPropertyChecks.forAll
import org.scalatest.prop.Tables.Table
import org.scalatest.verbs.ShouldVerb
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, GivenWhenThen}
import uk.gov.hmrc.pages.ClaimsListPages.userShouldSeeClaims
import uk.gov.hmrc.pages.CommonPage.{
  navigateToPage,
  pageTitleShouldBe,
  userClicksBackLink,
  userClicksOn,
  userShouldSeeBackLink,
  userShouldSeeHeading,
  userShouldSeeStaticText
}
import uk.gov.hmrc.pages.SignInPage.userIsSignedIn
import uk.gov.hmrc.pages.ViewAndAmendHomePage.userShouldSeeEoriDetails
import uk.gov.hmrc.selenium.webdriver.{Browser, ScreenshotOnFailure}
import uk.gov.hmrc.specs.tags.AcceptanceTest

class ClaimsListSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  Feature("Display claims list") {

    Scenario("Verify content on in progress claims list page", AcceptanceTest) {
      Given("User is signed in as an openClaim user")
      userIsSignedIn("", "openClaim")
      And("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      When("User clicks on 'View claims in progress'")
      userClicksOn("View claims in progress")
      Then("User should see the heading 'Claims in progress'")
      userShouldSeeHeading("", "", "Claims in progress")
      And("The page title should be 'Claims in progress - Claim back import duty and VAT - GOV.UK'")
      pageTitleShouldBe("Claims in progress - Claim back import duty and VAT - GOV.UK")
      And("User should see the following claims")
      userShouldSeeClaims(
        Seq(
          Seq("Claim reference", "MRN", "Type of claim", "Claim submitted date"),
          Seq("NDRC-1001", "MRN23014", "Overpayment or rejected goods", "1 May 2020")
        )
      )
    }

    Scenario("Verify content on pending claims list page", AcceptanceTest) {
      Given("User is signed in as a PendingQueriedClaim user")
      userIsSignedIn("", "PendingQueriedClaim")
      And("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      And("User should see the eori details Tony Stark - GB00000000004 (XI00000000004)")
      userShouldSeeEoriDetails("Tony Stark - GB00000000004 (XI00000000004)")
      When("User clicks on 'View claims needing more information'")
      userClicksOn("View claims needing more information")
      Then("User should see the heading 'Claims needing more information'")
      userShouldSeeHeading("", "", "Claims needing more information")
      And("The page title should be 'Claims needing more information - Claim back import duty and VAT - GOV.UK'")
      pageTitleShouldBe("Claims needing more information - Claim back import duty and VAT - GOV.UK")
      And("User should see the following claims")
      userShouldSeeClaims(
        Seq(
          Seq("Claim reference", "MRN", "Type of claim", "Claim submitted date"),
          Seq("NDRC-1004", "MRN23014", "Overpayment or rejected goods", "1 May 2020"),
          Seq("NDRC-1504", "MRN23014", "Overpayment or rejected goods", "1 May 2020")
        )
      )
    }

    Scenario("Verify content on closed claims list page", AcceptanceTest) {
      Given("User is signed in as a ResolvedWithdrawnClaim user")
      userIsSignedIn("", "ResolvedWithdrawnClaim")
      And("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      When("User clicks on 'View closed claims'")
      userClicksOn("View closed claims")
      Then("User should see the heading 'Closed claims'")
      userShouldSeeHeading("", "", "Closed claims")
      And("The page title should be 'Closed claims - Claim back import duty and VAT - GOV.UK'")
      pageTitleShouldBe("Closed claims - Claim back import duty and VAT - GOV.UK")
      And("User should see the following static text")
      userShouldSeeStaticText(
        "static",
        Seq(
          "This page shows claims that were approved, rejected, or withdrawn in the past 30 days.",
          "To view older closed claims, search using the claim reference number."
        )
      )
      And("User should see the following claims")
      userShouldSeeClaims(
        Seq(
          Seq("Claim reference", "MRN", "Type of claim", "Claim decision date", "Claim decision"),
          Seq("NDRC-1005", "MRN23014", "Overpayment or rejected goods", "1 May 2021", "Withdrawn")
        )
      )
    }

    Scenario("Navigation back to view and amend home page", AcceptanceTest) {
      val claimsStatuses = Table(
        "claims status",
        "View claims in progress",
        "View claims needing more information",
        "View closed claims"
      )
      forAll(claimsStatuses) { claimsStatus =>
        Given("User is signed in as a pagination user")
        userIsSignedIn("", "pagination")
        And("User navigates to the View and amend home page")
        navigateToPage("", "View and amend home")
        And(s"User clicks on '$claimsStatus'")
        userClicksOn(claimsStatus)
        Then("User should see the back link to previous page")
        userShouldSeeBackLink()
        When("User clicks on back link to previous page")
        userClicksBackLink()
        Then("The page title should be 'Claim dashboard - Claim back import duty and VAT - GOV.UK'")
        pageTitleShouldBe("Claim dashboard - Claim back import duty and VAT - GOV.UK")
      }
    }

    Scenario("Display error page when hods returned an error", AcceptanceTest) {
      Given("User is signed in as a TPI01error user")
      userIsSignedIn("", "TPI01error")
      When("User navigates to the in progress claims list page")
      navigateToPage("", "in progress claims list")
      Then("User should see the heading 'Sorry, there is a problem with the service'")
      userShouldSeeHeading("", "", "Sorry, there is a problem with the service")
      And("User should see the following static text")
      userShouldSeeStaticText("static", Seq("Try again later."))
    }
  }
}
