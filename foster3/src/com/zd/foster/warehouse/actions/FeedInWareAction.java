package com.zd.foster.warehouse.actions;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.ExcelUtil;
import com.zd.epa.utils.Pager;
import com.zd.epa.utils.PapUtil;
import com.zd.foster.dto.FeedAnalysis;
import com.zd.foster.warehouse.entity.FeedInWare;
import com.zd.foster.warehouse.services.IFeedInWareService;

/**
 * 类名：  FeedInWareAction
 * 功能：
 * @author wxb
 * @date 2017-7-26上午11:30:12
 * @version 1.0
 * 
 */
public class FeedInWareAction extends BaseAction<FeedInWare, IFeedInWareService> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * 功能:审核
	 * @author:wxb
	 * @data:2017-7-28下午02:01:58
	 * @file:FeedInWareAction.java
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
	 * @data:2017-7-28下午02:02:03
	 * @file:FeedInWareAction.java
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
		docFileName= ExcelUtil.getFileName("领料单导入模板.xlsx");
		return DOWN;
	}
	
	/**
	 * 导入领料单
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
	
	/**
	 * 饲料耗用报表
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: DZL
	 * @time:2017-9-5 下午03:26:07
	 */
	public String loadFeedInWareByPage()throws Exception{
		List<FeedAnalysis> falist = service.selectFeedInWareByPage(e,pageBean);
		if(falist != null){
			result.put("Rows", falist);
			result.put("Total", pageBean.getTotalCount());
			pageBean.setResult(null);
			result.put("pageBean", pageBean);
		}
		return JSON;
	}
	
	/**
	 * 饲料耗用报表导出
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: DZL
	 * @time:2017-9-5 下午03:26:07
	 */
	public String exportFeedInWareDetail()throws Exception{
		stream = service.exportFeedInWareDetail(e);
		docFileName= PapUtil.getFileName("饲料耗用报表.xls");
		return DOWN;
	}
}
