package person.daizhongde.virtue.util.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseDao {

	/** 取得日志记录器Logger */
	private static Logger log = LogManager.getLogger( BaseDao.class.getName());
	
	// 查询多个记录
	public Vector selectSomeNote(String sql) {
		log.debug(sql);
		Vector vector = new Vector();// 创建结果集向量
		try {
			Connection conn = JDBC.getConnection();// 获得数据库连接
			Statement stmt = conn.createStatement();// 创建连接状态对象
			ResultSet rs = stmt.executeQuery(sql);// 执行SQL语句获得查询结果
			int columnCount = rs.getMetaData().getColumnCount();// 获得查询数据表的列数
			int row = 1;// 定义行序号
			while (rs.next()) {// 遍历结果集
				Vector rowV = new Vector();// 创建行向量
				rowV.add(new Integer(row++));// 添加行序号
				for (int column = 1; column <= columnCount; column++) {
					rowV.add(rs.getObject(column));// 添加列值
				}
				vector.add(rowV);// 将行向量添加到结果集向量中
			}
			rs.close();// 关闭结果集对象
			stmt.close();// 关闭连接状态对象
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vector;// 返回结果集向量
	}
	/**
	 * 查询两个列:第一列是key,第二列是value
	 * <p>
	 * String CommentSQL = "select column_name,comments " +
				"			   from ALL_COL_COMMENTS " +
				"             where owner=upper('"+owner+"') and table_name=upper('"+tableName+"')";
		
		
	 * @param sql
	 * @return
	 */
	protected Map selectMapValue(String sql) {
		log.debug(sql);
		Map map = new HashMap();// 创建查询结果集向量
		try {
			Connection conn = JDBC.getConnection();// 获得数据库连接
			Statement stmt = conn.createStatement();// 创建连接状态对象
			ResultSet rs = stmt.executeQuery(sql);// 执行SQL语句获得查询结果
			int columnCount = rs.getMetaData().getColumnCount();// 获得查询数据表的列数
			int row = 1;// 定义行序号
			while (rs.next()) {// 遍历结果集
				map.put(rs.getObject(1), rs.getObject(2));// 将行向量添加到结果集向量中
			}
			rs.close();// 关闭结果集对象
			stmt.close();// 关闭连接状态对象
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;// 返回查询结果集向量
	}
	
	// 查询单个记录--如果查询结果有多条就返回最后一条
	public Vector selectOnlyNote(String sql) throws SQLException{
		log.debug(sql);
		Vector vector = null;// 声明记录向量
		Connection conn = JDBC.getConnection();// 获得数据库连接
//		try {
			Statement stmt = conn.createStatement();// 创建连接状态对象
			ResultSet rs = stmt.executeQuery(sql);// 执行SQL语句获得查询结果
			
//			log.info("Statement MaxRows:"+stmt.getMaxRows());  //0没有限制
			int columnCount = rs.getMetaData().getColumnCount();// 获得表格列数
			while (rs.next()) {
				vector = new Vector();// 创建记录向量
				for (int column = 1; column <= columnCount; column++) {
					vector.add(rs.getObject(column));// 封装记录向量
				}
			}
			rs.close();// 关闭结果集对象
			stmt.close();// 关闭连接状态对象
//		} catch (SQLSQLException e) {
//			e.printStackTrace();
//		}
		return vector;// 返回记录向量
	}
	//查询多个值(某列的值)--如果结果有多行，则vector包括第一列的所有值.length = 行数
	public  Vector selectSomeValue(String sql) {
		log.debug(sql);
		Vector vector = new Vector();// 创建查询结果集向量
		try {
			Connection conn = JDBC.getConnection();// 获得数据库连接
			Statement stmt = conn.createStatement();// 创建连接状态对象
			ResultSet rs = stmt.executeQuery(sql);// 执行SQL语句获得查询结果
			while (rs.next()) {
				vector.add(rs.getObject(1));// 封装查询结果集向量
			}
			rs.close();// 关闭结果集对象
			stmt.close();// 关闭连接状态对象
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vector;// 返回查询结果集向量
	}

	// 查询单个值
	public Object selectOnlyValue(String sql) throws SQLException{
		log.debug(sql);
		Object value = null;// 声明查询结果对象
		Connection conn = JDBC.getConnection();// 获得数据库连接
//		try {
			Statement stmt = conn.createStatement();// 创建连接状态对象
			ResultSet rs = stmt.executeQuery(sql);// 执行SQL语句获得查询结果
			while (rs.next()) {
				value = rs.getObject(1);// 获得查询结果
			}
			rs.close();// 关闭结果集对象
			stmt.close();// 关闭连接状态对象
//		} catch (SQLSQLException e) {
//			e.printStackTrace();
//		}
		return value;// 返回查询结果 
	}
	/**
	 * 查询sql语句的所有列名
	 * author daizhongde
	 * @param sql
	 * @return
	 */
	protected Vector selectColumnLabels(String sql) {
		log.debug(sql);
		Vector vector = null;// 声明记录向量
		try {
			Connection conn = JDBC.getConnection();// 获得数据库连接
			Statement stmt = conn.createStatement();// 创建连接状态对象
			ResultSet rs = stmt.executeQuery(sql);// 执行SQL语句获得查询结果
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();// 获得表格列数
			
			vector = new Vector();// 创建记录向量
			for (int column = 1; column <= columnCount; column++) {
//				vector.add(rsmd.getColumnLabel(column)+"|"+rsmd.getColumnName(column));// 封装记录向量
				vector.add(rsmd.getColumnLabel(column));// 封装记录向量
			}
			rs.close();// 关闭结果集对象
			stmt.close();// 关闭连接状态对象
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vector;// 返回记录向量
	}
	
	/**
	 * 查询sql语句的所有列名
	 * author daizhongde
	 * @param sql
	 * @return
	 */
	protected Vector selectColumnNames(String sql) {
		log.debug(sql);
		Vector vector = null;// 声明记录向量
		try {
			Connection conn = JDBC.getConnection();// 获得数据库连接
			Statement stmt = conn.createStatement();// 创建连接状态对象
			ResultSet rs = stmt.executeQuery(sql);// 执行SQL语句获得查询结果
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();// 获得表格列数
			
			vector = new Vector();// 创建记录向量
			for (int column = 1; column <= columnCount; column++) {
//				vector.add(rsmd.getColumnLabel(column)+"|"+rsmd.getColumnName(column));// 封装记录向量
				vector.add(rsmd.getColumnName(column));// 封装记录向量
			}
			rs.close();// 关闭结果集对象
			stmt.close();// 关闭连接状态对象
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vector;// 返回记录向量
	}

	/**
	 * 查询sql语句的所有列的SQL数据类型代码
	 * @param sql
	 * @return
	 */
	protected Vector selectColumnTypes(String sql) {
		log.debug(sql);
		Vector vector = null;// 声明记录向量
		try {
			Connection conn = JDBC.getConnection();// 获得数据库连接
			Statement stmt = conn.createStatement();// 创建连接状态对象
			ResultSet rs = stmt.executeQuery(sql);// 执行SQL语句获得查询结果
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();// 获得表格列数
			
			vector = new Vector();// 创建记录向量
			for (int column = 1; column <= columnCount; column++) {
//				vector.add(rsmd.getColumnType(column)+"-"+rsmd.getColumnTypeName(column)+"|");// 封装记录向量
				vector.add( new Integer(rsmd.getColumnType(column)) );// 封装记录向量
			}
			rs.close();// 关闭结果集对象
			stmt.close();// 关闭连接状态对象
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vector;// 返回记录向量
	}

	/**
	 * 查询sql语句的所有列的SQL类型名称
	 * @param sql
	 * @return
	 */
	protected Vector selectColumnTypeNames(String sql) {
		log.debug(sql);
		Vector vector = null;// 声明记录向量
		try {
			Connection conn = JDBC.getConnection();// 获得数据库连接
			Statement stmt = conn.createStatement();// 创建连接状态对象
			ResultSet rs = stmt.executeQuery(sql);// 执行SQL语句获得查询结果
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();// 获得表格列数
			
			vector = new Vector();// 创建记录向量
			for (int column = 1; column <= columnCount; column++) {
//				vector.add(rsmd.getColumnType(column)+"-"+rsmd.getColumnTypeName(column)+"|");// 封装记录向量
				vector.add(rsmd.getColumnTypeName(column));// 封装记录向量
			}
			rs.close();// 关闭结果集对象
			stmt.close();// 关闭连接状态对象
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vector;// 返回记录向量
	}

	/**
	 * 查询sql语句的所有列别名、名称、SQL数据类型代码、SQL数据类型名称
	 * @param sql
	 * @return Vector[]
	 */
	protected Vector[] selectColumnNamesAndTypes(String sql) {
		log.debug(sql);
		Vector[] vector =  new Vector[6];// 声明记录向量
		try {
			Connection conn = JDBC.getConnection();// 获得数据库连接
			Statement stmt = conn.createStatement();// 创建连接状态对象
			ResultSet rs = stmt.executeQuery(sql);// 执行SQL语句获得查询结果
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();// 获得表格列数
			
			Vector vector_0 = new Vector();// 列别名
			for (int column = 1; column <= columnCount; column++) {
				vector_0.add(rsmd.getColumnLabel(column));// 封装记录向量
			}
			vector[0] = vector_0;
			
			Vector vector_1 = new Vector();// 名称
			for (int column = 1; column <= columnCount; column++) {
				vector_1.add(rsmd.getColumnName(column));// 封装记录向量
			}
			vector[1] = vector_1;
			
			Vector vector_2 = new Vector();// SQL数据类型代码
			for (int column = 1; column <= columnCount; column++) {
				vector_2.add( new Integer(rsmd.getColumnType(column)) );// 封装记录向量
			}
			vector[2] = vector_2;
			
			Vector vector_3 = new Vector();// SQL数据类型名称
			for (int column = 1; column <= columnCount; column++) {
				vector_3.add(rsmd.getColumnTypeName(column));// 封装记录向量
			}
			vector[3] = vector_3;
			
			Vector vector_4 = new Vector();// 有效位数
			for (int column = 1; column <= columnCount; column++) {
				vector_4.add( new Integer(rsmd.getPrecision(column)) );// 封装记录向量
			}
			vector[4] = vector_4;
			
			Vector vector_5 = new Vector();// 小数点后数据位数
			for (int column = 1; column <= columnCount; column++) {
				vector_5.add( new Integer(rsmd.getScale(column)) );// 封装记录向量
			}
			vector[5] = vector_5;
			
//			VectorToStringA.print(vector);
			rs.close();// 关闭结果集对象
			stmt.close();// 关闭连接状态对象
			JDBC.closeConnection();//add by junit test
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vector;// 返回记录向量
	}
	
	// 插入、修改、删除记录
	protected boolean longHaul(String sql) throws SQLException{
		log.debug(sql);
		boolean isLongHaul = true;// 默认持久化成功
		Connection conn = JDBC.getConnection();// 获得数据库连接
		SQLException e3 = null;
		try {
			conn.setAutoCommit(false);// 设置为手动提交
			Statement stmt = conn.createStatement();// 创建连接状态对象
			stmt.executeUpdate(sql);// 执行SQL语句
			stmt.close();// 关闭连接状态对象
			conn.commit();// 提交持久化
		} catch (SQLException e) {
			isLongHaul = false;// 持久化失败
			try {
				conn.rollback();// 回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			e3 = e;
		}
		if(e3 != null){
			throw e3;
		}
		return isLongHaul;// 返回持久化结果
	}
	// 插入、修改、删除记录
	protected boolean longHaul(String sql, Object[] arr ) throws SQLException{
		log.debug(sql);
		boolean isLongHaul = true;// 默认持久化成功
		Connection conn = JDBC.getConnection();// 获得数据库连接
		SQLException e3 = null;
		try {
			conn.setAutoCommit(false);// 设置为手动提交
			PreparedStatement stmt = conn.prepareStatement(sql);// 创建连接状态对象
			for(int i=0,j=arr.length; i<j; i++){
				if(null == arr[i]){
					stmt.setNull(i+1,Types.INTEGER);
				}
				stmt.setObject(i+1, arr[i]);
			}
			stmt.executeUpdate();// 执行SQL语句
			stmt.close();// 关闭连接状态对象
			conn.commit();// 提交持久化
		} catch (SQLException e) {
			isLongHaul = false;// 持久化失败
			try {
				conn.rollback();// 回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			e3 = e;
		}
		if(e3 != null){
			throw e3;
		}
		return isLongHaul;// 返回持久化结果
	}
	// 插入记录
	public boolean insert(String sql) throws SQLException {
		return longHaul(sql);
	}
	// 插入记录
	public boolean insert(String sql, Object[] arr ) throws SQLException {
		return longHaul(sql, arr);
	}
	
	// 修改记录
	public boolean update(String sql) throws SQLException{
		return longHaul(sql);
	}
	// 删除记录
	public boolean delete(String sql) throws SQLException {
		return longHaul(sql);
	}

}
