package cn.itcast.bos.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.engine.spi.CascadingActions.BaseCascadingAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.util.MailUtils;
import cn.itcast.bos.util.SmsUtils;
import cn.itcast.crm.service.Customer;
import cn.itcast.crm.service.impl.CustomerService;


@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class CustomerAction extends BaseAction<Customer> {

	
	
	
	@Action("customerAction_sendCode")
	public void sendCode(){
		//生成验证码
		String randomNumeric = RandomStringUtils.randomNumeric(4);
		System.out.println("验证码是:"+randomNumeric);
		//吉信通发送短信
		SmsUtils.sendSmsByWebService(modle.getTelephone(), "尊敬的客户你好，您本次获取的验证码为："+randomNumeric);
		//放到session中
		ServletActionContext.getRequest().getSession().setAttribute(modle.getTelephone(), randomNumeric);
	}
	
	//注入代理调用服务端的对象
	@Autowired
	private CustomerService cproxy;
	private String checkcode;
	
	
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}


	@Autowired
	private RedisTemplate redisTemplate;
	//注册
	@Action(value="customerAction_regist",results={
							@Result(name="fail",type="redirect",location="/signup-fail.html"),
							@Result(name="success",type="redirect",location="/signup-success.html")})
	public String regist(){
		//先判断该手机是否被注册
		Customer customer = cproxy.findTelephone(modle.getTelephone());
		if(customer!=null){
			return "fail";
		}
		
		//需要校验手机端的短信和后台发送都短信是否一致
		String randomNumeric = (String) ServletActionContext.getRequest().getSession().getAttribute(modle.getTelephone());
		if(randomNumeric.equals(checkcode)){
			try{
				cproxy.regist(modle);
				//成功后给邮箱发送邮件来激活
				//生成激活码
				String uuid = UUID.randomUUID().toString();
				//把生成的激活码同时储存到redis中一份                 键                                        值          时间         时间单位timeunit.参数
				redisTemplate.opsForValue().set(modle.getTelephone(), uuid, 2, TimeUnit.DAYS);
				String params="<a href='http://localhost:8082/bos_fore/customerAction_active.action?telephone="+modle.getTelephone()+"&code="+uuid+"'>http://localhost:8082/customerAction_active.action?telephone="+modle.getTelephone()+"&code="+uuid+"</a>";
								//主题            内容		接受的邮箱
				MailUtils.sendMail("激活", "尊敬的用户您好，请点击下列激活码激活您的邮箱"+params, modle.getEmail());
				return "success";
			}catch(Exception e){
				e.printStackTrace();
				return "fail";
			}
			
		}else{
			return "fail";
		}
	}
	
	//查询手机号是否已经注册
	@Action("customerAction_findCustomer")
	public void findCustomer() throws IOException{
		HashMap<String, Object> map = new HashMap<String,Object>();
		Customer customer = cproxy.findTelephone(modle.getTelephone());
		
		if(customer==null){
			saveAjax(true, "用户可用", map);
		}else{
			saveAjax(false, "用户已存在", map);
		}
		
	}
	
	//获取页面的验证码注入属性
	private String code;
	public void setCode(String code) {
		this.code = code;
	}

	//邮箱激活修改数据库
	@Action("customerAction_active")
	public void active(){
		//判断页面的激活码是否是匹配的
		String activecode = (String) redisTemplate.opsForValue().get(modle.getTelephone());
		if(code==null){
			//没有激活码，失效
			javaToJson("激活码失效了");
			return ;
			
		}
		if(!code.equals(activecode)){
			//匹配不成功
			javaToJson("激活码不正确");
		}else{
			//匹配成功调用方法
			try{
				//为了防止多次激活，查询判断
				Customer customer = cproxy.findTelephone(modle.getTelephone());
				if(customer.getType()!=null&&customer.getType()==1){
					javaToJson("已经激活");
					return ;
				}
				cproxy.active(modle.getTelephone());
				javaToJson("激活成功");
			}catch(Exception e){
				e.printStackTrace();
				javaToJson("激活失败");
			}
		}
	}
	
	//登录
	@Action("customerAction_login")
	public void login(){
		HashMap<String, Object> map = new HashMap<String,Object>();
		//checkcode页面验证码
		//获取session中的验证码
		String validateCode = (String) ServletActionContext.getRequest().getSession().getAttribute("validateCode");
		//判断页面验证吗和产生的验证码是否正确
		Customer customer=null;
		if(!validateCode.equals(checkcode)){
			saveAjax(false, "验证码错误", map);
		}else{
			//判断是邮箱还是手机号
			if(modle.getTelephone().contains("@")){
				//包含@符号是邮箱，调用邮箱查询对象
				customer = cproxy.findByEmailAndPassword(modle.getTelephone(), modle.getPassword());
				if(customer.getType()==null){
					saveAjax(false, "邮箱未激活", map);
					return;
				}
			}else{
				//用手机号查询对象
				customer = cproxy.findByTelephoneAndPassword(modle.getTelephone(), modle.getPassword());
			}
			//对象为空说明没有查到，账号密码有错误
			if(customer==null){
				saveAjax(false, "账号或密码错误", map);
				return;
			}else{
				//不为空则返回true
				saveAjax(true, "", map);
			}
			
			
		}
	}
	
}