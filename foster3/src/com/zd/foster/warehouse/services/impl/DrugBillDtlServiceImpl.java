/**
 * 功能:
 * @author:wxb
 * @data:2017-9-8上午10:49:08
 * @file:DrugBillDtlServiceImpl.java
 */
package com.zd.foster.warehouse.services.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.foster.warehouse.dao.IDrugBillDtlDao;
import com.zd.foster.warehouse.entity.DrugBillDtl;
import com.zd.foster.warehouse.entity.DrugWarehouse;
import com.zd.foster.warehouse.services.IDrugBillDtlService;
import com.zd.foster.warehouse.services.IDrugWarehouseService;

/**
 * 类名：  DrugBillDtlServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-9-8上午10:49:08
 * @version 1.0
 * 
 */
public class DrugBillDtlServiceImpl extends BaseServiceImpl<DrugBillDtl, IDrugBillDtlDao> implements
		IDrugBillDtlService {
	@Resource
	private IDrugWarehouseService drugWarehouseService;
	/**
	 * 
	 * 功能:修改加载明细时，添加库存数量
	 * 重写:wxb
	 * 2017-7-31
	 * @see com.zd.epa.base.BaseServiceImpl#selectAll(com.zd.epa.base.BaseEntity)
	 */
	@Override
	public List<DrugBillDtl> selectAll(DrugBillDtl entity) throws Exception {
		List<DrugBillDtl> mbdList=super.selectAll(entity);
		if(mbdList!=null && mbdList.size()>0){
			Iterator<DrugBillDtl> it= mbdList.iterator();
			while(it.hasNext()){
				DrugBillDtl mbd=it.next();
				String hql="from com.zd.foster.warehouse.entity.DrugWarehouse e where e.drug.id='"+
					mbd.getDrug().getId()+
					"'and e.company.id='"+
					mbd.getDrugBill().getCompany().getId()+
					"'";
				List<DrugWarehouse> dwList=drugWarehouseService.selectByHQL(hql);
				if(dwList!=null && dwList.size()>0){
					DrugWarehouse dw=dwList.get(0);
					if(dw.getQuantity()!=null)
						mbd.setStockQuantity(dw.getQuantity());
					if(dw.getSubQuantity()!=null)
						mbd.setStockSubQuantity(dw.getSubQuantity());
				}
			}
		}
		return mbdList;
	}

}
