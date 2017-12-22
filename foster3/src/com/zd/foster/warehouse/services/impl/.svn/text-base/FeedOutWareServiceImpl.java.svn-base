/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午02:18:51
 * @file:FeedOutWareServiceImpl.java
 */
package com.zd.foster.warehouse.services.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.warehouse.dao.IFeedOutWareDao;
import com.zd.foster.warehouse.entity.FeedOutWare;
import com.zd.foster.warehouse.services.IFeedOutWareDtlService;
import com.zd.foster.warehouse.services.IFeedOutWareService;

/**
 * 类名：  FeedOutWareServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-7-26下午02:18:51
 * @version 1.0
 * 
 */
public class FeedOutWareServiceImpl extends BaseServiceImpl<FeedOutWare, IFeedOutWareDao> implements
		IFeedOutWareService {
	@Resource
	private IFeedOutWareDtlService feedOutWareDtlService;
	/**
	 * 
	 * 功能:删除
	 * 重写:wxb
	 * 2017-7-28
	 * @see com.zd.epa.base.BaseServiceImpl#deleteByIds(ID[])
	 */
	public <ID extends Serializable> int deleteById(ID PK)throws Exception{
		if(PK==null )
			throw new SystemException("请选择删除对象");
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("feedOutWare.id", "=", PK);
		feedOutWareDtlService.delete(sqlMap);
		return dao.deleteById(PK);
	}

}
