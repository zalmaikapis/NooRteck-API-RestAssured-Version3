Feature: Delete Existing Job

  Scenario Outline: Verify User able to delete an existing Job 
    Given User set <apiName> <region> webservice api
    Given User sets Path Parameters '00'
    When User sends DELETE request
    Then User validates status code <statusCode>
    And User validates response body field
      | message   |
      | <message> |
    And User validates cross validates against application database

    @DeleteJob
    Examples: 
      | region  | apiName         | statusCode | message              |
      | "scrum" | "Job:DeleteJob" | "200"      | Successfully deleted |
