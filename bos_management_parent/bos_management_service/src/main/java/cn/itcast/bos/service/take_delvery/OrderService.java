package cn.itcast.bos.service.take_delvery;

import java.util.List;

import javax.jws.WebService;

import cn.itcast.bos.domain.base.Order;

@WebService
public interface OrderService {

	void save(Order order);

	
}
