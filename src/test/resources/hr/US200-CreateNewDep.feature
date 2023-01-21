Feature: Create New Department API

  Scenario Outline: Verify User able to create new department
    Given User set <apiName> <region> webservice api
    When User sets Header Parameters
      | Content-Type     |
      | application/json |
    And User sets request body <requestBody>
    When User sends POST request
    Then User validates status code <statusCode>
    And User validates response body field
      | message   |
      | <message> |
    And User validates cross validates against application database

    @PostDepTest
    Examples: 
      | region  | apiName                 | statusCode | requestBody           | message |
      | "scrum" | "Department:PostNewDep" | "201"      | 'DepTestData:scrum:0' | Created |
     