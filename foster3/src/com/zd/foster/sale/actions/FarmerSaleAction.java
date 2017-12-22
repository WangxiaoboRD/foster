package com.zd.foster.sale.actions;

import org.apache.struts2.ServletActionContext;

import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.ExcelUtil;
import com.zd.foster.sale.entity.FarmerSale;
import com.zd.foster.sale.services.IFarmerSaleService;


/**
 * 类名：  ContractAction
 * 功能：
 * @author 杜中良
 * @date 2017-7-19下午02:18:34
 * @version 1.0
 * 
 */
public class FarmerSaleAction extends BaseAction<FarmerSale, IFarmerSaleService> {
	/**
	 * 单据审核
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
	 * 单据撤销
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
	 * 通过ids获取对象
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-30 上午09:45:36
	 */
	public String loadByIds()throws Exception {
		
		e = service.selectByIds(id);
		result.put("e", e);
		return JSON;
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
		docFileName= ExcelUtil.getFileName("销售单导入模板.xlsx");
		return DOWN;
	}
	
	/**
	 * 导入销售单
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
