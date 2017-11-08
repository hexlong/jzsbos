package cn.itcast.bos.service.base;

import java.util.List;

import cn.itcast.bos.domain.base.Order;

public interface OrderServicework {

	List<Order> findByWorkBillIsNull();
}
