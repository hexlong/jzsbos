package cn.itcast.bos.action.base;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@ParentPackage("struts-default")
@Namespace("/")
@Controller("standardAction")
@Scope("prototype")
public class StandardAction extends BaseAction<Standard> {

	
	
	@Autowired
	private StandardService standarService;
	
	//保存
	@Action(value="standardAction_save")
	public void save() throws IOException{
//		HttpServletResponse response = ServletActionContext.getResponse();
//		data {"success":true|false,"message":"保存成功"|"保存失败"}
		Map<String,Object> map = new HashMap<String,Object>();
//		response.setContentType("application/json;charset=utf-8");
//		System.out.println("访问到我了");
		try{
			standarService.save(modle);
			saveAjax(true, "操作成功", map);
//			map.put("success", true);
//			map.put("message", "操作成功");
//			把json字符串回写到浏览器上
//			String msg = JSONObject.fromObject(map).toString();
//			response.getWriter().print(msg);
		}catch(Exception e){
			saveAjax(false, "操作失败", map);
//			map.put("success", false);
//			map.put("message", "操作失败");
//			把json字符串回写到浏览器上
//			String msg = JSONObject.fromObject(map).toString();
//			response.getWriter().print(msg);
		}
	}
	/*
	 * 分页查询
	 * 1接受页面数据当前页面，页面显示条数
	 * 2通过框架查询分页数据
	 * 3给页面传回json数据
	 * * */
//	private int page;
//	private int rows;
//	public void setPage(int page) {
//		this.page = page;
//	}
//	public void setRows(int rows) {
//		this.rows = rows;
//	}
//分页
	@Action("standardaAction_ByPage")
	public void findByPage(){
		//利用pageable的实现类pagerequest和findall查询数据
		Pageable pageable=new PageRequest(page-1,rows);
		Page<Standard> page=standarService.findByPage(pageable);
//		//获取response对象
//		HttpServletResponse response = ServletActionContext.getResponse();
//		response.setContentType("application/json;charset=utf-8");
		//response返回json数据
//		页面上需要的数据格式是 {"total":100,"rows":[{},{}]}所以new一个map
		Map<String,Object> map = new HashMap<String,Object>();
		//total和rows是页面需要的固定参数
		map.put("total", page.getTotalElements());//页面总条数
		map.put("rows", page.getContent());//是查询后的数据list集合
		javaToJson(map);
		//然后把map转化为json数据返回页面
//		String string = JSONObject.fromObject(map).toString();
		
//			response.getWriter().write(string);
	}
	@Action("standardAction_findAll")
	public void findAll(){
		//查询所有standard的收派标准调用service层
		List<Standard> list=standarService.findAll();
		javaToJson(list,null);
		//把数据转化为json格式写回给页面
//		String string = JSONArray.fromObject(list).toString();
//		//获取response对象
//		HttpServletResponse response = ServletActionContext.getResponse();
//		response.setContentType("application/json;charset=utf-8");
//			response.getWriter().write(string);
	}
	
}
