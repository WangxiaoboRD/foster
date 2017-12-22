/**
 * 功能:
 * @author:wxb
 * @data:2017-9-5下午04:45:52
 * @file:FeedTransferDtlServiceImpl.java
 */
package com.zd.foster.warehouse.services.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.foster.warehouse.dao.IFeedTransferDtlDao;
import com.zd.foster.warehouse.entity.FeedTransferDtl;
import com.zd.foster.warehouse.entity.FeedWarehouse;
import com.zd.foster.warehouse.services.IFeedTransferDtlService;
import com.zd.foster.warehouse.services.IFeedWarehouseService;

/**
 * 类名：  FeedTransferDtlServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-9-5下午04:45:52
 * @version 1.0
 * 
 */
public class FeedTransferDtlServiceImpl extends BaseServiceImpl<FeedTransferDtl, IFeedTransferDtlDao>
		implements IFeedTransferDtlService {
	@Resource
	private IFeedWarehouseService feedWarehouseService;

	/**
	 * 
	 * 功能:修改加载明细时，添加库存数量
	 * 重写:wxb
	 * 2017-7-31
	 * @see com.zd.epa.base.BaseServiceImpl#selectAll(com.zd.epa.base.BaseEntity)
	 */
	@Override
	public List<FeedTransferDtl> selectAll(FeedTransferDtl entity) throws Exception {
		List<FeedTransferDtl> ftdList=super.selectAll(entity);
//		if(ftdList!=null && ftdList.size()>0){
//			Iterator<FeedTransferDtl> it= ftdList.iterator();
//			while(it.hasNext()){
//				FeedTransferDtl ftd=it.next();
//				//直接hql查询????-------------
//				String hql="from com.zd.foster.warehouse.entity.FeedWarehouse e where e.feed.id='"+
//					ftd.getFeed().getId()+
//					"'and e.farmer.id='"+
//					ftd.getFeedTransfer().getOutFarmer().getId()+
//					"'and e.batch.id='"+
//					ftd.getFeedTransfer().getOutBatch().getId()+
//					"'";
//				List<FeedWarehouse> fwList=feedWarehouseService.selectByHQL(hql);
//				//-----------------------
//				if(fwList!=null && fwList.size()>0){
//					FeedWarehouse fw=fwList.get(0);
//					if(fw.getQuantity()!=null)
//						ftd.setStockQuantity(fw.getQuantity());
//					if(fw.getSubQuantity()!=null)
//						ftd.setStockSubQuantity(fw.getSubQuantity());
//				}
//			}
//		}
		return ftdList;
	}

}
