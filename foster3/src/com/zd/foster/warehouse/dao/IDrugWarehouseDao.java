/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午02:59:46
 * @file:IDrugWarehouseDao.java
 */
package com.zd.foster.warehouse.dao;

import java.util.List;

import com.zd.epa.base.IBaseDao;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.warehouse.entity.DrugWarehouse;

/**
 * 类名：  IDrugWarehouseDao
 * 功能：
 * @author wxb
 * @date 2017-7-26下午02:59:46
 * @version 1.0
 * 
 */
public interface IDrugWarehouseDao extends IBaseDao<DrugWarehouse> {

	/**
	 * 独特的排序
	 * @param sqlMap
	 * @return
	 */
	List<DrugWarehouse> selectHQLSingle(SqlMap<String,String,Object> sqlMap)throws Exception;
}
