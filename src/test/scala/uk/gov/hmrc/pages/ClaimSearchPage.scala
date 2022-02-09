package uk.gov.hmrc.pages

import org.openqa.selenium.By

object ClaimSearchPage extends CustomsFinancialsWebPage {
  override lazy val url = baseUrl + "/customs/view-and-amend/search"

  def hintText = webDriver.findElement(By.id("search-claim-hint")).getText.trim

  def inputTextBox = webDriver.findElement(By.id("search-claim"))

  def enterClaim(text:String) = inputTextBox.sendKeys(text)

}
