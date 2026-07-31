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
import uk.gov.hmrc.pages.CommonPage.{errorSummaryTitleAndMessage, navigateToViewAndAmendHome, userClicksContinueOn, userClicksOn, userClicksUploadAnotherTypeOn, userIsPresentedWith, userIsPresentedWithErrorPage, userSelectsRadioButton, userShouldSeeHeading, userUploadsFile, userUploadsFileExpectingError}
import uk.gov.hmrc.pages.SignInPage.userIsSignedIn
import uk.gov.hmrc.pages.Constants
import uk.gov.hmrc.pages.{ChooseFileTypePage, ChooseFilesPage, ClaimSubmittedPage}
import uk.gov.hmrc.pages.Constants.UserType
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
      userIsSignedIn(UserType.Pagination)
      And("User navigates to the View and amend home page")
      navigateToViewAndAmendHome()
      And("User clicks on 'View claims needing more information'")
      userClicksOn("View claims needing more information")
      And("User clicks on 'NDRC-21'")
      userClicksOn("NDRC-21")
      Then("User should see the heading 'Claim reference NDRC-21'")
      userShouldSeeHeading("Claim reference NDRC-21")
      When("User clicks on 'Upload a file'")
      userClicksOn("Upload a file")
      Then("User is presented with the 'Choose File Type Page'")
      userIsPresentedWith(ChooseFileTypePage)
      And("User should see the heading 'Add supporting documents to your claim NDRC-21'")
      userShouldSeeHeading("Add supporting documents to your claim NDRC-21")
      When("User selects radio button 'Commercial invoice' on 'Choose File Type Page'")
      userSelectsRadioButton(Constants.FileType.CommercialInvoice)
      And("User clicks continue on 'Choose File Type Page'")
      userClicksContinueOn(ChooseFileTypePage)
      And("User is presented with the 'Choose Files Page' 'commercial invoice'")
      userIsPresentedWith(ChooseFilesPage, "commercial invoice")
      When("User uploads 'document.pdf' on 'Choose Files Page'")
      userUploadsFile("document.pdf", ChooseFilesPage, 1)
      And("User clicks 'Upload another type of document' on 'Choose Files Page'")
      userClicksUploadAnotherTypeOn(ChooseFilesPage)
      Then("User is presented with the 'Choose File Type Page'")
      userIsPresentedWith(ChooseFileTypePage)
      When("User selects radio button 'Substitute or diversion entry' on 'Choose File Type Page'")
      userSelectsRadioButton(Constants.FileType.SubstituteOrDiversionEntry)
      And("User clicks continue on 'Choose File Type Page'")
      userClicksContinueOn(ChooseFileTypePage)
      Then("User is presented with the 'Choose Files Page' 'substitute or diversion entry'")
      userIsPresentedWith(ChooseFilesPage, "substitute or diversion entry")
      When("User uploads 'image.png' on 'Choose Files Page'")
      userUploadsFile("image.png", ChooseFilesPage, 2)
      And("User clicks continue on 'Choose Files Page'")
      userClicksContinueOn(ChooseFilesPage)
      Then("User is presented with the 'Claim Submitted Page'")
      userIsPresentedWith(ClaimSubmittedPage)
    }

    Scenario("Pending claim submission by selecting multiple files", AcceptanceTest) {
      Given("User is signed in as a pagination user")
      userIsSignedIn(UserType.Pagination)
      And("User navigates to the View and amend home page")
      navigateToViewAndAmendHome()
      And("User clicks on 'View claims needing more information'")
      userClicksOn("View claims needing more information")
      And("User clicks on 'NDRC-21'")
      userClicksOn("NDRC-21")
      Then("User should see the heading 'Claim reference NDRC-21'")
      userShouldSeeHeading("Claim reference NDRC-21")
      When("User clicks on 'Upload a file'")
      userClicksOn("Upload a file")
      Then("User is presented with the 'Choose File Type Page'")
      userIsPresentedWith(ChooseFileTypePage)
      And("User should see the heading 'Add supporting documents to your claim NDRC-21'")
      userShouldSeeHeading("Add supporting documents to your claim NDRC-21")

      When("User selects radio button 'Commercial invoice' on 'Choose File Type Page'")
      userSelectsRadioButton(Constants.FileType.CommercialInvoice)
      And("User clicks continue on 'Choose File Type Page'")
      userClicksContinueOn(ChooseFileTypePage)
      And("User is presented with the 'Choose Files Page' 'commercial invoice'")
      userIsPresentedWith(ChooseFilesPage, "commercial invoice")
      When("User uploads 'document.pdf' on 'Choose Files Page'")
      userUploadsFile("document.pdf", ChooseFilesPage, 1)
      And("User clicks 'Upload another type of document' on 'Choose Files Page'")
      userClicksUploadAnotherTypeOn(ChooseFilesPage)

      Then("User is presented with the 'Choose File Type Page'")
      userIsPresentedWith(ChooseFileTypePage)
      When("User selects radio button 'Substitute or diversion entry' on 'Choose File Type Page'")
      userSelectsRadioButton(Constants.FileType.SubstituteOrDiversionEntry)
      And("User clicks continue on 'Choose File Type Page'")
      userClicksContinueOn(ChooseFileTypePage)
      Then("User is presented with the 'Choose Files Page' 'substitute or diversion entry'")
      userIsPresentedWith(ChooseFilesPage, "substitute or diversion entry")
      When("User uploads 'image.png' on 'Choose Files Page'")
      userUploadsFile("image.png", ChooseFilesPage, 2)
      And("User clicks 'Upload another type of document' on 'Choose Files Page'")
      userClicksUploadAnotherTypeOn(ChooseFilesPage)

      Then("User is presented with the 'Choose File Type Page'")
      userIsPresentedWith(ChooseFileTypePage)
      When("User selects radio button 'Packing list' on 'Choose File Type Page'")
      userSelectsRadioButton(Constants.FileType.PackingList)
      And("User clicks continue on 'Choose File Type Page'")
      userClicksContinueOn(ChooseFileTypePage)
      Then("User is presented with the 'Choose Files Page' 'packing list'")
      userIsPresentedWith(ChooseFilesPage, "packing list")
      When("User uploads 'image.jpg' on 'Choose Files Page'")
      userUploadsFile("image.jpg", ChooseFilesPage, 3)
      And("User clicks 'Upload another type of document' on 'Choose Files Page'")
      userClicksUploadAnotherTypeOn(ChooseFilesPage)

      Then("User is presented with the 'Choose File Type Page'")
      userIsPresentedWith(ChooseFileTypePage)
      When("User selects radio button 'Bill of lading' on 'Choose File Type Page'")
      userSelectsRadioButton(Constants.FileType.BillOfLading)
      And("User clicks continue on 'Choose File Type Page'")
      userClicksContinueOn(ChooseFileTypePage)
      Then("User is presented with the 'Choose Files Page' 'bill of lading'")
      userIsPresentedWith(ChooseFilesPage, "bill of lading")
      When("User uploads 'image.jpeg' on 'Choose Files Page'")
      userUploadsFile("image.jpeg", ChooseFilesPage, 4)
      And("User clicks continue on 'Choose Files Page'")
      userClicksContinueOn(ChooseFilesPage)

      Then("User is presented with the 'Claim Submitted Page'")
      userIsPresentedWith(ClaimSubmittedPage)
    }

    Scenario("check error Messages for during Pending claim submission", AcceptanceTest) {
      Given("User is signed in as a pagination user")
      userIsSignedIn(UserType.Pagination)
      And("User navigates to the View and amend home page")
      navigateToViewAndAmendHome()
      And("User clicks on 'View claims needing more information'")
      userClicksOn("View claims needing more information")
      And("User clicks on 'NDRC-21'")
      userClicksOn("NDRC-21")
      Then("User should see the heading 'Claim reference NDRC-21'")
      userShouldSeeHeading("Claim reference NDRC-21")
      When("User clicks on 'Upload a file'")
      userClicksOn("Upload a file")
      Then("User is presented with the 'Choose File Type Page'")
      userIsPresentedWith(ChooseFileTypePage)
      And("User should see the heading 'Add supporting documents to your claim NDRC-21'")
      userShouldSeeHeading("Add supporting documents to your claim NDRC-21")
      And("User clicks continue on 'Choose File Type Page'")
      userClicksContinueOn(ChooseFileTypePage)
      Then("User is presented with the 'Choose File Type Page' error page")
      userIsPresentedWithErrorPage(ChooseFileTypePage)
      And(
        "The error summary title is 'There is a problem' and the error message is 'Select which type of document you are uploading'"
      )
      errorSummaryTitleAndMessage("There is a problem", "Select which type of document you are uploading")

      When("User selects radio button 'Commercial invoice' on 'Choose File Type Page'")
      userSelectsRadioButton(Constants.FileType.CommercialInvoice)
      And("User clicks continue on 'Choose File Type Page'")
      userClicksContinueOn(ChooseFileTypePage)
      And("User is presented with the 'Choose Files Page' 'commercial invoice'")
      userIsPresentedWith(ChooseFilesPage, "commercial invoice")

      When("User uploads 'document.pdf' on 'Choose Files Page'")
      userUploadsFile("document.pdf", ChooseFilesPage, 1)
      And("User uploads the same 'document.pdf' file again on 'Choose Files Page'")
      userUploadsFileExpectingError("document.pdf", ChooseFilesPage)
      Then("User is presented with the 'Choose Files Page' 'commercial invoice' error page")
      userIsPresentedWithErrorPage(ChooseFilesPage, "commercial invoice")
      And(
        "The error summary title is 'There is a problem' and the error message is 'The selected file has already been uploaded'"
      )
      errorSummaryTitleAndMessage(
        "There is a problem",
        "The selected file has already been uploaded"
      )

      When("User clicks 'Upload another type of document' on 'Choose Files Page'")
      userClicksUploadAnotherTypeOn(ChooseFilesPage)
      Then("User is presented with the 'Choose File Type Page'")
      userIsPresentedWith(ChooseFileTypePage)
      When("User selects radio button 'Substitute or diversion entry' on 'Choose File Type Page'")
      userSelectsRadioButton(Constants.FileType.SubstituteOrDiversionEntry)
      And("User clicks continue on 'Choose File Type Page'")
      userClicksContinueOn(ChooseFileTypePage)
      Then("User is presented with the 'Choose Files Page' 'substitute or diversion entry'")
      userIsPresentedWith(ChooseFilesPage, "substitute or diversion entry")
      When("User uploads 'image.png' on 'Choose Files Page'")
      userUploadsFile("image.png", ChooseFilesPage, 2)
      And("User clicks continue on 'Choose Files Page'")
      userClicksContinueOn(ChooseFilesPage)
      Then("User is presented with the 'Claim Submitted Page'")
      userIsPresentedWith(ClaimSubmittedPage)
    }
  }
}
