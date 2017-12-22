/**
 * 功能:
 * @author:wxb
 * @data:2017-8-1下午03:32:21
 * @file:IMaterialBillService.java
 */
package com.zd.foster.breed.services;

import com.zd.epa.base.IBaseService;
import com.zd.foster.breed.entity.MaterialBill;

/**
 * 类名：  IMaterialBillService
 * 功能：
 * @author wxb
 * @date 2017-8-1下午03:32:21
 * @version 1.0
 * 
 */
public interface IMaterialBillService extends IBaseService<MaterialBill> {
	
	public void check(String[] idArr)throws Exception;
	
	public void cancelCheck(String id) throws Exception;

}
