@acceptance

Feature: Display claims list

  Scenario: Verify content on in progress claims list page
    Given I am signed in as a registered user
    And I navigate to the View and amend home page
    When I click on 'View claims in progress'
    Then I should see the heading "Claims in progress"
    And the page title should be "Claims in progress - Customs view and amend - GOV.UK"
    And I should see the following claims
      | Case reference | Claim start date | Claim status |
      | 4374422408     | 1 January 9999   | In progress  |

  Scenario: Verify content on pending claims list page
    Given I am signed in as a pagination user
    And I navigate to the View and amend home page
    When I click on 'View claims pending'
    Then I should see the heading "Claims pending"
    And the page title should be "Claims pending - Customs view and amend - GOV.UK"
    And I should see the following claims
      | Case reference | Claim start date | Claim status | Respond by date |
      | NDRC-101       | 1 January 9999   | Pending      | 1 January 9999  |
      | NDRC-102       | 1 January 9999   | Pending      | 1 January 9999  |
      | NDRC-103       | 1 January 9999   | Pending      | 1 January 9999  |
      | NDRC-104       | 1 January 9999   | Pending      | 1 January 9999  |
      | NDRC-105       | 1 January 9999   | Pending      | 1 January 9999  |
      | NDRC-106       | 1 January 9999   | Pending      | 1 January 9999  |
      | NDRC-107       | 1 January 9999   | Pending      | 1 January 9999  |
      | NDRC-108       | 1 January 9999   | Pending      | 1 January 9999  |
      | NDRC-109       | 1 January 9999   | Pending      | 1 January 9999  |
      | NDRC-110       | 1 January 9999   | Pending      | 1 January 9999  |
      | NDRC-111       | 1 January 9999   | Pending      | 1 January 9999  |
      | NDRC-112       | 1 January 9999   | Pending      | 1 January 9999  |
      | NDRC-113       | 1 January 9999   | Pending      | 1 January 9999  |
      | NDRC-114       | 1 January 9999   | Pending      | 1 January 9999  |
      | NDRC-115       | 1 January 9999   | Pending      | 1 January 9999  |
      | NDRC-116       | 1 January 9999   | Pending      | 1 January 9999  |
      | NDRC-117       | 1 January 9999   | Pending      | 1 January 9999  |
      | NDRC-118       | 1 January 9999   | Pending      | 1 January 9999  |
      | NDRC-119       | 1 January 9999   | Pending      | 1 January 9999  |
      | NDRC-120       | 1 January 9999   | Pending      | 1 January 9999  |

  Scenario: Verify content on closed claims list page
    Given I am signed in as a pagination user
    And I navigate to the View and amend home page
    When I click on 'View closed claims'
    Then I should see the heading "Claims closed"
    And the page title should be "Claims closed - Customs view and amend - GOV.UK"
    And I should see the following static text
      | Claims will be removed from this list on the removal date. |
    And I should see the following claims
      | Case reference | Claim start date | Claim status | Removal date    |
      | NDRC-201       | 1 January 9999   | Closed       | 1 February 9999 |
      | NDRC-202       | 1 January 9999   | Closed       | 1 February 9999 |
      | NDRC-203       | 1 January 9999   | Closed       | 1 February 9999 |
      | NDRC-204       | 1 January 9999   | Closed       | 1 February 9999 |
      | NDRC-205       | 1 January 9999   | Closed       | 1 February 9999 |
      | NDRC-206       | 1 January 9999   | Closed       | 1 February 9999 |
      | NDRC-207       | 1 January 9999   | Closed       | 1 February 9999 |
      | NDRC-208       | 1 January 9999   | Closed       | 1 February 9999 |
      | NDRC-209       | 1 January 9999   | Closed       | 1 February 9999 |
      | NDRC-210       | 1 January 9999   | Closed       | 1 February 9999 |
      | NDRC-211       | 1 January 9999   | Closed       | 1 February 9999 |
      | NDRC-212       | 1 January 9999   | Closed       | 1 February 9999 |
      | NDRC-213       | 1 January 9999   | Closed       | 1 February 9999 |
      | NDRC-214       | 1 January 9999   | Closed       | 1 February 9999 |
      | NDRC-215       | 1 January 9999   | Closed       | 1 February 9999 |
      | NDRC-216       | 1 January 9999   | Closed       | 1 February 9999 |
      | NDRC-217       | 1 January 9999   | Closed       | 1 February 9999 |
      | NDRC-218       | 1 January 9999   | Closed       | 1 February 9999 |
      | NDRC-219       | 1 January 9999   | Closed       | 1 February 9999 |
      | NDRC-220       | 1 January 9999   | Closed       | 1 February 9999 |

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

