package cn.itcast.bos.action.system;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.action.base.BaseAction;
import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.MenuService;

@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class MenuAction extends BaseAction<Menu> {

	@Autowired
	private MenuService service;
	//查询所有的数据
	@Action("menuAction_findAll")
	public void findAll(){
//		roles Menu parentMenu
		List<Menu> list =service.findAll();
		javaToJson(list, new String[]{"roles","parentMenu","childrenMenus","children"});
	}
	//查询父节点菜单 
	@Action("menuAction_findByPidIsNull")
	public void findByPidIsNull(){
//		roles Menu parentMenu
		List<Menu> list =service.findByPidIsNull();
		javaToJson(list, new String[]{"roles","parentMenu","childrenMenus"});
	}
	@Action("menuAction_save")
	public void save(){

		try {
			service.save(modle);
			javaToAjax(true, "");
		} catch (Exception e) {
			javaToAjax(false, "操作失败");
			e.printStackTrace();
		}
	}
	
	@Action("menuAction_findByUser")
	public void findByUser(){
		
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		List<Menu> list=service.findByUser(user);
		javaToJson(list,  new String[]{"roles","childrenMenus","parentMenu","children"});
	}
	
	
	private String rid;
	public void setRid(String rid) {
		this.rid = rid;
	}
	@Action("menuAction_findByRoleId")
	public void findByRoleId(){
		List<Menu> list = service.findByRoleId(rid);
		javaToJson(list, new String[]{"roles","parentMenu","childrenMenus"});
	}
}








