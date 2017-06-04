package person.daizhongde.virtue.util.net.http;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.Permission;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;

import person.daizhongde.virtue.util.file.FileNameUtil;
import person.daizhongde.virtue.util.file.FileUtil;
import person.daizhongde.virtue.util.net.TFTPUtil;
import person.daizhongde.virtue.util.test.Printer;

public class URLFileFetcher {
	/**
	 * 测试地址有效性
	 */
	public boolean fetcherCode(String url, String localPath, 
			Map<String, Object> propertys ) throws IOException{
		int status = 404;
		  try {
		   URL urlObj = new URL(url);
		   HttpURLConnection oc = (HttpURLConnection) urlObj.openConnection();
		   oc.setUseCaches(false);
		   oc.setConnectTimeout(3*1000); // 设置超时时间
		   status = oc.getResponseCode();// 请求状态
		   if (200 == status) {
		    // 200是请求地址顺利连通。。
		    return true;
		   }
		  } catch (Exception e) {
//			  e.printStackTrace();
//			  throw e;
		  }
		  return false;

	}
	/**
	 * 这个不可用
	 */
	public void fetcherImg(String url, String localPath, 
			Map<String, Object> propertys,  String extName ) throws IOException{
		File imgCodeFile = new File(localPath); 
		Connection conn2 = Jsoup.connect(url);
		conn2.userAgent(
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0")
				.method(Method.GET)
				.header("Accept","*/*")
				.header("Accept-Encoding", "gzip, deflate, br")
				.header("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
				.header("Connection", "keep-alive").header("Host", String.valueOf(propertys.get("Host")) )
				.header("Referer", String.valueOf(propertys.get("Referer")))
				.header("If-Modified-Since", "Sun, 17 Apr 2016 20:13:56 GMT")
				.header("If-None-Match", "\"5713ee84-222a2\"")
				.header("Upgrade-Insecure-Requests", "1").timeout(60000);

		BufferedImage image = ImageIO.read(new java.io.ByteArrayInputStream(conn2.cookies((Map)propertys.get("Cookie")).execute().bodyAsBytes())); 
		ImageIO.write(image, extName, imgCodeFile);
	}
	/**
	 * available
	 */
	public Object[] fetcherImgByHTTP(String url, String localPath, 
			Map<String, Object> propertys, long q , String extName) throws IOException{
		
		if(localPath.indexOf(".")!=-1){
			extName = FileUtil.getExtensionName(localPath);
		}
		
		URL url2 = new URL(url);
		java.net.HttpURLConnection conn2 = (java.net.HttpURLConnection) url2.openConnection();
		conn2.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0");
		try {
			conn2.setConnectTimeout(8*1000);
			conn2.setReadTimeout(45*1000);
			
//			propertys.put("Connection", "close");
//			propertys.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0");
//			conn2.setDoInput(false);//设置是否从httpUrlConnection读入，默认情况下是true;              
			conn2.setRequestMethod("GET");
			
			if (propertys != null)
				for (String key : propertys.keySet()) {
					conn2.addRequestProperty(key, String.valueOf(propertys.get(key)));
				}
			conn2.connect();
			
			String contentType = conn2.getContentType();
			int responseCode = conn2.getResponseCode();
			if( responseCode != 200 ){
				
				throw new RuntimeException("下载图片文件时出错！");
			}
//			InputStream inStream = conn2.getInputStream();//通过输入流获取图片数据  
			BufferedImage image = ImageIO.read(conn2.getInputStream() );
			//arr[3]:文件扩展名  arr[4]：文件mime_type
			Object[] arr = { image.getData().getDataBuffer().getSize(), image.getWidth(), image.getHeight(),"","" };
			arr[4] = contentType;
			
			String absDirectory_dest = localPath.substring(0, localPath.lastIndexOf("/"));
			File dest_dir = new File( absDirectory_dest );
			if(!dest_dir.exists()){
				/* 创建文件夹 */
				dest_dir.mkdirs();
			}
			
			if("".equalsIgnoreCase(extName)){
				System.out.println("extName 为空， contentType:"+contentType);
//				 image/gif ：gif图片格式    
//				    image/jpeg ：jpg图片格式 
//				    image/png：png图片格式
				if("image/jpeg".equalsIgnoreCase(contentType)){
					File imgCodeFile = new File(localPath+".jpg");
//					File imgCodeFile = new File(localPath);
					ImageIO.write(image, "jpg", imgCodeFile);
					arr[3] = "jpg";
				}else if("image/gif".equalsIgnoreCase(contentType)){
					File imgCodeFile = new File(localPath+".gif");
//					File imgCodeFile = new File(localPath);
					ImageIO.write(image, "gif", imgCodeFile);
					arr[3] = "gif";
				}else if("image/png".equalsIgnoreCase(contentType)){
					File imgCodeFile = new File(localPath+".png");
//					File imgCodeFile = new File(localPath);
					ImageIO.write(image, "png", imgCodeFile);
					arr[3] = "png";
				}else if("image/bmp".equalsIgnoreCase(contentType)){
					File imgCodeFile = new File(localPath+".bmp");
//					File imgCodeFile = new File(localPath);
					ImageIO.write(image, "bmp", imgCodeFile);
					arr[3] = "bmp";
				}else if( contentType.matches("^image\\/\\S+") ){
//					File imgCodeFile = new File(localPath+".png");
					File imgCodeFile = new File(localPath);
					ImageIO.write(image, "", imgCodeFile);
					arr[3] = "";
				}else{
					throw new RuntimeException("写文件时出错！");
				}
			}else{
				File imgCodeFile = new File(localPath);
				arr[3] = extName;
				ImageIO.write(image, extName, imgCodeFile);
			}
			
			return arr;
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
			Map<String, String> propertys, int q , String extName,
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
		String contentType = conn2.getContentType();
		String contentEncoding = conn2.getContentEncoding();
		System.out.println("contentType:"+contentType);
		System.out.println("contentEncoding:"+contentEncoding);
		
		InputStream is = conn2.getInputStream();
		FileUtil util = new FileUtil();
		util.write2File(is, "D:/usr/test2/Aan%20Mitsu%20%2871%29.jpg");
		
		try {
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
	 * 下载视频文件
	 * available
	 */
	public Object[] fetcherVideo(String url, String localPath, 
			Map<String, Object> propertys, long q , String extName) throws Exception{
		if(url.toLowerCase().startsWith("ftp")){
			return fetcherVideoByCommonNet(url, localPath, 
					propertys, q, "" );
		}else if(url.toLowerCase().startsWith("http")){
			return fetcherVideoByHTTP(url, localPath, 
					propertys, q, "" );
		}else{
			return fetcherVideoByCommonNet(url, localPath, 
					propertys, q, "" ); 
		}
	}
	/**
	 * 下载视频文件
	 * available
	 */
	public Object[] fetcherVideoByCommonNet(String url, String localPath, 
			Map<String, Object> propertys, long q , String extName) throws Exception{
		
		System.out.println("开始下载：url:"+url+",localPath:"+localPath);
		if(localPath.indexOf(".")!=-1){
			extName = FileUtil.getExtensionName(localPath);
		}
		
		String absDirectory_dest = localPath.substring(0, localPath.lastIndexOf("/"));
		File dest_dir = new File( absDirectory_dest );
		if(!dest_dir.exists()){
			/* 创建文件夹 */
			dest_dir.mkdirs();
		}
		
		try {
//			ftp://447022608-8dgo:854067@60.199.162.59:10846/2-sdmu493b-5b.mp4
			FileNameUtil fnamet = new FileNameUtil();
			String[] suffixArr = {"'s","/cC/f"};//to split url
			String localFilename = fnamet.cvUrl2legalFilename( url, suffixArr );
			String remoteFilename = localFilename;
			String temp = url.substring(url.indexOf("@")+1);
//			String temp = url.substring(url.indexOf("//")+2);
			String hostname  = temp.substring(0, temp.indexOf("/"));
//			
			
	        int transferMode = TFTP.BINARY_MODE;
//	        String hostname, localFilename, remoteFilename;
//	        int timeout = 24*60*60*1000;//24小时
	        int timeout = 3*1000;// 3秒
	        boolean verbose = false;
//			TFTPUtil.receive(transferMode, hostname, localPath, remoteFilename, timeout, verbose);
	        //未完待续
            
            Object[] arr = { "", "", "","","" };

			return arr;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			
		}
	}
	/**
	 * 下载视频文件
	 * <p>
	 * sun.net.www.protocol.ftp.FtpURLConnection 实现
	 * available
	 */
	public Object[] fetcherVideoByFTP(String url, String localPath, 
			Map<String, Object> propertys, long q , String extName) throws Exception{
		
		System.out.println("开始下载：url:"+url+",localPath:"+localPath);
		if(localPath.indexOf(".")!=-1){
			extName = FileUtil.getExtensionName(localPath);
		}
		
		String absDirectory_dest = localPath.substring(0, localPath.lastIndexOf("/"));
		File dest_dir = new File( absDirectory_dest );
		if(!dest_dir.exists()){
			/* 创建文件夹 */
			dest_dir.mkdirs();
		}
		
		URL url2 = new URL(url);
		sun.net.www.protocol.ftp.FtpURLConnection conn2 = (sun.net.www.protocol.ftp.FtpURLConnection) url2.openConnection();
		conn2.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0");
		try {
			conn2.setConnectTimeout(12*1000);
			conn2.setReadTimeout(80*1000);

//			propertys.put("Connection", "close");
//			propertys.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0");
//			conn2.setDoInput(false);//设置是否从httpUrlConnection读入，默认情况下是true;              
//			conn2.setRequestMethod("GET");
			
			if (propertys != null)
				for (String key : propertys.keySet()) {
					conn2.addRequestProperty(key, String.valueOf(propertys.get(key)));
				}
			conn2.connect();
			
			String contentType = conn2.getContentType();
//			int responseCode = conn2.getResponseCode();
//			if( responseCode != 200 ){
			int contentlength = conn2.getContentLength();
			System.out.println("contentlength:"+contentlength);
//			if( contentlength == 0 ){
//				throw new RuntimeException("下载电影文件时出错！url:"+url);
//			}
			
			InputStream inputStream = conn2.getInputStream();//得到服务器端传回来的数据，相对客户端为输入流

            byte[] data = readInstream(inputStream);
            File file = new File( localPath );
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(data);
            outputStream.close(); 
            
            
//			InputStream inStream = conn2.getInputStream();//通过输入流获取图片数据  
//			BufferedImage image = ImageIO.read(conn2.getInputStream() );
			//[0]size, [1]width, [2]heigth, arr[3]:文件扩展名  arr[4]：文件mime_type
//			Object[] arr = { image.getData().getDataBuffer().getSize(), image.getWidth(), image.getHeight(),"","" };
//			arr[4] = contentType;
            Object[] arr = { "", "", "","","" };

			return arr;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn2 != null)
				conn2.close();
		}
	}
	
	/**
	 * 下载视频文件
	 * available
	 */
	public Object[] fetcherVideoByHTTP(String url, String localPath, 
			Map<String, Object> propertys, long q , String extName) throws Exception{
		
		System.out.println("开始下载：url:"+url+",localPath:"+localPath);
		if(localPath.indexOf(".")!=-1){
			extName = FileUtil.getExtensionName(localPath);
		}
		
		String absDirectory_dest = localPath.substring(0, localPath.lastIndexOf("/"));
		File dest_dir = new File( absDirectory_dest );
		if(!dest_dir.exists()){
			/* 创建文件夹 */
			dest_dir.mkdirs();
		}
		
		URL url2 = new URL(url);
		java.net.HttpURLConnection conn2 = (java.net.HttpURLConnection) url2.openConnection();
		conn2.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0");
		try {
			conn2.setConnectTimeout(12*1000);
			conn2.setReadTimeout(80*1000);
			
//			propertys.put("Connection", "close");
//			propertys.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0");
//			conn2.setDoInput(false);//设置是否从httpUrlConnection读入，默认情况下是true;              
			conn2.setRequestMethod("GET");
			
			if (propertys != null)
				for (String key : propertys.keySet()) {
					conn2.addRequestProperty(key, String.valueOf(propertys.get(key)));
				}
			conn2.connect();
			
			String contentType = conn2.getContentType();
			int responseCode = conn2.getResponseCode();
			if( responseCode != 200 ){
				
				throw new RuntimeException("下载电影文件时出错！url:"+url);
				
			}
			
			InputStream inputStream = conn2.getInputStream();//得到服务器端传回来的数据，相对客户端为输入流

            byte[] data = readInstream(inputStream);
            File file = new File( localPath );
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(data);
            outputStream.close(); 
            
            
//			InputStream inStream = conn2.getInputStream();//通过输入流获取图片数据  
//			BufferedImage image = ImageIO.read(conn2.getInputStream() );
			//[0]size, [1]width, [2]heigth, arr[3]:文件扩展名  arr[4]：文件mime_type
//			Object[] arr = { image.getData().getDataBuffer().getSize(), image.getWidth(), image.getHeight(),"","" };
//			arr[4] = contentType;
            Object[] arr = { "", "", "","","" };

			return arr;
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn2 != null)
				conn2.disconnect();
		}
	}
	
	private byte[] readInstream(InputStream inputStream) throws Exception {
		ByteArrayOutputStream byteArrayOutPutStream = new ByteArrayOutputStream();// 创建ByteArrayOutputStream类
		byte[] buffer = new byte[1024];// 声明缓存区
		int length = -1;// 定义读取的默认长度
		while ((length = inputStream.read(buffer)) != -1) {
			byteArrayOutPutStream.write(buffer, 0, length);// 把缓存区中的输入到内存中
		}
		byteArrayOutPutStream.close();// 关闭输入流
		inputStream.close();// 关闭输入流

		return byteArrayOutPutStream.toByteArray();// 返回这个输入流的字节数组
	}
	public static void main(String args[]) throws Exception{
		//ftp://447022608-8dgo:607123@60.199.162.59:10719/2-jufd5792-5b.mp4
		//ftp://447022608-8dgo:607123@60.199.162.59:10719/2-jufd5792-5b.mp4
		String url="ftp://hadoopadmin:hadoopadmin123#@10.158.240.9:21/mnt/disk01/.hadoopadmin/tmp/G_TOBACCO.java";
		String localPath="D:/tmp/G_TOBACCO.java";
		
		URLFileFetcher fetcher = new URLFileFetcher();
		fetcher.fetcherVideoByCommonNet( url, localPath, 
				null, 2l,  "");
	}
}
