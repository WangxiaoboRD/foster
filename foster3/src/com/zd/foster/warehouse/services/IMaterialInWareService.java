/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午04:43:46
 * @file:IMaterialInWareService.java
 */
package com.zd.foster.warehouse.services;

import com.zd.epa.base.IBaseService;
import com.zd.foster.warehouse.entity.MaterialInWare;

/**
 * 类名：  IMaterialInWareService
 * 功能：
 * @author wxb
 * @date 2017-7-26下午04:43:46
 * @version 1.0
 * 
 */
public interface IMaterialInWareService extends IBaseService<MaterialInWare> {
	
	/**
	 * 
	 * 功能:
	 * @author:wxb
	 * @data:2017-7-29下午03:43:17
	 * @file:IMaterialInWareService.java
	 * @param idArr
	 * @throws Exception
	 */
	void check(String[] idArr)throws Exception;
	
	/**
	 * 
	 * 功能:
	 * @author:wxb
	 * @data:2017-7-29下午03:43:23
	 * @file:IMaterialInWareService.java
	 * @param id
	 * @throws Exception
	 */
	void cancelCheck(String id)throws Exception;

}
