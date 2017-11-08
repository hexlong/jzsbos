package cn.itcast.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.system.Menu;

public interface MenuDao extends JpaRepository<Menu, Integer> {

	List<Menu> findByParentMenuIsNull();

	@Query("select distinct m from Menu m join m.roles r join r.users u where u.id=?")  //jpql  hql
	List<Menu> findByUser(Integer id);
	
//	select t.*, t.rowid from T_MENU t , t_role_menu m 
//	where t.c_id=m.c_menu_id and m.c_role_id=261;
	
	@Query("select distinct m from Menu m join m.roles r where r.id=?")
	List<Menu> findRoleId(Integer id);

}
