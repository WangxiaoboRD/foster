package com.zd.foster.breed.actions;

import com.zd.epa.base.BaseAction;
import com.zd.epa.exception.SystemException;
import com.zd.epa.permission.entity.Users;
import com.zd.epa.utils.SysContainer;
import com.zd.epa.utils.SysContext;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.Technician;
import com.zd.foster.breed.entity.FeedUseDetail;
import com.zd.foster.breed.services.IFeedUseDetailService;

/**
 * 类名：  FeedUseDetailAction
 * 功能：
 * @author 杜中良
 * @date 2017-7-19下午02:18:34
 * @version 1.0
 * 
 */
public class FeedUseDetailAction extends BaseAction<FeedUseDetail, IFeedUseDetailService> {
	
	/**
	 * 获取批次
	 * @return
	 * @throws Exception
	 */
	public String loadBatch()throws Exception{
		//获取用户信息
		Users u = SysContainer.get();
		//根据用户名获取权限 如果是admin不能有录入权限，
		if(u==null)
			throw new SystemException("无法获取用户");
		elist = service.loadBatch(u,id);
		result.put("Rows", elist);
		return JSON;
	} 
	
	/**
	 * 加载明细
	 * @return
	 * @throws Exception
	 */
	public String loadByDetail()throws Exception{
		//获取用户信息
		Users u = SysContainer.get();
		if(u.getCompany()==null || "".equals(u.getCompany()))
			throw new SystemException("用户不具备修改权限");
		elist = service.selectAll(e);
		result.put("Rows", elist);
		//result.put("Total", elist.size());
		return JSON;
	}
}

