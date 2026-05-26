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
import uk.gov.hmrc.pages.ClaimsListPages.userShouldSeeClaims
import uk.gov.hmrc.pages.CommonPage.{navigateToPage, userClicksBackLink, userClicksOn, userShouldSeeHeading}
import uk.gov.hmrc.pages.SignInPage.userIsSignedIn
import uk.gov.hmrc.selenium.webdriver.{Browser, ScreenshotOnFailure}
import uk.gov.hmrc.specs.tags.AcceptanceTest

class NDRCClaimStatusSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  private val inProgressMultiHeaders =
    Seq("Claim reference", "MRN", "Type of claim", "Claim submitted date")
  private val closedMultiHeaders     =
    Seq("Claim reference", "MRN", "Type of claim", "Claim decision date", "Claim decision")

  Feature("Display backend claims status to in progress, pending and closed categories") {

    Scenario("Display in-progress status for multiple declarations claims", AcceptanceTest) {
      val examples = Table(
        ("user", "claim ref", "claim type"),
        ("openClaim", "NDRC-1001", "Rejected goods (C&E1179),Multiple declarations"),
        ("PendingApprovalClaim", "NDRC-1003", "Rejected goods (C&E1179),Multiple declarations"),
        ("PausedClaim", "NDRC-1009", "Rejected goods (C&E1179),Multiple declarations"),
        ("PendingPaymentConfirmationClaim", "NDRC-10013", "Rejected goods (C&E1179),Multiple declarations"),
        ("ApprovedClaim", "NDRC-10017", "Rejected goods (C&E1179),Multiple declarations"),
        ("ReworkPaymentDetailsClaim", "NDRC-10019", "Rejected goods (C&E1179),Multiple declarations")
      )
      forAll(examples) { (user, claimRef, claimType) =>
        Given(s"User is signed in as a $user user")
        userIsSignedIn("", user)
        And("User navigates to the View and amend home page")
        navigateToPage("", "View and amend home")
        When("User clicks on 'View claims in progress'")
        userClicksOn("View claims in progress")
        Then("User should see the heading 'Claims in progress'")
        userShouldSeeHeading("", "", "Claims in progress")
        And("User should see the following claims")
        userShouldSeeClaims(
          Seq(
            inProgressMultiHeaders,
            Seq(claimRef, "MRN23014", "Overpayment or rejected goods", "1 May 2020")
          )
        )
        When(s"User clicks on '$claimRef'")
        userClicksOn(claimRef)
        Then("User should see the following claim details")
        userShouldSeeClaimDetails(
          Map(
            "Local Reference Number (LRN)"      -> "KWMREF1",
            "Claimant‘s EORI number"            -> "GB98765432101",
            "Claim type"                        -> claimType,
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
    }

    Scenario("Display in-progress status for single declaration claims", AcceptanceTest) {
      val examples = Table(
        ("user", "claim ref1", "claim ref2", "claim type", "EORI number1", "EORI number2"),
        (
          "openAnalysisClaim",
          "NDRC-1002",
          "NDRC-1502",
          "Overpayment (C285),Single declaration",
          "GB98765432101",
          "XI98745632102"
        ),
        (
          "OpenReworkClaim",
          "NDRC-1008",
          "NDRC-1508",
          "Overpayment (C285),Single declaration",
          "GB98765432101",
          "XI98745632102"
        ),
        (
          "PendingDecisionLetterClaim",
          "NDRC-10016",
          "NDRC-10516",
          "Overpayment (C285),Single declaration",
          "GB98765432101",
          "XI98745632102"
        ),
        (
          "AnalysisReworkClaim",
          "NDRC-10018",
          "NDRC-10518",
          "Overpayment (C285),Single declaration",
          "GB98765432101",
          "XI98745632102"
        ),
        (
          "PendingComplianceRecommendationClaim",
          "NDRC-10022",
          "NDRC-10522",
          "Overpayment (C285),Single declaration",
          "GB98765432101",
          "XI98745632102"
        ),
        (
          "PendingComplianceCheckClaim",
          "NDRC-10024",
          "NDRC-10524",
          "Overpayment (C285),Single declaration",
          "GB98765432101",
          "XI98745632102"
        )
      )
      forAll(examples) { (user, claimRef1, claimRef2, claimType, eori1, eori2) =>
        Given(s"User is signed in as a $user user")
        userIsSignedIn("", user)
        And("User navigates to the View and amend home page")
        navigateToPage("", "View and amend home")
        When("User clicks on 'View claims in progress'")
        userClicksOn("View claims in progress")
        Then("User should see the heading 'Claims in progress'")
        userShouldSeeHeading("", "", "Claims in progress")
        And("User should see the following claims")
        userShouldSeeClaims(
          Seq(
            inProgressMultiHeaders,
            Seq(claimRef1, "MRN23014", "Overpayment or rejected goods", "1 May 2020"),
            Seq(claimRef2, "MRN23014", "Overpayment or rejected goods", "1 May 2020")
          )
        )
        When(s"User clicks on '$claimRef1'")
        userClicksOn(claimRef1)
        Then("User should see the following claim details")
        userShouldSeeClaimDetails(singleInProgressDetails(eori1, claimType))
        When("User clicks on back link to previous page")
        userClicksBackLink()
        Then("User should see the heading 'Claims in progress'")
        userShouldSeeHeading("", "", "Claims in progress")
        When(s"User clicks on '$claimRef2'")
        userClicksOn(claimRef2)
        Then("User should see the following claim details")
        userShouldSeeClaimDetails(singleInProgressDetails(eori2, claimType))
      }
    }

    Scenario("Display pending status for multiple declarations claims", AcceptanceTest) {
      val examples = Table(
        ("user", "claim ref", "claim type"),
        ("RTBHsentClaim", "NDRC-10011", "Rejected goods (C&E1179),Multiple declarations"),
        ("ReplyToRTBHClaim", "NDRC-10021", "Rejected goods (C&E1179),Multiple declarations"),
        ("PendingComplianceCheckQueryClaim", "NDRC-10023", "Rejected goods (C&E1179),Multiple declarations")
      )
      forAll(examples) { (user, claimRef, claimType) =>
        Given(s"User is signed in as a $user user")
        userIsSignedIn("", user)
        And("User navigates to the View and amend home page")
        navigateToPage("", "View and amend home")
        When("User clicks on 'View claims needing more information'")
        userClicksOn("View claims needing more information")
        Then("User should see the heading 'Claims needing more information'")
        userShouldSeeHeading("", "", "Claims needing more information")
        And("User should see the following claims")
        userShouldSeeClaims(
          Seq(
            inProgressMultiHeaders,
            Seq(claimRef, "MRN23014", "Overpayment or rejected goods", "1 May 2020")
          )
        )
        When(s"User clicks on '$claimRef'")
        userClicksOn(claimRef)
        Then("User should see the following claim details")
        userShouldSeeClaimDetails(
          Map(
            "Local Reference Number (LRN)"      -> "KWMREF1",
            "Claimant‘s EORI number"            -> "GB98765432101",
            "Claim type"                        -> claimType,
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

    Scenario("Display pending status for single declaration claims", AcceptanceTest) {
      val examples = Table(
        ("user", "claim ref1", "claim ref2", "claim type"),
        ("PendingQueriedClaim", "NDRC-1004", "NDRC-1504", "Overpayment (C285),Single declaration")
      )
      forAll(examples) { (user, claimRef1, claimRef2, claimType) =>
        Given(s"User is signed in as a $user user")
        userIsSignedIn("", user)
        And("User navigates to the View and amend home page")
        navigateToPage("", "View and amend home")
        When("User clicks on 'View claims needing more information'")
        userClicksOn("View claims needing more information")
        Then("User should see the heading 'Claims needing more information'")
        userShouldSeeHeading("", "", "Claims needing more information")
        And("User should see the following claims")
        userShouldSeeClaims(
          Seq(
            inProgressMultiHeaders,
            Seq(claimRef1, "MRN23014", "Overpayment or rejected goods", "1 May 2020"),
            Seq(claimRef2, "MRN23014", "Overpayment or rejected goods", "1 May 2020")
          )
        )
        When(s"User clicks on '$claimRef1'")
        userClicksOn(claimRef1)
        Then("User should see the following claim details")
        userShouldSeeClaimDetails(singlePendingDetails("GB98765432101", claimType))
        When("User clicks on back link to previous page")
        userClicksBackLink()
        Then("User should see the heading 'Claims needing more information'")
        userShouldSeeHeading("", "", "Claims needing more information")
        When(s"User clicks on '$claimRef2'")
        userClicksOn(claimRef2)
        Then("User should see the following claim details")
        userShouldSeeClaimDetails(singlePendingDetails("XI98745632102", claimType))
      }
    }

    Scenario("Display closed status for multiple declarations claims", AcceptanceTest) {
      val examples = Table(
        ("user", "claim ref", "claim type", "claim decision"),
        ("ResolvedWithdrawnClaim", "NDRC-1005", "Rejected goods (C&E1179),Multiple declarations", "Withdrawn"),
        ("ResolvedRejectedClaim", "NDRC-1007", "Rejected goods (C&E1179),Multiple declarations", "Rejected"),
        (
          "ResolvedPartialRefusedClaim",
          "NDRC-10015",
          "Rejected goods (C&E1179),Multiple declarations",
          "Partial Refused"
        )
      )
      forAll(examples) { (user, claimRef, claimType, claimDecision) =>
        Given(s"User is signed in as a $user user")
        userIsSignedIn("", user)
        And("User navigates to the View and amend home page")
        navigateToPage("", "View and amend home")
        When("User clicks on 'View closed claims'")
        userClicksOn("View closed claims")
        Then("User should see the heading 'Closed claims'")
        userShouldSeeHeading("", "", "Closed claims")
        And("User should see the following claims")
        userShouldSeeClaims(
          Seq(
            closedMultiHeaders,
            Seq(claimRef, "MRN23014", "Overpayment or rejected goods", "1 May 2021", claimDecision)
          )
        )
        When(s"User clicks on '$claimRef'")
        userClicksOn(claimRef)
        Then("User should see the following claim details")
        userShouldSeeClaimDetails(
          Map(
            "Local Reference Number (LRN)"      -> "KWMREF1",
            "Claimant‘s EORI number"            -> "GB98765432101",
            "Claim type"                        -> claimType,
            "Claim decision"                    -> claimDecision,
            "First MRN"                         -> "MRN23014",
            "Claim submitted date"              -> "1 May 2020",
            "Claim decision date"               -> "1 May 2021",
            "Claim amount requested"            -> "£900000.00",
            "Claimant‘s name"                   -> "Claimant name",
            "Claimant‘s email address"          -> "Claimant email address",
            "Other MRNs included in this claim" -> "MRN00002,MRN00003,MRN00004,MRN00005,MRN00006,MRN00007,MRN00008,MRN00009,MRN000010"
          )
        )
      }
    }

    Scenario("Display closed status for single declaration claims", AcceptanceTest) {
      val examples = Table(
        ("user", "claim ref1", "claim ref2", "claim type", "claim decision"),
        ("ResolvedNoReplyClaim", "NDRC-10010", "NDRC-10510", "Overpayment (C285),Single declaration", "No Reply"),
        ("ResolvedRefusedClaim", "NDRC-10012", "NDRC-10512", "Overpayment (C285),Single declaration", "Refused"),
        ("ResolvedApprovedClaim", "NDRC-10014", "NDRC-10514", "Overpayment (C285),Single declaration", "Approved")
      )
      forAll(examples) { (user, claimRef1, claimRef2, claimType, claimDecision) =>
        Given(s"User is signed in as a $user user")
        userIsSignedIn("", user)
        And("User navigates to the View and amend home page")
        navigateToPage("", "View and amend home")
        When("User clicks on 'View closed claims'")
        userClicksOn("View closed claims")
        Then("User should see the heading 'Closed claims'")
        userShouldSeeHeading("", "", "Closed claims")
        And("User should see the following claims")
        userShouldSeeClaims(
          Seq(
            closedMultiHeaders,
            Seq(claimRef1, "MRN23014", "Overpayment or rejected goods", "1 May 2021", claimDecision),
            Seq(claimRef2, "MRN23014", "Overpayment or rejected goods", "1 May 2021", claimDecision)
          )
        )
        When(s"User clicks on '$claimRef1'")
        userClicksOn(claimRef1)
        Then("User should see the following claim details")
        userShouldSeeClaimDetails(singleClosedDetails("GB98765432101", claimType, claimDecision))
        When("User clicks on back link to previous page")
        userClicksBackLink()
        Then("User should see the heading 'Closed claims'")
        userShouldSeeHeading("", "", "Closed claims")
        When(s"User clicks on '$claimRef2'")
        userClicksOn(claimRef2)
        Then("User should see the following claim details")
        userShouldSeeClaimDetails(singleClosedDetails("XI98745632102", claimType, claimDecision))
      }
    }
  }

  private def singleInProgressDetails(eori: String, claimType: String): Map[String, String] =
    Map(
      "MRN"                          -> "MRN23014",
      "Local Reference Number (LRN)" -> "KWMREF1",
      "Claimant‘s EORI number"       -> eori,
      "Claim type"                   -> claimType,
      "Claim status"                 -> "In progress",
      "Claim submitted date"         -> "1 May 2020",
      "Claim amount requested"       -> "£900000.00",
      "Claimant‘s name"              -> "Claimant name",
      "Claimant‘s email address"     -> "Claimant email address"
    )

  private def singlePendingDetails(eori: String, claimType: String): Map[String, String] =
    Map(
      "MRN"                          -> "MRN23014",
      "Local Reference Number (LRN)" -> "KWMREF1",
      "Claimant‘s EORI number"       -> eori,
      "Claim type"                   -> claimType,
      "Claim status"                 -> "Pending",
      "Claim submitted date"         -> "1 May 2020",
      "Claim amount requested"       -> "£900000.00",
      "Claimant‘s name"              -> "Claimant name",
      "Claimant‘s email address"     -> "Claimant email address"
    )

  private def singleClosedDetails(eori: String, claimType: String, claimDecision: String): Map[String, String] =
    Map(
      "MRN"                          -> "MRN23014",
      "Local Reference Number (LRN)" -> "KWMREF1",
      "Claimant‘s EORI number"       -> eori,
      "Claim type"                   -> claimType,
      "Claim decision"               -> claimDecision,
      "Claim submitted date"         -> "1 May 2020",
      "Claim decision date"          -> "1 May 2021",
      "Claim amount requested"       -> "£900000.00",
      "Claimant‘s name"              -> "Claimant name",
      "Claimant‘s email address"     -> "Claimant email address"
    )
}
