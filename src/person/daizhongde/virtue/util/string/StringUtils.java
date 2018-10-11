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
	/**
	 * 清除换行
	 * <p>
	 * eg:shell命令多行编辑功能支持添加.即支持编辑器中手工文本换行，也支持shell脚本换行语法
	 * @param str
	 * @return
	 */
	public static String replaceAllEnter(String str){
		str = str.replaceAll("\t|\r|\n", " ");
		return str.replace(" \\ ", " ");
	}  

}
