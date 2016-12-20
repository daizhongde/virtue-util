package person.daizhongde.virtue.util.config;

public class ConfigConstant {
	private final static ConfigFileReader cf = new ConfigFileReader();
	
	public final static String driver = cf.findProperty("driver");
	public final static String url = cf.findProperty("url");
	public final static String dbuser = cf.findProperty("dbuser");
	public final static String dbpass = cf.findProperty("dbpass");

//	public final static int singleFileRecordSize = Integer.valueOf(cf.findProperty("singleFileRecordSize"));
//	public final static String compress = cf.findProperty("compress");
//	public final static String departmentColName = loginColumnNames.split("\\|")[0];
//	public final static Boolean login2 = departmentColName.trim().equalsIgnoreCase("");
//	public final static String TAuthorityModule_query = cf.findProperty("sql.properties","TAuthorityModule_query");
//	public final static String TAuthorityModule_query = cf.findProperty( 
//			cf.findProperty("configFilePath")+"sql.properties",
//			"TAuthorityModule_query");
	
//	public final static String TAuthorityModule_ColumnTypes = cf.findProperty("sql.properties","TAuthorityModule_ColumnTypes");
	
//	public final static int TAuthorityModule_fromIndex = TAuthorityModule_query..toLowerCase()indexOf("from ")+5;
	
//	static{
//		System.out.println("1:"+TAuthorityModule_fromIndex);
//		System.out.println("2:"+TAuthorityModule_query.indexOf(" ",TAuthorityModule_fromIndex));
//	}
	
//	public final static String TAuthorityModule_tableName = TAuthorityModule_query.substring(
//			TAuthorityModule_fromIndex, TAuthorityModule_query.indexOf(" ",TAuthorityModule_fromIndex));
//	
}
