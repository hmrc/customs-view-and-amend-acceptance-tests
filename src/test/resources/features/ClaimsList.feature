@acceptance

Feature: Display claims list

  Scenario: Verify content on in progress claims list page
    Given I am signed in as a pagination user
    And I navigate to the View and amend home page
    When I click on 'View claims in progress'
    Then I should see the heading "Claims in progress"
    And the page title should be "Claims in progress - Customs view and amend - GOV.UK"
#    And I should see the following claims
#      | Case reference | Claim start date |
#      | NDRC-1         | 28 February 2022 |
#      | NDRC-2         | 27 February 2022 |
#      | NDRC-3         | 26 February 2022 |
#      | NDRC-4         | 25 February 2022 |
#      | NDRC-5         | 24 February 2022 |
#      | NDRC-6         | 23 February 2022 |
#      | NDRC-7         | 22 February 2022 |
#      | NDRC-8         | 21 February 2022 |
#      | NDRC-9         | 20 February 2022 |
#      | NDRC-10        | 19 February 2022 |
#      | NDRC-11        | 18 February 2022 |
#      | NDRC-12        | 17 February 2022 |
#      | NDRC-13        | 16 February 2022 |
#      | NDRC-14        | 15 February 2022 |
#      | NDRC-15        | 14 February 2022 |
#      | NDRC-16        | 13 February 2022 |
#      | NDRC-17        | 12 February 2022 |
#      | NDRC-18        | 11 February 2022 |
#      | NDRC-19        | 10 February 2022 |
#      | NDRC-20        | 9 February 2022  |

  Scenario: Verify content on pending claims list page
    Given I am signed in as a pagination user
    And I navigate to the View and amend home page
    When I click on 'View claims pending'
    Then I should see the heading "Claims pending"
    And the page title should be "Claims pending - Customs view and amend - GOV.UK"
#    And I should see the following claims
#      | Case reference | Claim start date |
#      | NDRC-21        | 8 February 2022  |
#      | NDRC-22        | 7 February 2022  |
#      | NDRC-23        | 6 February 2022  |
#      | NDRC-24        | 5 February 2022  |
#      | NDRC-25        | 4 February 2022  |
#      | NDRC-26        | 3 February 2022  |
#      | NDRC-27        | 2 February 2022  |
#      | NDRC-28        | 1 February 2022  |
#      | NDRC-29        | 31 January 2022  |
#      | NDRC-30        | 30 January 2022  |
#      | NDRC-31        | 29 January 2022  |
#      | NDRC-32        | 28 January 2022  |
#      | NDRC-33        | 27 January 2022  |
#      | NDRC-34        | 26 January 2022  |
#      | NDRC-35        | 25 January 2022  |
#      | NDRC-36        | 24 January 2022  |
#      | NDRC-37        | 23 January 2022  |
#      | NDRC-38        | 22 January 2022  |
#      | NDRC-39        | 21 January 2022  |
#      | NDRC-40        | 20 January 2022  |

  Scenario: Verify content on closed claims list page
    Given I am signed in as a pagination user
    And I navigate to the View and amend home page
    When I click on 'View closed claims'
    Then I should see the heading "Claims closed"
    And the page title should be "Claims closed - Customs view and amend - GOV.UK"
    And I should see the sub-heading "Claims will be removed from this list on the removal date."
    And I should see the table heading "Sorted by closest removal date."
#    And I should see the following claims
#      | Case reference | Claim start date | Removal date     |
#      | NDRC-41        | 19 January 2022  | 19 January 2022  |
#      | NDRC-42        | 18 January 2022  | 18 January 2022  |
#      | NDRC-43        | 17 January 2022  | 17 January 2022  |
#      | NDRC-44        | 16 January 2022  | 16 January 2022  |
#      | NDRC-45        | 15 January 2022  | 15 January 2022  |
#      | NDRC-46        | 14 January 2022  | 14 January 2022  |
#      | NDRC-47        | 13 January 2022  | 13 January 2022  |
#      | NDRC-48        | 12 January 2022  | 12 January 2022  |
#      | NDRC-49        | 11 January 2022  | 11 January 2022  |
#      | NDRC-50        | 10 January 2022  | 10 January 2022  |
#      | NDRC-51        | 9 January 2022   | 9 January 2022   |
#      | NDRC-52        | 8 January 2022   | 8 January 2022   |
#      | NDRC-53        | 7 January 2022   | 7 January 2022   |
#      | NDRC-54        | 6 January 2022   | 6 January 2022   |
#      | NDRC-55        | 5 January 2022   | 5 January 2022   |
#      | NDRC-56        | 4 January 2022   | 4 January 2022   |
#      | NDRC-57        | 3 January 2022   | 3 January 2022   |
#      | NDRC-58        | 2 January 2022   | 2 January 2022   |
#      | NDRC-59        | 1 January 2022   | 1 January 2022   |
#      | NDRC-60        | 31 December 2021 | 31 December 2021 |

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

