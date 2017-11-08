package cn.itcast.bos.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils {
	private static String smtp_host = "smtp.163.com"; 

	private static String username = "gaochao0073@163.com"; 

	private static String password = "gaochao123"; 

	private static String from = "gaochao0073@163.com"; // 使用当前账户
	public static String activeUrl = "http://localhost:8082/bos_fore/customerAction_activeMail";

	public static void sendMail(String subject, String content, String to) {
		System.out.println("da lang he le zhe wan yao ba");
		System.out.println("待我兄弟武松回来");
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", smtp_host);
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.auth", "true");
		Session session = Session.getInstance(props);
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(from));
			message.setRecipient(RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setContent(content, "text/html;charset=utf-8");
			Transport transport = session.getTransport();
			transport.connect(smtp_host, username, password);
			transport.sendMessage(message, message.getAllRecipients());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("邮件发送失败...");
		}
	}

	public static void main(String[] args) {
		sendMail("测试邮件", "你好，传智播客", "itcast_server@sina.com");
	}
}
