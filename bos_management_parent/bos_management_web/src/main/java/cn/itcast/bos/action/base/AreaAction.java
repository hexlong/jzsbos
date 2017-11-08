package cn.itcast.bos.action.base;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.AreaService;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@ParentPackage("struts-default")
@Namespace("/")
@Controller("areaAction")
@Scope("prototype")
public class AreaAction extends BaseAction<Area> {

	//重复的很多的代码抽取modeldrven
	@Autowired
	private AreaService service;
	private File myFile;
	
	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}
	@Action("areaAction_uploadExl")
	public void uploadExl() throws IOException{
		System.out.println(myFile);
		FileInputStream fileInputStream = new FileInputStream(myFile);
		service.importExl(fileInputStream);
	}
	//重复很多的代码
	@Action("areaAction_findAllByPage")
	public void findAllByPage(){
		Pageable pageable=new PageRequest(page-1,rows);
		Page<Area> pages=service.findAllByPage(pageable);
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("total", pages.getTotalElements());//页面总条数
		map.put("rows", pages.getContent());//是查询后的数据list集合
		javaToJson(map, new String[]{"subareas"});
	}
	
	private String q;
	
	public void setQ(String q) {
		this.q = q;
	}
	@Action("areaAction_findAll")
	public void findAll(){
		List<Area> list=service.findAll(modle,q);
		javaToJson(list,new String[]{"subareas"});
	}
}
