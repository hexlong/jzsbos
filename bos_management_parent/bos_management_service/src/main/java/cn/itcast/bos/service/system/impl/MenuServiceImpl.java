package cn.itcast.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.MenuDao;
import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.MenuService;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao dao;

	@Override
	public List<Menu> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public List<Menu> findByPidIsNull() {
		// TODO Auto-generated method stub
		return dao.findByParentMenuIsNull();
	}

	@Override
	public void save(Menu modle) {
		dao.save(modle);
		
	}

	@Override
	public List<Menu> findByUser(User user) {
		//判断是否是超级管理员
		if(user.getUsername().equals("admin")){
			return dao.findAll();
		}
		return dao.findByUser(user.getId());
		
	}

	@Override
	public List<Menu> findByRoleId(String rid) {
		 return dao.findRoleId(Integer.parseInt(rid));
		
	}
}
