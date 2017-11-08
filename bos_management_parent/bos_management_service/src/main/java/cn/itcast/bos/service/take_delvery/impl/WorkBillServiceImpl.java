package cn.itcast.bos.service.take_delvery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierDao;
import cn.itcast.bos.dao.take_delivery.OrderDao;
import cn.itcast.bos.dao.take_delivery.WorkBillDao;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Order;
import cn.itcast.bos.domain.base.WayBill;
import cn.itcast.bos.domain.base.WorkBill;
import cn.itcast.bos.service.take_delvery.WorkBillService;

@Service
@Transactional
public class WorkBillServiceImpl implements WorkBillService  {

	@Autowired
	private WorkBillDao wdao;
	@Autowired
	private OrderDao odao;
	@Autowired
	private CourierDao cdao;
	
	@Override
	public void saveWorkBill(WorkBill modle, Integer id) {
		WorkBill workBill = wdao.save(modle);
		Order order=odao.findById(id);
		Courier courier = cdao.findOne(modle.getCourier().getId());
		order.setCourier(courier);
		workBill.setOrder(order);
	}
}
