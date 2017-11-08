package cn.itcast.bos.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
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

import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.service.base.SubAreaService;
import cn.itcast.bos.utils.DownLoadUtils;

@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class SubAreaAction extends BaseAction<SubArea> {

	@Autowired
	private SubAreaService service;
	
	//保存
	@Action("sub_areaAction_save")
	public void save(){
		HashMap<String, Object> map = new HashMap<String,Object>();
		try{
			service.save(modle);
			saveAjax(true, "添加成功", map);
		}catch(Exception e){
			e.printStackTrace();
			saveAjax(false, "添加失败", map);
		}
	}
	//分页
	@Action("sub_areaAction_findAll")
	public void findAllByPage(){
		Pageable pageable=new PageRequest(page-1, rows);
		Page<SubArea> page=service.findAllByPage(pageable);
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
//		subareas  couriers
		javaToJson(map, new String[]{"subareas","couriers"});
	}
	//查所有
	@Action("subAreaAction_findAll")
	public void findAll(){
		List<SubArea> list=service.findAll();
		javaToJson(list, new String[]{"subareas","couriers"});
//		subareas  couriers
	}
	//导出excle
	@Action("subAreaAction_export")
	public void export() throws Exception{
		 HttpServletRequest request = ServletActionContext.getRequest();
		 HttpServletResponse response = ServletActionContext.getResponse();
		 //设置中文乱码等，一个流两个头  1.mime 2.打开方式 inline  attachment
		 String agent = request.getHeader("User-Agent");
		 String fileName = DownLoadUtils.getName(agent, "分区表格.xlsx");
		 //                 设置头信息                                     重新定义打开位置（下载）；+文件名=编码后文件名
		 response.setHeader("content-disposition", "attachment;fileName="+fileName);
		 //通过响应获取输出流
		 ServletOutputStream out = response.getOutputStream();
		 //获取项目路径+模板路径  File.separtor
		 String path=ServletActionContext.getServletContext().getRealPath(File.separator)+"template"+File.separator+"分区表格.xlsx";
		 //获取一个输入流读取模板
		 FileInputStream in= new FileInputStream(path);
		 //调用service
		 service.export(in,out);
	}
	
	@Action("subAreaAction_showChart")
	public void showChart(){
		//调用service
		List list=service.showChart();
		javaToJson(list, null);
	}
	private File subFile;
	public void setSubFile(File subFile) {
		this.subFile = subFile;
	}
	//导入excel
	@Action("subAreaAction_uploadExl")
	public void uploadExl(){
		try {
			FileInputStream in = new FileInputStream(subFile);
			service.uploadExl(in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
