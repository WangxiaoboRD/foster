package com.zd.foster.breed.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.zd.epa.base.IBaseService;
import com.zd.epa.utils.Pager;
import com.zd.foster.base.entity.Company;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.breed.entity.FeedBill;
import com.zd.foster.material.entity.FeedType;

/**
 * 类名：  IFeedBillService
 * 功能：
 * @author wxb
 * @date 2017-7-27上午08:57:12
 * @version 1.0
 * 
 */
public interface IFeedBillService extends IBaseService<FeedBill> {
	
	
	
	/**
	 * 
	 * 功能:保存表头信息
	 * @author:DZL
	 * @data:2017-7-31下午04:49:24
	 * @file:IFeedBillService.java
	 * @param id
	 * @throws Exception
	 */
	public Object saveHead(FeedBill f)throws Exception;
	/**
	 * 
	 * 功能:审核
	 * @author:wxb
	 * @data:2017-7-31下午04:49:20
	 * @file:IFeedBillService.java
	 * @param idArr
	 * @throws Exception
	 */
	public void check(String[] idArr)throws Exception;
	/**
	 * 
	 * 功能:撤销
	 * @author:wxb
	 * @data:2017-7-31下午04:49:24
	 * @file:IFeedBillService.java
	 * @param id
	 * @throws Exception
	 */
	public void cancelCheck(String id)throws Exception;
	
	/**
	 * 
	 * 功能:喂料分析
	 * @author:wxb
	 * @data:2017-9-1下午02:23:14
	 * @file:IFeedBillService.java
	 * @param entity
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectFeedAnalysisByPage(FeedBill entity,Pager<FeedBill> page)throws Exception;

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
	 * 导入耗料单
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-5 下午03:26:07
	 */
	List<FeedBill> operateFile(File doc, Company company, Object... objects)throws Exception;
	/**
	 * 查找日报饲料耗用
	 * @Description:TODO
	 * @param bat
	 * @param date
	 * @param feedTypes
	 * @param map
	 * void
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-22 上午09:10:19
	 */
	public void selectFeedDay(Batch bat, String date,Map<String, Object> map)throws Exception;
	/**
	 * 日报饲料总耗用
	 * @Description:TODO
	 * @param entity
	 * @param date
	 * @param totalMap
	 * @throws Exception
	 * void
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-22 下午02:53:04
	 */
	public void selectAllFeedDay(Batch entity, String date,Map<String, Object> totalMap)throws Exception;
	
	
	
}
