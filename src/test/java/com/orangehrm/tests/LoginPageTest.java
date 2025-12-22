package com.orangehrm.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;

public class LoginPageTest extends BaseClass{
	
	private LoginPage loginPage;
	private HomePage homepage;
	
	@BeforeMethod
	public void setupPages() {
		loginPage= new LoginPage(getDriver());
		homepage=new HomePage(getDriver());
	}
	
	@Test(dataProvider = "localhost",dataProviderClass = DataProviders.class)
	public void verifyLoginTest(String user,String password) {
		loginPage.login(user, password);
		Assert.assertTrue(homepage.isAdminTapAvailable(), "admin tab should be available");
		homepage.clickLogOut();
		staticWait(2); 

	}
	
	@Test(dataProvider = "invalid",dataProviderClass = DataProviders.class)
	public void invalidLoginTest(String user,String password) {
		loginPage.login(user, password);
		boolean status= loginPage.verifyErrorMsg("Invalid credentials");
		Assert.assertEquals(status, true);
		staticWait(2); 
		
	}

	
	
	
}
