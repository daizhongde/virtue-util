package person.daizhongde.virtue.util.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import person.daizhongde.virtue.util.db.JDBC;
import person.daizhongde.virtue.util.jdbc.MTCellValue;
import person.daizhongde.virtue.util.jdbc.MTConnectionPool;
import person.daizhongde.virtue.util.jdbc.MTSQLRunnable;

public class DaoImpl {

	private static Logger log = Logger.getLogger(DaoImpl.class);
	private static final String MySQL_driver = "com.mysql.jdbc.Driver";
	private static final String Oracle_driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:mysql://localhost:3306/tool?user=root&password=123";
	
	
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
	// 插入记录
	protected boolean insert(String sql) throws SQLException {
		return longHaul(sql);
	}

	// 修改记录
	protected boolean update(String sql) throws SQLException{
		return longHaul(sql);
	}
	// 删除记录
	protected boolean delete(String sql) throws SQLException {
		return longHaul(sql);
	}
	
	/** 把sql集合准备好后再调用  */
	public void getSqlValue(ArrayList<MTCellValue>  cellList,
			int maxThreadNum ) throws Exception {
		
		System.out.println("begin execute sql...");

		ExecutorService pool = Executors.newFixedThreadPool( maxThreadNum );
		List<Future> list = new ArrayList<Future>();
		
		MTConnectionPool pl = new MTConnectionPool( url, 
				MySQL_driver, maxThreadNum );
		
		
		for (int i = 0; i < cellList.size(); i++) {
			MTSQLRunnable c = new MTSQLRunnable( cellList.get(i), pl);
			// 执行任务并获取Future对象
			Future f = pool.submit(c);
			// System.out.println(">>>" + f.get().toString());
			list.add(f);				
		}
		// 关闭线程池

		for (Future f : list) {
			f.get();
		}
		
		pl.closePool();
		pool.shutdown();
		System.out.println("execute sql finish!");
	}
	
}
