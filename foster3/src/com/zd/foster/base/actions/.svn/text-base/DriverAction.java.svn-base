
package com.zd.foster.base.actions;

import com.zd.epa.base.BaseAction;
import com.zd.foster.base.entity.Driver;
import com.zd.foster.base.services.IDriverService;

/**
 * 司机Action层
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-20 下午03:50:37
 */
public class DriverAction extends BaseAction<Driver, IDriverService> {
	public String loadByFarmer()throws Exception{
		
		elist = service.selectAllByFarmer(e);
		result.put("Rows", elist);
		//result.put("Total", elist.size());
		return JSON;
	}

	

}
