Feature: Update Existing Job Title API

  Scenario Outline: Verify User able to update an existing Job Title
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

    @PatchJobTitle
    Examples: 
      | region  | apiName             | statusCode | requestBody           | message              |
      | "scrum" | "Job:PatchJobTitle" | "200"      | 'JobTestData:scrum:0' | Successfully updated |
