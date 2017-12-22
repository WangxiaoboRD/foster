
package com.zd.foster.warehouse.actions;

import org.apache.struts2.ServletActionContext;

import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.ExcelUtil;
import com.zd.foster.warehouse.entity.DrugAllocation;
import com.zd.foster.warehouse.services.IDrugAllocationService;

/**
 * 药品调拨单action
 * @Description:TODO
 * @author:小丁
 * @time:2017-9-14 下午05:23:59
 */
public class DrugAllocationAction extends BaseAction<DrugAllocation, IDrugAllocationService> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * 功能:审核
	 * @author:wxb
	 * @data:2017-7-28下午08:02:58
	 * @file:DrugInWareAction.java
	 * @return
	 * @throws Exception
	 */
	public String check()throws Exception{	
		String[] idArr = ids.split(",");
		service.check(idArr);
		message = idArr.length + "";		
		text(message);
		return NONE;
	}
	
	/**
	 * 
	 * 功能:撤销
	 * @author:wxb
	 * @data:2017-7-28下午08:03:08
	 * @file:DrugInWareAction.java
	 * @return
	 * @throws Exception
	 */
	public String cancelCheck()throws Exception{	
		
		service.cancelCheck(id);
		message = "1";		
		text(message);
		return NONE;
	}

	/**
	 * 导入模板下载
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-5 下午03:24:15
	 */
	public String downloadTemplate()throws Exception{
		stream = service.downloadTemplate(ServletActionContext.getServletContext().getRealPath("/"));
		docFileName= ExcelUtil.getFileName("药品调拨单导入模板.xlsx");
		return DOWN;
	}
	
	/**
	 * 导入药品调拨单
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-5 下午03:26:07
	 */
	public String importFile()throws Exception{
		elist = service.operateFile(doc,e.getCompany(),docFileName);
		result.put("Rows", elist);
		result.put("Total", elist.size());
		return JSON;
	}
	
	
	
}
