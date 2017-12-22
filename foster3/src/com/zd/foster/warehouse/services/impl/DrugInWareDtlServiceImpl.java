/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午03:50:19
 * @file:DrugInWareDtlServiceImpl.java
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
import com.zd.epa.utils.JDBCWrapperx;
import com.zd.foster.dto.DrugInOut;
import com.zd.foster.warehouse.dao.IDrugInWareDtlDao;
import com.zd.foster.warehouse.entity.DrugInWareDtl;
import com.zd.foster.warehouse.entity.DrugOutWare;
import com.zd.foster.warehouse.services.IDrugInWareDtlService;

/**
 * 类名：  DrugInWareDtlServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-7-26下午03:50:19
 * @version 1.0
 * 
 */
public class DrugInWareDtlServiceImpl extends BaseServiceImpl<DrugInWareDtl, IDrugInWareDtlDao> implements
		IDrugInWareDtlService {

	@Resource
	private IBussinessEleDetailService bussinessEleDetailService;
	@Resource
	private JDBCWrapperx jdbc;
	
	/**
	 * 查找药品入库总条数
	 * @Description:TODO
	 * @param entity
	 * @param startRow
	 * @param endRow
	 * @return
	 * @throws Exception
	 * List<DrugInOut>
	 * @exception:
	 * @author: 小丁
	 * @param con 
	 * @time:2017-9-18 上午10:36:01
	 */
	public float[] selectDrugInWareRown(DrugOutWare entity)throws Exception{
		int totalRows = 0;
		float totalQuan = 0;
		String fromSql = " from FS_DRUGINWAREDTL t,fs_druginware di,fs_drug d ";
		String whereSql = " where t.druginware=di.id and t.drug=d.id ";
		// 查询条件
		// 养殖公司
		if(entity.getCompany() !=null && entity.getCompany().getId()!=null && !"".equals(entity.getCompany().getId())){
			whereSql += " and di.company= '"+entity.getCompany().getId()+"' ";
		}
		//入库日期
		if (entity.getTempStack().get("startTime")!=null && !"".equals(entity.getTempStack().get("startTime")) ){
			whereSql += " and di.registdate>='" + entity.getTempStack().get("startTime") + "'";
		}
		if (entity.getTempStack().get("endTime")!=null && !"".equals(entity.getTempStack().get("endTime")) ){
			whereSql += " and di.registdate<='" + entity.getTempStack().get("endTime") + "'";
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
				totalQuan = Float.parseFloat(rows[1][1]==null?"0":rows[1][1].toString());
			}
		} finally {
			jdbc.destroy(con);
		}
			
		float[] total = {totalRows,totalQuan};
		return total;
	}
	
	/**
	 * 查找药品入库分页
	 * @Description:TODO
	 * @param entity
	 * @param startRow
	 * @param endRow
	 * @return
	 * @throws Exception
	 * List<DrugInOut>
	 * @exception:
	 * @author: 小丁
	 * @param rs 
	 * @param st 
	 * @param con 
	 * @time:2017-9-18 上午10:36:01
	 */
	public List<DrugInOut> selectDrugInWareByPage(DrugOutWare entity,int startRow,int endRow )throws Exception{
		StringBuilder sqlSb = new StringBuilder();
		sqlSb.append("select company.name as cname,di.registdate as rdate ,drug.name as dname,drug.code as dcode,drug.spec as spec," +
				"drug.unit as unit,drug.supplier as supplier,t.quantity as quan ");
		String fromSql = " from FS_DRUGINWAREDTL t,fs_druginware di,fs_company company,fs_drug drug ";
		String whereSql = " where t.druginware=di.id and company.id=di.company and t.drug=drug.id ";
		String orderSql = " order by t.id desc";
		// 查询条件
		// 养殖公司
		if(entity.getCompany() !=null && entity.getCompany().getId()!=null && !"".equals(entity.getCompany().getId())){
			whereSql += " and di.company= '"+entity.getCompany().getId()+"' ";
		}
		//入库日期
		if (entity.getTempStack().get("startTime")!=null && !"".equals(entity.getTempStack().get("startTime")) ){
			whereSql += " and di.registdate>='" + entity.getTempStack().get("startTime") + "'";
		}
		if (entity.getTempStack().get("endTime")!=null && !"".equals(entity.getTempStack().get("endTime")) ){
			whereSql += " and di.registdate<='" + entity.getTempStack().get("endTime") + "'";
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
		logger.info("药品出入库报表sql语句：" + pageSql);
		
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
				dio.setType("入库");
				dio.setName(rs.getString("dname"));
				dio.setCode(rs.getString("dcode"));
				dio.setSpec(rs.getString("spec"));
				BussinessEleDetail unit = bussinessEleDetailService.selectBySinglet("dcode", rs.getString("unit"));
				dio.setUnit(unit.getValue());
				dio.setSupplier(rs.getString("supplier"));
				dio.setQuantity(rs.getString("quan"));
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
