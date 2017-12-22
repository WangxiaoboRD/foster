/**
 * 功能:
 * @author:wxb
 * @data:2017-9-7下午01:35:38
 * @file:IDeathReasonService.java
 */
package com.zd.foster.base.services;

import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.foster.base.entity.DeathReason;
import com.zd.foster.base.entity.Farmer;

/**
 * 类名：  IDeathReasonService
 * 功能：
 * @author wxb
 * @date 2017-9-7下午01:35:38
 * @version 1.0
 * 
 */
public interface IDeathReasonService extends IBaseService<DeathReason> {
	
	List<DeathReason> selectByPinyin(DeathReason e, String key) throws Exception;

}
