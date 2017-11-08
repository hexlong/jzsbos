package cn.itcast.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.take_delivery.OrderDao;
import cn.itcast.bos.domain.base.Order;
import cn.itcast.bos.service.base.OrderServicework;

@Service
@Transactional
public class OrderServiceworkImpl implements OrderServicework {

	@Autowired
	private OrderDao dao;

	@Override
	public List<Order> findByWorkBillIsNull() {
		
		return dao.findByWorkBillIsNull();
	}
	
}
