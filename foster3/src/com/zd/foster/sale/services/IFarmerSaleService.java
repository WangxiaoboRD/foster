package com.zd.foster.sale.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.foster.base.entity.Company;
import com.zd.foster.breed.entity.FeedBill;
import com.zd.foster.sale.entity.FarmerSale;

/**
 * 类名：  IFarmerSaleService
 * 功能：
 * @author DZL
 * @date 2017-7-19下午02:13:02
 * @version 1.0
 * 
 */
public interface IFarmerSaleService extends IBaseService<FarmerSale> {

	/**
	 * 功能：审核<br/>
	 *
	 * @author dzl
	 * @version 2015-3-9 下午05:28:16 <br/>
	 */
	void check(String[] idArr)throws Exception;
	/**
	 * 功能：撤销<br/>
	 *
	 * @author dzl
	 * @version 2015-3-9 下午05:28:16 <br/>
	 */
	void cancelCheck(String id)throws Exception;
	/**
	 * 通过ids查找对象
	 * @Description:TODO
	 * @param id
	 * @return
	 * @throws Exception
	 * FarmerSale
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-30 上午09:51:16
	 */
	FarmerSale selectByIds(String id)throws Exception;
	
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
	 * 导入销售单
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-5 下午03:26:07
	 */
	List<FarmerSale> operateFile(File doc, Company company, Object... objects)throws Exception;
	
	
}
