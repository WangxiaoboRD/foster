/**
 * 功能:
 * @author:wxb
 * @data:2017-7-27上午09:17:14
 * @file:FeedBillDtlServiceImpl.java
 */
package com.zd.foster.breed.services.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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
import com.zd.epa.utils.JDBCWrapperx;
import com.zd.epa.utils.Pager;
import com.zd.foster.breed.dao.IFeedBillDtlDao;
import com.zd.foster.breed.entity.FeedBillDtl;
import com.zd.foster.breed.services.IFeedBillDtlService;
import com.zd.foster.dto.DeathAnalysis;
import com.zd.foster.dto.FeedCal;
import com.zd.foster.warehouse.entity.FeedWarehouse;
import com.zd.foster.warehouse.services.IFeedWarehouseService;

/**
 * 类名：  FeedBillDtlServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-7-27上午09:17:14
 * @version 1.0
 * 
 */
public class FeedBillDtlServiceImpl extends BaseServiceImpl<FeedBillDtl, IFeedBillDtlDao> implements
		IFeedBillDtlService {
	@Resource
	private IFeedWarehouseService feedWarehouseService;
	@Resource
	private JDBCWrapperx jdbc;

	/**
	 * 
	 * 功能:修改加载明细时，添加库存数量
	 * 重写:wxb
	 * 2017-7-31
	 * @see com.zd.epa.base.BaseServiceImpl#selectAll(com.zd.epa.base.BaseEntity)
	 */
	@Override
	public List<FeedBillDtl> selectAll(FeedBillDtl entity) throws Exception {
//		SqlMap<String, String, String> sqlMap=new SqlMap<String, String, String>();
		List<FeedBillDtl> fbdList=super.selectAll(entity);
		if(fbdList!=null && fbdList.size()>0){
			Iterator<FeedBillDtl> it= fbdList.iterator();
			while(it.hasNext()){
				FeedBillDtl fbd=it.next();
//				sqlMap.put("feed.id", "=", fbd.getFeed().getId());
//				sqlMap.put("farmer.id", "=", fbd.getFeedBill().getFarmer().getId());
//				List<FeedWarehouse> fwList=feedWarehouseService.selectHQL(sqlMap);
				//直接hql查询????-------------
				String hql="from com.zd.foster.warehouse.entity.FeedWarehouse e where e.feed.id='"+
					fbd.getFeed().getId()+
					"'and e.farmer.id='"+
					fbd.getFeedBill().getFarmer().getId()+
					"'";
				List<FeedWarehouse> fwList=feedWarehouseService.selectByHQL(hql);
				//-----------------------
//				sqlMap.clear();
				if(fwList!=null && fwList.size()>0){
					FeedWarehouse fw=fwList.get(0);
					if(fw.getQuantity()!=null)
						fbd.setStockQuantity(fw.getQuantity());
					if(fw.getSubQuantity()!=null)
						fbd.setStockSubQuantity(fw.getSubQuantity());
				}
			}
		}
		return fbdList;
	}

	@Override
	public InputStream exportBook(FeedBillDtl e,Pager<FeedBillDtl> page) throws Exception {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("喂料分析报表");
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		//设置表头样式
		HSSFCellStyle headerStyle = ExcelUtil.getHeaderStyle(workbook);
		//设置表格样式
		HSSFCellStyle cellStyle = ExcelUtil.getCellStyle(workbook);
		String exportFields = "养殖公司,饲料厂,代养户,批次,技术员,当日存栏(头),当日喂料(kg),当日标准(kg),当日差值(kg),当日差值比例,累积喂料(吨),累积标准(吨),累积差值(吨),累积差值比例,当日库存(吨),合同头数,进猪日期";
		
		String[] fields = exportFields.split(",");
		// 生成表头标题行
		int cellIndex = -1;
		for (String field : fields) {
			ExcelUtil.setCellValue(sheet, headerStyle, 0, ++cellIndex, field);
		}
		// 生成数据行
		int index = 0;
		List<FeedCal> datas = selectFeedCalAnalysis(e,page);
		if (null != datas && datas.size() > 0) {
			for (FeedCal obj : datas) {
				index ++;
				HSSFRow row = sheet.createRow(index);
				HSSFCell cell = row.createCell(0);
				cell.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell, obj.getCompany());
				
				HSSFCell cellX = row.createCell(1);
				cellX.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cellX, obj.getFeedFac());
				
				HSSFCell cell1 = row.createCell(2);
				cell1.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell1, obj.getFarmer());
				
				HSSFCell cell2 = row.createCell(3);
				cell2.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell2, obj.getBatch());
				
				HSSFCell cell3 = row.createCell(4);
				cell3.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell3, obj.getTechnician());
				
				HSSFCell cell4 = row.createCell(5);
				cell4.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell4, obj.getPigNum());
				
				HSSFCell cell5 = row.createCell(6);
				cell5.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell5, obj.getdQty());
				
				HSSFCell cell6 = row.createCell(7);
				cell6.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell6, obj.getDayStd());
				
				HSSFCell cell7 = row.createCell(8);
				cell7.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell7, obj.getDayDif());
				
				HSSFCell cell8 = row.createCell(9);
				cell8.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell8, obj.getDayDifPro());
				
				HSSFCell cell9 = row.createCell(10);
				cell9.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell9, obj.getTolQty());
				
				HSSFCell cell10 = row.createCell(11);
				cell10.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell10, obj.getStdtq());
				
				HSSFCell cell11 = row.createCell(12);
				cell11.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell11, obj.getTolDif());
				
				HSSFCell cell12 = row.createCell(13);
				cell12.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell12, obj.getDifPro());
				
				HSSFCell cell13 = row.createCell(14);
				cell13.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell13, obj.getStock());
				
				HSSFCell cell14 = row.createCell(15);
				cell14.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell14, obj.getContQuan());
				
				HSSFCell cell15 = row.createCell(16);
				cell15.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell15, obj.getPiginDate());
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
		} catch (IOException ex) {
			ex.printStackTrace();
		}finally{
			try {
				if (null != inputStream) {
					inputStream.close();
				}
				if (null != out) {
					out.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return inputStream;
	}
/**
	@Override
	public List<FeedCal> selectFeedCalAnalysisByPage(FeedBillDtl e,
			Pager<FeedBillDtl> page) throws Exception {
		//查询条件
		String searchDate=null, company=null,farmer=null,batch=null,technician=null;
		if(e.getFeedBill().getRegistDate()!=null)
			searchDate=e.getFeedBill().getRegistDate();
		if(e.getFeedBill().getCompany()!=null && e.getFeedBill().getCompany().getId()!=null && !"".equals(e.getFeedBill().getCompany().getId()))
			company=e.getFeedBill().getCompany().getId();
		else
			throw new SystemException("请选择查询公司！");
		if(e.getFeedBill().getFarmer()!=null && e.getFeedBill().getFarmer().getId()!=null && !"".equals(e.getFeedBill().getFarmer().getId()))
			farmer=e.getFeedBill().getFarmer().getId();
		if(e.getFeedBill().getBatch()!=null && e.getFeedBill().getBatch().getId()!=null && !"".equals(e.getFeedBill().getBatch().getId()))
			batch=e.getFeedBill().getBatch().getId()+"";
		if(e.getFeedBill().getTechnician()!=null && e.getFeedBill().getTechnician().getId()!=null && !"".equals(e.getFeedBill().getTechnician().getId()))
			technician=e.getFeedBill().getTechnician().getId()+"";
		

		//死亡查询
		String sql_dead1="select batch,registdate dd,to_number(totalquan) qua from fs_deathbill where registdate <= '"+searchDate+"' and company='"+company+"'";
		//销售查询
		String sql_sale1="select batch,registdate dd,to_number(quantity) qua from fs_outpigsty where registdate <= '"+searchDate+"' and company='"+company+"'";
		//查询日期猪库存
		String sql_a="select batch,to_number(quantity + nvl(givequantity, 0)) quan from fs_pigpurchase where registdate <= '"+searchDate+"' and company='"+company+"'"+
                         " union all"+
                         " select batch, 0-totalquan quan from fs_deathbill where registdate <= '"+searchDate+"' and company='"+company+"'"+
                         " union all"+
                         " select batch, 0-quantity quan from fs_outpigsty where registdate <= '"+searchDate+"' and company='"+company+"'";
		String sql_pigNumber="select batch, '"+searchDate+"' dd, sum(quan) qua from ("+sql_a+")xx group by batch";
		//死亡，销售，在养表
		String sql_con=sql_dead1+" union all "+sql_sale1+" union all "+sql_pigNumber;
		
		//需要查询的在养批次，公司名称，代养户名称，批次号，技术员
//		String sql_tagetBatch="select b.id batch,b.batchnumber btName ,ffac.name ffName,far.name farName,tc.name tcName ,b.contquan contQuan,b.indate piginDate" +
//				" from fs_batch b,fs_contract c,fs_company cpy,fs_farmer far,fs_technician tc,fs_feedfac ffac"+
//                " where b.contract=c.id and c.status='BREED' and b.company = cpy.id and b.farmer=far.id  and c.feedfac=ffac.id and b.technician=tc.id and cpy.id='"+company+"'";
		String sql_tagetBatch="select b.id batch" +
				" from fs_batch b,fs_contract c"+
		        " where b.contract=c.id and c.status='BREED' and b.company='"+company+"'";
		if(farmer!=null)
			sql_tagetBatch+=" and b.farmer='"+farmer+"'";
		if(batch!=null)
			sql_tagetBatch+=" and b.id='"+batch+"'";
		if(technician!=null)
			sql_tagetBatch+=" and b.technician='"+technician+"'";
		//需要查询的猪的进购日期，进购头数，进购日龄
		String sql_purInfo="select batch,min(registdate) indate,min(days) inage from fs_pigpurchase where registdate <= '"+searchDate+"' and company='"+company+"' group by batch";
		//中间表
//		String sql_b="select ffName,farName,btName,tcName,contQuan,piginDate,aa.batch,aa.dd,aa.qua,cc.indate,cc.inage,cc.inage + (to_date(aa.dd, 'yyyy-mm-dd') -to_date(cc.indate, 'yyyy-mm-dd')) outage"+
//                          " from ("+sql_con+")aa,("+sql_tagetBatch+")bb,("+sql_purInfo+")cc"+
//                          " where aa.batch = bb.batch and cc.batch = bb.batch";
		String sql_b="select aa.batch,aa.dd,aa.qua,cc.indate,cc.inage,cc.inage + (to_date(aa.dd, 'yyyy-mm-dd') -to_date(cc.indate, 'yyyy-mm-dd')) outage"+
		        " from ("+sql_con+")aa,("+sql_tagetBatch+")bb,("+sql_purInfo+")cc"+
		        " where aa.batch = bb.batch and cc.batch = bb.batch";
		//中间表"select cpyName,ffName,farName,btName,tcName,contQuan,piginDate,batch,inage,outage,qua,"
//		String sql_c="select ffName,farName,btName,tcName,contQuan,piginDate,batch," +
//				" (select sum(feedweight)"+
//                " from fs_growstanddtl gsd join fs_growstand gs on gs.id=gsd.growstand"+
//                " where to_number(gsd.days) >= to_number(dd.inage)"+
//                " and to_number(gsd.days) <= to_number(dd.outage)"+
//                " and gs.company='"+company+"') * qua ss"+
//                " from ("+sql_b+")dd";
		String sql_c="select batch," +
				" (select sum(feedweight)"+
		        " from fs_growstanddtl gsd join fs_growstand gs on gs.id=gsd.growstand"+
		        " where to_number(gsd.days) >= to_number(dd.inage)"+
		        " and to_number(gsd.days) <= to_number(dd.outage)"+
		        " and gs.company='"+company+"') * qua ss"+
		        " from ("+sql_b+")dd";
		//中间表
//		String sql_d="select ffName, farName, btName, tcName,contQuan,piginDate, batch, sum(ss) as stdTolQty from("+sql_c+")ee"+
//         		" group by batch,ffName, farName, btName, tcName,contQuan,piginDate";
		String sql_d="select  batch, sum(ss) as stdTolQty from("+sql_c+")ee"+
 				" group by batch";
        //实际截止到查询时间喂料量
		String sql_totalQty="select fe.batch, sum(fbd.quantity) as totalQty"+
	            " from fs_feedbilldtl fbd, fs_feedbill fe"+
	            " where fe.id = fbd.feedbill"+
	            " and fe.registdate <= '"+searchDate+"'and fe.company='"+company+"'"+
	            " group by fe.batch";
		//实际转接料量
		String sql_transfer="select sum(ftd.quantity) tranQty,ft.outbatch"+
		        " from fs_feedtransferdtl ftd,fs_feedtransfer ft "+
		        " where ftd.feedtransfer=ft.id "+
		        " and ft.registdate<='"+searchDate+"'and ft.company='"+company+"'"+ 
		        " group by ft.outbatch";
		//截止到查询时间的总领料量
		String sql_totalFed="select fi.batch, sum(fid.quantity) feedIn"+
	            " from fs_feedinwaredtl fid, fs_feedinware fi"+
	            " where fid.feedinware = fi.id"+
	            " and fi.registdate <= '"+searchDate+"'and fi.company='"+company+"'"+
	            " group by fi.batch";
		//查询时间当日实际喂料量
		String sql_dayQty="select fe.batch batch, sum(fbd.quantity) as dayQty"+
	            " from fs_feedbilldtl fbd, fs_feedbill fe"+
	            " where fe.id = fbd.feedbill"+
	            " and fe.registdate = '"+searchDate+"'and fe.company='"+company+"'"+
	            " group by fe.batch";
		
		//查询批次
		String sql_sb="select b.id batch from fs_batch b, fs_contract c where b.contract = c.id and c.status = 'BREED'";
		//查询日标准喂料量
		String sql_dayStd="select kk.batch,kk.qua,"+
                "(select feedweight from fs_growstanddtl"+
                " where days = (mm.inage + (to_date(kk.dd, 'yyyy-mm-dd') -to_date(mm.indate, 'yyyy-mm-dd')))) * kk.qua dayStd"+
	            " from ("+sql_pigNumber+")kk,("+sql_sb+")ll,("+sql_purInfo+")mm " +
	            " where kk.batch = ll.batch and mm.batch = ll.batch";
		//批次号，饲料厂名，代养户名，合同头数，进猪日期
		String sql_name="select b.id  batch,"+
                 " b.batchnumber btName,"+
                 " ffac.name     ffName,"+
                 " far.name      farName,"+
                 " tc.name       tcName,"+
                 " b.contquan    contQuan,"+
                 " b.indate      piginDate"+
            " from fs_batch      b,"+
                 " fs_contract   c , "+             
                 " fs_farmer     far,"+
                 " fs_technician tc,"+
                 " fs_feedfac    ffac"+
           " where b.contract = c.id"+
             " and c.status = 'BREED' "+          
             " and b.farmer = far.id"+
             " and c.feedfac = ffac.id"+
             " and b.technician = tc.id"+
             " and b.company='"+company+"'";
		//最终结果 //decode(dayStd,0,0,round(abs(nvl(dayQty, 0) - dayStd) / dayStd * 100, 2))|| '%' dayDifPro,
		//--round(abs(nvl(totalQty, 0) - stdTolQty) / stdTolQty * 100, 2) || '%' difPro, 
		String sql_end="select ffName,farName,btName,tcName,nn.qua pigNum,"+
		        " nvl(dayQty, 0) dQty,dayStd,abs(nvl(dayQty, 0) - dayStd) dayDif,decode(dayStd,0,0,round(nvl(dayQty, 0) / dayStd * 100, 2))|| '%' dayDifPro,"+
		        " nvl(totalQty, 0)/1000 tolQty,stdTolQty/1000 stdtq,abs(nvl(totalQty, 0) - stdTolQty)/1000 tolDif,round(nvl(totalQty, 0) / stdTolQty * 100, 2) || '%' difPro,"+
		        " (nvl(feedIn, 0) - nvl(totalQty, 0)-nvl(tranQty,0))/1000 stock,contQuan,piginDate"+
		        " from("+sql_d+")gg,("+sql_totalQty+")ff,("+sql_transfer+")pp,("+sql_totalFed+")hh,("+sql_dayQty+")ii,("+sql_dayStd+")nn,("+sql_name+")qq"+
		        " where gg.batch = ff.batch(+)and gg.batch = hh.batch(+)and gg.batch = ii.batch(+)and gg.batch = nn.batch and gg.batch=pp.outbatch(+) and gg.batch = qq.batch" +
		        " and not(nn.qua =0 and  (nvl(feedIn, 0) - nvl(totalQty, 0)-nvl(tranQty,0)) =0 and nvl(dayQty, 0)=0)"+
		        " order by ffName,tcName,piginDate";
		//汇总
		String sql_all="select sum(jj.pigNum),sum(jj.dqty),sum(jj.daystd),sum(jj.daydif),sum(jj.tolqty),sum(jj.stdtq),sum(jj.toldif),sum(jj.stock),sum(jj.contQuan) from("+sql_end+")jj";
		//获取总行数
		String sql_tolNum="select count(*) from ("+sql_end+")";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<FeedCal> fcList = new ArrayList<FeedCal>();
		// 分页查询
		int startRow = (page.getPageNo()-1)*page.getPageSize(); // 分页开始行
		int endRow = startRow + page.getPageSize(); // 分页结束行
		try{			
			conn=jdbc.getConnection();
			ps=conn.prepareStatement(sql_tolNum);
			rs=ps.executeQuery();
			while(rs.next())
				page.setTotalCount(Integer.valueOf(rs.getString(1)));;
			
			ps=conn.prepareStatement(sql_end);
			rs=ps.executeQuery();
			int i=0;
			while(rs.next()){
				if(i>=endRow)
					break;
				else if(i>=startRow && i<endRow){
					FeedCal fc=new FeedCal();
					//fc.setCompany(rs.getString(1));
					fc.setFeedFac(rs.getString(1));
					fc.setFarmer(rs.getString(2));
					fc.setBatch(rs.getString(3));
					fc.setTechnician(rs.getString(4));
					fc.setPigNum(rs.getString(5));
					fc.setDayQty(rs.getString(6));
					fc.setDayStd(rs.getString(7));
					fc.setDayDif(rs.getString(8));
					//
					if(rs.getString(9).equals("%"))
						fc.setDayDifPro(null);
					else
						fc.setDayDifPro(rs.getString(9));
					fc.setTotalQty(rs.getFloat(10)+"");
					//
					String totalStd=rs.getString(11);
					if(totalStd!=null && totalStd.startsWith("."))
						fc.setTotalStd("0"+totalStd);
					else	
						fc.setTotalStd(totalStd);
					//
					String totalDif=rs.getString(12);
					if(totalDif!=null && totalDif.startsWith("."))
						fc.setTotalDif("0"+totalDif);
					else	
						fc.setTotalDif(totalDif);
					//fc.setTotalDif(rs.getString(11));
					fc.setTotalDifPro(rs.getString(13));
					fc.setStock(rs.getString(14));
					fc.setContQuan(rs.getString(15));
					fc.setPiginDate(rs.getString(16));
					fcList.add(fc);
				}
				i++;
			}
//			page.setTotalCount(i);
			//加上合计
			ps=conn.prepareStatement(sql_all);
			rs=ps.executeQuery();
			while(rs.next()){
				FeedCal fc=new FeedCal();
				fc.setFeedFac("合计:");
				fc.setPigNum(rs.getString(1));
				fc.setDayQty(rs.getString(2));
				fc.setDayStd(rs.getString(3));
				fc.setDayDif(rs.getString(4));
				
				fc.setTotalQty(rs.getFloat(5)+"");
				//
				String totalStd=rs.getString(6);
				if(totalStd!=null && totalStd.startsWith("."))
					fc.setTotalStd("0"+totalStd);
				else	
					fc.setTotalStd(totalStd);
				//
				String totalDif=rs.getString(7);
				if(totalDif!=null && totalDif.startsWith("."))
					fc.setTotalDif("0"+totalDif);
				else	
					fc.setTotalDif(totalDif);
				
				fc.setStock(rs.getString(8));
				fc.setContQuan(rs.getString(9));
				fcList.add(fc);
			}
		}finally{
			if(rs!=null)
				rs.close();
			if(ps!=null)
				ps.close();
			if(conn!=null)
				conn.close();
		}
		return fcList;
	}
	*/
	
	public List<FeedCal> selectFeedCalAnalysis(FeedBillDtl e,Pager<FeedBillDtl> page) throws Exception {
		//排序条件
		String sortName=page.getSortName();
		//排列顺序
		String sortOrder=page.getSortorder();
		//查询条件
		String searchDate=null, company=null,farmer=null,batch=null,technician=null;
		if(e.getFeedBill().getRegistDate()!=null)
			searchDate=e.getFeedBill().getRegistDate();
		if(e.getFeedBill().getCompany()!=null && e.getFeedBill().getCompany().getId()!=null && !"".equals(e.getFeedBill().getCompany().getId()))
			company=e.getFeedBill().getCompany().getId();
		else
			throw new SystemException("请选择查询公司！");
		if(e.getFeedBill().getFarmer()!=null && e.getFeedBill().getFarmer().getId()!=null && !"".equals(e.getFeedBill().getFarmer().getId()))
			farmer=e.getFeedBill().getFarmer().getId();
		if(e.getFeedBill().getBatch()!=null && e.getFeedBill().getBatch().getId()!=null && !"".equals(e.getFeedBill().getBatch().getId()))
			batch=e.getFeedBill().getBatch().getId()+"";
		if(e.getFeedBill().getTechnician()!=null && e.getFeedBill().getTechnician().getId()!=null && !"".equals(e.getFeedBill().getTechnician().getId()))
			technician=e.getFeedBill().getTechnician().getId()+"";
		//死亡查询
		String sql_dead1="select batch,registdate dd,to_number(totalquan) qua from fs_deathbill where registdate <= '"+searchDate+"'";
		//销售查询
		String sql_sale1="select batch,registdate dd,to_number(quantity) qua from fs_outpigsty where registdate <= '"+searchDate+"'";
		//查询日期猪库存
		/**批次  +购进头数
		 * 批次  -死亡头数
		 * 批次  -销售头数
		 * **/
		String sql_a="select batch,to_number(quantity + nvl(givequantity, 0)) quan from fs_pigpurchase where registdate <= '"+searchDate+"'"+
                         " union all"+
                         " select batch, 0-totalquan quan from fs_deathbill where registdate <= '"+searchDate+"'"+
                         " union all"+
                         " select batch, 0-quantity quan from fs_outpigsty where registdate <= '"+searchDate+"'";
		String sql_pigNumber="select batch, '"+searchDate+"' dd, sum(quan) qua from ("+sql_a+")xx group by batch";
		//死亡，销售，在养表
		String sql_con=sql_dead1+" union all "+sql_sale1+" union all "+sql_pigNumber;
		
		//需要查询的在养批次，公司名称，代养户名称，批次号，技术员
		String sql_tagetBatch="select b.id batch,b.batchnumber btName ,cpy.name cpyName,ffac.name ffName,far.name farName,tc.name tcName ,b.contquan contQuan,b.indate piginDate" +
				" from fs_batch b,fs_contract c,fs_company cpy,fs_farmer far,fs_technician tc,fs_feedfac ffac"+
                " where b.contract=c.id and c.status='BREED' and b.company = cpy.id and b.farmer=far.id  and c.feedfac=ffac.id and b.technician=tc.id and cpy.id='"+company+"'";
		if(farmer!=null)
			sql_tagetBatch+=" and far.id='"+farmer+"'";
		if(batch!=null)
			sql_tagetBatch+=" and b.id='"+batch+"'";
		if(technician!=null)
			sql_tagetBatch+=" and tc.id='"+technician+"'";
		//需要查询的猪的进购日期，进购头数，进购日龄
		String sql_purInfo="select batch,min(registdate) indate,min(days) inage from fs_pigpurchase group by batch";
		//中间表
		String sql_b="select cpyName,ffName,farName,btName,tcName,contQuan,piginDate,aa.batch,aa.dd,aa.qua,cc.indate,cc.inage,cc.inage + (to_date(aa.dd, 'yyyy-mm-dd') -to_date(cc.indate, 'yyyy-mm-dd')) outage"+
                          " from ("+sql_con+")aa,("+sql_tagetBatch+")bb,("+sql_purInfo+")cc"+
                          " where aa.batch = bb.batch and cc.batch = bb.batch";
		//中间表
		String sql_c="select cpyName,ffName,farName,btName,tcName,contQuan,piginDate,batch,inage,outage,qua," +
				" (select sum(feedweight)"+
                " from fs_growstanddtl gsd join fs_growstand gs on gs.id=gsd.growstand"+
                " where to_number(gsd.days) >= to_number(dd.inage)"+
                " and to_number(gsd.days) <= to_number(dd.outage)"+
                " and gs.company='"+company+"') * qua ss"+
                " from ("+sql_b+")dd";
		//中间表
		String sql_d="select cpyName,ffName, farName, btName, tcName,contQuan,piginDate, batch, sum(ss) as stdTolQty from("+sql_c+")ee"+
         		" group by batch, cpyName,ffName, farName, btName, tcName,contQuan,piginDate";
        //实际截止到查询时间喂料量
		String sql_totalQty="select fe.batch, sum(fbd.quantity) as totalQty"+
	            " from fs_feedbilldtl fbd, fs_feedbill fe"+
	            " where fe.id = fbd.feedbill"+
	            " and fe.registdate <= '"+searchDate+"'"+
	            " group by fe.batch";
		//实际转接料量
		String sql_transfer="select sum(ftd.quantity) tranQty,ft.outbatch"+
		        " from fs_feedtransferdtl ftd,fs_feedtransfer ft "+
		        " where ftd.feedtransfer=ft.id "+
		        " and ft.registdate<='"+searchDate+"'"+ 
		        " group by ft.outbatch";
		//截止到查询时间的总领料量
		String sql_totalFed="select fi.batch, sum(fid.quantity) feedIn"+
	            " from fs_feedinwaredtl fid, fs_feedinware fi"+
	            " where fid.feedinware = fi.id"+
	            " and fi.registdate <= '"+searchDate+"'"+
	            " group by fi.batch";
		//查询时间当日实际喂料量
		String sql_dayQty="select fe.batch batch, sum(fbd.quantity) as dayQty"+
	            " from fs_feedbilldtl fbd, fs_feedbill fe"+
	            " where fe.id = fbd.feedbill"+
	            " and fe.registdate = '"+searchDate+"'"+
	            " group by fe.batch";
		
		//查询批次
		String sql_sb="select b.id batch from fs_batch b, fs_contract c where b.contract = c.id and c.status = 'BREED'";
		//查询日标准喂料量
		String sql_dayStd="select kk.batch,kk.qua,"+
                "(select feedweight from fs_growstanddtl gsd join fs_growstand gs on gs.id=gsd.growstand"+
                " where gsd.days = (mm.inage + (to_date(kk.dd, 'yyyy-mm-dd') -to_date(mm.indate, 'yyyy-mm-dd')))and gs.company='"+company+"') * kk.qua dayStd"+
	            " from ("+sql_pigNumber+")kk,("+sql_sb+")ll,("+sql_purInfo+")mm " +
	            " where kk.batch = ll.batch and mm.batch = ll.batch";
		//最终结果 //decode(dayStd,0,0,round(abs(nvl(dayQty, 0) - dayStd) / dayStd * 100, 2))|| '%' dayDifPro,
		//--round(abs(nvl(totalQty, 0) - stdTolQty) / stdTolQty * 100, 2) || '%' difPro, 
		String sql_end="select cpyName,ffName,farName,btName,tcName,nn.qua pigNum,"+
		        " nvl(dayQty, 0) dQty,dayStd,abs(nvl(dayQty, 0) - dayStd) dayDif,decode(dayStd,0,0,round(nvl(dayQty, 0) / dayStd * 100, 2)) dayDifPro,"+
		        " nvl(totalQty, 0)/1000 tolQty,stdTolQty/1000 stdtq,abs(nvl(totalQty, 0) - stdTolQty)/1000 tolDif,round(nvl(totalQty, 0) / stdTolQty * 100, 2) difPro,"+
		        " (nvl(feedIn, 0) - nvl(totalQty, 0)-nvl(tranQty,0))/1000 stock,to_number(contQuan) contQuan,piginDate"+
		        " from("+sql_d+")gg,("+sql_totalQty+")ff,("+sql_transfer+")pp,("+sql_totalFed+")hh,("+sql_dayQty+")ii,("+sql_dayStd+")nn"+
		        " where gg.batch = ff.batch(+)and gg.batch = hh.batch(+)and gg.batch = ii.batch(+)and gg.batch = nn.batch and gg.batch=pp.outbatch(+)" +
		        " and not(nn.qua =0 and  (nvl(feedIn, 0) - nvl(totalQty, 0)-nvl(tranQty,0)) =0 and nvl(dayQty, 0)=0)";
		if(sortName==null || "".equals(sortName)||"createDate".equals(sortName)||sortOrder==null || "".equals(sortOrder))      
			sql_end+=" order by ffName,tcName,piginDate";
		else if("technician".equals(sortName))
			sql_end+=(" order by tcName "+sortOrder);
		else if("farmer".equals(sortName))
			sql_end+=(" order by farName "+sortOrder);
		else if("feedFac".equals(sortName))
			sql_end+=(" order by ffName "+sortOrder);
		else if("batch".equals(sortName))
			sql_end+=(" order by btName "+sortOrder);
		else
			sql_end+=(" order by "+sortName+" "+sortOrder);
		
			
		//汇总
		String sql_all="select sum(jj.pigNum),sum(jj.dqty),sum(jj.daystd),sum(jj.daydif),sum(jj.tolqty),sum(jj.stdtq),sum(jj.toldif),sum(jj.stock),sum(jj.contQuan) from("+sql_end+")jj";
		
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<FeedCal> fcList = new ArrayList<FeedCal>();
		try{
			conn=jdbc.getConnection();
			ps=conn.prepareStatement(sql_end);
			rs=ps.executeQuery();
			while(rs.next()){
				FeedCal fc=new FeedCal();
				fc.setCompany(rs.getString(1));
				fc.setFeedFac(rs.getString(2));
				fc.setFarmer(rs.getString(3));
				fc.setBatch(rs.getString(4));
				fc.setTechnician(rs.getString(5));
				fc.setPigNum(rs.getString(6));
				fc.setdQty(rs.getString(7));
				fc.setDayStd(rs.getString(8));
				fc.setDayDif(rs.getString(9));
				//
//				if(rs.getString(10).equals("%"))
//					fc.setDayDifPro(null);
//				else
//					fc.setDayDifPro(rs.getString(10));
				fc.setDayDifPro(rs.getString(10));
				fc.setTolQty(rs.getFloat(11)+"");
				//
				String totalStd=rs.getString(12);
				if(totalStd!=null && totalStd.startsWith("."))
					fc.setStdtq("0"+totalStd);
				else	
					fc.setStdtq(totalStd);
				//
				String totalDif=rs.getString(13);
				if(totalDif!=null && totalDif.startsWith("."))
					fc.setTolDif("0"+totalDif);
				else	
					fc.setTolDif(totalDif);
				//fc.setTotalDif(rs.getString(11));
				fc.setDifPro(rs.getString(14));
				fc.setStock(rs.getString(15));
				fc.setContQuan(rs.getString(16));
				fc.setPiginDate(rs.getString(17));
				fcList.add(fc);
			}
			//加上合计
			ps=conn.prepareStatement(sql_all);
			rs=ps.executeQuery();
			while(rs.next()){
				FeedCal fc=new FeedCal();
				fc.setCompany("合计:");
				fc.setPigNum(rs.getString(1));
				fc.setdQty(rs.getString(2));
				fc.setDayStd(rs.getString(3));
				fc.setDayDif(rs.getString(4));
				
				fc.setTolQty(rs.getFloat(5)+"");
				//
				String totalStd=rs.getString(6);
				if(totalStd!=null && totalStd.startsWith("."))
					fc.setStdtq("0"+totalStd);
				else	
					fc.setStdtq(totalStd);
				//
				String totalDif=rs.getString(7);
				if(totalDif!=null && totalDif.startsWith("."))
					fc.setTolDif("0"+totalDif);
				else	
					fc.setTolDif(totalDif);
				
				fc.setStock(rs.getString(8));
				fc.setContQuan(rs.getString(9));
				fcList.add(fc);
			}
		}finally{
			if(rs!=null)
				rs.close();
			if(ps!=null)
				ps.close();
			if(conn!=null)
				conn.close();
		}
		return fcList;
	}
	
	
	

}
