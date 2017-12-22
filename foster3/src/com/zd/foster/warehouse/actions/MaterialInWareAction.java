/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午04:45:41
 * @file:MaterialInWareAction.java
 */
package com.zd.foster.warehouse.actions;

import com.zd.epa.base.BaseAction;
import com.zd.foster.warehouse.entity.MaterialInWare;
import com.zd.foster.warehouse.services.IMaterialInWareService;

/**
 * 类名：  MaterialInWareAction
 * 功能：
 * @author wxb
 * @date 2017-7-26下午04:45:41
 * @version 1.0
 * 
 */
public class MaterialInWareAction extends BaseAction<MaterialInWare, IMaterialInWareService> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * 功能:审核
	 * @author:wxb
	 * @data:2017-7-29下午03:43:52
	 * @file:MaterialInWareAction.java
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
	 * @data:2017-7-29下午03:44:00
	 * @file:MaterialInWareAction.java
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
