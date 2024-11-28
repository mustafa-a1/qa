package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {

	public ExtentSparkReporter spark;
	public ExtentTest test;
	public ExtentReports extent;
	public String reportName;

	public void onStart(ITestContext context) {

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		reportName = "Test-Repor" + timeStamp + ".html";

		spark = new ExtentSparkReporter("./reports/" + reportName);
		spark.config().setDocumentTitle("Doc Title");
		spark.config().setReportName("Report Name");
		spark.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(spark);
		extent.setSystemInfo("Application", "OpenCart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");

		String os = context.getCurrentXmlTest().getParameter("OS");
		extent.setSystemInfo("Operating System", os);

		String browserName = context.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browserName);

		List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();

		if (!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
		}

	}

	public void onTestSuccess(ITestResult result) {

		// Creates a new test entry in the report using the name of the test class.
		test = extent.createTest(result.getTestClass().getName());

		// This assigns categories (groups) to the test, so you know which type of tests
		// were run.
		test.assignCategory(result.getMethod().getGroups());

		// This records a message in the report that says the test passed and includes
		// the name of the test.
		test.log(Status.PASS, result.getName() + " got passed!");

	}

	public void onTestFailure(ITestResult result) {

		// This creates a new test entry in the report using the name of the test class.
		test = extent.createTest(result.getTestClass().getName());

		// This assigns categories (groups) to the test, so you know which type of tests
		// were run.
		test.assignCategory(result.getMethod().getGroups());

		// This records a message in the report saying the test failed and includes the
		// name of the test.
		test.log(Status.FAIL, result.getName() + " got failed!");

		// This records additional information about why the test failed, such as an
		// error message.
		test.log(Status.INFO, result.getThrowable().getMessage());

		// This tries to take a screenshot of the test failure, using the test's name
		// for the file.
		try {
			String imgPath = new BaseClass().captureScreenshot(result.getName());
			// If the screenshot is taken successfully, it adds the image to the report.
			test.addScreenCaptureFromPath(imgPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {

		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());

		test.log(Status.SKIP, result.getName() + " got skipped!");
		test.log(Status.INFO, result.getThrowable().getMessage());

	}

	public void onFinish(ITestContext context) {
		// Saves all the information that has been collected for the report so far.
		extent.flush();

		// Builds the full path to where the report file is saved, including the folder
		// and the report's name.
		String pathOfExtentReport = System.getProperty("user.dir") + "/reports/" + reportName;

		// Creates a file object that represents the report
		File extentReport = new File(pathOfExtentReport);

		try {
			// Tries to open the report in your default web browser so you can view it
			// immediately after the tests are done.
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
