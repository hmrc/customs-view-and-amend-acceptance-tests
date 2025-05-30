@acceptance

Feature: Display claims list

  Scenario: Verify content on in progress claims list page
    Given I am signed in as a openClaim user
    And I navigate to the View and amend home page
    When I click on 'View claims in progress'
    Then I should see the heading "Claims in progress"
    And the page title should be "Claims in progress - Claim back import duty and VAT - GOV.UK"
    And I should see the following claims
      | Claim reference | MRN      | Type of claim                 | Claim submitted date |
      | NDRC-1001       | MRN23014 | Overpayment or rejected goods | 1 May 2020       |

  Scenario: Verify content on pending claims list page
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

  Scenario: Verify content on closed claims list page
    Given I am signed in as a ResolvedWithdrawnClaim user
    And I navigate to the View and amend home page
    When I click on 'View closed claims'
    Then I should see the heading "Closed claims"
    And the page title should be "Closed claims - Claim back import duty and VAT - GOV.UK"
    And I should see the following static text
     | This page shows claims that were approved, rejected, or withdrawn in the past 30 days. |
     |To view older closed claims, search using the claim reference number. |
    And I should see the following claims
      | Claim reference | MRN      | Type of claim                 | Claim decision date | Claim decision |
      | NDRC-1005       | MRN23014 | Overpayment or rejected goods | 1 May 2021       | Withdrawn      |

  Scenario Outline: Navigation back to view and amend home page
    Given I am signed in as a pagination user
    And I navigate to the View and amend home page
    And I click on '<claims status>'
    Then I should see the back link to previous page
    When I click on back link to previous page
    Then the page title should be "Claim dashboard - Claim back import duty and VAT - GOV.UK"
    Examples:
      | claims status                        |
      | View claims in progress              |
      | View claims needing more information |
      | View closed claims                   |

  Scenario: Display error page when hods returned an error
    Given I am signed in as a TPI01error user
    When I navigate to the in progress claims list page
    Then I should see the heading "Sorry, there is a problem with the service"
    And I should see the following static text
      | Try again later. |

