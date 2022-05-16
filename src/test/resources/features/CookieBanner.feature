@acceptance

Feature: Tracking consent banner on View and amend pages

  Background:
    Given I am not signed in
    And I am signed in as a registered user

  Scenario Outline: Cookie consent banner is displayed on view and amend pages
    When I navigate to the <page> page
    Then I should see a cookie consent banner with the following details
      | Cookies on HMRC services                                                                                                                |
      | We use some essential cookies to make our services work.                                                                                |
      | We would like to set additional cookies so we can remember your settings, understand how people use our services and make improvements. |
      | Accept additional cookies                                                                                                               |
      | Reject additional cookies                                                                                                               |
      | View cookies                                                                                                                            |
    And I should see the following links on the cookie banner
      | View cookies |
    And I should see the following buttons on the cookie banner
      | Accept additional cookies |
      | Reject additional cookies |
    Examples:
      | page                |
      | View and amend home |

  Scenario: Accept cookies on cookie consent banner
    When I navigate to the View and amend home page
    And I click on Accept additional cookies button
    Then I should see a cookie consent banner with the following details
      | You have accepted additional cookies. You can change your cookie settings at any time. |
      | Hide cookies message                                                                   |
    And I should see the following links on the cookie banner
      | change your cookie settings |
    And I should see the following buttons on the cookie banner
      | Hide cookies message |
    When I click on Hide cookies message button
    Then I should not see cookie consent banner

  Scenario: Reject cookies on cookie consent banner
    When I navigate to the View and amend home page
    And I click on Reject additional cookies button
    Then I should see a cookie consent banner with the following details
      | You have rejected additional cookies. You can change your cookie settings at any time. |
      | Hide cookies message                                                                   |
    And I should see the following links on the cookie banner
      | change your cookie settings |
    And I should see the following buttons on the cookie banner
      | Hide cookies message |
    When I click on Hide cookies message button
    Then I should not see cookie consent banner

  Scenario: Cookie consent banner is not displayed once it is accepted
    When I navigate to the View and amend home page
    And I click on Accept additional cookies button
    And I navigate to the Customs Financials Home page
    And I navigate to the View and amend home page
    Then I should not see cookie consent banner

  Scenario: Cookie consent banner is not displayed once it is rejected
    When I navigate to the View and amend home page
    And I click on Reject additional cookies button
    And I navigate to the Customs Financials Home page
    And I navigate to the View and amend home page
    Then I should not see cookie consent banner
