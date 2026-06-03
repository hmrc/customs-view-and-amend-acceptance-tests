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

import org.apache.pekko.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import play.api.libs.json.JsValue
import play.api.libs.ws.JsonBodyWritables.*
import play.api.libs.ws.*
import play.api.libs.ws.ahc.{AhcWSClientConfigFactory, StandaloneAhcWSClient}

import scala.concurrent.duration.*
import scala.concurrent.{Await, Future}

object WSClient {

  import scala.concurrent.ExecutionContext.Implicits.global
  private given system: ActorSystem = ActorSystem()
  private val wsClient              = StandaloneAhcWSClient(config = AhcWSClientConfigFactory.forConfig(ConfigFactory.load()))

  sys.addShutdownHook {
    wsClient.close()
    system.terminate()
  }

  def httpGet(
    url: String,
    cookie: Set[WSCookie] = Set.empty,
    headers: Seq[(String, String)] = Nil
  ): Future[StandaloneWSRequest#Response] = {
    def request(url: String): StandaloneWSRequest =
      wsClient.url(url)
    request(url).addHttpHeaders(headers*).withCookies(cookie.toSeq*).get()
  }

  def httpPost(
    url: String,
    requestBody: JsValue,
    headers: (String, String)*
  ): Future[StandaloneWSRequest#Response] = {
    def request(url: String): StandaloneWSRequest =
      wsClient.url(url)
    request(url).withHttpHeaders(headers*).post(requestBody)
  }

  def captureLinkContent(url: String, cookies: Set[WSCookie]): DownloadedFile =
    Await.result(httpGet(url, cookies).map(r => DownloadedFile(r)), 20.seconds)

}

case class DownloadedFile(data: Array[Byte], mimeType: String, disposition: String, name: String) {
  def sizeDescription: String = data.length match {
    case kb if 1000 until 1000000 contains kb => f"${kb.toFloat / 1000}%1.1f kB"
    case mb if mb >= 1000000                  => f"${mb.toFloat / 1000000}%1.1f MB"
    case _                                    => s"${data.length} bytes"
  }
}

object DownloadedFile {
  def apply(resp: StandaloneWSResponse): DownloadedFile = {
    val data         = resp.bodyAsBytes.toArray
    val mimeType     = resp.contentType
    val parts        = resp.header("Content-Disposition").getOrElse("").split(";").map(_.trim).toList
    val disposition  = parts.headOption.getOrElse("")
    val name: String = parts
      .lift(1)
      .flatMap(_.split("=", 2).map(_.trim).lift(1))
      .map(_.stripPrefix("\"").stripSuffix("\""))
      .getOrElse("")
    DownloadedFile(data, mimeType, disposition, name)
  }
}
