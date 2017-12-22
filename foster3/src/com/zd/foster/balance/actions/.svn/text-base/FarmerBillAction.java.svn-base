package com.zd.foster.balance.actions;

import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.TypeUtil;
import com.zd.foster.balance.entity.FarmerBill;
import com.zd.foster.balance.services.IFarmerBillService;

/**
 * 类名：  FarmerBillAction
 * 功能：
 * @author dzl
 * @date 2017-7-19下午02:56:58
 * @version 1.0
 * 
 */
public class FarmerBillAction extends BaseAction<FarmerBill, IFarmerBillService> {


	/**
	 * 更新对象值的前置处理
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 22, 2013 3:22:32 PM <br/>
	 */
	public String loadJustById()throws Exception{
		e =service.selectById(id);
		return "adjust";
	}
	
	/**
	 * 打印内容获取
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version 2015-11-22 下午05:48:46 <br/>
	 */
	public String print()throws Exception{
		elist = service.print(ids);
		result.put("Rows", elist);
		return JSON;
	}
}
