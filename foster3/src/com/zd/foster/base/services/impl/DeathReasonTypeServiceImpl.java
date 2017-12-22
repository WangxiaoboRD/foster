package com.zd.foster.base.services.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.foster.base.dao.IDeathReasonTypeDao;
import com.zd.foster.base.entity.DeathReasonType;
import com.zd.foster.base.services.IDeathReasonService;
import com.zd.foster.base.services.IDeathReasonTypeService;

/**
 * 类名：  DeathReasonTypeServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-7-19下午02:14:10
 * @version 1.0
 * 
 */
public class DeathReasonTypeServiceImpl extends BaseServiceImpl<DeathReasonType, IDeathReasonTypeDao> implements IDeathReasonTypeService {
	
	@Resource
	private IDeathReasonService deathReasonService;
	/**
	 * 单个对象批量删除
	 * 功能：<br/>
	 * @author dzl
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		for(ID id : PK){
			//删除司机：验证单据上的司机是否被猪苗登记单、饲料入库单引用
			int puNum = deathReasonService.selectTotalRows("deathReasonType.id", id);
			if(puNum>0)
				throw new SystemException("此类型正在被使用，不允许删除");
		}
		
		return dao.deleteByIds(PK);
	}

}
