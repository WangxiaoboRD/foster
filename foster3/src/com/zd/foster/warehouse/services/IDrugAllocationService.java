
package com.zd.foster.warehouse.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.foster.base.entity.Company;
import com.zd.foster.warehouse.entity.DrugAllocation;

/**
 * 药品调拨单业务层
 * @Description:TODO
 * @author:小丁
 * @time:2017-9-14 下午05:14:51
 */
public interface IDrugAllocationService extends IBaseService<DrugAllocation> {
	
	/**
	 * 
	 * 功能:
	 * @author:wxb
	 * @data:2017-9-8下午04:37:53
	 * @file:IDrugBillService.java
	 * @param idArr
	 * @throws Exception
	 */
	void check(String[] idArr)throws Exception;
	/**
	 * 
	 * 功能:
	 * @author:wxb
	 * @data:2017-9-8下午04:37:57
	 * @file:IDrugBillService.java
	 * @param id
	 * @throws Exception
	 */
	void cancelCheck(String id)throws Exception;
	
	/**
	 * 导入模板下载
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-5 下午03:24:15
	 */
	InputStream downloadTemplate(String realPath)throws Exception;
	/**
	 * 导入药品调拨单
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-5 下午03:26:07
	 */
	List<DrugAllocation> operateFile(File doc, Company company, Object... objects)throws Exception;
	
	
	
	
}
