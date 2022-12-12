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

object ViewAndAmendHomePage extends CustomsFinancialsWebPage {

  override lazy val url = baseUrl + "/claim-back-import-duty-vat/claims-status"

  def notificationBarLinks: List[String] = webDriver
    .findElement(By.cssSelector(".notifications-bar"))
    .findElements(By.tagName("li"))
    .asScala
    .map(_.getText.trim)
    .toList

  def eoriCompany: String = webDriver.findElement(By.id("eori-company")).getText.trim

  def cardDetails = webDriver
    .findElements(By.cssSelector(".custom-card"))
    .asScala
    .map { card =>
      val heading = card.findElement(By.tagName("h2")).getText.trim
      val text    = card.findElements(By.tagName("p")).asScala.map(_.getText).toList.mkString(",").trim
      val link    = card.findElements(By.tagName("a")).asScala.headOption.map(_.getText.trim).getOrElse(null)
      List(heading, text, link)
    }
    .toList
}
