Feature: Users and user likes and dislikes can be managed

  Scenario: A user can be created
    Given the user "New user" does not exist
    When this user is created through REST call
    Then we obtain a 201 status code

  Scenario: A user can like a video
    Given the user "New user"
    And an existing video
    When this user likes this video
    Then we obtain a 200 status code

  Scenario: A user can dislike a video
    Given the user "New user"
    And an existing video
    When this user dislikes this video
    Then we obtain a 200 status code

  Scenario: A user can delete a like or dislike from a video
    Given the user "New user"
    And an existing video
    When this user deletes the like or dislike from this video
    Then we obtain a 200 status code
