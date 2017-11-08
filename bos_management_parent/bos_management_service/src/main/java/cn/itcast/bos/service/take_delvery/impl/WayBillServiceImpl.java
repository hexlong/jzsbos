package cn.itcast.bos.service.take_delvery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.take_delivery.OrderDao;
import cn.itcast.bos.dao.take_delivery.WayBillDao;
import cn.itcast.bos.domain.base.WayBill;
import cn.itcast.bos.service.take_delvery.WayBillService;

@Service("wayBillService")
@Transactional
public class WayBillServiceImpl implements WayBillService {

	@Autowired
	private WayBillDao dao;
	@Autowired
	private OrderDao odao;

	@Override
	public void save(WayBill modle) {
		dao.save(modle);
		
	}

	
}
