package cn.itcast.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.RoleDao;
import cn.itcast.bos.dao.system.UserDao;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;
	@Autowired
	private RoleDao rdao;

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		
		return dao.findByUsernameAndPassword(username,password);
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public void save(User modle, String rolesIds) {
		User user = dao.save(modle);
		//判断rolesIds是否为空
		if(rolesIds!=null&&!rolesIds.equals("")){
			//字符串切割
			String[] rolesId = rolesIds.split(",");
			for (String id : rolesId) {
				//查询单个role对象
				Role role = rdao.findOne(Integer.parseInt(id));
				user.getRoles().add(role);
			}
		}
		
	}

	@Override
	public User findByUserId(String id) {
		
		return dao.findOne(Integer.parseInt(id));
	}
}
