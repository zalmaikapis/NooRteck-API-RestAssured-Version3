package com.noorteck.qa.api;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.noorteck.qa.utils.Constants;
import com.noorteck.qa.utils.DBUtils;

import io.cucumber.datatable.DataTable;
import io.restassured.path.json.JsonPath;

public interface DepartmentAPI {

	public static void depFieldLevelValidation(DataTable dataTable) {
		switch (Constants.apiRequestConfigTestDataMap.get("apiName")) {

		case "PostNewDep":
		case "PatchManagerDep":
		case "PutExistingDep":
		case "PatchNameDep":
		case "DeleteDep":

			verifyResponseField(dataTable);
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

	public static void depDBLevelValidation() {
		switch (Constants.apiRequestConfigTestDataMap.get("apiName")) {

		case "PostNewDep":
			JsonPath jsonPath = Constants.response.jsonPath();
			Constants.requestBodyMap.put("departmentId", jsonPath.getInt("departmentId"));
			verifyAgainstDB();
			break;
		case "PatchManagerDep":

			verifyAgainstDBPatchDepManager();
			break;
		case "PutExistingDep":

			verifyAgainstDB();
			break;

		case "PatchNameDep":
			verifyAgainstDBPatchDepName();
			break;

		case "DeleteDep":
			verifyAgainstDBDelete();
			break;
		}
	}

	public static void verifyAgainstDBPatchDepName() {
		List<LinkedHashMap<String, String>> actResultMaps = new ArrayList<>();

		String query = "SELECT * FROM hr_scrum.departments WHERE department_id = '"
				+ Constants.requestBodyMap.get("departmentId") + "'" + ";";

		// connect to database
		actResultMaps = DBUtils.makeDBRequest(query);
		for (LinkedHashMap<String, String> actResultMap : actResultMaps) {

			Integer actIntegerDepId = Integer.parseInt(actResultMap.get("department_id"));
			compareTwoValues(actIntegerDepId, Constants.requestBodyMap.get("departmentId"));

			compareTwoValues(Constants.requestBodyMap.get("departmentName"), actResultMap.get("department_name"));
		}
	}

	public static void verifyAgainstDBPatchDepManager() {
		List<LinkedHashMap<String, String>> actResultMaps = new ArrayList<>();

		String query = "SELECT * FROM hr_scrum.departments WHERE department_id = '"
				+ Constants.requestBodyMap.get("departmentId") + "'" + ";";

		// connect to database
		actResultMaps = DBUtils.makeDBRequest(query);
		for (LinkedHashMap<String, String> actResultMap : actResultMaps) {

			Integer actIntegerDepId = Integer.parseInt(actResultMap.get("department_id"));
			compareTwoValues(actIntegerDepId, Constants.requestBodyMap.get("departmentId"));

			Integer actIntegerManID = Integer.parseInt(actResultMap.get("manager_id"));
			compareTwoValues(actIntegerManID, Constants.requestBodyMap.get("managerId"));
		}
	}

	public static void verifyAgainstDBDelete() {
		List<LinkedHashMap<String, String>> actResultMaps = new ArrayList<>();

		String query = "SELECT * FROM hr_scrum.departments WHERE department_id = '"
				+ Constants.requestBodyMap.get("departmentId") + "'" + ";";

		// connect to database
		actResultMaps = DBUtils.makeDBRequest(query);

		boolean isEmpty = actResultMaps.isEmpty();
		System.err.println(isEmpty);
		Constants.apiSoftAssert.assertEquals(isEmpty, true);

	}

	public static void verifyAgainstDB() {
		List<LinkedHashMap<String, String>> actResultMaps = new ArrayList<>();

		String query = "SELECT * FROM hr_scrum.departments WHERE department_id = '"
				+ Constants.requestBodyMap.get("departmentId") + "'" + ";";

		// connect to database
		actResultMaps = DBUtils.makeDBRequest(query);
		for (LinkedHashMap<String, String> actResultMap : actResultMaps) {

	
			Integer actIntegerDepId = Integer.parseInt(actResultMap.get("department_id"));
			compareTwoValues(actIntegerDepId, Constants.requestBodyMap.get("departmentId"));

			compareTwoValues(actResultMap.get("department_name"), Constants.requestBodyMap.get("departmentName"));

			Integer actIntegerManID = Integer.parseInt(actResultMap.get("manager_id"));
			compareTwoValues(actIntegerManID, Constants.requestBodyMap.get("managerId"));

			Integer actIntegerLocID = Integer.parseInt(actResultMap.get("location_id"));
			compareTwoValues(actIntegerLocID, Constants.requestBodyMap.get("locationId"));

		}

	}

	public static void compareTwoValues(Object actualValue, Object expValue) {
		Constants.apiSoftAssert.assertEquals(actualValue, expValue);
	}
}
