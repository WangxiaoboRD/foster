/**
 * 功能:
 * @author:wxb
 * @data:2017-7-27上午09:32:35
 * @file:MedicalBillAction.java
 */
package com.zd.foster.breed.actions;

import org.apache.struts2.ServletActionContext;

import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.ExcelUtil;
import com.zd.foster.breed.entity.MedicalBill;
import com.zd.foster.breed.services.IMedicalBillService;

/**
 * 类名：  MedicalBillAction
 * 功能：
 * @author wxb
 * @date 2017-7-27上午09:32:35
 * @version 1.0
 * 
 */
public class MedicalBillAction extends BaseAction<MedicalBill, IMedicalBillService> {

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * 功能:审核
	 * @author:wxb
	 * @data:2017-8-1上午10:48:35
	 * @file:MedicalBillAction.java
	 * @return
	 * @throws Exception
	 */
	public String check()throws Exception{
		String[] idArr=ids.split(",");
		service.check(idArr);
		message=idArr.length+"";
		text(message);
		return NONE;
	}
	/**
	 * 
	 * 功能:撤销
	 * @author:wxb
	 * @data:2017-8-1上午10:48:43
	 * @file:MedicalBillAction.java
	 * @return
	 * @throws Exception
	 */
	public String cancelCheck()throws Exception{
		service.cancelCheck(id);
		message="1";
		text(message);
		return NONE;
	}
	
	/**
	 * 
	 * 功能:下载模板
	 * @author:wxb
	 * @data:2017-9-12下午01:43:44
	 * @file:DrugBillAction.java
	 * @return
	 * @throws Exception
	 */
	public String downloadTemplate()throws Exception{
		stream = service.downloadTemplate(ServletActionContext.getServletContext().getRealPath("/"));
		docFileName= ExcelUtil.getFileName("领药单导入模板.xlsx");
		return DOWN;
	}
	
	/**
	 * 
	 * 功能:导入领药单
	 * 重写:wxb
	 * 2017-9-12
	 * @see com.zd.epa.base.BaseAction#importFile()
	 */
	public String importFile()throws Exception{
		elist = service.operateFile(doc,e.getCompany(),docFileName);
		result.put("Rows", elist);
		result.put("Total", elist.size());
		return JSON;
	}


}
