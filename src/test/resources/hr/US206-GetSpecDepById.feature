Feature: Get Specific Department by Id API

  @GetSpecDeps
  Scenario Outline: Verify User able to retrieve specific department
    Given User set <apiName> <region> webservice api
    And User sets Path Parameters <path>
    When User sends GET request
    Then User validates status code <statusCode>
    And User validates response body field
      | message   | <depId>.department_id | <depId>.department_name | <depId>.manager_id | <depId>.location_id |
      | <message> | <departmentId>        | <depName>               | <ManId>            | <locId>             |

    Examples: 
      | region  | apiName                 | statusCode | message | departmentId | depName | ManId | locId | path |
      | "scrum" | "Department:GetSpecDep" | "200"      | Success |           80 | Sales   |   145 |  2500 | "80" |
      | "scrum" | "Department:GetSpecDep" | "200"      | Success |           60 | IT      |   103 |  1400 | "60" |
