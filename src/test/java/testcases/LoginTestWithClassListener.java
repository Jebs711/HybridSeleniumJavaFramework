/*
 *  - Using Listener at class level => @Listeners(listeners.ListenerDemo.class)
 *  - It should be mentioned above class name
 * 
 * 
 */


package testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseClass;
import dataProvider.CustomDataProvider;
import pages.LoginPage;

@Listeners(listeners.ListenerDemo.class)
public class LoginTestWithClassListener extends BaseClass {
	
	
	@Test(dataProvider = "loginDetails",dataProviderClass = CustomDataProvider.class)
	public void loginToApplication(String uname,String pass)
	{
		LoginPage login=new LoginPage(driver);
		
		login.loginToApplication(uname, pass);
		
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Welcome')]")));	
		
		Assert.assertTrue(driver.findElement(By.xpath("//button[text()='Sign out']")).isDisplayed());
		
		driver.findElement(By.xpath("//button[text()='Sign out']")).click();
		
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.numberOfElementsToBe(By.xpath("//a[@href]//img"), 4));
		
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='New user? Signup']")).isDisplayed());
		
		Reporter.log("Login Successful",true);
	}
}
