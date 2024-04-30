@acceptance

Feature: Display backend claims status to in progress, pending and closed categories

  Scenario Outline: Display in-progress status for multiple declarations claims
    Given I am signed in as a <user> user
    And I navigate to the View and amend home page
    When I click on 'View claims in progress'
    Then I should see the heading "Claims in progress"
    And I should see the following claims
      | Claim reference | MRN      | Type of claim                 | Claim submitted date |
      | <claim ref>     | MRN23014 | Overpayment or rejected goods | 1 May 2020       |
    When I click on '<claim ref>'
    Then I should see the following claim details
      | Local Reference Number (LRN)     | Claimant‘s EORI number | Claim type   | Claim status | First MRN | Claim submitted date | Claim amount requested | Claimant‘s name | Claimant‘s email address | Other MRNs included in this claim                                                 |
      | KWMREF1 | GB98745632101          | <claim type> | In progress  | MRN23014  | 1 May 2020       | £900000.00             | Claimant name   | Claimant email address   | MRN00002,MRN00003,MRN00004,MRN00005,MRN00006,MRN00007,MRN00008,MRN00009,MRN000010 |

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
      | Claim reference | MRN      | Type of claim                 | Claim submitted date |
      | <claim ref1>    | MRN23014 | Overpayment or rejected goods | 1 May 2020       |
      | <claim ref2>    | MRN23014 | Overpayment or rejected goods | 1 May 2020       |
    When I click on '<claim ref1>'
    Then I should see the following claim details

      | MRN      | Local Reference Number (LRN)     | Claimant‘s EORI number | Claim type   | Claim status | Claim submitted date | Claim amount requested | Claimant‘s name | Claimant‘s email address |
      | MRN23014 | KWMREF1 | <EORI number1>         | <claim type> | In progress  | 1 May 2020       | £900000.00             | Claimant name   | Claimant email address   |
    When I click on back link to previous page
    Then I should see the heading "Claims in progress"
    When I click on '<claim ref2>'
    Then I should see the following claim details

      | MRN      | Local Reference Number (LRN)     | Claimant‘s EORI number | Claim type   | Claim status | Claim submitted date | Claim amount requested | Claimant‘s name | Claimant‘s email address |
      | MRN23014 | KWMREF1 | <EORI number2>         | <claim type> | In progress  | 1 May 2020       | £900000.00             | Claimant name   | Claimant email address   |


    Examples:
      | user                                 | claim ref1 | claim ref2 | claim type                            | EORI number1  | EORI number2  |
      | openAnalysisClaim                    | NDRC-1002  | NDRC-1502  | Overpayment (C285),Single declaration | GB98745632101 | XI98745632102 |
      | OpenReworkClaim                      | NDRC-1008  | NDRC-1508  | Overpayment (C285),Single declaration | GB98745632101 | XI98745632102 |
      | PendingDecisionLetterClaim           | NDRC-10016 | NDRC-10516 | Overpayment (C285),Single declaration | GB98745632101 | XI98745632102 |
      | AnalysisReworkClaim                  | NDRC-10018 | NDRC-10518 | Overpayment (C285),Single declaration | GB98745632101 | XI98745632102 |
      | PendingComplianceRecommendationClaim | NDRC-10022 | NDRC-10522 | Overpayment (C285),Single declaration | GB98745632101 | XI98745632102 |
      | PendingComplianceCheckClaim          | NDRC-10024 | NDRC-10524 | Overpayment (C285),Single declaration | GB98745632101 | XI98745632102 |


  Scenario Outline: Display pending status for multiple declarations claims
    Given I am signed in as a <user> user
    And I navigate to the View and amend home page
    When I click on 'View claims needing more information'
    Then I should see the heading "Claims needing more information"
    And I should see the following claims
      | Claim reference | MRN      | Type of claim                 | Claim submitted date |
      | <claim ref>     | MRN23014 | Overpayment or rejected goods | 1 May 2020       |
    When I click on '<claim ref>'
    Then I should see the following claim details

      | Local Reference Number (LRN)     | Claimant‘s EORI number | Claim type   | Claim status | First MRN | Claim submitted date | Claim amount requested | Claimant‘s name | Claimant‘s email address | Other MRNs included in this claim                                                 |
      | KWMREF1 | GB98745632101          | <claim type> | Pending      | MRN23014  | 1 May 2020       | £900000.00             | Claimant name   | Claimant email address   | MRN00002,MRN00003,MRN00004,MRN00005,MRN00006,MRN00007,MRN00008,MRN00009,MRN000010 |


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
      | Claim reference | MRN      | Type of claim                 | Claim submitted date |
      | <claim ref1>    | MRN23014 | Overpayment or rejected goods | 1 May 2020       |
      | <claim ref2>    | MRN23014 | Overpayment or rejected goods | 1 May 2020       |
    When I click on '<claim ref1>'
    Then I should see the following claim details
      | MRN      | Local Reference Number (LRN)     | Claimant‘s EORI number | Claim type   | Claim status | Claim submitted date | Claim amount requested | Claimant‘s name | Claimant‘s email address |
      | MRN23014 | KWMREF1 | GB98745632101          | <claim type> | Pending      | 1 May 2020       | £900000.00             | Claimant name   | Claimant email address   |
    When I click on back link to previous page
    Then I should see the heading "Claims needing more information"
    When I click on '<claim ref2>'
    Then I should see the following claim details
      | MRN      | Local Reference Number (LRN)     | Claimant‘s EORI number | Claim type   | Claim status | Claim submitted date | Claim amount requested | Claimant‘s name | Claimant‘s email address |
      | MRN23014 | KWMREF1 | XI98745632102          | <claim type> | Pending      | 1 May 2020       | £900000.00             | Claimant name   | Claimant email address   |

    Examples:
      | user                | claim ref1 | claim ref2 | claim type                            |
      | PendingQueriedClaim | NDRC-1004  | NDRC-1504  | Overpayment (C285),Single declaration |

  Scenario Outline: Display closed status for multiple declarations claims
    Given I am signed in as a <user> user
    And I navigate to the View and amend home page
    When I click on 'View closed claims'
    Then I should see the heading "Claims closed"
    And I should see the following claims
      | Claim reference | MRN      | Type of claim                 | Claim decision date | Claim decision   |
      | <claim ref>     | MRN23014 | Overpayment or rejected goods | 1 May 2021       | <claim decision> |
    When I click on '<claim ref>'
    Then I should see the following claim details
      | Local Reference Number (LRN)     | Claimant‘s EORI number | Claim type   | Claim decision   | First MRN | Claim submitted date | Claim removal date | Claim amount requested | Claimant‘s name | Claimant‘s email address | Other MRNs included in this claim                                                 |
      | KWMREF1 | GB98745632101          | <claim type> | <claim decision> | MRN23014  | 1 May 2020       | 1 May 2021         | £900000.00             | Claimant name   | Claimant email address   | MRN00002,MRN00003,MRN00004,MRN00005,MRN00006,MRN00007,MRN00008,MRN00009,MRN000010 |

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
      | Claim reference | MRN      | Type of claim                 | Claim decision date | Claim decision   |
      | <claim ref1>    | MRN23014 | Overpayment or rejected goods | 1 May 2021       | <claim decision> |
      | <claim ref2>    | MRN23014 | Overpayment or rejected goods | 1 May 2021       | <claim decision> |
    When I click on '<claim ref1>'
    Then I should see the following claim details
      | MRN      | Local Reference Number (LRN)     | Claimant‘s EORI number | Claim type   | Claim decision   | Claim submitted date | Claim removal date | Claim amount requested | Claimant‘s name | Claimant‘s email address |
      | MRN23014 | KWMREF1 | GB98745632101          | <claim type> | <claim decision> | 1 May 2020       | 1 May 2021         | £900000.00             | Claimant name   | Claimant email address   |
    When I click on back link to previous page
    Then I should see the heading "Claims closed"
    When I click on '<claim ref2>'
    Then I should see the following claim details
      | MRN      | Local Reference Number (LRN)     | Claimant‘s EORI number | Claim type   | Claim decision   | Claim submitted date | Claim removal date | Claim amount requested | Claimant‘s name | Claimant‘s email address |
      | MRN23014 | KWMREF1 | XI98745632102          | <claim type> | <claim decision> | 1 May 2020       | 1 May 2021         | £900000.00             | Claimant name   | Claimant email address   |

    Examples:
      | user                  | claim ref1 | claim ref2 | claim type                            | claim decision |
#      | RejectedFailedValidationClaim | NDRC-1006  | NDRC-1506  | Overpayment (C285),Single declaration | Failed Validation |
      | ResolvedNoReplyClaim  | NDRC-10010 | NDRC-10510 | Overpayment (C285),Single declaration | No Reply       |
      | ResolvedRefusedClaim  | NDRC-10012 | NDRC-10512 | Overpayment (C285),Single declaration | Refused        |
      | ResolvedApprovedClaim | NDRC-10014 | NDRC-10514 | Overpayment (C285),Single declaration | Approved       |
