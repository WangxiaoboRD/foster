/**
 * 功能:
 * @author:wxb
 * @data:2017-7-19下午03:35:08
 * @file:ICustVenderService.java
 */
package com.zd.foster.base.services;

import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.foster.base.entity.CustVender;
import com.zd.foster.base.entity.FeedFac;
import com.zd.foster.base.entity.TransportCo;

/**
 * 类名：  ICustVenderService
 * 功能：
 * @author wxb
 * @date 2017-7-19下午03:35:08
 * @version 1.0
 * 
 */
public interface ICustVenderService extends IBaseService<CustVender> {


	/**
	 *查询 按照代养编码
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 5:57:37 PM <br/>
	 */
	List<CustVender> selectAllByFarmerId(CustVender entity)throws Exception;
	
	List<CustVender> selectAllByFarmer(CustVender entity)throws Exception;
}
