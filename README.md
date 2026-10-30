# customs-view-and-amend-acceptance-tests

## About
These acceptance tests are written using ScalaTest and include smoke and regression tests for these services:
- [customs-view-and-amend](https://github.com/hmrc/customs-view-and-amend)
- [cds-reimbursement-claim](https://github.com/hmrc/cds-reimbursement-claim)

The acceptance tests can be run both locally and on Jenkins.
## Key Information
When changes have been made to the front-end or back-end services listed above, run the acceptance tests locally against your changes.
1. Stop the front-end and/or back-end services that are affected by your changes, if they are currently running through sm2:
    - `sm2 --stop CUSTOMS_VIEW_AND_AMEND`
    - `sm2 --stop CDSRC`
2. Run the customs-view-and-amend and cds-reimbursement-claim services locally using:
    - `sbt "run 7500 -Dplay.http.router=testOnlyDoNotUseInAppConf.Routes`
3. Verify that both the front-end and back-end services are running successfully.
4. Run both the smoke and regression tests locally, against the front-end and back-end services.
5. Once the front-end and/or back-end changes have been approved and merged, ensure that the smoke tests Jenkins job completes successfully.
6. Regression tests run automatically as part of frontend changes. Ensure the Jenkins regression test job has completed successfully and all tests have passed. See the [Jenkins Builds](#jenkins-builds) section for links to verify the build status.

## How to Run the Tests

### Prerequisites

Run the services for Customs View And Amend:

```bash
 sm2 --start CDSRC_ALL
```

## Testing Locally
Follow the steps below to run the acceptance tests locally.

### How to Run the Smoke Tests
```bash
./run_e2e.sh
```

### How to Run the Regression Tests
```bash
./run_acceptance.sh
```

### Optional Arguments for the Test Script
Note that the order of the arguments is not relevant.

By default, the script runs the scenarios using the Chrome browser. To run the script using a different browser, specify the browser as follows:
```bash
./run_acceptance.sh firefox # chrome, edge or firefox
```

By default, the script runs against the local environment. To run the script against a specific environment, specify the environment as follows:
```bash
./run_acceptance.sh staging  # local or staging
```

## Post-Merge Regression Testing
>**Important:** When acceptance tests are added or updated, run the relevant tests locally before raising a PR. Once the changes have been reviewed and merged, ensure that the following Jenkins jobs have completed successfully against your front-end and/or back-end changes.
> - Accessibility and ZAP tests are run as part of the Jenkins job. The latest reports can be accessed using the links in the Jenkins Build section below.
## Jenkins Builds
 - [customs-view-and-amend-acceptance-tests](https://build.tax.service.gov.uk/job/BordersAndTradeLiveServices/job/CDSReimbursements/job/customs-view-and-amend-acceptance-tests/)

## Manual Testing in QA and Staging
Once the above acceptance tests jobs have been completed successfully, manually verify the service in the QA and Staging environments.
> **Note:** The QA environment uses real upstream services, so the acceptance tests are **not** run against QA. QA must be verified manually.
> Before testing in QA, make sure the EORINumber being used has a valid CDS subscription.

| Environment | Service URL                                                                                      | 
|-------------|--------------------------------------------------------------------------------------------------|
| Local       | [Open the service in Local](http://localhost:9949/auth-login-stub/gg-sign-in)                    |
| QA          | [Open the service in QA](https://www.qa.tax.service.gov.uk/auth-login-stub/gg-sign-in)           |
| Staging     | [Open the service in Staging](https://www.staging.tax.service.gov.uk/auth-login-stub/gg-sign-in) |

### Enrolment Required
Enter the required details as shown below and select **Submit** to continue.

| Field            | Value                                                                                                                                                                                    |
|------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Redirect URL     | Local : `http://localhost:7500/claim-back-import-duty-vat/claims-status`<br> QA : `/claim-back-import-duty-vat/claims-status` <br> Staging : `/claim-back-import-duty-vat/claims-status` |
| Enrolment Key    | `HMRC-CUS-ORG`                                                                                                                                                                           |
| Identifier Name  | `EORINumber`                                                                                                                                                                             |
| Identifier Value | A GB EORI number, e.g. `GB744638982000 / GB744638982001'                                                                                                                                 |

## Scalafmt

Check all project files are formatted as expected as follows:

```bash
sbt scalafmtCheckAll scalafmtCheck
```

Format `*.sbt` and `project/*.scala` files as follows:

```bash
sbt scalafmtSbt
```

Format all project files as follows:

```bash
sbt scalafmtAll
```

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
