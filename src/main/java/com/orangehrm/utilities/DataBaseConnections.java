package com.orangehrm.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.orangehrm.base.BaseClass;

public class DataBaseConnections {

	private static final String DB_URL = "jdbc:mysql://localhost:3307/orangehrm";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "";
	public static final Logger logger=BaseClass.logger;

	public static Connection getDBConnection() {
		try {
			System.out.println("Starting DB Connection...");
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			logger.info("DB connection successfull");
			ExtentManager.logStep("DB connection successfull");
			return conn;
		} catch (SQLException e) {
			logger.info("error while stablishing DB connection");
			ExtentManager.logStep("error while stablishing DB connection");
			e.printStackTrace();
			return null;
		}
		
		
	}
	//get the employee details and store in a Map
	public static Map<String,String> getEmployeeDetails(String employeeID) {
		String query="SELECT employee_id,emp_firstname,emp_middle_name,emp_lastname FROM hs_hr_employee WHERE employee_id="+employeeID;			
		Map<String,String> employeeDetails=new HashMap<>();
		
		try {
			Connection conn=getDBConnection();
			Statement stmt= conn.createStatement();
			ResultSet rs= stmt.executeQuery(query);
			logger.info("Executing query :"+query);
			ExtentManager.logStep("Executing query :"+query);
			
			if(rs.next()) {
				String  employee_id=rs.getString("employee_id");
				String  firstname=rs.getString("emp_firstname");
				String  middle_name=rs.getString("emp_middle_name");
				String  lastname=rs.getString("emp_lastname");
				
				//storing in Map
				employeeDetails.put("employee_id",employee_id );
				employeeDetails.put("firstname", firstname);
				employeeDetails.put("middle_name",middle_name!=null?middle_name:"" );
				employeeDetails.put("lastname",lastname );
				
				logger.info("query executed successfully");
				ExtentManager.logStep("query executed successfully");
			}else {
				logger.info("employee details not found!!!");
				ExtentManager.logStep("employee details not found!!!");
			}
		} catch (SQLException e) {
			logger.info("error while executing SQL query!!!");
			ExtentManager.logStep("error while executing SQL query!!!");
			e.printStackTrace();
		}
		
		return employeeDetails;
	}
}







