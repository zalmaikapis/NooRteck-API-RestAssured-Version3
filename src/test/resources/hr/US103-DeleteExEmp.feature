Feature: Delete Existing Employee API

  Scenario Outline: Verify User able to delete existing employee
    Given User set <apiName> <region> webservice api
    Given User sets Path Parameters <path>
    When User sends DELETE request
    Then User validates status code <statusCode>
    And User validates response body field
      | message   |
      | <message> |
    And User validates cross validates against application database

    @DeleteExisEmp
    Examples: 
      | region  | apiName              | statusCode | requestBody           | message              | path  |
      | "scrum" | "Employee:DeleteEmp" | "200"      | 'EmpTestData:scrum:0' | Successfully deleted | "209" |
    
