package com.zd.epa.log.actions;

import com.zd.epa.base.BaseAction;
import com.zd.epa.log.entity.ModifyLogObject;
import com.zd.epa.log.services.IModifyLogObjectService;

@SuppressWarnings("serial")
public class ModifyLogObjectAction extends BaseAction<ModifyLogObject,IModifyLogObjectService>{
	
	/**
	 * 保存对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 16, 2013 10:44:09 AM <br/>
	 */
	public String saveAll()throws Exception{
		message = service.saveAll(ids);
		text(message);
		return NONE;
	}
}
