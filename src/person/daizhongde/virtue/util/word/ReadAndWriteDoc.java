package person.daizhongde.virtue.util.word;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
 
 
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.FieldsDocumentPart;
import org.apache.poi.hwpf.usermodel.Field;
import org.apache.poi.hwpf.usermodel.Fields;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.wp.usermodel.CharacterRun;
import org.apache.poi.xwpf.usermodel.PositionInParagraph;
import org.apache.poi.xwpf.usermodel.TextSegement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
 
public class ReadAndWriteDoc {
 
	
    /**
     * 实现对word读取和修改操作(word2003.doc)
     * @throws IOException 
     */
    public static void readwriteWord1(String filePath, Map<String,String> map, String dest) throws IOException{
        //读取word模板  
//      String fileDir = new File(base.getFile(),"http://www.cnblogs.com/http://www.cnblogs.com/../doc/").getCanonicalPath();  
      FileInputStream in = null;  
      try {  
          in = new FileInputStream(new File(filePath));  
      } catch (FileNotFoundException e1) {  
          e1.printStackTrace();  
      }  
      HWPFDocument hdt = null;  
      try {  
          hdt = new HWPFDocument(in);  
      } catch (IOException e1) {  
          e1.printStackTrace();  
      }  
      Fields fields = hdt.getFields();  
      Iterator<Field> it = fields.getFields(FieldsDocumentPart.MAIN).iterator();  
      while(it.hasNext()){  
          System.out.println(it.next().getType());  
      }  
    
      //读取word文本内容  
      Range range = hdt.getRange();  
      System.out.println(range.text());  
      //替换文本内容  
      for (Map.Entry<String,String> entry: map.entrySet()) {  
          range.replaceText( entry.getKey() ,entry.getValue());  
      }  
      ByteArrayOutputStream ostream = new ByteArrayOutputStream();  
//      String fileName = ""+System.currentTimeMillis();  
//      fileName += ".doc";  
      FileOutputStream out = null;  
      try {  
          out = new FileOutputStream(dest,true);  
      } catch (FileNotFoundException e) {  
          e.printStackTrace();  
      }  
      try {  
          hdt.write(ostream);  
      } catch (IOException e) {  
          e.printStackTrace();  
      }  
      //输出字节流  
      try {  
          out.write(ostream.toByteArray());  
      } catch (IOException e) {  
          e.printStackTrace();  
      }  
      try {  
          out.close();  
      } catch (IOException e) {  
          e.printStackTrace();  
      }  
      try {  
          ostream.close();  
      } catch (IOException e) {  
          e.printStackTrace();  
      }  
  }  

    /**
     * 实现对word读取和修改操作(word2007.docx)
     */
    public static void readwriteWord2(String filePath, Map<String,String> map, String dest){
        try {
            OPCPackage pack = POIXMLDocument.openPackage(filePath);
            XWPFDocument doc = new XWPFDocument(pack);
            List<XWPFParagraph> paragraphs = doc.getParagraphs();
            System.out.println(paragraphs.size());
            for (XWPFParagraph tmp : paragraphs) {
                System.out.println(tmp.getParagraphText());
                List<XWPFRun> runs = tmp.getRuns();
//                for (XWPFRun aa : runs) {
//                    System.out.println("XWPFRun-Text:" + aa.getText(0));
//                    for (Map.Entry<String,String> entry: map.entrySet()) {
//                        if (aa.getText(0) != null && aa.getText(0).contains(entry.getKey())) {
//                            aa.setText(entry.getValue(), 0);
//                        }
//                    }
// 
//                }

    			for (String s :map.keySet()) {
    	            String find = s;
    	            String replValue = String.valueOf( map.get(s) );
    		            
    				TextSegement found = tmp.searchText(find,
    	                    new PositionInParagraph());
    	            if (found != null) {
    	                // 判断查找内容是否在同一个Run标签中
    	                if (found.getBeginRun() == found.getEndRun()) {
    	                    XWPFRun run = runs.get(found.getBeginRun());
    	                    String runText = run.getText(run.getTextPosition());
    	                    String replaced = runText.replace(find, replValue);
    	                    run.setText(replaced, 0);
    	                } else {
    	                    // 存在多个Run标签
    	                    StringBuilder sb = new StringBuilder();
    	                    for (int runPos = found.getBeginRun(); runPos <= found
    	                            .getEndRun(); runPos++) {
    	                        XWPFRun run = runs.get(runPos);
    	                        sb.append(run.getText((run.getTextPosition())));
    	                    }
    	                    String connectedRuns = sb.toString();
    	                    String replaced = connectedRuns.replace(find, replValue);
    	                    XWPFRun firstRun = runs.get(found.getBeginRun());
    	                    firstRun.setText(replaced, 0);
    	                    // 删除后边的run标签
    	                    for (int runPos = found.getBeginRun() + 1; runPos <= found
    	                            .getEndRun(); runPos++) {
    	                        // 清空其他标签内容
    	                        XWPFRun partNext = runs.get(runPos);
    	                        partNext.setText("", 0);
    	                    }
    	                }
    	            }
    			}
            }
            String fileName = System.currentTimeMillis()+filePath.substring(filePath.lastIndexOf("/")+1, filePath.length());
            FileOutputStream fos = new FileOutputStream(dest,true);
            doc.write(fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
 
 
 
    public static void main(String[] args) throws IOException {
		// 需要进行文本替换的信息
		Map<String, String> data = new HashMap<String, String>();
		data.put("${name}", "姓名");//姓名
		data.put("${yyyyMM}", "2018年02月");//年月 eg:2018年02月
		data.put("${employee_no}", "0086");//员工编号   eg:  0086
		data.put("${employee_idcard}", "430722198710286115");//证件号  eg:  430722198710286115
		data.put("${duration}", "2018/02/01-2018/02/28");//计算期  eg:  2018/02/01-2018/02/28
		
		
		data.put("${nSfgz}", "16581.48");//实发工资
		data.put("${nBygz}", "19798.3");//本月工资
		data.put("${nSqbk}", "30");//税前补款
		data.put("${nSbgjjgrkk}", "1377.6");//社保公积金个人扣款
		data.put("${nGrsds}", "990.07");//个人所得税
		data.put("${picture2}", "63.01");//税后扣款
		
		data.put("${nJbgz}", "5600");//基本工资
		data.put("${nGwgz}", "0");//岗位工资
//		data.put("${nJxgz}", "7002.93");//绩效工资
		data.put("${nJzgxjxsfgz}", "7002.93");//价值贡献绩效实发工资
		data.put("${nBthj}", "0");//补贴合计
		data.put("${nEndowment}", "448");//养老个人扣款
		data.put("${nSygrkk}", "33.6");//失业个人扣款
		data.put("${nMedical}", "224");//医疗个人扣款
		data.put("${nGjjgrkk}", "672");//公积金个人扣款
		data.put("${nGrsds}", "990.07");//个人所得税
		data.put("${nQtkk_ms}", "233.99");//其他扣款（免税）

        String filePath1 = "D:/daizd/Desktop/工资/工资清单-非mercer.doc";
//        String filePath2 = "D:/daizd/Desktop/工资/工资清单-非mercer.docx";
        readwriteWord1(filePath1, data,"D:/呵呵.doc");
//        readwriteWord2(filePath2, data,"D:/呵呵.docx");
    }
}