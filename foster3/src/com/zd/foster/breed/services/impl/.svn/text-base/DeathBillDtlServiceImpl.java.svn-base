/**
 * 功能:
 * @author:wxb
 * @data:2017-8-1下午08:32:28
 * @file:DeathBillDtlServiceImpl.java
 */
package com.zd.foster.breed.services.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
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
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.ArithUtil;
import com.zd.epa.utils.ExcelUtil;
import com.zd.epa.utils.JDBCWrapperx;
import com.zd.epa.utils.Pager;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.breed.dao.IDeathBillDtlDao;
import com.zd.foster.breed.entity.DeathBillDtl;
import com.zd.foster.breed.services.IDeathBillDtlService;
import com.zd.foster.dto.DeathAnalysis;

/**
 * 类名：  DeathBillDtlServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-8-1下午08:32:28
 * @version 1.0
 * 
 */
public class DeathBillDtlServiceImpl extends BaseServiceImpl<DeathBillDtl, IDeathBillDtlDao> implements
		IDeathBillDtlService {
	@Resource
	private JDBCWrapperx jdbc;
	
	/**
	 * 查询死亡报表
	 * @Description:TODO
	 * @param entity
	 * @param pageBean
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-17 上午11:14:07
	 */
	public List<DeathAnalysis> selectDeathAnalysisByPage(DeathBillDtl entity,Pager<DeathBillDtl> page)throws Exception{
		List<DeathAnalysis> das = new ArrayList<DeathAnalysis>();
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		
		if(entity.getDeathBill().getCompany()!=null && entity.getDeathBill().getCompany().getId()!=null && !"".equals(entity.getDeathBill().getCompany().getId()))
			sqlMap.put("deathBill.company.id", "=", entity.getDeathBill().getCompany().getId());
		else
			throw new SystemException("请选择养殖公司");
		if(entity.getDeathBill().getFarmer()!=null && entity.getDeathBill().getFarmer().getId()!=null && !"".equals(entity.getDeathBill().getFarmer().getId()))
			sqlMap.put("deathBill.farmer.id", "=", entity.getDeathBill().getFarmer().getId());
		if(entity.getDeathBill().getBatch()!=null && entity.getDeathBill().getBatch().getId()!=null && !"".equals(entity.getDeathBill().getBatch().getId()))
			sqlMap.put("deathBill.batch.id", "=", entity.getDeathBill().getBatch().getId());
		if(entity.getDeathBill().getTechnician()!=null && entity.getDeathBill().getTechnician().getId()!=null && !"".equals(entity.getDeathBill().getTechnician().getId()))
			sqlMap.put("deathBill.technician.id", "=", entity.getDeathBill().getTechnician().getId());
		if(entity.getDeathBill().getIsReceipt()!=null && !"".equals(entity.getDeathBill().getIsReceipt()))
			sqlMap.put("deathBill.isReceipt", "=", entity.getDeathBill().getIsReceipt());
		Map<String, String> ts = entity.getTempStack();
		if (null != ts) {
			//开始时间 
			String startDate = ts.get("startTime");
			if (null != startDate && !"".equals(startDate)) {
				sqlMap.put("deathBill.registDate", ">=", startDate);
			}
			//结束时间
			String endDate = ts.get("endTime");
			if (null != endDate && !"".equals(endDate)) {
				sqlMap.put("deathBill.registDate", "<=", endDate);
			}
		}
		sqlMap.put("deathBill.batch.contract.feedFac.id,deathBill.registDate", "order by", "desc");
		//设置排序字节id
		page.setSortName("id");
		page.setSortorder("desc");
		selectHQL(sqlMap, page);
		List<DeathBillDtl> dbds = page.getResult();
		if(dbds != null && dbds.size()>0){
			for(DeathBillDtl dbd : dbds){
				DeathAnalysis da = new DeathAnalysis();
				da.setCompany(dbd.getDeathBill().getCompany().getName());
				String[] dates = dbd.getDeathBill().getRegistDate().split("-");
				da.setYear(dates[0]);
				da.setMonth(dates[1]);
				da.setDeathDate(dbd.getDeathBill().getRegistDate());
				da.setFarmer(dbd.getDeathBill().getFarmer().getName());
				da.setBatch(dbd.getDeathBill().getBatch().getBatchNumber());
				da.setQuantity("1");
				da.setWeight(dbd.getWeight());
				if("Y".equals(dbd.getDeathBill().getIsCorDeath()))
					da.setDeathAffiliation("养殖公司");
				else
					da.setDeathAffiliation("代养户");
				da.setReason(dbd.getReason().getName());
				da.setReasonType(dbd.getReason().getDeathReasonType().getName());
				da.setAcuteChronic(dbd.getReason().getAcuteChronic().getValue());
				if("Y".equals(dbd.getDeathBill().getIsReceipt()))
					da.setReceipt("已回执");
				else
					da.setReceipt("未回执");
				da.setTechnician(dbd.getDeathBill().getTechnician().getName());
				da.setFeedFac(dbd.getDeathBill().getBatch().getContract().getFeedFac().getName());
				
				das.add(da);
			}
		}
		//合计
		String totalWeight = "0";
		String totalQuan = "0";
		float[] total = selectDeathRown(entity);
		totalWeight = Float.toString(total[1]);
		totalQuan = Float.toString(total[0]);
		
//		List<DeathBillDtl> deathBillDtls = selectHQL(sqlMap);
//		if(deathBillDtls!=null && deathBillDtls.size()>0){
//			for(DeathBillDtl dbd : deathBillDtls){
//				totalWeight = ArithUtil.add(totalWeight, dbd.getWeight());
//			}
//			totalQuan = deathBillDtls.size()+"";
//		}
		DeathAnalysis da = new DeathAnalysis();
		da.setBatch("合计");
		da.setQuantity(totalQuan);
		da.setWeight(totalWeight);
		das.add(da);
		
		return das;
	}
	
	/**
	 * 查询死亡总头数与总重量
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * float[]
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-20 上午09:12:26
	 */
	public float[] selectDeathRown(DeathBillDtl entity)throws Exception{
		int totalQuan = 0;
		float totalWeight = 0;
		String fromSql = " from FS_DEATHBILLDTL t,fs_deathbill d ";
		String whereSql = " where t.deathbill=d.id ";
		// 查询条件
		if(entity.getDeathBill().getCompany()!=null && entity.getDeathBill().getCompany().getId()!=null && !"".equals(entity.getDeathBill().getCompany().getId()))
			whereSql += " and d.company= '"+entity.getDeathBill().getCompany().getId()+"' ";
		if(entity.getDeathBill().getFarmer()!=null && entity.getDeathBill().getFarmer().getId()!=null && !"".equals(entity.getDeathBill().getFarmer().getId()))
			whereSql += " and d.farmer= '"+entity.getDeathBill().getFarmer().getId()+"' ";
		if(entity.getDeathBill().getBatch()!=null && entity.getDeathBill().getBatch().getId()!=null && !"".equals(entity.getDeathBill().getBatch().getId()))
			whereSql += " and d.batch= '"+entity.getDeathBill().getBatch().getId()+"' ";
		if(entity.getDeathBill().getTechnician()!=null && entity.getDeathBill().getTechnician().getId()!=null && !"".equals(entity.getDeathBill().getTechnician().getId()))
			whereSql += " and d.technician= '"+entity.getDeathBill().getTechnician().getId()+"' ";
		if(entity.getDeathBill().getIsReceipt()!=null && !"".equals(entity.getDeathBill().getIsReceipt()))
			whereSql += " and d.isreceipt= '"+entity.getDeathBill().getIsReceipt()+"' ";
		Map<String, String> ts = entity.getTempStack();
		if (null != ts) {
			//开始时间 
			String startDate = ts.get("startTime");
			if (null != startDate && !"".equals(startDate)) {
				whereSql += " and d.registdate>='" + startDate + "'";
			}
			//结束时间
			String endDate = ts.get("endTime");
			if (null != endDate && !"".equals(endDate)) {
				whereSql += " and d.registdate<='" + endDate + "'";
			}
		}
		
		String countSql = "select count(1),sum(t.weight) " + fromSql.toString() + whereSql.toString(); // 统计总条数sql语句
		
		Connection con = null;
		try {
			con = jdbc.getConnection();
			Object[][] rows = jdbc.doQuerry(countSql, con);
			if (null != rows && rows.length > 1){
				totalQuan = Integer.parseInt(rows[1][0].toString());
				totalWeight = Float.parseFloat(rows[1][1]==null?"0.0":rows[1][1].toString());
			}
		} finally {
			jdbc.destroy(con);
		}
			
		float[] total = {totalQuan,totalWeight};
		return total;
	}
	
	
	
	/**
	 * 导出死亡报表
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-17 下午04:19:36
	 */
	public InputStream exportBook(DeathBillDtl entity)throws Exception{
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("死亡报表");
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		//设置表头样式
		HSSFCellStyle headerStyle = ExcelUtil.getHeaderStyle(workbook);
		//设置表格样式
		HSSFCellStyle cellStyle = ExcelUtil.getCellStyle(workbook);
		String exportFields = "年,月,日期,区域,代养户,批次,数量,重量,死亡归属,死亡原因,死因类别,急慢性,技术员,回执状态";
		
		String[] fields = exportFields.split(",");
		// 生成表头标题行
		int cellIndex = -1;
		for (String field : fields) {
			ExcelUtil.setCellValue(sheet, headerStyle, 0, ++cellIndex, field);
		}
		// 生成数据行
		int index = 0;
		List<DeathAnalysis> datas = selectDeathAnalysis(entity);
		if (null != datas && datas.size() > 0) {
			for (DeathAnalysis obj : datas) {
				index ++;
				HSSFRow row = sheet.createRow(index);
				HSSFCell cell = row.createCell(0);
				cell.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell, obj.getYear());
				
				HSSFCell cell1 = row.createCell(1);
				cell1.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell1, obj.getMonth());
				
				HSSFCell cell2 = row.createCell(2);
				cell2.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell2, obj.getDeathDate());
				
				HSSFCell cell3 = row.createCell(3);
				cell3.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell3, obj.getFeedFac());
				
				HSSFCell cell4 = row.createCell(4);
				cell4.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell4, obj.getFarmer());
				
				HSSFCell cell5 = row.createCell(5);
				cell5.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell5, obj.getBatch());
				
				HSSFCell cell6 = row.createCell(6);
				cell6.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell6, obj.getQuantity());
				
				HSSFCell cell7 = row.createCell(7);
				cell7.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell7, obj.getWeight());
				
				HSSFCell cell8 = row.createCell(8);
				cell8.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell8, obj.getDeathAffiliation());
				
				HSSFCell cell9 = row.createCell(9);
				cell9.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell9, obj.getReason());
				
				HSSFCell cell10 = row.createCell(10);
				cell10.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell10, obj.getReasonType());
				
				HSSFCell cell11 = row.createCell(11);
				cell11.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell11, obj.getAcuteChronic());
				
				HSSFCell cell12 = row.createCell(12);
				cell12.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell12, obj.getTechnician());
				
				HSSFCell cell13 = row.createCell(13);
				cell13.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell13, obj.getReceipt());
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
	 * 查询死亡报表不分页
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * List<DeathAnalysis>
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-17 下午04:45:48
	 */
	private List<DeathAnalysis> selectDeathAnalysis(DeathBillDtl entity) throws Exception {
		List<DeathAnalysis> das = new ArrayList<DeathAnalysis>();
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		
		if(entity.getDeathBill().getCompany()!=null && entity.getDeathBill().getCompany().getId()!=null && !"".equals(entity.getDeathBill().getCompany().getId()))
			sqlMap.put("deathBill.company.id", "=", entity.getDeathBill().getCompany().getId());
		if(entity.getDeathBill().getFarmer()!=null && entity.getDeathBill().getFarmer().getId()!=null && !"".equals(entity.getDeathBill().getFarmer().getId()))
			sqlMap.put("deathBill.farmer.id", "=", entity.getDeathBill().getFarmer().getId());
		if(entity.getDeathBill().getBatch()!=null && entity.getDeathBill().getBatch().getId()!=null && !"".equals(entity.getDeathBill().getBatch().getId()))
			sqlMap.put("deathBill.batch.id", "=", entity.getDeathBill().getBatch().getId());
		if(entity.getDeathBill().getTechnician()!=null && entity.getDeathBill().getTechnician().getId()!=null && !"".equals(entity.getDeathBill().getTechnician().getId()))
			sqlMap.put("deathBill.technician.id", "=", entity.getDeathBill().getTechnician().getId());
		if(entity.getDeathBill().getIsReceipt()!=null && !"".equals(entity.getDeathBill().getIsReceipt()))
			sqlMap.put("deathBill.isReceipt", "=", entity.getDeathBill().getIsReceipt());
		Map<String, String> ts = entity.getTempStack();
		if (null != ts) {
			//开始时间 
			String startDate = ts.get("startTime");
			if (null != startDate && !"".equals(startDate)) {
				sqlMap.put("deathBill.registDate", ">=", startDate);
			}
			//结束时间
			String endDate = ts.get("endTime");
			if (null != endDate && !"".equals(endDate)) {
				sqlMap.put("deathBill.registDate", "<=", endDate);
			}
		}
		sqlMap.put("id", "order by", "desc");
		sqlMap.put("deathBill.batch.contract.feedFac.id,deathBill.registDate", "order by", "desc");
		List<DeathBillDtl> dbds = selectHQL(sqlMap);
		//合计
		String totalWeight = "0";
		String totalQuan = "0";
		if(dbds != null && dbds.size()>0){
			for(DeathBillDtl dbd : dbds){
				DeathAnalysis da = new DeathAnalysis();
				da.setCompany(dbd.getDeathBill().getCompany().getName());
				String[] dates = dbd.getDeathBill().getRegistDate().split("-");
				da.setYear(dates[0]);
				da.setMonth(dates[1]);
				da.setDeathDate(dbd.getDeathBill().getRegistDate());
				da.setFarmer(dbd.getDeathBill().getFarmer().getName());
				da.setBatch(dbd.getDeathBill().getBatch().getBatchNumber());
				da.setQuantity("1");
				da.setWeight(dbd.getWeight());
				if("Y".equals(dbd.getDeathBill().getIsCorDeath()))
					da.setDeathAffiliation("养殖公司");
				else
					da.setDeathAffiliation("代养户");
				da.setReason(dbd.getReason().getName());
				da.setReasonType(dbd.getReason().getDeathReasonType().getName());
				da.setAcuteChronic(dbd.getReason().getAcuteChronic().getValue());
				if("Y".equals(dbd.getDeathBill().getIsReceipt()))
					da.setReceipt("已回执");
				else
					da.setReceipt("未回执");
				da.setTechnician(dbd.getDeathBill().getTechnician().getName());
				da.setFeedFac(dbd.getDeathBill().getBatch().getContract().getFeedFac().getName());
				
				totalWeight = ArithUtil.add(totalWeight, dbd.getWeight());
				das.add(da);
			}
			totalQuan = dbds.size()+"";
		}
		DeathAnalysis da = new DeathAnalysis();
		da.setBatch("合计");
		da.setQuantity(totalQuan);
		da.setWeight(totalWeight);
		das.add(da);
		
		return das;
	}
	
	
	
}
