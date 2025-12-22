package com.orangehrm.utilities;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReaderUtility {

	public static List<String[]> getSheetData(String filePath, String sheetName) {
		List<String[]> data = new ArrayList<>();
		try {
			FileInputStream fis = new FileInputStream(filePath);
			Workbook workbook = WorkbookFactory.create(fis);
			Sheet sheet = workbook.getSheet(sheetName);

			if (sheet == null) {
				throw new IllegalArgumentException("sheet :" + sheetName + " does not exist");
			}

			// iterating through row and then column
			for (Row row : sheet) {
				List<String> rowData = new ArrayList<>();

				if (row.getRowNum() == 0) {
					continue;
				}
				for (Cell cell : row) {
					rowData.add(getCellValue(cell));
				}
				data.add(rowData.toArray(new String[0]));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;

	}

	private static String getCellValue(Cell cell) {

		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue().toString();
			}
			return String.valueOf((int) cell.getNumericCellValue());

		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		default:
			return "";

		}
	}
}
