@acceptance
Feature: Search for claims using claim number and MRN

  Scenario: Verify the content claims search page
    Given I am signed in as a pagination user
    And I navigate to the View and amend home page
    When I click on 'Find a claim'
    Then I should see the heading "Find a claim"
    And I should see the following hint text
      | Search by claim reference number |

  Scenario: Verify no matching results page for claim number search
    Given I am signed in as a pagination user
    And I navigate to the Find a claim page
    When I search for claim 'NDRC-9999'
    Then I should see the heading "Find a claim"
    And the page title should be "Find a claim - Claim back import duty and VAT - GOV.UK"
    And I should see the following static-content text
      | There are no matching results for NDRC-9999. |
    And I should see the following static text
      | Check your claim reference number and search again |
    When I click on Search button on the Find a claim page
    Then I should see the heading "Find a claim"

  Scenario: Verify no matching results page for MRN search
    Given I am signed in as a pagination user
    And I navigate to the Find a claim page
    When I search for MRN '22GBJD4DCMAM33DOI2'
    And the page title should be "Find a claim - Claim back import duty and VAT - GOV.UK"
    And I should see the following static-content text
      | There are no matching results for 22GBJD4DCMAM33DOI2. |
    And I should see the following static text
      | Check your claim reference number and search again |
#    When I click on 'Search'
    When I click on Search button on the Find a claim page
    When I click on back link to previous page
    Then I should see the heading "Find a claim"

  Scenario: Navigation back to view and amend home page
    Given I am signed in as a pagination user
    And I navigate to the Find a claim page
    Then I should see the back link to previous page
    When I click on back link to previous page
    Then the page title should be "Claims summary - Claim back import duty and VAT - GOV.UK"

  @e2e
  Scenario: Search closed claims using claim number
    Given I am signed in as a pagination user
    And I navigate to the Find a claim page
    When I search for claim 'NDRC-42'
    Then I should see the heading "Find a claim"
    And I should see the following static-content text
      | Your search returned |
    And I should see the following claims
      | Claim reference | MRN      | Claim type                    | Claim status |
      | NDRC-42         | MRN23014 | Overpayment or rejected goods | Closed       |
    When I click on 'NDRC-42'
    Then I should see the heading "Claim reference NDRC-42"
    And I should see the following static text
      | Claim details: This claim has been closed |
    And I should see the following claim details
      | MRN      | LRN     | Claimant's EORI number | Claim type                            | Claim start date | Claim removal date | Claim decision | Claim amount requested | Claimant's name | Claimant's email address |
      | MRN23014 | KWMREF1 | GB98745632101          | Overpayment (C285),Single declaration | 1 May 2020       | 1 May 2021         | Approved       | £900000.00             | Claimant name   | Claimant email address   |

  Scenario: Search in progress claims using claim number
    Given I am signed in as a pagination user
    And I navigate to the Find a claim page
    When I search for claim 'NDRC-1'
    Then I should see the heading "Find a claim"
    And I should see the following static-content text
      | Your search returned |
  #   And I should see the table heading "There is 1 matching result for ‘NDRC-41’."
    And I should see the following claims
      | Claim reference | MRN      | Claim type                    | Claim status |
      | NDRC-1          | MRN23014 | Overpayment or rejected goods | In progress  |
    When I click on 'NDRC-1'
    Then I should see the heading "Claim reference NDRC-1"
    And I should see the following static text
      | Claim details: This claim is open and being reviewed by HMRC. |
    And I should see the following claim details
      | LRN     | Claimant's EORI number | Claim type                                     | Claim status | First MRN | Claim start date | Claim amount requested | Claimant's name | Claimant's email address | Other MRNs included in this claim                                                 |
      | KWMREF1 | GB98745632101          | Rejected goods (C&E1179),Multiple declarations | In progress  | MRN23014  | 1 May 2020       | £900000.00             | Claimant name   | Claimant email address   | MRN00002,MRN00003,MRN00004,MRN00005,MRN00006,MRN00007,MRN00008,MRN00009,MRN000010 |


  Scenario: Search pending claims using claim number
    Given I am signed in as a pagination user
    And I navigate to the Find a claim page
    When I search for claim 'NDRC-21'
    Then I should see the heading "Find a claim"
    And I should see the following static-content text
      | Your search returned |
    And I should see the following claims
      | Claim reference | MRN      | Claim type                    | Claim status |
      | NDRC-21         | MRN23014 | Overpayment or rejected goods | Pending      |
    When I click on 'NDRC-21'
    Then I should see the heading "Claim reference NDRC-21"
    And I should see the following static text
      | Claim details: This claim requires additional information or documentation.                                                 |
      | Check your correspondence for details of missing documents and any requests for additional information from your caseworker |
      | If your claim was submitted online, upload your supporting documents                                                        |
      | File upload                                                                                                                 |
      | Valid file formats: JPG, PNG, PDF.                                                                                          |
      | If your claim was submitted manually, you will need to send your supporting documents by post.                              |
    And I should see the following claim details
      | LRN     | Claimant's EORI number | Claim type                                     | Claim status | First MRN | Claim start date | Claim amount requested | Claimant's name | Claimant's email address | Other MRNs included in this claim                                                 |
      | KWMREF1 | GB98745632101          | Rejected goods (C&E1179),Multiple declarations | Pending      | MRN23014  | 1 May 2020       | £900000.00             | Claimant name   | Claimant email address   | MRN00002,MRN00003,MRN00004,MRN00005,MRN00006,MRN00007,MRN00008,MRN00009,MRN000010 |
