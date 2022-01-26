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
