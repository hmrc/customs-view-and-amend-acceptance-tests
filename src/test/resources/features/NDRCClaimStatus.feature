@acceptance

Feature: Display backend claims status to in progress, pending and closed categories

  Scenario Outline: Display in-progress status
    Given I am signed in as a <user> user
    And I navigate to the View and amend home page
    When I click on 'View claims in progress'
    Then I should see the heading "Claims in progress"
    And I should see the following claims
      | Case reference | Claim start date |
      | <case ref>     | 1 May 2020       |
    When I click on '<case ref>'
    Then I should see the following claim details
      | MRN      | LRN     | Claimant's EORI number | Claim type   | Claim status | Claim start date | Value of claim | Claimant's name | Email address      |
      | MRN23014 | KWMREF1 | GB98745632101          | <claim type> | In progress  | 1 May 2020       | 900000.00      | Claimant name   | someemail@mail.com |

  Examples:
    | user                                 | case ref   | claim type                      |
    | openClaim                            | NDRC-1001  | C&E1179 - Multiple declarations |
    | openAnalysisClaim                    | NDRC-1002  | C285 - Multiple declarations    |
    | PendingApprovalClaim                 | NDRC-1003  | C&E1179 - Multiple declarations |
    | OpenReworkClaim                      | NDRC-1008  | C285 - Multiple declarations    |
    | PausedClaim                          | NDRC-1009  | C&E1179 - Multiple declarations |
    | PendingPaymentConfirmationClaim      | NDRC-10012 | C285 - Multiple declarations    |
    | PendingDecisionLetterClaim           | NDRC-10015 | C&E1179 - Multiple declarations |
    | ApprovedClaim                        | NDRC-10016 | C285 - Multiple declarations    |
    | AnalysisReworkClaim                  | NDRC-10017 | C&E1179 - Multiple declarations |
    | ReworkPaymentDetailsClaim            | NDRC-10018 | C285 - Multiple declarations    |
    | PendingRTBHClaim                     | NDRC-10019 | C&E1179 - Multiple declarations |
    | PendingComplianceRecommendationClaim | NDRC-10021 | C&E1179 - Multiple declarations |
    | PendingComplianceCheckClaim          | NDRC-10023 | C&E1179 - Multiple declarations |

  Scenario Outline: Display pending status
    Given I am signed in as a <user> user
    And I navigate to the View and amend home page
    When I click on 'View claims pending'
    Then I should see the heading "Claims pending"
    And I should see the following claims
      | Case reference | Claim start date |
      | <case ref>     | 1 May 2020       |
    When I click on '<case ref>'
    Then I should see the following claim details
      | MRN      | LRN     | Claimant's EORI number | Claim type   | Claim status | Claim start date | Value of claim | Claimant's name | Email address      |
      | MRN23014 | KWMREF1 | GB98745632101          | <claim type> | Pending      | 1 May 2020       | 900000.00      | Claimant name   | someemail@mail.com |

    Examples:
      | user                             | case ref   | claim type                   |
      | PendingQueriedClaim              | NDRC-1004  | C285 - Multiple declarations |
      | RTBHsentClaim                    | NDRC-10020 | C285 - Multiple declarations |
      | PendingComplianceCheckQueryClaim | NDRC-10022 | C285 - Multiple declarations |

  Scenario Outline: Display closed status
    Given I am signed in as a <user> user
    And I navigate to the View and amend home page
    When I click on 'View closed claims'
    Then I should see the heading "Claims closed"
    And I should see the following claims
      | Case reference | Claim start date | Removal date |
      | <case ref>     | 1 May 2020       | 1 May 2021   |
    When I click on '<case ref>'
    Then I should see the following claim details
      | MRN      | LRN     | Claimant's EORI number | Claim type   | Claim status | Claim start date | Value of claim | Claimant's name | Email address      |
      | MRN23014 | KWMREF1 | GB98745632101          | <claim type> | Closed       | 1 May 2020       | 900000.00      | Claimant name   | someemail@mail.com |

    Examples:
      | user                          | case ref   | claim type                      |
      | ResolvedWithdrawnClaim        | NDRC-1005  | C&E1179 - Multiple declarations |
      | RejectedFailedValidationClaim | NDRC-1006  | C285 - Multiple declarations    |
      | ResolvedRejectedClaim         | NDRC-1007  | C&E1179 - Multiple declarations |
      | ResolvedNoReplyClaim          | NDRC-10010 | C285 - Multiple declarations    |
      | ResolvedRefusedClaim          | NDRC-10011 | C&E1179 - Multiple declarations |
      | ResolvedApprovedClaim         | NDRC-10013 | C&E1179 - Multiple declarations |
      | ResolvedPartialRefusedClaim   | NDRC-10014 | C285 - Multiple declarations    |

