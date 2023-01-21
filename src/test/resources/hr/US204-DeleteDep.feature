Feature: Delete Existing Department
@DeleteDep
  Scenario Outline: Verify User able to delete a department
    Given User set <apiName> <region> webservice api
    Given User sets Path Parameters <path>
    When User sends DELETE request
    Then User validates status code <statusCode>
    And User validates response body field
      | message   |
      | <message> |
    And User validates cross validates against application database

    
    Examples: 
      | region  | apiName                | statusCode | message              | path |
      | "scrum" | "Department:DeleteDep" | "200"      | Deleted successfully | "273" |
      | "scrum" | "Department:DeleteDep" | "200"      | Deleted successfully | "274" |
