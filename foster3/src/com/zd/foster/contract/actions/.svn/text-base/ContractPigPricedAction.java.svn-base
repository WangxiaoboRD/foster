package com.zd.foster.contract.actions;

import java.util.List;

import com.zd.epa.base.BaseAction;
import com.zd.foster.contract.entity.ContractPigPriced;
import com.zd.foster.contract.services.IContractPigPricedService;

/**
 * 类名：  ContractPigPricedAction
 * 功能：
 * @author 杜中良
 * @date 2017-7-19下午02:18:34
 * @version 1.0
 * 
 */
public class ContractPigPricedAction extends BaseAction<ContractPigPriced, IContractPigPricedService> {
	/**
	 * 获取当前库存
	 * 功能：<br/>
	 *
	 * @author DZL
	 * @version 2015-3-14 下午03:06:48 <br/>
	 */
	public String loadDetails() throws Exception{
		List<ContractPigPriced> lpd = service.loadDetails(e);
		result.put("Rows", lpd);
		result.put("Total", lpd.size());
		return JSON;
	}
	
	/**
	 * 获取当前库存
	 * 功能：<br/>
	 *
	 * @author DZL
	 * @version 2015-3-14 下午03:06:48 <br/>
	 */
	public String loadByBatch() throws Exception{
		elist = service.selectAllByBatch(e);
		result.put("Rows", elist);
		//result.put("Total", elist.size());
		return JSON;
	}
}
