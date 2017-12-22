/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午04:12:45
 * @file:IDrugOutWareService.java
 */
package com.zd.foster.warehouse.services;

import java.io.InputStream;
import java.util.Map;

import com.zd.epa.base.IBaseService;
import com.zd.epa.utils.Pager;
import com.zd.foster.warehouse.entity.DrugOutWare;

/**
 * 类名：  IDrugOutWareService
 * 功能：
 * @author wxb
 * @date 2017-7-26下午04:12:45
 * @version 1.0
 * 
 */
public interface IDrugOutWareService extends IBaseService<DrugOutWare> {
	/**
	 * 查询公司药品出入库报表
	 * @Description:TODO
	 * @param e
	 * @param pageBean
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-18 上午10:05:30
	 */
	Map<String, Object> selectDrugInOutByPage(DrugOutWare e,Pager<DrugOutWare> pageBean)throws Exception;
	/**
	 * 导出药品出入库报表
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-19 上午08:50:10
	 */
	InputStream exportDrugInOut(DrugOutWare e)throws Exception;

}
