Feature: Get All Existing Departments API

  @GetAllExDeps
  Scenario Outline: Verify User able to retrieve ALL existing departments
    Given User set <apiName> <region> webservice api
    When User sends GET request
    Then User validates status code <statusCode>
    And User validates response body field
      | [<index>].department_id | [<index>].department_name | [<index>].manager_id | [<index>].location_id |
      | <depId>                 | <depName>                 | <manId>              | <locId>               |

    Examples: 
      | region  | apiName                | statusCode | depId | depName                | manId | locId | index |
      | "scrum" | "Department:GetAllDep" | "200"      |    50 | Shipping               |   204 |  1500 |     0 |
      | "scrum" | "Department:GetAllDep" | "200"      |   170 | Manufacturing          |       |  1700 |    12 |
      | "scrum" | "Department:GetAllDep" | "200"      |   272 | Torey POSTMAN post req |   123 |  1000 |    24 |
