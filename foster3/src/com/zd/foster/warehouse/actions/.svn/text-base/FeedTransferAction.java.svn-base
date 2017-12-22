/**
 * 功能:
 * @author:wxb
 * @data:2017-9-5下午04:48:59
 * @file:FeedTransferAction.java
 */
package com.zd.foster.warehouse.actions;

import com.zd.epa.base.BaseAction;
import com.zd.foster.warehouse.entity.FeedTransfer;
import com.zd.foster.warehouse.services.IFeedTransferService;

/**
 * 类名：  FeedTransferAction
 * 功能：
 * @author wxb
 * @date 2017-9-5下午04:48:59
 * @version 1.0
 * 
 */
public class FeedTransferAction extends BaseAction<FeedTransfer, IFeedTransferService> {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 功能:审核
	 * @author:wxb
	 * @data:2017-9-6下午02:22:58
	 * @file:FeedTransferAction.java
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
	 * 
	 * 功能:撤销
	 * @author:wxb
	 * @data:2017-9-6下午02:23:28
	 * @file:FeedTransferAction.java
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
