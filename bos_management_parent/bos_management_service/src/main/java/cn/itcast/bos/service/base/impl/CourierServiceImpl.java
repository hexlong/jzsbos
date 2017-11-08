package cn.itcast.bos.service.base.impl;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierDao;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;
@Service
@Transactional
public class CourierServiceImpl implements CourierService {

	@Autowired
	private CourierDao dao;
	@Override
	public void save(Courier courier) {
		// TODO Auto-generated method stub
		dao.save(courier);
	}
	@Override
	public Page<Courier> findByPage(Specification specification,Pageable pageable) {
		// TODO Auto-generated method stub
		return dao.findAll(specification, pageable);
	}
	@Override
//	@RequiresPermissions("courier-delete")
	public void delete(String ar) {
		// 这里传过来的ar是一个字符串，所以要不他切割成一个个id
		//用一个数组来装他，并循环遍历
		String [] ars=ar.split(",");
		for (String st : ars) {
			//因为id是int类型所以字符串要转
			
			dao.updateDeltag(Integer.parseInt(st));
		}
		
	}
	@Override
	public Page<Courier> findByPage(Pageable pageable) {
		// TODO Auto-generated method stub
		return dao.findAll(pageable);
	}
	@Override
	public List<Courier> findByDeltagIsNull() {
		// TODO Auto-generated method stub
		return dao.findByDeltagIsNull();
	}
	@Override
	public void update(String ar) {
		String[] ids = ar.split(",");
		for (String id : ids) {
			dao.updateDeltags( Integer.parseInt(id));;
		}
	}


}
