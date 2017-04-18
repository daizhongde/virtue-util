package person.daizhongde.virtue.util.jdbc;

import java.sql.*;

public class DB2Connection {
	private final String driver = "com.ibm.db2.jcc.DB2Driver";
//	private final String url = "jdbc:db2://10.158.133.154:50000/orderdb1";
	private final String url = "jdbc:db2://10.158.133.154:50000/orderdb1:currentSchema=nbadv;";
	private final String user = "nbadv";
	private final String password = "zoom8992";
	private Connection conn = null;

	public DB2Connection() {
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
		DB2Connection cd = new DB2Connection();
		
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
//             String sql = "select * from sysibm.systables where type='T' and creator='NBADV' and UPPER(name) = 'CC_CUSTOMER'";
             String sql = "SELECT count(*) FROM NBADV.g_tobacco";
             String sql1 = "SELECT * FROM NBADV.CC_PROV_CITY fetch first 10 rows only ";
             String sql2 = "SELECT * FROM NBADV.CC_CUSTOMER fetch first 10 rows only";
             String sql3 = "SELECT * FROM NBADV.cc_order fetch first 10 rows only";
             String sql4 = "SELECT * FROM NBADV.cc_order_detail fetch first 10 rows only";
             
             /* 查询表列 */
//             String sql = "select * from SYSCAT.COLUMNS where UPPER(TABSCHEMA)='NBADV' and TABNAME='CC_ORDER_DETAIL' ";
             /* 查询最大最小值    */
             String sqlMax = "SELECT MAX(amt_order_sum),MAX(qty_demand_sum1),MAX(qty_order_sum1) FROM nbadv.cc_order where yearmonth between '201401' and '201605'";
             String sqlMin = "SELECT MIN(amt_order_sum),MIN(qty_demand_sum1),MIN(qty_order_sum1) FROM nbadv.cc_order";

/*          db2   37389936.000000	8618022167.560000	7701.000000	
            hive  37389936.000000	8618022167.560000	7701.000000	
            
            db2   -34152564.000000	-3552.420000	-15290.000000	
            hive  -3.4152564E7      -3552.42        -15290.0
   */          
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