package cn.itcast.crm.service;

import java.util.List;

import javax.jws.WebService;

import cn.itcast.crm.domain.Customer;

@WebService
public interface CustomerService {

	List<Customer> findByFixedAreaIdIsNull();
	List<Customer> findByFixedAreaId(String fixedAreaId);
	void updateFixedAreaId(String fixedAreaId, String customerIds);
	Customer findTelephone(String telephone);
	void regist(Customer customer);
	Customer findByEmailAndPassword(String email, String password);
	Customer findByTelephoneAndPassword(String telephone, String password);
	void active(String telephone);
	String findByAddress(String address);
	List<Customer> findByCustomerBirthday();
}
