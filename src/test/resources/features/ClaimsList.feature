@acceptance

Feature: Display claims list

  Scenario: Verify content on in progress claims list page
    Given I am signed in as a openClaim user
    And I navigate to the View and amend home page
    When I click on 'View claims in progress'
    Then I should see the heading "Claims in progress"
    And the page title should be "Claims in progress - Customs view and amend - GOV.UK"
    And I should see the following claims
      | Case reference | Claim start date |
      | NDRC-1001      | 1 May 2020       |

  Scenario: Verify content on pending claims list page
    Given I am signed in as a PendingQueriedClaim user
    And I navigate to the View and amend home page
    When I click on 'View claims pending'
    Then I should see the heading "Claims pending"
    And the page title should be "Claims pending - Customs view and amend - GOV.UK"
    And I should see the following claims
      | Case reference | Claim start date |
      | NDRC-1004      | 1 May 2020       |

  Scenario: Verify content on closed claims list page
    Given I am signed in as a ResolvedWithdrawnClaim user
    And I navigate to the View and amend home page
    When I click on 'View closed claims'
    Then I should see the heading "Claims closed"
    And the page title should be "Claims closed - Customs view and amend - GOV.UK"
    And I should see the sub-heading "Claims will be removed from this list on the removal date."
    And I should see the table heading "Sorted by closest removal date."
    And I should see the following claims
      | Case reference | Claim start date | Removal date |
      | NDRC-1005      | 1 May 2020       | 1 May 2021   |

  Scenario Outline: Navigation back to view and amend home page
    Given I am signed in as a pagination user
    And I navigate to the View and amend home page
    And I click on '<claims status>'
    Then I should see the back link to previous page
    When I click on back link to previous page
    Then the page title should be "Your claims for repayment of customs charges - Customs view and amend - GOV.UK"
    Examples:
      | claims status           |
      | View claims in progress |
      | View claims pending     |
      | View closed claims      |

  Scenario: Display error page when hods returned an error
    Given I am signed in as a TPI01error user
    When I navigate to the in progress claims list page
    Then I should see the heading "Sorry, we’re experiencing technical difficulties"
    And I should see the following static text
      | Please try again in a few minutes. |

