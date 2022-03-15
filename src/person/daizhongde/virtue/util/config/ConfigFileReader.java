package person.daizhongde.virtue.util.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

/** 
 * @Description: 
 *    以后读任何配置文件统一整这个类
 *
 *
 * @author daizd
 * @date 2021年12月21日
 *
 */
public class ConfigFileReader { 
//	private static int i=0;
	/**
	 * 读取配置项
	 * 
	 * @param key-属性名称
	 * @return 属性值
	 */
	public static String findProperty(String key) {
//		System.out.println("i="+ i);
//		i++;
		
		String value = "";
		// System.out.println("user.dir="+System.getProperty("user.dir"));
		Properties prop = new Properties();
//		InputStream in = getClass().getResourceAsStream("/init.properties");
		InputStream in = ConfigFileReader.class.getResourceAsStream("/init.properties");
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
	 * <p>
	 * 前缀                          例子                          					      说明
<br>classpath: 	classpath:com/myapp/config.xml  从classpath中加载。
<br>file: 		file:/data/config.xml 			 作为 URL 从文件系统中加载。
<br>http: 		http://myserver/logo.png 		 作为 URL 加载。
<br>(none) 		/data/config.xml 				 根据 ApplicationContext 进行判断。
<br>ini 		run jar 同级目录的ini配置文件 			 用于读软件产品安装目录下的配置

	 * @param key-属性名称
	 * @return 属性值
	 */
	public static String findProperty(String fname,String key) {
		String value = "";
		// System.out.println("user.dir="+System.getProperty("user.dir"));
		Properties prop = new Properties();
		InputStream is=null;
		String classpath = ConfigFileReader.class.getResource("/").getPath();//得到工程名WEB-INF/classes/路径
		
		try {
			if( fname.startsWith("com/") || fname.startsWith("person/") || fname.startsWith("org/") )
			{
//				System.out.println("1 starts with com/");
//				is = getClass().getResourceAsStream( fname );
//				Properties  properties = System.getProperties();
////				properties.list(System.out);
//				if( properties.get("sun.java.command").toString().indexOf("junit") != -1 ){
//					
//				}

//				System.out.println("###### path:"+path);
//				同目录文件夹：classes，m2e-jee，test-classes
				int index = classpath.indexOf("/test-classes/");
				
//				System.out.println("path:"+path);
//				path:/D:/daizd/Workspaces/MyEclipse%202015%20CI/migration/target/test-classes/
//				or: D:/tomcat/webapps/migration/WEB-INF/classes/
				
				classpath=classpath.substring(0, 
			    		index==-1 ? classpath.indexOf("/classes/") : index
			    	);//从路径字符串中取出工程路径

			    if(fname.indexOf("authority") != -1){
			    	is = ConfigFileReader.class.getResourceAsStream( "/"+fname );
			    }else{
			    	is = new FileInputStream( java.net.URLDecoder.decode( classpath +"/classes/"+ fname,"UTF-8") );
			    }
			}
			else if( fname.startsWith("/WEB-INF/") )
			{
//				System.out.println("2...");
				
//				System.out.println("path:"+path);
				classpath=classpath.substring(0, classpath.indexOf("/WEB-INF/"));//从路径字符串中取出工程路径
				is = new FileInputStream( java.net.URLDecoder.decode( classpath + fname,"UTF-8") );
			}
			else if( fname.indexOf(".ini")!=-1)
			{
//				System.out.println("ini...");
				String directory = ResourceLoadFromJarUtils.getCurrentFilePath();
				System.out.println("directory:"+directory);

				if(directory.indexOf("target")!=-1){

					String dir2 = ResourceLoadFromJarUtils.getCurrentFilePath();
					System.out.println("directory2:"+dir2);
					is = ConfigFileReader.class.getResourceAsStream("/" + fname);

				} else {

					File jarfile = new File(directory);
					System.out.println("jarfile.getParentFile().getPath() :"+jarfile.getParentFile().getPath() );
					File tmpFile = new File(jarfile.getParentFile().getPath() +"/"+fname);
					if(tmpFile.exists()){
						is = new FileInputStream( jarfile.getParentFile().getPath() +"/"+fname);
					}else{
						is = new FileInputStream( jarfile.getParentFile().getPath() +"/bin/"+fname);
					}
				}

			}
			else if( fname.indexOf("/")==-1 && fname.indexOf("\\")==-1)
			{
//				System.out.println("3...");
				if(null == ConfigFileReader.class.getResourceAsStream( "/"+fname ) )
					is = ResourceLoadFromJarUtils.loadResourceFromJars( fname );
				else
					is = ConfigFileReader.class.getResourceAsStream( "/"+fname );
			}
			else
			{
//				System.out.println("3...");
				is = new FileInputStream( fname);
			}
			prop.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(null!=is){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
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
	public static Properties findAll( String fname ) {
		String value = "";
		// System.out.println("user.dir="+System.getProperty("user.dir"));
		Properties prop = new Properties();
		InputStream in = null;
		try {
			if( fname.startsWith("/com/")
					|| fname.indexOf("/")==-1 ){
//				InputStream in = getClass().getResourceAsStream("/"+fname);
				in = ConfigFileReader.class.getResourceAsStream( fname );
			}else{
				in = new FileInputStream( fname);
			}
			prop.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		value = prop.getProperty(key);
//		System.out.println(key+":" + value);
		return prop;
	}
	// Prevent instantiation
    private void ConfigReader_PROP() {}
    
    
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
