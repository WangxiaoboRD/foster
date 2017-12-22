/**
 * 功能:
 * @author:wxb
 * @data:2017-7-19下午03:17:59
 * @file:FeedFacAction.java
 */
package com.zd.foster.base.actions;

import com.zd.epa.base.BaseAction;
import com.zd.foster.base.entity.FeedFac;
import com.zd.foster.base.services.IFeedFacService;

/**
 * 类名：  FeedFacAction
 * 功能：
 * @author wxb
 * @date 2017-7-19下午03:17:59
 * @version 1.0
 * 
 */
public class FeedFacAction extends BaseAction<FeedFac, IFeedFacService> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 查询对象
	 * @return
	 * @throws Exception
	 */
	public String loadByFeedFac()throws Exception{
		elist = service.selectAllByFarmerId(e);
		result.put("Rows", elist);
		//result.put("Total", elist.size());
		return JSON;
	}

}
