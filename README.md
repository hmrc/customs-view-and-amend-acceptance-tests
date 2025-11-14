# cds-reimbursement-claim-ui-tests
UI test suite for the Customs Reimbursement Frontend service using WebDriver and `<scalatest/cucumber>`.

# Cloning the project and service startup

Clone this project to the directory of your choice

# Service manager profile required to run the test
On a Terminal instance, start up all services using:

```
sm2 --start CDSRC_ALL
```

If you want to run the Customs Reimbursement Frontend service locally, stop the service using:

```
sm2 --stop CDSRC_FRONTEND
```

Then run the Frontend locally using:

```sbt "run 7500 -Dplay.http.router=testOnlyDoNotUseInAppConf.Routes"```

## Running the Tests

1. Navigate to the directory where the project has been cloned.
2. To run all acceptance tests on your local machine, use: ```./run_tests.sh```  OR ```sbt -Dbrowser=$BROWSER -Denvironment=$ENV $DRIVER -Ddrivernotquit=true "testOnly uk.gov.hmrc.cdsrc.cucumber.runner.Runner"```
3. To run all tests that are tagged as "@wip", use: ```./run_tests_wip.sh```
4. By default, the scripts execute in headless mode. To run them with the browser ui visible, set the below-mentioned property in run_tests_wip.sh script - Argument -Dbrowser.option.headless=false

Security and Accessibility tests
The accessibility and ZAP tests are run as part of Jenkins job. We can get the latest reports from the corresponding acceptance-tests customs-cash-account-acceptance-tests job.

#### Running the tests against a test environment

To run the tests against an environment set the corresponding `host` environment property as specified under
`<env>.host.services` in the [application.conf](/src/test/resources/application.conf).

For example, to execute the `run_tests.sh` script against QA  environment using Chrome remote-webdriver

    ./run_tests.sh qa remote-chrome

## Installing local driver binaries

This project supports UI test execution using Firefox (Geckodriver) and Chrome (Chromedriver) browsers.

See the `drivers/` directory for some helpful scripts to do the installation work for you.  They should work on both Mac and Linux by running the following command:

    ./installGeckodriver.sh <operating-system> <driver-version>
    or
    ./installChromedriver <operating-system> <driver-version>

- *<operating-system>* defaults to **linux64**, however it also supports **macos**
- *<driver-version>* defaults to **0.21.0** for Gecko/Firefox, and the latest release for Chrome.  You can, however, however pass any version available at the [Geckodriver](https://github.com/mozilla/geckodriver/tags) or [Chromedriver](http://chromedriver.storage.googleapis.com/) repositories.

**Note 1:** *You will need to ensure that you have a recent version of Chrome and/or Firefox installed for the later versions of the drivers to work reliably.*

**Note 2** *These scripts use sudo to set the right permissions on the drivers so you will likely be prompted to enter your password.*

### Scalafmt
This repository uses [Scalafmt](https://scalameta.org/scalafmt/), a code formatter for Scala. The formatting rules configured for this repository are defined within [.scalafmt.conf](.scalafmt.conf).

To apply formatting to this repository using the configured rules in [.scalafmt.conf](.scalafmt.conf) execute:

 ```
 sbt scalafmtAll
 ```

To check files have been formatted as expected execute:

 ```
 sbt scalafmtCheckAll scalafmtSbtCheck
 ```

[Visit the official Scalafmt documentation to view a complete list of tasks which can be run.](https://scalameta.org/scalafmt/docs/installation.html#task-keys)
