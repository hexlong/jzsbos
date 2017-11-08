package cn.itcast.demo;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.utils.MailUtils;

public class SendMailDemo {

	public static void main(String[] args) {
		ApplicationContext app=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		MailUtils mailutils = (MailUtils) app.getBean("mailUtils");
//		mailutils.sendMain("gaochao0073@163.com", "18634268117@163.com", "哈哈哈哈", "来了来了");

//		mailutils.sendMain2("gaochao0073@163.com", "18634268117@163.com",
//				"带有附件的", "这是内容","北京传智JavaEE就业264期（20170425面授）.xls",new File("F:\\web就业视频\\北京传智JavaEE就业264期（20170425面授）.xls"));

		mailutils.sendMain3("gaochao0073@163.com", "18634268117@163.com", "spring整合mail", "这是文字内容",new File("F:\\图片\\2005415161043190.jpg"));
	}
}
