Feature: Get All Managers & Employees working for them API

  @GetAllManAndEmp
  Scenario Outline: Verify User able retrieve all managers and their employees
    Given User set <apiName> <region> webservice api
    When User sends GET request
    Then User validates status code <statusCode>
    And User validates response body field
      | managers[<manId>].employeeId | managers[<manId>].firstName | managers[<manId>].phoneNumber | managers[<manId>].employees[<empID>].employeeId | managers[<manId>].employees[<empID>].firstName | managers[<manId>].employees[<empID>].email | managers[<manId>].employees[<empID>].phoneNumber |
      | <man_empID>                  | <man_fName>                 | <man_number>                  | <empId>                                         | <emp_FName>                                    | <emp_Email>                                | <emp_number>                                     |

    Examples: 
      | region  | apiName                   | statusCode | man_empID | man_fName | man_number         | empId | emp_FName | emp_Email           | emp_number         | manId | empID |
      | "scrum" | "Employee:GetAllManagers" | "200"      |       103 | Alexander | 590.423.4567       |   250 | Toms      | example@example.com | 424-111-4545       |     0 |     3 |
      | "scrum" | "Employee:GetAllManagers" | "200"      |       145 | John      | 011.44.1344.429268 |   155 | Oliver    | OTUVAULT            | 011.44.1344.486508 |     9 |     5 |
