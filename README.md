# OrangeHRM Hybrid Test Automation Framework

## Overview

This project is a **Hybrid Test Automation Framework** developed to automate testing of the **OrangeHRM web application** using **Java, Selenium WebDriver, and TestNG**.

The framework follows **Page Object Model (POM)** design principles and includes utilities for **Excel-based test data, database validation, API testing, reporting, and logging**.

It is designed to be **scalable, reusable, and suitable for parallel execution**.

---

## Technology Stack

| Technology         | Usage                           |
| ------------------ | ------------------------------- |
| Java               | Programming language            |
| Selenium WebDriver | Web UI automation               |
| TestNG             | Test framework and execution    |
| Maven              | Dependency and build management |
| Log4j              | Logging                         |
| Extent Reports     | HTML reporting                  |
| Apache POI         | Excel data reading              |
| MySQL Connector    | Database testing                |
| RestAssured        | API testing                     |
| Selenium Grid      | Distributed execution           |
| Docker             | Containerized Selenium Grid     |

---

## Framework Architecture

src/main/java

* actiondrivers

  * ActionDriver.java (Reusable UI actions)

* base

  * BaseTest.java (Driver setup and teardown)

* listeners

  * TestNG listeners for reporting

* pages

  * Page Object classes

* utilities

  * ExcelReaderUtility.java
  * MyDataProvider.java
  * DBConnectionUtil.java
  * ApiUtility.java
  * MyExtentReport.java

src/test/java

* tests

  * Test classes

src/test/resources

* ExecutableTestSuites
* TestData
* ExtentReports
* screenShots

---

## Key Features

* Page Object Model (POM) architecture
* ThreadLocal WebDriver for parallel execution
* Custom ActionDriver methods
* Excel DataProvider support
* Database validation with MySQL
* API testing with RestAssured
* Extent HTML reports with screenshots
* Log4j logging support
* Selenium Grid execution
* Docker-based Selenium Grid support

---

## ActionDriver Utilities

A custom **ActionDriver** class is implemented to wrap Selenium actions such as:

* click()
* enterText()
* waitForElementVisible()
* waitForElementClickable()
* scrollToElement()
* compareText()

This improves **code reusability and readability** in test scripts.

---

## Test Data Handling

Test data is stored in **Excel files** and read using **Apache POI**.

Example:

```java
List<String[]> sheetData =
    MyExcelReader.getCellData(filePath, sheetName);
```

Data is converted into **TestNG DataProvider format** for parameterized testing.

---

## Database Testing

Database validation is implemented using **MySQL JDBC Connector**.

Example:

```java
Connection conn = DBConnectionUtil.getConnection();

Statement stmt = conn.createStatement();

ResultSet rs = stmt.executeQuery(
"SELECT * FROM hs_hr_employee");
```

This allows verification of **UI data against database records**.

---

## API Testing

Basic API validation is implemented using **RestAssured**.

Example request:

```java
Response response =
    RestAssured
        .given()
        .header("Content-Type","application/json")
        .body(payload)
        .post(endpoint);
```

---

## Reporting

Test execution reports are generated using **Extent Reports**.

Reports include:

* Passed tests
* Failed tests
* Screenshots for failures
* Step level logs

Reports are generated under:

src/test/resources/ExtentReports

---

## Running the Tests

Run using Maven:

mvn clean test

Run using TestNG suite file:

src/test/resources/ExecutableTestSuites/testng.xml

---

## Parallel Execution

Parallel execution is supported using:

* ThreadLocal WebDriver
* TestNG parallel configuration

---

## Selenium Grid Execution

Tests can be executed on Selenium Grid.

Example Grid URL:

http://localhost:4444/wd/hub

This allows tests to run on multiple browsers simultaneously.

---

## Author

Manish Kumar
Software Test Engineer

Skills:

* Selenium Automation
* Java
* API Testing
* Database Testing
* TestNG
* Maven
* Automation Framework Design

GitHub:
https://github.com/Manishkf10

---

## Future Enhancements

* CI/CD integration using Jenkins or GitHub Actions
* Cloud execution (BrowserStack / SauceLabs)
* Allure reporting integration
* Advanced API test coverage
