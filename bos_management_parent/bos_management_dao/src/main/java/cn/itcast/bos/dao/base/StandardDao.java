package cn.itcast.bos.dao.base;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.bos.domain.base.Standard;





public interface StandardDao extends JpaRepository<Standard, Integer> {

	List<Standard> findAll();


	
}
