Feature: Users and user likes and dislikes can be managed

  Scenario: A user can be created
    Given the user "Username 1" does not exist
    When this user is created through REST call
    Then we obtain a 201 status code


