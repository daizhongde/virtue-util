package person.daizhongde.virtue.util.xml;

import java.util.Iterator;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HtmlCharset {
	public String getHtmlCharset(Document document){
		String charset = "";
		org.jsoup.select.Elements metas = document.getElementsByTag("meta");
		Iterator<Element> it = metas.iterator();
		while(it.hasNext()){
			Element meta = it.next();
			if(meta.hasAttr("content") && meta.attr("content").split("=").length==2){
				String content = meta.attr("content");
				System.out.println("content:"+content);
				charset = content.split("=")[1];
				break;
			}
		}
		return charset;
	}
}
