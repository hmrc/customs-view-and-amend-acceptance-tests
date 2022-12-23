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
#    Then I should see the heading "No results found"
#    And the page title should be "Search results - View your customs financial accounts - GOV.UK"
    And I should see the following static-content text
      | There are no matching results for NDRC-9999. |
#      | Try searching again by double-checking your claim reference or MRN.  |
    When I click on 'search'
    Then I should see the heading "Find a claim"

  Scenario: Verify no matching results page for MRN search
    Given I am signed in as a pagination user
    And I navigate to the Find a claim page
    When I search for MRN '22GBJD4DCMAM33DOI2'
#    Then I should see the heading "No results found"
#    And the page title should be "Search results - View your customs financial accounts - GOV.UK"
    And I should see the following static-content text
      | There are no matching results for 22GBJD4DCMAM33DOI2. |
#      | Try searching again by double-checking your claim reference or MRN.  |
    When I click on 'search'
#    When I click on back link to previous page
    Then I should see the heading "Find a claim"

#    TODO
#  Scenario: Navigation back to view and amend home page
#    Given I am signed in as a pagination user
#    And I navigate to the Find a claim page
#    Then I should see the back link to previous page
#    When I click on back link to previous page
#    Then the page title should be "Your claims for repayment of customs charges - View your customs financial accounts - GOV.UK"

#  TODO
#  @e2e
#  Scenario: search in progress claims using claim number and MRN
#    Given I am signed in as a openClaim user
#    And I navigate to the Find a claim page
#    When I search for claim 'NDRC-41'
#    Then I should see the heading "Search results"
#    And I should see the table heading "There is 1 matching result for ‘NDRC-41’."
#    And I should see the following claims
#      | claim reference | Claim start date | Claim status |
#      | NDRC-41        | 1 May 2020       | Closed       |
#    When I click on 'NDRC-1001'
#    Then I should see the heading "claim reference NDRC-1001"
#    And I should see the following static text
#      | Claim details: This claim is open and being reviewed by HMRC. |
#    And I should see the following claim details
#      | MRN      | LRN     | Claimant's EORI number | Claim type                      | Claim status | Claim start date | Claim amount requested | Claimant's name | Claimant's email address      |
#      | MRN23014 | KWMREF1 | GB98745632101          | C&E1179 - Multiple declarations | In progress  | 1 May 2020       | £900000.00             | Claimant name   | someemail@mail.com           |

