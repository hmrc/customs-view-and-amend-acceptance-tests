@acceptance
Feature: Search for claims using case number and MRN

  Scenario: Verify the content claims search page
    Given I am signed in as a pagination user
    And I navigate to the View and amend home page
    When I click on 'Find a claim'
    Then I should see the heading "Find a claim"
    And I should see the following hint text
      | For example, ‘NDRC-1234’ or ‘22GBJD4DCMAM33DOI2’. |

  Scenario: Verify no matching results page for case number search
    Given I am signed in as a pagination user
    And I navigate to the Find a claim page
    When I search for claim 'NDRC-9999'
    Then I should see the heading "No results found"
    And the page title should be "Search results - Customs view and amend - GOV.UK"
    And I should see the following static text
      | There are 0 matching results for NDRC-9999.                        |
      | Try searching again by double-checking your case reference or MRN. |
    When I click on 'searching again'
    Then I should see the heading "Find a claim"

  Scenario: Verify no matching results page for MRN search
    Given I am signed in as a pagination user
    And I navigate to the Find a claim page
    When I search for MRN '22GBJD4DCMAM33DOI2'
    Then I should see the heading "No results found"
    And the page title should be "Search results - Customs view and amend - GOV.UK"
    And I should see the following static text
      | There are 0 matching results for 22GBJD4DCMAM33DOI2.               |
      | Try searching again by double-checking your case reference or MRN. |
    When I click on back link to previous page
    Then I should see the heading "Find a claim"

  Scenario: Navigation back to view and amend home page
    Given I am signed in as a pagination user
    And I navigate to the Find a claim page
    Then I should see the back link to previous page
    When I click on back link to previous page
    Then the page title should be "Your claims for repayment of customs charges - Customs view and amend - GOV.UK"

  @e2e
  Scenario: search in progress claims using case number and MRN
    Given I am signed in as a openClaim user
    And I navigate to the Find a claim page
    When I search for claim 'NDRC-1001'
    Then I should see the heading "Search results"
    And I should see the table heading "There is 1 matching result for ‘NDRC-1001’."
    And I should see the following claims
      | Case reference | Claim start date | Claim status |
      | NDRC-1001      | 1 May 2020       | In progress  |
    When I click on 'NDRC-1001'
    Then I should see the heading "Case reference NDRC-1001"
    And I should see the following static text
      | Claim details: This claim is open and being reviewed by HMRC. |
    And I should see the following claim details
      | MRN      | LRN     | Claimant's EORI number | Claim type                      | Claim status | Claim start date | Claim amount requested | Claimant's name | Claimant's email address      |
      | MRN23014 | KWMREF1 | GB98745632101          | C&E1179 - Multiple declarations | In progress  | 1 May 2020       | £900000.00             | Claimant name   | someemail@mail.com           |

