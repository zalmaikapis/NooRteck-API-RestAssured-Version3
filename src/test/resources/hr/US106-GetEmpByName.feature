Feature: Get Existing Employees By Name API

  @GetExEmpByName
  Scenario Outline: Verify User able to retrieve existing employees by name
    Given User set <apiName> <region> webservice api
    And User sets Query Parameters
      | name   |
      | <name> |
    When User sends GET request
    Then User validates status code <statusCode>
    And User validates response body field
      | employees[<ID>].employee_id | employees[<ID>].first_name | employees[<ID>].last_name | employees[<ID>].job_id |
      | <employee_id>               | <first_name>               | <last_name>               | <job_id>               |

    Examples: 
      | region  | apiName                 | statusCode | employee_id | first_name | last_name    | job_id  | ID | name    |
      | "scrum" | "Employee:GetEmpByName" | "200"      |         224 | Nicolas    | HappyNewYear | IT_Prog |  0 | Nicolas |
      | "scrum" | "Employee:GetEmpByName" | "200"      |         246 | Nicolas    | TYler        | AD_VP   |  4 | Nicolas |
      | "scrum" | "Employee:GetEmpByName" | "200"      |         229 | Nicolas    | HappyNewYear | IT_Prog |  3 | Nicolas |
	