Feature: Get All Existing Jobs & Employees API

  @GetAllJobsAndEmps
  Scenario Outline: Verify User able to retrieve ALL existing jobs and employees
    Given User set <apiName> <region> webservice api
    When User sends GET request
    Then User validates status code <statusCode>
    And User validates response body field
      | <JobID>.jobId | <JobID>.jobTitle | <JobID>.minSalary | <JobID>.maxSalary | <JobID>.employees[<EmpIndex>].employeeId | <JobID>.employees[<EmpIndex>].firstName | <JobID>.employees[<EmpIndex>].salary |
      | <jobId>       | <jobTit>         | <minSal>          | <maxSal>          | <EmployeeID>                             | <EmpFName>                              | <EmpSal>                             |

    Examples: 
      | region  | apiName              | statusCode | jobTit               | minSal | maxSal | jobId   | EmpIndex | EmployeeID | EmpFName  | EmpSal   |
      | "scrum" | "Job:GetAllEmpByJob" | "200"      | Programmer           |   4000 |  10000 | IT_PROG |        0 |        103 | Alexander |  9000.00 |
      | "scrum" | "Job:GetAllEmpByJob" | "200"      | Sales Representative |   6000 | 120000 | SA_REP  |       18 |        168 | Lisa      | 11500.00 |
