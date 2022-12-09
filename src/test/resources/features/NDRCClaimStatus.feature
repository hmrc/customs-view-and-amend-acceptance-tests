@acceptance

Feature: Display backend claims status to in progress, pending and closed categories

  Scenario Outline: Display in-progress status
    Given I am signed in as a <user> user
    And I navigate to the View and amend home page
    When I click on 'View claims in progress'
    Then I should see the heading "Claims in progress"
    And I should see the following claims
      | Case reference | MRN      | Type of claim                 | Claim start date |
      | <case ref>     | MRN23014 | Overpayment or rejected goods | 1 May 2020       |
    When I click on '<case ref>'
    Then I should see the following claim details
      | MRN      | LRN     | Claimant's EORI number | Claim type   | Claim status | Claim start date | Claim amount requested | Claimant's name | Claimant's email address |
      | MRN23014 | KWMREF1 | GB98745632101          | <claim type> | In progress  | 1 May 2020       | £900000.00             | Claimant name   | someemail@mail.com       |

    Examples:
      | user                                 | case ref   | claim type |
      | openClaim                            | NDRC-1001  | C&E1179    |
      | openAnalysisClaim                    | NDRC-1002  | C285       |
      | PendingApprovalClaim                 | NDRC-1003  | C&E1179    |
      | OpenReworkClaim                      | NDRC-1008  | C285       |
      | PausedClaim                          | NDRC-1009  | C&E1179    |
#      | PendingPaymentConfirmationClaim      | NDRC-10012 | C285       |
#      | PendingDecisionLetterClaim           | NDRC-10015 | C&E1179    |
      | ApprovedClaim                        | NDRC-10016 | C285       |
      | AnalysisReworkClaim                  | NDRC-10017 | C&E1179    |
      | ReworkPaymentDetailsClaim            | NDRC-10018 | C285       |
      | PendingRTBHClaim                     | NDRC-10019 | C&E1179    |
#      | PendingComplianceRecommendationClaim | NDRC-10021 | C&E1179    |
#      | PendingComplianceCheckClaim          | NDRC-10023 | C&E1179    |

  Scenario Outline: Display pending status
    Given I am signed in as a <user> user
    And I navigate to the View and amend home page
    When I click on 'View claims needing more information'
    Then I should see the heading "Claims needing more information"
    And I should see the following claims
      | Case reference | MRN      | Type of claim                 | Claim start date |
      | <case ref>     | MRN23014 | Overpayment or rejected goods | 1 May 2020       |
    When I click on '<case ref>'
    Then I should see the following claim details
      | MRN      | LRN     | Claimant's EORI number | Claim type   | Claim status | Claim start date | Claim amount requested | Claimant's name | Claimant's email address |
      | MRN23014 | KWMREF1 | GB98745632101          | <claim type> | Pending      | 1 May 2020       | £900000.00             | Claimant name   | someemail@mail.com       |

    Examples:
      | user                             | case ref   | claim type |
      | PendingQueriedClaim              | NDRC-1004  | C285       |
#      | RTBHsentClaim                    | NDRC-10020 | C285       |
#      | PendingComplianceCheckQueryClaim | NDRC-10022 | C285       |

  Scenario Outline: Display closed status
    Given I am signed in as a <user> user
    And I navigate to the View and amend home page
    When I click on 'View closed claims'
    Then I should see the heading "Claims closed"
    And I should see the following claims
      | Case reference | MRN      | Type of claim                 | Claim close date | Claim decision   |
      | <case ref>     | MRN23014 | Overpayment or rejected goods | 1 May 2021       | <claim decision> |
    When I click on '<case ref>'
    Then I should see the following claim details
      | MRN      | LRN     | Claimant's EORI number | Claim type   | Claim decision   | Claim start date | Claim removal date | Claim amount requested | Claimant's name | Claimant's email address |
      | MRN23014 | KWMREF1 | GB98745632101          | <claim type> | <claim decision> | 1 May 2020       | 1 May 2021         | £900000.00             | Claimant name   | someemail@mail.com       |

  Examples:
    | user                          | case ref   | claim type | claim decision    |
    | ResolvedWithdrawnClaim        | NDRC-1005  | C&E1179    | Withdrawn         |
    | RejectedFailedValidationClaim | NDRC-1006  | C285       | Failed Validation |
#    | ResolvedRejectedClaim         | NDRC-1007  | C&E1179    |                   |
#    | ResolvedNoReplyClaim          | NDRC-10010 | C285       |                   |
#    | ResolvedRefusedClaim          | NDRC-10011 | C&E1179    |                   |
#    | ResolvedApprovedClaim         | NDRC-10013 | C&E1179    |                   |
#    | ResolvedPartialRefusedClaim   | NDRC-10014 | C285       |                   |

