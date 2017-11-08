package cn.itcast.bos.service.system;

import java.util.List;

import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;

public interface RoleService {

	List<Role> findAll();

	void save(Role modle, String menuData, String permissionData);

	List<Role> findByUser(User user);

	void update(Role modle);

	Role findByRoleId(Integer integer);

	List<Role> findByUser(int id);

}
