@acceptance
Feature: Logout after login

  Scenario: User sign out from view and amend homepage
    Given I am signed in as a registered user
    When I navigate to the View and amend home page
    And I click on 'Sign out'
    Then I should see the heading "Give feedback"
#   When I navigate to the View and amend home page
#    Then I am redirected to the "Sign in using Government Gateway" page
