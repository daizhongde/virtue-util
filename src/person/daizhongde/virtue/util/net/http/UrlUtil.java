package person.daizhongde.virtue.util.net.http;

import java.io.IOException;

public class UrlUtil {
	private String domain;
	public UrlUtil(){
	}
	
	public UrlUtil(String domain){
		this.domain = domain;
	}
	/**
	 * 
	 * @param url eg: ./img/jjk.jpg
	 * @param domain  eg:http://www.caoqun.la/bbs/
	 * @return
	 */
	public String cvUrl2absUrl( String url ){
		if(!url.startsWith("http")
				&& !url.startsWith("ftp")
				&& !url.startsWith("sftp")){
			domain = domain.replaceFirst("[\\\\\\/]+$", "");
			url = url.replaceAll("^\\.\\/", "");//去掉/./
//			url = "http://www.caoqun.la/bbs/"+url;
			url = domain + "/" + url;
			if(url.indexOf("'s")!=-1){//去年's及后面的字符串
				url = url.substring(0, url.indexOf("'s"));
			}
		}
		return url;
	}
	/**
	 * 
	 * @param url eg: ./img/jjk.jpg
	 * @param domain  eg:http://www.caoqun.la/bbs/
	 * @return
	 */
	public String cvUrl2absUrl( String url, String domain ){
		if(!url.startsWith("http")
				&& !url.startsWith("ftp")
				&& !url.startsWith("sftp")){
			domain = domain.replaceFirst("[\\\\\\/]+$", "");
			url = url.replaceAll("^\\.\\/", "");//去掉/./
//			url = "http://www.caoqun.la/bbs/"+url;
			url = domain + "/" + url;
			if(url.indexOf("'s")!=-1){//去年's及后面的字符串
				url = url.substring(0, url.indexOf("'s"));
			}
		}
		return url;
	}
	public String toggleUrlprotocol( String url ){
		if(url.startsWith("https://")){
			url = url.replaceFirst("^https://", "http://");
		}else if(url.startsWith("http://")){
			url = url.replaceFirst("^http://", "https://");
		}
		return url;
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		String url = "./img/jjk.jpg";
		UrlUtil  u = new UrlUtil();
		String r = u.cvUrl2absUrl(url, "http://www.caoqun.la/bbs\\");
		System.out.println("r:"+r);
	}
}
