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

package uk.gov.hmrc.pages

import io.cucumber.scala.{EN, ScalaDsl}
import org.openqa.selenium.{By, WebElement}
import org.scalatest.AppendedClues
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import uk.gov.hmrc.utils.StartUpTearDown

class CustomsFinancialsWebPage extends WebPage with ScalaDsl with EN with Matchers with StartUpTearDown with ScalaFutures with AppendedClues {

  var bookmarkUrl: String = ""

  def helpText(): String = {
    findElementById("help-message").getText
  }

  def backLink(): WebElement = {
    webDriver.findElement(By.cssSelector(".govuk-back-link"))
  }

  def getTextById(id: String) = {
    findElementById(id).getText.trim
  }

  def clearFieldById(id: String) = {
    findElementById(id).clear()
  }
}


