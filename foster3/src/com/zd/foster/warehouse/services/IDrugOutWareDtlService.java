/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午04:30:18
 * @file:IDrugOutWareDtlService.java
 */
package com.zd.foster.warehouse.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.epa.utils.JDBCWrapperx;
import com.zd.foster.dto.DrugInOut;
import com.zd.foster.warehouse.entity.DrugOutWare;
import com.zd.foster.warehouse.entity.DrugOutWareDtl;

/**
 * 类名：  IDrugOutWareDtlService
 * 功能：
 * @author wxb
 * @date 2017-7-26下午04:30:18
 * @version 1.0
 * 
 */
public interface IDrugOutWareDtlService extends IBaseService<DrugOutWareDtl> {
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
	public List<DrugInOut> selectDrugOutWareByPage(DrugOutWare entity,int startRow,int endRow)throws Exception;
	
	
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
	public float[] selectDrugOutWareRown(DrugOutWare entity)throws Exception;
	
	
	
	
	
	
	
	
}
