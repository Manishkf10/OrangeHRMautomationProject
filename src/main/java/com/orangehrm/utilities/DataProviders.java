package com.orangehrm.utilities;

import java.util.List;

import org.testng.annotations.DataProvider;

public class DataProviders {

	private static final String PATH=System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\testData1.xlsx";
	
	@DataProvider(name="valid")
	public static Object[][] getValidData(){
		return getSheetData("valid");
	}
	
	@DataProvider(name="invalid")
	public static Object[][] getInvalidData(){
		return getSheetData("invalid");
	}
	
	@DataProvider(name="localhost")
	public static Object[][] getValidDataLocalhost(){
		return getSheetData("localhost-valid");
	}
	
	
	private static Object[][] getSheetData(String sheetName){
		List<String[]> sheetData=ExcelReaderUtility.getSheetData(PATH, sheetName);
		Object[][] data=new Object[sheetData.size()][sheetData.get(0).length];
		
		
		for(int i=0;i<sheetData.size();i++) {
			 data[i]=sheetData.get(i);
		}
		return data;
		
		 
	}
}
