package person.daizhongde.virtue.util.http;

import java.util.HashMap;
import java.util.Map;
/**
 * 把从网络获取的亚信员工信息测试
 * */
public class ClientTest {
	public static void main(String[] args) {
		try {
			HttpRequester request = new HttpRequester();
//			HttpRespons hr = request.sendPost("http://www.jb51.net");
//			key_sbu=&key_company=
//			&key_org_name=&key_last_name=
//			&key_working_location=&key_mobile=%25
//			&key_nt_account=&key_employee_number=
//			&key_office=&pageIndex=0
//			&pageSize=10&sortField=
//			&sortOrder=
			Map params = new HashMap();
			params.put("key_sbu", "");params.put("key_company", "");
			params.put("key_org_name", "");params.put("key_last_name", "");
			params.put("key_working_location", "");params.put("key_mobile", "%25");
			params.put("key_nt_account", "");params.put("key_employee_number", "");
			params.put("key_office", "");params.put("pageIndex", "0");
			params.put("pageSize", "10");params.put("sortField", "");
			params.put("sortOrder", "");
			
			Map propertys = new HashMap();
//			请求	POST /user/employeeSeachList HTTP/1.1
//			x-requested-with	XMLHttpRequest
//			Accept-Language	zh-cn
//			Referer	https://oa.asiainfo.com/user/employeeSeach
//			Accept	text/plain, */*; q=0.01
//			Content-Type	application/x-www-form-urlencoded
//			Accept-Encoding	gzip, deflate
//			User-Agent	Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; InfoPath.3; GWX:RESERVED)
//			Host	oa.asiainfo.com
//			Content-Length	182
//			Connection	Keep-Alive
//			Cache-Control	no-cache
//			Cookie	JSESSIONID=b4xf4FebvN0NXhgtM9q0Z1AaiZv2LWdvHsgScC5K1P86rOPY8Fmd!931069562
			propertys.put("请求", "POST /user/employeeSeachList HTTP/1.1");
			propertys.put("x-requested-with", "XMLHttpRequest");
			propertys.put("Accept-Language", "zh-cn");
			propertys.put("Referer", "https://oa.asiainfo.com/user/employeeSeach");
			propertys.put("Accept", "text/plain, */*; q=0.01");
			propertys.put("Content-Type", "application/x-www-form-urlencoded");
			propertys.put("Accept-Encoding", "gzip, deflate");
			propertys.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; InfoPath.3; GWX:RESERVED)");
			propertys.put("Host", "oa.asiainfo.com");
			propertys.put("Content-Length", "182");
			propertys.put("Connection", "Keep-Alive");
			propertys.put("Cache-Control", "no-cache");
			propertys.put("Cookie", "JSESSIONID=b4xf4FebvN0NXhgtM9q0Z1AaiZv2LWdvHsgScC5K1P86rOPY8Fmd!931069562");
			
			HttpRespons hr = request.sendPost("https://oa.asiainfo.com/user/employeeSeachList",
					params, propertys);
					
			System.out.println(hr.getUrlString());
			System.out.println(hr.getProtocol());
			System.out.println(hr.getHost());
			System.out.println(hr.getPort());
			System.out.println(hr.getContentEncoding());
			System.out.println(hr.getMethod());
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			System.out.println(hr.getContent());
			System.out.println("##############################");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void write100row2db(int pageIndex, int pageSize ){
		
	}
}
