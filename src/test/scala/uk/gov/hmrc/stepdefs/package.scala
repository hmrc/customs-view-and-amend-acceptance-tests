package uk.gov.hmrc

import io.cucumber.datatable.DataTable

package object stepdefs {

  implicit class DataTableConverters(dataTable: DataTable) {
    import scala.collection.JavaConverters._

    def asScalaListOfStrings: List[String] = dataTable.cells().asScala.flatMap(_.asScala).toList

    def asScalaListOfLists: List[List[String]] = dataTable.rows(0).asLists().asScala.map(_.asScala.toList).toList

    def asScalaListOfUtilMaps: List[java.util.Map[String, String]] = dataTable
      .asMaps().asScala.toList
  }
}
