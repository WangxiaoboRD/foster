/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午03:35:06
 * @file:DrugInWareAction.java
 */
package com.zd.foster.warehouse.actions;

import org.apache.struts2.ServletActionContext;

import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.ExcelUtil;
import com.zd.foster.warehouse.entity.DrugInWare;
import com.zd.foster.warehouse.services.IDrugInWareService;

/**
 * 类名：  DrugInWareAction
 * 功能：
 * @author wxb
 * @date 2017-7-26下午03:35:06
 * @version 1.0
 * 
 */
public class DrugInWareAction extends BaseAction<DrugInWare, IDrugInWareService> {
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
	 * 
	 * 功能:模板下载
	 * @author:wxb
	 * @data:2017-9-11下午03:25:30
	 * @file:DrugInWareAction.java
	 * @return
	 * @throws Exception
	 */
	public String downloadTemplate()throws Exception{
		stream = service.downloadTemplate(ServletActionContext.getServletContext().getRealPath("/"));
		docFileName= ExcelUtil.getFileName("代养户导入模板.xlsx");
		return DOWN;
	}
	
	/**
	 * 
	 * 功能:导入药品入库单
	 * 重写:wxb
	 * 2017-9-11
	 * @see com.zd.epa.base.BaseAction#importFile()
	 */
	public String importFile()throws Exception{
		elist = service.operateFile(doc,e.getCompany(),docFileName);
		result.put("Rows", elist);
		result.put("Total", elist.size());
		return JSON;
	}

}
