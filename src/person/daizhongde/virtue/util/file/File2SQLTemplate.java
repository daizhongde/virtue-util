package person.daizhongde.virtue.util.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class File2SQLTemplate {
 
	/**
	 * InputStreamReader+BufferedReader读取字符串 ， InputStreamReader类是从字节流到字符流的桥梁
	 * 按行读对于要处理的格式化数据是一种读取的好方式 <br>
	 * read by line
	 */
	public String read2String(String file) {
		int len = 0;
		StringBuffer str = new StringBuffer("");
		// File file = new File(FILE_IN);
		try {
			FileInputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is,"UTF-8");
			BufferedReader in = new BufferedReader(isr);
			String line = null;
			while ((line = in.readLine()) != null) {
				if (len != 0) // 处理换行符的问题
				{
					str.append(" \r\n" + line);
				} else {
					str.append(line);
				}
				len++;
			}
			in.close();
			isr.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str.toString();
	}
	/**
	 * 适用于不带上级机构条件的情况 -- 
	 * @param sql
	 * @param jgdmArr
	 * @return
	 */
		public String generateUnionSQL(String sql, String[] jgdmArr ) {
			
//			String splitstr = "\nunion all\n";
//			String splitstr = "\n;\n";
			String splitstr = "\n\n";
//			String splitstr = "\ncommit; \n";
//			String splitstr = "\n \n";
			StringBuilder  ret = new StringBuilder("");
//			for(int i=0; i<jgdmArr.length; i++ ){
//			String temp = sql.replace("$N_JGDM", jgdmArr[i]);
//			 temp = temp.replace("${NUM}", new Integer(i+1).toString()  );
			for(int i=0; i<128; i++ ){
				String temp = sql.replace("${NUM}", String.format("%04d", i) );
				ret.append(temp);
				if(i != jgdmArr.length-1){
//					ret.append("\nunion all\n");
					ret.append( splitstr );
				}
			}
			return ret.toString();
		}
/**
 * 适用于不带上级机构条件的情况
 * @param sql
 * @param jgdmArr
 * @return
 */
	public String generateUnionSQL(String sql, String[] jgdmArr, String year ) {
		
		String splitstr = "\nunion all\n";
//		String splitstr = "\n \n";
		StringBuilder  ret = new StringBuilder("");
		for(int i=0; i<jgdmArr.length; i++ ){
			String temp = sql.replace("$N_JGDM", jgdmArr[i]);
			temp = temp.replace("$N_YEAR", year);
			ret.append(temp);
			if(i != jgdmArr.length-1){
//				ret.append("\nunion all\n");
				ret.append( splitstr );
			}
		}
		return ret.toString();
	}
/**
 * 适用于带上级机构条件的情况
 * @param sql
 * @param jgdmArr
 * @param sjjgArr
 * @return
 */
	public String generateUnionSQL(String sql, String[] jgdmArr,  String[] sjjgArr ) {
		if(sjjgArr.length!=jgdmArr.length){
			throw new RuntimeException("上级机构与机构代码数量不一致！");
		}
		
		StringBuilder  ret = new StringBuilder("");
		for(int i=0; i<jgdmArr.length; i++ ){
			if( !sjjgArr[i].startsWith(jgdmArr[i].substring(0, 3)) ){
				System.out.println(sjjgArr[i]+"--"+jgdmArr[i]);
				throw new RuntimeException("上级机构与网点机构对应关系上不匹配！");
			}
			String temp = sql.replace("$N_JGDM", jgdmArr[i]);
			temp = temp.replace("$N_SJJG", sjjgArr[i]);
			ret.append(temp);
			if(i != jgdmArr.length-1){
				ret.append("\nunion all\n");
			}
		}
		return ret.toString();
	}
	
	/**
	 * 用于按机构组装union all sql
	 * 用于新一代系统数据整体迁移
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws UnsupportedEncodingException { 
		
		File2SQLTemplate util = new File2SQLTemplate();
		// util.readFile("com/mysoftstone/mq/mdm/xml4test/T_GB_ORGANIZATION.xml");/
		
		// DRDS_XYDJY_SC_DBZX_1570698929547FOCY_RIDU_0127
		String sql = util.read2String(
				"D:/daizd/Desktop/1.txt");
		
//		String begin_Str = util.read2String(
//				"D:/daizd/Desktop/begin.txt");
//		
//		String end_Str = util.read2String(
//				"D:/daizd/Desktop/end.txt");
		
		String begin_Str = "";

		String end_Str = "";
		
		/**     20190531  */
		

//		String jgdmStr = "1001022,3000131,7105114,11006700,13005117,15006100,21001801,23004800,25001101,30001102,"
//				+ "32500005,40001002,43500403,45001405,53002201,55000114,57031101,61006300,65001132,71000313,"
//				+ "73000002,75000111,81000102,83000030";
		
		String[] jgdmArr=new String[]{"41000100"};
		
		/** 适用于不带上级机构条件的情况    */
		String result = util.generateUnionSQL(sql, jgdmArr   );

		/** 适用于不带上级机构，带年度条件的情况    */
//		String result = util.generateUnionSQL(sql, jgdmStr.split(","),"2006" );
		
		/** 适用于带上级机构条件的情况    */
//		String result = util.generateUnionSQL(sql, jgdmStr.split(","), sjjgStr.split(","));
		
//		System.out.println(result);
		String tt = begin_Str+" "+result+" "+ end_Str;

		tt = new String(tt.getBytes("UTF-8"),"utf-8");
		tt = tt.replaceAll("[?]", "");
		
		System.out.println(tt);
 
 
	}
}
