package cn.itcast.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.MenuDao;
import cn.itcast.bos.dao.system.PermissionDao;
import cn.itcast.bos.dao.system.RoleDao;
import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao dao;
	@Autowired
	private PermissionDao pdao;
	@Autowired
	private MenuDao mdao;

	@Override
	public List<Role> findAll() {
		return dao.findAll();
	}

	@Override
	public void save(Role role, String menuData, String permissionData) {
				
		//保存数据
		Role role2 = dao.save(role);
		//分析角色和权限之间的关系，进行关联
		if(permissionData!=null&&!permissionData.equals("")){
			//把字符串切割
			String[] pids = permissionData.split(",");
			for (String pe : pids) {
				//操作实体类要先查询到对象
				Permission permission = pdao.findOne(Integer.parseInt(pe));
				//role2.getpermission是获取了set集合再 add添加每个对象关联角色role
				role2.getPermissions().add(permission);
			}
		}
		if(menuData!=null&&!menuData.equals("")){
			//把字符串切割
			String[] menuids = menuData.split(",");
			for (String menuid : menuids) {
				//操作实体类要先查询到对象
				Menu menu = mdao.findOne(Integer.parseInt(menuid));
				role2.getMenus().add(menu);
			}
		}
		
	}

	//通过用查询角色
	@Override
	public List<Role> findByUser(User user) {
//		先判断是否是超级管理员
		if(user.getUsername().equals("admin")){
//			是超级管理员就给他所有的角色
			return dao.findAll();
		}
		//不是超级管理员就通过用户的id关联其他表查出所对应的角色
		
		return dao.findByUser(user.getId());
	}

	//角色修改
	@Override
	public void update(Role modle) {
		// TODO Auto-generated method stub
		
	}

//	通过id查询角色，用户，菜单，权限信息
	@Override
	public Role findByRoleId(Integer id) {
		Role role = dao.findOne(id);
//		//查询权限
//		List<Permission> plist=pdao.findByRoleId(id);
//		for (Permission permission : plist) {
//			role.getPermissions().add(permission);
//		}
//		//查询菜单
//		List<Menu> list =mdao.findRoleId(id);
//		for (Menu menu : list) {
//			role.getMenus().add(menu);
//		}
		return role;
	}

	@Override
	public List<Role> findByUser(int id) {
		
		return dao.findByUser(id);
	}
}
