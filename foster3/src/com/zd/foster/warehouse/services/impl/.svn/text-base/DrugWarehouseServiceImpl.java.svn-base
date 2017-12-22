/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午03:03:40
 * @file:DrugWarehouseServiceImpl.java
 */
package com.zd.foster.warehouse.services.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.utils.ArithUtil;
import com.zd.epa.utils.ExcelUtil;
import com.zd.epa.utils.Pager;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.breed.entity.DeathBillDtl;
import com.zd.foster.dto.DeathAnalysis;
import com.zd.foster.material.entity.Drug;
import com.zd.foster.warehouse.dao.IDrugWarehouseDao;
import com.zd.foster.warehouse.entity.DrugWarehouse;
import com.zd.foster.warehouse.services.IDrugWarehouseService;

/**
 * 类名：  DrugWarehouseServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-7-26下午03:03:40
 * @version 1.0
 * 
 */
public class DrugWarehouseServiceImpl extends BaseServiceImpl<DrugWarehouse, IDrugWarehouseDao> implements
		IDrugWarehouseService {
	@Resource
	private IFarmerService farmerService;

	/**
	 * 
	 * 功能:药品查询时，isDrug属性需为0
	 * 重写:wxb
	 * 2017-7-29
	 * @see com.zd.epa.base.BaseServiceImpl#selectAll(com.zd.epa.base.BaseEntity, com.zd.epa.utils.Pager)
	 */
	@Override
	public void selectAll(DrugWarehouse entity, Pager<DrugWarehouse> page)
			throws Exception {
		Map<String,Object> map=entity.getMap();
		map.put("e.isDrug", "0");
		//根据代养户查询对应公司的库存
		if(entity.getFarmer()!=null && !"".equals(entity.getFarmer())){
			Farmer f=farmerService.selectById(entity.getFarmer());
			map.put("e.company", f.getCompany().getId());
//			map.put("e.farmer", null);
		}
		super.selectAll(entity, page);
	}
	
	
	public InputStream exportWarehouse(DrugWarehouse entity)throws Exception{
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("公司药品库存报表");
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		//设置表头样式
		HSSFCellStyle headerStyle = ExcelUtil.getHeaderStyle(workbook);
		//设置表格样式
		HSSFCellStyle cellStyle = ExcelUtil.getCellStyle(workbook);
		String exportFields = "药品,编码,类型,规格,单位,副单位,供应商,数量,副单位数量";
		
		String[] fields = exportFields.split(",");
		// 生成表头标题行
		int cellIndex = -1;
		for (String field : fields) {
			ExcelUtil.setCellValue(sheet, headerStyle, 0, ++cellIndex, field);
		}
		// 生成数据行
		int index = 0;
		List<DrugWarehouse> datas = selectAnalysis(entity);
		if (null != datas && datas.size() > 0) {
			for (DrugWarehouse obj : datas) {
				index ++;
				HSSFRow row = sheet.createRow(index);
				HSSFCell cell = row.createCell(0);
				cell.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell, obj.getDrug().getName());
				
				HSSFCell cell1 = row.createCell(1);
				cell1.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell1, obj.getDrug().getCode());
				
				HSSFCell cell2 = row.createCell(2);
				cell2.setCellStyle(cellStyle);
				if(obj.getDrug().getDrugType()!=null){
					if( obj.getDrug().getDrugType()==0)
						ExcelUtil.setCellValue(cell2, "药品");
					else
						ExcelUtil.setCellValue(cell2, "疫苗");
				}
				
				
				HSSFCell cell3 = row.createCell(3);
				cell3.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell3, obj.getDrug().getSpec());
				
				HSSFCell cell4 = row.createCell(4);
				cell4.setCellStyle(cellStyle);
				if(obj.getDrug().getUnit()!=null)
					ExcelUtil.setCellValue(cell4, obj.getDrug().getUnit().getValue());
				
				HSSFCell cell5 = row.createCell(5);
				cell5.setCellStyle(cellStyle);
				if(obj.getDrug().getSubUnit()!=null)
					ExcelUtil.setCellValue(cell5, obj.getDrug().getSubUnit().getValue());
				
				HSSFCell cell6 = row.createCell(6);
				cell6.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell6, obj.getDrug().getSupplier());
				
				HSSFCell cell7 = row.createCell(7);
				cell7.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell7, obj.getQuantity());
				
				HSSFCell cell8 = row.createCell(8);
				cell8.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell8, obj.getSubQuantity());
			}
		}
		// 生成数据行
		ByteArrayOutputStream out = null;
		InputStream inputStream = null;
		try {
			out = new ByteArrayOutputStream();
			workbook.write(out);
			out.flush();
			byte[] outByte = out.toByteArray();
			inputStream = new ByteArrayInputStream(outByte, 0, outByte.length);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if (null != inputStream) {
					inputStream.close();
				}
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return inputStream;
	}

	/**
	 * 查询药品库存不分页
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * List<DrugWarehouse>
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-17 下午06:02:54
	 */
	private List<DrugWarehouse> selectAnalysis(DrugWarehouse entity) throws Exception {
		
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		
		if(entity.getCompany()!=null && entity.getCompany().getId()!=null && !"".equals(entity.getCompany().getId()))
			sqlMap.put("company.id", "=", entity.getCompany().getId());
		if(entity.getDrug().getDrugType()!=null && !"".equals(entity.getDrug().getDrugType()))
			sqlMap.put("drug.drugType", "=", entity.getDrug().getDrugType());
		if(entity.getDrug()!=null && entity.getDrug().getId()!=null && !"".equals(entity.getDrug().getId()))
			sqlMap.put("drug.id", "=", entity.getDrug().getId());
		sqlMap.put("isDrug", "=", "0");
		
		sqlMap.put("id", "order by", "asc");
		List<DrugWarehouse> dbds = selectHQL(sqlMap);
		//合计
		String totalSubQuan = "0";
		String totalQuan = "0";
		if(dbds != null && dbds.size()>0){
			for(DrugWarehouse dbd : dbds){
				totalQuan = ArithUtil.add(totalQuan, dbd.getQuantity());
				totalSubQuan = ArithUtil.add(totalSubQuan, dbd.getSubQuantity());
			}
		}
		DrugWarehouse da = new DrugWarehouse();
		Drug drug = new Drug();
		drug.setSupplier("合计");
		da.setDrug(drug);
		da.setQuantity(totalQuan);
		da.setSubQuantity(totalSubQuan);
		dbds.add(da);
		
		return dbds;
	}

	/**
	 * 根据code加载药品
	 * @Description:TODO
	 * @param e
	 * @return
	 * @throws Exception
	 * List<Drug>
	 * @exception:
	 * @author: 小丁
	 * @throws Exception 
	 * @time:2017-10-18 上午09:23:20
	 */
	public List<DrugWarehouse> selectByCodes(DrugWarehouse entity) throws Exception{
		List<DrugWarehouse> drugs = new ArrayList<DrugWarehouse>();
		SqlMap<String,String,Object> sqlmap =new SqlMap<String,String,Object>();
		
		if(entity.getCompany()==null || "".equals(entity.getCompany().getId())){
			if(entity.getFarmer()!=null && !"".equals(entity.getFarmer())){
				Farmer farmer = farmerService.selectById(entity.getFarmer());
				sqlmap.put("company.id", "=", farmer.getCompany().getId());
			}
		}else{
			sqlmap.put("company.id", "=", entity.getCompany().getId());
		}
		sqlmap.put("isDrug", "=", "0");
		//--------添加饲料厂-----------
		if(entity.getFeedFac() != null && entity.getFeedFac().getId() != null && !"".equals(entity.getFeedFac().getId()))
			sqlmap.put("feedFac.id", "=",entity.getFeedFac().getId());
		//sqlmap.put("farmer", "=", entity.getFarmer());
		if(entity.getDrug().getDrugType()!=null){
			sqlmap.put("drug.drugType", "=", entity.getDrug().getDrugType());
		}
		if(entity.getDrug().getName()!=null && !"".equals(entity.getDrug().getName())){
			sqlmap.put("drug.name", "=", entity.getDrug().getName());
		}
		if(entity.getDrug().getCode()!=null && !"".equals(entity.getDrug().getCode())){
			String codes = entity.getDrug().getCode().replaceAll(" ", "").replaceAll("，", ",").replaceAll("[',']+", ",").replaceAll("\\.",",");
			String[] strCode=codes.split(",");
			
//			List<String> codeList = new ArrayList<String>();
//			for(String code : strCode){
//				if(!codeList.contains(code))
//					codeList.add(code);
//			}
//			
//			for(String code : codeList){
//				sqlmap.put("drug.code", "=", code);
//				List<DrugWarehouse> drugList = selectHQL(sqlmap);
//				sqlmap.remove("drug.code");
//				
//				drugs.addAll(drugList);
//			}
			
			sqlmap.put("drug.code","in",PapUtil.arrayToSQLStr(strCode));
		}
		//排序
		sqlmap.put("drug.code","order by","asc");
		drugs = dao.selectHQLSingle(sqlmap);
		return drugs;
	}
	
}
