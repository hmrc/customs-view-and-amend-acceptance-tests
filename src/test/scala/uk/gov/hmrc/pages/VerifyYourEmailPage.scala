package uk.gov.hmrc.pages

import org.openqa.selenium.By

object VerifyYourEmailPage extends CustomsFinancialsWebPage {

   def verifyMyEmailLinkUrl(linkName: String): String = webDriver
     .findElement(By.linkText(linkName))
     .getAttribute("href")
}
