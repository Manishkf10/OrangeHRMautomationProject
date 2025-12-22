package com.orangehrm.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.RetryAnalyzer;

public class TestListener implements ITestListener,IAnnotationTransformer{
	
	
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {

		annotation.setRetryAnalyzer(RetryAnalyzer.class);
	}

	@Override
	public void onStart(ITestContext context) {
		ExtentManager.getReporter();
	}
	
	@Override
	public void onFinish(ITestContext context) {
		ExtentManager.endTest();
	}

	@Override
	public void onTestStart(ITestResult result) {
		String nameName=result.getMethod().getMethodName();
		ExtentManager.startTest(nameName);
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String nameName=result.getMethod().getMethodName();
		if(!result.getTestClass().getName().toLowerCase().contains("api")) {
			ExtentManager.logStepWithScreeenShot(BaseClass.getDriver(), "Test passed successfully,"+"Test End : "+nameName);
		}else {
			ExtentManager.logStepAPI("test passed : "+nameName);
		}
		
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String nameName=result.getMethod().getMethodName();
		if(!result.getTestClass().getName().toLowerCase().contains("api")) {
		ExtentManager.logFailure(BaseClass.getDriver(), "Test failed --"+"Test End : "+nameName);
		}
		else {
			ExtentManager.logFailureAPI("test Failed : "+nameName);
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String nameName=result.getMethod().getMethodName();
		ExtentManager.logSkip("test skipped : "+nameName);
	}

	

	

	
}
