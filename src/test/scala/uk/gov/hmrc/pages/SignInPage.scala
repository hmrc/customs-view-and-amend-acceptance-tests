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

import uk.gov.hmrc.pages.Constants.UserType

import org.openqa.selenium.support.ui.WebDriverWait
import uk.gov.hmrc.utils.{Configuration, CustomExpectedConditions, Environment}

import java.net.{URL, URLDecoder}
import java.nio.charset.StandardCharsets
import java.time.Duration

object SignInPage extends SignInPage {

  private var signedInUserType: Option[UserType] = None

  def resetSignIn(): Unit =
    signedInUserType = None

  def userNotSignedIn(): Unit = {
    delete all cookies
    signedInUserType = None
  }

  def userIsSignedIn(userType: UserType): Unit = {
    val redirectUrl = continueUrl.getOrElse(ViewAndAmendHomePage.url)
    signedInUserType match {
      case Some(`userType`) =>
        println(s"=============> skipping login - already logged in as $userType")
      case _                =>
        doLogin {
          AuthLoginStubPage.loginAuth(userType, redirectUrl)
          signedInUserType = Some(userType)
        }
    }
  }

  private def doLogin(fillAuthLoginStubForm: => Unit): Unit = {
    AuthLoginStubPage.goToPage()
    fillAuthLoginStubForm
    AuthLoginStubPage.submit()

    if (Configuration.environment == Environment.Dev && !signedInUserType.contains(UserType.NotSubscribed)) {
      val wait = new WebDriverWait(webDriver, Duration.ofSeconds(10))
      wait.until(CustomExpectedConditions.urlEndsWith(continueUrl.getOrElse(ViewAndAmendHomePage.url)))
    }
  }
}

trait SignInPage extends WebPage {

  override lazy val url: String = Configuration.settings.SIGN_IN_PAGE

  def continueUrl: Option[String] = {
    val q: Option[String] = webDriver.getCurrentUrl match {
      case notInitialized if notInitialized == "data:,"          => None // Chrome
      case notInitializedIe if notInitializedIe == "about:blank" => None // IE & Firefox
      case notInitializedSafari if notInitializedSafari == ""    => None // Safari
      case initialized                                           => Option(new URL(initialized).getQuery)
    }
    q.flatMap { str =>
      str
        .split("&")
        .map { v =>
          val m = v.split("=", 2).map(s => URLDecoder.decode(s, StandardCharsets.UTF_8.name()))
          m(0) -> m(1)
        }
        .toMap
        .get("continue")
        .map(relativeUrl => baseUrl + relativeUrl)
    }
  }

}
