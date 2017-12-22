/**
 * 功能:
 * @author:wxb
 * @data:2017-9-8下午01:35:28
 * @file:DrugTransferDtlServiceImpl.java
 */
package com.zd.foster.warehouse.services.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.foster.warehouse.dao.IDrugTransferDtlDao;
import com.zd.foster.warehouse.entity.DrugTransferDtl;
import com.zd.foster.warehouse.entity.DrugWarehouseFar;
import com.zd.foster.warehouse.services.IDrugTransferDtlService;
import com.zd.foster.warehouse.services.IDrugWarehouseFarService;

/**
 * 类名：  DrugTransferDtlServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-9-8下午01:35:28
 * @version 1.0
 * 
 */
public class DrugTransferDtlServiceImpl extends BaseServiceImpl<DrugTransferDtl, IDrugTransferDtlDao>
		implements IDrugTransferDtlService {
	@Resource
	private IDrugWarehouseFarService drugWarehouseFarService;

	/**
	 * 
	 * 功能:修改加载明细时，添加库存数量
	 * 重写:wxb
	 * 2017-7-31
	 * @see com.zd.epa.base.BaseServiceImpl#selectAll(com.zd.epa.base.BaseEntity)
	 */
	@Override
	public List<DrugTransferDtl> selectAll(DrugTransferDtl entity) throws Exception {
		List<DrugTransferDtl> ftdList=super.selectAll(entity);
		if(ftdList!=null && ftdList.size()>0){
			Iterator<DrugTransferDtl> it= ftdList.iterator();
			while(it.hasNext()){
				DrugTransferDtl ftd=it.next();
				//直接hql查询????-------------
				String hql="from com.zd.foster.warehouse.entity.DrugWarehouseFar e where e.drug.id='"+
					ftd.getDrug().getId()+
					"'and e.farmer.id='"+
					ftd.getDrugTransfer().getOutFarmer().getId()+
					"'and e.batch.id='"+
					ftd.getDrugTransfer().getOutBatch().getId()+
					"'";
				List<DrugWarehouseFar> fwList=drugWarehouseFarService.selectByHQL(hql);
				//-----------------------
				if(fwList!=null && fwList.size()>0){
					DrugWarehouseFar fw=fwList.get(0);
					if(fw.getQuantity()!=null)
						ftd.setStockQuantity(fw.getQuantity());
					if(fw.getSubQuantity()!=null)
						ftd.setStockSubQuantity(fw.getSubQuantity());
				}
			}
		}
		return ftdList;
	}


}
