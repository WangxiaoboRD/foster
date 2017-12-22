package com.zd.foster.breed.services;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.zd.epa.base.IBaseService;
import com.zd.epa.permission.entity.Users;
import com.zd.epa.utils.Pager;
import com.zd.foster.breed.entity.Batch;

/**
 * 类名：  IBatchService
 * 功能：
 * @author DZL
 * @date 2017-7-19下午02:13:02
 * @version 1.0
 * 
 */
public interface IBatchService extends IBaseService<Batch> {

	/**
	 * 代养跟进报表
	 * @return
	 * @throws Exception
	 */
	void selectCurrentBatchByPage(Batch batch,Pager<Batch> page)throws Exception;
	
	/**
	 * 代养跟进报表合计
	 * @return
	 * @throws Exception
	 */
	List<Batch> selectAllByFarmer(Batch batch)throws Exception;
	
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
	InputStream exportCurrentBatch(Batch e)throws Exception;
	/**
	 * 查找日报表
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-21 下午02:19:03
	 */
	Map<String, Object> selectDayAnalysisByPage(Batch e, Pager<Batch> pageBean)throws Exception;
	
	/**
	 * 根据用户查询批次
	 * @return
	 * @throws Exception
	 */
	List<Batch> selectAllByUser(Users u,Batch b)throws Exception;
	
	/**
	 * 导出日报表
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-17 下午04:19:36
	 */
	public InputStream exportBook(Batch e)throws Exception;
	
	/**
	 * 获取工厂
	 */
	public String selectByFeedFac(String id)throws Exception;
}
