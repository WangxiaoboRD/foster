/**
 * 功能:
 * @author:wxb
 * @data:2017-9-8下午03:19:43
 * @file:DrugWarehouseFarAction.java
 */
package com.zd.foster.warehouse.actions;

import com.zd.epa.base.BaseAction;
import com.zd.foster.warehouse.entity.DrugWarehouseFar;
import com.zd.foster.warehouse.services.IDrugWarehouseFarService;

/**
 * 类名：  DrugWarehouseFarAction
 * 功能：
 * @author wxb
 * @date 2017-9-8下午03:19:43
 * @version 1.0
 * 
 */
public class DrugWarehouseFarAction extends BaseAction<DrugWarehouseFar, IDrugWarehouseFarService> {

	private static final long serialVersionUID = 1L;

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
