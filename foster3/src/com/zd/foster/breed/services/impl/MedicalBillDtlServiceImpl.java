/**
 * 功能:
 * @author:wxb
 * @data:2017-7-27上午09:41:04
 * @file:MedicalBillDtlServiceImpl.java
 */
package com.zd.foster.breed.services.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.foster.breed.dao.IMedicalBillDtlDao;
import com.zd.foster.breed.entity.MedicalBillDtl;
import com.zd.foster.breed.services.IMedicalBillDtlService;
import com.zd.foster.warehouse.entity.DrugWarehouseFar;
import com.zd.foster.warehouse.services.IDrugWarehouseFarService;

/**
 * 类名：  MedicalBillDtlServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-7-27上午09:41:04
 * @version 1.0
 * 
 */
public class MedicalBillDtlServiceImpl extends BaseServiceImpl<MedicalBillDtl, IMedicalBillDtlDao>
		implements IMedicalBillDtlService {
	@Resource
	private IDrugWarehouseFarService drugWarehouseFarService;
	/**
	 * 
	 * 功能:修改加载明细时，添加代养户库存数量
	 * 重写:wxb
	 * 2017-7-31
	 * @see com.zd.epa.base.BaseServiceImpl#selectAll(com.zd.epa.base.BaseEntity)
	 */
	@Override
	public List<MedicalBillDtl> selectAll(MedicalBillDtl entity) throws Exception {
		List<MedicalBillDtl> mbdList=super.selectAll(entity);
		if(mbdList!=null && mbdList.size()>0){
			Iterator<MedicalBillDtl> it= mbdList.iterator();
			while(it.hasNext()){
				MedicalBillDtl mbd=it.next();
				String hql="from com.zd.foster.warehouse.entity.DrugWarehouseFar e where e.drug.id='"+
					mbd.getDrug().getId()+
					"'and e.batch.id='"+
					mbd.getMedicalBill().getBatch().getId()+
					"'";
				List<DrugWarehouseFar> dwList=drugWarehouseFarService.selectByHQL(hql);
				if(dwList!=null && dwList.size()>0){
					DrugWarehouseFar dw=dwList.get(0);
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
