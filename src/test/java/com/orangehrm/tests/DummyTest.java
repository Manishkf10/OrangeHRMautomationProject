package com.orangehrm.tests;

import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;

public class DummyTest extends BaseClass {

	@Test
	public void demotest() {
		String title = getDriver().getTitle();

		assert title.equals("OrangeHRM") : "Test Failed-title not matched";
		System.out.println("title matched");
	}

}
