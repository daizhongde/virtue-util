package person.daizhongde.virtue.util.mail;

import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 本类测试html邮件
 * 
 * @author sunny
 * 
 */
public class HTMLMailDemo {
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

		// 设定mail server
		senderImpl.setHost("smtp.qq.com");

		// 建立邮件消息,发送简单邮件和html邮件的区别
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);

		// 设置收件人，寄件人
		messageHelper.setTo("413881461@qq.com");
		messageHelper.setFrom("447022608@qq.com");
		messageHelper.setSubject("测试HTML邮件！");
		// true 表示启动HTML格式的邮件
		messageHelper
				.setText(
						"<html><head></head><body><h1>hello!!spring html Mail</h1></body></html>",
						true);

		senderImpl.setUsername("447022608"); // 根据自己的情况,设置username
		senderImpl.setPassword("dlsseydutgcybhai"); // 根据自己的情况, 设置password
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put("mail.smtp.timeout", "25000");
		prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		prop.setProperty("mail.smtp.port", "465");
		prop.setProperty("mail.smtp.socketFactory.port", "465");
		senderImpl.setJavaMailProperties(prop);
		// 发送邮件
		senderImpl.send(mailMessage);

		System.out.println("邮件发送成功..");
	}
}