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

import org.openqa.selenium.support.ui.WebDriverWait
import uk.gov.hmrc.utils.{Configuration, CustomExpectedConditions}

import java.net.{URL, URLDecoder}
import java.nio.charset.StandardCharsets
import java.time.Duration

object SignInPage extends SignInPage {

  private var signedInUserType: Option[String] = None

  def resetSignIn(): Unit =
    signedInUserType = None

  def userNotSignedIn(): Unit = {
    delete all cookies
    signedInUserType = None
  }

  def userIsSignedIn(dutyDefermentType: String, userType: String): Unit = {
    val redirectUrl = if (dutyDefermentType.isEmpty) continueUrl.getOrElse(ViewAndAmendHomePage.url)
    else bookmarkedUrl(dutyDefermentType)
    signedInUserType match {
      case Some(`userType`) =>
        println(s"=============> skipping login - already logged in as $userType")
      case _ =>
        doLogin {
          AuthLoginStubPage.loginAuth(userType, redirectUrl)
          signedInUserType = Some(userType)
        }
    }
  }

  private def bookmarkedUrl(dutyDefermentType: String): String = dutyDefermentType match {
    case x if x.contains("Duty deferment requested statements bookmarked url") =>
      "http://localhost:9396/customs/historic-statement/requested/duty-deferment/980831025ef64f389e2397566637a5e9"
    case _ =>
      throw new IllegalArgumentException(s"Unexpected dutyDefermentType: $dutyDefermentType")
  }

  private def doLogin(fillAuthLoginStubForm: => Unit): Unit = {
    AuthLoginStubPage.goToPage()
    fillAuthLoginStubForm
    AuthLoginStubPage.submit()

    if (Configuration.environment.toString.equals("Dev") && !signedInUserType.contains("notsubscribed")) {
      val wait = new WebDriverWait(webDriver, Duration.ofSeconds(10))
      wait.until(CustomExpectedConditions.urlEndsWith(continueUrl.getOrElse(ViewAndAmendHomePage.url)))
    }
  }
}

trait SignInPage extends WebPage {

  override lazy val url: String = Configuration.settings.SIGN_IN_PAGE

  def continueUrl: Option[String] = {
    val q: Option[String] = webDriver.getCurrentUrl match {
      case notInitialized if notInitialized == "data:," => None //Chrome
      case notInitializedIe if notInitializedIe == "about:blank" => None //IE & Firefox
      case notInitializedSafari if notInitializedSafari == "" => None //Safari
      case initialized => Option(new URL(initialized).getQuery)
    }
    q.flatMap { str =>
      str.split("&").map(v => {
        val m =  v.split("=", 2).map(s => URLDecoder.decode(s, StandardCharsets.UTF_8.name()))
        m(0) -> m(1)
      }).toMap.get("continue").map(relativeUrl => baseUrl + relativeUrl)
    }
  }

}