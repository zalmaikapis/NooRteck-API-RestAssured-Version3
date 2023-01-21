package com.noorteck.qa.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.given;

import org.apache.commons.io.FileSystemUtils;
import org.json.JSONException;
import org.json.JSONObject;

import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.Json;
import io.cucumber.datatable.DataTable;
import io.restassured.http.ContentType;

public class APIUtils extends Constants {

	/**
	 * This method sets up the header part before sending the request
	 * 
	 * @param dataTable
	 */
	public static void setUpHeaders(DataTable dataTable) {
		Constants.headerMap= new HashMap<String, String>();
		// Convert datatable to List of Map
		List<Map<String, String>> listMap = dataTable.asMaps(String.class, String.class);

		// Convert List Map to a Map by Looping through
		Map<String, String> map = listMap.get(0);
		for (Map.Entry<String, String> mapEntry : map.entrySet()) {
			headerMap.put(mapEntry.getKey(), mapEntry.getValue());
		}

		if (headerMap != null) {
			request = request.headers(headerMap);
		}
	}

	/**
	 * This method sets up the queryParameter part before sending the request
	 * 
	 * @param dataTable
	 */
	public static void setUpQueryParams(DataTable dataTable) {
		// Convert datatable to List of Map
		List<Map<String, String>> listMap = dataTable.asMaps(String.class, String.class);

		// Convert List Map to a Map by Looping through
		// System.err.println("&&&&QueryParams list map : "+listMap);
		queryParamMap = new HashMap();
		Map<String, String> map = listMap.get(0);
		for (Map.Entry<String, String> mapEntry : map.entrySet()) {
			queryParamMap.put(mapEntry.getKey(), mapEntry.getValue());
		}

//		System.err.println(listMap.get(0).get("name"));
//		queryParamMap.put("name", listMap.get(0).get("name"));
//		
//		System.out.println(" query map "+queryParamMap);

		if (queryParamMap != null) {
			request = request.queryParams(queryParamMap);

		}
	}
	
	/*
	 * This method adds a path param to the endpoint
	 */
	public static void setUpPathParams(String pathValue) {

		if (pathValue != null) {
			request = request.pathParam("id", pathValue);

		}
	}
	
	

	/**
	 * This method sends GET Request to server
	 */
	public static void getRequest() {
		
		response = 
				request
				//.log().all()
				.when()
					.get(apiRequestConfigTestDataMap.get("endpoint"))
				.then()
				
					.extract()
					.response();
		
		
		//response.prettyPrint();
		jsonPath = response.jsonPath();
	}
	

	/**
	 * This method sends POST Request to server
	 */
	public static void postRequest() {
			System.out.println(apiRequestConfigTestDataMap.get("requestBody"));
		response = 
				request
			
					.body(apiRequestConfigTestDataMap.get("requestBody"))
				.when()
					.post(apiRequestConfigTestDataMap.get("endpoint"))
				.then()
					.extract()
					.response();
		
		response.prettyPrint();
	}
	
	/**
	 * This method sends PATCH Request to server
	 */
	public static void patchRequest() {
		
		response = 
				request
			
					.body(apiRequestConfigTestDataMap.get("requestBody"))
				.when()
					.patch(apiRequestConfigTestDataMap.get("endpoint"))
				.then()
					.extract()
					.response();
		
		response.prettyPrint();
		jsonPath = response.jsonPath();
	}

	/**
	 * This method sends PUT Request to server
	 */
	public static void putRequest() {
			
		response = 
				request
			
					.body(apiRequestConfigTestDataMap.get("requestBody"))
				.when()
					.put(apiRequestConfigTestDataMap.get("endpoint"))
				.then()
					.extract()
					.response();
		
		response.prettyPrint();
		jsonPath = response.jsonPath();
	}
	
	/**
	 * This method sends DELETE Request to server
	 */
	public static void deleteRequest() {
			
		response = 
				request
				.when()
					.delete(apiRequestConfigTestDataMap.get("endpoint"))
				.then()
					.extract()
					.response();
		
		response.prettyPrint();
		jsonPath = response.jsonPath();
	}
	


	/**
	 * This method returns the status code
	 * 
	 * @return
	 */
	public static int statusCode() {
		System.out.println("Status Code: " + response.getStatusCode());
		return response.getStatusCode();
	}
	
	
	public static void loadApiInfo(String apiName, String region) {
		Constants.request = given();
		String baseUrl = null;
		String uri = null;
		String endpoint = null;

		envDataMap = JsonFileUtils.filterJson(region, prop.getProperty("Environment"), null);

		// Employee:PostNewEmp

		String apiInfo[] = apiName.split(":");

		hrApiEnvDataMap = JsonFileUtils.filterJson(apiInfo[0], prop.getProperty("HRApiServerData"), null);

		/**
		 * From hrApiEnvDataMap retrieve the baseUrl and the correct api path
		 */

		switch (region) {

		case "scrum":
			baseUrl = (String) hrApiEnvDataMap.get("baseScrumURL");
			uri = (String) hrApiEnvDataMap.get(apiInfo[1]);
			break;
		case "sit":
			baseUrl = (String) hrApiEnvDataMap.get("baseSitURL");
			uri = (String) hrApiEnvDataMap.get(apiInfo[1]);
			break;
		}

		endpoint = baseUrl + uri;
		apiRequestConfigTestDataMap.put("endpoint", endpoint);

		apiRequestConfigTestDataMap.put("moduloName", apiInfo[0]);

		apiRequestConfigTestDataMap.put("apiName", apiInfo[1]);

	}
	
	public static void setRequestBodyTestData(String regionName, String testDataFileName, Integer index)throws JSONException {
       	requestBodyMap = JsonFileUtils.filterJson(regionName, prop.getProperty(testDataFileName), index);

		String requestBody = "";

		switch (apiRequestConfigTestDataMap.get("apiName")) {

		case "PostNewEmp":

			requestBody = getRequestBody(prop.getProperty("EmpPostRBody"), requestBodyMap);
			break;

		case "PatchExistingEmp":
			requestBody = getRequestBody(prop.getProperty("EmpPatchRBody"), requestBodyMap);
			break;

		case "PutExistingEmp":
			requestBody = getRequestBody(prop.getProperty("EmpPutRBody"), requestBodyMap);
			break;

		case "PostNewJob":
			requestBody = getRequestBody(prop.getProperty("JobPostRBody"), requestBodyMap);
			break;

		case "PatchJobTitle":
			requestBody = getRequestBody(prop.getProperty("JobPatchTitleRBody"), requestBodyMap);
			break;

		case "PatchJobSalary":
			requestBody = getRequestBody(prop.getProperty("JobPatchSalaryRBody"), requestBodyMap);
			break;

		case "PutExistingJob":
			requestBody = getRequestBody(prop.getProperty("JobPutRBody"), requestBodyMap);
			break;

		case "PostNewDep":

			requestBody = getRequestBody(prop.getProperty("DepPostRBody"), requestBodyMap);
			break;

		case "PatchManagerDep":
			requestBody = getRequestBody(prop.getProperty("DepPatchManagerRBody"), requestBodyMap);
			break;

		case "PatchNameDep":
			requestBody = getRequestBody(prop.getProperty("DepPatchNameRBody"), requestBodyMap);
			break;

		case "PutExistingDep":
			requestBody = getRequestBody(prop.getProperty("DepPutRBody"), requestBodyMap);
			break;
		}

		apiRequestConfigTestDataMap.put("requestBody", requestBody);

	}
	

	/**
	 * This method takes requestBodyFile template and takes testdata for request body
	 * then then adds the test data to request body template and returns as string 
	 * @param requestBodyFileTempaltePath
	 * @param testDataMap
	 * @return
	 * @throws JSONException
	 */
	
	public static String getRequestBody(String requestBodyFileTempaltePath, Map<String, Object> testDataMap)
			throws JSONException {

		String jsonToString = JsonFileUtils.jsonToStrConvertion(requestBodyFileTempaltePath);

		// Convert to JSON object
		JSONObject obj = new JSONObject(jsonToString);
		System.out.println("request body object fields: "+ obj);
		
		Iterator<String> list = obj.keys();

		while (list.hasNext()) {
			String key = list.next();
			obj.put(key, testDataMap.get(key));

		}


//		for (Map.Entry<String, Object> map : testDataMap.entrySet()) {
//
//			/**
//			 * Note, value can be integer, boolean, double, string type our script needs to
//			 * handle and update the request body accordingly
//			 */
//
//			String key = map.getKey();
//			Object value = map.getValue();
//			
//			//if(!obj.has(key))continue; // only go forward if your template JsonObj has the key ( THOMAS NEWLY ADDED)
//
//			/**
//			 * Before adding to JsonObj, check if the type of value each field accepts
//			 */
//
//			if (obj.get(key) instanceof Integer) {
//				obj.put(key, Integer.valueOf((String) value));
//			} else if (obj.get(key) instanceof Double) {
//				obj.put(key, Double.valueOf((String) value));
//			} else if (obj.get(key) instanceof Boolean) {
//				obj.put(key, Boolean.valueOf((String) value));
//			} else {
//				obj.put(key, value);
//			}
//
//		}
		return obj.toString();
	}
	
	public static void removeFieldsFromRequeestBody(DataTable dataTable) throws JSONException {

		List<Map<String, String>> listMap = dataTable.asMaps(String.class, String.class);
		// Convert to JSON object
		JSONObject obj = new JSONObject(apiRequestConfigTestDataMap.get("requestBody"));

		for (Map<String, String> map : listMap) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String field = entry.getValue();
				obj.remove(field);
			}
		}
		apiRequestConfigTestDataMap.put("requestBody", obj.toString());

	}

}
