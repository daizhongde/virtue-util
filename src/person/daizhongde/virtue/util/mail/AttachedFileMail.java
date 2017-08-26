package person.daizhongde.virtue.util.mail;  
  
import java.io.File;  
import java.util.Properties;  
  
import javax.mail.internet.MimeMessage;  
import org.springframework.core.io.FileSystemResource;  
import org.springframework.mail.javamail.JavaMailSenderImpl;  
import org.springframework.mail.javamail.MimeMessageHelper;  
  
public class AttachedFileMail  
{  
    /** 
     * 本类测试的是关于邮件中带有附件的例子 
     *  
     * @param args 
     */  
    public static void main(String[] args) throws Exception  
    {  
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();  
  
        // 设定mail server  
        senderImpl.setHost("smtp.qq.com");  
        // 建立邮件消息,发送简单邮件和html邮件的区别  
        MimeMessage mailMessage = senderImpl.createMimeMessage();  
        // 注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用，  
        // multipart模式 为true时发送附件 可以设置html格式  
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,  
                true, "utf-8");  
  
        // 设置收件人，寄件人  
        messageHelper.setTo("413881461@qq.com");  
        messageHelper.setFrom("447022608@qq.com");  
        messageHelper.setSubject("测试邮件中上传附件!！");  
        // true 表示启动HTML格式的邮件  
        messageHelper.setText(  
                "<html><head></head><body><h1>你好：附件中有学习资料！</h1></body></html>",  
                true);  
  
        FileSystemResource file = new FileSystemResource(  
                new File("F:\\360data\\重要数据\\我的文档\\我的文档-20141113.rar"));  
        // 这里的方法调用和插入图片是不同的。  
        messageHelper.addAttachment("我的文档-20141113.rar", file);  
  
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