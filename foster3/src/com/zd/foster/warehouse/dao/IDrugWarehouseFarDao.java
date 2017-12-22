/**
 * 功能:
 * @author:wxb
 * @data:2017-9-8下午03:09:04
 * @file:DrugWarehouseFarDao.java
 */
package com.zd.foster.warehouse.dao;

import java.util.List;

import com.zd.epa.base.IBaseDao;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.warehouse.entity.DrugWarehouse;
import com.zd.foster.warehouse.entity.DrugWarehouseFar;

/**
 * 类名：  DrugWarehouseFarDao
 * 功能：
 * @author wxb
 * @date 2017-9-8下午03:09:04
 * @version 1.0
 * 
 */
public interface IDrugWarehouseFarDao extends IBaseDao<DrugWarehouseFar> {

	/**
	 * 独特的排序
	 * @param sqlMap
	 * @return
	 */
	List<DrugWarehouseFar> selectHQLSingle(SqlMap<String,String,Object> sqlMap)throws Exception;
}
