package com.HelloWorld.t;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.ChromeDriverManager;

/**
 * Hello world!
 *
 */
public class App {
	static String currentDir = System.getProperty("user.dir");
	static WebDriver driver;
	public ExtentReports extent;
	public ExtentTest extentTest;
	Urls_lunch lunch = new Urls_lunch();

	@BeforeTest
	public void startReport() {
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/ExtendReport.html", true);
		extent.addSystemInfo("Host Name", "Mashek").addSystemInfo("Environment", "QA").addSystemInfo("User Name",
				"Mashekul Alam");
		extent.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));
	}

	@BeforeMethod
	public void setupClass() {

		/*
		 * ChromeDriverManager.getInstance().setup(); driver= new
		 * ChromeDriver(); driver.manage().window().maximize();
		 * driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		 * driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 * 
		 * driver.get("https://www.google.com/");
		 */
		driver = lunch.Url();
	}

	// Start test cases from here

	@Test
	public void test() {
		extentTest = extent.startTest("test");
		String title = driver.getTitle();
		System.out.println(title);
		
		
		Assert.assertEquals(title, "Google");

		System.out.println("Hello World!");

	}

	@Test
	public void logo() {
		extentTest = extent.startTest("logo");
		boolean b = driver.findElement(By.xpath("//img[@id='hplogo1']")).isDisplayed();
		Assert.assertTrue(b);

	}

	// End test cases here

	@AfterTest
	public void endReport() {
		extent.flush();
		extent.close();
	}

	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots"
		// under src folder
		String destination = System.getProperty("user.dir") + "/FailedScreenshots/" + screenshotName + dateName
				+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.FAILURE) {
			extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS " + result.getName()); // to
																						// add
																						// name
																						// in
																						// extent
																						// report
			extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS " + result.getThrowable()); // to
																							// add
																							// error/exception
																							// in
																							// extent
																							// report

			String screenshotPath = App.getScreenshot(driver, result.getName());
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotPath)); // to
																							// add
																							// screenshot
																							// in
																							// extent
																							// report
			// extentTest.log(LogStatus.FAIL,
			// extentTest.addScreencast(screenshotPath)); //to add
			// screencast/video in extent report
		} else if (result.getStatus() == ITestResult.SKIP) {
			extentTest.log(LogStatus.SKIP, "Test Case SKIPPED IS " + result.getName());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			extentTest.log(LogStatus.PASS, "Test Case PASSED IS " + result.getName());

		}

		extent.endTest(extentTest); // ending test and ends the current test and
									// prepare to create html report
		driver.quit();
	}
}
