Feature: Get Detailed Employee information By ID API

  @GetDetEmpJobHis
  Scenario Outline: Verify User able to retrieve detailed employee information 
    Given User set <apiName> <region> webservice api
    And User sets Path Parameters "224"
    When User sends GET request
    Then User validates status code <statusCode>
    And User validates response body field
      | employee_id | first_name | job.jobId | manager_id | department.departmentId | job.jobTitle | location.streetAddress |
      | <empId>     | <fName>    | <jobId>   | <ManId>    | <depId>                 | <jobTit>     | <locAdd>               |

    Examples: 
      | region  | apiName                       | statusCode | empId | fName   | jobId   | ManId | depId | jobTit     | locAdd              |
      | "scrum" | "Employee:GetEmpDetailedInfo" | "200"      |   224 | Nicolas | IT_Prog |   123 |    50 | Programmer | 2011 Interiors Blvd |
