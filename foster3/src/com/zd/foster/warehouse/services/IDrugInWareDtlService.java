/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午03:49:07
 * @file:IDrugInWareDtlService.java
 */
package com.zd.foster.warehouse.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.epa.utils.JDBCWrapperx;
import com.zd.foster.dto.DrugInOut;
import com.zd.foster.warehouse.entity.DrugInWareDtl;
import com.zd.foster.warehouse.entity.DrugOutWare;

/**
 * 类名：  IDrugInWareDtlService
 * 功能：
 * @author wxb
 * @date 2017-7-26下午03:49:07
 * @version 1.0
 * 
 */
public interface IDrugInWareDtlService extends IBaseService<DrugInWareDtl> {
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
	 * @time:2017-9-18 上午10:36:01
	 */
	public List<DrugInOut> selectDrugInWareByPage(DrugOutWare entity,int startRow,int endRow)throws Exception;
	
	
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
	 * @time:2017-9-18 上午10:36:01
	 */
	public float[] selectDrugInWareRown(DrugOutWare entity)throws Exception;
	
	
	
}
