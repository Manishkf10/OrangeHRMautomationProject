package com.orangehrm.tests;

import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.orangehrm.utilities.API_Utility;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.LoggerManager;
import com.orangehrm.utilities.RetryAnalyzer;

import io.restassured.response.Response;

public class ApiTest {

	@Test//(retryAnalyzer = RetryAnalyzer.class)->option 1-if test fail, allows to retry/ Option 2: add innotationTransformar in listener class
	public void varifyGetUserAPI() {

		SoftAssert soft = new SoftAssert();

		// step:Define API endPoint
		String endPoint = "https://jsonplaceholder.typicode.com/users/1";
		ExtentManager.logStep("API endPoint :" + endPoint);

		// step2 :send GET request
		Response response = API_Utility.sendGetRequest(endPoint);
		ExtentManager.logStep("sended GET request");

		// step3 :validate status code
		ExtentManager.logStep("Validating status code");
		boolean codeStatus = API_Utility.validateStatusCode(response, 200);
		soft.assertTrue(codeStatus, "status code is not Expected");

		if (codeStatus) {
			ExtentManager.logStepAPI("Status code validation passed");
		} else {
			ExtentManager.logFailureAPI("Status code validation failed");
		}

		// step4:validation username
		ExtentManager.logStep("validation username");
		String userName = API_Utility.getJSONvalue(response, "username");
		boolean isUserNameValid = "Bret".equals(userName);

		if (isUserNameValid) {
			ExtentManager.logStepAPI("username is valid :" + userName);
		} else {
			ExtentManager.logFailureAPI("username is not valid :" + userName);
		}

		soft.assertTrue(isUserNameValid, "Username is not Valid");

		// step4:validation email
		ExtentManager.logStep("validation user email");
		String email = API_Utility.getJSONvalue(response, "email");
		boolean isEmailValid = "Sincere@april.biz".equals(email);

		if (isEmailValid) {
			ExtentManager.logStepAPI("email is valid :" + email);
		} else {
			ExtentManager.logFailureAPI("email is not valid :" + email);
		}

		soft.assertTrue(isEmailValid, "email is not Valid");
		soft.assertAll();
	}
}
