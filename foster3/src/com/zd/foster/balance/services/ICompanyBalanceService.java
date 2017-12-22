package com.zd.foster.balance.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.foster.balance.entity.CompanyBalance;
import com.zd.foster.balance.entity.FarmerBalance;
import com.zd.foster.base.entity.Company;

/**
 * 养殖公司结算单业务层接口
 * @Description:TODO
 * @author:小丁
 * @time:2017-8-7 上午10:04:04
 */
public interface ICompanyBalanceService extends IBaseService<CompanyBalance> {
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

	List<CompanyBalance> operateFile(File doc, Company company,List<String> idList, Object... objects)throws Exception;
}
