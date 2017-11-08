package cn.itcast.bos.action.take_delivery;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.action.base.BaseAction;
import cn.itcast.bos.domain.base.WayBill;
import cn.itcast.bos.domain.base.WorkBill;
import cn.itcast.bos.service.take_delvery.WorkBillService;

@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class WorkBillAction extends BaseAction<WorkBill> {
	
	@Autowired
	private WorkBillService service;
	

	@Action("workBillAction_save")
	public void workBillSave(){
		try{
		service.saveWorkBill(modle,modle.getId());
		javaToAjax(true, "派单成功");
		}catch(Exception e){
			javaToAjax(false, "派单失败");
			e.printStackTrace();
		}
	}
}
