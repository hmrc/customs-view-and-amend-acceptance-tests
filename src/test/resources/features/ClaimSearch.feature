@acceptance
Feature: Search for claims using case number and MRN

  Scenario: Verify the content claims search page
    Given I am signed in as a registered user
    And I navigate to the View and amend home page
    When I click on 'Find a claim'
    Then I should see the heading "Find a claim"
    And I should see the following static text
      | Enter a case reference or Movement Reference Number (MRN) |
    And I should see the following hint text
      | For example, NDRC-1234 or 22GBJD4DCMAM33DOI2. |

  Scenario: Verify no matching results page for case number search
    Given I am signed in as a registered user
    And I navigate to the Find a claim page
    When I search for claim 'NDRC-9999'
    Then I should see the following static text
      | Enter a case reference or Movement Reference Number (MRN) |
      | There are no matching results for NDRC-9999.              |
      | Try searching again by:                                   |
    And I should see the following text in bullet points
      | double-checking your case reference or MRN |

  Scenario: Verify no matching results page for MRN search
    Given I am signed in as a registered user
    And I navigate to the Find a claim page
    When I search for MRN '22GBJD4DCMAM33DOI2'
    Then I should see the following static text
      | Enter a case reference or Movement Reference Number (MRN) |
      | There are no matching results for 22GBJD4DCMAM33DOI2.     |
      | Try searching again by:                                   |
    And I should see the following text in bullet points
      | double-checking your case reference or MRN |

  Scenario: Navigation back to view and amend home page
    Given I am signed in as a registered user
    And I navigate to the Find a claim page
    Then I should see the back link to previous page
    When I click on back link to previous page
    Then the page title should be "Your claims for repayment of customs charges - Customs view and amend - GOV.UK"

  @e2e
  Scenario: search in progress claims using case number and MRN
    Given I am signed in as a registered user
    And I navigate to the Find a claim page
    When I search for claim '4374422408'
    Then I should see the table heading "Your search returned"
    And I should see the following claims
      | Case reference | Claim start date | Claim status |
      | 4374422408     | 1 January 9999   | In progress  |
    When I click on '4374422408'
    Then I should see the heading "Case reference 4374422408"
    And I should see the following static text
      | Claim details: |
    And I should see the following claim details
      | MRN               | LRN               | Claimant's EORI number | Claim type | Claim status | Claim start date | Value of claim  | Claimant's name   | Email address      |
      | AWAITING API SPEC | AWAITING API SPEC | GB529339334644474      | C285       | In progress  | 1 January 9999   | £123,456,789.23 | AWAITING API SPEC | someemail@mail.com |

