/**
 * 功能:
 * @author:wxb
 * @data:2017-9-8下午01:37:22
 * @file:DrugTransferAction.java
 */
package com.zd.foster.warehouse.actions;

import com.zd.epa.base.BaseAction;
import com.zd.foster.warehouse.entity.DrugTransfer;
import com.zd.foster.warehouse.services.IDrugTransferService;

/**
 * 类名：  DrugTransferAction
 * 功能：
 * @author wxb
 * @date 2017-9-8下午01:37:22
 * @version 1.0
 * 
 */
public class DrugTransferAction extends BaseAction<DrugTransfer, IDrugTransferService> {

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * 功能:审核
	 * @author:wxb
	 * @data:2017-9-9上午09:07:51
	 * @file:DrugTransferAction.java
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
	 * @data:2017-9-9上午09:08:05
	 * @file:DrugTransferAction.java
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
