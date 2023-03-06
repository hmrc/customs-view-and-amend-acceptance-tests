@acceptance
Feature: View customs view and amend home page details

  Scenario: Gov.uk link points to correct page
    Given I am signed in as a pagination user
    When I navigate to the View and amend home page
    And I click on 'GOV.UK'
    Then the page title should be "Welcome to GOV.UK"
# TODO
#  Scenario: Verify content on view and amend home page
#    Given I am signed in as a pagination user
#    When I navigate to the View and amend home page
#    Then I should see the heading "Claims summary"
##    And the page title should be "Your claims for repayment of customs charges - Customs view and amend - GOV.UK"
#    And I should see the following notification bar links
#      | Claims summary      |
#      | Find a claim        |
#      | Start a new claim   |
#    And I should see the eori details Tony Stark - GB744638982001

  Scenario: Display claims summary cards with claims for all status
    Given I am signed in as a pagination user
    When I navigate to the View and amend home page
    Then I should see the following cards
      | 40 claims need more information | Claims that require additional information or documentation.                                                         | View claims needing more information |
      | 40 claims are in progress       | Claims in progress are open and being reviewed by HMRC                                                               | View claims in progress              |
      | 40 claims have been closed      | Closed claims have been approved or rejected by HMRC or withdrawn, and will be removed from this list after 31 days. | View closed claims                   |

  Scenario: Display no claims to view message
    Given I am signed in as a noClaims user
    When I navigate to the View and amend home page
    Then I should see the heading "Claims summary"
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
    Given I am signed in as a pagination user
    When I navigate to the View and amend home page
    Then I should see a link to "Is this page not working properly? (opens in new tab)"
    And the Is this page not working properly? page url is correct

  Scenario: UR Banner is displayed on homepage
    Given I am signed in as a pagination user
    When I navigate to the View and amend home page
    Then I should see a banner with the following information
      | Help improve HMRC services                               |
      | Sign up to take part in user research (opens in new tab) |
      | No thanks                                                |
    And the signup url is correct
