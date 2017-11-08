package cn.itcast.bos.service.base.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.AreaDao;
import cn.itcast.bos.dao.base.SubAreaDao;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.service.base.SubAreaService;

@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {

	@Autowired
	private SubAreaDao dao;
	@Autowired
	private AreaDao adao;


	@Override
	public void save(SubArea modle) {
		//这里有个异常因为实体类中id是String的oracle无法自增所以没有封装id到实体类中
		//用uuid来是指id值，但是save和update的方法不能使id一直自增，所以判断没有id有就不设置
		if(modle.getId()==null){
			modle.setId(UUID.randomUUID().toString());
		}
		
		dao.save(modle);
		
	}

	@Override
	public Page<SubArea> findAllByPage( Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<SubArea> findAll() {
		
		return dao.findAll();
	}

	@Override
	public void export(FileInputStream in, ServletOutputStream out) {
		try {
			//获取工作簿,引用模板 in 输入流中有模板
			XSSFWorkbook book =new XSSFWorkbook(in);
			//使用第一张表
			XSSFSheet sheet = book.getSheetAt(0);
					//获取模板中的样式,位置第二张表第一行第一格的样式
				XSSFCellStyle cellStyle = book.getSheetAt(1).getRow(0).getCell(0).getCellStyle();
			//去数据库中查询数据
			List<SubArea> list = dao.findAll();
//				遍历list数据
			XSSFRow row = null;
			XSSFCell cell = null;
			int i=2;
			for (SubArea subArea : list) {
//	分拣编号	省	市	区	关键字	起始号	终止号	单双号	辅助关键字
				//有多少对象插入多少行
				 row = sheet.createRow(i);
				 //没创建一行就创建一列
				 cell = row.createCell(0);
				 //创建一列就插入一条数据
				 cell.setCellValue(subArea.getId());
//				 设置样式
				 cell.setCellStyle(cellStyle);
				 
				 cell = row.createCell(1);
				 cell.setCellStyle(cellStyle);
				 cell.setCellValue(subArea.getArea().getProvince());

				 cell = row.createCell(2);
				 cell.setCellStyle(cellStyle);
				 cell.setCellValue(subArea.getArea().getCity());

				 cell = row.createCell(3);
				 cell.setCellStyle(cellStyle);
				 cell.setCellValue(subArea.getArea().getDistrict());

				 cell = row.createCell(4);
				 cell.setCellStyle(cellStyle);
				 cell.setCellValue(subArea.getKeyWords());

				 cell = row.createCell(5);
				 cell.setCellStyle(cellStyle);
				 cell.setCellValue(subArea.getStartNum());

				 cell = row.createCell(6);
				 cell.setCellStyle(cellStyle);
				 cell.setCellValue(subArea.getEndNum());

				 cell = row.createCell(7);
				 cell.setCellStyle(cellStyle);
				 cell.setCellValue(subArea.getSingle());

				 cell = row.createCell(8);
				 cell.setCellStyle(cellStyle);
				 cell.setCellValue(subArea.getAssistKeyWords());
				i++;
			}
			//把工作簿返回给浏览器，输出流通过response响应信息获取
			book.write(out);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	//区域分布查询
	@Override
	public List showChart() {
		// TODO Auto-generated method stub
		return dao.showChart();
	}

	//导入分区数据
	@Override
	public void uploadExl(FileInputStream in) {
		try {
			XSSFWorkbook book =new XSSFWorkbook(in);
			XSSFSheet sheet = book.getSheetAt(0);
			int lastRowNum = sheet.getLastRowNum();
			XSSFRow row =null;
//			XSSFCell cell =null;
			SubArea sb=null;
			Area area =null;
			ArrayList<SubArea> list = new ArrayList<SubArea>();
			for (int i = 1; i <= lastRowNum; i++) {
				 row = sheet.getRow(i);
				 sb=new SubArea();
				 sb.setId(row.getCell(0).getStringCellValue());
				 sb.setKeyWords(row.getCell(4).getStringCellValue());
				 sb.setStartNum(row.getCell(5).getStringCellValue());
				 sb.setEndNum(row.getCell(6).getStringCellValue());
				 sb.setSingle(row.getCell(7).getStringCellValue().charAt(0));
				 sb.setAssistKeyWords(row.getCell(8).getStringCellValue());
				 
				 area = adao.findByProvinceAndCityAndDistrict(row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue(), row.getCell(3).getStringCellValue());
				 sb.setArea(area);  
				//为了防止事务一次次开启关闭所以使用list来提高效率，只保存一次就可以了
					list.add(sb);
			}
			dao.save(list);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
