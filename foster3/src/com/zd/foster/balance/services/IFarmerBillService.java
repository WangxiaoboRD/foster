package com.zd.foster.balance.services;

import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.foster.balance.entity.FarmerBill;

/**
 * 类名：  IFarmerBillService
 * 功能：
 * @author DZL
 * @date 2017-7-19下午02:13:02
 * @version 1.0
 * 
 */
public interface IFarmerBillService extends IBaseService<FarmerBill> {

	/**
	 * 打印内容获取
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version 2015-11-22 下午05:52:59 <br/>
	 */
	public List<FarmerBill> print(String ids)throws Exception;
}
