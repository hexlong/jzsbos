package cn.itcast.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.Courier;

public interface CourierDao extends JpaRepository<Courier, Integer>,JpaSpecificationExecutor<Courier> {

	//因为是自定义的方法所以要家注解query和modifying
	//语句为jbql语句 对应实体类字段的sql语句
	@Query("update Courier set deltag=1 where id=?")
	@Modifying
	void updateDeltag(int parseInt);

	List<Courier> findByDeltagIsNull();

	@Query("update Courier set deltag=null where id=?")
	@Modifying
	void updateDeltags(int parseInt);

}
