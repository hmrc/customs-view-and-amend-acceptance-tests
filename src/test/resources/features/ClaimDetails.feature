@acceptance

Feature: View claim details page

  @e2e
  Scenario: View in progress claim details
    Given I am signed in as a registered user
    And I navigate to the View and amend home page
    And I click on 'View claims in progress'
    When I click on '4374422408'
    Then I should see the heading "Case reference 4374422408"
    And I should see the following static text
      | Claim details: |
    And I should see the following claim details
      | MRN               | LRN               | Claimant's EORI number | Claim type | Claim status | Claim start date | Value of claim | Claimant's name    | Email address |
      | AWAITING API SPEC | AWAITING API SPEC | GB529339334644474      | C285       | In progress  | 1 January 9999   | £123,456,789.23 | AWAITING API SPEC | someemail@mail.co |

  @e2e
  Scenario: View pending claim details
    Given I am signed in as a pagination user
    And I navigate to the View and amend home page
    And I click on 'View claims pending'
    When I click on 'NDRC-101'
    Then I should see the heading "Case reference NDRC-101"
    And I should see the following static text
      | Claim details: |
    And I should see the following claim details
      | MRN               | LRN               | Claimant's EORI number | Claim type | Claim status | Claim start date | Value of claim  | Claimant's name   | Email address      |
      | AWAITING API SPEC | AWAITING API SPEC | GB529339334644474      | C285       | Pending      | 1 January 9999   | £123,456,789.23 | AWAITING API SPEC | someemail@mail.com |

  @e2e
  Scenario: View closed claim details
    Given I am signed in as a pagination user
    And I navigate to the View and amend home page
    And I click on 'View closed claims'
    When I click on 'NDRC-201'
    Then I should see the heading "Case reference NDRC-201"
    And I should see the following static text
      | Claim details: |
    And I should see the following claim details
      | MRN               | LRN               | Claimant's EORI number | Claim type | Claim status | Claim start date | Value of claim  | Claimant's name   | Email address      |
      | AWAITING API SPEC | AWAITING API SPEC | GB529339334644474      | C285       | Closed       | 1 January 9999   | £123,456,789.23 | AWAITING API SPEC | someemail@mail.com |

  Scenario Outline: Navigation back to claims list page
    Given I am signed in as a pagination user
    And I navigate to the View and amend home page
    And I click on '<claims status>'
    When I click on '<claim number>'
    Then I should see the back link to previous page
    When I click on back link to previous page
    Then the page title should be "<page title>"
    Examples:
      | claims status           | claim number | page title                                           |
      | View claims in progress | NDRC-1       | Claims in progress - Customs view and amend - GOV.UK |
      | View claims pending     | NDRC-101     | Claims pending - Customs view and amend - GOV.UK     |
      | View closed claims      | NDRC-201     | Claims closed - Customs view and amend - GOV.UK      |
