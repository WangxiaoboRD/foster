/**
 * 功能:
 * @author:wxb
 * @data:2017-8-1下午07:56:11
 * @file:EliminateBillAction.java
 */
package com.zd.foster.breed.actions;

import com.zd.epa.base.BaseAction;
import com.zd.foster.breed.entity.EliminateBill;
import com.zd.foster.breed.services.IEliminateBillService;

/**
 * 类名：  EliminateBillAction
 * 功能：
 * @author wxb
 * @date 2017-8-1下午07:56:11
 * @version 1.0
 * 
 */
public class EliminateBillAction extends BaseAction<EliminateBill, IEliminateBillService> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * 功能:审核
	 * @author:wxb
	 * @data:2017-8-5下午09:28:24
	 * @file:EliminateBillAction.java
	 * @return
	 * @throws Exception
	 */
	public String check()throws Exception{
		String[] idArr=ids.split(",");
		service.check(idArr);
		message=idArr.length+"";
		text(message);
		return NONE;
	}
	
	/**
	 * 
	 * 功能:撤销
	 * @author:wxb
	 * @data:2017-8-5下午09:28:35
	 * @file:EliminateBillAction.java
	 * @return
	 * @throws Exception
	 */
	public String cancelCheck()throws Exception{
		service.cancelCheck(id);
		message="1";
		text(message);
		return NONE;
	}

}
