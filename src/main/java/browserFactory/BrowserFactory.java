package browserFactory;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class BrowserFactory {
	
	static WebDriver driver;
	
	public static WebDriver getBrowserInstance()
	{
		return driver;
	}
	
	public static WebDriver startBrowser(String browserName, String applicationURL)
	{		
		if(browserName.equals("Chrome") || browserName.equals("GC") || browserName.equals("Google Chrome"))
		{
			driver=new ChromeDriver();
		}
		else if(browserName.equals("Firefox") || browserName.equals("FF") || browserName.equals("Mozila FireFox"))
		{
			driver=new FirefoxDriver();
		}
		else if(browserName.equals("Safari"))
		{
			driver=new SafariDriver();
		}
		else if(browserName.equals("Edge"))
		{
			driver=new EdgeDriver();
		}
		else
		{
			driver=new ChromeDriver();
		}
		
//		Incase of Opera or any other browser, which is not mentioned above and if default driver code is not added in IfElse condition,
//		then driver will remain null, so it will throw NullPointerException.
		
		driver.manage().window().maximize();
		
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		
		driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(20));
		
		driver.get(applicationURL);
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		return driver;
	}

}
