@acceptance
Feature: View customs view and amend home page details

  Scenario: Gov.uk link points to correct page
    Given I am signed in as a registered user
    When I navigate to the View and amend home page
    And I click on 'GOV.UK'
    Then the page title should be "Welcome to GOV.UK"

  Scenario: Verify content on view and amend home page
    Given I am signed in as a registered user
    When I navigate to the View and amend home page
    Then I should see the heading "Your claims for repayment of customs charges"
    And the page title should be "Your claims for repayment of customs charges - Customs view and amend - GOV.UK"
    And I should see the following notification bar links
      | Home              |
      | Start a new claim |
      | Find a claim      |
    And I should see the eori details Tony Stark - GB744638982000

  Scenario: Display claims summary cards with no claims for some status
    Given I am signed in as a registered user
    When I navigate to the View and amend home page
    Then I should see the sub-heading "Summary"
    And I should see the following cards
      | You have 1 claim in progress | Claims currently being reviewed by HMRC.                                                                                                             | View claims in progress |
      | You have 0 claims pending    | A HMRC caseworker has asked you for additional information.                                                                                          |                         |
      | You have 0 claims closed     | Claims closed when HMRC has not received a response in time, are rejected or resolved.,Closed claims will be removed one month from date of closure. |                         |

  Scenario: Display claims summary cards with claims for all status
    Given I am signed in as a pagination user
    When I navigate to the View and amend home page
    Then I should see the sub-heading "Summary"
    And I should see the following cards
      | You have 100 claims in progress | Claims currently being reviewed by HMRC.                                                                                                             | View claims in progress |
      | You have 100 claims pending     | A HMRC caseworker has asked you for additional information.                                                                                          | View claims pending     |
      | You have 100 claims closed      | Claims closed when HMRC has not received a response in time, are rejected or resolved.,Closed claims will be removed one month from date of closure. | View closed claims      |

  Scenario: Display no claims to view message
    Given I am signed in as a noClaims user
    When I navigate to the View and amend home page
    Then I should see the heading "Your claims for repayment of customs charges"
    And I should see the following static text
      | You have no claims to view.                          |
      | It can take up to 24 hours for new claims to appear. |

  Scenario: Display error page when hods returned an error
    Given I am signed in as a TPI01error user
    When I navigate to the View and amend home page
    Then I should see the heading "Sorry, we’re experiencing technical difficulties"
    And I should see the following static text
      | Please try again in a few minutes. |

  Scenario: View Deskpro link on homepage
    Given I am signed in as a registered user
    When I navigate to the View and amend home page
    Then I should see a link to "Is this page not working properly? (opens in new tab)"
    And the Is this page not working properly? page url is correct

  Scenario: UR Banner is displayed on homepage
    Given I am signed in as a registered user
    When I navigate to the View and amend home page
    Then I should see a banner with the following information
      | Help improve HMRC services                               |
      | Sign up to take part in user research (opens in new tab) |
      | No thanks                                                |
    And the signup url is correct
