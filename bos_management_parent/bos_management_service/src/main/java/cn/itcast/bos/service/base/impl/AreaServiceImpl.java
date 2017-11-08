package cn.itcast.bos.service.base.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.AreaDao;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.AreaService;
import cn.itcast.bos.utils.PinYin4jUtils;

@Service
@Transactional
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaDao dao;
	
	@Override
	public void importExl(FileInputStream fileInputStream) {
//		POI解析office软件  word  Excel  ppt 
//		workbook  工作薄  就是一个excel文档
//		sheet     工作表
//		row       行
//		cell      单元格
//		HSSF低版本        XSSF高版本     
		//创建一个给你工作簿
		
		// String province; // 省
		//	String city; // 城市
		//String district; // 区域
		//String postcode; // 邮编
		//String citycode; // 城市编码
		//String shortcode; // 简码
		HSSFWorkbook book=null;
		try {
			book=new HSSFWorkbook(fileInputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取索引0也就是第一张表
		HSSFSheet sheet = book.getSheetAt(0);
		//表中有很多行数据所以要循环遍历出每行数据最后一行是lastnum
		int lastRowNum = sheet.getLastRowNum();
		HSSFRow row=null;
		Area area=null;
		List<Area> list =new ArrayList<Area>();
//		HSSFRow row2 = sheet.getRow(0);
//		Area area2=new Area();
//		area2.setId(row2.getCell(0).getStringCellValue());
//		area2.setProvince(row2.getCell(1).getStringCellValue());
//		area2.setCity(row2.getCell(2).getStringCellValue());
//		area2.setDistrict(row2.getCell(3).getStringCellValue());
//		area2.setPostcode(row2.getCell(4).getStringCellValue());
//		list.add(area2);
		for(int i=1;i<=lastRowNum;i++){
			row=sheet.getRow(i);
			String id = row.getCell(0).getStringCellValue();
			String province = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district = row.getCell(3).getStringCellValue();
			String postcode = row.getCell(4).getStringCellValue();
			//对象封装属性数据
			area=new Area();
			area.setId(id);
			area.setCity(city);
			area.setPostcode(postcode);
			area.setDistrict(district);
			area.setProvince(province);
			
			//切割字符串
			province=province.substring(0, province.length()-1);
			city=city.substring(0, city.length()-1);
			district=district.substring(0, district.length()-1);
			//设置简码转拼音
			String citycode = PinYin4jUtils.hanziToPinyin(city);//城市编码拼音
			//简码只要首字母
			String[] headByString = PinYin4jUtils.getHeadByString(province+city+district);
			//转化为字符串
			String shortcode = StringUtils.join(headByString);
			
			area.setCitycode(citycode);
			area.setShortcode(shortcode);
			//为了防止事务一次次开启关闭所以使用list来提高效率，只保存一次就可以了
			list.add(area);
		}
		System.out.println();
		dao.save(list);
	
		
	}

	@Override
	public Page<Area> findAllByPage(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<Area> findAll(Area modle,String q) {
		List<Area> list =null;
		if(q==null){
			 list = dao.findAll();
		}else{
			
			list=dao.findByCitycodeLikeOrShortcodeLike("%"+q.toLowerCase()+"%","%"+q.toUpperCase()+"%");
		}
		return list;
		
	}

}
