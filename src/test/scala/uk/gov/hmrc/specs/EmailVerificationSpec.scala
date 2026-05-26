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
import uk.gov.hmrc.pages.CommonPage.{customsDataStoreIsEmpty, emailIsUndeliverable, navigateToPage, pageTitleShouldBe, theUrlIsCorrect, userHasVerifiedEmailInDataStore, userIsRedirectedToPage, userShouldSeeHeading, userShouldSeeLinkTo, userShouldSeeStaticText, userShouldSeeTextInBulletPoints}
import uk.gov.hmrc.pages.SignInPage.userIsSignedIn
import uk.gov.hmrc.selenium.webdriver.{Browser, ScreenshotOnFailure}
import uk.gov.hmrc.specs.tags.AcceptanceTest

class EmailVerificationSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  Feature("Email Verification") {

    Scenario("User without a verified email address is redirected to email verification page", AcceptanceTest) {
      Given("User is signed in as an unverifiedUser user")
      userIsSignedIn("", "unverifiedUser")
      When("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      Then(
        "User is redirected to the 'You need to verify the registered CDS email address - Claim back import duty and VAT - GOV.UK' page"
      )
      userIsRedirectedToPage(
        "You need to verify the registered CDS email address - Claim back import duty and VAT - GOV.UK"
      )
      And("User should see the heading 'You need to verify the registered CDS email address'")
      userShouldSeeHeading("", "", "You need to verify the registered CDS email address")
      And("The 'Verify or change email address' url is correct")
      theUrlIsCorrect("Verify or change email address")
      And("User should see the following static text")
      userShouldSeeStaticText(
        "static",
        Seq(
          "This is the email address your organisation has registered for the Customs Declaration Service (CDS). You need to verify this email address or change it. You can verify the email instantly provided you have access to the email account.",
          "This will be the only email address we use for:"
        )
      )
      And("User should see the following text in bullet points")
      userShouldSeeTextInBulletPoints(
        Seq(
          "updates on changes to the Customs Declaration Service",
          "urgent updates about goods in customs",
          "some financial notifications, including Direct Debit notices and VAT"
        )
      )
    }

    Scenario("Display undeliverable email page", AcceptanceTest) {
      Given("The customs data store is empty")
      customsDataStoreIsEmpty()
      And("The user has a verified email address in the data store")
      userHasVerifiedEmailInDataStore()
      And("The email is undeliverable")
      emailIsUndeliverable()
      And("User signs in as an undeliverableEmail user")
      userIsSignedIn("", "undeliverableEmail")
      When("User navigates to the View and amend home page")
      navigateToPage("", "View and amend home")
      Then(
        "The page title should be 'There‘s a problem with the CDS registered email address - Claim back import duty and VAT - GOV.UK'"
      )
      pageTitleShouldBe(
        "There‘s a problem with the CDS registered email address - Claim back import duty and VAT - GOV.UK"
      )
      And("User should see the heading 'There‘s a problem with the CDS registered email address'")
      userShouldSeeHeading("", "", "There‘s a problem with the CDS registered email address")
      And("User should see the following static text")
      userShouldSeeStaticText(
        "static",
        Seq(
          "We tried to send you an email but it could not be delivered. This could be because the inbox is full, or due to a technical problem with your email provider.",
          "This is the email address your organisation has registered for the Customs Declaration Service (CDS). You need to verify this email address or change it. You can verify the email instantly provided you have access to the email account.",
          "This will be the only email address we use for:"
        )
      )
      And("User should see the following text in bullet points")
      userShouldSeeTextInBulletPoints(
        Seq(
          "updates on changes to the Customs Declaration Service",
          "urgent updates about goods in customs",
          "some financial notifications, including Direct Debit notices and VAT"
        )
      )
      And("User should see a link to 'Verify or change email address'")
      userShouldSeeLinkTo("", "Verify or change email address")
      And("The 'Verify or change email address' url is correct")
      theUrlIsCorrect("Verify or change email address")
    }
  }
}
