package cn.itcast.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.PermissionDao;
import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.PermissionService;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionDao dao;

	@Override
	public List<Permission> findAll() {
		
		return dao.findAll();
	}

	@Override
	public void save(Permission modle) {
		dao.save(modle);
		
	}

	@Override
	public List<Permission> findByUser(User user) {
		//判断是否是超级管理员
		if(user.getUsername().equals("admin")){
			//是超级管理元就赋予他所有权限
			return dao.findAll();
		}
		//不是超级管理员就需要关联各表通过用户下角色表权限表查询所拥有的权限
		
		return dao.findByUser(user.getId());
	}

	@Override
	public List<Permission>  findByRoleId(String rid) {
		return dao.findByRoleId(Integer.parseInt(rid));
		
	}
}
