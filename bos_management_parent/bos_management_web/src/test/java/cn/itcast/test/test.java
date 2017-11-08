package cn.itcast.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.bos.dao.base.StandardDao;
import cn.itcast.bos.domain.base.Standard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class test {
	
	@Autowired
	private StandardDao dao;
	@Test
	public void t1(){
		Standard standard = new Standard();
		standard.setMinLength(123);
		standard.setMaxWeight(12);
		standard.setMaxLength(322);
		standard.setOperator("abc");
		Standard sss = dao.save(standard);
		System.out.println(sss);
	}
	
	@Test
	public void findAll(){
		Standard standard = new Standard();
		standard.setMinLength(123);
		standard.setMaxWeight(12);
		standard.setMaxLength(322);
		standard.setOperator("abc");
		List<Standard> list = dao.findAll();
		System.out.println(list);
	}
}
