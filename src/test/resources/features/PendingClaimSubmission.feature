@acceptance
Feature: Pending claim submission by adding additional document

  Scenario: Pending claim submission by adding commercial invoice document
    Given I am signed in as a Pagination user
    And I navigate to the View and amend home page
    And I click on 'View claims needing more information'
    And I click on 'NDRC-21'
    Then I should see the heading "Claim reference NDRC-21"
    When I click on 'File upload'
    Then I am presented with the "Choose File Type Page"
    Then I should see the heading "Add supporting documents to your claim NDRC-21"
    When I select radio button "Commercial invoice" on "Choose File Type Page"
    And I click continue on "Choose File Type Page"
    And I am presented with the "Choose Files Page" "commercial invoice"
    When I upload a 1 "document.pdf" file on "Choose Files Page"
    And I select radio button "Yes" on "Choose Files Page"
    And I click continue if I'm on "Choose Files Page"
    Then I am presented with the "Choose File Type Page"
    When I select radio button "Substitute or diversion entry" on "Choose File Type Page"
    And I click continue on "Choose File Type Page"
    Then I am presented with the "Choose Files Page" "substitute or diversion entry"
    And I select radio button "No" on "Choose Files Page"
    And I click continue on "Choose Files Page"
    Then I am presented with the "Claim Submitted Page"