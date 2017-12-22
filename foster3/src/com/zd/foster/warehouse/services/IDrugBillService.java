/**
 * 功能:
 * @author:wxb
 * @data:2017-9-8上午10:36:58
 * @file:IDrugBillService.java
 */
package com.zd.foster.warehouse.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.zd.epa.base.IBaseService;
import com.zd.epa.utils.Pager;
import com.zd.foster.base.entity.Company;
import com.zd.foster.dto.MedicalAnalysis;
import com.zd.foster.warehouse.entity.DrugBill;

/**
 * 类名：  IDrugBillService
 * 功能：
 * @author wxb
 * @date 2017-9-8上午10:36:58
 * @version 1.0
 * 
 */
public interface IDrugBillService extends IBaseService<DrugBill> {
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
	 * 
	 * 功能:
	 * @author:wxb
	 * @data:2017-9-9下午09:09:29
	 * @file:IDrugBillService.java
	 * @param id
	 * @throws Exception
	 */
	public void receipt(String[] idArr)throws Exception;
	/**
	 * 
	 * 功能:
	 * @author:wxb
	 * @data:2017-9-9下午09:09:33
	 * @file:IDrugBillService.java
	 * @param id
	 * @throws Exception
	 */
	public void cancelReceipt(String id)throws Exception;
	
	InputStream downloadTemplate(String realPath)throws Exception;

	List<DrugBill> operateFile(File doc, Company company, Object... objects)throws Exception;
	
	Map<String, Object> printDrugBill(String id)throws Exception;

	/**
	 * 药品耗用报表
	 * @param feedInWare
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<MedicalAnalysis> selectMedicalByPage(DrugBill drugBill,Pager<DrugBill> page)throws Exception;
	
	/**
	 * 
	 * 功能： 导出药品耗用表<br/>
	 * @author:DZL<br/>
	 * @version:2016-12-2上午09:55:02
	 * @param @param e
	 * @param @return
	 * @param @throws Exception  
	 * @throws
	 * @return InputStream
	 * @see com.zd.pb.breed.services
	 */
	InputStream exportMedical(DrugBill e)throws Exception;

}
