@acceptance

Feature: View claim details page

  @e2e
  Scenario: View in progress claim details
    Given I am signed in as a openClaim user
    And I navigate to the View and amend home page
    And I click on 'View claims in progress'
    When I click on 'NDRC-1001'
    Then I should see the heading "Claim reference NDRC-1001"
    And I should see the following static text
      | Claim details: This claim is open and being reviewed by HMRC. |
    And I should see the following claim details
      | MRN      | LRN     | Claimant's EORI number | Claim type                                     | Claim status | Claim start date | Claim amount requested | Claimant's name | Claimant's email address |
      | MRN23014 | KWMREF1 | GB98745632101          | Rejected goods (C&E1179),Multiple declarations | In progress  | 1 May 2020       | £900000.00             | Claimant name   | someemail@mail.com       |
#    When I click on 'File upload'
#    And I should see the heading "Add supporting documents to your claim NDRC-1001"


  @e2e
  Scenario: View pending claim details
    Given I am signed in as a PendingQueriedClaim user
    And I navigate to the View and amend home page
    And I click on 'View claims needing more information'
    When I click on 'NDRC-1004'
    Then I should see the heading "Claim reference NDRC-1004"
    And I should see the following static text
      | Claim details: This claim requires additional information or documentation.                                        |
      | Check your email for details of missing documents and any requests for additional information from your caseworker |
      | File upload                                                                                                        |
      | Valid file formats: JPG, PNG, PDF.                                                                                 |
    And I should see the following claim details
      | MRN      | LRN     | Claimant's EORI number | Claim type                            | Claim status | Claim start date | Claim amount requested | Claimant's name | Claimant's email address |
      | MRN23014 | KWMREF1 | GB98745632101          | Overpayment (C285),Single declaration | Pending      | 1 May 2020       | £900000.00             | Claimant name   | someemail@mail.com       |
#TODO
#  @e2e
#  Scenario: View closed claim details
#    Given I am signed in as a ResolvedApprovedClaim user
#    And I navigate to the View and amend home page
#    And I click on 'View closed claims'
#    When I click on 'NDRC-10013'
#    Then I should see the heading "Claim reference NDRC-10013"
#    And I should see the following static text
#      | Claim details: This claim has been closed |
#    And I should see the following claim details
#      | MRN      | LRN     | Claimant's EORI number | Claim type     | Claim decision   | Claim start date | Claim removal date | Claim amount requested | Claimant's name | Claimant's email address  |
#      | MRN23014 | KWMREF1 | GB98745632101          | C&E1179        | Approved         | 1 May 2020       | 1 May 2021         | £900000.00             | Claimant name   | someemail@mail.com        |

  Scenario Outline: Navigation back to claims list page
    Given I am signed in as a pagination user
    And I navigate to the View and amend home page
    And I click on '<claims status>'
    When I click on '<claim number>'
    Then I should see the back link to previous page
    When I click on back link to previous page
    Then the page title should be "<page title>"
    Examples:
      | claims status                        | claim number | page title                                                                      |
      | View claims needing more information | NDRC-21      | Claims needing more information - View your customs financial accounts - GOV.UK |
      | View claims in progress              | NDRC-1       | Claims in progress - View your customs financial accounts - GOV.UK              |
      | View closed claims                   | NDRC-41      | Claims closed - View your customs financial accounts - GOV.UK                   |

#    TODO
#  Scenario: Navigate to home page using Claims summary link
#    Given I am signed in as a pagination user
#    And I navigate to the View and amend home page
#    And I click on 'View closed claims'
#    And I click on 'NDRC-41'
#    When I click on 'Claims summary'
#    Then the page title should be "Your claims for repayment of customs charges - Customs view and amend - GOV.UK"
