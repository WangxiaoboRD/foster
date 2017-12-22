/**
 * 功能:
 * @author:wxb
 * @data:2017-8-1下午03:37:42
 * @file:MaterialBillDtlServiceImpl.java
 */
package com.zd.foster.breed.services.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.foster.breed.dao.IMaterialBillDtlDao;
import com.zd.foster.breed.entity.MaterialBillDtl;
import com.zd.foster.breed.services.IMaterialBillDtlService;
import com.zd.foster.warehouse.entity.MaterialWarehouse;
import com.zd.foster.warehouse.services.IMaterialWarehouseService;

/**
 * 类名：  MaterialBillDtlServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-8-1下午03:37:42
 * @version 1.0
 * 
 */
public class MaterialBillDtlServiceImpl extends BaseServiceImpl<MaterialBillDtl, IMaterialBillDtlDao>
		implements IMaterialBillDtlService {
	@Resource
	private IMaterialWarehouseService materialWarehouseService;
	/**
	 * 
	 * 功能:修改加载明细时，添加库存数量
	 * 重写:wxb
	 * 2017-7-31
	 * @see com.zd.epa.base.BaseServiceImpl#selectAll(com.zd.epa.base.BaseEntity)
	 */
	@Override
	public List<MaterialBillDtl> selectAll(MaterialBillDtl entity) throws Exception {
		List<MaterialBillDtl> mbdList=super.selectAll(entity);
		if(mbdList!=null && mbdList.size()>0){
			Iterator<MaterialBillDtl> it= mbdList.iterator();
			while(it.hasNext()){
				MaterialBillDtl mbd=it.next();
				String hql="from MaterialWarehouse e where e.material.id='"+
					mbd.getMaterial().getId()+
					"'and e.company.id='"+
					mbd.getMaterialBill().getCompany().getId()+
					"'";
				List<MaterialWarehouse> mwList=materialWarehouseService.selectByHQL(hql);
				if(mwList!=null && mwList.size()>0){
					MaterialWarehouse mw=mwList.get(0);
					if(mw.getQuantity()!=null)
						mbd.setStockQuantity(mw.getQuantity());
					if(mw.getSubQuantity()!=null)
						mbd.setStockSubQuantity(mw.getSubQuantity());
				}
			}
		}
		return mbdList;
	}

}
