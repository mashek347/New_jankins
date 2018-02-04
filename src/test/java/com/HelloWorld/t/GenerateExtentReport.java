package com.HelloWorld.t;

import java.io.File;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class GenerateExtentReport {
	 ExtentReports extent;
	 ExtentTest test;

	@BeforeTest
	public ExtentReports startReport() {
//		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/MyOwnReport.html", true);
//		extent.addSystemInfo("Host Name", "Mashek").addSystemInfo("Environment", "QA").addSystemInfo("User Name",
//				"Mashekul Alam");
		// extent.loadConfig(new File(System.getProperty("user.dir") +
		// "\\extent-config.xml"));
		return extent;
	}

	//@Test
	public void demoReportPass(ExtentReports extent) {
		System.out.println("extent-2-"+extent);
		test = extent.startTest("Test is Pass");
		Assert.assertTrue(true);
		test.log(LogStatus.PASS, "Assert Pass as condition is True");
		
	}

	//@Test
	public void demoReportFail(ExtentReports extent) {
		test = extent.startTest("Test is Fail");
		Assert.assertTrue(true);
		test.log(LogStatus.FAIL, "Assert Fail as condition is False");
	}

	@AfterMethod
	public void getResult(ITestResult result) {
		System.out.println("result---" + result);
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, result.getThrowable());

		}
		extent.endTest(test);
	}

	@AfterTest
	public void endreport() {
		extent.flush();
		extent.close();
	}
}