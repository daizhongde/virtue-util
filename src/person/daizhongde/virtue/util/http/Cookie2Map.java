package person.daizhongde.virtue.util.http;

import java.util.HashMap;
import java.util.Map;

public class Cookie2Map {
	public static Map<String, String> cookie2Map(String coo){
		Map<String, String>  map = new HashMap<String, String>();
		String[] a = coo.split(";");
		for(String c: a){
			String[] kv = c.split("=");
			map.put(kv[0].trim(), kv[1].trim());
		}
		
		return map;
	}
}
