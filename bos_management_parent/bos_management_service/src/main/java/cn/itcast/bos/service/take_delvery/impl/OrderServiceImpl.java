package cn.itcast.bos.service.take_delvery.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.AreaDao;
import cn.itcast.bos.dao.base.FixedAreaDao;
import cn.itcast.bos.dao.base.SubAreaDao;
import cn.itcast.bos.dao.take_delivery.OrderDao;
import cn.itcast.bos.dao.take_delivery.WorkBillDao;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.Order;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.domain.base.WorkBill;
import cn.itcast.bos.service.take_delvery.OrderService;
import cn.itcast.crm.service.impl.CustomerService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	// 注入orderdao
	@Autowired
	private OrderDao odao;
	@Autowired
	private AreaDao adao;
	@Autowired
	private FixedAreaDao fdao;
	@Autowired
	private WorkBillDao wdao;
	@Autowired
	private SubAreaDao sdao;

	@Autowired
	private CustomerService cproxy;

	// 创建添加订单的方法，把订单数据放到数据库
	public void save(Order order) {
		// 操作Dao执行保存
		/*
		 * 传递过来的order中已经设置了省市区各个属性但是没有id，因为有关联id所以要 查找通过条件区域获取区域id，也就是操作持久态的对象
		 */
		// 获取传递的order数据

		Area sendArea = order.getSendArea();

		// 通过条件查询获取sendArea对象，操作Areadao
		sendArea = adao.findByProvinceAndCityAndDistrict(sendArea.getProvince(), sendArea.getCity(),
				sendArea.getDistrict());

		// 查询到sendArea持久化对象后set到order中
		order.setSendArea(sendArea);
		// 保存order到数据库
		odao.save(order);
		// 当保存成功时要生成工单
		/*
		 * crm地址完全匹配的方式 所以要调用crm中通过地址查询定区id的方法 获取到定区id后通过id查询定区对象 定区对象中包含有快递员集合
		 * 模拟随机分配一个快递员
		 */
		// 调用crm中通过地址查询定区id的方法
		String fixedAreaId = cproxy.findByAddress(order.getSendAddress());
		if (fixedAreaId != null) {
			// 获取到定区id后通过id查询定区对象
			try {
				FixedArea fixedArea = fdao.findOne(fixedAreaId);
				// 定区对象中包含有快递员集合
				Set<Courier> couriers = fixedArea.getCouriers();
				// 模拟随机分配一个快递员
				for (Courier courier : couriers) {
					// 创建工单
					WorkBill workBill = new WorkBill();
					// 工单中添加数据
					workBill.setAttachbilltimes(0);// 追单次数
					workBill.setBuildtime(new Date());// 下单时间
					workBill.setCourier(courier);
					System.out.println(courier + "快递员");
					workBill.setOrder(order);
					workBill.setPickstate("新单");
					workBill.setRemark(order.getRemark());// 订单备注
					workBill.setSmsNumber("123432123");// 生成的短信码
					workBill.setType("新");
					// 然后 保存工单
					wdao.save(workBill);
					System.out.println("保存工单");
					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			// 关键字匹配实现工单分配
			// 先查询到所有分区信息
			// 遍历所有分区匹配发送地址sendaddress中是否包含分区的关键字或者辅助关键字
			List<SubArea> subAreas = sdao.findAll();
			for (SubArea subArea : subAreas) {
				if (order.getSendAddress().contains(subArea.getKeyWords())
						|| order.getSendAddress().contains(subArea.getAssistKeyWords())) {
					// 匹配到以后
					FixedArea fixedArea = subArea.getFixedArea();
					Set<Courier> couriers = fixedArea.getCouriers();
					// 模拟找到了一个快递员
					for (Courier courier : couriers) {
						// courier
						WorkBill workBill = new WorkBill();
						workBill.setAttachbilltimes(0);// 追单次数
						workBill.setBuildtime(new Date()); // 下单时间
						workBill.setCourier(courier);
						workBill.setOrder(order);
						workBill.setPickstate("新单");
						workBill.setRemark(order.getRemark());// 订单备注
						workBill.setSmsNumber("79862833223");
						workBill.setType("新");
						wdao.save(workBill);
						// 给快递员发短信
						System.out.println("发送了一条通知短信");
						// SmsUtils.sendSmsByWebService(courier.getTelephone(),
						// "请到"+sendAddress+"取件,客户的联系电话是"+order.getSendMobile());
						break;
					}

				}
			}
		}
	}


}
