package person.daizhongde.virtue.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class File2UnionSQL {
 
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
			InputStreamReader isr = new InputStreamReader(is);
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
			
			String splitstr = "\nunion all\n";
//			String splitstr = "\n \n";
			StringBuilder  ret = new StringBuilder("");
			for(int i=0; i<jgdmArr.length; i++ ){
				String temp = sql.replace("$N_JGDM", jgdmArr[i]);
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
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) { 
		
		File2UnionSQL util = new File2UnionSQL();
		// util.readFile("com/mysoftstone/mq/mdm/xml4test/T_GB_ORGANIZATION.xml");/
		String sql = util.read2String(
				"D:/daizd/Desktop/ydcrkdmx-1.sql");
		
		String jgdmStr = "1001022,3000131,7105114,11006700,13005117,"
				+ "15006100,21001801,23004800,25001101,30001102,"
				+ "32500005,40001002,43500403,45001405,53002201,"
				+ "55000114,57031101,61006300,65001132,71000313,"
				+ "73000002,75000111,81000102,83000030";
		
		String sjjgStr = "1002400,3002800,7103100,11002300,13003100,"
				+ "15002400,21002300,23003400,25003300,30003400,"
				+ "32502900,40002165,43502300,45002800,53002700,"
				+ "55002700,57032200,61002600,65002600,71002800,"
				+ "73003200,75003100,81002600,83002700";
		
		/** 适用于不带上级机构条件的情况    */
		String result = util.generateUnionSQL(sql, jgdmStr.split(","),"2006" );
		
		/** 适用于带上级机构条件的情况    */
//		String result = util.generateUnionSQL(sql, jgdmStr.split(","), sjjgStr.split(","));
		
		System.out.println(result);
 
 
	}

}
