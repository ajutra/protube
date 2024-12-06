Feature: Videos can be managed and commented

  Scenario: Successfully upload a video
    Given a user with username "Username 1" exists
    When the user uploads a video file "video.mp4" and a thumbnail "thumbnail.png"
    Then we obtain a 201 status code

  Scenario: A user can like a video
    Given the user "Username 1"
    And an existing video
    When this user likes this video
    Then we obtain a 200 status code

  Scenario: A user can dislike a video
    Given the user "Username 1"
    And an existing video
    When this user dislikes this video
    Then we obtain a 200 status code

  Scenario: A user can delete a like or dislike from a video
    Given the user "Username 1"
    And an existing video
    When this user deletes the like or dislike from this video
    Then we obtain a 200 status code

  Scenario: All videos can be queried
    When we query for all videos
    Then we ensure the result is a list of all videos

  Scenario: A video can be queried by id
    When we query for created video by id
    Then we ensure the result is the queried video

    Scenario: A video can be queried by username
    Given the user "Username 1"
    When we query for videos by username
    Then we ensure the result is a list of videos by this user

  Scenario: A video can be searched with search term
    When we search for videos with search term "Title 1"
    Then we ensure the result is a list of videos with the search term

  Scenario: We can see the acceptance ratio of a video
    Given the user "Username 1"
    And an existing video
    When we query for the acceptance ratio of this video
    Then we obtain a 200 status code

  Scenario: A video can be edited
    Given the user "Username 1"
    And an existing video
    When this user edits the video
    Then we obtain a 200 status code

  Scenario: A video can be commented
    Given the user "Username 1"
    And an existing video
    When this user comments on this video
    Then we obtain a 201 status code

  Scenario: A comment can be edited
    Given the user "Username 1"
    And an existing video
    And a comment
    When this user edits the comment
    Then we obtain a 200 status code

  Scenario: All comments of a video can be queried
    Given an existing video
    When we query for all comments of a video
    Then we ensure the result is a list of all comments of this video

  Scenario: All comments of a user can be queried
    Given the user "Username 1"
    When we query for all comments of this user
    Then we ensure the result is a list of all comments of this user

  Scenario: A comment can be deleted
    Given the user "Username 1"
    And an existing video
    And a comment
    When we delete the comment
    Then we obtain a 200 status code

  Scenario: A video can be deleted
    Given the user "Username 1"
    And an existing video
    When we delete the video
    Then we obtain a 200 status code
