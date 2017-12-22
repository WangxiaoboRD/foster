/**
 * 功能:
 * @author:wxb
 * @data:2017-9-8下午01:30:15
 * @file:IDrugTransferService.java
 */
package com.zd.foster.warehouse.services;

import com.zd.epa.base.IBaseService;
import com.zd.foster.warehouse.entity.DrugTransfer;

/**
 * 类名：  IDrugTransferService
 * 功能：
 * @author wxb
 * @date 2017-9-8下午01:30:15
 * @version 1.0
 * 
 */
public interface IDrugTransferService extends IBaseService<DrugTransfer> {
	
	public void check(String[] idArr)throws Exception;
	
	public void cancelCheck(String id)throws Exception;

}
