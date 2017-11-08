package cn.itcast.bos.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@ParentPackage("struts-default")
@Namespace("/")
@Controller("courierAction")
@Scope("prototype")
public class CourierAction extends ActionSupport implements ModelDriven<Courier> {

	Courier courier =new Courier();
	@Override
	public Courier getModel() {
		// TODO Auto-generated method stub
		return courier;
	}
	//注入dao
	@Autowired
	private CourierService service;
	
//	保存
	@Action(value="courierAction_save")
	public void save() throws IOException{
		
		HttpServletResponse response = ServletActionContext.getResponse();
//		data {"success":true|false,"message":"保存成功"|"保存失败"}
		Map<String,Object> map = new HashMap<String,Object>();
		response.setContentType("application/json;charset=utf-8");
//		System.out.println("访问到我了");
		try{
			service.save(courier);
			map.put("success", true);
			map.put("message", "操作成功");
//			把json字符串回写到浏览器上
			String msg = JSONObject.fromObject(map).toString();
			response.getWriter().print(msg);
		}catch(Exception e){
			
			map.put("success", false);
			map.put("message", "操作失败");
//			把json字符串回写到浏览器上
			String msg = JSONObject.fromObject(map).toString();
			response.getWriter().print(msg);
		}
	}
	/*提供page属性和rows的set方法
	 * 
	 * */
	private int page;
	private int rows;
	
public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	//	分页查询全部
	@Action("courierAction_findByPage")
	public void findByPage() throws IOException{
		//获取response对象写回json数据
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
//		Specification级别相当于离线查询的方式DetachedCriteria
//		创建时new一个内部匿名类
//		DetachedCriteria dc =DetachedCriteria.forClass(Courier.class);
//		dc.add(Restrictions.like("name", "%"+courier.getName()+"%"));
		Specification<Courier> specification =new Specification<Courier>() {
			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				root 看作查询的主体对象courier  
//				cb相当于Restrictions，用来组装条件的
//				避免只执行一个查询条件，创建一个集合装条件
				ArrayList<Predicate> list = new ArrayList<Predicate>();
//				model.getCourierNum();工号
				if(StringUtils.isNotBlank(courier.getCourierNum())){
					Predicate predicate1 = cb.like(root.get("courierNum").as(String.class), "%"+courier.getCourierNum()+"%");
					list.add(predicate1);
				}
				//name姓名
				if(StringUtils.isNotBlank(courier.getName())){
					Predicate predicate = cb.like(root.get("name").as(String.class), "%"+courier.getName()+"%");
					list.add(predicate);
				}
				//telephone电话
				if(StringUtils.isNotBlank(courier.getTelephone())){
					Predicate predicate5 = cb.like(root.get("telephone").as(String.class), "%"+courier.getTelephone()+"%");
					list.add(predicate5);
				}
				
//				model.getCompany(); 公司
				if(StringUtils.isNotBlank(courier.getCompany())){
					Predicate predicate2 = cb.like(root.get("company").as(String.class), "%"+courier.getCompany()+"%");
					list.add(predicate2);
				}
//				model.getType();类型
				if(StringUtils.isNotBlank(courier.getType())){
					Predicate predicate3 = cb.like(root.get("type").as(String.class), "%"+courier.getType()+"%");
					list.add(predicate3);
				}
//				model.getStandard().getName() 收派标准的名称这是一个对象中还存在一个对象的数据
				if(courier.getStandard()!=null&& StringUtils.isNotBlank(courier.getStandard().getName())){
	//				类似于	关联查询   select * from courier c inner join standard s where c.standardID=s.id where s.name=?
					Join<Object, Object> join = root.join("standard");//join来当作Standard对象
					Predicate predicate4 = cb.like(join.get("name").as(String.class),"%"+courier.getStandard().getName()+"%");
					list.add(predicate4);
				}
//				System.out.println(list.size());
//				这里判断条件是否为空，空就不做任何条件查询返回null
				if(list.size()==0){
					return null;
				}
//				集合转成对象数组需要new一个对象数组，长度为集合的长度
				Predicate[] predicates = new Predicate[list.size()];
//				把list转成数组再转成一个对象数组
				predicates = list.toArray(predicates);

				return cb.and(predicates);
			}
		};
		Pageable pageable=new PageRequest(page-1,rows);
		//查询到的数据封装到page中所以用page接受
		Page<Courier> page=service.findByPage(specification,pageable);
		//查到数据封装到map集合写回页面
		//response返回json数据
//		页面上需要的数据格式是 {"total":100,"rows":[{},{}]}所以new一个map
		Map<String, Object> map = new HashMap<String,Object>();
		//封装总页数
		map.put("total", page.getTotalElements());
		//封装查询到的数据
		map.put("rows", page.getContent());
		//这里涉及多个对象使用了延迟加载（懒加载）重新查询数据时session关闭而没有对象
		//要排除一个不需要的字段封装json
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"fixedAreas"});
//		写回页面
		String string = JSONObject.fromObject(map, jsonConfig).toString();
		response.getWriter().write(string);
	}
	
	private String ar;
	
	//逻辑删除
	public void setAr(String ar) {
		this.ar = ar;
	}

	@Action("courierAction_delete")
	public void delete() throws IOException{
		//获取response对象写回json数据
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			service.delete(ar);
			map.put("success", true);
			map.put("message", "操作成功");
	//				写回页面
			String string = JSONObject.fromObject(map).toString();
			response.getWriter().write(string);
		}catch(UnauthorizedException e){
			map.put("success", false);
			map.put("message", "无此权限");
			String string = JSONObject.fromObject(map).toString();
			response.getWriter().write(string);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("message", "操作失败");
	//					写回页面
			String string = JSONObject.fromObject(map).toString();
			response.getWriter().write(string);
				
		}
	}
		
	@Action("courierAction_findAll")
	public void findAll() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		List<Courier> list=service.findByDeltagIsNull();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"fixedAreas"});
		String string = JSONArray.fromObject(list,jsonConfig).toString();
		response.getWriter().write(string);
	}
	
	@Action("courierAction_reset")
	public void reset(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			service.update(ar);
			map.put("success", true);
			map.put("message", "操作成功");
			String string = JSONObject.fromObject(map).toString();
			response.getWriter().write(string);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("success", false);
			map.put("message", "操作失败");
			String string = JSONObject.fromObject(map).toString();
			try {
				response.getWriter().write(string);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}

