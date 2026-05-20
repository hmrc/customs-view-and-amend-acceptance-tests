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
import uk.gov.hmrc.pages.ClaimDetailsPage.userShouldSeeClaimDetails
import uk.gov.hmrc.pages.ClaimSearchPage.userSearchesFor
import uk.gov.hmrc.pages.CommonPage.{
  navigateToPage,
  pageTitleShouldBe,
  userClicksBackLink,
  userClicksOn,
  userClicksSearchButton,
  userShouldSeeBackLink,
  userShouldSeeHeading,
  userShouldSeeStaticText
}
import uk.gov.hmrc.pages.SignInPage.userIsSignedIn
import uk.gov.hmrc.selenium.webdriver.{Browser, ScreenshotOnFailure}
import uk.gov.hmrc.specs.tags.{AcceptanceTest, E2ETest}

class ClaimSearchSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  Feature("Search for claims using claim number and MRN") {

    Scenario("Verify the content claims search page", AcceptanceTest) {
      Given("User is signed in as a pagination user")
      userIsSignedIn("", "pagination")
      And("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      When("User clicks on 'Search for a claim'")
      userClicksOn("Search for a claim")
      Then("User should see the heading 'Search for a claim'")
      userShouldSeeHeading("", "", "Search for a claim")
      And("User should see the following hint text")
      userShouldSeeStaticText(
        "hint",
        Seq("Enter a Movement Reference Number (MRN), or the claim reference number for older claims")
      )
    }

    Scenario("Verify no matching results page for claim number search", AcceptanceTest) {
      Given("User is signed in as a pagination user")
      userIsSignedIn("", "pagination")
      And("User navigates to the Find a claim page")
      navigateToPage("", "Find a claim")
      When("User searches for claim 'NDRC-9999'")
      userSearchesFor("NDRC-9999")
      Then("User should see the heading 'No matching results for NDRC-9999'")
      userShouldSeeHeading("", "", "No matching results for NDRC-9999")
      And("User should see the following static text")
      userShouldSeeStaticText(
        "static",
        Seq(
          "If your claim was submitted in the last 30 days and includes more than one Movement Reference Number (MRN), try searching with the first MRN you entered when you started the claim.",
          "To find older claims, search with your claim reference number."
        )
      )
      When("User clicks on Search button on the Find a claim page")
      userClicksSearchButton()
      Then("User should see the heading 'Search for a claim'")
      userShouldSeeHeading("", "", "Search for a claim")
    }

    Scenario("Verify no matching results page for MRN search", AcceptanceTest) {
      Given("User is signed in as a pagination user")
      userIsSignedIn("", "pagination")
      And("User navigates to the Find a claim page")
      navigateToPage("", "Find a claim")
      When("User searches for MRN '22GBJD4DCMAM33DOI2'")
      userSearchesFor("22GBJD4DCMAM33DOI2")
      Then("User should see the heading 'No matching results for 22GBJD4DCMAM33DOI2'")
      userShouldSeeHeading("", "", "No matching results for 22GBJD4DCMAM33DOI2")
      And("User should see the following static text")
      userShouldSeeStaticText(
        "static",
        Seq(
          "If your claim was submitted in the last 30 days and includes more than one Movement Reference Number (MRN), try searching with the first MRN you entered when you started the claim.",
          "To find older claims, search with your claim reference number."
        )
      )
      When("User clicks on Search button on the Find a claim page")
      userClicksSearchButton()
      When("User clicks on back link to previous page")
      userClicksBackLink()
      Then("User should see the heading 'Search for a claim'")
      userShouldSeeHeading("", "", "Search for a claim")
    }

    Scenario("Navigation back to view and amend home page", AcceptanceTest) {
      Given("User is signed in as a pagination user")
      userIsSignedIn("", "pagination")
      And("User navigates to the Find a claim page")
      navigateToPage("", "Find a claim")
      Then("User should see the back link to previous page")
      userShouldSeeBackLink()
      When("User clicks on back link to previous page")
      userClicksBackLink()
      Then("The page title should be 'Claim dashboard - Claim back import duty and VAT - GOV.UK'")
      pageTitleShouldBe("Claim dashboard - Claim back import duty and VAT - GOV.UK")
    }

    Scenario("Search closed claims using claim number", E2ETest) {
      Given("User is signed in as a pagination user")
      userIsSignedIn("", "pagination")
      And("User navigates to the Find a claim page")
      navigateToPage("", "Find a claim")
      When("User searches for claim 'NDRC-42'")
      userSearchesFor("NDRC-42")
      Then("User should see the heading 'Claim reference NDRC-42'")
      userShouldSeeHeading("", "", "Claim reference NDRC-42")
      And("User should see the following static text")
      userShouldSeeStaticText("static", Seq("Claim details: This claim has been closed"))
      And("User should see the following claim details")
      userShouldSeeClaimDetails(
        Map(
          "MRN"                          -> "MRN23014",
          "Local Reference Number (LRN)" -> "KWMREF1",
          "Claimant‘s EORI number"       -> "GB98765432101",
          "Claim type"                   -> "Overpayment (C285),Single declaration",
          "Claim submitted date"         -> "1 May 2020",
          "Claim decision date"          -> "1 May 2021",
          "Claim decision"               -> "Approved",
          "Claim amount requested"       -> "£900000.00",
          "Claimant‘s name"              -> "Claimant name",
          "Claimant‘s email address"     -> "Claimant email address"
        )
      )
    }

    Scenario("Search in progress claims using claim number", AcceptanceTest) {
      Given("User is signed in as a pagination user")
      userIsSignedIn("", "pagination")
      And("User navigates to the Find a claim page")
      navigateToPage("", "Find a claim")
      When("User searches for claim 'NDRC-1'")
      userSearchesFor("NDRC-1")
      Then("User should see the heading 'Claim reference NDRC-1'")
      userShouldSeeHeading("", "", "Claim reference NDRC-1")
      And("User should see the following static text")
      userShouldSeeStaticText("static", Seq("Claim details: This claim is open and being reviewed by HMRC."))
      And("User should see the following claim details")
      userShouldSeeClaimDetails(
        Map(
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
      )
    }

    Scenario("Search pending claims using claim number", AcceptanceTest) {
      Given("User is signed in as a pagination user")
      userIsSignedIn("", "pagination")
      And("User navigates to the Find a claim page")
      navigateToPage("", "Find a claim")
      When("User searches for claim 'NDRC-21'")
      userSearchesFor("NDRC-21")
      Then("User should see the heading 'Claim reference NDRC-21'")
      userShouldSeeHeading("", "", "Claim reference NDRC-21")
      And("User should see the following static text")
      userShouldSeeStaticText(
        "static",
        Seq(
          "Claim details: This claim requires additional information or documentation.",
          "Check the inbox of Claimant email address for missing documents and any requests for more information from your caseworker.",
          "If your claim was submitted online, upload your supporting documents.",
          "Valid file formats: Excel, JPG, PNG, PDF, CSV, TXT or Word.",
          "If your claim was submitted manually, you will need to send your supporting documents by post."
        )
      )
      And("User should see the following claim details")
      userShouldSeeClaimDetails(
        Map(
          "Local Reference Number (LRN)"      -> "KWMREF1",
          "Claimant‘s EORI number"            -> "GB98765432101",
          "Claim type"                        -> "Rejected goods (C&E1179),Multiple declarations",
          "Claim status"                      -> "Pending",
          "First MRN"                         -> "MRN23014",
          "Claim submitted date"              -> "1 May 2020",
          "Claim amount requested"            -> "£900000.00",
          "Claimant‘s name"                   -> "Claimant name",
          "Claimant‘s email address"          -> "Claimant email address",
          "Other MRNs included in this claim" -> "MRN00002,MRN00003,MRN00004,MRN00005,MRN00006,MRN00007,MRN00008,MRN00009,MRN000010"
        )
      )
    }
  }
}
