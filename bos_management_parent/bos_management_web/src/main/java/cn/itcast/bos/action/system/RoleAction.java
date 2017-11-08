package cn.itcast.bos.action.system;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import cn.itcast.bos.action.base.BaseAction;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.service.system.RoleService;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {

	@Autowired
	private RoleService service;
	
	@Action("roleAction_findAll")
	public void findAll(){
		List<Role> list=service.findAll();
//		users permissions menus
		javaToJson(list, new String []{"users","permissions","menus"});
	}
	
	
	//因为实体类没有提供permission和menu的数据属性提供set方法属性驱动
	private String  menuData ;
	private String 	permissionData;
	public void setMenuData(String menuData) {
		this.menuData = menuData;
	}

	public void setPermissionData(String permissionData) {
		this.permissionData = permissionData;
	}
	//角色保存
	@Action("roleAction_save")
	public void save(){
	
		try {
			service.save(modle,menuData,permissionData);
			javaToAjax(true, "");
		} catch (Exception e) {
			javaToAjax(false, "操作失败");
			e.printStackTrace();
		}
	}
	//角色修改
	@Action("roleAction_findByRoleId")
	public void findByRoleId(){
		//调用service层修改数据
			Role role = service.findByRoleId(modle.getId());
			javaToJson(role, new String []{"users","permissions","menus"});
	}
	
	private String idr;
	public void setIdr(String idr) {
		this.idr = idr;
	}

	@Action("roleAction_findByUser")
	public void findByUser(){
		List<Role> list=service.findByUser(Integer.parseInt(idr));
		javaToJson(list, new String []{"users","permissions","menus"});
	}
}



