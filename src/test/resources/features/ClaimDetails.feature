@acceptance

Feature: View claim details page

  @e2e
  Scenario: View in progress claim details
    Given I am signed in as a openClaim user
    And I navigate to the View and amend home page
    And I click on 'View claims in progress'
    When I click on 'NDRC-1001'
    Then I should see the heading "Claim reference NDRC-1001"
    And I should see the following static text
      | Claim details: This claim is open and being reviewed by HMRC. |
    And I should see the following claim details
      | LRN     | Claimant's EORI number | Claim type                                     | Claim status | First MRN | Claim start date | Claim amount requested | Claimant's name | Claimant's email address | Other MRNs included in this claim                                                          |
      | KWMREF1 | GB98745632101          | Rejected goods (C&E1179),Multiple declarations | In progress  | MRN23014  | 1 May 2020       | £900000.00             | Claimant name   | Claimant email address   | MRN00002,MRN00003,MRN00004,MRN00005,MRN00006,MRN00007,MRN00008,MRN00009,MRN000010 |


  @e2e @TEST
  Scenario: View pending claim details
    Given I am signed in as a PendingQueriedClaim user
    And I navigate to the View and amend home page
    And I click on 'View claims needing more information'
    When I click on 'NDRC-1004'
    Then I should see the heading "Claim reference NDRC-1004"
    And I should see the following static text
      | Claim details: This claim requires additional information or documentation.                                        |
      | Check your email for details of missing documents and any requests for additional information from your caseworker. |
      | Valid file formats: JPG, PNG, PDF.                                                                                                        |
      | Upload a file                                                                                 |
    And I should see the following claim details
      | MRN      | LRN     | Claimant's EORI number | Claim type                            | Claim status | Claim start date | Claim amount requested | Claimant's name | Claimant's email address |
      | MRN23014 | KWMREF1 | GB98745632101          | Overpayment (C285),Single declaration | Pending      | 1 May 2020       | £900000.00             | Claimant name   | Claimant email address   |
    When I click on 'Upload a file'
    And I should see the heading "Add supporting documents to your claim NDRC-1004"


  @e2e
  Scenario: View closed claim details
    Given I am signed in as a ResolvedApprovedClaim user
    And I navigate to the View and amend home page
    And I click on 'View closed claims'
    When I click on 'NDRC-10014'
    Then I should see the heading "Claim reference NDRC-10014"
    And I should see the following static text
      | Claim details: This claim has been closed |
    And I should see the following claim details
      | MRN      | LRN     | Claimant's EORI number | Claim type                            | Claim decision | Claim start date | Claim removal date | Claim amount requested | Claimant's name | Claimant's email address |
      | MRN23014 | KWMREF1 | GB98745632101          | Overpayment (C285),Single declaration | Approved       | 1 May 2020       | 1 May 2021         | £900000.00             | Claimant name   | Claimant email address   |

  Scenario Outline: Navigation back to claims list page
    Given I am signed in as a pagination user
    And I navigate to the View and amend home page
    And I click on '<claims status>'
    When I click on '<claim number>'
    Then I should see the back link to previous page
    When I click on back link to previous page
    Then the page title should be "<page title>"
    Examples:
      | claims status                        | claim number | page title                                                                |
      | View claims needing more information | NDRC-21      | Claims needing more information - Claim back import duty and VAT - GOV.UK |
      | View claims in progress              | NDRC-1       | Claims in progress - Claim back import duty and VAT - GOV.UK              |
      | View closed claims                   | NDRC-41      | Claims closed - Claim back import duty and VAT - GOV.UK                   |

  Scenario: Navigate to home page using Claims summary link
    Given I am signed in as a pagination user
    And I navigate to the View and amend home page
    And I click on 'View closed claims'
    And I click on 'NDRC-41'
    When I click on 'Claims summary'
    Then the page title should be "Claims summary - Claim back import duty and VAT - GOV.UK"
