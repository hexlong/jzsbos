package cn.itcast.bos.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.Order;
import cn.itcast.bos.service.take_delvery.impl.OrderService;
import cn.itcast.crm.service.impl.CustomerService;

@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class OrderAction extends BaseAction<Order> {

	private String  sendAreaInfo;
	
	public void setSendAreaInfo(String sendAreaInfo) {
		this.sendAreaInfo = sendAreaInfo;
	}
// 注入order的webservice代理对象
	@Autowired
	private OrderService orderproxy;
	@Autowired
	private CustomerService cproxy;
	
	@Action("orderAction_add")
	public void add(){
		
		//这是前端的数据所以要保存到bos数据库，需要调用bos中方法和数据库交互
		//因为页面的数据传输过来的省市区
		//省市区模型驱动接受不到，用属性驱动获取,获取的xx省/xx市/xx区
		String[] split = sendAreaInfo.split("/");
		
			Area sendArea = new Area();
			sendArea.setProvince(split[0]);
			sendArea.setCity(split[1]);
			sendArea.setDistrict(split[2]);
			modle.setSendArea(sendArea);
	
		//把装有数据的sendarea放到modle中也就是order中
		//调用代理对象的save方来保存order
		orderproxy.save(modle);
	
		
		
	}
}
