/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午03:04:49
 * @file:DrugWarehouseAction.java
 */
package com.zd.foster.warehouse.actions;

import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.PapUtil;
import com.zd.foster.warehouse.entity.DrugWarehouse;
import com.zd.foster.warehouse.services.IDrugWarehouseService;

/**
 * 类名：  DrugWarehouseAction
 * 功能：
 * @author wxb
 * @date 2017-7-26下午03:04:49
 * @version 1.0
 * 
 */
public class DrugWarehouseAction extends BaseAction<DrugWarehouse, IDrugWarehouseService> {
	private static final long serialVersionUID = 1L;

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
	public String exportWarehouse()throws Exception{
		stream = service.exportWarehouse(e);
		docFileName= PapUtil.getFileName("公司药品库存报表.xls");
		return DOWN;
	}
	
	/**
	 * 多个编码加载药品
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-18 下午02:08:46
	 */
	public String loadByCodes()throws Exception{
		
		elist = service.selectByCodes(e);
		result.put("Rows", elist);
		result.put("Total", elist.size());
		return JSON;
	}
}
