/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午03:22:35
 * @file:MaterialWarehouseServiceImpl.java
 */
package com.zd.foster.warehouse.services.impl;

import java.util.Map;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.utils.Pager;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.warehouse.dao.IMaterialWarehouseDao;
import com.zd.foster.warehouse.entity.MaterialWarehouse;
import com.zd.foster.warehouse.services.IMaterialWarehouseService;

/**
 * 类名：  MaterialWarehouseServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-7-26下午03:22:35
 * @version 1.0
 * 
 */
public class MaterialWarehouseServiceImpl extends BaseServiceImpl<MaterialWarehouse, IMaterialWarehouseDao>
		implements IMaterialWarehouseService {
	@Resource
	private IFarmerService farmerService;

	/**
	 * 
	 * 功能:其他物料查询时，isDrug属性需为1
	 * 重写:wxb
	 * 2017-7-29
	 * @see com.zd.epa.base.BaseServiceImpl#selectAll(com.zd.epa.base.BaseEntity, com.zd.epa.utils.Pager)
	 */
	@Override
	public void selectAll(MaterialWarehouse entity,
			Pager<MaterialWarehouse> page) throws Exception {
		Map<String,Object> map=entity.getMap();
		map.put("e.isDrug", "1");
		//根据代养户查询对应公司的库存
		if(entity.getFarmer()!=null && !"".equals(entity.getFarmer())){
			Farmer f=farmerService.selectById(entity.getFarmer());
			map.put("e.company", f.getCompany().getId());
//			map.put("e.farmer", null);
		}
		super.selectAll(entity, page);
	}
	

}
