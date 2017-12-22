package com.zd.foster.balance.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.foster.balance.entity.FarmerBalance;
import com.zd.foster.base.entity.Company;
import com.zd.foster.warehouse.entity.DrugBill;

/**
 * 类名：  IFarmerBalanceService
 * 功能：
 * @author DZL
 * @date 2017-7-19下午02:13:02
 * @version 1.0
 * 
 */
public interface IFarmerBalanceService extends IBaseService<FarmerBalance> {

	/**
	 * 功能：结算<br/>
	 *
	 * @author DZL 
	 * @version 2015-3-9 下午05:28:16 <br/>
	 */
	void balance(String id)throws Exception;
	
	/**
	 * 功能：撤销<br/>
	 *
	 * @author dzl
	 * @version 2015-3-9 下午05:28:16 <br/>
	 */
	void cancelCheck(String id)throws Exception;
	
	InputStream downloadTemplate(String realPath)throws Exception;

	List<FarmerBalance> operateFile(File doc, Company company,List<String> idList, Object... objects)throws Exception;
}
