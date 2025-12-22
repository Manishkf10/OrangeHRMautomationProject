package com.orangehrm.tests;

import java.sql.DatabaseMetaData;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataBaseConnections;

public class DBVerificationTest extends BaseClass{

	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setUpPages(){
		loginPage=new LoginPage(getDriver());
		homePage=new HomePage(getDriver());
	}
	
	@Test
	public void verifyEmployeeFromDB() {
		
		
		logger.info("starting verifyEmployeeFromDB()");
		loginPage.login(ppt.getProperty("localUser"), ppt.getProperty("localPassword"));
		homePage.clickPIMTab();
		logger.info("clicked on PIM Tab");
		homePage.enterEmployeeName("harish");
		String id="0002";
		
		Map<String, String> employeeDetails= DataBaseConnections.getEmployeeDetails(id);
		String employeeFirstName= employeeDetails.get("firstname");
		String employeeMiddleName=employeeDetails.get("middle_name");
		String employeeLastName=employeeDetails.get("lastname");
		logger.info("able to store data from DB");
		String employeeFistMiddle=(employeeFirstName+" "+employeeMiddleName).trim();
		System.out.println("EmployeeName fetch from BD========>"+employeeFistMiddle);
		
		//validation of first and middle name
		getSoftAssert().assertTrue(homePage.verifyEmployeeFirstMiddleName(employeeFistMiddle),"unable to match employee first and middle name from DB");
		
		//validation of last name
		getSoftAssert().assertTrue(homePage.verifyEmployeeLastName(employeeLastName),"unavle to verify employee last name");
		
		getSoftAssert().assertAll();
		logger.info("verifyEmployeeFromDB COMPLETED");
	}
}
