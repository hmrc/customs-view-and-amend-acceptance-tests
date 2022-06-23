package uk.gov.hmrc.pages

import org.openqa.selenium.By

import scala.collection.JavaConverters._

object ViewAndAmendHomePage extends CustomsFinancialsWebPage {

  override lazy val url = baseUrl + "/customs/view-and-amend"

  def notificationBarLinks: List[String] = webDriver.findElement(By.cssSelector(".notifications-bar"))
    .findElements(By.tagName("li")).asScala.map(_.getText.trim).toList

  def eoriCompany: String = webDriver.findElement(By.id("eori-company")).getText.trim

  def cardDetails = webDriver.findElements(By.cssSelector(".custom-card"))
    .asScala.map{ card =>
    val heading = card.findElement(By.tagName("h2")).getText.trim
    val text = card.findElements(By.tagName("p")).asScala.map(_.getText).toList.mkString(",").trim
    val link = card.findElements(By.tagName("a")).asScala.headOption.map(_.getText.trim).getOrElse(null)
    List(heading, text, link)
  }.toList
}
