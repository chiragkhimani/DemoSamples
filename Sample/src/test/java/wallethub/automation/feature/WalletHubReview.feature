Feature: Post Status

  Scenario: Post Facebook Status
    Given I open wallethub website
    Then I login with username "tuser0317@gmail.com" and password "Test@123" in wallethub app
    When I navigate to test_insurance_company website
    And I select "4" stars for review
    Then verify review page should be displayed
    When I select "Health" from the policy dropdown
    And I write text in review text box
    And I click on submit button
    Then verify review has been posted successfully
