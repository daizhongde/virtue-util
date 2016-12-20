package person.daizhongde.virtue.util.jdbc;

import java.sql.*;

public class OracleConnection {
	/* 初始化变量 */
	String user = "user"; // 用户名
	String pass = "pass"; // 口令
	String url = "jdbc:oracle:thin:@10.193.65.12:1521:ora10g"; // 数据源
	String driver = "oracle.jdbc.driver.OracleDriver"; // 数据库驱动程序

	// 取得数据库连接
	public Connection getConnection() {
		try {
			Object obj = Class.forName(driver).newInstance();
			DriverManager.registerDriver((Driver) obj);

			Connection conn = DriverManager.getConnection(url, user, pass);
			return conn;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	// 关闭数据库连接
	public void closeConnection(Connection conn) {
		try {
			if (conn != null && (!conn.isClosed()))
				conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String args[]) {
		String tableName = "t_online_trans"; // 数据库中表名
		String sqlstr; // sql语句
		Statement stmt = null; // 语句对象
		ResultSet rs = null; // 结果集对象
		OracleConnection dbcb = new OracleConnection();
		try {
			stmt = dbcb.getConnection().createStatement();

			/* 书写SQL语句实现功能一 */
			// sqlstr="insert into "+tableName+" values('000099990','zhhdhh',45)";
			// stmt.executeUpdate(sqlstr);

			/* 书写SQL语句实现功能二 */
			sqlstr = "select * from " + tableName;

			rs = stmt.executeQuery(sqlstr);

			/* 进行数据处理 */
			ResultSetMetaData rsmd;
			rsmd = rs.getMetaData();
			// 获取元数据
			int j = 0;
			j = rsmd.getColumnCount(); // 获得结果集的行数
			for (int k = 0; k < j; k++) {
				System.out.print(rsmd.getColumnName(k + 1)); // 显示表中字段属性
				System.out.print("\t");
			}
			System.out.print("\n");
			while (rs.next()) // 显示结果集的内容
			{
				for (int i = 0; i < j; i++) {
					System.out.print(rs.getString(i + 1));
					System.out.print("\t");
				}
				System.out.print("\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
