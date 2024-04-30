@acceptance

Feature: XI EORI Claims


  Scenario: View XIEORI claims in progress
    Given I am signed in as a openAnalysisClaim user
    And I navigate to the View and amend home page
    And I should see the eori details Tony Stark - GB00000000002 (XI00000000002)
    When I click on 'View claims in progress'
    Then I should see the heading "Claims in progress"
    And the page title should be "Claims in progress - Claim back import duty and VAT - GOV.UK"
    And I should see the following claims
      | Claim reference | MRN      | Type of claim                 | Claim submitted date |
      | NDRC-1002       | MRN23014 | Overpayment or rejected goods | 1 May 2020       |
      | NDRC-1502       | MRN23014 | Overpayment or rejected goods | 1 May 2020       |


  Scenario: View XIEORI claims needing more information
    Given I am signed in as a PendingQueriedClaim user
    And I navigate to the View and amend home page
    And I should see the eori details Tony Stark - GB00000000004 (XI00000000004)
    When I click on 'View claims needing more information'
    Then I should see the heading "Claims needing more information"
    And the page title should be "Claims needing more information - Claim back import duty and VAT - GOV.UK"
    And I should see the following claims
      | Claim reference | MRN      | Type of claim                 | Claim submitted date |
      | NDRC-1004       | MRN23014 | Overpayment or rejected goods | 1 May 2020       |
      | NDRC-1504       | MRN23014 | Overpayment or rejected goods | 1 May 2020       |



  Scenario: View XIEORI closed claims
    Given I am signed in as a RejectedFailedValidationClaim user
    And I navigate to the View and amend home page
    And I should see the eori details Tony Stark - GB00000000006 (XI00000000006)
    When I click on 'View closed claims'
    Then I should see the heading "Claims closed"
    And the page title should be "Claims closed - Claim back import duty and VAT - GOV.UK"
