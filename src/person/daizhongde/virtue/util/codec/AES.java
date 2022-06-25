package person.daizhongde.virtue.util.codec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AES {
	/**
	 * content: 加密内容
	 * slatKey: 加密的盐，16位字符串
	 * vectorKey: 加密的向量，16位字符串
	 */
	public String encrypt(String content, String slatKey, String vectorKey) throws Exception {
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), "AES");
	    IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
	    cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
	    byte[] encrypted = cipher.doFinal(content.getBytes());
	    return Base64.encodeBase64String(encrypted);
	}
	/**
	 * content: 解密内容(base64编码格式)
	 * slatKey: 加密时使用的盐，16位字符串
	 * vectorKey: 加密时使用的向量，16位字符串
	 */
	public String decrypt(String base64Content, String slatKey, String vectorKey) throws Exception {
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), "AES");
	    IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
	    cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
	    byte[] content = Base64.decodeBase64(base64Content);
	    byte[] encrypted = cipher.doFinal(content);
	    return new String(encrypted);
	}
}
