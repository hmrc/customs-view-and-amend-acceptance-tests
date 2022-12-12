/*
 * Copyright 2022 HM Revenue & Customs
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

package uk.gov.hmrc.pages

import org.openqa.selenium.By

import scala.collection.JavaConverters._

object ClaimsListPages extends CustomsFinancialsWebPage {

  val inProgressClaimsPageUrl = baseUrl + "/claim-back-import-duty-vat/claims-status/claims-in-progress"

  def tableCaption = webDriver.findElement(By.tagName("caption")).getText.trim

  def claimsList = webDriver
    .findElement(By.tagName("table"))
    .findElements(By.tagName("tr"))
    .asScala
    .map(_.findElements(By.xpath("td | th")).asScala.map(_.getText.trim).toList)
    .toList

  def paginationBlockPresence = webDriver.findElements(By.id("pagination-label")).asScala.isEmpty

  def paginationBlockContent(tag: String) = webDriver
    .findElement(By.id("pagination-label"))
    .findElements(By.tagName(tag))
    .asScala
    .map(_.getText.trim.split("\n").head)
    .toList

  def paginationResultsText = webDriver.findElement(By.cssSelector(".govuk-pagination__results")).getText.trim

  def clickOnPaginationLink(linkText: String) = webDriver
    .findElement(By.id("pagination-label"))
    .findElement(By.partialLinkText(linkText))
    .click()
}
