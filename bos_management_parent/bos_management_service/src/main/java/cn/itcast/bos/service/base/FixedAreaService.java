package cn.itcast.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.FixedArea;

public interface FixedAreaService {

	void save(FixedArea modle, String data);

	Page<FixedArea> findAllByPage(Pageable pageable);

	void save(String id, Integer courierId, Integer takeTimeId);

}
