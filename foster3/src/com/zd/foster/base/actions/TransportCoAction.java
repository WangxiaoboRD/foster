/**
 * 功能:
 * @author:wxb
 * @data:2017-7-19下午03:51:31
 * @file:TransportCoAction.java
 */
package com.zd.foster.base.actions;

import com.zd.epa.base.BaseAction;
import com.zd.foster.base.entity.TransportCo;
import com.zd.foster.base.services.ITransportCoService;

/**
 * 类名：  TransportCoAction
 * 功能：
 * @author wxb
 * @date 2017-7-19下午03:51:31
 * @version 1.0
 * 
 */
public class TransportCoAction extends BaseAction<TransportCo, ITransportCoService> {

	private static final long serialVersionUID = 1L;
	
	public String loadByFarmer()throws Exception{
		
		elist = service.selectAllByFarmer(e);
		result.put("Rows", elist);
		//result.put("Total", elist.size());
		return JSON;
	}

}
