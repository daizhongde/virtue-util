package person.daizhongde.virtue.util.system;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import person.daizhongde.virtue.util.test.Printer;

public class SystemProperties {
	public static void main(String args[]) throws UnknownHostException, SocketException {
		String key="";
		Properties prop = System.getProperties();
		Set set6 = prop.keySet();
		Iterator it6 = set6.iterator();
		while(it6.hasNext()){
			key = it6.next().toString();
			System.out.println(key+":"+prop.get(key));
		}
		
		String str = prop.getProperty("java.io.tmpdir");
		System.out.println("str:"+str);
//		CharacterConvert.testCharSet(str);
		
//		String[] arr = str.split();
		
//		File fold  = new File();
//		StringTokenizer 是出于兼容性的原因而被保留的遗留类（虽然在新代码中并不鼓励使用它）。
//		建议所有寻求此功能的人使用 String 的 split 方法或 java.util.regex 包
		
//		StringTokenizer StringT = new StringTokenizer("this is a test");
		StringTokenizer StringT = new StringTokenizer(str, "\\");
		str.replaceAll( "\'\\\'" , "-");
		System.out.println("str:"+str);
		Printer.print(StringT);
	}
}
