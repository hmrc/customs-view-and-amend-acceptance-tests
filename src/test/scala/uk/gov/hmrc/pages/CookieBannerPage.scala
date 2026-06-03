/*
 * Copyright 2026 HM Revenue & Customs
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

import org.openqa.selenium.{By, WebElement}
import uk.gov.hmrc.pages.Constants.{BannerElement, CookieButton}

import scala.jdk.CollectionConverters.*

object CookieBannerPage extends CustomsFinancialsWebPage {

  def shouldShowConsentBanner(expectedText: Seq[String]): Unit =
    bannerText() should be(expectedText)

  def shouldShowElements(element: BannerElement, expectedText: Seq[String]): Unit = {
    if (element == BannerElement.Links)
      expectedText.foreach(link => linkUrl(link) should include("/tracking-consent/cookie-settings"))
    linksButtonsText(element) should be(expectedText)
  }

  def clickButton(button: CookieButton): Unit =
    findButton(button).click()

  def isVisible: Boolean = !webDriver.findElements(By.cssSelector(".cbanner-govuk-cookie-banner")).isEmpty

  private def banner(): WebElement = webDriver.findElement(By.cssSelector(".cbanner-govuk-cookie-banner"))

  private def bannerText(): List[String] = banner().getText.split("\n").toList

  private def linksButtonsText(element: BannerElement): List[String] = banner()
    .findElements(By.tagName(element.tagName))
    .asScala
    .map(_.getText.trim)
    .toList

  private def linkUrl(linkText: String): String = banner()
    .findElement(By.partialLinkText(linkText))
    .getAttribute("href")
    .trim

  private def findButton(button: CookieButton): WebElement =
    banner().findElements(By.tagName("button")).asScala.filter(_.getText == button.label).head
}
