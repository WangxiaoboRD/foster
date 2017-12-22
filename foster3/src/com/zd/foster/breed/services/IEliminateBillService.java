/**
 * 功能:
 * @author:wxb
 * @data:2017-8-1下午07:54:52
 * @file:IEliminateBillService.java
 */
package com.zd.foster.breed.services;

import com.zd.epa.base.IBaseService;
import com.zd.foster.breed.entity.EliminateBill;

/**
 * 类名：  IEliminateBillService
 * 功能：
 * @author wxb
 * @date 2017-8-1下午07:54:52
 * @version 1.0
 * 
 */
public interface IEliminateBillService extends IBaseService<EliminateBill> {
	
	public void check(String[] idArr)throws Exception;
	public void cancelCheck(String id)throws Exception;

}
