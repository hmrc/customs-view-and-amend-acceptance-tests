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
import org.openqa.selenium.chrome.ChromeDriverService
import uk.gov.hmrc.webdriver.SingletonDriver

object Driver {

  def getOs: String = System.getProperty("os.name")

  lazy val isMac: Boolean = getOs.startsWith("Mac")
  lazy val isLinux: Boolean = getOs.startsWith("Linux")

  if (!Option(System.getProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY)).exists(_.length > 0)) {
    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "/usr/local/bin/chromedriver")
  }

  if (!Option(System.getProperty("browser")).exists(_.length > 0)) {
    System.setProperty("browser", "chrome")
  }

  val instance: WebDriver = newWebDriver()

  def newWebDriver(): WebDriver = {
    val driver = SingletonDriver.getInstance()
    driver
  }

  sys.addShutdownHook(SingletonDriver.closeInstance())
}
