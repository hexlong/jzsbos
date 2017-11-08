package cn.itcast.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.webxml.MobileCodeWSSoap;

public class PhoneTest {

	public static void main(String[] args) {
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		MobileCodeWSSoap bean = (MobileCodeWSSoap) app.getBean("phoneTest");
		String info = bean.getMobileCodeInfo("17710525357", null);
		System.out.println(info);
	
		
	}
}
