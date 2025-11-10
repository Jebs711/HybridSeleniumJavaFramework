/*
 *  Create a utility method to verify whether pdf file is downloaded in a local folder
 *  and verify its size, number of pages and content of a pdf.
 * 
 */

package helper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import javax.imageio.ImageIO;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Reporter;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;

public class Utility {
	
	
	public static WebElement highlightElement(WebDriver driver, WebElement element)
	{
		JavascriptExecutor js=(JavascriptExecutor) driver;
		
		String originalStyle=element.getAttribute("style");
		
		js.executeScript("arguments[0].setAttribute('style',arguments[1])",element,"background : yellow; border : 2px solid red;");
		
		waitForSecond(1);
		
//		js.executeScript("arguments[0].setAttribute('style','')",element);
//		js.executeScript("arguments[0].setAttribute('style','background : transparent; border : 3px none red;')",element);
		
		js.executeScript("arguments[0].setAttribute('style',arguments[1])",element,originalStyle);
		
		return element;
	}
	
	public static WebElement highlightElement(WebDriver driver, By locator)
	{
		WebElement element = driver.findElement(locator);
		
		String originalStyle = element.getAttribute("style");
		
		JavascriptExecutor js=(JavascriptExecutor) driver;
		
		js.executeScript("arguments[0].setAttribute('style',arguments[1])",element,"background : yellow; border : 2px solid red;");
		
		waitForSecond(1);
		
//		js.executeScript("arguments[0].setAttribute('style','')",element);
//		js.executeScript("arguments[0].setAttribute('style','background : transparent; border : 3px none red;')",element);
		
		js.executeScript("arguments[0].setAttribute('style',arguments[1])",element,originalStyle);
		
		return element;
	}
	
	public void waitForElement(By locator,int seconds,WebDriver driver) throws InterruptedException
	{
	    for(int i=0;i<seconds;i++)
	    {
		try
	    	{
				WebElement element = driver.findElement(locator);
				if(element.isEnabled())
				{
					System.out.println("Element found and enabled");
					return;      //return statement within a loop not only exits the loop but also immediately terminates the entire method in which the loop resides, returning control to the method's caller.
				}                //This differs from the break statement, which only exits the current loop, allowing the rest of the method's code to execute.
			} 
	    	catch (Exception e) 
	    	{
				System.out.println("Waiting for element - "+e.getMessage());
				Thread.sleep(1000);
			}
	     }
	   System.out.println("Element is not enabled after "+seconds+" seconds");
	}
	
	/*
	 *  Here, Element is located for every try block to avoid StaleElementReferenceException
	 */
	public static void tryAllClicks(WebDriver driver,By locator) throws InterruptedException
	{
		try
		{
			WebElement element = driver.findElement(locator);
			element.click();
			System.out.println("Clicked using WebElement.click()");
		}
		catch (Exception e)
		{
			System.out.println("WebElement click is not working - trying with JS click "+e.getMessage());
			Thread.sleep(1000);
			try
			{
				WebElement element = driver.findElement(locator);
				JavascriptExecutor js=(JavascriptExecutor) driver;
				js.executeScript("arguments[0].click()", element);
			    System.out.println("Clicked using JS click");
			}
			catch (Exception e2)
			{
				System.out.println("JS click is not working - trying with Actions click "+e.getMessage());
				Thread.sleep(1000);
				
	            WebElement element = driver.findElement(locator);
	            Actions act = new Actions(driver);
	            act.moveToElement(element).click().perform();
	            System.out.println("Clicked using Actions click");
			}
		}
	}
	
	public static Alert waitForAlert(WebDriver driver)
	{
        Alert alt=null;
		
		for(int i=0;i<15;i++)
		{
		  try
		  {
			    alt=driver.switchTo().alert();
			    break;
		  }
		  catch(NoAlertPresentException e)
		  {
			    System.out.println("No Alert found - Waiting for Alert");
			    waitForSecond(1);            
		  }
		}
		return alt;
	}
	
	
	
	public static Alert waitForAlert(WebDriver driver,int time)
	{
        Alert alt=null;
		
		for(int i=0;i<time;i++)
		{
		  try
		  {
			    alt=driver.switchTo().alert();
			    break;
		  }
		  catch(NoAlertPresentException e)
		  {
			    System.out.println("No Alert found - Waiting for Alert");
			    waitForSecond(1);            
		  }
		}
		return alt;
	}
	
	
	
	public static void waitForSecond(int seconds)
	{
		try
		{
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e)
		{

		} 
	}
	
	
	
	public static String captureVisibleViewportScreenshotInBase64(WebDriver driver)
	{
		TakesScreenshot ts=(TakesScreenshot)driver;
		
		String base64 = ts.getScreenshotAs(OutputType.BASE64);
		
		return base64;
	}
	
	

	public static void captureVisibleViewportScreenshot(WebDriver driver)
	{
		try
		{
			FileHandler.copy(((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE), new File("./WebDriverScreenshot/Screenshot_"+getCurrentTime()+".png"));
		} 
		catch (IOException e)
		{
			System.out.println("Something went wrong "+e.getMessage());
		}
	}
	
/*
	public static String testCaptureVisibleViewportScreenshot(WebDriver driver) throws IOException
	{
		TakesScreenshot ts=(TakesScreenshot) driver;
		
		File src= ts.getScreenshotAs(OutputType.FILE);
		
		String destPath = System.getProperty("user.dir")+"//screenshots//Screenshot_"+getCurrentTime()+".png";
		
		FileHandler.copy(src, new File(destPath));
		
		return destPath;
		
	}
*/	
		
	public static void captureWebEementScreenshot(WebElement element)
	{ 
		try
		{
			FileHandler.copy(element.getScreenshotAs(OutputType.FILE), new File("./ElementScreenshot/Screenshot_"+getCurrentTime()+".png"));
		}
		catch (IOException e)
		{
			System.out.println("Something went wrong - "+e.getMessage());
		}
	}
	
	
	
	public static void BasicScreenshotUsingAshot(WebDriver driver)
	{
        AShot ashot = new AShot();
		
		Screenshot screenshot = ashot.takeScreenshot(driver);
		
		File dest=new File("./AshotScreenshot/WithoutScroll/MyAshot_"+getCurrentTime()+"Screenshot.png");
		
		try {
			ImageIO.write(screenshot.getImage(), "PNG", dest);
		} catch (IOException e) {
			System.out.println("Something went wrong - "+e.getMessage());
		}
	}
	
	
	
	public static void FullPageScreenshotUsingAshot(WebDriver driver)
	{
		AShot ashot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100));
		
		Screenshot screenshot = ashot.takeScreenshot(driver);
		
		try {
			ImageIO.write(screenshot.getImage(), "PNG", new File("./AshotScreenshot/WithScroll_FullPage/MyAshot_"+getCurrentTime()+"Screenshot.png"));
		} catch (IOException e) {
			System.out.println("Something went wrong - "+e.getMessage());
		}
	}
	
	
	
	public static void WebElementScreenshotUsingAshot(WebDriver driver,WebElement element)
	{
		Screenshot screenshot= new AShot().takeScreenshot(driver, element);
		try {
			ImageIO.write(screenshot.getImage(), "PNG", new File("./AshotScreenshot/WebElement/MyAshot_"+getCurrentTime()+"Screenshot.png"));
		} catch (IOException e) {
			System.out.println("Something went wrong - "+e.getMessage());
		}
	}
	
	
	
	public static String getCurrentTime()
	{
		 String date = new SimpleDateFormat("HH-mm-ss_dd_MM_yy").format(new Date());
		 return date;
	}
}
	