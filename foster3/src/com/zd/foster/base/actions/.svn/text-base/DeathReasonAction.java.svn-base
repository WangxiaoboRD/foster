/**
 * 功能:
 * @author:wxb
 * @data:2017-9-7下午01:41:21
 * @file:DeathReasonAction.java
 */
package com.zd.foster.base.actions;

import com.zd.epa.base.BaseAction;
import com.zd.foster.base.entity.DeathReason;
import com.zd.foster.base.services.IDeathReasonService;

/**
 * 类名：  DeathReasonAction
 * 功能：
 * @author wxb
 * @date 2017-9-7下午01:41:21
 * @version 1.0
 * 
 */
public class DeathReasonAction extends BaseAction<DeathReason, IDeathReasonService> {
	
	private static final long serialVersionUID = 1L;
	
	private String key; 
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public String loadByPinYin()throws Exception{
		elist = service.selectByPinyin(e,key);
		result.put("Rows", elist);
		//result.put("Total", elist.size());
		return JSON;
	}

}
