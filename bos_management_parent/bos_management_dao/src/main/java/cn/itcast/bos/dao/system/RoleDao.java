package cn.itcast.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.system.Role;

public interface RoleDao extends JpaRepository<Role, Integer> {
//	select r.* from t_role r,t_user u,t_user_role ur
//	where r.c_id=ur.c_role_id and u.c_id=ur.c_user_id
//	 and u.c_id=89
//sql语句这里要用本地查询就要加属性nativequery=true
//	@Query(value="select r.* from t_role r,t_user u,t_user_role ur where "
//	+ "r.c_id=ur.c_role_id and u.c_id=ur.c_user_id and u.c_id=?",nativeQuery=true)

	
	@Query("select r from Role r join r.users u  where u.id=?")  //jpql
	List<Role> findByUser(Integer id);

}
