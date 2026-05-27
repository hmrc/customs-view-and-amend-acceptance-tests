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
import org.scalatest.prop.TableDrivenPropertyChecks.forAll
import org.scalatest.prop.Tables.Table
import org.scalatest.verbs.ShouldVerb
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, GivenWhenThen}
import uk.gov.hmrc.pages.ClaimDetailsPage.userShouldSeeClaimDetails
import uk.gov.hmrc.pages.CommonPage.{navigateToPage, pageTitleShouldBe, userClicksBackLink, userClicksOn, userShouldSeeBackLink, userShouldSeeHeading, userShouldSeeStaticText}
import uk.gov.hmrc.pages.SignInPage.userIsSignedIn
import uk.gov.hmrc.selenium.webdriver.{Browser, ScreenshotOnFailure}
import uk.gov.hmrc.specs.tags.{AcceptanceTest, E2ETest}

class ClaimDetailsSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  Feature("View claim details page") {

    Scenario("View in progress claim details", E2ETest) {
      Given("User is signed in as an openClaim user")
      userIsSignedIn("", "openClaim")
      And("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      And("User clicks on 'View claims in progress'")
      userClicksOn("View claims in progress")
      When("User clicks on 'NDRC-1001'")
      userClicksOn("NDRC-1001")
      Then("User should see the heading 'Claim reference NDRC-1001'")
      userShouldSeeHeading("", "", "Claim reference NDRC-1001")
      And("User should see the following static text")
      val inProgressClaimText = Seq("Claim details: This claim is open and being reviewed by HMRC.")
      inProgressClaimText.foreach(line => info(s"- $line"))
      userShouldSeeStaticText("static", inProgressClaimText)
      And("User should see the following claim details")
      val inProgressClaimDetails = Map(
        "Local Reference Number (LRN)"      -> "KWMREF1",
        "Claimant‘s EORI number"            -> "GB98765432101",
        "Claim type"                        -> "Rejected goods (C&E1179),Multiple declarations",
        "Claim status"                      -> "In progress",
        "First MRN"                         -> "MRN23014",
        "Claim submitted date"              -> "1 May 2020",
        "Claim amount requested"            -> "£900000.00",
        "Claimant‘s name"                   -> "Claimant name",
        "Claimant‘s email address"          -> "Claimant email address",
        "Other MRNs included in this claim" -> "MRN00002,MRN00003,MRN00004,MRN00005,MRN00006,MRN00007,MRN00008,MRN00009,MRN000010"
      )
      inProgressClaimDetails.foreach { case (k, v) => info(s"- $k: $v") }
      userShouldSeeClaimDetails(inProgressClaimDetails)
    }

    Scenario("View pending claim details", E2ETest) {
      Given("User is signed in as a PendingQueriedClaim user")
      userIsSignedIn("", "PendingQueriedClaim")
      And("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      And("User clicks on 'View claims needing more information'")
      userClicksOn("View claims needing more information")
      When("User clicks on 'NDRC-1004'")
      userClicksOn("NDRC-1004")
      Then("User should see the heading 'Claim reference NDRC-1004'")
      userShouldSeeHeading("", "", "Claim reference NDRC-1004")
      And("User should see the following static text")
      val pendingClaimText = Seq(
        "Claim details: This claim requires additional information or documentation.",
        "Check the inbox of Claimant email address for missing documents and any requests for more information from your caseworker.",
        "If your claim was submitted online, upload your supporting documents.",
        "Valid file formats: Excel, JPG, PNG, PDF, CSV, TXT or Word.",
        "If your claim was submitted manually, you will need to send your supporting documents by post."
      )
      pendingClaimText.foreach(line => info(s"- $line"))
      userShouldSeeStaticText("static", pendingClaimText)
      And("User should see the following claim details")
      val pendingClaimDetails = Map(
        "MRN"                          -> "MRN23014",
        "Local Reference Number (LRN)" -> "KWMREF1",
        "Claimant‘s EORI number"       -> "GB98765432101",
        "Claim type"                   -> "Overpayment (C285),Single declaration",
        "Claim status"                 -> "Pending",
        "Claim submitted date"         -> "1 May 2020",
        "Claim amount requested"       -> "£900000.00",
        "Claimant‘s name"              -> "Claimant name",
        "Claimant‘s email address"     -> "Claimant email address"
      )
      pendingClaimDetails.foreach { case (k, v) => info(s"- $k: $v") }
      userShouldSeeClaimDetails(pendingClaimDetails)
      When("User clicks on 'Upload a file'")
      userClicksOn("Upload a file")
      And("User should see the heading 'Add supporting documents to your claim NDRC-1004'")
      userShouldSeeHeading("", "", "Add supporting documents to your claim NDRC-1004")
    }

    Scenario("View closed claim details", E2ETest) {
      Given("User is signed in as a ResolvedApprovedClaim user")
      userIsSignedIn("", "ResolvedApprovedClaim")
      And("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      And("User clicks on 'View closed claims'")
      userClicksOn("View closed claims")
      When("User clicks on 'NDRC-10014'")
      userClicksOn("NDRC-10014")
      Then("User should see the heading 'Claim reference NDRC-10014'")
      userShouldSeeHeading("", "", "Claim reference NDRC-10014")
      And("User should see the following static text")
      val closedClaimText = Seq("Claim details: This claim has been closed")
      closedClaimText.foreach(line => info(s"- $line"))
      userShouldSeeStaticText("static", closedClaimText)
      And("User should see the following claim details")
      val closedClaimDetails = Map(
        "MRN"                          -> "MRN23014",
        "Local Reference Number (LRN)" -> "KWMREF1",
        "Claimant‘s EORI number"       -> "GB98765432101",
        "Claim type"                   -> "Overpayment (C285),Single declaration",
        "Claim decision"               -> "Approved",
        "Claim submitted date"         -> "1 May 2020",
        "Claim decision date"          -> "1 May 2021",
        "Claim amount requested"       -> "£900000.00",
        "Claimant‘s name"              -> "Claimant name",
        "Claimant‘s email address"     -> "Claimant email address"
      )
      closedClaimDetails.foreach { case (k, v) => info(s"- $k: $v") }
      userShouldSeeClaimDetails(closedClaimDetails)
    }

    Scenario("Navigation back to claims list page", AcceptanceTest) {
      val examples = Table(
        ("claims status", "claim number", "page title"),
        (
          "View claims needing more information",
          "NDRC-21",
          "Claims needing more information (page 1 of 2) - Claim back import duty and VAT - GOV.UK"
        ),
        (
          "View claims in progress",
          "NDRC-1",
          "Claims in progress (page 1 of 2) - Claim back import duty and VAT - GOV.UK"
        ),
        (
          "View closed claims",
          "NDRC-41",
          "Closed claims (page 1 of 2) - Claim back import duty and VAT - GOV.UK"
        )
      )
      forAll(examples) { (claimsStatus, claimNumber, pageTitle) =>
        Given("User is signed in as a pagination user")
        userIsSignedIn("", "pagination")
        And("User navigates to the View and amend home page")
        navigateToPage("", "View and amend home")
        And(s"User clicks on '$claimsStatus'")
        userClicksOn(claimsStatus)
        When(s"User clicks on '$claimNumber'")
        userClicksOn(claimNumber)
        Then("User should see the back link to previous page")
        userShouldSeeBackLink()
        When("User clicks on back link to previous page")
        userClicksBackLink()
        Then(s"The page title should be '$pageTitle'")
        pageTitleShouldBe(pageTitle)
      }
    }

    Scenario("Navigate to home page using Claims summary link", AcceptanceTest) {
      Given("User is signed in as a pagination user")
      userIsSignedIn("", "pagination")
      And("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      And("User clicks on 'View closed claims'")
      userClicksOn("View closed claims")
      And("User clicks on 'NDRC-41'")
      userClicksOn("NDRC-41")
      When("User clicks on 'Claim dashboard'")
      userClicksOn("Claim dashboard")
      Then("The page title should be 'Claim dashboard - Claim back import duty and VAT - GOV.UK'")
      pageTitleShouldBe("Claim dashboard - Claim back import duty and VAT - GOV.UK")
    }
  }
}
