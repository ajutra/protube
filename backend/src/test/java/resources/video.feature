Feature: Videos can be managed

  Scenario: Successfully upload a video
    Given a user with username "Username 1" exists
    When the user uploads a video file "video.mp4" and a thumbnail "thumbnail.png" with title "title1"
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
    Then we obtain a 200 status code

  Scenario: A video can be queried by id
    When we query for created video by id
    Then we obtain a 200 status code

    Scenario: A video can be queried by username
    Given the user "Username 1"
    When we query for videos by username
    Then we obtain a 200 status code

  Scenario: A video can be searched with search term
    When we search for videos with search term "title1"
    Then we obtain a 200 status code