package com.zd.foster.sale.actions;

import com.zd.epa.base.BaseAction;
import com.zd.foster.sale.entity.CompanySale;
import com.zd.foster.sale.services.ICompanySaleService;


/**
 * 类名：  CompanySaleAction
 * 功能：
 * @author 杜中良
 * @date 2017-7-19下午02:18:34
 * @version 1.0
 * 
 */
public class CompanySaleAction extends BaseAction<CompanySale, ICompanySaleService> {
	/**
	 * 单据审核
	 * @return
	 * @throws Exception
	 */
	public String check()throws Exception{	
		String[] idArr = ids.split(",");
		service.check(idArr);
		message = idArr.length + "";		
		text(message);
		return NONE;
	}
	
	/**
	 * 单据撤销
	 * @return
	 * @throws Exception
	 */
	public String cancelCheck()throws Exception{	
		
		service.cancelCheck(id);
		message = "1";		
		text(message);
		return NONE;
	}
}
