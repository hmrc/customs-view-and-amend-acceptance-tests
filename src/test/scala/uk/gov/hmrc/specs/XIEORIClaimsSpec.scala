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
import uk.gov.hmrc.pages.ClaimsListPages.userShouldSeeClaims
import uk.gov.hmrc.pages.CommonPage.{navigateToPage, pageTitleShouldBe, userClicksOn, userShouldSeeHeading}
import uk.gov.hmrc.pages.SignInPage.userIsSignedIn
import uk.gov.hmrc.pages.ViewAndAmendHomePage.userShouldSeeEoriDetails
import uk.gov.hmrc.selenium.webdriver.{Browser, ScreenshotOnFailure}
import uk.gov.hmrc.specs.tags.AcceptanceTest

class XIEORIClaimsSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  private val inProgressHeaders =
    Seq("Claim reference", "MRN", "Type of claim", "Claim submitted date")

  Feature("XI EORI Claims") {

    Scenario("View XIEORI claims in progress", AcceptanceTest) {
      Given("User is signed in as an openAnalysisClaim user")
      userIsSignedIn("", "openAnalysisClaim")
      And("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      And("User should see the eori details Tony Stark - GB00000000002 (XI00000000002)")
      userShouldSeeEoriDetails("Tony Stark - GB00000000002 (XI00000000002)")
      When("User clicks on 'View claims in progress'")
      userClicksOn("View claims in progress")
      Then("User should see the heading 'Claims in progress'")
      userShouldSeeHeading("", "", "Claims in progress")
      And("The page title should be 'Claims in progress - Claim back import duty and VAT - GOV.UK'")
      pageTitleShouldBe("Claims in progress - Claim back import duty and VAT - GOV.UK")
      And("User should see the following claims")
      val inProgressClaims = Seq(
        inProgressHeaders,
        Seq("NDRC-1002", "MRN23014", "Overpayment or rejected goods", "1 May 2020"),
        Seq("NDRC-1502", "MRN23014", "Overpayment or rejected goods", "1 May 2020")
      )
      inProgressClaims.foreach(row => info(s"- ${row.mkString(" | ")}"))
      userShouldSeeClaims(inProgressClaims)
    }

    Scenario("View XIEORI claims needing more information", AcceptanceTest) {
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
      val pendingClaims = Seq(
        inProgressHeaders,
        Seq("NDRC-1004", "MRN23014", "Overpayment or rejected goods", "1 May 2020"),
        Seq("NDRC-1504", "MRN23014", "Overpayment or rejected goods", "1 May 2020")
      )
      pendingClaims.foreach(row => info(s"- ${row.mkString(" | ")}"))
      userShouldSeeClaims(pendingClaims)
    }

    Scenario("View XIEORI closed claims", AcceptanceTest) {
      Given("User is signed in as a RejectedFailedValidationClaim user")
      userIsSignedIn("", "RejectedFailedValidationClaim")
      And("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      And("User should see the eori details Tony Stark - GB00000000006 (XI00000000006)")
      userShouldSeeEoriDetails("Tony Stark - GB00000000006 (XI00000000006)")
      When("User clicks on 'View closed claims'")
      userClicksOn("View closed claims")
      Then("User should see the heading 'Closed claims'")
      userShouldSeeHeading("", "", "Closed claims")
      And("The page title should be 'Closed claims - Claim back import duty and VAT - GOV.UK'")
      pageTitleShouldBe("Closed claims - Claim back import duty and VAT - GOV.UK")
    }
  }
}
