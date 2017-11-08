package cn.itcast.bos.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Standard;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

//多行重复代码的抽取；
	
	//要new一个实例对象
	protected T modle;
	@Override
	public T getModel() {
		// TODO Auto-generated method stub
		return modle;
	}

	public BaseAction(){
//		this.getClass()是子类的action  cn.itcast.bos.action.base.AreaAction
		Type type = this.getClass().getGenericSuperclass();//父类的参数化类型
		//这是Type的子类来获取泛型对象的数组
		ParameterizedType ptype=(ParameterizedType)type;
		Type[] types = ptype.getActualTypeArguments();
		//因为只获取数组中的一个对象说以直接【0】就可以,并强转成<T>类型
		Class<T> class1 = (Class<T>) types[0];
		//然后实例化对象
		try {
			modle=class1.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected  int page;
	protected int rows;
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	//写回页面map 分页查询所有的方法，排除属性
	public void javaToJson( Map map,String []names){	
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		JsonConfig jsonConfig = new  JsonConfig();
		jsonConfig.setExcludes(names);
		String string = JSONObject.fromObject(map,jsonConfig).toString();
		try {
			response.getWriter().write(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	
	//写回页面map 分页查询所有的方法，排除属性
		public void javaToJson(Object obj,String []names){	
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/json;charset=utf-8");
			JsonConfig jsonConfig = new  JsonConfig();
			jsonConfig.setExcludes(names);
			String string = JSONObject.fromObject(obj,jsonConfig).toString();
			try {
				response.getWriter().write(string);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
	
	//写回页面map 分页查询所有的方法
	public void javaToJson(Map map){	
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		//total和rows是页面需要的固定参数
		
		String string = JSONObject.fromObject(map).toString();
		try {
			response.getWriter().write(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	//写回页面list 查询所有的方法
	public void javaToJson( List list,String[] names){	
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(names);
		String string = JSONArray.fromObject(list,jsonConfig).toString();
		try {
			response.getWriter().write(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	
	//保存的方法
	public void saveAjax(Boolean success,String message,Map map){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		map.put("success", success);
		map.put("message", message);
//		把json字符串回写到浏览器上
		String string = JSONObject.fromObject(map).toString();
		try {
			response.getWriter().print(string);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void javaToAjax(Boolean success,String message){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("success", success);
		map.put("message", message);
//		把json字符串回写到浏览器上
		String string = JSONObject.fromObject(map).toString();
		try {
			response.getWriter().print(string);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
