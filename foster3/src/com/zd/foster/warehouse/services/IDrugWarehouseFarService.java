/**
 * 功能:
 * @author:wxb
 * @data:2017-9-8下午03:17:05
 * @file:IDrugWarehouseFarService.java
 */
package com.zd.foster.warehouse.services;

import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.foster.warehouse.entity.DrugWarehouseFar;

/**
 * 类名：  IDrugWarehouseFarService
 * 功能：
 * @author wxb
 * @date 2017-9-8下午03:17:05
 * @version 1.0
 * 
 */
public interface IDrugWarehouseFarService extends IBaseService<DrugWarehouseFar> {

	List<DrugWarehouseFar> selectByCodes(DrugWarehouseFar e) throws Exception;

}
