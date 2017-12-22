/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午04:31:18
 * @file:DrugOutWareDtlServiceImpl.java
 */
package com.zd.foster.warehouse.services.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.bussobj.entity.BussinessEleDetail;
import com.zd.epa.bussobj.services.IBussinessEleDetailService;
import com.zd.epa.utils.ArithUtil;
import com.zd.epa.utils.JDBCWrapperx;
import com.zd.foster.dto.DrugInOut;
import com.zd.foster.warehouse.dao.IDrugOutWareDtlDao;
import com.zd.foster.warehouse.entity.DrugOutWare;
import com.zd.foster.warehouse.entity.DrugOutWareDtl;
import com.zd.foster.warehouse.services.IDrugOutWareDtlService;

/**
 * 类名：  DrugOutWareDtlServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-7-26下午04:31:18
 * @version 1.0
 * 
 */
public class DrugOutWareDtlServiceImpl extends BaseServiceImpl<DrugOutWareDtl, IDrugOutWareDtlDao>
		implements IDrugOutWareDtlService {
	@Resource
	private JDBCWrapperx jdbc;
	@Resource
	private IBussinessEleDetailService bussinessEleDetailService;
	
	
	/**
	 * 查找药品出库总条数
	 * @Description:TODO
	 * @param entity
	 * @param startRow
	 * @param endRow
	 * @return
	 * @throws Exception
	 * List<DrugInOut>
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-18 上午10:36:01
	 */
	public float[] selectDrugOutWareRown(DrugOutWare entity)throws Exception{
		int totalRows = 0;
		float totalQuan = 0;
		String fromSql = " from FS_DRUGOUTWAREDTL t,fs_drugoutware do,fs_drugbill db,fs_batch batch,fs_drug d ";
		String whereSql = " where t.drugoutware=do.id and db.id=do.drugbill and db.batch=batch.id and t.drug=d.id ";
		// 查询条件
		// 养殖公司
		if(entity.getCompany() !=null && entity.getCompany().getId()!=null && !"".equals(entity.getCompany().getId())){
			whereSql += " and do.company= '"+entity.getCompany().getId()+"' ";
		}
		//入库日期
		if (entity.getTempStack().get("startTime")!=null && !"".equals(entity.getTempStack().get("startTime")) ){
			whereSql += " and do.registdate>='" + entity.getTempStack().get("startTime") + "'";
		}
		if (entity.getTempStack().get("endTime")!=null && !"".equals(entity.getTempStack().get("endTime")) ){
			whereSql += " and do.registdate<='" + entity.getTempStack().get("endTime") + "'";
		}
		//代养户
		if(entity.getFarmer() !=null && entity.getFarmer().getId()!=null && !"".equals(entity.getFarmer().getId())){
			whereSql += " and do.farmer= '"+entity.getFarmer().getId()+"' ";
		}
		//批次
		if(entity.getBatch() !=null && entity.getBatch().getId()!=null && !"".equals(entity.getBatch().getId())){
			whereSql += " and do.batch= '"+entity.getBatch().getId()+"' ";
		}
		//回执
		if(entity.getTempStack().get("isReceipt") !=null && !"".equals(entity.getTempStack().get("isReceipt"))){
			whereSql += " and db.isreceipt= '"+entity.getTempStack().get("isReceipt")+"' ";
		}
		//药品名称
		if (entity.getTempStack().get("drugname")!=null && !"".equals(entity.getTempStack().get("drugname")) ){
			whereSql += " and d.name= '" + entity.getTempStack().get("drugname") + "'";
		}
		//药品编码
		if (entity.getTempStack().get("drugcode")!=null && !"".equals(entity.getTempStack().get("drugcode")) ){
			whereSql += " and d.code= '" + entity.getTempStack().get("drugcode") + "'";
		}
		
		String countSql = "select count(1),sum(t.quantity) " + fromSql.toString() + whereSql.toString(); // 统计总条数sql语句
		
		Connection con = null;
		try {
			con = jdbc.getConnection();
			Object[][] rows = jdbc.doQuerry(countSql, con);
			if (null != rows && rows.length > 1){
				totalRows = Integer.parseInt(rows[1][0].toString());
				totalQuan = Float.parseFloat(rows[1][1]==null?"0.0":rows[1][1].toString());
			}
		} finally {
			jdbc.destroy(con);
		}
			
		float[] total = {totalRows,totalQuan};
		return total;
	}
	
	/**
	 * 查找药品出库分页
	 * @Description:TODO
	 * @param entity
	 * @param startRow
	 * @param endRow
	 * @return
	 * @throws Exception
	 * List<DrugInOut>
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-18 上午10:36:01
	 */
	public List<DrugInOut> selectDrugOutWareByPage(DrugOutWare entity,int startRow,int endRow)throws Exception{
		StringBuilder sqlSb = new StringBuilder();
		sqlSb.append("select company.name as cname,do.registdate as rdate,drug.name as dname,drug.code as dcode,drug.spec as spec," +
				"drug.unit as unit,drug.supplier as supplier,tec.name as tname,farmer.name as fname,db.isreceipt as hz," +
				"t.quantity as quan,bat.batchnumber as bat ");
		String fromSql = " from FS_DRUGOUTWAREDTL t,fs_drugoutware do,fs_drugbill db,fs_company company,fs_drug drug,fs_technician tec," +
				"fs_farmer farmer,fs_batch bat ";
		String whereSql = " where t.drugoutware=do.id and db.id=do.drugbill and do.company=company.id and t.drug=drug.id and " +
				"tec.id(+)=bat.technician and db.farmer=farmer.id and db.batch=bat.id ";
		String orderSql = " order by t.id desc";
		// 查询条件
		// 养殖公司
		if(entity.getCompany() !=null && entity.getCompany().getId()!=null && !"".equals(entity.getCompany().getId())){
			whereSql += " and do.company= '"+entity.getCompany().getId()+"' ";
		}
		//入库日期
		if (entity.getTempStack().get("startTime")!=null && !"".equals(entity.getTempStack().get("startTime")) ){
			whereSql += " and do.registdate>='" + entity.getTempStack().get("startTime") + "'";
		}
		if (entity.getTempStack().get("endTime")!=null && !"".equals(entity.getTempStack().get("endTime")) ){
			whereSql += " and do.registdate<='" + entity.getTempStack().get("endTime") + "'";
		}
		//代养户
		if(entity.getFarmer() !=null && entity.getFarmer().getId()!=null && !"".equals(entity.getFarmer().getId())){
			whereSql += " and do.farmer= '"+entity.getFarmer().getId()+"' ";
		}
		//批次
		if(entity.getBatch() !=null && entity.getBatch().getId()!=null && !"".equals(entity.getBatch().getId())){
			whereSql += " and do.batch= '"+entity.getBatch().getId()+"' ";
		}
		//回执
		if(entity.getTempStack().get("isReceipt") !=null && !"".equals(entity.getTempStack().get("isReceipt"))){
			whereSql += " and db.isreceipt= '"+entity.getTempStack().get("isReceipt")+"' ";
		}
		//药品名称
		if (entity.getTempStack().get("drugname")!=null && !"".equals(entity.getTempStack().get("drugname")) ){
			whereSql += " and drug.name= '" + entity.getTempStack().get("drugname") + "'";
		}
		//药品编码
		if (entity.getTempStack().get("drugcode")!=null && !"".equals(entity.getTempStack().get("drugcode")) ){
			whereSql += " and drug.code= '" + entity.getTempStack().get("drugcode") + "'";
		}
		
		sqlSb.append(fromSql).append(whereSql).append(orderSql);
		// 返回结果集
		List<DrugInOut> drugInOuts = new ArrayList<DrugInOut>();
		// 分页查询sql语句
		String pageSql = "select * from (select row_.*, rownum rownum_ from (" + sqlSb.toString() + ") row_) where rownum_ <= " + endRow + " and rownum_ > " + startRow; 
		logger.info("药品出库报表sql语句：" + pageSql);
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = jdbc.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(pageSql);
			while(rs.next()) {
				DrugInOut dio = new DrugInOut();
				String date = rs.getString("rdate");
				String[] rdate = date.split("-");
				dio.setYear(rdate[0]);
				dio.setMonth(rdate[1]);
				dio.setDate(date);
				dio.setType("出库");
				dio.setName(rs.getString("dname"));
				dio.setCode(rs.getString("dcode"));
				dio.setSpec(rs.getString("spec"));
				BussinessEleDetail unit = bussinessEleDetailService.selectBySinglet("dcode", rs.getString("unit"));
				dio.setUnit(unit.getValue());
				dio.setSupplier(rs.getString("supplier"));
				dio.setFarmer(rs.getString("fname"));
				dio.setBatch(rs.getString("bat"));
				dio.setTechnician(rs.getString("tname"));
				if("Y".equals(rs.getString("hz")))
					dio.setReceipt("已回执");
				if("N".equals(rs.getString("hz")))
					dio.setReceipt("未回执");
				dio.setQuantity(ArithUtil.scale(rs.getString("quan"), 2));
				dio.setCompany(rs.getString("cname"));
				
				drugInOuts.add(dio);
			}
		} finally {
			if (null != st) {
				st.close();
			}
			if (null != rs) {
				rs.close();
			}
			jdbc.destroy(con);
		} 
		
		return drugInOuts;
	}
	
	
	
	
	
}
