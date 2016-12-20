package person.daizhongde.virtue.util.db;

import java.sql.*;

public class HiveConnection {
	/** 
	 * org.apache.hive.jdbc.HiveDriver 
	 * org.apache.hadoop.hive.jdbc.HiveDriver  */
	private final String driver = "org.apache.hive.jdbc.HiveDriver";
	//hive 
//	jdbc:hive://10.16.124.71:10002/
//	jdbc:hive2://n4.hnzy.com:10000/default
//	124.232.132.94  n4.hnzy.com
//	private final String url = "jdbc:hive://10.16.124.71:10002/";
	//spark 
	private final String url = "jdbc:hive2://10.158.240.9:10000/orderdb";
	/** migesystem/migesystem123!@#   doccenter/doccenter123\!\@\#      */
	private final String user = "ordercenter";
	private final String password = "ordercenter123!@#";
//	private final String user = "hadoopadmin";
//	private final String password = "hadoopadmin123#";
	private Connection conn = null;

	public HiveConnection() {
		try {
			Class.forName(driver);
			this.conn = DriverManager.getConnection(url, user, password);
			System.out.println("连接成功了");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("......");
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return conn;
	}

	public void close() {
		try {
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("数据库连接关闭失败......");
		}
	}

	public static void main(String args[]) {
		HiveConnection cd = new HiveConnection();
		
		Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        try
        {
        	connection = cd.getConnection();
        
        
             if(connection!=null)
                 System.out.println("success");
             statement=connection.createStatement();
             
//             select TABSCHEMA,TABNAME,COLNAME from SYSCAT.COLUMNS where UPPER(TABSCHEMA)='NBADV'"
//             select name,creator from sysibm.systables where type='T' and UPPER(name) like '%CC_ORDER%'
//             select name,creator from sysibm.systables where type='T' and creator='NBADV' //SYSIBM  DB2ADMIN
             
//             TABSCHEMA,TABNAME,COLNAME
//             type='T' and creator='NBADV' and UPPER(name) like '%CC_ORDER%' and UPPER(TABSCHEMA)='NBADV'
//             String sql = "select * from sysibm.systables where type='T' and creator='NBADV' and UPPER(name) like '%CC_ORDER%'";
//             String sql = "select * from sysibm.systables where type='T' and creator='NBADV' and UPPER(name) = 'CC_ORDER'";
             /* 查询表列 */
             String sql = "select * from orderdb.cc_order limit 2 ";
             
             resultSet=statement.executeQuery(sql);

 			/* 进行数据处理 */
 			ResultSetMetaData rsmd;
 			rsmd = resultSet.getMetaData();
 			// 获取元数据
 			int j = 0;
 			j = rsmd.getColumnCount(); // 获得结果集的列数
 			for (int k = 0; k < j; k++) {
 				System.out.print(rsmd.getColumnName(k + 1)); // 显示表中字段属性
 				System.out.print("\t");
 			}
 			System.out.print("\n");
 			int limit=0;
 			while (resultSet.next()) // 显示结果集的内容
 			{
 				for (int i = 0; i < j; i++) {
 					String s = resultSet.getString(i + 1);
 					System.out.print( s == null || s.length()< 50 ? s : s.substring(0, 40) );
// 					System.out.print(resultSet.getString(i + 1));
 					System.out.print("\t");
 				}
 				System.out.print("\n");
 				
 				limit++;
             	 if(limit==20)  break;
 			}
           
             
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }finally{
        	try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
}