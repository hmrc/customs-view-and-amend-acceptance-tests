package uk.gov.hmrc.pages

import org.openqa.selenium.By

import scala.collection.JavaConverters._

object ClaimsListPages extends CustomsFinancialsWebPage {

  val inProgressClaimsPageUrl = baseUrl + "/customs/view-and-amend/claims-in-progress"

  def tableCaption = webDriver.findElement(By.tagName("caption")).getText.trim

  def claimsList = webDriver.findElement(By.tagName("table")).findElements(By.tagName("tr"))
    .asScala.map(_.findElements(By.xpath("td | th")).asScala.map(_.getText.trim).toList).toList

  def paginationBlockPresence = webDriver.findElements(By.id("pagination-label")).asScala.isEmpty

  def paginationBlockContent(tag: String) = webDriver.findElement(By.id("pagination-label"))
    .findElements(By.tagName(tag)).asScala.map(_.getText.trim.split("\n").head).toList

  def paginationResultsText = webDriver.findElement(By.cssSelector(".govuk-pagination__results")).getText.trim

  def clickOnPaginationLink(linkText: String) = webDriver.findElement(By.id("pagination-label"))
    .findElement(By.partialLinkText(linkText)).click()
}
