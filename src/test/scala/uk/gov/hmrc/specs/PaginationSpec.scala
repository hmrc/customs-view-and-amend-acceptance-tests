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
import uk.gov.hmrc.pages.ClaimsListPages.{userClicksOnPaginationLink, userEntersInQueryParameter, userShouldSeePaginationContent, userShouldSeePaginationResultsText}
import uk.gov.hmrc.pages.CommonPage.navigateToPage
import uk.gov.hmrc.pages.SignInPage.userIsSignedIn
import uk.gov.hmrc.selenium.webdriver.{Browser, ScreenshotOnFailure}
import uk.gov.hmrc.specs.tags.AcceptanceTest

class PaginationSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  Feature("Pagination") {

    Scenario("A user can see pagination for more than 20 claims", AcceptanceTest) {
      Given("User is signed in as a pagination user")
      userIsSignedIn("", "pagination")
      When("User navigates to the in progress claims list page")
      navigateToPage("", "in progress claims list")
      Then("User should see the following pagination content")
      userShouldSeePaginationContent("content", Seq("1", "2", "Next"))
      And("User should see the following pagination links")
      userShouldSeePaginationContent("links", Seq("1", "2", "Next"))
      And("User should see the following pagination results text")
      userShouldSeePaginationResultsText("Showing 1 to 20 of 40 claims")
    }

    Scenario("Navigate to pages using next link on pagination", AcceptanceTest) {
      Given("User is signed in as a pagination user")
      userIsSignedIn("", "pagination")
      And("User navigates to the in progress claims list page")
      navigateToPage("", "in progress claims list")
      When("User clicks on pagination link 'Next'")
      userClicksOnPaginationLink("Next")
      Then("User should see the following pagination results text")
      userShouldSeePaginationResultsText("Showing 21 to 40 of 40 claims")
      And("User should see the following pagination content")
      userShouldSeePaginationContent("content", Seq("Previous", "1", "2"))
      And("User should see the following pagination links")
      userShouldSeePaginationContent("links", Seq("Previous", "1", "2"))
    }

    Scenario("Navigate to pages using previous link on pagination", AcceptanceTest) {
      Given("User is signed in as a pagination user")
      userIsSignedIn("", "pagination")
      And("User navigates to the in progress claims list page")
      navigateToPage("", "in progress claims list")
      And("User clicks on pagination link '2'")
      userClicksOnPaginationLink("2")
      When("User clicks on pagination link 'Previous'")
      userClicksOnPaginationLink("Previous")
      Then("User should see the following pagination results text")
      userShouldSeePaginationResultsText("Showing 1 to 20 of 40 claims")
      And("User should see the following pagination content")
      userShouldSeePaginationContent("content", Seq("1", "2", "Next"))
      And("User should see the following pagination links")
      userShouldSeePaginationContent("links", Seq("1", "2", "Next"))
    }

    Scenario("Navigate to pages using pagination number links", AcceptanceTest) {
      Given("User is signed in as a pagination user")
      userIsSignedIn("", "pagination")
      And("User navigates to the in progress claims list page")
      navigateToPage("", "in progress claims list")
      When("User clicks on pagination link '2'")
      userClicksOnPaginationLink("2")
      Then("User should see the following pagination results text")
      userShouldSeePaginationResultsText("Showing 21 to 40 of 40 claims")
      And("User should see the following pagination links")
      userShouldSeePaginationContent("links", Seq("Previous", "1", "2"))
      When("User clicks on pagination link '1'")
      userClicksOnPaginationLink("1")
      Then("User should see the following pagination results text")
      userShouldSeePaginationResultsText("Showing 1 to 20 of 40 claims")
      And("User should see the following pagination links")
      userShouldSeePaginationContent("links", Seq("1", "2", "Next"))
    }

    Scenario("User enters invalid number or higher than available pages in query parameter", AcceptanceTest) {
      val examples = Table(
        ("pageNumber", "pagination result"),
        ("-1", "Showing 1 to 20 of 40 claims"),
        ("10", "Showing 21 to 40 of 40 claims")
      )
      forAll(examples) { (pageNumber, paginationResult) =>
        Given("User is signed in as a pagination user")
        userIsSignedIn("", "pagination")
        And("User navigates to the in progress claims list page")
        navigateToPage("", "in progress claims list")
        And(s"User enters $pageNumber in the query parameter")
        userEntersInQueryParameter(pageNumber)
        Then("User should see the following pagination results text")
        userShouldSeePaginationResultsText(paginationResult)
      }
    }
  }
}
