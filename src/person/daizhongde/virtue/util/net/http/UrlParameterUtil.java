package person.daizhongde.virtue.util.net.http;

import java.io.UnsupportedEncodingException;

public class UrlParameterUtil {
	
	public static String getParameter(String url, String name) throws UnsupportedEncodingException{
		if(url.length()==0)return "";
		if(url.charAt(0)!='?')return "";
		url=java.net.URLDecoder.decode(url, "UTF-8");
		url=url.substring(1);
		if(url.length()==0)return "";
		String[] params=url.split("&");
		
	    String p = "";
	    for(int i=0;i<params.length;i++){
	        if(params[i].indexOf(name) >= 0){           
	            p = params[i].split("=")[1];
	        }
	    }
	    return p;
	}
	
//	function getParameter(name){ //js 
//	    var paramStr=location.search; 
//	    if(paramStr.length==0)return null; 
//	    if(paramStr.charAt(0)!='?')return null; 
//	    paramStr=unescape(paramStr); 
//	    paramStr=paramStr.substring(1); 
//	    if(paramStr.length==0)return null; 
//	    var params=paramStr.split('&'); 
//	    //alert(params);
//	    var p = null;
//	    for(var i=0;i<params.length;i++){
//	        if(params[i].indexOf(name) >= 0){           
//	            p = params[i].split('=');
//	            p = p[1];         
//	        }
//	    }
//	    return p;
//	}
}
