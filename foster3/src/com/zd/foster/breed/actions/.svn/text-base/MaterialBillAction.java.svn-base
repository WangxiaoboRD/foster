/**
 * 功能:
 * @author:wxb
 * @data:2017-8-1下午03:34:15
 * @file:MaterialBillAction.java
 */
package com.zd.foster.breed.actions;

import com.zd.epa.base.BaseAction;
import com.zd.foster.breed.entity.MaterialBill;
import com.zd.foster.breed.services.IMaterialBillService;

/**
 * 类名：  MaterialBillAction
 * 功能：
 * @author wxb
 * @date 2017-8-1下午03:34:15
 * @version 1.0
 * 
 */
public class MaterialBillAction extends BaseAction<MaterialBill, IMaterialBillService> {
	private static final long serialVersionUID = 1L;
	
	public String check() throws Exception{
		String[] idArr=ids.split(",");
		service.check(idArr);
		message=idArr.length+"";
		text(message);
		return NONE;
	}
	public String cancelCheck()throws Exception{
		service.cancelCheck(id);
		message="1";
		text(message);
		return NONE;
	}

}
