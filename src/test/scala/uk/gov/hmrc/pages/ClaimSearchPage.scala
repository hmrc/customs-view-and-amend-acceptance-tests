package uk.gov.hmrc.pages

import org.openqa.selenium.By

object ClaimSearchPage extends CustomsFinancialsWebPage {
  override lazy val url = baseUrl + "/customs/view-and-amend/search"

  def hintText = webDriver.findElement(By.id("search-hint")).getText.trim

  def inputTextBox = webDriver.findElement(By.id("value"))

  def enterClaim(text:String) = webDriver.findElement(By.id("value")).sendKeys(text)

  def tableHeading = webDriver.findElement(By.id("claims-caption")).getText.trim

}
