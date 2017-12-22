/**
 * 功能:
 * @author:wxb
 * @data:2017-7-19下午03:48:55
 * @file:ITransportCoService.java
 */
package com.zd.foster.base.services;

import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.foster.base.entity.TransportCo;

/**
 * 类名：  ITransportCoService
 * 功能：
 * @author wxb
 * @date 2017-7-19下午03:48:55
 * @version 1.0
 * 
 */
public interface ITransportCoService extends IBaseService<TransportCo> {
	
	List<TransportCo> selectAllByFarmer(TransportCo entity)throws Exception;

}
