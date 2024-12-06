Feature: Users and user likes and dislikes can be managed

  Scenario: A user can be registered
    Given the user "Username 1" does not exist
    When this user is created through REST call
    Then we obtain a 201 status code

  Scenario: A user can be logged in
    Given the user "Username 1" exists
    When this user logs in through REST call
    Then we obtain a 200 status code