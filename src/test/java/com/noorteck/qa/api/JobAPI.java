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

public class JobAPI {

	public static void jobFieldLevelValidation(DataTable dataTable) {
		switch (Constants.apiRequestConfigTestDataMap.get("apiName")) {
		case "PostNewJob":
		case "PutExistingJob":
		case "PatchJobSalary":
		case "PatchJobTitle":
		case "DeleteJob":
			verifyResponseField(dataTable);
			break;
		}
	}

	public static void verifyResponseField(DataTable dataTable) {
		List<Map<String, String>> listMap = dataTable.asMaps(String.class, String.class);
		JsonPath jsonPath = Constants.response.jsonPath();

		for (Map<String, String> map : listMap) {

			for (Map.Entry<String, String> entry : map.entrySet()) {
				String expKey = entry.getKey(); // message for this case
				String expValue = entry.getValue();
				String actValue = jsonPath.getString(expKey); // extract actual response field

				System.out.println(actValue);

				compareTwoValues(actValue, expValue);
			}
		}

	}

	public static void jobDBLevelValidation() {
		switch (Constants.apiRequestConfigTestDataMap.get("apiName")) {
		case "PostNewJob":
			verifyAgainstDB();
			break;

		case "PutExistingJob":
			verifyAgainstDB();
			break;

		case "PatchJobSalary":
			verifyAgainstDBPatchSal();
			break;
		case "PatchJobTitle":
			verifyAgainstDBPatchJobTitle();
			break;

		case "DeleteJob":
			verifyAgainstDBDeleteJob();
			break;
		}

	}

	public static void verifyAgainstDBDeleteJob() {
		List<LinkedHashMap<String, String>> actResultMaps = new ArrayList<>();

		String query = "SELECT * FROM hr_scrum.jobs WHERE job_id = '" + Constants.requestBodyMap.get("jobId") + "'"
				+ ";";

		// connect to database
		actResultMaps = DBUtils.makeDBRequest(query);

		boolean isEmpty = actResultMaps.isEmpty();
		System.err.println(isEmpty);
		Constants.apiSoftAssert.assertEquals(isEmpty, true);

	}

	public static void verifyAgainstDBPatchJobTitle() {
		List<LinkedHashMap<String, String>> actResultMaps = new ArrayList<>();

		String query = "SELECT * FROM hr_scrum.jobs WHERE job_id = '" + Constants.requestBodyMap.get("jobId") + "'"
				+ ";";

		// connect to database
		actResultMaps = DBUtils.makeDBRequest(query);
		for (LinkedHashMap<String, String> actResultMap : actResultMaps) {
			// SQl // JobTestData
			compareTwoValues(actResultMap.get("job_id"), Constants.requestBodyMap.get("jobId"));

			compareTwoValues(actResultMap.get("job_title"), Constants.requestBodyMap.get("jobTitle"));
		}
	}

	public static void verifyAgainstDBPatchSal() {
		List<LinkedHashMap<String, String>> actResultMaps = new ArrayList<>();

		String query = "SELECT * FROM hr_scrum.jobs WHERE job_id = '" + Constants.requestBodyMap.get("jobId") + "'"
				+ ";";

		// connect to database
		actResultMaps = DBUtils.makeDBRequest(query);
		for (LinkedHashMap<String, String> actResultMap : actResultMaps) {
			// SQl // JobTestData
			compareTwoValues(actResultMap.get("job_id"), Constants.requestBodyMap.get("jobId"));

			Double actDoubleMinSal = Double.parseDouble(actResultMap.get("min_salary"));
			BigDecimal bigDecimalMinSal = (BigDecimal) Constants.requestBodyMap.get("minSalary");
			compareTwoValues(actDoubleMinSal, bigDecimalMinSal.doubleValue());

			Double actDoubleMaxSal = Double.parseDouble(actResultMap.get("max_salary"));
			BigDecimal bigDecimalMaxSal = (BigDecimal) Constants.requestBodyMap.get("maxSalary");
			compareTwoValues(actDoubleMaxSal, bigDecimalMaxSal.doubleValue()); // done
		}
	}

	public static void verifyAgainstDB() {
		List<LinkedHashMap<String, String>> ActResultMaps = new ArrayList<>();

		String query = "SELECT * FROM hr_scrum.jobs WHERE job_id = '" + Constants.requestBodyMap.get("jobId") + "'"
				+ ";";

		// connect to database
		ActResultMaps = DBUtils.makeDBRequest(query);
		for (LinkedHashMap<String, String> actResultMap : ActResultMaps) {

			// SQL // JobTestData
			compareTwoValues(actResultMap.get("job_id"), Constants.requestBodyMap.get("jobId"));

			compareTwoValues(actResultMap.get("job_title"), Constants.requestBodyMap.get("jobTitle"));

			Double actDoubleMinSal = Double.parseDouble(actResultMap.get("min_salary"));
			BigDecimal bigDecimalMinSal = (BigDecimal) Constants.requestBodyMap.get("minSalary");
			compareTwoValues(actDoubleMinSal, bigDecimalMinSal.doubleValue());

			Double actDoubleMaxSal = Double.parseDouble(actResultMap.get("max_salary"));
			BigDecimal bigDecimalMaxSal = (BigDecimal) Constants.requestBodyMap.get("maxSalary");
			compareTwoValues(actDoubleMaxSal, bigDecimalMaxSal.doubleValue());

			/**
			 * Double actDoubleMinSal = Double.parseDouble(actResultMap.get("min_salary"));
			 * Double doubleMinSal = Double.valueOf((Integer)Constants.requestBodyMap.get("minSalary")) ;
			 * compareTwoValues(actDoubleMinSal, doubleMinSal);
			 */ // -- > use this when the testdata is integer 1000 for salary
		}
	}

	public static void compareTwoValues(Object actualValue, Object expValue) {
		Constants.apiSoftAssert.assertEquals(actualValue, expValue);
	}

}
