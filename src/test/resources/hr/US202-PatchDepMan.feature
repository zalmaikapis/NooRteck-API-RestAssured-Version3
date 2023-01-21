Feature: Patch Existing Department Manager ID API

  Scenario Outline: Verify User able to update managerID of a department
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

    @PatchDepMan
    Examples: 
      | region  | apiName                      | statusCode | requestBody           | message              |
      | "scrum" | "Department:PatchManagerDep" | "200"      | 'DepTestData:scrum:0' | Updated successfully |
