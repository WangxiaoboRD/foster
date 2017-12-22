/**
 * 功能:
 * @author:wxb
 * @data:2017-7-19下午03:37:30
 * @file:CustVenderAction.java
 */
package com.zd.foster.base.actions;

import com.zd.epa.base.BaseAction;
import com.zd.foster.base.entity.CustVender;
import com.zd.foster.base.services.ICustVenderService;

/**
 * 类名：  CustVenderAction
 * 功能：
 * @author wxb
 * @date 2017-7-19下午03:37:30
 * @version 1.0
 * 
 */
public class CustVenderAction extends BaseAction<CustVender, ICustVenderService> {

	private static final long serialVersionUID = 1L;

	/**
	 * 查询对象
	 * @return
	 * @throws Exception
	 */
	public String loadByCustomer()throws Exception{
		elist = service.selectAllByFarmerId(e);
		result.put("Rows", elist);
		//result.put("Total", elist.size());
		return JSON;
	}
	/**
	 * 
	 * 功能:根据代养户查询
	 * @author:wxb
	 * @data:2017-9-11上午10:24:03
	 * @file:CustVenderAction.java
	 * @return
	 * @throws Exception
	 */
	public String loadByFarmer()throws Exception{
		elist = service.selectAllByFarmer(e);
		result.put("Rows", elist);
		//result.put("Total", elist.size());
		return JSON;
	}
}
