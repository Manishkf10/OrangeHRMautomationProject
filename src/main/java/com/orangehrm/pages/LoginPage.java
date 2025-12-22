package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondrivers.ActionDriver;
import com.orangehrm.base.BaseClass;

public class LoginPage {

	private ActionDriver actionDriver;
	
	
//constructor
	public LoginPage(WebDriver driver) {
		this.actionDriver= BaseClass.getActionDriver();
	}
	
//Locators
	private By userName=By.xpath("//input[@name='username']");
	private By passwaord=By.xpath("//input[@name='password']");
	private By loginButton=By.xpath("//button[.=' Login ']");
	private By errorMessage=By.xpath("//p[.='Invalid credentials']");
	
	
//method to perform login
	public void login(String username,String password) {
		actionDriver.enterText(userName, username);
		actionDriver.enterText(passwaord, password);
		actionDriver.click(loginButton);
	}
	
//errorMessage
	public boolean isErrorMsgDisplay() {
		return actionDriver.isDisplayed(errorMessage);
	}
	
//get Error msg
	public String getErrorMsg() {
		return actionDriver.getText(errorMessage);
	}
	
//verification of error msg
	public boolean verifyErrorMsg(String expected) {
		return actionDriver.compareText(errorMessage, expected);
		
	}
	
	
}
