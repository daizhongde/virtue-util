package person.daizhongde.virtue.util.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

public class ConfigFileReader { 
//	private static int i=0;
	/**
	 * 读取配置项
	 * 
	 * @param key-属性名称
	 * @return 属性值
	 */
	public String findProperty(String key) {
//		System.out.println("i="+ i);
//		i++;
		
		String value = "";
		// System.out.println("user.dir="+System.getProperty("user.dir"));
		Properties prop = new Properties();
		InputStream in = getClass().getResourceAsStream("/init.properties");
		try {
			prop.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		value = prop.getProperty(key);
//		System.out.println(key+":" + value);
		return value;
	}
	/**
	 * 读取配置项
	 * 
	 * @param key-属性名称
	 * @return 属性值
	 */
	public String findProperty(String fname,String key) {
		String value = "";
		// System.out.println("user.dir="+System.getProperty("user.dir"));
		Properties prop = new Properties();
		InputStream is;
		try {
			if( fname.startsWith("/com/") )
			{
//				System.out.println("1 starts with com/");
//				is = getClass().getResourceAsStream( fname );
//				Properties  properties = System.getProperties();
////				properties.list(System.out);
//				if( properties.get("sun.java.command").toString().indexOf("junit") != -1 ){
//					
//				}
				String path = getClass().getResource("/").getPath();//得到工程名WEB-INF/classes/路径

//				同目录文件夹：classes，m2e-jee，test-classes
				int index = path.indexOf("/test-classes/");
				
//				System.out.println("path:"+path);
//				path:/D:/daizd/Workspaces/MyEclipse%202015%20CI/migration/target/test-classes/
//				or: D:/tomcat/webapps/migration/WEB-INF/classes/
				
			    path=path.substring(0, 
			    		index==-1 ? path.indexOf("/classes/") : index
			    	);//从路径字符串中取出工程路径

			    if(fname.indexOf("authority") != -1){
			    	is = getClass().getResourceAsStream( "/"+fname );
			    }else{
			    	is = new FileInputStream( java.net.URLDecoder.decode( path +"/classes/"+ fname,"UTF-8") );
			    }
			}
			else if( fname.startsWith("/WEB-INF/") )
			{
//				System.out.println("2...");
				String path =getClass().getResource("/").getPath();//得到工程名WEB-INF/classes/路径
				
//				System.out.println("path:"+path);
			    path=path.substring(0, path.indexOf("/WEB-INF/"));//从路径字符串中取出工程路径
				is = new FileInputStream( java.net.URLDecoder.decode( path + fname,"UTF-8") );
			}
			else if( fname.indexOf("/")==-1 )
			{
//				System.out.println("2...");
				is = getClass().getResourceAsStream( "/"+fname );
			}
			else
			{
//				System.out.println("3...");
				is = new FileInputStream( fname);
			}
			prop.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		value = prop.getProperty(key);
//		System.out.println(key+":" + value);
		return value;
	}

	public static void main(String[] args) {
		ConfigFileReader cf = new ConfigFileReader();
		System.out.println("value:"+cf.findProperty("sql")+"\n"+cf.findProperty("tableColumnNames"));
		String[] ns = cf.findProperty("tableColumnNames").split("\\|");
		System.out.println("ns[0]:"+ns[0]);
		Vector leftTableColumnV=null;
		for (int i = 0; i < ns.length; i++) {
			leftTableColumnV.add(ns[i]);
		}
	}
}
