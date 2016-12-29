package person.daizhongde.virtue.util.http;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;

public class URLFileFetcher {
	/**
	 * 这个不可用
	 */
	public void fetcherImg(String url, String localPath, Map<String, String> cookie, String Host, String Referer, String extName ) throws IOException{
		File imgCodeFile = new File(localPath); 
		Connection conn2 = Jsoup.connect(url);
		conn2.userAgent(
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0")
				.method(Method.GET)
				.header("Accept","*/*")
				.header("Accept-Encoding", "gzip, deflate, br")
				.header("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
				.header("Connection", "keep-alive").header("Host", Host)
				.header("Referer", Referer)
				.header("If-Modified-Since", "Sun, 17 Apr 2016 20:13:56 GMT")
				.header("If-None-Match", "\"5713ee84-222a2\"")
				.header("Upgrade-Insecure-Requests", "1").timeout(60000);
		
		BufferedImage image = ImageIO.read(new java.io.ByteArrayInputStream(conn2.cookies(cookie).execute().bodyAsBytes())); 
		ImageIO.write(image, extName, imgCodeFile);
	}
	/**
	 * available
	 */
	public void fetcherImgByHTTP(String url, String localPath, 
			Map<String, String> cookie, Map<String, String> propertys,
			String Host, String Referer, int q , String extName) throws IOException{
		URL url2 = new URL(url);  
		HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
		try {
			conn2.setConnectTimeout(3*1000);
			
			if (propertys != null)
				for (String key : propertys.keySet()) {
					conn2.addRequestProperty(key, propertys.get(key));
				}
			
			File imgCodeFile = new File(localPath);
			conn2.connect();
			BufferedImage image = ImageIO.read(conn2.getInputStream());
			ImageIO.write(image, extName, imgCodeFile);
		} catch (IOException e) {
			throw e;
		} finally {
			if (conn2 != null)
				conn2.disconnect();
		}
	}
	
	/**
	 * available with proxy
	 * <p>
	 * 使用 http://www.imagefruity.com/images/30082790157708013302.jpg 测试出的结果
	 * <br>
	 * 美国01：98.126.159.71:443	     * 美国02：98.126.159.72:443 -	 * 美国03：98.126.159.73:443	     * 美国04：98.126.219.25:443 -
	 * 美国05：67.198.188.101:443	 * 美国06：67.198.188.30:443	     * 美国07：67.198.188.60:443	     * 美国08：7.198.189.159:443
	 * 美国09：67.198.189.170:443	 * 美国10：67.198.189.172:443	 * 美国11：67.198.189.173:443	 * 美国12：67.198.189.174:443 -
	 * 美国13：67.198.189.175:443	 * 美国14：67.198.189.177:443	 * 美国15：67.198.189.178:443	 * 美国16：67.198.189.179:443
	 * 美国17：67.198.189.185:443
	 * 韩国01：14.47.125.241:443	     * 韩国02：222.117.212.25:443
	 * 日本01：160.16.73.164:443	     * 日本02：160.16.82.129:443 -	 * 日本03：160.16.211.6:443	     * 日本04：160.16.119.187:443  -
	 * 日本05：160.16.82.129:443	 -   * 日本06：160.16.103.87:443
	 * 中国01：119.254.209.95:110 - 	 * 中国02：119.254.209.95:110 -
	 * 台湾01：61.216.121.205:443	 * 台湾02：60.249.13.130:443	     * 台湾03：60.249.13.127:443 -	 * 台湾04：61.216.121.55:443
	 * 台湾05：	                     * 台湾06：60.249.13.127:443 -
	 * 新加坡01：103.233.80.75:443
	 * 
	 * */
	public void fetcherImgByHttpUseProxy(String url, String localPath, 
			Map<String, String> cookie, Map<String, String> propertys,
			String Host, String Referer, int q , String extName,
			String proxyipport) throws IOException{
//		URL url2 = new URL("http://www.imagefruity.com/images/08822445040280144568.jpg");  
		
//		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("67.198.189.159", 443));
		URL url2 = new URL(url);  
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress((proxyipport.split(":"))[0], Integer.parseInt((proxyipport.split(":"))[1])));
		HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection(proxy);
		if (propertys != null)
			for (String key : propertys.keySet()) {
				conn2.addRequestProperty(key, propertys.get(key));
			}
//		conn2.addRequestProperty("Proxy-Authorization", "Basic ZHpkMjc0NjY3OUAxNjMuY29tOmpqazU1NTU1");
//		conn2.addRequestProperty("Proxy-Connection", "keep-alive");
		
		File imgCodeFile = new File(localPath);
		conn2.connect();
		BufferedImage image = ImageIO.read(conn2.getInputStream());
		ImageIO.write(image, extName, imgCodeFile);
	}
	
	public static void main(String args[]) throws IOException{
		String url="http://www.imagefruity.com/images/08822445040280144568.jpg";
		String picname=System.currentTimeMillis()+".jpg";
		String localPath = ""+"/caoqun/"+picname;
		String Host = "www.imagefruity.com";
//		String Referer = "http://www.caoqun.la/bbs/forum.php?mod=viewthread&tid=1";
//		System.out.println(Host.substring(2,-1));
		
//		Accept			*/*
//		Accept-Encoding			gzip, deflate
//		Accept-Language			zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3
//		Cache-Control			max-age=0
//		Connection			keep-alive
//		Host			www.caoqun.la
//		If-Modified-Since			Sun, 17 Apr 2016 20:13:56 GMT
//		If-None-Match			"5713ee84-222a2"
//		Referer			http://www.caoqun.la/bbs/forum.php?mod=viewthread&tid=23&extra=page%3D1
//		User-Agent			Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0
		
		File imgCodeFile = new File(localPath); 
		Connection conn2 = Jsoup.connect(url);
		conn2.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36")
		//		.ignoreContentType(true).
				.method(Method.GET)
		//		.data(params)
				.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
				.header("Accept-Encoding", "gzip, deflate, sdch")
				.header("Accept-Language", "zh-CN,zh;q=0.8")
//				.header("Connection", "keep-alive")
				.header("Host", "www.imagefruity.com")
				.header("Proxy-Authorization", "Basic ZHpkMjc0NjY3OUAxNjMuY29tOmpqazU1NTU1")
				.header("Proxy-Connection", "keep-alive")
				.header("Upgrade-Insecure-Requests", "1")
				.timeout(30000);
		
		BufferedImage image = ImageIO.read(new java.io.ByteArrayInputStream(conn2.execute().bodyAsBytes())); 
		ImageIO.write(image, "jpg", imgCodeFile);
	}
}
