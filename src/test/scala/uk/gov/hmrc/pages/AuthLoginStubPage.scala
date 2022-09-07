/*
 * Copyright 2022 HM Revenue & Customs
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

import play.api.libs.json.Json
import uk.gov.hmrc.utils.{Configuration, TestDataLoader}

case class Enrolment(name: String, identifier: String, value: String, state: String)
case class User(pid: String, enrolments: Option[Seq[Enrolment]])

object AuthLoginStubPage extends AuthLoginStubPage

trait AuthLoginStubPage extends WebPage {

  override lazy val url: String = s"${Configuration.settings.AUTH_LOGIN_STUB}/gg-sign-in"

  implicit val enrolmentFormats = Json.format[Enrolment]
  implicit val userFormats = Json.format[User]

  def loginAuth(userType:String, continueUrl: String, fixedEori: Option[String] = None): Unit = {
    val user = TestDataLoader.loadFromJson[User](s"user-$userType")
    //    textField("authorityId").value = user.pid
    textField("redirectionUrl").value = continueUrl
    singleSel("affinityGroup").value = "Organisation"

    for ((enrolment, i) <- user.enrolments.getOrElse(List()).zipWithIndex) {
      textField(s"enrolment[$i].name").value = enrolment.name
      textField(s"enrolment[$i].taxIdentifier[$i].name").value = enrolment.identifier
      textField(s"enrolment[$i].taxIdentifier[$i].value").value = fixedEori.getOrElse(enrolment.value)
      // TODO: Activated by default - cannot set select option, without value attribute
      // singleSel(s"enrolment[$i].state").value = enrolment.state
    }
  }

  def paginatorAuth(userType:String, continueUrl: String, numberOfAccounts: String): Unit = {
    loginAuth(userType, continueUrl, Some("GENERATE" + numberOfAccounts))
  }
}
