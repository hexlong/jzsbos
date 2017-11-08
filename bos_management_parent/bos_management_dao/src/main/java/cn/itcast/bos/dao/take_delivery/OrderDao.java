package cn.itcast.bos.dao.take_delivery;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.Order;

public interface OrderDao extends JpaRepository<Order, Integer> {
//	select t.* from T_ORDER t ,t_work_bill w 
//	where t.c_id != w.c_order_id
	
	@Query(value="select t.*, t.rowid from T_ORDER t where  t.c_courier_id is null",nativeQuery=true)
	List<Order> findByWorkBillIsNull();

	Order findById(Integer id);

}
