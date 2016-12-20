package person.daizhongde.virtue.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import person.daizhongde.virtue.util.config.ConfigConstant;
import person.daizhongde.virtue.util.config.ConfigFileReader;

public class JDBC {
	/**  sql server2000  */
//	private static final String DRIVERCLASS = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
//	private static final String URL = "jdbc:microsoft:sqlserver://127.0.0.1:1433;DatabaseName=db_DrinkeryManage";
	
	/**  sql server2005/2008  */
//	private static final String DRIVERCLASS = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//	private static final String URL = "jdbc:sqlserver://localhost:1433;DatabaseName=db_MoneyManage";
	
	/**  oracle9i  */
//	private static final String DRIVERCLASS = "oracle.jdbc.OracleDriver";
//	private static final String URL = "jdbc:oracle:thin:@200.200.200.53:1521:fsnew";
	/**  oracle9i,10g,11g may only nse url without driver  */
//	private static final String URL = "jdbc:oracle:oci:@POST97";
//	private static final String URL = "jdbc:oracle:thin:@20.12.3.97:1521:NCBSDB";
	
//	private static final String USERNAME = "sa";
//	private static final String PASSWORD = "123";

	private static final ThreadLocal threadLocal = new ThreadLocal();

	private static ConfigFileReader cf = new ConfigFileReader();
	
	static {// 在静态代码块中加载数据库驱动
		try {
			if(ConfigConstant.driver == null || ConfigConstant.driver.trim().equalsIgnoreCase("")){
				//什么都不做
			}else{
//				Class.forName(DRIVERCLASS).newInstance();// 加载数据库驱动
				Class.forName(cf.findProperty("driver")).newInstance();// 加载数据库驱动
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*public static Connection getConnection() {// 创建数据库连接的方法
		Connection conn = threadLocal.get();// 从线程中获得数据库连接
		if (conn == null) {// 没有可用的数据库连接
			try {
//				if(ConfigConstant.driver == null || ConfigConstant.driver.trim().equalsIgnoreCase("")){
//					OracleDataSource ds;
//					ds = new OracleDataSource();
//		            ds.setURL(cf.findProperty("url"));
//		            conn=ds.getConnection(cf.findProperty("dbuser"), cf.findProperty("dbpass"));
//				}else{
//
//					conn = DriverManager.getConnection(cf.findProperty("url"), cf.findProperty("dbuser"), cf.findProperty("dbpass"));// 创建新的数据库连接
//				}
//				conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);// 创建新的数据库连接
				conn = DriverManager.getConnection(cf.findProperty("url"), cf.findProperty("dbuser"), cf.findProperty("dbpass"));// 创建新的数据库连接
				threadLocal.set(conn);// 将数据库连接保存到线程中
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;// 返回数据库连接
	}*/
	
	public static Connection getConnection() throws SQLException {// 创建数据库连接的方法
		Connection conn = (Connection)threadLocal.get();// 从线程中获得数据库连接
		if (conn == null) {// 没有可用的数据库连接
//			try {
//				if(ConfigConstant.driver == null || ConfigConstant.driver.trim().equalsIgnoreCase("")){
//					OracleDataSource ds;
//					ds = new OracleDataSource();
//		            ds.setURL(cf.findProperty("url"));
//		            conn=ds.getConnection(cf.findProperty("dbuser"), cf.findProperty("dbpass"));
//				}else{
//
//					conn = DriverManager.getConnection(cf.findProperty("url"), cf.findProperty("dbuser"), cf.findProperty("dbpass"));// 创建新的数据库连接
//				}
//				conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);// 创建新的数据库连接
				conn = DriverManager.getConnection(cf.findProperty("url"), cf.findProperty("dbuser"), cf.findProperty("dbpass"));// 创建新的数据库连接

				threadLocal.set(conn);// 将数据库连接保存到线程中
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
		}
		return conn;// 返回数据库连接
	}
	public static boolean closeConnection() {// 关闭数据库连接的方法
		boolean isClosed = true;// 默认关闭成功
		Connection conn = (Connection)threadLocal.get();// 从线程中获得数据库连接
		if (conn != null) {// 数据库连接可用
			threadLocal.set(null);// 清空线程中的数据库连接
			try {
				conn.close();// 关闭数据库连接
			} catch (SQLException e) {
				isClosed = false;// 关闭失败
				e.printStackTrace();
			}
		}
		return isClosed;// 返回关闭结果
	}
	
	public static void main(String args[]) throws SQLException{
//		ResultSet rs = getConnection().createStatement().executeQuery("select * from [db_MoneyManage].[dbo].[tb_authority_user]");
//		ResultSet rs = getConnection().createStatement().executeQuery("select * from tb_authority_user");
//		ResultSet rs = getConnection().createStatement().executeQuery("select main_inst_id,tlr_id,node_type from cpabg1.tb_tlr_info");
		//mysql
//		ResultSet rs = getConnection().createStatement().executeQuery("select * from t_dzd_user");
		//sqlserver 2008
		ResultSet rs = getConnection().createStatement().executeQuery("SELECT TOP 1000 [userid]      ,[username]      ,[password]      ,[passconf]  FROM [DB_JXC].[dbo].[tb_username]");//mysql
		int i=0;
		while(rs.next()&&i<5){
			System.out.println("ID:"+rs.getString(1)+"|用户登陆名:"+rs.getString(2)+"|真实姓名:"+rs.getString(3));
			i++;
		}
		
	}
}
