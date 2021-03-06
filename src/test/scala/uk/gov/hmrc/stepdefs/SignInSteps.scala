package uk.gov.hmrc.stepdefs

import org.openqa.selenium.support.ui.WebDriverWait
import uk.gov.hmrc.pages.{AuthLoginStubPage, CustomsFinancialsWebPage, SignInPage, ViewAndAmendHomePage}
import uk.gov.hmrc.utils.{Configuration, CustomExpectedConditions}

class SignInSteps extends CustomsFinancialsWebPage {

  private val continueUrl = SignInPage.continueUrl.getOrElse(ViewAndAmendHomePage.url)

  private var signedInUserType: Option[String] = None
  private var signedInNumberOfAccounts: Option[String] = None

  Given("""^I am not signed in$"""){ () =>
    println("*** BEFORE ***" + webDriver.manage().getCookies)
    delete all cookies
    signedInUserType = None
    signedInNumberOfAccounts = None
    println("*** AFTER ***" + webDriver.manage().getCookies)
  }

  When("""^I (am |)sign(ed|) in (.*)as a(n|) (.*) user$""") {
    (_: String, _: String, dutyDefermentType: String, _: String, userType: String) =>
    val redirectUrl = if(dutyDefermentType.isEmpty) continueUrl else bookmarkedUrl(dutyDefermentType)
    signedInUserType match {
      case Some(`userType`) => println(s"=============> skipping login - already logged in as $userType")
      case _ => doLogin {
        AuthLoginStubPage.loginAuth(userType, redirectUrl)
        signedInUserType = Some(userType)
      }
    }
  }

  def bookmarkedUrl(dutyDefermentType: String) = {
    dutyDefermentType match {
      case x if x.contains("Duty deferment requested statements bookmarked url") => "http://localhost:9396/customs/historic-statement/requested/duty-deferment/980831025ef64f389e2397566637a5e9"
    }
  }

  private def doLogin(fillAuthLoginStubForm: => Unit) = {
    AuthLoginStubPage.goToPage()
    fillAuthLoginStubForm
    AuthLoginStubPage.submit()

    if(Configuration.environment.toString.equals("Dev") && !signedInUserType.contains("notsubscribed")) {
      val wait = new WebDriverWait(webDriver, 10)
      wait.until(CustomExpectedConditions.urlEndsWith(continueUrl))
    }
  }

}
