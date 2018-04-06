package person.daizhongde.virtue.util.string;

import java.io.File;

public class StringUtils {

	public static String mergeStringWithSeparator(String...args){  
	    StringBuilder sb=new StringBuilder();  
	    for (String arg : args) {  
	        sb.append(arg);  
	        sb.append(File.separator);  
	    }  
	          
	    return sb.substring(0, sb.length()-1).toString();  
	}  
	
}
