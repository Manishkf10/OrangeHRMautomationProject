package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondrivers.ActionDriver;
import com.orangehrm.base.BaseClass;

public class HomePage {

	private ActionDriver actiondriver;
	
	
	public HomePage(WebDriver driver) {
		this.actiondriver= BaseClass.getActionDriver();
		
	}
	
//Locators
	private By adminTag=By.xpath("//span[.='Admin']");
	private By userIDButton=By.xpath("//span[@class='oxd-userdropdown-tab']");
	private By LogOutBtn=By.xpath("//a[text()='Logout']");
	private By orangeHRMLogo=By.xpath("//div[@class=\"oxd-brand-banner\"]/img");
	private By pimTab=By.xpath("//span[.='PIM']");
	private By employeeNameSearch=By.xpath("//label[.='Employee Name']/parent::div/following-sibling::div//input");
	private By searchButton=By.xpath("//button[@type='submit']");
	private By employeeFirstMiddle=By.xpath("//div[@class=\"oxd-table-card\"]/div/div[3]");
	private By employeeLastName=By.xpath("//div[@class=\"oxd-table-card\"]/div/div[4]");
	
	
	
	
	
//Action
	public boolean isAdminTapAvailable() {
		return actiondriver.isDisplayed(adminTag);
		
	}
	public boolean verifyOrangeHRMLogo() {
		return actiondriver.isDisplayed(orangeHRMLogo);
	}
	
	public void clickLogOut() {
		actiondriver.click(userIDButton);
		actiondriver.click(LogOutBtn);
	}


	public void clickPIMTab() {
		actiondriver.click(pimTab);
	}
	public void enterEmployeeName(String value) {
		actiondriver.enterText(employeeNameSearch, value);
		actiondriver.click(searchButton);
		actiondriver.scrollToElement(employeeFirstMiddle);
	}
	
	public boolean verifyEmployeeFirstMiddleName(String firstMidleNameFromDB) {
		return actiondriver.compareText(employeeFirstMiddle, firstMidleNameFromDB);
	}
	
	public boolean verifyEmployeeLastName(String LastNameFromDB) {
		return actiondriver.compareText(employeeLastName, LastNameFromDB);
	}
	
}
