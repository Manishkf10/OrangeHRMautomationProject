package com.orangehrm.actiondrivers;

import java.time.Duration;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class ActionDriver {

	private WebDriver driver;
	private WebDriverWait wait;
	public static final Logger logger=BaseClass.logger;

//constructor	
	public ActionDriver(WebDriver driver) {
		this.driver = driver;
		int time = Integer.parseInt(BaseClass.getPpt().getProperty("explicitwait"));
		wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		logger.info("ActionDriver initialized!!!");
		

	}

//click with wait	
	public void click(By by) {
		String element=getElementDescription(by);
		
		try {
			
			waitElementClickable(by);
			applyBorder(by, "green");
			driver.findElement(by).click();
			logger.info("clicked on :"+element);
			ExtentManager.logStep("clicked on :"+element);
		} catch (Exception e) {
			System.out.println("Unable to click!!! " + e.getMessage());
			logger.error("unable to click element");
			ExtentManager.logStep("unable to click element");
		}
	}

//sendkeys with wait
	public void enterText(By by, String value) {
		try {
			waitElementVisible(by);
			applyBorder(by, "green");
			driver.findElement(by).clear();
 			driver.findElement(by).sendKeys(value); 
 			logger.info("Entered text :"+value+" in "+getElementDescription(by));
 			ExtentManager.logStep("Entered text :"+value+" in "+getElementDescription(by));
		} catch (Exception e) {
			System.out.println("unable to enter Text!!! " + e.getMessage());
			logger.error("unable to enter text");
			ExtentManager.logStep("unable to enter text");
		}
	}

//return getText
	public String getText(By by) {

		try {
			waitElementVisible(by);
			applyBorder(by, "green");
			return driver.findElement(by).getText();
		} catch (Exception e) {
			System.out.println("unable to get Text!!! " + e.getMessage());
			ExtentManager.logStep("unable to getText");
			logger.error("unable to getText");
			return "";
		}
	}

//compare to text
	public boolean compareText(By by, String expected) {

		try {
			waitElementVisible(by);
			String actual = driver.findElement(by).getText();

			if (expected.equals(actual)) {
				
				applyBorder(by, "green");
				logger.info("Text are equals :" + expected + "equals to " + actual);
				ExtentManager.logStep("Text are equals :" + expected + " equals to " + actual);
				return true;
			} else {
				applyBorder(by, "red");
				logger.info("Text are not equals :" + expected + " is not equals to " + actual);
				ExtentManager.logStep("Text are not equals :" + expected + " is not equals to " + actual);
				return false;
			}
		} catch (Exception e) {
			System.out.println("unable to campare text!!! " + e.getMessage());
			ExtentManager.logStep("unable to compare texts");
			logger.error("unable to compare texts");
			return false;
			
		}
	}

//element is displayed
	public boolean isDisplayed(By by) {
		try {
			waitElementVisible(by);
			applyBorder(by, "green");
			logger.info("element is displayed :"+getElementDescription(by));
			ExtentManager.logStep("element is displayed :"+getElementDescription(by));
			return driver.findElement(by).isDisplayed();
			

		} catch (Exception e) {
			System.out.println("element is not visible!!! " + e.getMessage());
			ExtentManager.logStep("unable to locate element");
			logger.error("unable to locate element");
			return false;
		}
	}

//scroll to element
	public void scrollToElement(By by) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(by);
			
			js.executeScript("arguments[0].scrollIntoView(true);", element);
			applyBorder(by, "green");
			ExtentManager.logStep("page is scrolled to element :"+getElementDescription(by));
			logger.info("page is scrolled to element :"+getElementDescription(by));
			
		} catch (Exception e) {
			System.out.println("unable to locate element!!! " + e.getMessage());
			ExtentManager.logStep("unable to scroll to the element!!!");
			logger.info("unable to scroll to the element!!!");
		}
	}

//wait for the page load
	public void waitTillPageLoad(int seconds) {
		try {
			wait.withTimeout(Duration.ofSeconds(seconds)).until(WebDriver -> ((JavascriptExecutor) WebDriver)
					.executeScript("return document.readyState").equals("complete"));
			logger.info("page loaded successfully");
			ExtentManager.logStep("page loaded successfully");
		} catch (Exception e) {
			System.out.println("page did not loaded within " + seconds + " seconds!!!" + e.getMessage());
			logger.error("page did not loaded");
			ExtentManager.logStep("page did not loaded");
		}

	}

//wait for element is clickable
	public void waitElementClickable(By by) {
		try {
			waitTillPageLoad(10);
			wait.until(ExpectedConditions.elementToBeClickable(by));
			applyBorder(by, "green");
			logger.info("located element by "+getElementDescription(by)+" is clickable");
			ExtentManager.logStep("located element by "+getElementDescription(by)+" is clickable");
		} catch (Exception e) {
			System.out.println("element is not clickable!!!" + e.getMessage());
			logger.error("element is not clickable!!!");
			ExtentManager.logStep("element is not clickable!!!");
		}
	}

//wait for element is visible

	public void waitElementVisible(By by) {
		try {
			waitTillPageLoad(10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			applyBorder(by, "green");
			logger.info(getElementDescription(by)+" is visible");
			ExtentManager.logStep(getElementDescription(by)+" is visible");
		} catch (Exception e) {
			System.out.println("element is not visible!!! " + e.getMessage());
			ExtentManager.logStep("element is not visible!!!");
		}
	}
	
	
//getting description of webElement
	public String getElementDescription(By locator) {
		//is driver and location is not null
		
		if(driver==null)
			return "driver is null!!!";
		if(locator==null)
			return "locator is null!!!";
		
		try {
			//finding webelement attribute type
			WebElement element= driver.findElement(locator);
			String name= element.getDomAttribute("name");
			String id= element.getDomAttribute("id");
			String text= element.getText();
			String className= element.getDomAttribute("class");
			String placeHolder= element.getDomAttribute("placeholder");
			
			//returning description
			if(isStringNull(name)) {
				return "element with Name attribute:"+name;
			}else if(isStringNull(id)) {
				return "element with id attribute:"+id;
			}else if(isStringNull(className)) {
				return "element with class attribute:"+className;
			}else if(isStringNull(placeHolder)) {
				return "element with placeholder attribute:"+placeHolder;
			}
		} catch (Exception e) {
			logger.error("unable to describe element attribute type!!!"+e.getMessage());
			ExtentManager.logStep("unable to describe element attribute type!!!");
		}
		return "unable to describe element attribute type!!!";
	}
	
	//util to check String is null or not
	public boolean isStringNull(String value) {
		return value!=null && !value.isEmpty();
	}
	
	//util to coloring the border of element
	public void applyBorder(By by, String color) {
		try {
			WebElement element =driver.findElement(by);//arguments[0].style.border='3px solid green'"
			String script="arguments[0].style.border='3px solid "+color+"'";
			JavascriptExecutor js=(JavascriptExecutor)driver;
			js.executeScript(script,element);
			ExtentManager.logStep("applied "+color+" color on border of element "+getElementDescription(by));
			logger.info("applied "+color+" color on border of element "+getElementDescription(by));
		} catch (Exception e) {
			logger.warn("failed to apply color on border of element :"+getElementDescription(by),e.getMessage());
		}
		
		
	}

}
