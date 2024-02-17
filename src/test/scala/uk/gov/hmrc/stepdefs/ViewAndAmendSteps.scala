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

package uk.gov.hmrc.stepdefs

import io.cucumber.datatable.DataTable
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import uk.gov.hmrc.pages.ClaimsListPages.claimsList
import uk.gov.hmrc.pages.ViewAndAmendHomePage.{cardDetails, eoriCompany, notificationBarLinks}
import uk.gov.hmrc.pages.{ClaimDetailsPage, ClaimSearchPage, ClaimsListPages, CustomsFinancialsWebPage}

import scala.collection.JavaConverters._

class ViewAndAmendSteps extends CustomsFinancialsWebPage {

  And("""^I should see the following notification bar links$""") { data: DataTable =>
    val expected = data.asScalaListOfStrings
    notificationBarLinks should be(expected)
  }

  And("""^I should see the eori details (.*)$""") { eoriDetails: String =>
    eoriCompany should be(eoriDetails)
  }

  And("""^I should see the following cards$""") { data: DataTable =>
    val expectedCardDetails = data.asScalaListOfLists
    cardDetails should be(expectedCardDetails)
  }

  And("""^I should see the following claims$""") { data: DataTable =>
    val expected = data.asScalaListOfLists
    val actual   = claimsList
    actual should be(expected)
  }

  Then("""^I should see the following claim details$""") { data: DataTable =>
    val expected = data.asMaps().asScala.toList.flatMap(_.asScala.toMap).toMap
    val actual   = ClaimDetailsPage.claimDetails
    actual should be(expected)
  }

  Then("""^I should not see pagination links$""") { () =>
    ClaimsListPages.paginationBlockPresence should be(true)
  }

  Then("""^I should see the following pagination (content|links)$""") { (linkOrContent: String, data: DataTable) =>
    val expected = data.asScalaListOfStrings
    val tagName  = linkOrContent match {
      case "content" => "li"
      case "links"   => "a"
    }
    ClaimsListPages.paginationBlockContent(tagName) should be(expected)
  }

  And("""^I should see the following pagination results text$""") { data: DataTable =>
    val expectedText = data.asScalaListOfStrings.head
    ClaimsListPages.paginationResultsText should be(expectedText)
  }

  When("""^I click on pagination link "(.*)"$""") { linkName: String =>
    ClaimsListPages.clickOnPaginationLink(linkName)
  }

  Then("""^I enter (.*) in the query parameter$""") { (pageNumber: String) =>
    val url = baseUrl + s"/claim-back-import-duty-vat/claims-status/claims-in-progress?page=$pageNumber"
    goTo(url)
  }

  And("""^I search for (claim|MRN) '(.*)'$""") { (_: String, caseNumber: String) =>
    ClaimSearchPage.enterClaim(caseNumber)
    ClaimSearchPage.submit()
  }

  And("""^I should see the table heading "(.*)"$""") { expectedTableHeading: String =>
    ClaimsListPages.tableCaption should be(expectedTableHeading)
  }
}
