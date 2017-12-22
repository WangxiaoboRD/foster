/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26上午10:49:33
 * @file:FeedWarehouseServiceImpl.java
 */
package com.zd.foster.warehouse.services.impl;

import java.util.Collections;
import java.util.List;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.utils.Pager;
import com.zd.foster.warehouse.dao.IFeedWarehouseDao;
import com.zd.foster.warehouse.entity.FeedWarehouse;
import com.zd.foster.warehouse.services.IFeedWarehouseService;

/**
 * 类名：  FeedWarehouseServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-7-26上午10:49:33
 * @version 1.0
 * 
 */
public class FeedWarehouseServiceImpl extends BaseServiceImpl<FeedWarehouse, IFeedWarehouseDao> implements
		IFeedWarehouseService {

	@Override
	public void selectAll(FeedWarehouse entity, Pager<FeedWarehouse> page)
			throws Exception {
		super.selectAll(entity, page);
		List<FeedWarehouse> flist=page.getResult();
		if(flist.size()>0)
		//按公司，代养户，批次，饲料类别排序
			Collections.sort(flist);
	}
	

}
