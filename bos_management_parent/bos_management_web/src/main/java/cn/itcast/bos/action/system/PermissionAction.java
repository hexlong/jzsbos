package cn.itcast.bos.action.system;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.action.base.BaseAction;
import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.service.system.PermissionService;

@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class PermissionAction extends BaseAction<Permission> {

	@Autowired
	private PermissionService service;
	
	@Action("permissionAction_findAll")
	public void findAll(){
		List<Permission> list =service.findAll();
		javaToJson(list, new String[]{"roles"});
	}
	
	@Action("permissionAction_save")
	public void save(){
		try {
			service.save(modle);
			javaToAjax(true, "");
		} catch (Exception e) {
			javaToAjax(false, "操作失败");
			e.printStackTrace();
		}
	}
	private String rid;
	
	
	public void setRid(String rid) {
		this.rid = rid;
	}

	@Action("permissionAction_findByRoleId")
	public void findByRoleId(){
		List<Permission> list = service.findByRoleId(rid);
		javaToJson(list, new String[]{"roles"});
	}
	
}
