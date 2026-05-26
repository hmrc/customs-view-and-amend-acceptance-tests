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
import uk.gov.hmrc.pages.CommonPage.{errorSummaryTitleAndMessage, navigateToPage, userClicksContinueIfOn, userClicksContinueOn, userClicksOn, userIsPresentedWith, userIsPresentedWithErrorPage, userSelectsRadioButton, userShouldSeeHeading, userUploadsFile}
import uk.gov.hmrc.pages.SignInPage.userIsSignedIn
import uk.gov.hmrc.selenium.webdriver.{Browser, ScreenshotOnFailure}
import uk.gov.hmrc.specs.tags.AcceptanceTest

class PendingClaimSubmissionSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  Feature("Pending claim submission by adding additional document") {

    Scenario("Pending claim submission by adding commercial invoice document", AcceptanceTest) {
      Given("User is signed in as a pagination user")
      userIsSignedIn("", "pagination")
      And("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      And("User clicks on 'View claims needing more information'")
      userClicksOn("View claims needing more information")
      And("User clicks on 'NDRC-21'")
      userClicksOn("NDRC-21")
      Then("User should see the heading 'Claim reference NDRC-21'")
      userShouldSeeHeading("", "", "Claim reference NDRC-21")
      When("User clicks on 'Upload a file'")
      userClicksOn("Upload a file")
      Then("User is presented with the 'Choose File Type Page'")
      userIsPresentedWith("Choose File Type Page")
      And("User should see the heading 'Add supporting documents to your claim NDRC-21'")
      userShouldSeeHeading("", "", "Add supporting documents to your claim NDRC-21")
      When("User selects radio button 'Commercial invoice' on 'Choose File Type Page'")
      userSelectsRadioButton("Commercial invoice", "Choose File Type Page")
      And("User clicks continue on 'Choose File Type Page'")
      userClicksContinueOn("Choose File Type Page")
      And("User is presented with the 'Choose Files Page' 'commercial invoice'")
      userIsPresentedWith("Choose Files Page", "commercial invoice")
      When("User uploads a 1 'document.pdf' file on 'Choose Files Page'")
      userUploadsFile(1, "document.pdf", "Choose Files Page")
      And("User selects radio button 'Yes' on 'Choose Files Page'")
      userSelectsRadioButton("Yes", "Choose Files Page")
      And("User clicks continue if on 'Choose Files Page'")
      userClicksContinueIfOn("Choose Files Page")
      Then("User is presented with the 'Choose File Type Page'")
      userIsPresentedWith("Choose File Type Page")
      When("User selects radio button 'Substitute or diversion entry' on 'Choose File Type Page'")
      userSelectsRadioButton("Substitute or diversion entry", "Choose File Type Page")
      And("User clicks continue on 'Choose File Type Page'")
      userClicksContinueOn("Choose File Type Page")
      Then("User is presented with the 'Choose Files Page' 'substitute or diversion entry'")
      userIsPresentedWith("Choose Files Page", "substitute or diversion entry")
      When("User uploads a 2 'image.png' file on 'Choose Files Page'")
      userUploadsFile(2, "image.png", "Choose Files Page")
      And("User selects radio button 'No' on 'Choose Files Page'")
      userSelectsRadioButton("No", "Choose Files Page")
      And("User clicks continue if on 'Choose Files Page'")
      userClicksContinueIfOn("Choose Files Page")
      Then("User is presented with the 'Claim Submitted Page'")
      userIsPresentedWith("Claim Submitted Page")
    }

    Scenario("Pending claim submission by selecting multiple files", AcceptanceTest) {
      Given("User is signed in as a pagination user")
      userIsSignedIn("", "pagination")
      And("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      And("User clicks on 'View claims needing more information'")
      userClicksOn("View claims needing more information")
      And("User clicks on 'NDRC-21'")
      userClicksOn("NDRC-21")
      Then("User should see the heading 'Claim reference NDRC-21'")
      userShouldSeeHeading("", "", "Claim reference NDRC-21")
      When("User clicks on 'Upload a file'")
      userClicksOn("Upload a file")
      Then("User is presented with the 'Choose File Type Page'")
      userIsPresentedWith("Choose File Type Page")
      And("User should see the heading 'Add supporting documents to your claim NDRC-21'")
      userShouldSeeHeading("", "", "Add supporting documents to your claim NDRC-21")

      When("User selects radio button 'Commercial invoice' on 'Choose File Type Page'")
      userSelectsRadioButton("Commercial invoice", "Choose File Type Page")
      And("User clicks continue on 'Choose File Type Page'")
      userClicksContinueOn("Choose File Type Page")
      And("User is presented with the 'Choose Files Page' 'commercial invoice'")
      userIsPresentedWith("Choose Files Page", "commercial invoice")
      When("User uploads a 1 'document.pdf' file on 'Choose Files Page'")
      userUploadsFile(1, "document.pdf", "Choose Files Page")
      And("User selects radio button 'Yes' on 'Choose Files Page'")
      userSelectsRadioButton("Yes", "Choose Files Page")
      And("User clicks continue if on 'Choose Files Page'")
      userClicksContinueIfOn("Choose Files Page")

      Then("User is presented with the 'Choose File Type Page'")
      userIsPresentedWith("Choose File Type Page")
      When("User selects radio button 'Substitute or diversion entry' on 'Choose File Type Page'")
      userSelectsRadioButton("Substitute or diversion entry", "Choose File Type Page")
      And("User clicks continue on 'Choose File Type Page'")
      userClicksContinueOn("Choose File Type Page")
      Then("User is presented with the 'Choose Files Page' 'substitute or diversion entry'")
      userIsPresentedWith("Choose Files Page", "substitute or diversion entry")
      When("User uploads a 2 'image.png' file on 'Choose Files Page'")
      userUploadsFile(2, "image.png", "Choose Files Page")
      And("User selects radio button 'Yes' on 'Choose Files Page'")
      userSelectsRadioButton("Yes", "Choose Files Page")
      And("User clicks continue if on 'Choose Files Page'")
      userClicksContinueIfOn("Choose Files Page")

      Then("User is presented with the 'Choose File Type Page'")
      userIsPresentedWith("Choose File Type Page")
      When("User selects radio button 'Packing list' on 'Choose File Type Page'")
      userSelectsRadioButton("Packing list", "Choose File Type Page")
      And("User clicks continue on 'Choose File Type Page'")
      userClicksContinueOn("Choose File Type Page")
      Then("User is presented with the 'Choose Files Page' 'packing list'")
      userIsPresentedWith("Choose Files Page", "packing list")
      When("User uploads a 3 'image.jpg' file on 'Choose Files Page'")
      userUploadsFile(3, "image.jpg", "Choose Files Page")
      And("User selects radio button 'Yes' on 'Choose Files Page'")
      userSelectsRadioButton("Yes", "Choose Files Page")
      And("User clicks continue if on 'Choose Files Page'")
      userClicksContinueIfOn("Choose Files Page")

      Then("User is presented with the 'Choose File Type Page'")
      userIsPresentedWith("Choose File Type Page")
      When("User selects radio button 'Bill of lading' on 'Choose File Type Page'")
      userSelectsRadioButton("Bill of lading", "Choose File Type Page")
      And("User clicks continue on 'Choose File Type Page'")
      userClicksContinueOn("Choose File Type Page")
      Then("User is presented with the 'Choose Files Page' 'bill of lading'")
      userIsPresentedWith("Choose Files Page", "bill of lading")
      When("User uploads a 4 'image.jpeg' file on 'Choose Files Page'")
      userUploadsFile(4, "image.jpeg", "Choose Files Page")
      And("User selects radio button 'No' on 'Choose Files Page'")
      userSelectsRadioButton("No", "Choose Files Page")
      And("User clicks continue if on 'Choose Files Page'")
      userClicksContinueIfOn("Choose Files Page")

      Then("User is presented with the 'Claim Submitted Page'")
      userIsPresentedWith("Claim Submitted Page")
    }

    Scenario("check error Messages for during Pending claim submission", AcceptanceTest) {
      Given("User is signed in as a pagination user")
      userIsSignedIn("", "pagination")
      And("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      And("User clicks on 'View claims needing more information'")
      userClicksOn("View claims needing more information")
      And("User clicks on 'NDRC-21'")
      userClicksOn("NDRC-21")
      Then("User should see the heading 'Claim reference NDRC-21'")
      userShouldSeeHeading("", "", "Claim reference NDRC-21")
      When("User clicks on 'Upload a file'")
      userClicksOn("Upload a file")
      Then("User is presented with the 'Choose File Type Page'")
      userIsPresentedWith("Choose File Type Page")
      And("User should see the heading 'Add supporting documents to your claim NDRC-21'")
      userShouldSeeHeading("", "", "Add supporting documents to your claim NDRC-21")
      And("User clicks continue on 'Choose File Type Page'")
      userClicksContinueOn("Choose File Type Page")
      Then("User is presented with the 'Choose File Type Page' error page")
      userIsPresentedWithErrorPage("Choose File Type Page")
      And(
        "The error summary title is 'There is a problem' and the error message is 'Select which type of document you are uploading'"
      )
      errorSummaryTitleAndMessage("There is a problem", "Select which type of document you are uploading")

      When("User selects radio button 'Commercial invoice' on 'Choose File Type Page'")
      userSelectsRadioButton("Commercial invoice", "Choose File Type Page")
      And("User clicks continue on 'Choose File Type Page'")
      userClicksContinueOn("Choose File Type Page")
      And("User is presented with the 'Choose Files Page' 'commercial invoice'")
      userIsPresentedWith("Choose Files Page", "commercial invoice")
      And("User clicks continue on 'Choose Files Page'")
      userClicksContinueOn("Choose Files Page")
      And("User is presented with the 'Choose Files Page' 'commercial invoice' error page")
      userIsPresentedWithErrorPage("Choose Files Page", "commercial invoice")
      And(
        "The error summary title is 'There is a problem' and the error message is 'Select yes to add a different type of supporting document to your claim'"
      )
      errorSummaryTitleAndMessage(
        "There is a problem",
        "Select yes to add a different type of supporting document to your claim"
      )

      When("User uploads a 1 'document.pdf' file on 'Choose Files Page'")
      userUploadsFile(1, "document.pdf", "Choose Files Page")
      And("User selects radio button 'Yes' on 'Choose Files Page'")
      userSelectsRadioButton("Yes", "Choose Files Page")
      And("User clicks continue if on 'Choose Files Page'")
      userClicksContinueIfOn("Choose Files Page")
      Then("User is presented with the 'Choose File Type Page'")
      userIsPresentedWith("Choose File Type Page")
      When("User selects radio button 'Substitute or diversion entry' on 'Choose File Type Page'")
      userSelectsRadioButton("Substitute or diversion entry", "Choose File Type Page")
      And("User clicks continue on 'Choose File Type Page'")
      userClicksContinueOn("Choose File Type Page")
      Then("User is presented with the 'Choose Files Page' 'substitute or diversion entry'")
      userIsPresentedWith("Choose Files Page", "substitute or diversion entry")
      When("User uploads a 2 'image.png' file on 'Choose Files Page'")
      userUploadsFile(2, "image.png", "Choose Files Page")
      And("User selects radio button 'No' on 'Choose Files Page'")
      userSelectsRadioButton("No", "Choose Files Page")
      And("User clicks continue if on 'Choose Files Page'")
      userClicksContinueIfOn("Choose Files Page")
      Then("User is presented with the 'Claim Submitted Page'")
      userIsPresentedWith("Claim Submitted Page")
    }
  }
}
