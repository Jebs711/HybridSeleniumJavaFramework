/*
 *  - ExtentManager class just returns an instance of ExtentReports
 *  - We can have 100 test cases but our report should not have instance for each test, only one report instance should be there for
 *    all tests.
 *    
 *  📌 Quick Reference for Common Java Date Patterns : 
 *  
 *  | Pattern | Output Example      |
 *  | ------- | ------------------- |
 *  | `dd`    | 09 (day of month)   |
 *  | `DD`    | 313 (day of year)   |
 *  | `MM`    | 11 (month number)   |
 *  | `MMM`   | Nov                 |
 *  | `yyyy`  | 2025                |
 *  | `HH`    | 17 (24-hour format) |
 *  | `hh`    | 05 (12-hour format) |
 *  | `mm`    | 00 (minutes)        |
 *  | `ss`    | 38 (seconds)        |
 *    
 */

package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import helper.Utility;

public class ExtentManager {
	
	static ExtentReports extent;
	
	public static ExtentReports getInstance()
	{
		if(extent==null)
		{
		 	extent = createInstance();      //If there is no existing extent instance, then create one and return else return existing one directly. 
		}
		return extent;
	}
	
	public static ExtentReports createInstance()
	{
		ExtentSparkReporter sparkReporter=new ExtentSparkReporter(System.getProperty("user.dir")+"//reports//Automation_"+Utility.getCurrentTime()+".html");
		sparkReporter.config().setTheme(Theme.DARK);
		sparkReporter.config().setReportName("Automation Report");
		sparkReporter.config().setDocumentTitle("Sprint 1 Report");
		sparkReporter.config().setEncoding("utf-8");
		sparkReporter.config().setTimelineEnabled(true);
		sparkReporter.config().setTimeStampFormat("dd-MM-yy HH:mm:ss");  //DD Gives Day of year → 313 (because 09 Nov 2025 = 313th day of 2025)

		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		
		return extent;
	}
}
