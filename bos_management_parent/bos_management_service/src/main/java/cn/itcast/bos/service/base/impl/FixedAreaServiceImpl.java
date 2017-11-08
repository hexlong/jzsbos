package cn.itcast.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierDao;
import cn.itcast.bos.dao.base.FixedAreaDao;
import cn.itcast.bos.dao.base.SubAreaDao;
import cn.itcast.bos.dao.base.TakeTimeDao;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.FixedAreaService;

@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {

	@Autowired
	private FixedAreaDao dao;
	
	@Autowired
	private SubAreaDao sdao;
	
	@Autowired
	private CourierDao cdao;
	
	@Autowired
	private TakeTimeDao tdao;


	@Override
	public void save(FixedArea modle,String data) {
		modle = dao.save(modle);
		//定区和分区是一对多的关系，在分区的一方维护外键，所以在分区一方插入数据
		//切割data获取id
		sdao.update(modle);
		SubArea subArea =null;
		String[] ids = data.split(",");
		for (String sid : ids) {
			 subArea = sdao.findOne(sid);
			subArea.setFixedArea(modle);
//			sdao.save(subArea);
		}
		
	}

	@Override
	public Page<FixedArea> findAllByPage(Pageable pageable) {
		// TODO Auto-generated method stub
		return dao.findAll(pageable);
	}

	@Override
	public void save(String id, Integer courierId, Integer takeTimeId) {
//		1、定区和快递员的中间表会多出一条数据
//		fixedArea  维护方
		FixedArea fixedArea = dao.findOne(id);
		Courier courier = cdao.findOne(courierId);
		fixedArea.getCouriers().add(courier);//相当于insert
		
//		2、快递员的taketimeid字段会被修改
		TakeTime takeTime = tdao.findOne(takeTimeId);
		courier.setTakeTime(takeTime);
		
	}
}
