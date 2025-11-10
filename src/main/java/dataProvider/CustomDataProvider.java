/*
 *  - If we keep DataProvider in same class, and suppose multiple employee in a team wants to use same DataProvider, then
 *    they will write DataProvider by themselves and it will create Duplicacy of code.
 *  - Therefore, we're keeping all DataProviders in separate class.
 *  - Here, method should be static so that @Test can call Dataprover with just name.
 */

package dataProvider;

import org.testng.annotations.DataProvider;

public class CustomDataProvider {
	
	@DataProvider(name="loginDetails")
	public static Object[][] getData()
	{
		Object[][]arr=ExcelReader.getDataFromSheet("login");
		return arr;
	}
	
	@DataProvider(name="UserDataSet")
	public static Object[][] getUserData()
	{
		Object[][] arr= JSONDataReader.readJSONData();
		return arr;
	}
}
