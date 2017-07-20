Feature: Post Status

  Scenario: Post Facebook Status
    Given I open facebook website
    When I login with username "tuser0317@gmail.com" and password "Test@123"
    Then verify homepage should be dispalyed
    When I post status messsae "Hello World"
    Then verify status message should be updated succesfully
