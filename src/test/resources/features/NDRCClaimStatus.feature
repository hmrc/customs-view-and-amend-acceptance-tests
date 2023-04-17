@acceptance

Feature: Display backend claims status to in progress, pending and closed categories

  Scenario Outline: Display in-progress status for multiple declarations claims
    Given I am signed in as a <user> user
    And I navigate to the View and amend home page
    When I click on 'View claims in progress'
    Then I should see the heading "Claims in progress"
    And I should see the following claims
      | Claim reference | MRN      | Type of claim                 | Claim start date |
      | <claim ref>     | MRN23014 | Overpayment or rejected goods | 1 May 2020       |
    When I click on '<claim ref>'
    Then I should see the following claim details
      | LRN     | Claimant's EORI number | Claim type   | Claim status | First MRN | Claim start date | Claim amount requested | Claimant's name | Claimant's email address | Other MRNs included in this claim                                                          |
      | KWMREF1 | GB98745632101          | <claim type> | In progress  | MRN23014  | 1 May 2020       | £900000.00             | Claimant name   | someemail@mail.com       | MRN00001,MRN00002,MRN00003,MRN00004,MRN00005,MRN00006,MRN00007,MRN00008,MRN00009,MRN000010 |

    Examples:
      | user                            | claim ref  | claim type                                     |
      | openClaim                       | NDRC-1001  | Rejected goods (C&E1179),Multiple declarations |
      | PendingApprovalClaim            | NDRC-1003  | Rejected goods (C&E1179),Multiple declarations |
      | PausedClaim                     | NDRC-1009  | Rejected goods (C&E1179),Multiple declarations |
      | PendingPaymentConfirmationClaim | NDRC-10013 | Rejected goods (C&E1179),Multiple declarations |
      | ApprovedClaim                   | NDRC-10017 | Rejected goods (C&E1179),Multiple declarations |
      | ReworkPaymentDetailsClaim       | NDRC-10019 | Rejected goods (C&E1179),Multiple declarations |


  Scenario Outline: Display in-progress status for single declaration claims
    Given I am signed in as a <user> user
    And I navigate to the View and amend home page
    When I click on 'View claims in progress'
    Then I should see the heading "Claims in progress"
    And I should see the following claims
      | Claim reference | MRN      | Type of claim                 | Claim start date |
      | <claim ref>     | MRN23014 | Overpayment or rejected goods | 1 May 2020       |
    When I click on '<claim ref>'
    Then I should see the following claim details

      | MRN      | LRN     | Claimant's EORI number | Claim type   | Claim status | Claim start date | Claim amount requested | Claimant's name | Claimant's email address |
      | MRN23014 | KWMREF1 | <EORI number>          | <claim type> | In progress  | 1 May 2020       | £900000.00             | Claimant name   | someemail@mail.com       |

    Examples:
      | user                                 | claim ref  | claim type                            | EORI number     |
      | openAnalysisClaim                    | NDRC-1002  | Overpayment (C285),Single declaration | GB98745632101   |
      | openAnalysisClaim                    | NDRC-1502  | Overpayment (C285),Single declaration | XI98745632102   |
      | OpenReworkClaim                      | NDRC-1008  | Overpayment (C285),Single declaration | GB98745632101   |
      | OpenReworkClaim                      | NDRC-1508  | Overpayment (C285),Single declaration | XI98745632102   |
#      | PendingDecisionLetterClaim           | NDRC-10016 | Overpayment (C285),Single declaration | GB98745632101   |
#      | PendingDecisionLetterClaim           | NDRC-10516 | Overpayment (C285),Single declaration | XI98745632102   |
#      | AnalysisReworkClaim                  | NDRC-10018 | Overpayment (C285),Single declaration | GB98745632101   |
#      | AnalysisReworkClaim                  | NDRC-10518 | Overpayment (C285),Single declaration | XI98745632102   |
#      | PendingComplianceRecommendationClaim | NDRC-10022 | Overpayment (C285),Single declaration | GB98745632101   |
#      | PendingComplianceRecommendationClaim | NDRC-10522 | Overpayment (C285),Single declaration | XI98745632102   |
#      | PendingComplianceCheckClaim          | NDRC-10024 | Overpayment (C285),Single declaration | GB98745632101   |
#      | PendingComplianceCheckClaim          | NDRC-10524 | Overpayment (C285),Single declaration | XI98745632102   |


  Scenario Outline: Display pending status for multiple declarations claims
    Given I am signed in as a <user> user
    And I navigate to the View and amend home page
    When I click on 'View claims needing more information'
    Then I should see the heading "Claims needing more information"
    And I should see the following claims
      | Claim reference | MRN      | Type of claim                 | Claim start date |
      | <claim ref>     | MRN23014 | Overpayment or rejected goods | 1 May 2020       |
    When I click on '<claim ref>'
    Then I should see the following claim details

      | LRN     | Claimant's EORI number | Claim type   | Claim status | First MRN | Claim start date | Claim amount requested | Claimant's name | Claimant's email address | Other MRNs included in this claim                                                          |
      | KWMREF1 | GB98745632101          | <claim type> | Pending      | MRN23014  | 1 May 2020       | £900000.00             | Claimant name   | someemail@mail.com       | MRN00001,MRN00002,MRN00003,MRN00004,MRN00005,MRN00006,MRN00007,MRN00008,MRN00009,MRN000010 |


    Examples:
      | user                             | claim ref  | claim type                                     |
      | RTBHsentClaim                    | NDRC-10011 | Rejected goods (C&E1179),Multiple declarations |
      | ReplyToRTBHClaim                 | NDRC-10021 | Rejected goods (C&E1179),Multiple declarations |
      | PendingComplianceCheckQueryClaim | NDRC-10023 | Rejected goods (C&E1179),Multiple declarations |

  Scenario Outline: Display pending status for single declaration claims
    Given I am signed in as a <user> user
    And I navigate to the View and amend home page
    When I click on 'View claims needing more information'
    Then I should see the heading "Claims needing more information"
    And I should see the following claims
      | Claim reference | MRN      | Type of claim                 | Claim start date |
      | <claim ref>     | MRN23014 | Overpayment or rejected goods | 1 May 2020       |
    When I click on '<claim ref>'
    Then I should see the following claim details
      | MRN      | LRN     | Claimant's EORI number | Claim type   | Claim status | Claim start date | Claim amount requested | Claimant's name | Claimant's email address |
      | MRN23014 | KWMREF1 | GB98745632101          | <claim type> | Pending      | 1 May 2020       | £900000.00             | Claimant name   | someemail@mail.com       |

    Examples:
      | user                | claim ref | claim type                            |
      | PendingQueriedClaim | NDRC-1004 | Overpayment (C285),Single declaration |

  Scenario Outline: Display closed status for multiple declarations claims
    Given I am signed in as a <user> user
    And I navigate to the View and amend home page
    When I click on 'View closed claims'
    Then I should see the heading "Claims closed"
    And I should see the following claims
      | Claim reference | MRN      | Type of claim                 | Claim close date | Claim decision   |
      | <claim ref>     | MRN23014 | Overpayment or rejected goods | 1 May 2021       | <claim decision> |
    When I click on '<claim ref>'
    Then I should see the following claim details
      | LRN     | Claimant's EORI number | Claim type   | Claim decision   | First MRN | Claim start date | Claim removal date | Claim amount requested | Claimant's name | Claimant's email address | Other MRNs included in this claim                                                          |
      | KWMREF1 | GB98745632101          | <claim type> | <claim decision> | MRN23014  | 1 May 2020       | 1 May 2021         | £900000.00             | Claimant name   | someemail@mail.com       | MRN00001,MRN00002,MRN00003,MRN00004,MRN00005,MRN00006,MRN00007,MRN00008,MRN00009,MRN000010 |

    Examples:
      | user                        | claim ref  | claim type                                     | claim decision  |
      | ResolvedWithdrawnClaim      | NDRC-1005  | Rejected goods (C&E1179),Multiple declarations | Withdrawn       |
      | ResolvedRejectedClaim       | NDRC-1007  | Rejected goods (C&E1179),Multiple declarations | Rejected        |
      | ResolvedPartialRefusedClaim | NDRC-10015 | Rejected goods (C&E1179),Multiple declarations | Partial Refused |

  Scenario Outline: Display closed status for single declaration claims
    Given I am signed in as a <user> user
    And I navigate to the View and amend home page
    When I click on 'View closed claims'
    Then I should see the heading "Claims closed"
    And I should see the following claims
      | Claim reference | MRN      | Type of claim                 | Claim close date | Claim decision   |
      | <claim ref>     | MRN23014 | Overpayment or rejected goods | 1 May 2021       | <claim decision> |
    When I click on '<claim ref>'
    Then I should see the following claim details
      | MRN      | LRN     | Claimant's EORI number | Claim type   | Claim decision   | Claim start date | Claim removal date | Claim amount requested | Claimant's name | Claimant's email address |
      | MRN23014 | KWMREF1 | GB98745632101          | <claim type> | <claim decision> | 1 May 2020       | 1 May 2021         | £900000.00             | Claimant name   | someemail@mail.com       |

    Examples:
      | user                          | claim ref  | claim type                            | claim decision    |
#      | RejectedFailedValidationClaim | NDRC-1006  | Overpayment (C285),Single declaration | Failed Validation |
      | ResolvedNoReplyClaim          | NDRC-10010 | Overpayment (C285),Single declaration | No Reply          |
      | ResolvedRefusedClaim          | NDRC-10012 | Overpayment (C285),Single declaration | Refused           |
      | ResolvedApprovedClaim         | NDRC-10014 | Overpayment (C285),Single declaration | Approved          |
