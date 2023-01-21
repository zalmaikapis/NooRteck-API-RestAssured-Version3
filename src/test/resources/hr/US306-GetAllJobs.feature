Feature: Get All Existing Jobs API

  @GetAllJobs
  Scenario Outline: Verify User able to retrieve ALL existing jobs
    Given User set <apiName> <region> webservice api
    When User sends GET request
    Then User validates status code <statusCode>
    And User validates response body field
      | [<ID>].job_id | [<ID>].job_title | [<ID>].min_salary | [<ID>].max_salary |
      | <jobId>       | <jobTit>         | <minSal>          | <maxSal>          |

    Examples: 
      | region  | apiName         | statusCode | jobId      | jobTit            | minSal | maxSal | ID |
      | "scrum" | "Job:GetAllJob" | "200"      | AC_ACCOUNT | Public Accountant |   4200 |   9000 |  0 |
      | "scrum" | "Job:GetAllJob" | "200"      | SH_CLERK   | Shipping Clerk    |   2500 |   5500 | 28 |
