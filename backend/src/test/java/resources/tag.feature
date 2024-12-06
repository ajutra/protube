Feature: Tags can be managed

  Scenario: A tag can be created
    Given the tag "New tag" does not exist
    When this tag is created through REST call
    Then we obtain a 201 status code


  Scenario: A tag can be queried by name
    When we query for tag "New tag"
    Then we ensure the result is the queried tag


  Scenario: All tags can be queried
    When we query for all tags
    Then we ensure the result is a list of all tags
