package cn.itcast.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.SubArea;

public interface SubAreaDao extends JpaRepository<SubArea, String> {
//	select a.c_district, count(*) from T_SUB_AREA t ,t_area a  
//	where t.c_area_id=a.c_id group by a.c_district
	@Query("select a.district,count(*) from SubArea s join s.area a group by a.district")
	List showChart();


	@Query("update SubArea set fixedArea=null where fixedArea=? ")
	@Modifying
	void update(FixedArea modle);

}
