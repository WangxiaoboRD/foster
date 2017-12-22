/**
 * 功能:
 * @author:wxb
 * @data:2017-8-1下午04:24:43
 * @file:IPigPurchaseService.java
 */
package com.zd.foster.breed.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.zd.epa.base.IBaseService;
import com.zd.epa.utils.Pager;
import com.zd.foster.base.entity.Company;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.breed.entity.PigPurchase;

/**
 * 类名：  IPigPurchaseService
 * 功能：
 * @author wxb
 * @date 2017-8-1下午04:24:43
 * @version 1.0
 * 
 */
public interface IPigPurchaseService extends IBaseService<PigPurchase> {
	/**
	 * 
	 * 功能:审核
	 * @author:wxb
	 * @data:2017-8-3上午09:08:06
	 * @file:IPigPurchaseService.java
	 * @param idArr
	 * @throws Exception
	 */
	public void check(String[] idArr)throws Exception;
	/**
	 * 
	 * 功能:撤销
	 * @author:wxb
	 * @data:2017-8-3上午09:08:18
	 * @file:IPigPurchaseService.java
	 * @param id
	 * @throws Exception
	 */
	public void cancelCheck(String id)throws Exception;
	
	/**
	 * 
	 * 功能:
	 * @author:wxb
	 * @data:2017-9-10下午08:06:00
	 * @file:IPigPurchaseService.java
	 * @param id
	 * @throws Exception
	 */
	public void receipt(String[] idArr)throws Exception;
	
	/**
	 * 
	 * 功能:
	 * @author:wxb
	 * @data:2017-9-10下午08:06:07
	 * @file:IPigPurchaseService.java
	 * @param id
	 * @throws Exception
	 */
	public void cancelReceipt(String id)throws Exception;

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
	 * 导入猪苗登记
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-5 下午03:26:07
	 */
	List<PigPurchase> operateFile(File doc, Company company, Object... objects)throws Exception;
	/**
	 * 查询猪苗登记报表
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-17 上午10:34:18
	 */
	public List<PigPurchase> selectPigPurchaseByPage(PigPurchase e,Pager<PigPurchase> pageBean)throws Exception;
	/**
	 * 导出猪苗登记报表
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-17 下午04:19:36
	 */
	public InputStream exportPigPurchase(PigPurchase e)throws Exception;
	/**
	 * 日报表查找固定字段
	 * @Description:TODO
	 * @param bat
	 * @param date
	 * @param map
	 * @throws Exception
	 * void
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-22 上午08:34:14
	 */
	public void selectPigDay(Batch bat, String date, Map<String, Object> map)throws Exception;
	
}
