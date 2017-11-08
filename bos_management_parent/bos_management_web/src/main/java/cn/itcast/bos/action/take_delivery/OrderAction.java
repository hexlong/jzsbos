package cn.itcast.bos.action.take_delivery;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.action.base.BaseAction;
import cn.itcast.bos.domain.base.Order;
import cn.itcast.bos.service.base.OrderServicework;

@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class OrderAction extends BaseAction<Order> {

	@Autowired
	private OrderServicework service;
	
	@Action("orderAction_findAll")
	public void findAll(){
		
		List<Order> list=service.findByWorkBillIsNull();
		javaToJson(list, new String[]{"workBills","wayBill","courier","recArea","sendArea"});
	}
}
