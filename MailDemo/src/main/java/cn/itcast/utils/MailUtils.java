package cn.itcast.utils;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailUtils {

	private JavaMailSender javaMailSender;
//	set属性注入到spring管理
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	//发送方法
	public void sendMain(String from,String to, String subject,String contans){
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(message);
		
		try {
//		发送方"gaochao0073@163.com"
			helper.setFrom(from);
//			接收方"itcast_server@sina.com"
			helper.setTo(to);
//			主题"spring整合发送邮件"
			helper.setSubject(subject);
//			内容"这是我邮件的内容"
			helper.setText(contans);
			javaMailSender.send(message);

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//多媒体附件
	public void sendMain2(String from,String to, String subject,String contans,String filename,File file){
		MimeMessage message = javaMailSender.createMimeMessage();
		
		try {
			MimeMessageHelper helper=new MimeMessageHelper(message,true);
//		发送方"gaochao0073@163.com"
			helper.setFrom(from);
//			接收方"itcast_server@sina.com"
			helper.setTo(to);
//			主题"spring整合发送邮件"
			helper.setSubject(subject);
//			内容"这是我邮件的内容"
			helper.setText(contans);
			//要传递的附件
			helper.addAttachment(filename, file);
			
			
			javaMailSender.send(message);

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//多媒体附件内容带有图片
		public void sendMain3(String from,String to, String subject,String contans,File file){
			MimeMessage message = javaMailSender.createMimeMessage();
			
			try {
				//发送的邮件种有多媒体文件设置为true
				MimeMessageHelper helper=new MimeMessageHelper(message,true);
//			发送方"gaochao0073@163.com"
				helper.setFrom(from);
//				接收方"itcast_server@sina.com"
				helper.setTo(to);
//				主题"spring整合发送邮件"
				helper.setSubject(subject);
//				内容"这是我邮件的内容"
//				helper.setText(contans);
				helper.setText("<img src='cid:xxCid'>", true);
				helper.addInline("xxCid", file);
				//要传递的附件
//				helper.addAttachment(filename, file);
				
				
				javaMailSender.send(message);

			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
