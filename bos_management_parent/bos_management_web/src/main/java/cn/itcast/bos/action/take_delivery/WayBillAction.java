package cn.itcast.bos.action.take_delivery;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.action.base.BaseAction;
import cn.itcast.bos.domain.base.WayBill;
import cn.itcast.bos.service.take_delvery.WayBillService;

@ParentPackage("struts-default")
@Namespace("/")
@Controller("wayBillAction")
@Scope("prototype")
public class WayBillAction extends BaseAction<WayBill> {

	@Autowired
	private WayBillService wayBillService;
	
	@Action("wayBill_quickAction_save")
	public void save(){
		System.out.println("数据保存了哦，亲");
		//调用service保存
		try{
			wayBillService.save(modle);
			javaToAjax(true, "操作成功");
		}catch(Exception e){
			javaToAjax(false, "操作失败");
			e.printStackTrace();
		}
	}
	
	
}
