/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午03:02:30
 * @file:IDrugWarehouseService.java
 */
package com.zd.foster.warehouse.services;

import java.io.InputStream;
import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.foster.warehouse.entity.DrugWarehouse;

/**
 * 类名：  IDrugWarehouseService
 * 功能：
 * @author wxb
 * @date 2017-7-26下午03:02:30
 * @version 1.0
 * 
 */
public interface IDrugWarehouseService extends IBaseService<DrugWarehouse> {
	/**
	 * 导出公司药品库存
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-17 下午05:41:18
	 */
	InputStream exportWarehouse(DrugWarehouse e)throws Exception;

	List<DrugWarehouse> selectByCodes(DrugWarehouse e) throws Exception;

}
