package com.orangehrm.utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer{

	private int retryCount=0;
	public static final int MAXretry=2;//maximum retry time
	@Override
	public boolean retry(ITestResult result) {
		if(retryCount<MAXretry) {
			retryCount++;
			return true;//re-try test
		}
		return false;
	}

	
}
