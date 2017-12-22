package com.zd.foster.breed.actions;

import org.apache.struts2.ServletActionContext;

import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.ExcelUtil;
import com.zd.foster.breed.entity.FeedBill;
import com.zd.foster.breed.services.IFeedBillService;

/**
 * 类名：  FeedBillAction
 * 功能：
 * @author wxb
 * @date 2017-7-27上午08:59:25
 * @version 1.0
 * 
 */
public class FeedBillAction extends BaseAction<FeedBill, IFeedBillService> {

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * 功能:审核
	 * @author:wxb
	 * @data:2017-7-31下午04:50:51
	 * @file:FeedBillAction.java
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
	 * @data:2017-7-31下午04:50:41
	 * @file:FeedBillAction.java
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
	 * 功能:喂料分析
	 * @author:wxb
	 * @data:2017-9-1下午02:24:51
	 * @file:FeedBillAction.java
	 * @return
	 * @throws Exception
	 */
	public String loadFeedAnalysisByPage()throws Exception{
		result=service.selectFeedAnalysisByPage(e,pageBean);
		result.put("pagebean", pageBean);
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
		docFileName= ExcelUtil.getFileName("耗料单导入模板.xlsx");
		return DOWN;
	}
	
	/**
	 * 导入耗料单
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
