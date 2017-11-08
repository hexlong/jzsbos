package cn.itcast.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.crm.dao.CustomerDao;
import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerDao dao;
	
	//查询定区ID是空的客户（关联定区）
	public List<Customer> findByFixedAreaIdIsNull(){
		
		List<Customer> list = dao.findByFixedAreaIdIsNull();
		return list;
	}


	@Override
	//查询定区id的客户(指定定区)
	public List<Customer> findByFixedAreaId(String fixedAreaId) {
		
		
		return dao.findByFixedAreaId(fixedAreaId);
	}
	//通过定区id更改客户的关联定区
	public void updateFixedAreaId(String fixedAreaId, String customerIds){
		//****先清空所选中的所有用户的关联定区id，都改为null
		dao.updateFixedAreaIdIsNull(fixedAreaId);
		//然后统一再修改为选中的定区和关联的客户
		//由于是多个客户所以传递的字符串要切割成数组然后循环遍历再修改
		String[] ids = customerIds.split(",");
		for (String id : ids) {
			System.out.println(id);
			dao.updateFixedAreaId(fixedAreaId,Integer.parseInt(id));
		}
	}
	
	//注册用户
	public void regist(Customer customer){
		dao.save(customer);
	}
	
	//查询手机是与否被注册
	public Customer findTelephone(String telephone){
		Customer customer=dao.findByTelephone(telephone);
		return customer;
	}
	//邮箱激活用户
	public void active(String telephone){
		dao.updateType(telephone);
	}
	/*登陆时查询手机号和邮箱号
	 * 
	 * 1.查询手机号
	 * 2.查询邮箱号
	 */
	public Customer findByTelephoneAndPassword(String telephone, String password) {
		return dao.findByTelephoneAndPassword(telephone,password);
	}
	public Customer findByEmailAndPassword(String email, String password) {
		return 	dao.findByEmailAndPassword(email,password);
	}
	
	/*
	 * 通过地址查询对象获取
	 * 
	 */
	public String findByAddress(String address){
		
		List<Customer> list=dao.findByAddress(address);
		if(list!=null&&list.size()>0){
			
			return list.get(0).getFixedAreaId();
		}
		return null;
	}
	
//	查找当前日期是用户生日的用户
	public List<Customer> findByCustomerBirthday(){
		List<Customer> list=dao.findByBirthDay();
		
		return list;
	}
}
