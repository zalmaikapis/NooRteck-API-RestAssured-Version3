Feature: Get Job History for Employee By Id API

  @GetAllJobHisByID
  Scenario Outline: Verify User able to retrieve an employees job history by id
    Given User set <apiName> <region> webservice api
    And User sets Path Parameters "107"
    When User sends GET request
    Then User validates status code <statusCode>
    And User validates response body field
      | employeeId    | firstName    | lastName    | hisory.startDate | hisory.job.jobTitle |
      | <employee_id> | <first_name> | <last_name> | <job_his_Start>  | <job_his_title>     |

    Examples: 
      | region  | apiName                      | statusCode | employee_id | first_name | last_name | job_his_Start            | job_his_title     |
      | "scrum" | "Employee:GetJobHistoryById" | "200"      |         107 | Diana      | Lorentz   | 1992-02-27T00:00:00.000Z | Public Accountant |
