package com.HelloWorld.t;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.ChromeDriverManager;

/**
 * Hello world!
 *
 */
public class App 
{
	static String currentDir = System.getProperty("user.dir");
	static WebDriver driver;
   
	   @BeforeClass
	    public static void setupClass() {
	        ChromeDriverManager.getInstance().setup();
	        driver= new ChromeDriver();
	        driver.get("https://www.google.com/");
	    }

	
	@Test
    	public void test() {
		
	
		

		
        System.out.println( "Hello World!" );
    
    	}
	
	
	@AfterTest
	public void teardown()
	{
		
		driver.close();
		driver=null;
		
	}
}
