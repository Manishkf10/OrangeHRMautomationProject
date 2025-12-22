package com.orangehrm.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;

public class HomePageTest extends BaseClass{
	
	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setUpPages(){
		loginPage=new LoginPage(getDriver());
		homePage=new HomePage(getDriver());
	}
	
	@Test(dataProvider = "localhost",dataProviderClass = DataProviders.class)
	public void login(String user,String pss) {
		loginPage.login(user, pss);
		Assert.assertTrue(homePage.verifyOrangeHRMLogo(), "unable to locate OrangeHRM logo!!!");
	}

}
