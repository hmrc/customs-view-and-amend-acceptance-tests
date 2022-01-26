package uk.gov.hmrc.utils

import play.api.libs.json.{Json, Reads}

import scala.io.Source

object TestDataLoader {

  def loadFromJson[A](resource: String)(implicit reads: Reads[A]): A = {
    Json.parse(Source.fromInputStream(getClass.getResourceAsStream(s"/test-data/${resource}.json")).getLines().mkString).as[A]
  }

}
