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

import org.mongodb.scala.MongoClient

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps


object DropMongo extends DropMongo
trait DropMongo {

  def dropMongo(dbName: String): Unit = {
    val mongoClient: MongoClient = MongoClient()
    Await.result(
      mongoClient
        .getDatabase(dbName)
        .drop()
        .head(),
      2 seconds
    )
    println("DROPPING MONGO DB : " + dbName)
    mongoClient.close()
  }

}