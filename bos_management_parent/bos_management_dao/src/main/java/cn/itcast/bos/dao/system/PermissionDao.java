package cn.itcast.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.system.Permission;

public interface PermissionDao extends JpaRepository<Permission, Integer> {

//	@Query(value="select distinct p.* from t_permission p,t_role_permission rp,t_role r,t_user_role ur, t_user u "+
//	"where p.c_id=rp.c_permission_id and rp.c_role_id=r.c_id " +
//	"and r.c_id=ur.c_role_id and ur.c_user_id=u.c_id	 and u.c_id=?",nativeQuery=true)


	
	@Query("select distinct p from Permission p join p.roles r join r.users u where u.id=?")
	List<Permission> findByUser(Integer id);
	
//	select t.*, t.rowid from T_PERMISSION t ,T_ROLE_PERMISSION  r
//	WHERE  r.c_permission_id=t.c_id and r.c_role_id=221;
	@Query("select distinct p from Permission p join p.roles r  where r.id=?")
	List<Permission> findByRoleId(Integer id);

}
