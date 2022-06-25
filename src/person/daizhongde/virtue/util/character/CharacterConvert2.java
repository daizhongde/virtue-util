package person.daizhongde.virtue.util.character;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 将字符使用另一种编码表示 
 * 
 * 这是真实的转换类，不是将乱码转对
 * 
 * 
 * 开发中，未使用
 * 
 * @author dzd
 * @modify 20220620 method to static
 * 
 * 
 */
public class CharacterConvert2{
	private static Logger log = LogManager.getLogger(CharacterConvert2.class.getName());
//	
//	public static String getStr(String s){
//	    String str="";
//	  	try{
//	  		str= new String(s.getBytes("ISO-8859-1"),"GBK");
//	  	}catch(Exception e){
//	  		e.printStackTrace();
//	  	}
//	  	return str;
//	}
	
	public static String getISO2GBK(String s){
	    String str="";
	  	try{
	  		str= new String(s.getBytes("ISO-8859-1"),"GBK");
	  	}catch(Exception e){
	  		e.printStackTrace();
	  	}
	  	return str;
	}
	public static String getISO2UTF8(String s){
	    String str="";
	  	try{
	  		str= new String(s.getBytes("ISO-8859-1"),"UTF-8");
	  	}catch(Exception e){
	  		e.printStackTrace();
	  	}
	  	return str;
	}
	public static String getGBK2ISO(String s){
	    String str="";
	  	try{
	  		str= new String(s.getBytes("GBK"),"ISO-8859-1");
	  	}catch(Exception e){
	  		e.printStackTrace();
	  	}
	  	return str;
	}
	public static String getGBK2UTF8(String s){
	    String str="";
	  	try{
	  		str= new String(s.getBytes("GBK"),"UTF-8");
	  	}catch(Exception e){
	  		e.printStackTrace();
	  	}
	  	return str;
	}
	public static String getUTF82ISO(String s){
	    String str="";
	  	try{
	  		str= new String(s.getBytes("UTF-8"),"ISO-8859-1");
	  	}catch(Exception e){
	  		e.printStackTrace();
	  	}
	  	return str;
	}
	public static String getUTF82GBK(String s){
	    String str="";
	  	try{
	  		str= new String(s.getBytes("UTF-8"),"GBK");
	  	}catch(Exception e){
	  		e.printStackTrace();
	  	}
	  	return str;
	}
	public static String getASCII2GBK(String s){
	    String str="";
	  	try{
	  		str= new String(s.getBytes("US-ASCII"),"GBK");
	  	}catch(Exception e){
	  		e.printStackTrace();
	  	}
	  	return str;
	}
	public static String getUTF162GBK(String s){
	    String str="";
	  	try{
	  		str= new String(s.getBytes("UTF-16"),"GBK");
	  	}catch(Exception e){
	  		e.printStackTrace();
	  	}
	  	return str;
	}
	
	public static String testCharSet(String s){
		String[] arr = {"GB18030","UTF-8","ISO-8859-1"};
		 String str="";
		  	try{
		  		for(int i =0; i<arr.length; i++){
		  			for(int j =0; j<arr.length; j++){
		  				str= new String(s.getBytes(arr[i]),arr[j]);
		  				System.out.println(i+"-"+j+":"+arr[i]+" to "+arr[j]+" -->"+str);
		  			}
		  		}
		  		
		  	}catch(Exception e){
		  		e.printStackTrace();
		  	}
		  	return str;
	}
	
	public static String testCharSet(String s, String level){
		String[] arr = {"GBK","UTF-8","ISO-8859-1"};
		 String str="";
		  	try{
		  		for(int i =0; i<arr.length; i++){
		  			for(int j =0; j<arr.length; j++){
		  				str= new String(s.getBytes(arr[i]),arr[j]);
		  				log.error(i+"-"+j+":"+arr[i]+" to "+arr[j]+" -->"+str);
		  			}
		  		}
		  		
		  	}catch(Exception e){
		  		e.printStackTrace();
		  	}
		  	return str;
	}
	
	public static void main(String args[]){
		testCharSet("\r");
	}
}