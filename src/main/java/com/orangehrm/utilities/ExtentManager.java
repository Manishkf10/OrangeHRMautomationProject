package com.orangehrm.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private static Map<Long, WebDriver> driverMap = new HashMap<>();

	// Initialize the extent report
	public static ExtentReports getReporter() {
		if (extent == null) {
			String reportPath = System.getProperty("user.dir")
					+ "\\src\\test\\resources\\ExtentReports\\extentReport.html";
			ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
			spark.config().setReportName("Automation Test Report");
			spark.config().setDocumentTitle("OrangeHRM REPORT");
			spark.config().setTheme(Theme.DARK);

			extent = new ExtentReports();
			extent.setSystemInfo("Operating System", System.getProperty("os.name"));
			extent.setSystemInfo("java version", System.getProperty("java.version"));
			extent.setSystemInfo("User Name", System.getProperty("user.name"));
			extent.attachReporter(spark);

		}

		return extent;
	}

	// start the test
	public static ExtentTest startTest(String testName) {

		ExtentTest extentTest = getReporter().createTest(testName);
		test.set(extentTest);
		return extentTest;
	}

	public static void endTest() {
		getReporter().flush();
	}

	// get current thread's test
	public static ExtentTest getTest() {
		return test.get();
	}

	// get name of current test
	public static String getTestName() {
		ExtentTest currentTest = getTest();

		if (currentTest != null) {
			return currentTest.getModel().getName();
		} else {
			return "no test is currently active for this Thread!!!";
		}
	}

	// log the steps
	public static void logStep(String logMessage) {
		getTest().info(logMessage);
	}

	// logS a Step verification with screenshots
	public static void logStepWithScreeenShot(WebDriver driver, String logMessage) {
		getTest().pass(logMessage);

		String base64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		getTest().pass(logMessage, MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
	}
	//log a step for API-Testing
	public static void logStepAPI(String logMessage) {
		getTest().pass(logMessage);
	}

	// log a failure
	public static void logFailure(WebDriver driver, String logMessage) {
		getTest().fail(logMessage);

		String base64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		getTest().fail(logMessage, MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());

	}
	// log a failure for API-Testing
	public static void logFailureAPI(String logMessage) {
		getTest().fail(logMessage);

	
	}

	public static void logSkip(String logMessage) {
		getTest().skip(logMessage);

	}

	// screen shot method
	public static void takeScreenshots(WebDriver driver, String screenshotName) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File scr = ts.getScreenshotAs(OutputType.FILE);

		String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss").format(new Date());
		String path = System.getProperty("user.dir") + "\\src\\test\\resources\\screenShots\\" + screenshotName + "_"
				+ timeStamp + ".png";
		File target = new File(path);
		try {
			FileUtils.copyFile(scr, target);
		} catch (IOException e) {
			System.out.println("unable to store screenshoot");
			e.printStackTrace();
		}

	}

	public static void registerDriver(WebDriver driver) {
		driverMap.put(Thread.currentThread().getId(), driver);
	}

}
