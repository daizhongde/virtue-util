package person.daizhongde.virtue.util.xml;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Dom4jParseUtil {
	public static Document parseXML(String html) throws DocumentException{
		
//		System.out.println(html);
		html = html.substring(html.indexOf("<tbody>"), html.indexOf("</tbody>")+8);
//		System.out.println(html);
		SAXReader reader = new SAXReader();
//		Document doc = reader.read(new java.io.ByteArrayInputStream(html.getBytes()));
		return DocumentHelper.parseText(html);
		
	}
	public static Document parseXML2(String html) throws DocumentException{
		
//		System.out.println(html);
		html = html.substring(html.indexOf("<tbody>"), html.indexOf("</tbody>")+8);
//		System.out.println(html);
		SAXReader reader = new SAXReader();
		return reader.read(new java.io.ByteArrayInputStream(html.getBytes()));

	}
	public static List parseHTML(String html) throws DocumentException{
		
//		System.out.println(html);
		html = html.substring(html.indexOf("<tbody>"), html.indexOf("</tbody>")+8);
//		System.out.println(html);
		SAXReader reader = new SAXReader();
//		Document doc = reader.read(new java.io.ByteArrayInputStream(html.getBytes()));
		Document doc = DocumentHelper.parseText(html);
		
		Element root = doc.getRootElement();
		return root.elements("tr");
	}
}
