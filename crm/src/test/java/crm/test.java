package crm;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;
import cn.itcast.crm.service.impl.CustomerServiceImpl;


public class test {
	
//	@Autowired
	CustomerService service=new CustomerServiceImpl();
	
	@Test
	public void t1(){
		 service.findByFixedAreaIdIsNull();
//		 service.findByFixedAreaId("11");
	
	}
}
