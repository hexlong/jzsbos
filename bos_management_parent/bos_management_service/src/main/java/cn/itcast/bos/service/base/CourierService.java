package cn.itcast.bos.service.base;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.Courier;


public interface CourierService {

	void save(Courier courier);

	Page<Courier> findByPage(Pageable pageable);

	void delete(String ar);

	Page<Courier> findByPage(Specification specification, Pageable pageable);

	List<Courier> findByDeltagIsNull();

	void update(String ar);

}
