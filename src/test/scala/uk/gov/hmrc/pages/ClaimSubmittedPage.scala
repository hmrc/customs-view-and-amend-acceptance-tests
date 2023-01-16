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

import org.openqa.selenium.By
import uk.gov.hmrc.conf.TestConfiguration

object ClaimSubmittedPage extends BasePage {
  override val url: String = TestConfiguration.url("view-and-upload") + "/claims-status/amendment-submitted"

  override val title = "Claims in progress"

  override def expectedPageErrorTitle: Option[String] = Some(
    "Claims in progress - Claim back import duty and VAT - GOV.UK"
  )

  override def expectedPageTitle: Option[String] = Some(
    "Claims in progress - Claim back import duty and VAT - GOV.UK"
  )

  override def expectedPageHeader: Option[String] = Some("Claim amendment sent")

  override def checkPageTitle(duty: String): Unit =
    driver.findElement(By cssSelector "#main-content > div > div > h1").getText should equal(title)

  override def checkPageErrorTitle(duty: String): Unit =
    driver.findElement(By cssSelector "#main-content > div > div > h1").getText should equal(title)
}
