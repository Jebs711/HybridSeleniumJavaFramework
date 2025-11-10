/*
 * - Each ExtentTest object is associated to each separate test log entry in ExtentReports.
 * - If ExtentReports is the whole report file, then ExtentTest is the block inside the report where logs, screenshots, status, etc.
 *   are written for one test method.
 *   
 * - ITestResult result, contains the runtime information about each test method. 
 * 
 * ✅ Relationship Between ITestResult and ExtentTest : 
 * 
 * | Item        | Provided by   | Purpose                                                      |
   | ------------| ------------- | ------------------------------------------------------------ |
   | ITestResult | TestNG        | Gives test execution details (name, status, exception, etc.) |
   | ExtentTest  | ExtentReports | Stores & logs report info for that test                      |
 * 
 * - ITestResult gives the test result/info, ExtentTest records it in the report.
 * 
 * ✅ Diagram to clarify : 
 * 
 * ┌────────────────────┐       ┌───────────────────────┐
   │ TestNG (runs test) │       │  ExtentReports (HTML) │
   └─────────┬──────────┘       └─────────┬─────────────┘
             │                            │
        ITestResult  ─────────▶       ExtentTest
             │ (gives info)               │ (logs info)
             │                            │
  name, status, exception     pass(), fail(), attach screenshot
  
 * 
 * 
 *  ✅ If there are two listeners in testng.xml execution file, then all of them will be executed, and TestNG will call their
 *     listener methods in the order they are listed in the XML file.
 *  
 *  ✅ If same listener is added in both XML and @Listeners annotation?
 *
 *   - It will execute twice → duplicate logs or reports.
 *   - So never register a listener in two places.
 *   
 *  ✅ Summary : 
 *  
 *   | Scenario                                              | Result                                           |
 *   | ----------------------------------------------------- | ------------------------------------------------ |
 *   | 2 different listeners in XML                          | Both run normally                                |
 *   | Same listener added twice                             | Methods execute twice → bad                      |
 *   | One listener in XML + one via `@Listeners` annotation | Both execute                                     |
 *   | Conflicts?                                            | Only if they write to same file or global object |
 *   
 *   
 *  ✅ What if two different listeners are used, one via @Listeners annotation and another in XML? who will be executed first? 
 *  
 *  - both listeners will be executed, but TestNG does NOT guarantee a fixed order between annotation-based and XML-based listeners.
 *  - but for years, XML has been executed first, still you should not rely on order.
 *  
 *  
 *  
 *  🧩 Base64 Screenshot : 
 *
 *  - In Selenium (Java), when you take a screenshot, you can get it in mostly two common formats:
 *
 *  1. File format – saved as an image file (PNG/JPG)
 *  2. Base64 format – returned as a Base64-encoded string
 *
 *
 *  ✅ What is Base64 format in screenshot?
 *
 *  - Base64 is a text representation of binary image data. Instead of saving the screenshot as a file,
 *    you get a long string like:
 *
 *    iVBORw0KGgoAAAANSUhEUgAA...
 *    
 *  - This is useful when:
 *
 *  1. You want to attach screenshots into Extent Reports, Allure, Cucumber, etc.
 *  2. You want to send screenshots over API or network
 *  3. You want to avoid saving files to disk (use memory only).
 *  
 *
 *  => You can convert any JPG/PNG file into Base64 format and also you can get JPG/PNG file from any Base64 encoded String.  
 *  
 *    
 *  ✅ What is the benifit of attaching base64 format screenshot into Extent report instead of File format?
 *
 *  - If you create screenshot in File format, then you have to copy screenshot to a physical location and then you have to attach
 *    screenshot fromt there in extent report.
 *  - Whereas, for base64, you just have to send Base64 String in a report and Extent report itself will convert image from Base64 to
 *    relevant JPG/PNG format.
 *   
 */

package listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import browserFactory.BrowserFactory;
import helper.Utility;

public class ExtentTestNGITestListener implements ITestListener{
	
	ExtentReports extent =ExtentManager.getInstance();
	
	ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	
	
	
	public void onTestStart(ITestResult result)
	{
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
		parentTest.set(extentTest);
    }
	
	public void onTestSuccess(ITestResult result)
	{
	   parentTest.get().pass("Test passed");
	}
	
	public void onTestFailure(ITestResult result)
	{
	   WebDriver driver = BrowserFactory.getBrowserInstance();
	   String base64 = Utility.captureVisibleViewportScreenshotInBase64(driver);
	   parentTest.get().fail("Test Failed "+result.getMethod().getMethodName());
	   parentTest.get().fail(result.getThrowable().getMessage(),MediaEntityBuilder.createScreenCaptureFromBase64String(base64,"My Screenshot").build());
	}

	public void onTestSkipped(ITestResult result)
	{
	    parentTest.get().skip("Test Skipped "+result.getThrowable().getMessage());
	}

	public void onFinish(ITestContext context)
	{
		extent.flush();
	}
	

}
