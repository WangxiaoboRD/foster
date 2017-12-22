package com.zd.foster.breed.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.zd.epa.base.IBaseService;
import com.zd.foster.base.entity.Company;
import com.zd.foster.breed.entity.DeathBill;

/**
 * 类名：  IDeathBillService
 * 功能：
 * @author wxb
 * @date 2017-8-1下午08:27:58
 * @version 1.0
 * 
 */
public interface IDeathBillService extends IBaseService<DeathBill> {
	/**
	 * 功能:审核
	 * @author:wxb
	 * @data:2017-8-7下午05:44:04
	 * @file:IDeathBillService.java
	 * @param idArr
	 * @throws Exception
	 */
	public void check(String[] idArr)throws Exception;
	/**
	 * 
	 * 功能:撤销
	 * @author:wxb
	 * @data:2017-8-7下午05:44:09
	 * @file:IDeathBillService.java
	 * @param id
	 * @throws Exception
	 */
	public void cancelCheck(String id)throws Exception;
	/**
	 * 
	 * 功能:上传
	 * @author:wxb
	 * @data:2017-8-7下午05:44:36
	 * @file:IDeathBillService.java
	 * @param doc
	 * @param path
	 * @param fileName
	 * @param id
	 * @return
	 * @throws Exception
	 */
	boolean upload(File[] files,String path,String[] fileNames,String id)throws Exception;
	
	/**
	 * 
	 * 功能:下载
	 * @author:wxb
	 * @data:2017-8-8上午11:41:10
	 * @file:IDeathBillService.java
	 * @param path
	 * @param id
	 * @param fname
	 * @return
	 * @throws Exception
	 */
	InputStream down(String path,String id,String[] fname)throws Exception;
	
	/**
	 * 
	 * 功能:确认回执
	 * @author:wxb
	 * @data:2017-9-9下午05:50:23
	 * @file:IDeathBillService.java
	 * @param id
	 * @throws Exception
	 */
	public void receipt(String[] idArr)throws Exception;
	
	/**
	 * 
	 * 功能:撤销回执
	 * @author:wxb
	 * @data:2017-9-9下午05:54:37
	 * @file:IDeathBillService.java
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
	 * 导入死亡单
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-5 下午03:26:07
	 */
	List<DeathBill> operateFile(File doc, Company company, Object... objects)throws Exception;
	
	/**
	 * 查询图片
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: dzl
	 * @time:2017-9-5 下午03:26:07
	 */
	Map<String,String> selectByImg(DeathBill deathBill,String path)throws Exception;
}
