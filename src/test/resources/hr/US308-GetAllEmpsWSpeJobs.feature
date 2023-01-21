Feature: Get All Employees With Specific jobs by job Id API

  @GetAllEmpsWithSpecJobs
  Scenario Outline: Verify User able to retrieve all employees with a specific job
    Given User set <apiName> <region> webservice api
    And User sets Path Parameters <path>
    When User sends GET request
    Then User validates status code <statusCode>
    And User validates response body field
      | [<index>].employee_id | [<index>].first_name | [<index>].email | [<index>].job_id | [<index>].department_id |
      | <empId>               | <fName>              | <empEmail>      | <jobID>          | <depId>                 |

    Examples: 
      | region  | apiName                | statusCode | empId | fName | empEmail            | jobID  | depId | index | path     |
      | "scrum" | "Job:GetAllEmpSpecJob" | "200"      |   290 | Toms  | tomty@afterdark.com | AD_VP  |    80 |    70 | "AD_VP"  |
      | "scrum" | "Job:GetAllEmpSpecJob" | "404"      |   114 | John  | JRUSSEL             | SA_MAN |    80 |     0 | "SA_MAN" |
