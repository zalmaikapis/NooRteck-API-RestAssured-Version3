Feature: Get all job History for all Employees API

  @GetAllJobHis
  Scenario Outline: Verify User able to retrieve all the employees job history
    Given User set <apiName> <region> webservice api
    When User sends GET request
    Then User validates status code <statusCode>
    And User validates response body field
      | [<ID>].employeeId | [<ID>].firstName | [<ID>].lastName | [<ID>].hisory.startDate | [<ID>].hisory.job.jobTitle |
      | <employee_id>     | <first_name>     | <last_name>     | <job_his_Start>         | <job_his_title>            |

    Examples: 
      | region  | apiName                     | statusCode | employee_id | first_name | last_name | job_his_Start            | job_his_title    | ID |
      | "scrum" | "Employee:GetAllJobHistory" | "200"      |         103 | Alexander  | Hunold    | 1986-12-31T00:00:00.000Z | Purchasing Clerk |  0 |
      | "scrum" | "Employee:GetAllJobHistory" | "200"      |         195 | Vance      | Jones     | 1993-12-21T00:00:00.000Z | Stock Manager    | 85 |
