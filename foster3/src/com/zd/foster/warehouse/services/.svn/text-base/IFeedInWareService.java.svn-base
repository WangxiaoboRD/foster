/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26上午11:27:30
 * @file:IFeedInWareService.java
 */
package com.zd.foster.warehouse.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.zd.epa.base.IBaseService;
import com.zd.epa.utils.ExcelUtil;
import com.zd.epa.utils.Pager;
import com.zd.epa.utils.TypeUtil;
import com.zd.foster.base.entity.Company;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.dto.FeedAnalysis;
import com.zd.foster.warehouse.entity.FeedInWare;

/**
 * 类名：  IFeedInWareService
 * 功能：
 * @author wxb
 * @date 2017-7-26上午11:27:30
 * @version 1.0
 * 
 */
public interface IFeedInWareService extends IBaseService<FeedInWare> {
	
	/**
	 * 
	 * 功能:
	 * @author:wxb
	 * @data:2017-7-28下午02:03:24
	 * @file:IFeedInWareService.java
	 * @param idArr
	 * @throws Exception
	 */
	void check(String[] idArr)throws Exception;
	
	/**
	 * 
	 * 功能:
	 * @author:wxb
	 * @data:2017-7-28下午02:03:29
	 * @file:IFeedInWareService.java
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
	 * 导入领料单
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-5 下午03:26:07
	 */
	List<FeedInWare> operateFile(File doc, Company company, Object... objects)throws Exception;
	/**
	 * 日报饲料入库
	 * @Description:TODO
	 * @param bat
	 * @param date
	 * @param map
	 * @throws Exception
	 * void
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-22 上午10:04:57
	 */
	void selectFeedInDay(Batch bat, String date, Map<String, Object> map)throws Exception;
	/**
	 * 日报饲料入库总计
	 * @Description:TODO
	 * @param entity
	 * @param date
	 * @param totalMap
	 * @throws Exception
	 * void
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-22 下午03:07:30
	 */
	void selectAllFeedInDay(Batch entity, String date,Map<String, Object> totalMap)throws Exception;
	
	/**
	 * 饲料耗用报表
	 * @param feedInWare
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<FeedAnalysis> selectFeedInWareByPage(FeedInWare feedInWare,Pager<FeedInWare> page)throws Exception;
	
	/**
	 * 
	 * 功能： 导出进度追踪表<br/>
	 * @author:DZL<br/>
	 * @version:2016-12-2上午09:55:02
	 * @param @param e
	 * @param @return
	 * @param @throws Exception  
	 * @throws
	 * @return InputStream
	 * @see com.zd.pb.breed.services
	 */
	InputStream exportFeedInWareDetail(FeedInWare e)throws Exception;
}
