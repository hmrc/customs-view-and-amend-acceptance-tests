@acceptance

Feature: Pagination
  As a user
  I want to quickly select which page to view

  Scenario: A user with up to 20 claims will see no pagination
    Given I am signed in as a registered user
    When I navigate to the in progress claims list page
    Then I should not see pagination links

  Scenario: A user can see pagination for more than 20 claims
    Given I am signed in as a pagination user
    When I navigate to the in progress claims list page
    Then I should see the following pagination content
      | 1    |
      | 2    |
      | 3    |
      | 4    |
      | 5    |
      | Next |
    And I should see the following pagination links
      | 2    |
      | 3    |
      | 4    |
      | 5    |
      | Next |
    And I should see the following pagination results text
      | Showing 1 to 20 of 100 claims |

  Scenario: Navigate to pages using next link on pagination
    Given I am signed in as a pagination user
    And I navigate to the in progress claims list page
    When I click on pagination link "Next"
    Then I should see the following pagination results text
      | Showing 21 to 40 of 100 claims |
    And I should see the following pagination content
      | Previous |
      | 1        |
      | 2        |
      | 3        |
      | 4        |
      | 5        |
      | Next     |
    And I should see the following pagination links
      | Previous |
      | 1        |
      | 3        |
      | 4        |
      | 5        |
      | Next     |

  Scenario: Navigate to pages using previous link on pagination
    Given I am signed in as a pagination user
    And I navigate to the in progress claims list page
    And I click on pagination link "3"
    When I click on pagination link "Previous"
    Then I should see the following pagination results text
      | Showing 21 to 40 of 100 claims |
    And I should see the following pagination content
      | Previous |
      | 1        |
      | 2        |
      | 3        |
      | 4        |
      | 5        |
      | Next     |
    And I should see the following pagination links
      | Previous |
      | 1        |
      | 3        |
      | 4        |
      | 5        |
      | Next     |

  Scenario: Navigate to pages using pagination number links
    Given I am signed in as a pagination user
    And I navigate to the in progress claims list page
    When I click on pagination link "4"
    Then I should see the following pagination results text
      | Showing 61 to 80 of 100 claims |
    And I should see the following pagination links
      | Previous |
      | 1        |
      | 2        |
      | 3        |
      | 5        |
      | Next     |
    When I click on pagination link "5"
    Then I should see the following pagination results text
      | Showing 81 to 100 of 100 claims |
    And I should see the following pagination links
      | Previous |
      | 1        |
      | 2        |
      | 3        |
      | 4        |

  Scenario Outline: User enters invalid number or higher than available pages in query parameter
    Given I am signed in as a pagination user
    And I navigate to the in progress claims list page
    And I enter <pageNumber> in the query parameter
    Then I should see the following pagination results text
      | <pagination result> |
    Examples:
      | pageNumber | pagination result               |
      | -1         | Showing 1 to 20 of 100 claims   |
      | 10         | Showing 81 to 100 of 100 claims |

