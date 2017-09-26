package person.daizhongde.virtue.util.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MTConnectionPool {
	private Logger log = LogManager.getLogger(MTConnectionPool.class);
	
	private String url;
	private String driver;
	private List<Connection> freeList = Collections
			.synchronizedList(new ArrayList());
	private List<Connection> useList = Collections
			.synchronizedList(new ArrayList());

	private final Semaphore semp;

	public MTConnectionPool(String url, String driver, int conNumber)
			throws Exception {
		this.url = url;
		this.driver = driver;

		Class.forName(driver);
		for (int i = 0; i < conNumber; ++i) {
			Connection conn=DriverManager.getConnection(this.url);
			conn.setAutoCommit(false);
			freeList.add(conn);
		}

		semp = new Semaphore(conNumber);

	}

	public synchronized Connection getConn() throws Exception {
		// System.out.println(Thread.currentThread().getId()+":enter getConn:"+freeList.size());
		semp.acquire();
		Connection conn = freeList.get(0);
		freeList.remove(conn);
		useList.add(conn);

		return conn;
	}

	public synchronized void freeConn(Connection conn) {

		useList.remove(conn);
		freeList.add(conn);
		semp.release();
		// System.out.println(Thread.currentThread().getId()+":exit freeConn");

	}

	public void closePool() {
		for (int i = 0; i < freeList.size(); ++i) {
			try{
				freeList.get(i).commit();
				freeList.get(i).close();
			}catch(Exception e){
				log.error("关闭连接时出错<可能人为kill掉了mysql进程...>，error:"+e.getLocalizedMessage());
			}
		}
	}
}