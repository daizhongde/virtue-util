package person.daizhongde.virtue.util.word.type2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import person.daizhongde.virtue.util.date.ElapsedTimePrinter;
import person.daizhongde.virtue.util.file.FilePathUtils;
/**
 * 第一个可用于替换docx类型文件的测试类
 * @author daizd
 * @date 2019年4月27日
 */
public class Test {

	public static void main(String[] args) throws Exception {
		// 需要进行文本替换的信息
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("${name}", "戴忠德");//姓名
		data.put("${yyyyMM}", "2018年02月");//年月 eg:2018年02月
		data.put("${employee_no}", "0086");//员工编号   eg:  0086
		data.put("${employee_idcard}", "430722198710286115");//证件号  eg:  430722198710286115
		data.put("${duration}", "2018/02/01-2018/02/28");//计算期  eg:  2018/02/01-2018/02/28
		
		
		data.put("${nSfgz}", "16581.48");//实发工资
		data.put("${nBygz}", "19798.3");//本月工资
		data.put("${nSqbk}", "30");//税前补款
		data.put("${nSbgjjgrkk}", "1377.6");//社保公积金个人扣款
		data.put("${nGrsds}", "990.07");//个人所得税
		data.put("${nShkk}", "63.01");//税后扣款
		
		data.put("${nJbgz}", "5600");//基本工资
		data.put("${nGwgz}", "0");//岗位工资
		data.put("${nJzgxjxsfgz}", "7002.93");//价值贡献绩效实发工资
		data.put("${nBthj}", "0");//补贴合计
		data.put("${nEndowment}", "448");//养老个人扣款
		data.put("${nSygrkk}", "33.6");//失业个人扣款
		data.put("${nMedical }", "224");//医疗个人扣款
		data.put("${nGjjgrkk}", "672");//公积金个人扣款
		data.put("${nGrsds}", "990.07");//个人所得税
		data.put("${nQtkk_ms}", "233.99");//其他扣款（免税）


		// 图片，如果是多个图片，就新建多个map
//		Map<String, Object> picture1 = new HashMap<String, Object>();
//		picture1.put("width", 100);
//		picture1.put("height", 150);
//		picture1.put("type", "jpg");
//		picture1.put("content",
//				WorderToNewWordUtils.inputStream2ByteArray(new FileInputStream("template/c1.jpg"), true));
//		data.put("${picture1}", picture1);

		// 需要进行动态生成的信息
		Map<String, Object> mapList = new HashMap<String, Object>();
//
//		// 第一个动态生成的数据列表
//		List<String[]> list01 = new ArrayList<String[]>();
//		list01.add(new String[] { "A", "美女好看" });
//		list01.add(new String[] { "A", "美女好多" });
//		list01.add(new String[] { "B", "漫展人太多" });
//		list01.add(new String[] { "C", "妹子穿的很清凉" });
//
//		// 第二个动态生成的数据列表
//		List<String> list02 = new ArrayList<String>();
//		list02.add("1、民主");
//		list02.add("2、富强");
//		list02.add("3、文明");
//		list02.add("4、和谐");
//
//		mapList.put("list01", list01);
//		mapList.put("list02", list02);

		Date beginTime = new Date();
		
		File f = new File(Test.class.getResource("/").getPath()+"\\template\\工资清单-非mercer.docx");
		
//		System.out.println("FilePathUtils:"+fu.getWebRoot());
		/* 耗时： use time: 0 day 0 hour 0 minute 1 seconds 421 milliseconds 
		 * 
		 * 这个做为湘邮工资邮件的附件生成实现  
		 *   */
		WordUtils util = new WordUtils();
		//"D:/daizd/Desktop/工资/工资清单-非mercer.docx"
		CustomXWPFDocument doc = util.getWord(
				f.getAbsolutePath(), data, null, null);
		FileOutputStream fopts = new FileOutputStream("D:/呵呵.docx");
		doc.write(fopts);
		fopts.close();
		
		Date endTime = new Date();
		ElapsedTimePrinter.printElapsedTime(beginTime, endTime, "耗时：");
		
	}
}