package uk.gov.hmrc.pages

import org.openqa.selenium.By

import scala.collection.JavaConverters._

object ClaimDetailsPage extends CustomsFinancialsWebPage {

  def claimDetails: Map[String, String] = webDriver.findElements(By.cssSelector(".govuk-summary-list__row")).asScala
    .flatMap{ row => {
      val k = row.findElement(By.cssSelector(".govuk-summary-list__key")).getText.trim
      val v = row.findElement(By.cssSelector(".govuk-summary-list__value")).getText.trim.replace("\n",",")
      Map(k -> v)
    }
    }.toMap

}
