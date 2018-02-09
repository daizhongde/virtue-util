package person.daizhongde.virtue.util.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import person.daizhongde.virtue.constant.INIT;
import person.daizhongde.virtue.util.ie.POICellUtil;

/**
 * support multi-thread
 * @author daizd
 *
 */

class MyCellValue {
	String sheet;
	int row;
	int col;
	String sql;
	boolean isSql;
	Object[][] values;
	String urlName;
	int[][] columnTypes, scales;
}

public class Excel2Excel {
	
	
	private static final Logger log = LoggerFactory.getLogger(Excel2Excel.class);
	
//	private POICellStyle cs;
	
	private static String DefaultURL_Name="";
	
	/** 假定最多同时查询两个库，且两个库不为同一类型数据库,还需要在URL名称中指定数据库类型 **/
	private static final String MySQL_URL_Name_prefix="MySQL";
	private static final String Oracle_URL_Name_prefix="Oracle";
	
	private static final String MySQL_driver = "com.mysql.jdbc.Driver";
	private static final String Oracle_driver = "oracle.jdbc.driver.OracleDriver";
	
	/** defineDBURL MySQL_retail="jdbc:mysql://10.1.249.100:3306?user=migdb&password=migdb"
	 * <br>defineDBURL Oracle_legacy="jdbc:oracle:thin:mig123/mig123@10.226.4.235:1521:ora11gR2" 
	 * <p>use:
	 * <br> #Query MySQL_retail select count(1) from ......
	 * <br> #Query Oracle_legacy select count(1) from ......
	 *   ***/
	
	private static final String DB_URL_Define_prefix = "defineDBURL";
	/**
	 * 是否指定了默认数据库 
	 * <p>true 代表已经指定默认数据库， 默认最先定义的dburl为默认数据库  **/
	private boolean haveSetDefault = false;
	
	/** #Query MySQL  **/
	private static final String MySQL_targetDB_of_SQLprefix="#Query MySQL";
	/** #Query Oracle 
	 *  --is faluma in excel,so not use #
	 *  **/
	private static final String Oracle_targetDB_of_SQLprefix="#Query Oracle";
	
	/** urlName: url **/
	private Map<String,String> map = new HashMap<String,String>();
//	/** urlName: Connection **/
//	private Map<String,Connection> conn_map = new HashMap<String,Connection>();
//	private static Connection con_Default;

	private Map<String,ArrayList<MyCellValue>> cellList_Map = new HashMap<String,ArrayList<MyCellValue>>();
	/** 单个库同时支持的最大连接数  INIT.maxThreadNum , suggest 20, good db can be bigger **/
	private int taskNum = INIT.maxThreadNum/2;
//	private int taskNum = 2;
	
//	/** 每个库同时支持的最大连接数 **/
//	private Map<String, Semaphore> taskNum_Map = new HashMap<String, Semaphore>();
	
//	public void closeAllConnections() throws SQLException{
//		Iterator it = conn_map.keySet().iterator();
//		while( it.hasNext() ){
//			Connection conn =conn_map.get( it.next() );
//			if( null != conn ){
//				if(!conn.isClosed()){
//					conn.close();
//				}
//			}
//		}
//	}
	
//	public static Connection getMySQLJDBCConnection( String url ) throws Exception {
//		String driver = "com.mysql.jdbc.Driver";
//		Class.forName(driver);
//
//		return DriverManager.getConnection( url );
//	}
//	  
//	public static Connection getOracleJDBCConnection( String url  ) throws Exception {
//		String driver = "oracle.jdbc.driver.OracleDriver";
//		Object obj = Class.forName(driver).newInstance();
//		DriverManager.registerDriver((Driver) obj);
//		
//		return DriverManager.getConnection(url);
//	}
//	public static Connection getJDBCConnection(String url, String dbtype ) throws Exception {
//		if(dbtype.trim().toLowerCase().equalsIgnoreCase("mysql")){
//			return getMySQLJDBCConnection( url );
//		}else if(dbtype.trim().toLowerCase().equalsIgnoreCase("oracle")){
//			return getOracleJDBCConnection( url );
//		}else{
//			throw new Exception("This db is not support:<"+dbtype+">");
//		}
//	}
//
//	public static Connection getJDBCConnection(String IP, String port,
//			String db, String user, String passwd) throws Exception {
//		String url = ("jdbc:mysql://" + IP + ":" + port + "/" + db + "?user="
//				+ user + "&password=" + passwd);
//		String driver = "com.mysql.jdbc.Driver";
//		Class.forName(driver);
//		return DriverManager.getConnection(url);
//
//	}

	/** input file's path is absolute path
	 * @throws Exception */
	public void exchangeExcel( String inputFile, String outputFile, String uploadContentType ) throws Exception{
		if(!new File(inputFile).exists() ){
			throw new Exception("input file is not exist!");
		}
		try{
			this.exchangeExcel(new File(inputFile), outputFile, uploadContentType);
		}catch(Exception e){
			throw e;
		}finally{
//			con_Default.close();
//			closeAllConnections();
		}
	}
	public void exchangeExcel( File inputFile, String outputFile, String uploadContentType ) throws Exception{

		  //创建工作文档对象  
        Workbook wb = null;
        
        if (uploadContentType.equals("application/vnd.ms-excel")) {  
           wb = new HSSFWorkbook(new FileInputStream(inputFile));  
	    }  
	    else if(uploadContentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))  
	    {  
	       wb = new XSSFWorkbook(new FileInputStream(inputFile));  
	    }  
        else  
        {  
            System.out.println("The file with the wrong format！");  
        }
        
//        cs = new POICellStyle(wb);
        
        for(int sn=0; sn<wb.getNumberOfSheets(); sn++) {
//        	HSSFSheet t = new HSSFSheet();
        	Sheet sheet = wb.getSheetAt(sn);
            System.out.println("Sheet #" + sn + " : " + sheet.getSheetName());
            
            for(Row row : sheet) {
//               System.out.println(sheet.getSheetName() + "  Row " + row.getRowNum());
               
               for(Cell cell : row) {
            	  if(cell.getCellType() != HSSFCell.CELL_TYPE_STRING){
            		  continue;
            	  }
                  String value = cell.getStringCellValue();
                  value = value.trim().replaceAll("  ", " ");
                  
                  if(this.isSQL(value)){
                	  String db_urlName = this.getTargetDBURL_Name(value);
                	  String pureSQL = this.getPureSQL(value);
                	  
                	  try {
//                		  Date beginTime = new Date();
                		  if(db_urlName.trim().toLowerCase().startsWith("mysql"))
                		  {
//                			  d = this.exeuteQuery( pureSQL, conn_map.get( db_urlName ) );
//                			  cell.setCellValue(d.doubleValue());
                			  
                			  MyCellValue cv = new MyCellValue();
                			  cv.sheet = sheet.getSheetName();
                			  cv.row = cell.getRowIndex();
                			  cv.col = cell.getColumnIndex();
                			  cv.sql = pureSQL;
                			  cv.isSql = true;

                			  ArrayList<MyCellValue> cellList = cellList_Map.containsKey(db_urlName)?
                					  cellList_Map.get(db_urlName) : new ArrayList<MyCellValue>();
                			  cellList.add(cv);
                			  
                			  cellList_Map.put(db_urlName, cellList);
                		  }
                		  else if(db_urlName.trim().toLowerCase().startsWith("oracle"))
                		  {
//                			  d = this.exeuteQuery(pureSQL, conn_map.get( db_urlName ));
//                			  cell.setCellValue(d.doubleValue());

                			  MyCellValue cv = new MyCellValue();
                			  cv.sheet = sheet.getSheetName();
                			  cv.row = cell.getRowIndex();
                			  cv.col = cell.getColumnIndex();
                			  cv.sql = pureSQL;
                			  cv.isSql = true;

                			  ArrayList<MyCellValue> cellList = cellList_Map.containsKey(db_urlName)?
                					  cellList_Map.get(db_urlName) : new ArrayList<MyCellValue>();
                			  cellList.add(cv);
                			  
                			  cellList_Map.put(db_urlName, cellList);
                		  }
                		  else
                		  {
                			  throw new Exception("This url Name is not legal:<"+db_urlName+">");
                		  }
//                		  Date endTime = new Date();
//                		  long diff = endTime.getTime() - beginTime.getTime();//这样得到的差值是微秒级别
//                		  long days = diff / (1000 * 60 * 60 * 24);
                		 
//                		  long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
//                		  long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
//                		  long seconds = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60) - minutes*(1000* 60))/1000;
//                		  System.out.println(""+days+"天"+hours+"小时"+minutes+"分");
//                		  System.out.println(sn+" "+row.getRowNum()+" "+cell.getColumnIndex()+" use time: "+days+" day "+hours+" hour "+minutes+" minute "+seconds+" seconds");
					  } catch (Exception  e) {
						e.printStackTrace();
						System.out.println("########## below sql have problem ########## ");
						System.out.println(pureSQL);
						System.out.println("###--above sql position："+sheet.getSheetName() + "  Row " + row.getRowNum()+" (" + cell.getColumnIndex() + ") ");
//						throw new Exception("There SQL have problem："+sheet.getSheetName() + "  Row " + row.getRowNum()+" (" + cell.getColumnIndex() + ") ");
					  }
                  }else if(this.isDBDefine(value)){
                	  String[] arr = value.substring(12).trim().split("=\"| ");
                	  if( arr.length<2 || arr.length>3 ){
                		  throw new Exception("DB Connection URL define is not legal:"+value);
                	  }
                	  arr[0]=arr[0].trim().toLowerCase();
                	  String url = arr[1].replaceAll("\"$", "");
                	  map.put(arr[0], url );
                	  if(arr[0].startsWith("mysql")){
//                		  Connection con_MySQL = getJDBCConnection(url, "MySQL");
//                		  conn_map.put(arr[0], con_MySQL);
                		  //conn_map.get( db_urlName )
                	  }else if( arr[0].startsWith("oracle") ){
                		  if( arr.length!=2 ){
                    		  throw new Exception("DB Connection URL define is not legal:"+value);
                    	  }
//                		  String[] acct = arr[2].split("\\/");
//                		  if( acct.length!=2 ){
//                    		  throw new Exception("DB Connection URL define is not legal:"+value);
//                    	  }
//                		  Connection con_Oracle = getJDBCConnection(url, acct[0], acct[1], "Oracle" );
//                		  Connection con_Oracle = getJDBCConnection(url, "Oracle" );
//                		  conn_map.put(arr[0], con_Oracle);
                	  }else{
                		  throw new Exception("DB Connection URL define is not legal:"+value);
                	  }
                	  if(!haveSetDefault){
                		  DefaultURL_Name = arr[0];
//                		  con_Default = conn_map.get(DefaultURL_Name);
                		  haveSetDefault = true;
                	  }
                  }
                  
//                  System.out.print("    " + ref.formatAsString());
//                  System.out.print(" (" + cell.getColumnIndex() + ") ");
                  
//                  CellStyle style = cell.getCellStyle();
//                  System.out.print("Format=" + style.getDataFormatString() + " ");
                  
//                  Font font = wb.getFontAt( style.getFontIndex() );
//                  System.out.print("Font=" + font.getFontName() + " ");
//                  System.out.print("FontColor=");
               }
            }
         }// end of for
        
        getSqlValue();
                
//        writeExcel();
        Set<String> keys = cellList_Map.keySet();
		Iterator<String> it2 = keys.iterator();
		while(it2.hasNext()){
			String urlName = it2.next();
			
			ArrayList<MyCellValue> cellList = cellList_Map.get(urlName);
			
			for (int i = cellList.size() - 1; i >= 0; i--) {
				if (cellList.get(i).isSql == false)
					cellList.remove(i);
			}
			
			for (MyCellValue cv : cellList) {
				Sheet ws = wb.getSheet(cv.sheet);
				
				for (int row = cv.row; row < cv.row + cv.values.length; row++)
					for (int col = cv.col; col < cv.col + cv.values[0].length; col++) {
						
//						cs.initializeRequiredStyle(
//								cv.columnTypes[row - cv.row][col - cv.col], 
//								cv.scales[row - cv.row][col - cv.col]);
						
						this.setContent(ws, row, col, cv.values[row - cv.row][col - cv.col], 
								cv.columnTypes[row - cv.row][col - cv.col],
								cv.scales[row - cv.row][col - cv.col]);
					}
			}
		}
     // end deleted sheet
 		FileOutputStream out = new FileOutputStream(outputFile);
 		wb.write(out);
 		out.close();
 		wb.close();
 		
	}
	
//	public void setContent(Sheet sht, int row, int col, BigDecimal value) {
////		System.out.println("value:"+value+",row:"+row+",col:"+col);
//		Cell cell = sht.getRow(row).getCell(col);
//		cell.setCellValue( null==value? -1 : value.doubleValue() );
//	}
	public void setContent(Sheet sht, int row, int col, Object value, int columnType, int scale ) {
		Row row0 = sht.getRow(row);
		if(null == row0){
			row0 = sht.createRow(row);
		}
		Cell cell = row0.getCell(col);
		if(null == cell){
			cell = row0.createCell(col);
		}
		POICellUtil.setCellValueT(value, cell, columnType );
//		cell.setCellValue( String.valueOf( value ) );
	}
	@SuppressWarnings("unchecked")
	public void getSqlValue() throws Exception {
		
		System.out.println("begin execute sql...");
		
		Set<String> keys = cellList_Map.keySet();
		Iterator<String> it = keys.iterator();
		
		while(it.hasNext()){
			String urlName = it.next();
			
			ExecutorService pool = Executors.newFixedThreadPool( taskNum );
			List<Future> list = new ArrayList<Future>();
			
			MyConnectionPool pl = new MyConnectionPool(map.get(urlName), 
					getDBdriver(urlName), taskNum );
			
			ArrayList<MyCellValue> cellList = cellList_Map.get(urlName);
			for (int i = 0; i < cellList.size(); i++) {
				SQLRunnable c = new SQLRunnable(cellList.get(i), pl );
				// 执行任务并获取Future对象
				Future f = pool.submit(c);
				// System.out.println(">>>" + f.get().toString());
				list.add(f);				
			}
			// 关闭线程池

			for (Future f : list) {
				f.get();
			}
			cellList_Map.put(urlName, cellList);
			
			pl.closePool();
			pool.shutdown();
			
		}

		System.out.println("execute sql finish!");
	}

	/**
	 * 
	 * 判断是否是统计sql
	 * <p>
	 * the value has been value.trim().replaceAll("  ", " ")
	 * @param value
	 * @return
	 */
	public boolean isCountorSumSQL(String value){
		value = value.trim().replaceAll("  ", " ");
		if(StringUtils.isEmpty(value)){
			return false;
		}else if(value.toLowerCase().startsWith("select count(") || value.toLowerCase().startsWith("select sum(")){
			return true;
		}else if(value.toLowerCase().startsWith(MySQL_targetDB_of_SQLprefix.toLowerCase())){
			return true;
		}else if(value.toLowerCase().startsWith(Oracle_targetDB_of_SQLprefix.toLowerCase())){
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 判断是否是sql
	 * <p>
	 * the value has been value.trim().replaceAll("  ", " ")
	 * @param value
	 * @return
	 */
	public boolean isSQL(String value){
		value = value.trim().replaceAll("  ", " ");
		if(StringUtils.isEmpty(value)){
			return false;
		}else if(value.toLowerCase().startsWith("select ")){
			return true;
		}else if(value.toLowerCase().startsWith(MySQL_targetDB_of_SQLprefix.toLowerCase())){
			return true;
		}else if(value.toLowerCase().startsWith(Oracle_targetDB_of_SQLprefix.toLowerCase())){
			return true;
		}
		return false;
	}
	
	/** judge whether database connection url define
	 * <p>
	 * the value has been value.trim().replaceAll("  ", " ")
	 **/
	public boolean isDBDefine(String value){
		value = value.trim().replaceAll("  ", " ");
		if(StringUtils.isEmpty(value)){
			return false;
		}else if(value.toLowerCase().startsWith(DB_URL_Define_prefix.toLowerCase())){
			return true;
		}
		return false;
	}

	/** get target db's url name
		 * <p>
		 * the value has been value.trim().replaceAll("  ", " ")
		 * <br>eg:#Query Oracle_legacy select count(1) 
		 * 
		 *   **/
		public String getTargetDBURL_Name(String value){
			/* 调用时value不可能为null  */
			value = value.trim().toLowerCase().replaceAll("  ", " ");
			if( value.startsWith("select count(") || value.startsWith("select sum(") ){
				return DefaultURL_Name;
			}else if(value.startsWith(MySQL_targetDB_of_SQLprefix.toLowerCase())){
				return value.substring( 7, value.indexOf("select ") ).trim();
			}else if(value.startsWith(Oracle_targetDB_of_SQLprefix.toLowerCase())){
				return value.substring( 7, value.indexOf("select ") ).trim();
			}
			//此语句永远都不会执行
			return DefaultURL_Name;
		}

	public String getDBdriver(String urlName){
		if(urlName.trim().toLowerCase().startsWith("oracle"))
		{
			return this.Oracle_driver;
		}
		else if(urlName.trim().toLowerCase().startsWith("mysql"))
		{
			return this.MySQL_driver;
		}
		else
		{
			return "";
		}
	}

	/** 获取可运行的sql,去掉目标库定义（如果有的话）
		 * <p>
		 * the value has been value.trim().replaceAll("  ", " ")
		 *  **/
		public String getPureSQL(String value){
			/* 调用时value不可能为null  */
			value = value.trim().replaceAll("  ", " ");
			if( value.toLowerCase().startsWith("select count(") || value.toLowerCase().startsWith("select sum(") ){
				
			}else if(value.toLowerCase().startsWith(MySQL_targetDB_of_SQLprefix.toLowerCase())){
				value = value.substring( value.toLowerCase().indexOf("select ") );
			}else if(value.toLowerCase().startsWith(Oracle_targetDB_of_SQLprefix.toLowerCase())){
				value = value.substring( value.toLowerCase().indexOf("select ") );
			}
			value = value.replaceAll(";$", "");
			//此语句永远都不会执行
			return value;
		}

	public static void main(String[] args) {
//		Connection conn = null;
		try {
//			String inputFile = "F:\\asiainfo\\DR4\\connexion report\\OP Migration WS_Connexion Report DR4#.xlsx";
//			String outputFile = "F:\\asiainfo\\DR4\\connexion report\\OP Migration WS_Connexion Report DR4#_output.xlsx";
//			String inputFile = "F:\\Java项目\\migration2.0\\测试文档\\OPP Migration Report DR# - Wholesale  V0.2 - test.xlsx";
//			String outputFile ="F:\\Java项目\\migration2.0\\测试文档\\OPP Migration Report DR# - Wholesale  V0.2 - test1.xlsx";
			
			String inputFile = "G:\\KPI3-sql.xlsx";
			String outputFile ="G:\\result.xlsx";
			
//			"G:\\KPI2-sql.xls","G:\\result.xls"
			
			//"F:\asiainfo\DR4\connexion report\OP Migration WS_Connexion Report DR4#.xlsx"
//			conn = getJDBCConnection("localhost", "3306", "tool", "root",
//					"123");
//			conn.setAutoCommit(false);
			Excel2Excel e2e = new Excel2Excel();
			//text/plain,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
			e2e.exchangeExcel(inputFile, outputFile, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			
//			con_Default.commit();
//			con_Default.close();
			System.out.println("all success,no errors!");
		} catch (Exception e) {
//			try {
//				con_Default.rollback();
//				con_Default.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//				System.err.println("rollback error!");
//			}
			e.printStackTrace();
			System.out.println("error:" + e.getLocalizedMessage());
		}finally{
//			try {
//				con_Default.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
}

class SQLRunnable implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(SQLRunnable.class);
	
//	private static int i=0;
	
	private MyCellValue cv;
	private MyConnectionPool pool;

	SQLRunnable(MyCellValue cv, MyConnectionPool pool) {
		this.cv = cv;
		this.pool = pool;
	}

	@Override
	public void run() {
		Connection conn=null;
		PreparedStatement stmt =null;
		ResultSet rs =null;
		try {
			log.info("execute sql ("+cv.sheet+")["+cv.row+","+cv.col+"]:"+this.cv.sql);
			
			conn = pool.getConn();
			stmt = conn.prepareStatement(this.cv.sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
	//		System.out.println(Thread.currentThread().getId());
					
			stmt.execute();

			while (true) {
				
				int rowCount = stmt.getUpdateCount();
				if (rowCount > 0)
					System.out.println("Rows changed = " + rowCount);
				else if (rowCount == 0) {
					System.out
							.println(" No rows changed or statement was DDL command");
				} else {
					rs = stmt.getResultSet();

					rs.last();
					int rows = rs.getRow();
					int cols = rs.getMetaData().getColumnCount();
					
					rs.beforeFirst();
					cv.values = new Object[rows][cols];
					cv.columnTypes = new int[rows][cols];
					cv.scales = new int[rows][cols];
					int row = 0;
					
					ResultSetMetaData metaData = rs.getMetaData();
					while (rs.next()) {					
						for (int col = 0; col < cols; col++) {
							cv.values[row][col] = rs.getObject(col + 1);
//							cv.values[row][col] = rs.getString(col + 1);
//							System.out.println("(" + col + "," + row + "):"+ cv.values[row][col]);
							cv.columnTypes[row][col] = metaData.getColumnType(col+1);
							cv.scales[row][col] = metaData.getScale(col+1);
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
			log.error(e.getLocalizedMessage()+" sql("+cv.sheet+")["+cv.row+","+cv.col+"]:"+this.cv.sql);
			cv.isSql = false;
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
	}
}

class MyConnectionPool {
	private static final Logger log = LoggerFactory.getLogger(MyConnectionPool.class);
	
	private String url;
	private String driver;
	private List<Connection> freeList = Collections
			.synchronizedList(new ArrayList());
	private List<Connection> useList = Collections
			.synchronizedList(new ArrayList());

	private final Semaphore semp;

	public MyConnectionPool(String url, String driver, int conNumber)
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