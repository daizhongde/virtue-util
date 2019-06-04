package person.daizhongde.virtue.util.net.http;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import person.daizhongde.virtue.util.json.JsonUtils;
import net.sf.json.JSONObject;

/**
 * 把从网络获取的亚信员工信息写入数据库
 * */
public class WriteRespons2DB {
	static String url = "jdbc:mysql://localhost/tool?charset=utf-8&user=root&password=123";
	static Connection con;
	public static Connection getMySQLJDBCConnection(String url,
			String username, String password) throws Exception {
		String driver = "com.mysql.jdbc.Driver";
		Class.forName(driver);
		return DriverManager.getConnection(url);
	}

	static int length = 194;//182  只有pageIndex=0&pageSize=10&key_mobile=%25
	static HttpRequester request = new HttpRequester();
	static Map params = new HashMap();
	static Map propertys = new HashMap();
	static {
		params.put("key_sbu", "");
		params.put("key_company", "");
		params.put("key_org_name", "");
		params.put("key_last_name", "");
		params.put("key_working_location", "");
		params.put("key_mobile", "%25");
		params.put("key_nt_account", "");
		params.put("key_employee_number", "");
		params.put("key_office", "");
		params.put("pageIndex", "0");
		params.put("pageSize", "10");// 10--length
		params.put("sortField", "person_id");
		params.put("sortOrder", "asc");

//		键	值
//		请求	POST /user/employeeSeachList HTTP/1.1
//		x-requested-with	XMLHttpRequest
//		Accept-Language	zh-cn
//		Referer	https://oa.mysiainfo.com/user/employeeSeach
//		Accept	text/plain, */*; q=0.01
//		Content-Type	application/x-www-form-urlencoded
//		Accept-Encoding	gzip, deflate
//		User-Agent	Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; InfoPath.3; GWX:RESERVED)
//		Host	oa.mysiainfo.com
//		Content-Length	180
//		Connection	Keep-Alive
//		Cache-Control	no-cache
//		Cookie	JSESSIONID=5LVjSKq1YKNMS2oxOx5Pno8EOZ5d4vfswVobbINoN8vPg2EdltAL!931069562
		
		propertys.put("请求", "POST /user/employeeSeachList HTTP/1.1");
		propertys.put("x-requested-with", "XMLHttpRequest");
		propertys.put("Accept-Language", "zh-cn");
		propertys.put("Referer", "https://oa.mysiainfo.com/user/employeeSeach");
		propertys.put("Accept", "text/plain, */*; q=0.01");
		propertys.put("Content-Type", "application/x-www-form-urlencoded");
		propertys.put("Accept-Encoding", "gzip, deflate");
		propertys
				.put("User-Agent",
						"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; InfoPath.3; GWX:RESERVED)");
		propertys.put("Host", "oa.mysiainfo.com");
		propertys.put("Content-Length", new Integer(length).toString());
		propertys.put("Connection", "Keep-Alive");
		propertys.put("Cache-Control", "no-cache");
		propertys
				.put("Cookie",
						"JSESSIONID=5LVjSKq1YKNMS2oxOx5Pno8EOZ5d4vfswVobbINoN8vPg2EdltAL!931069562");

	}

	public static void main(String[] args) {
		try {
			
			
			params.put("pageSize", "10");
			propertys.put("Content-Length", new Integer(length).toString() );

			HttpRespons hr = request.sendPost(
					"https://oa.mysiainfo.com/user/employeeSeachList", params,
					propertys);
			String str_res = hr.getContent();
			JSONObject json = JSONObject.fromObject(str_res);

			int total = json.getInt("total");
			int pageSize = 1000;
			int pageCount = (total + pageSize - 1) / pageSize;

			WriteRespons2DB w2db = new WriteRespons2DB();
			params.put("pageSize", new Integer(pageSize).toString() );
			propertys.put("Content-Length", new Integer(length + String.valueOf(
							pageSize).length() - 2).toString());
			System.out.println(length+" + String.valueOf(pageSize).length()-2="
					+ (length + String.valueOf(pageSize).length() - 2));

			con = getMySQLJDBCConnection(url, "", "");
			con.setAutoCommit(false);
//			for (int i = 0; i < pageCount; i++) {
//				
//				w2db.write100row2db( i, pageSize);
//				
//			}
			w2db.write100row2db( pageCount-1, pageSize);
			con.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println("数据库回滚失败！");
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			try {
				con.setAutoCommit(true);
				con.close();
			} catch (SQLException e) {
				System.out.println("关闭连接时出错！");
			}
		}
	}

	public void write100row2db( int pageIndex, int pageSize) {
		try {
			params.put("pageIndex", new Integer(pageIndex).toString());
			System.out.println("开始获取第"+pageIndex+"页数据...");
			HttpRespons hr = request.sendPost(
					"https://oa.mysiainfo.com/user/employeeSeachList", params,
					propertys);

			String str_res = hr.getContent();
			System.out.println("获取第"+pageIndex+"页数据成功！");
			
			JSONObject json = JSONObject.fromObject(str_res);

			@SuppressWarnings("unchecked")
			List<TAuthorityEmployee> list = (List<TAuthorityEmployee>) JsonUtils
					.jsonStr2List(json.getJSONArray("data").toString(),
							TAuthorityEmployee.class);

			String sql = "insert into `t_authority_employee` ("
					+ "`sbu_id`, `sbu`, `company_id`, `company`,`organization_id`,"
					+ " `org_name`, `office`, `pager`, `person_id`, `employee_number`,"
					+ " `first_name`, `last_name`, `full_name`, `email_address`,`age`,"
					+ " `assignment_id`, `birth_date`, `class`, `working_location`, `seat_no`,"
					+ " `mobile`,`nt_account`, `supervisor_id`, `supervisor_name`, `highest_degree`,"
					+ " `hire_date`) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			PreparedStatement stmt = null;
			stmt = con.prepareStatement(sql);
			
			System.out.println("开始插入第"+pageIndex+"页数据...");
//			1963-01-01T00:00:00
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
			
			for(int i=0; i<list.size(); i++){
				TAuthorityEmployee e = list.get(i);
				
				stmt.setString(1, e.getSbu_id() );
				stmt.setString(2, e.getSbu());
				stmt.setString(3, e.getCompany_id());
				stmt.setString(4, e.getCompany());
				stmt.setInt(5,e.getOrganization_id());
				
				stmt.setString(6,e.getOrg_name());
				if( e.getOffice()==null ) stmt.setNull(7, Types.VARCHAR) ;else stmt.setString(7,e.getOffice()  );
				if( e.getPager()==null ) stmt.setNull(8, Types.VARCHAR) ;else stmt.setString(8,e.getPager()  );
				stmt.setInt(9,e.getPerson_id());
				stmt.setString(10,e.getEmployee_number());
				
				stmt.setString(11,e.getFirst_name());
				stmt.setString(12,e.getLast_name());
				stmt.setString(13,e.getFull_name());
				stmt.setString(14,e.getEmail_address());
				if( e.getAge()==null ) stmt.setNull(15, Types.INTEGER) ;else stmt.setInt(15,e.getAge()  );
				
				if( e.getAssignment_id()==null ) stmt.setNull(16, Types.INTEGER) ;else stmt.setInt(16,e.getAssignment_id()  );
				if( e.getBirth_date()==null ) 
					stmt.setNull(17, Types.DATE) ;
				else{
					String birth_date = e.getBirth_date();
					birth_date = birth_date.replace("T", " ");
					stmt.setDate(17, new java.sql.Date( dateformat.parse( birth_date ).getTime() ) );
				}
				stmt.setString(18,e.getClass_());
				stmt.setString(19,e.getWorking_location());
				if( e.getSeat_no()==null ) stmt.setNull(20, Types.VARCHAR) ;else stmt.setString(20,e.getSeat_no()  );
				
				stmt.setString(21,e.getMobile());
				stmt.setString(22,e.getNt_account());
				if( e.getSupervisor_id()==null ) stmt.setNull(23, Types.INTEGER) ;else stmt.setInt(23,e.getSupervisor_id()  );
				if( e.getSupervisor_name()==null ) stmt.setNull(24, Types.VARCHAR) ;else stmt.setString(24,e.getSupervisor_name()  );
				if( e.getHighest_degree()==null ) stmt.setNull(25, Types.VARCHAR) ;else stmt.setString(25,e.getHighest_degree()  );
				
				if( e.getHire_date()==null ) 
					stmt.setNull(26, Types.DATE) ;
				else{
					String hire_date = e.getHire_date();
					hire_date = hire_date.replace("T", " ");
					stmt.setDate(26, new java.sql.Date( dateformat.parse( hire_date ).getTime() ) );
				}
				
				stmt.addBatch();
			}

			stmt.executeBatch();
			stmt.close();
//			con.commit();
			System.out.println("插入第"+pageIndex+"页数据成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
