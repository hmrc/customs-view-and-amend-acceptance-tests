/*
 * Copyright 2023 HM Revenue & Customs
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

import scala.collection.JavaConverters._

object CommonPage extends CustomsFinancialsWebPage {

  def continue(buttonName: String) = webDriver.findElements(By.tagName("button"))
    .asScala.filter(_.getText == buttonName).head

  def getLinkUrl(linkText: String): String = webDriver.findElement(By.partialLinkText(linkText)).getAttribute("href")

  private def cookieBanner() = webDriver.findElement(By.cssSelector(".cbanner-govuk-cookie-banner"))

  def cookieBannerText() = cookieBanner().getText.split("\n").toList

  def cookieBannerLinksButtonsText(tag: String) = cookieBanner().findElements(By.tagName(tag)).asScala
    .map(_.getText.trim).toList

  def cookieBannerLinkUrl(linkText: String) = cookieBanner().findElement(By.partialLinkText(linkText))
    .getAttribute("href").trim

  def button(buttonName: String) = cookieBanner().findElements(By.tagName("button"))
    .asScala.filter(_.getText == buttonName).head

  def cookieBannerPresence() = !webDriver.findElements(By.cssSelector(".cbanner-govuk-cookie-banner"))
    .isEmpty

  def recruitmentBannerTextIsNotVisible(): Boolean = !recruitmentBanner().isDisplayed

  def recruitmentBannerSignUpHref(): String = webDriver.findElement(By.cssSelector(".hmrc-user-research-banner__link")).getAttribute("href")

  def recruitmentBannerIsNotPresent(): Boolean = webDriver.findElements(By.cssSelector(".hmrc-user-research-banner--show")).isEmpty()

  private def recruitmentBanner(): WebElement = webDriver.findElement(By.cssSelector(".govuk-phase-banner"))

  def recruitmentBannerText() = List(recruitmentBannerHeadingText(), recruitmentBannerNoThanksButtonText())

  private def recruitmentBannerHeadingText() = recruitmentBanner().findElement(By.cssSelector(".govuk-tag.govuk-phase-banner__content__tag")).getText.trim

  private def recruitmentBannerNoThanksButtonText() = {
    recruitmentBanner().findElement(By.cssSelector(".govuk-phase-banner__text")).getText.trim
  }

  def clickNoThanksButtonOnBanner() = recruitmentBanner().findElement(By.cssSelector(".hmrc-user-research-banner__close")).click()

  def clickOnSearchButton(): WebElement = webDriver.findElement(By.cssSelector(".govuk-button"))

}
