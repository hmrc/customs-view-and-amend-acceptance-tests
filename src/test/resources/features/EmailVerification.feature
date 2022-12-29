@acceptance
Feature: Email Verification

  Scenario: User without a verified email address is redirected to email verification page
    Given I am signed in as an unverifiedUser user
    When I navigate to the View and amend home page
    Then I am redirected to the "You need to verify the registered CDS email address - Claim back import duty and VAT - GOV.UK" page
    And I should see the heading "You need to verify the registered CDS email address"
    And the "Verify or change email address" url is correct
    And I should see the following static text
      | This is the email address your organisation has registered for the Customs Declaration Service (CDS). You need to verify this email address or change it. You can verify the email instantly provided you have access to the email account. |
      | This will be the only email address we use for:                                                                                                                                                                                             |
    And I should see the following text in bullet points
      | updates on changes to the Customs Declaration Service                |
      | urgent updates about goods in customs                                |
      | some financial notifications, including Direct Debit notices and VAT |

  Scenario: Display undeliverable email page
    Given the customs data store is empty
    And the user has a verified email address in the data store
    And the email is undeliverable
    And I sign in as an undeliverableEmail user
    When I navigate to the View and amend home page
    Then the page title should be "There's a problem with the CDS registered email address - Claim back import duty and VAT - GOV.UK"
    And I should see the heading "There's a problem with the CDS registered email address"
    And I should see the following static text
      | We tried to send you an email but it could not be delivered. This could be because the inbox is full, or due to a technical problem with your email provider.                                                                               |
      | This is the email address your organisation has registered for the Customs Declaration Service (CDS). You need to verify this email address or change it. You can verify the email instantly provided you have access to the email account. |
      | This will be the only email address we use for:                                                                                                                                                                                             |
    And I should see the following text in bullet points
      | updates on changes to the Customs Declaration Service                |
      | urgent updates about goods in customs                                |
      | some financial notifications, including Direct Debit notices and VAT |
    And I should see a link to "Verify or change email address"
    And the "Verify or change email address" url is correct