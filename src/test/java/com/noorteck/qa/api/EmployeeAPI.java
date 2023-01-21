package com.noorteck.qa.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.noorteck.qa.utils.Constants;
import com.noorteck.qa.utils.DBUtils;

import io.cucumber.datatable.DataTable;
import io.restassured.path.json.JsonPath;

public class EmployeeAPI {

	public static void empFieldLevelValidation(DataTable dataTable) {
//		switch (Constants.apiRequestConfigTestDataMap.get("apiName")) {
//		case "PostNewEmp":
//		case "PutExistingEmp":
//		case "PatchExistingEmp":
//		case "DeleteEmp":
//			verifyResponseField(dataTable);
//			break;
//		}
		verifyResponseField(dataTable);
	}

	public static void empDBLevelValidation() {
		switch (Constants.apiRequestConfigTestDataMap.get("apiName")) {
		case "PostNewEmp":
			JsonPath jsonPath = Constants.response.jsonPath();
			Constants.requestBodyMap.put("employeeId", jsonPath.getInt("id"));
			verifyAgainstDB();
			break;

		case "PutExistingEmp":
			verifyAgainstDB();
			break;

		case "PatchExistingEmp":
			verifyAgainstDBPatch();
			break;

		case "DeleteEmp":
			verifyAgainstDBDelete();
			break;
	 
		}
	}

	public static void verifyResponseField(DataTable dataTable) {
		List<Map<String, String>> listMap = dataTable.asMaps(String.class, String.class);
		JsonPath jsonPath = Constants.response.jsonPath();

		for (Map<String, String> map : listMap) {

			for (Map.Entry<String, String> entry : map.entrySet()) {
				String expKey = entry.getKey();
				String expValue = entry.getValue();
				String actValue = jsonPath.getString(expKey);

				System.out.println(actValue);

				compareTwoValues(actValue, expValue);
			}
		}
	}

	public static void verifyAgainstDBDelete() {
		List<LinkedHashMap<String, String>> actResultMaps = new ArrayList<>();

		String query = "SELECT * FROM hr_scrum.employees WHERE employee_id = "
				+ Constants.requestBodyMap.get("employeeId") + ";";

		// connect to database
		actResultMaps = DBUtils.makeDBRequest(query);

		boolean isEmpty = actResultMaps.isEmpty();
		System.err.println(isEmpty);
		Constants.apiSoftAssert.assertEquals(isEmpty, true);

	}

	public static void verifyAgainstDBPatch() {
		List<LinkedHashMap<String, String>> actResultMaps = new ArrayList<>();

		String query = "SELECT * FROM hr_scrum.employees WHERE employee_id = "
				+ Constants.requestBodyMap.get("employeeId") + ";";

		// connect to database
		actResultMaps = DBUtils.makeDBRequest(query);
		for (LinkedHashMap<String, String> actResultMap : actResultMaps) {

			Integer actIntegerEmployeeId = Integer.parseInt(actResultMap.get("employee_id"));
			compareTwoValues(actIntegerEmployeeId, Constants.requestBodyMap.get("employeeId"));

			compareTwoValues(actResultMap.get("first_name"), Constants.requestBodyMap.get("firstName"));

			compareTwoValues(actResultMap.get("last_name"), Constants.requestBodyMap.get("lastName"));

			compareTwoValues(actResultMap.get("phone_number"), Constants.requestBodyMap.get("phoneNumber"));

		}

	}

	public static void verifyAgainstDB() {
		List<LinkedHashMap<String, String>> actResultMaps = new ArrayList<>();

		String query = "SELECT * FROM hr_scrum.employees WHERE employee_id = "
				+ Constants.requestBodyMap.get("employeeId") + ";";

		// connect to database
		actResultMaps = DBUtils.makeDBRequest(query);
		for (LinkedHashMap<String, String> actResultMap : actResultMaps) {

			Integer actIntegerEmployeeId = Integer.parseInt(actResultMap.get("employee_id"));
			compareTwoValues(actIntegerEmployeeId,Constants.requestBodyMap.get("employeeId"));
			
			compareTwoValues(actResultMap.get("first_name"),Constants.requestBodyMap.get("firstName"));
			
			compareTwoValues(actResultMap.get("last_name"),Constants.requestBodyMap.get("lastName"));
			
			compareTwoValues( actResultMap.get("email").toLowerCase(),Constants.requestBodyMap.get("email").toString().toLowerCase());
			
			compareTwoValues(actResultMap.get("phone_number"),Constants.requestBodyMap.get("phoneNumber"));
			
			boolean dateComparison = compareDates(Constants.requestBodyMap.get("hireDate").toString(), actResultMap.get("hire_date"));
			Constants.apiSoftAssert.assertEquals(dateComparison, true);
			// compareTwoValues(actResultMap.get("hire_date"),Constants.requestBodyMap.get("hireDate")));
			
			compareTwoValues(actResultMap.get("job_id"),Constants.requestBodyMap.get("jobId").toString());
			
			Double actDoubleCommissionPct = Double.parseDouble(actResultMap.get("commission_pct"));
			String expCommissionPct = Constants.requestBodyMap.get("comissionPct").toString();
			compareTwoValues(actDoubleCommissionPct, Double.valueOf(expCommissionPct));
			//BigDecimal bigDecimalCommission = (BigDecimal) Constants.requestBodyMap.get("comissionPct");
			//compareTwoValues(actDoubleCommissionPct,bigDecimalCommission.doubleValue());
	
			Integer actIntegerManagerId = Integer.parseInt(actResultMap.get("manager_id"));
			compareTwoValues(actIntegerManagerId,Constants.requestBodyMap.get("managerId"));
		
			Integer actIntegerDepartmentId = Integer.parseInt(actResultMap.get("department_id"));
			compareTwoValues(actIntegerDepartmentId,Constants.requestBodyMap.get("departmentId"));
			
			Double actDoubleSalary = Double.parseDouble(actResultMap.get("salary"));
			Double expDoubleSalary = Double.valueOf(Constants.requestBodyMap.get("salary").toString()); // 100.5  => 100.0
			compareTwoValues(actDoubleSalary, expDoubleSalary);
		}

	}

	public static boolean compareDates(String expDate, String actDate) {
		// US format
		String expDateArr[] = expDate.split("/");

		String expDay = expDateArr[1];
		String expMonth = expDateArr[0];
		String expYear = expDateArr[2];

		String actDateArr[] = actDate.split("-");

		String actDay = actDateArr[2];
		String actMonth = actDateArr[1];
		String actYear = actDateArr[0];

		return (expDay.equals(actDay) && expMonth.equals(actMonth) && expYear.equals(actYear));
	}

	public static void compareTwoValues(Object actualValue, Object expValue) {
		Constants.apiSoftAssert.assertEquals(actualValue, expValue);
	}

}
