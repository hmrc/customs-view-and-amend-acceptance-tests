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

package uk.gov.hmrc.utils

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedCondition

object CustomExpectedConditions {


  def urlEndsWith(suffix: String)(implicit webDriver: WebDriver): ExpectedCondition[Boolean] = {
    new ExpectedCondition[Boolean] {
      override def apply(wd: WebDriver): Boolean = {
        val u = wd.getCurrentUrl
        u.endsWith(suffix)
      }
    }
  }

  def pageTitleIs(title: String)(implicit webDriver: WebDriver): ExpectedCondition[Boolean] = {
    new ExpectedCondition[Boolean] {
      override def apply(wd: WebDriver): Boolean = {
        val u = wd.getTitle
        u.equals(title)
      }
    }
  }

}
