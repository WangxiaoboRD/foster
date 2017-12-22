/**
 * 功能:
 * @author:wxb
 * @data:2017-7-19下午03:09:10
 * @file:IFeedFacService.java
 */
package com.zd.foster.base.services;

import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.foster.base.entity.FeedFac;

/**
 * 类名：  IFeedFacService
 * 功能：
 * @author wxb
 * @date 2017-7-19下午03:09:10
 * @version 1.0
 * 
 */
public interface IFeedFacService extends IBaseService<FeedFac> {

	/**
	 *查询 按照代养编码
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 5:57:37 PM <br/>
	 */
	List<FeedFac> selectAllByFarmerId(FeedFac entity)throws Exception;
}
