package com.zd.foster.balance.actions;

import com.zd.epa.base.BaseAction;
import com.zd.foster.balance.entity.FarmerSaleIncome;
import com.zd.foster.balance.services.IFarmerSaleIncomeService;

/**
 * 类名：  FarmerSaleIncomeAction
 * 功能：
 * @author dzl
 * @date 2017-7-19下午02:56:58
 * @version 1.0
 * 
 */
public class FarmerSaleIncomeAction extends BaseAction<FarmerSaleIncome, IFarmerSaleIncomeService> {

	/**
	 * 查询对象
	 * @return
	 * @throws Exception
	 */
	public String loadByFarmerSaleIncome()throws Exception{
		
		elist = service.selectFarmerSaleIncome(e);
		result.put("Rows", elist);
		//result.put("Total", elist.size());
		return JSON;
	}

}
