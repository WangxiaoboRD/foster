/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午03:33:01
 * @file:IDrugInWareService.java
 */
package com.zd.foster.warehouse.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.warehouse.entity.DrugInWare;

/**
 * 类名：  IDrugInWareService
 * 功能：
 * @author wxb
 * @date 2017-7-26下午03:33:01
 * @version 1.0
 * 
 */
public interface IDrugInWareService extends IBaseService<DrugInWare> {
	/**
	 * 
	 * 功能:
	 * @author:wxb
	 * @data:2017-7-28下午08:03:41
	 * @file:IDrugInWareService.java
	 * @param idArr
	 * @throws Exception
	 */
	void check(String[] idArr)throws Exception;
	
	/**
	 * 
	 * 功能:
	 * @author:wxb
	 * @data:2017-7-28下午08:03:47
	 * @file:IDrugInWareService.java
	 * @param id
	 * @throws Exception
	 */
	void cancelCheck(String id)throws Exception;
	
	InputStream downloadTemplate(String realPath)throws Exception;

	List<DrugInWare> operateFile(File doc, Company company, Object... objects)throws Exception;

}
