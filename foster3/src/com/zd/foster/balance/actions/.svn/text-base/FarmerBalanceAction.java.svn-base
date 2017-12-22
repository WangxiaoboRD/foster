package com.zd.foster.balance.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.ExcelUtil;
import com.zd.foster.balance.entity.FarmerBalance;
import com.zd.foster.balance.services.IFarmerBalanceService;

/**
 * 类名：  FarmerBillAction
 * 功能：
 * @author dzl
 * @date 2017-7-19下午02:56:58
 * @version 1.0
 * 
 */
public class FarmerBalanceAction extends BaseAction<FarmerBalance, IFarmerBalanceService> {
	/**
	 * 单据审核
	 * @return
	 * @throws Exception
	 */
	public String balance()throws Exception{	
		service.balance(id);
		text("1");
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
	 * 
	 * 功能:下载代养户结算单模板
	 * @author:wxb
	 * @data:2017-9-12下午05:52:20
	 * @file:FarmerBalanceAction.java
	 * @return
	 * @throws Exception
	 */
	public String downloadTemplate()throws Exception{
		stream = service.downloadTemplate(ServletActionContext.getServletContext().getRealPath("/"));
		docFileName= ExcelUtil.getFileName("代养户结算单导入模板.xlsx");
		return DOWN;
	}
	
	/**
	 * 
	 * 功能:导入代养户结算单
	 * 重写:wxb
	 * 2017-9-12
	 * @see com.zd.epa.base.BaseAction#importFile()
	 */
	public String importFile()throws Exception{
		List<String> idList=new ArrayList<String>();
		elist = service.operateFile(doc,e.getCompany(),idList,docFileName);
		Iterator<String> it=idList.iterator();
		while(it.hasNext()){
			service.balance(it.next());
		}
		result.put("Rows", elist);
		result.put("Total", elist.size());
		return JSON;
	}

}
