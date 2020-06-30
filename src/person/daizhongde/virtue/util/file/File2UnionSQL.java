package person.daizhongde.virtue.util.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

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
			String splitstr = "\n;\n";
//			String splitstr = "\ncommit; \n";
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
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws UnsupportedEncodingException { 
		
		File2UnionSQL util = new File2UnionSQL();
		// util.readFile("com/mysoftstone/mq/mdm/xml4test/T_GB_ORGANIZATION.xml");/
		
		String sql = util.read2String(
				"D:/daizd/Desktop/bak/1.txt");
		
//		String begin_Str = util.read2String(
//				"D:/daizd/Desktop/begin.txt");
//		
//		String end_Str = util.read2String(
//				"D:/daizd/Desktop/end.txt");
		
		String begin_Str = "";

		String end_Str = "";
		
		/**     20190531  */
//String jgdmStr = "13005117,45001405,57031101,3000131,73000002,40003903,42300095,61006300,25010103,41001507,"
//		+ "10003101,42100125,41126300,25003102,83000030,7105114,40003003,40070001,40132001,40980002,"
//		+ "56216100,81020001,10001501,42200029,41000516,42546500,25000114,55000114,27786400,1001022,"
//		+ "40276012,41136300,42416300,65001132,81000102,81600006,41426600,40520006,75000111,32500005,"
//		+ "40132901,40840101,42136600,42616600,41110104,42426200,41000715,41200011,53002201,41446600,"
//		+ "42157000,42336200,41600016,41936400,42296400,25001101,15006100,41226900,42196500,42276500,"
//		+ "30001102,41400014,21001801,41000716,42446600,81000017,71000313,40800004,42186500,55040004,"
//		+ "23004800,42726400,42356600,40256001,42116300,42100213,11006700,41557100,42256500,55306100,"
//		+ "40470002,55830002,41700042,43500403,40001002,42500202,41466600,42700008";
		

//		String jgdmStr = "1001022,3000131,7105114,11006700,13005117,15006100,21001801,23004800,25001101,30001102,"
//				+ "32500005,40001002,43500403,45001405,53002201,55000114,57031101,61006300,65001132,71000313,"
//				+ "73000002,75000111,81000102,83000030";
		// 31个省机构--来自省份代码表
		String jgdmStr="4700,10000100,30000100,5000900,3000100,1000500,11000500,13000500,15001100,20000100,21000500,31000500,23000100,35001000,33000500,25000100,45000100,43000100,41000100,51080100,53000100,57010500,40000100,61000100,55000100,65001200,85000500,71000900,73000500,81001000,75000500,83001200";
		
		/** 适用于不带上级机构条件的情况    */
		String result = util.generateUnionSQL(sql, jgdmStr.split(",")  );

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
	@SuppressWarnings("unchecked")
	public static void main3(String[] args) throws UnsupportedEncodingException { 
		
		File2UnionSQL util = new File2UnionSQL();
		// util.readFile("com/mysoftstone/mq/mdm/xml4test/T_GB_ORGANIZATION.xml");/
		
		String sql = util.read2String(
				"D:/daizd/Desktop/1.txt");
		/*
		任务启动时刻                    : 2019-07-24 00:11:06
		任务结束时刻                    : 2019-07-24 00:11:48
		任务总计耗时                    :                 42s
		任务平均流量                    :            1.42MB/s
		记录写入速度                    :           5263rec/s
		读出记录总数                    :              210548
		读写失败总数                    :                   0
		 */
		
		String starttime="";
		String endtime="";
		String usetime="";
		String average="";
		String speed="";
		String readcount="";
		String writecount="";
		
		String[] arr = sql.split("\n");
		System.out.println("arr.length:"+arr.length);

		
		String node_remark = "";
		if(arr.length>7 &&  arr[arr.length-7].startsWith("任务启动时刻") ){
			System.out.println("arr["+(arr.length-7)+"]:"+  arr[arr.length-7]);
			System.out.println("arr["+(arr.length-6)+"]:"+  arr[arr.length-6]);
			System.out.println("arr["+(arr.length-5)+"]:"+  arr[arr.length-5]);
			System.out.println("arr["+(arr.length-4)+"]:"+  arr[arr.length-4]);
			System.out.println("arr["+(arr.length-3)+"]:"+  arr[arr.length-3]);
			System.out.println("arr["+(arr.length-2)+"]:"+  arr[arr.length-2]);
			System.out.println("arr["+(arr.length-1)+"]:"+  arr[arr.length-1]);

			
			starttime=arr[arr.length-7].split(" :")[1].trim();
			endtime=arr[arr.length-6].split(" :")[1].trim();
			usetime=arr[arr.length-5].split(" :")[1].trim();
			average=arr[arr.length-4].split(" :")[1].trim();
			speed=arr[arr.length-3].split(" :")[1].trim();
			readcount=arr[arr.length-2].split(" :")[1].trim();
			writecount=arr[arr.length-1].split(" :")[1].trim();
			

			System.out.println("starttime:"+  starttime);
			System.out.println("endtime:"+  endtime);
			System.out.println("usetime:"+  usetime);
			System.out.println("average:"+  average);
			System.out.println("speed:"+  speed);
			System.out.println("readcount:"+  readcount);
			System.out.println("writecount:"+  writecount);
		}
		
//		if (true)return;
		if("30_DATAXTOV3_CRKDMX2".toLowerCase().contains("datax")){
			System.out.println("true");
		}else{
			System.out.println("false");
		}
 
	}
}
