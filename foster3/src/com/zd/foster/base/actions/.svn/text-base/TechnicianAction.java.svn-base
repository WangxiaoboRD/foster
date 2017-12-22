
package com.zd.foster.base.actions;

import com.zd.epa.base.BaseAction;
import com.zd.foster.base.entity.Technician;
import com.zd.foster.base.services.ITechnicianService;

/**
 * 类名：  TechnicianAction
 * 功能：
 * @author 小丁
 * @date 2017-7-19下午02:18:34
 * @version 1.0
 * 
 */
public class TechnicianAction extends BaseAction<Technician, ITechnicianService> {
	
	/**
	 * 获取技术员
	 * @return
	 * @throws Exception
	 */
	public String loadByTech()throws Exception{
		elist = service.selectAllByFarmerId(e);
		result.put("Rows", elist);
		//result.put("Total", elist.size());
		return JSON;
	}
	
	/**
	 * 获取技术员通过batch
	 * @return
	 * @throws Exception
	 */
	public String loadByBatch()throws Exception{
		elist = service.selectAllByBatchId(e);
		result.put("Rows", elist);
		//result.put("Total", elist.size());
		return JSON;
	}
	
	private String key; 
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * 检索技术员
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-10-12 下午01:27:49
	 */
	public String loadByName()throws Exception{
		elist = service.selectByName(e,key);
		result.put("Rows", elist);
		return JSON;
	}
}
