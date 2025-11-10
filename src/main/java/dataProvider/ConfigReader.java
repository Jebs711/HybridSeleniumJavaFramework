/*
 *  - pro.get(key) value returns Object not String.
 *  - Therefore, we're using pro.getProperty(key), which will return String value of mapped value. 
 *  
 */


package dataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	public static String getProperty(String key) {
		
		Properties pro = new Properties();
		
		try {
			pro.load(new FileInputStream(new File(System.getProperty("user.dir") + "//Config//QA config.properties")));
		} catch (FileNotFoundException e) {
			System.out.println("Could not found the file "+e.getMessage());
		} catch (IOException e) {
			System.out.println("Could not load the file or eror while reading the file "+e.getMessage());
		}
		
		String value = pro.getProperty(key);
		return value;
	}
}
