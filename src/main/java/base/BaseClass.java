/*
 *  - If we use setupBrowser() @BeforeClass and closeBrowser() @AfterMethod, for multiple execution using DataProvider, driver will
 *    session will be null for 2nd execution as @AfterMethod -> driver.quit() is already called.
 *  - Therefore,we're using setupBrowser() as well for @BeforeMethod.
 */

package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import browserFactory.BrowserFactory;
import dataProvider.ConfigReader;

public class BaseClass {
	
	public WebDriver driver;    //LoginTest extends BaseClass but both classes are in different packages, therefore default driver is 
	                            //not visible to LoginTest, so we've to make driver proteced or public.
	
	@BeforeMethod
	public void setupBrowser()
	{
		System.out.println("LOG:INFO - Setting up browser");
		
		//Approach 1 - Config - Does not suite for cross browser
		driver = BrowserFactory.startBrowser(ConfigReader.getProperty("browser"),ConfigReader.getProperty("url"));

		System.out.println("LOG:INFO - Application is up and running");
	}
	
	
	@AfterMethod
	public void closeBrowser()
	{
		driver.quit();
		System.out.println("LOG:INFO - Closing the browser and application");
	}

}
