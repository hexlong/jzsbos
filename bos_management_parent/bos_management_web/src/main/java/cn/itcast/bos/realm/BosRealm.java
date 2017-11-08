package cn.itcast.bos.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.PermissionService;
import cn.itcast.bos.service.system.RoleService;
import cn.itcast.bos.service.system.UserService;

public class BosRealm extends AuthorizingRealm {

	//注入service
	@Autowired
	private UserService service;
	@Autowired
	private RoleService rservice;
	@Autowired
	private PermissionService pservice;
	//认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		//向下转型为实现类  
		UsernamePasswordToken token=(UsernamePasswordToken) arg0;
		//获取传递过来的username 和 password
		String username = token.getUsername();
		char[] password = token.getPassword();
		//调用service查询用户名和密码因为只有一个密码所以(把char[]数组转成字符串)
		User user = service.findByUsernameAndPassword(username, new String(password));
		//判断用户是否为空
		if(user==null){
			//没有用户
			return null;
		}else{
			//principal 主角, credentials 密码, realmName
			//查到了,new 一个SimpleAuthenticationInfo对象return回去
			SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(user,new String(password), getName());
			return info;
		}
	}

	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		//new一个授权对象
		SimpleAuthorizationInfo info =new SimpleAuthorizationInfo();
		//授予当前登录人‘courier’的访问权限
//		info.addStringPermission("courier");
		//授予当前登录人‘delete’的权限--注解
//		info.addStringPermission("courier-delete");
		
		//获取当前登录人
		User user=(User) arg0.getPrimaryPrincipal();
		//获取当前登录人的角色
		List<Role> list = rservice.findByUser(user);
		for (Role role : list) {
			//赋予每个角色权限
			info.addRole(role.getKeyword());
		}
//		获取当前登录人的权限
		List<Permission> plist =pservice.findByUser(user);
		for (Permission permission : plist) {
			//赋予权限
			info.addStringPermission(permission.getKeyword());
//			等同于info.addStringPermission("courier");拥有courier后的所有权限
		}
		
		return info;
	}

	
}
