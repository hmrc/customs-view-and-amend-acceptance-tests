/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.pages.auth

import org.openqa.selenium.By
import uk.gov.hmrc.conf.TestConfiguration
import uk.gov.hmrc.pages.BasePage

object AuthorityLoginStubPage extends BasePage {

  override val url: String = TestConfiguration.url("auth-login-stub") + "/gg-sign-in"
  override val title = "Authority Wizard"

  override def expectedPageErrorTitle: Option[String] = Some("")

  override def expectedPageTitle: Option[String] = Some("Authority Wizard")

  override def expectedPageHeader: Option[String] = Some("Authority Wizard")

  def enterRedirectURL(url: String): Unit = {
    driver.findElement(By.cssSelector("#redirectionUrl")).sendKeys(url)
  }

  def enrolments(eKey: String, IDName: String, IDValue: String): Unit = {
    driver.findElement(By.id("enrolment[0].name")).sendKeys(eKey)
    driver.findElement(By.id("input-0-0-name")).sendKeys(IDName)
    driver.findElement(By.id("input-0-0-value")).sendKeys(IDValue)
  }

  override def clickContinueButton(): Unit ={
    click on id("submit")
  }

}
