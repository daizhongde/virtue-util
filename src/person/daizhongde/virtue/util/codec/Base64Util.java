package person.daizhongde.virtue.util.codec;

import java.util.Base64;

public class Base64Util {

	private String text = "DZd123456";
	private String mw = "0kDNwkWYilGe";//"null"
	
	public static String encode(String mingWenStr) {
		String miWenStr = Base64.getEncoder().encodeToString(mingWenStr.getBytes());
		System.out.println("加密得密文：" + miWenStr);

		return miWenStr;
	}

	public static String decode(String miWenStr) {
		byte[] byteArr = Base64.getDecoder().decode(miWenStr);
		String mingWenStr = new String(byteArr);
		System.out.println("解密得明文:" + mingWenStr);
		return mingWenStr;
	}
	
	
//	@Test
	public void testJDKBase64() {
		String msg = Base64.getEncoder().encodeToString(text.getBytes());
		System.out.println("加密：" + msg);

		byte[] byteArr = Base64.getDecoder().decode(msg);
		String msg2 = new String(byteArr);
		System.out.println("解密:" + msg2);
		
		//反转
		StringBuffer sb = new StringBuffer(msg);
		sb = sb.reverse();
		String reve=sb.toString();
        System.out.println("反转:"+reve);
	}

//	@Test
	public void testCommonsCodec() {
		byte[] byteArr = org.apache.commons.codec.binary.Base64.encodeBase64(text.getBytes());
		String msg = new String(byteArr);
		System.out.println("加密：" + msg);

		byte[] byteArr2 = org.apache.commons.codec.binary.Base64.decodeBase64(msg);
		String msg2 = new String(byteArr2);
		System.out.println("解密：" + msg2);

	}

//	@Test
	public void testDecodeCopoteMailPWD() {
		//反转
		StringBuffer sb = new StringBuffer("0kDNwkWYilGe");
		sb = sb.reverse();
		String reve=sb.toString();
        System.out.println("反转:"+reve);

		//解密
		byte[] byteArr = Base64.getDecoder().decode(reve);
		String msg2 = new String(byteArr);
		System.out.println("解密:" + msg2);
		

	}

	public static String decodeCopoteMailPWD(String mw) {
		//反转
		StringBuffer sb = new StringBuffer(mw);
		sb = sb.reverse();
		String reve=sb.toString();
//        System.out.println("反转:"+reve);

		//解密
		byte[] byteArr = Base64.getDecoder().decode(reve);
		String msg2 = new String(byteArr);
//		System.out.println("解密:" + msg2);
		return msg2;

	}
}