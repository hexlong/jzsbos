package cn.itcast.bos.service.base;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.SubArea;

public interface SubAreaService {

	void save(SubArea modle);

	Page<SubArea> findAllByPage(Pageable pageable);

	List<SubArea> findAll();

	void export(FileInputStream in, ServletOutputStream out);

	List showChart();

	void uploadExl(FileInputStream in);

}
