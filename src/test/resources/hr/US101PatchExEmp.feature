Feature: Update Existing Employee API

  Scenario Outline: Verify User able to partially update existing employee
    Given User set <apiName> <region> webservice api
    When User sets Header Parameters
      | Content-Type     |
      | application/json |
    And User sets request body <requestBody>
    When User sends PATCH request
    Then User validates status code <statusCode>
    And User validates response body field
      | message   |
      | <message> |
    And User validates cross validates against application database

    @PatchExEmp
    Examples: 
      | region  | apiName                     | statusCode | requestBody           | message              |
      | "scrum" | "Employee:PatchExistingEmp" | "200"      | 'EmpTestData:scrum:0' | Successfully updated |
