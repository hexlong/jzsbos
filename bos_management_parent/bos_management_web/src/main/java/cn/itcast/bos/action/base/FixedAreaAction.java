package cn.itcast.bos.action.base;

import java.util.HashMap;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.base.FixedAreaService;
import cn.itcast.crm.service.Customer;
import cn.itcast.crm.service.impl.CustomerService;

@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea> {

	@Autowired
	private FixedAreaService service;
	
	//定义一个属性data用来接收数据，属性驱动
	private String data;
	
	public void setData(String data) {
		this.data = data;
	}

	@Action("fixed_areaAction_save")
	public void save(){
		
		HashMap<String, Object> map = new HashMap<String,Object>();
		try{
			service.save(modle,data);
			saveAjax(true, "操作成功", map);
		}catch(Exception e){
			e.printStackTrace();
			saveAjax(false, "操作失败", map);
		}
	}
	
	@Action("fixedAreaAction_findAllByPage")
	public void findAllByPage(){
		Pageable pageable=new PageRequest(page-1, rows);
		Page<FixedArea> page=service.findAllByPage(pageable);
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
//		couriers  subareas
		javaToJson(map, new String[]{"couriers","subareas"});
	}
	
	@Autowired
	private CustomerService cproxy;
//	fixedAreaAction_findByFixedAreaIdIsNull
/*页面关联客户左侧下拉选
 * 
 * */ 
	@Action("fixedAreaAction_findByFixedAreaIdIsNull")
	public void findByFixedAreaIdIsNull(){
		List<Customer> list = cproxy.findByFixedAreaIdIsNull();
		javaToJson(list, null);
	}
	
//	fixedAreaAction_findByFixedAreaId
	/*页面关联客户右侧下拉选
	 * 
	 * */ 
	@Action("fixedAreaAction_findByFixedAreaId")
	public void findByFixedAreaId(){
		
		List<Customer> list = cproxy.findByFixedAreaId(modle.getId());
		
		javaToJson(list, null);
	}
	
	//要从页面接受数据用属性驱动，id在modle模型驱动中已经封装
	private String rightparams;
	public void setRightparams(String rightparams) {
		this.rightparams = rightparams;
	}

	//	fixedAreaAction_updateCustomer
	@Action(value="fixedAreaAction_updateCustomer")
	public void updateCustomer(){
		HashMap<String, Object> map = new HashMap<String,Object>();
		try{
		cproxy.updateFixedAreaId(modle.getId(), rightparams);;
		
		saveAjax(true, "操作成功", map);
		}catch(Exception e){
			e.printStackTrace();
		saveAjax(false, "操作失败", map);
		}
		
	}
	//定区id已经有了，但是缺少快递员id和时间id
	private Integer courierId;
	private Integer takeTimeId;
	
public void setCourierId(Integer courierId) {
		this.courierId = courierId;
	}

	public void setTakeTimeId(Integer takeTimeId) {
		this.takeTimeId = takeTimeId;
	}

	//	fixedCourier_save保存关联的快递员
	@Action("fixedCourier_save")
	public void fixedCouriersave(){
		//调用service执行保存方法
		HashMap<String, Object> map = new HashMap<String,Object>();
		try{
		service.save(modle.getId(), courierId,takeTimeId);
		saveAjax(true, "操作成功", map);
		}catch(Exception e){
			e.printStackTrace();
			saveAjax(false, "操作失败", map);
		}
	}
}
