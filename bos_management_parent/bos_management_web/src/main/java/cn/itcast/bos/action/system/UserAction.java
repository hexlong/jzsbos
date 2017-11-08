package cn.itcast.bos.action.system;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.action.base.BaseAction;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.UserService;

@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {

	@Autowired
	private UserService service;
	
	//登陆查询用户
	@Action(value="userAction_login")
	public void login(){
		//整合shiro框架，1创建令牌
		UsernamePasswordToken token=new UsernamePasswordToken(modle.getUsername(), modle.getPassword());
		//2获取主题	
		Subject subject = SecurityUtils.getSubject();
		
		try {
		//3开始认证
		subject.login(token);
		javaToAjax(true, "");
		} catch (Exception e) {
			javaToAjax(false, "用户名或者密码错误");
		}
		/*//调用service查询用户是否存在
		User user=service.findByUsernameAndPassword(modle.getUsername(),modle.getPassword());
		//判断查询到的对象是否为空，如果为空说明账号或者密码错误
		if(user==null){
			javaToAjax(false, "账号或密码错误");
		}else{
			//如果查到，则登陆成功，存用户到session中
			ServletActionContext.getRequest().getSession().setAttribute("user", user);
			javaToAjax(true, "");
		}*/
		
		
	}
	//判断session获取标题登陆人名字
	@Action("userAction_getName")
	public void getName(){
		//shiro整合管理session者
		//获取主题对象再获取主角user
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		//判断user是否为空
		if(user==null){
			javaToAjax(false, "");
		}else{
			javaToAjax(true, user.getUsername());
		}
		
		/*//去session域中获取，如果获取到数据说明不用登陆
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if(user==null){
			javaToAjax(false, null);
		}else{
			javaToAjax(true, user.getUsername());
		}	
	*/
	}
	//退出登录
	@Action(value="userAction_logout",results={
			@Result(name="success",type="redirect",location="/login.html"),
		})
	public String userAction_logout(){
		SecurityUtils.getSubject().logout();
		
		/*//清空session的数据
		ServletActionContext.getRequest().getSession().removeAttribute("user");*/
		
		return SUCCESS;
	}
	
    @Action("userAction_findAll")
	public void findAll(){
    	List<User> list=service.findAll();
    	javaToJson(list, new String[]{"roles"});
    }
    
    private String rolesIds;
    public void setRolesIds(String rolesIds) {
		this.rolesIds = rolesIds;
	}
    
	@Action("userAction_save")
   	public void save(){
       
    	try {
			service.save(modle,rolesIds);
			javaToAjax(true, "");
		} catch (Exception e) {
			javaToAjax(false, "操作失败");
			e.printStackTrace();
		}
     }
	
	private String idr;
	public void setIdr(String idr) {
		this.idr = idr;
	}
	@Action("userAction_update")
	public void update(){
		User user=service.findByUserId(idr);
		javaToJson(user, new String[]{"roles","birthday"});
	}
}
