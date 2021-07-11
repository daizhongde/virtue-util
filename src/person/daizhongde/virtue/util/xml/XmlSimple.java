package person.daizhongde.virtue.util.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSONObject;

public class XmlSimple {

	public String write(){
		Document document = DocumentHelper.createDocument();   
		Element root = document.addElement("members");// 创建根节点
		return "";
		
	}
	public Map<String, String> readBodyAttrs(String text){
		Document document = this.read2(text);
		
		/* 方式
		 * 2、也可直接读文件,需要联调。。。待完善  */
//		Document document = sample.read2Document( "D:\\daizd\\Desktop\\a.xml");

				
		// 读元素Envelope->Body->business->para的'aae010'属性
		List<Element> para = document.getRootElement()
				.element("Body")
				.element("business")
				.elements("para");//.get(4).attributeValue("aac002");
		
//		Set<String> set = new HashSet<String>();
		
		
		Map<String, String> map = new HashMap<String,String>();
		for(Element e : para){
			Attribute attr = e.attributes().get(0);
			System.out.println(" attr.getText():"
					+ attr.getName()
							+ ", attr.getValue(): "
					+ attr.getValue()); 
			map.put( attr.getName(), attr.getValue());
		}
		return map;
	}

	public String read1(Document doc){
		
		SAXReader reader = new SAXReader();   
		try {
			Document document = reader.read(new File("D:\\daizd\\Desktop\\a.xml"));
			
		} catch (DocumentException e) {
			System.out.println("读xml文件时出错！");
		}
		
		return "";
	}
	
	public Document read2Document(String absPath){
		Document document = null;
		SAXReader reader = new SAXReader();   
		try {
			document = reader.read(new File(absPath));
		} catch (DocumentException e) {
			System.out.println("解析xml数据时出错！");
			e.printStackTrace();
		}
		
		return document;
	}
	
	public Document read2(String text){
		Document document = null;
		try {
			document = DocumentHelper.parseText(text);
			
		} catch (DocumentException e) {
			System.out.println("读xml文件时出错！");
			e.printStackTrace();
		}
		
		return document;
	}
	/**
	 * InputStreamReader+BufferedReader读取字符串 ， InputStreamReader类是从字节流到字符流的桥梁
	 * 按行读对于要处理的格式化数据是一种读取的好方式 <br>
	 * read by line
	 */
	public String read2String(String file, String charset) {
		int len = 0;
		StringBuffer str = new StringBuffer("");
		// File file = new File(FILE_IN);
		try {
			FileInputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is, charset);
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
	
	
	public static void main(String args[]){
		XmlSimple sample = new XmlSimple();
		String fileCharset = "GBK";
		/* 方式
		 * 1、可通过工具方法把文件内容读成string  */
		String text = sample.read2String("D:\\daizd\\Desktop\\a.xml",
				fileCharset );

		Map<String, String> map = sample.readBodyAttrs( text);
		
		
		System.out.println("aae010:"+  map.get("aae010") ); 
		System.out.println("sad028:"+  map.get("sad028") ); 
		System.out.println("aac058:"+  map.get("aac058") ); 
		System.out.println("aac003:"+  map.get("aac003") ); 
		
	}
}
