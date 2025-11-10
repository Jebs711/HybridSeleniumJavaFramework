package testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import base.BaseClass;
import dataProvider.CustomDataProvider;
import pages.HomePage;
import pages.LoginPage;

public class LoginTest extends BaseClass {
	
	@Test(dataProvider = "loginDetails",dataProviderClass = CustomDataProvider.class)
	public void loginToApplication(String uname,String pass)
	{
		LoginPage login=new LoginPage(driver);
		
		login.loginToApplication(uname, pass);
		
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Welcome')]")));	
		
		HomePage home=new HomePage(driver);
		
		Assert.assertTrue(home.getWelcomeMsg().contains("Welcome"));
		
		home.clickOnSignOut();
		
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.numberOfElementsToBe(By.xpath("//a[@href]//img"), 4));
		
		Assert.assertTrue(login.isSignInPresent());
		
		Reporter.log("Login Successful",true);
	}
}
