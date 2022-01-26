package uk.gov.hmrc.pages

import org.openqa.selenium.By

object NotSubscribedToCDSPage extends CustomsFinancialsWebPage {

   def registerForCdsLinkUrl: String = webDriver
     .findElement(By.linkText("Economic Operator and Registration Identification (EORI) number (opens in a new window or tab)"))
     .getAttribute("href")

   def subscribeToCdsLinkUrl: String = webDriver
     .findElement(By.linkText("get access to CDS (opens in a new window or tab)"))
     .getAttribute("href")
}
