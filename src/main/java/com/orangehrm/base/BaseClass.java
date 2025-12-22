package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.Dimension;

import com.orangehrm.actiondrivers.ActionDriver;
import com.orangehrm.utilities.LoggerManager;

public class BaseClass {

	protected static Properties ppt;
//	protected static WebDriver driver;
//	protected static ActionDriver actionDriver;

	public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	public static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();
	public static ThreadLocal<SoftAssert> softAssert = ThreadLocal.withInitial(SoftAssert::new);// only initalize when
																								// called first time
																								// otherwise not
																								// initiallized

	public static final Logger logger = LoggerManager.getLogger(BaseClass.class);

//accessing properties file to read date
	@BeforeSuite
	public void readingPropertiesFile() throws IOException {

		// loading properties file
		ppt = new Properties();
		FileInputStream fi = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties");
		ppt.load(fi);
		logger.info("config.properties file is loaded!!!");

	}

	@BeforeMethod
	public void setUp() throws IOException {

		launchBrowser();
		settingPropertiesOfOpenedBrowser();

		logger.info("browser is launched and window is mazimized");

		// initallizing ActionDriver for only one instance

		actionDriver.set(new ActionDriver(getDriver()));
		logger.info("ActionDriver class instance is created!!!----->" + Thread.currentThread().getId());

	}

	@AfterMethod
	public void tearDown() {
		staticWait(2);
		if (getDriver() != null)
			try {
				getDriver().quit();
			} catch (Exception e) {
				System.out.println("unable to quit browser!!!" + e.getMessage());
			}

		driver.remove();
		actionDriver.remove();
		// driver = null;
		// actionDriver = null;
		logger.info("webdriver & ActionDriver classes instance terminated!!!");

	}

//selecton of driver
	private void launchBrowser() {
		String browser = ppt.getProperty("browser");
		switch (browser.toLowerCase()) {
		case "chrome": {
			ChromeOptions options = new ChromeOptions();

			options.addArguments("--headless=new");
			// options.addArguments("--window-size=1920,1080");--?Unable to work some how
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-notifications");

			driver.set(new ChromeDriver(options));
			logger.info("chromeDriver initialized!!!");
			break;
		}
		case "edge": {
			EdgeOptions options = new EdgeOptions();
			options.addArguments("--headless");// Run headlessly cases
			// options.addArguments("disable-gpu");//disable GPU as we go already headless
			// options.addArguments("--window-size=1920,1080");//set window size
			options.addArguments("--disable-notifications");// stop notification
			options.addArguments("--no-sendbox");// Disables Chrome’s sandbox security feature, Required in many CI/CD,
													// Docker, and Linux environments
			options.addArguments("--disable-dev-shm-usage");// avoiding crashes like:DevToolsActivePort file doesn’t
															// exist
			// driver = new EdgeDriver();
			driver.set(new EdgeDriver(options));
			logger.info("edgeDriver initialized!!!");
			break;
		}
		case "firefox": {
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments("--headless");// Run headlessly cases
			options.addArguments("disable-gpu");// disable GPU as we go already headless
			// options.addArguments("--window-size=1920,1080");//set window size
			options.addArguments("--disable-notifications");// stop notification
			// firefox not support some featurs like chrome, edge
			// driver = new FirefoxDriver();
			driver.set(new FirefoxDriver(options));
			logger.info("firefox initialized!!!");
			break;
		}
		default:
			throw new IllegalArgumentException("browser not supported :" + browser);
		}
	}

//driver commands	
	private void settingPropertiesOfOpenedBrowser() {
		getDriver().manage().timeouts()
				.implicitlyWait(Duration.ofSeconds(Integer.parseInt(ppt.getProperty("implicitWait"))));
		getDriver().manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
	//	getDriver().manage().window().maximize();

		try {
			getDriver().get(ppt.getProperty("url"));
		} catch (Exception e) {
			System.out.println("failed to navigate url!!!" + e.getMessage());
		}

		Dimension size = getDriver().manage().window().getSize();
		System.out.println("HEADLESS WINDOW SIZE: " + size);

	}

	public SoftAssert getSoftAssert() {
		return softAssert.get();
	}

//wait method
	public void staticWait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
	}

//driver getter and setter methods
	public static WebDriver getDriver() {
		if (driver.get() == null) { // this .get() is from ThreadLocal class not webdriver
			logger.info("WebDriver is not available here");
			throw new IllegalStateException("WebDriver is not available here");
		}
		return driver.get();// this .get() is from ThreadLocal class not webdriver
	}

	public void setDriver(ThreadLocal<WebDriver> driver) {
		this.driver = driver;
	}

//ActionDriver class instance getter and setter methods
	public static ActionDriver getActionDriver() {
		if (actionDriver.get() == null) {
			logger.info("ActionDriver  is not available here");
			throw new IllegalStateException("ActionDriver is not available here");
		}
		return actionDriver.get();
	}

//ppt getter
	public static Properties getPpt() {
		return ppt;
	}
}
