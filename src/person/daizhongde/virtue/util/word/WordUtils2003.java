package person.daizhongde.virtue.util.word;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.ListTables;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*******************************************
 * 通过word模板生成新的word工具类   -->docx
 * @Package com.cccuu.project.myUtils
 * @Author daizd
 * @Date 2018/3/29 14:24
 * @Version V1.0
 *******************************************/
public class WordUtils2003 {

    /**
     * 根据模板生成word
     * @param path     模板的路径
     * @param params   需要替换的参数
     * @param tableList   需要插入的参数
     * @param fileName 生成word文件的文件名
     * @param response
     */
    public HWPFDocument getWord(String path, Map<String, Object> params,
    		List<String[]> tableList, String fileName ) throws Exception {
        File file = new File(path);
    	HWPFDocument doc = new HWPFDocument(new FileInputStream(file));;
        this.replaceInPara(doc, params);    //替换文本里面的变量
        this.replaceInTable(doc, params, tableList); //替换表格里面的变量
//        OutputStream os = response.getOutputStream();
//        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
//        doc.write(os);
//        this.close(os);
//        this.close(is);
        return doc;


    }

    /**
     * 替换段落里面的变量
     * @param doc    要替换的文档
     * @param params 参数
     */
    private void replaceInPara(HWPFDocument doc, Map<String, Object> params) {

        Range range = doc.getRange();
        int paragNum = range.numParagraphs();
        
        for (int i=0; i<paragNum; i++ ) {
        	Paragraph para = range.getParagraph(i);
   
            this.replaceInPara(para, params, doc);
            
            
        }
    }

    /**
     * 替换段落里面的变量
     *
     * @param para   要替换的段落
     * @param params 参数
     */
    private void replaceInPara(Paragraph para, Map<String, Object> params, 
    		HWPFDocument doc) {
        List<CharacterRun> runs = null;
        Matcher matcher;
        if (this.matcher(para.text()).find()) {
//        	int runsNum = para.numCharacterRuns();
//            for (int i=0; i<runsNum; i++ ) {
//            	runs.add(para.getCharacterRun(i));
//            }

            int start = -1;
            int end = -1;
            String str = "";
//            for (int i = 0; i < runs.size(); i++) {
//                XWPFRun run = runs.get(i);
//                String runText = run.toString();
//                if ('$' == runText.charAt(0) && '{' == runText.charAt(1)) {
//                    start = i;
//                }
//                if ((start != -1)) {
//                    str += runText;
//                }
//                if ('}' == runText.charAt(runText.length() - 1)) {
//                    if (start != -1) {
//                        end = i;
//                        break;
//                    }
//                }
//            }

            for (String s :params.keySet()) {
	            String find = s;
	            String replValue = String.valueOf( params.get(s) );
	            String text = para.text();
                String replaced = text.replace(find, replValue);
                para.replaceText(replaced, true);
                
//				TextSegement found = para.searchText(find,
//	                    new PositionInParagraph());
//	            if (found != null) {
//	                // 判断查找内容是否在同一个Run标签中
//	                if (found.getBeginRun() == found.getEndRun()) {
//	                    XWPFRun run = runs.get(found.getBeginRun());
//	                    String runText = run.getText(run.getTextPosition());
//	                    String replaced = runText.replace(find, replValue);
//	                    run.setText(replaced, 0);
//	                } else {
//	                    // 存在多个Run标签
//	                    StringBuilder sb = new StringBuilder();
//	                    for (int runPos = found.getBeginRun(); runPos <= found
//	                            .getEndRun(); runPos++) {
//	                        XWPFRun run = runs.get(runPos);
//	                        sb.append(run.getText((run.getTextPosition())));
//	                    }
//	                    String connectedRuns = sb.toString();
//	                    String replaced = connectedRuns.replace(find, replValue);
//	                    XWPFRun firstRun = runs.get(found.getBeginRun());
//	                    firstRun.setText(replaced, 0);
//	                    // 删除后边的run标签
//	                    for (int runPos = found.getBeginRun() + 1; runPos <= found
//	                            .getEndRun(); runPos++) {
//	                        // 清空其他标签内容
//	                        XWPFRun partNext = runs.get(runPos);
//	                        partNext.setText("", 0);
//	                    }
//	                }
//	            }
			}
			
//            for (int i = start; i <= end; i++) {
//                para.removeRun(i);
//                i--;
//                end--;
//            }

            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                if (str.indexOf(key) != -1) {
                    Object value = entry.getValue();
                    if (value instanceof String) {
                        str = str.replace(key, value.toString());
                        para.getParagraph(0).getCharacterRun(0).replaceText(str, true);

//                        para.createRun().setText(str, 0);
                        break;
                    } else if (value instanceof Map) {
//                        str = str.replace(key, "");
//                        Map pic = (Map) value;
//                        int width = Integer.parseInt(pic.get("width").toString());
//                        int height = Integer.parseInt(pic.get("height").toString());
//                        int picType = getPictureType(pic.get("type").toString());
//                        byte[] byteArray = (byte[]) pic.get("content");
//                        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);
//                        try {
//                            //int ind = doc.addPicture(byteInputStream,picType);
//                            //doc.createPicture(ind, width , height,para);
//                            doc.addPictureData(byteInputStream, picType);
//                            doc.createPicture(doc.getAllPictures().size() - 1, width, height, para);
//                            para.createRun().setText(str, 0);
//                            break;
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                    }
                }
            }
        }
    }


    /**
     * 为表格插入数据，行数不够添加新行
     *
     * @param table     需要插入数据的表格
     * @param tableList 插入数据集合
     */
    private static void insertTable(Table table, List<String[]> tableList) {
        //创建行,根据需要插入的数据添加新行，不处理表头
        for (int i = 0; i < tableList.size(); i++) {
//        	table.getParagraph(0).getCharacterRun(0).insert
//        	XWPFTableRow row = table.createRow();
        }
        //遍历表格插入数据
        int length = table.numRows();
        for (int i = 1; i < length - 1; i++) {
        	/* 这里跟源2007版不一样手工改为-1了，不知道是否正确？  */
            TableRow newRow = table.getRow(i-1);
            for (int j = 0; j < newRow.numCells(); j++) {
                TableCell cell = newRow.getCell(j);
                String s = tableList.get(i - 1)[j];
                cell.getParagraph(0).getCharacterRun(0).
                	insertBefore(s);
            }
        }
    }

    /**
     * 替换表格里面的变量
     * @param doc    要替换的文档
     * @param params 参数
     */
    private void replaceInTable(HWPFDocument doc, Map<String, Object> params, List<String[]> tableList) {
    	TableIterator iterator=new TableIterator(doc.getRange());
    			
    	Table table;
        List<TableRow> rows;
        List<TableCell> cells;
        List<Paragraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            if (table.numRows() > 1) {
                //判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
                if (this.matcher(table.text()).find()) {
                    for (int i=0; i<table.numRows(); i++ ) {
                    	TableRow row = table.getRow(i);
                        for (int j=0; j<row.numCells(); j++ ) {
                        	TableCell cell = row.getCell(j);
                            for (int k=0; k<cell.numParagraphs(); k++) {
                            	Paragraph para = cell.getParagraph(k);
                                this.replaceInPara(para, params, doc);
                            }
                        }
                    }
                } else {
                	if(null==tableList|| tableList.size()==0){
                		continue;
                	}
                    insertTable(table, tableList);  //插入数据
                }
            }
        }
    }


    /**
     * 正则匹配字符串
     *
     * @param str
     * @return
     */
    private Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

 

    /**
     * 将输入流中的数据写入字节数组
     *
     * @param in
     * @return
     */
    public static byte[] inputStream2ByteArray(InputStream in, boolean isClose) {
        byte[] byteArray = null;
        try {
            int total = in.available();
            byteArray = new byte[total];
            in.read(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (isClose) {
                try {
                    in.close();
                } catch (Exception e2) {
                    e2.getStackTrace();
                }
            }
        }
        return byteArray;
    }


    /**
     * 关闭输入流
     *
     * @param is
     */
    private void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流
     *
     * @param os
     */
    private void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}