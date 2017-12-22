package com.zd.foster.breed.actions;

import com.zd.epa.base.BaseAction;
import com.zd.epa.permission.entity.Users;
import com.zd.epa.utils.SysContainer;
import com.zd.foster.base.entity.Company;
import com.zd.foster.breed.entity.FeedUse;
import com.zd.foster.breed.services.IFeedUseService;

/**
 * 类名：  FeedUseAction
 * 功能：
 * @author 杜中良
 * @date 2017-7-19下午02:18:34
 * @version 1.0
 * 
 */
public class FeedUseAction extends BaseAction<FeedUse, IFeedUseService> {
	
	/**
	 * 
	 * 功能:审核
	 * @author:DZL
	 * @data:2017-7-31下午04:50:51
	 * @file:FeedBillAction.java
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
	 * @author:DZL
	 * @data:2017-7-31下午04:50:41
	 * @file:FeedBillAction.java
	 * @return
	 * @throws Exception
	 */
	public String cancelCheck()throws Exception{	
		service.cancelCheck(id);
		message = "1";		
		text(message);
		return NONE;
	}
	
	public String getUser(){
		Users u = SysContainer.get();
		String companyid = "";
		if(u != null  && u.getCompany() != null){
			companyid = u.getCompany().getId();
		}
		String technicianId = "";
		if(u.getTechnician() != null)
			technicianId = u.getTechnician().getId()+"";
		text(technicianId+","+companyid);
		return NONE;
	}
}
