/*
 * - ITestResult result, represents the result of a test, which contains all information about our test result.
 * 
 * 
 * 
 */

package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerDemo implements ITestListener {
	
	public void onTestSuccess(ITestResult result)
	{
	    System.out.println("Test passed "+result.getMethod().getMethodName());
	}
	
	public void onTestFailure(ITestResult result)
	{
	    System.out.println("Test Failed "+result.getMethod().getMethodName());
	    System.out.println("Exception Thrown By This Method "+result.getThrowable().getMessage());
	}
	
	public void onTestSkipped(ITestResult result)
	{
	    
	}
	
	public void onStart(ITestContext context)
	{
	    
	}
	

}
