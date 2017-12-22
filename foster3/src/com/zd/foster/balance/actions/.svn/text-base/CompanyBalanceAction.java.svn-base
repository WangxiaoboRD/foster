package com.zd.foster.balance.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.ExcelUtil;
import com.zd.foster.balance.entity.CompanyBalance;
import com.zd.foster.balance.services.ICompanyBalanceService;

/**
 * 养殖公司结算单Action层
 * @Description:TODO
 * @author:小丁
 * @time:2017-8-7 上午10:08:25
 */
public class CompanyBalanceAction extends BaseAction<CompanyBalance, ICompanyBalanceService> {

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
	 * 功能:下载模板
	 * @author:wxb
	 * @data:2017-9-13上午11:25:21
	 * @file:CompanyBalanceAction.java
	 * @return
	 * @throws Exception
	 */
	public String downloadTemplate()throws Exception{
		stream = service.downloadTemplate(ServletActionContext.getServletContext().getRealPath("/"));
		docFileName= ExcelUtil.getFileName("公司结算单导入模板.xlsx");
		return DOWN;
	}
	
	/**
	 * 
	 * 功能:导入养殖公司结算单
	 * 重写:wxb
	 * 2017-9-13
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
