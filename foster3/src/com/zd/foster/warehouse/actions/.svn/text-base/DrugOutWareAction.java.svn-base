/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午04:14:41
 * @file:DrugOutWareAction.java
 */
package com.zd.foster.warehouse.actions;

import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.PapUtil;
import com.zd.foster.warehouse.entity.DrugOutWare;
import com.zd.foster.warehouse.services.IDrugOutWareService;

/**
 * 类名：  DrugOutWareAction
 * 功能：
 * @author wxb
 * @date 2017-7-26下午04:14:41
 * @version 1.0
 * 
 */
public class DrugOutWareAction extends BaseAction<DrugOutWare, IDrugOutWareService> {
	private static final long serialVersionUID = 1L;

	/**
	 * 查询药品出入库报表
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-18 上午10:04:56
	 */
	public String loadDrugInOutByPage()throws Exception{
		result = service.selectDrugInOutByPage(e, pageBean);
		result.put("pageBean", pageBean);
		return JSON;
	}
	
	/**
	 * 导出药品出入库报表
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-19 上午08:50:10
	 */
	public String exportDrugInOut()throws Exception{
		stream = service.exportDrugInOut(e);
		docFileName= PapUtil.getFileName("药品出入库报表.xls");
		return DOWN;
	}
	
}
