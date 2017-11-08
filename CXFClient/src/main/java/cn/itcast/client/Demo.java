package cn.itcast.client;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

import cn.itcast.cxfserver.WeatherService;

public class Demo {

	public static void main(String[] args) {
		//加载spring容器
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		//在容器中取对象通过getbean
		WeatherService weatherService = (WeatherService) app.getBean("weatherService");
		String infoByCityName = weatherService.getInfoByCityName("北京");
		System.out.println(infoByCityName);
		String infoByCityName2 = weatherService.getInfoByCityName("天津");
		System.out.println(infoByCityName2);

	
	}
}
