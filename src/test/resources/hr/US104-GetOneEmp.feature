Feature: Get One Existing Employee by id API

  @GetOneExEmp
  Scenario Outline: Verify User able to retrieve one existing employee
    Given User set <apiName> <region> webservice api
    Given User sets Query Parameters
      | id            |
      | <employee_id> |
    When User sends GET request
    Then User validates status code <statusCode>
    And User validates response body field
      | first_name   | last_name   | job_id   |
      | <first_name> | <last_name> | <job_id> |

    Examples: 
      | region  | apiName              | statusCode | employee_id | first_name | last_name    | job_id  |
      | "scrum" | "Employee:GetOneEmp" | "200"      |         2240 | Nicolas    | HappyNewYear | IT_Prog |
