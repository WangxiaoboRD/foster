/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午04:13:37
 * @file:DrugOutWareServiceImpl.java
 */
package com.zd.foster.warehouse.services.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.zd.epa.utils.ExcelUtil;
import com.zd.epa.utils.Pager;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.dto.DrugInOut;
import com.zd.foster.warehouse.dao.IDrugOutWareDao;
import com.zd.foster.warehouse.entity.DrugOutWare;
import com.zd.foster.warehouse.services.IDrugInWareDtlService;
import com.zd.foster.warehouse.services.IDrugOutWareDtlService;
import com.zd.foster.warehouse.services.IDrugOutWareService;

/**
 * 类名：  DrugOutWareServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-7-26下午04:13:37
 * @version 1.0
 * 
 */
public class DrugOutWareServiceImpl extends BaseServiceImpl<DrugOutWare, IDrugOutWareDao> implements
		IDrugOutWareService {
	@Resource
	private IDrugOutWareDtlService drugOutWareDtlService;
	@Resource
	private IDrugInWareDtlService drugInWareDtlService;
	/**
	 * 
	 * 功能:删除
	 * 重写:wxb
	 * 2017-7-28
	 * @see com.zd.epa.base.BaseServiceImpl#deleteByIds(ID[])
	 */
	public <ID extends Serializable> int deleteById(ID PK)throws Exception{
		if(PK==null )
			throw new SystemException("请选择删除对象");
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("drugOutWare.id", "=", PK);
		drugOutWareDtlService.delete(sqlMap);
		return dao.deleteById(PK);
	}

	
	/**
	 * 查询药品出入库报表分页
	 * @Description:TODO
	 * @param e
	 * @param page
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-18 上午11:28:10
	 */
	public Map<String, Object> selectDrugInOutByPage(DrugOutWare entity,Pager<DrugOutWare> page)throws Exception{
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		List<DrugInOut> drugInOuts = new ArrayList<DrugInOut>();
		int rown = page.getPageSize();
		int startRow = (page.getPageNo()-1)*rown; // 分页开始行
		int endRow = startRow + rown; // 分页结束行
		int total = 0;
		float total_quan = 0;
		
		if(entity.getCompany()==null || entity.getCompany().getId()==null || "".equals(entity.getCompany().getId()))
			throw new SystemException("请选择养殖公司");
		
		//入库总条数
		float[] total_in = drugInWareDtlService.selectDrugInWareRown(entity);
		int total_inrow = (int) total_in[0];
		float total_inquan = total_in[1];
		//出库总条数
		float[] total_out = drugOutWareDtlService.selectDrugOutWareRown(entity);
		int total_outrow = (int) total_out[0];
		float total_outquan = total_out[1];
		
		if(entity.getTempStack().get("inout")==null || "".equals(entity.getTempStack().get("inout"))){
			total = total_inrow+total_outrow;
			total_quan = total_inquan + total_outquan;
			
			if(total_inrow>0){
				//先查入库
				List<DrugInOut> ins = drugInWareDtlService.selectDrugInWareByPage(entity, startRow, endRow);
				if(ins!=null && ins.size()>0){
					drugInOuts.addAll(ins);
					//若入库完结
					int inrown = ins.size();
					if(inrown<rown ){
						int out_start = 0;
						int out_end = endRow-total_inrow;
						List<DrugInOut> outs = drugOutWareDtlService.selectDrugOutWareByPage(entity, out_start, out_end);
						if(outs!=null && outs.size()>0)
							drugInOuts.addAll(outs);
					}
				}else{
					//若没有入库单
					int out_start = startRow-total_inrow;
					int out_end = endRow-total_inrow;
					List<DrugInOut> outs = drugOutWareDtlService.selectDrugOutWareByPage(entity, out_start, out_end);
					if(outs!=null && outs.size()>0)
						drugInOuts.addAll(outs);
				}
			}else{
				//若没有入库单
				int out_start = startRow;
				int out_end = endRow;
				List<DrugInOut> outs = drugOutWareDtlService.selectDrugOutWareByPage(entity, out_start, out_end);
				if(outs!=null && outs.size()>0)
					drugInOuts.addAll(outs);
			}
		}
		if( "in".equals(entity.getTempStack().get("inout"))){
			total = total_inrow;
			total_quan = total_inquan;
			
			if(total_inrow>0){
				List<DrugInOut> ins = drugInWareDtlService.selectDrugInWareByPage(entity, startRow, endRow);
				if(ins!=null && ins.size()>0)
					drugInOuts.addAll(ins);
			}
		}
		if( "out".equals(entity.getTempStack().get("inout"))){
			total = total_outrow;
			total_quan = total_outquan;
			
			if(total_outrow>0){
				List<DrugInOut> outs = drugOutWareDtlService.selectDrugOutWareByPage(entity, startRow, endRow);
				if(outs!=null && outs.size()>0)
					drugInOuts.addAll(outs);
			}
		}
		
		page.setTotalCount(total);
		
		DrugInOut dio = new DrugInOut();
		dio.setSupplier("合计");
		dio.setQuantity(total_quan+"");
		drugInOuts.add(dio);
		
		result.put("Rows", drugInOuts); // 返回查询结果集
		result.put("Total", total); // 返回数据总条数
		return result;
	}

	/**
	 * 导出药品出入库报表
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-19 上午08:50:10
	 */
	public InputStream exportDrugInOut(DrugOutWare entity)throws Exception{
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("药品出入库报表");
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		//设置表头样式
		HSSFCellStyle headerStyle = ExcelUtil.getHeaderStyle(workbook);
		//设置表格样式
		HSSFCellStyle cellStyle = ExcelUtil.getCellStyle(workbook);
		String exportFields = "年,月,日期,出入库,代养户,批次,药品名称,药品编码,药品规格,药品单位,供应商,数量,技术员,回执状态";
		
		String[] fields = exportFields.split(",");
		// 生成表头标题行
		int cellIndex = -1;
		for (String field : fields) {
			ExcelUtil.setCellValue(sheet, headerStyle, 0, ++cellIndex, field);
		}
		// 生成数据行
		int index = 0;
		List<DrugInOut> datas = selectDrugInOut(entity);
		if (null != datas && datas.size() > 0) {
			for (DrugInOut obj : datas) {
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
				ExcelUtil.setCellValue(cell2, obj.getDate());
				
				HSSFCell cell3 = row.createCell(3);
				cell3.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell3, obj.getType());
				
				HSSFCell cell4 = row.createCell(4);
				cell4.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell4, obj.getFarmer());
				
				HSSFCell cell5 = row.createCell(5);
				cell5.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell5, obj.getBatch());
				
				HSSFCell cell6 = row.createCell(6);
				cell6.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell6, obj.getName());
				
				HSSFCell cell7 = row.createCell(7);
				cell7.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell7, obj.getCode());
				
				HSSFCell cell8 = row.createCell(8);
				cell8.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell8, obj.getSpec());
				
				HSSFCell cell9 = row.createCell(9);
				cell9.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell9, obj.getUnit());
				
				HSSFCell cell10 = row.createCell(10);
				cell10.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell10, obj.getSupplier());
				
				HSSFCell cell11 = row.createCell(11);
				cell11.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell11, obj.getQuantity());
				
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
	 * 查找药品出入库不分页
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * List<DrugInOut>
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-19 上午09:40:01
	 */
	private List<DrugInOut> selectDrugInOut(DrugOutWare entity) throws Exception {
		List<DrugInOut> drugInOuts = new ArrayList<DrugInOut>();
		int total = 0;
		float total_quan = 0;
			
		//入库总条数
		float[] total_in = drugInWareDtlService.selectDrugInWareRown(entity);
		int total_inrow = (int) total_in[0];
		float total_inquan = total_in[1];
		//出库总条数
		float[] total_out = drugOutWareDtlService.selectDrugOutWareRown(entity);
		int total_outrow = (int) total_out[0];
		float total_outquan = total_out[1];
		//入库
		if( "in".equals(entity.getTempStack().get("inout"))){
			total = total_inrow;
			total_quan = total_inquan;
			
			if(total_inrow>0){
				List<DrugInOut> ins = drugInWareDtlService.selectDrugInWareByPage(entity, 0, total_inrow);
				if(ins!=null && ins.size()>0)
					drugInOuts.addAll(ins);
			}
		}
		//出库
		if( "out".equals(entity.getTempStack().get("inout"))){
			total = total_outrow;
			total_quan = total_outquan;
			
			if(total_outrow>0){
				List<DrugInOut> outs = drugOutWareDtlService.selectDrugOutWareByPage(entity, 0, total_outrow);
				if(outs!=null && outs.size()>0)
					drugInOuts.addAll(outs);
			}
		}
		//全部
		if(entity.getTempStack().get("inout")==null || "".equals(entity.getTempStack().get("inout"))){
			total = total_inrow+total_outrow;
			total_quan = total_inquan + total_outquan;
			
			if(total_inrow>0){
				//先查入库
				List<DrugInOut> ins = drugInWareDtlService.selectDrugInWareByPage(entity, 0, total_inrow);
				drugInOuts.addAll(ins);
				List<DrugInOut> outs = drugOutWareDtlService.selectDrugOutWareByPage(entity, 0, total_outrow);
				if(outs!=null && outs.size()>0)
					drugInOuts.addAll(outs);
			}else{
				//若没有入库单
				List<DrugInOut> outs = drugOutWareDtlService.selectDrugOutWareByPage(entity, 0, total_outrow);
				if(outs!=null && outs.size()>0)
					drugInOuts.addAll(outs);
			}
		}
		
		DrugInOut dio = new DrugInOut();
		dio.setSupplier("合计");
		dio.setQuantity(total_quan+"");
		drugInOuts.add(dio);
		
		return drugInOuts;
	}
	
	
	
}
