package person.daizhongde.virtue.util.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class MTSQLRunnable implements Runnable {
	private Logger log = Logger.getLogger(MTSQLRunnable.class);
		
	private MTCellValue cv;
	private MTConnectionPool pool;

	public MTSQLRunnable(MTCellValue cv, MTConnectionPool pool ) {
		this.cv = cv;
		this.pool = pool;
	}

	@Override
	public void run() {
		Connection conn=null;
		PreparedStatement stmt =null;
		ResultSet rs =null;
		try {
			log.info("execute sql:"+this.cv.sql);
			
			conn = pool.getConn();
			stmt = conn.prepareStatement(this.cv.sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
						
			for(int i=0, j = cv.ps.length; i<j; i++ ){
				stmt.setObject(i+1, cv.ps[i]);
			}
			stmt.execute();

			while (true) {
				
				int rowCount = stmt.getUpdateCount();
				if (rowCount > 0)
					System.out.println("Rows changed = " + rowCount);
				else if (rowCount == 0) {
					System.out.println(" No rows changed or statement was DDL command");
				} else {
					rs = stmt.getResultSet();

					rs.last();
					int rows = rs.getRow();
					int cols = rs.getMetaData().getColumnCount();
					
					int row = 0;
					
					ResultSetMetaData metaData = rs.getMetaData();
					while (rs.next()) {					
						for (int col = 0; col < cols; col++) {
//							cv.values[row][col] = rs.getObject(col + 1);
//							cv.columnTypes[row][col] = metaData.getColumnType(col+1);
//							cv.scales[row][col] = metaData.getScale(col+1);
						}
						row++;
					}
				}
				if ((stmt.getMoreResults() == false)
						&& (stmt.getUpdateCount() == -1))
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage()+" sql:"+this.cv.sql);
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pool.freeConn(conn);
		}
		synchronized (this) {
			this.notify();
		}
	}
}