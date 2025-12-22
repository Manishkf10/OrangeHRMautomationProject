package com.orangehrm.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;

public class LocalhostLoginValidation extends BaseClass {

	LoginPage loginPage;
	HomePage homePage;

	@BeforeMethod
	public void setupPages() {
		loginPage= new LoginPage(getDriver());
		homePage=new HomePage(getDriver());
	}
	
	
	@Test(dataProvider = "localhost",dataProviderClass =DataProviders.class )
	public void verifyLogin(String user,String password) {

		
		loginPage.login(user, password);
		Assert.assertTrue( homePage.isAdminTapAvailable(),"unable to locate Admin-Tab");
		homePage.clickLogOut();
		staticWait(2);
	}
}
