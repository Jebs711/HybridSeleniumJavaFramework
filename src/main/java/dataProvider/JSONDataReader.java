/*
 * => First, Copy json-simple dependency to pom.xl file
 * 
 *  - For JSon reading, FileReader is preferrable over FileInputSteam.
 *  
 *  
| Feature           | FileReader                                                                 | FileInputStream                           |
| ----------------- | -------------------------------------------------------------------------- | ----------------------------------------- |
| Type              | Character stream                                                           | Byte stream                               |
| Reads             | Text data (characters)                                                     | Binary data (raw bytes)                   |
| Best used for     | .txt, .json, .csv, .xml (text files)                                       | Images, PDFs, audio, video (binary files) |
| Encoding handling | Uses default character encoding                                            | No encoding conversion (just bytes)       |

 *  
 *  ✅ Why FileReader is preferred for JSON?
 *  
 *  1. JSON is a text-based format, so it must be read as characters, not bytes.
 *  2. FileReader automatically converts bytes → characters internally, which is missing in FileInputSteam, need to do manually.
 *  3. JSON parsers (JsonParser etc.) expect a Reader (like FileReader) rather than raw InputStream.
 * 
 *  ❓ So why not FileInputStream?
 *  
 *  -> You can use it, but then you must convert bytes to characters manually:
 *  
 *  => JSONObject internally works as a Map.
 *   - Therefore we can use JSONObject.get(key), which will return Object obj, that we have to type cast to String as needed.
 *  
 *  => But, JSONObject is not excatly map, therefore JSONObject.entrySet() returns raw set without generics (raw Set — no <...>>)
 *     Whereas, Map.entrySet() returns Set<Map.Entry<K, V>> (With Generics)
 *   - So, each element in that set is treated as Object, therefore we've to cast it to Map.Entry<String,Object>, then
 *     we can use getKey() and getValue(). 
 *  
 *  => To get String and JSONArray value using key, we need to cast accordingly to String and JSONArray.
 */

package dataProvider;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JSONDataReader {
	
	public static Object[][] readJSONData()
	{
		JSONParser jsonParser=new JSONParser();
		Object[][] arr=null;
		
		try {
			File src=new File(System.getProperty("user.dir")+"//testdata//data.json");
			FileReader reader=new FileReader(src);
			Object obj = jsonParser.parse(reader);  //parse method returns Object
			JSONObject jsonObject= (JSONObject)obj;
//			getStringValue(jsonObject,key);
//			getJsonArray(jsonObject, jsonArrayKey);			
			
			int row=jsonObject.size();
			arr=new Object[row][2];
			int i=0;
			
			for(Object entryObj :jsonObject.entrySet())
			{
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) entryObj;
				arr[i][0] = entry.getKey();
				arr[i][1] = entry.getValue();
				i++;
			}
		}
		catch (IOException | ParseException e)
		{
			System.out.println("Could not read or parse file "+e.getMessage());
		}
		return arr;
	}
	
	
	public static String getStringValue(JSONObject jsonObject, String key)
	{
		Object obj= jsonObject.get(key);
		String value=(String) obj;
		return value;
	}
	
	public static JSONArray getJsonArray(JSONObject jsonObject, String jsonArrayKey)
	{
		Object obj=jsonObject.get(jsonArrayKey);
		JSONArray jsonArray = (JSONArray) obj;
		return jsonArray;
	}
}
