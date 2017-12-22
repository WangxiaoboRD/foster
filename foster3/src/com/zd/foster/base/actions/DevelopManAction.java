
package com.zd.foster.base.actions;

import com.zd.epa.base.BaseAction;
import com.zd.foster.base.entity.DevelopMan;
import com.zd.foster.base.services.IDevelopManService;

/**
 * 类名：  DevelopManAction
 * 功能：
 * @author DZL
 * @date 2017-7-19下午02:18:34
 * @version 1.0
 * 
 */
public class DevelopManAction extends BaseAction<DevelopMan, IDevelopManService> {
	
	/**
	 * 获取开发人员
	 * @return
	 * @throws Exception
	 */
	public String loadByDevelopMan()throws Exception{
		elist = service.selectAllByFarmerId(e);
		result.put("Rows", elist);
		//result.put("Total", elist.size());
		return JSON;
	}
}
