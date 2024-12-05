Feature: Categories can be managed

  Scenario: A category can be created
    Given the category "New category" does not exist
    When this category is created through REST call
    Then we obtain a 201 status code


  Scenario: A category can be queried by name
    When we query for category "New category"
    Then we obtain a 200 status code


  Scenario: All categories can be queried
    When we query for all categories
    Then we obtain a 200 status code
