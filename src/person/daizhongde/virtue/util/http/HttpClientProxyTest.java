package person.daizhongde.virtue.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientProxyTest {

	public static void main(String args[]) {
		StringBuffer sb = new StringBuffer();
		String proxyHost=""; 
		int proxyPort=8080;
		String userName="";
		String password="";
		
		// 创建HttpClient实例
		HttpClient client = getHttpClient( proxyHost,  proxyPort, 
				 userName,   password);
		// 创建httpGet
		HttpGet httpGet = new HttpGet("http://www.a-hospital.com");
		// 执行
		try {
			HttpResponse response = client.execute(httpGet);

			HttpEntity entry = response.getEntity();

			if (entry != null) {
				InputStreamReader is = new InputStreamReader(entry.getContent());
				BufferedReader br = new BufferedReader(is);
				String str = null;
				while ((str = br.readLine()) != null) {
					sb.append(str.trim());
				}
				br.close();
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(sb.toString());
	}

	// 设置代理
		public static HttpClient getHttpClient(String proxyHost, int proxyPort, 
				String userName,  String password ) {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			httpClient.getCredentialsProvider().setCredentials(
					new AuthScope(proxyHost, proxyPort),
					new UsernamePasswordCredentials(userName, password));
			HttpHost proxy = new HttpHost(proxyHost, proxyPort);
			httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY,
					proxy);
			return httpClient;
		}
}
