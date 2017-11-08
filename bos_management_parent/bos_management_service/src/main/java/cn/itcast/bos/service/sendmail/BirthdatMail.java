package cn.itcast.bos.service.sendmail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.bos.utils.MailUtils;
import cn.itcast.crm.service.Customer;
import cn.itcast.crm.service.impl.CustomerService;

public class BirthdatMail {

//	注入代理对象
	@Autowired
	private CustomerService cproxy;
	
	public void sendMail(){
		//需求是每天查询数据库发送生日祝福
		//先查询crm数据库用户的生日调用crm,返回list集合然后遍历发送邮件
		List<Customer> list = cproxy.findByCustomerBirthday();
		for (Customer customer : list) {
			//执行发送邮件的方法
			
			MailUtils.sendMail("生日祝福", "亲爱的"+customer.getUsername()+"用户:，今天是您的生日，祝您生日快乐，再忙也要照顾好自己！", customer.getEmail());
		}
		
		
	}
}
