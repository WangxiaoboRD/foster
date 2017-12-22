
package com.zd.foster.price.actions;

import org.apache.struts2.ServletActionContext;

import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.ExcelUtil;
import com.zd.foster.price.entity.FeedPriceDtl;
import com.zd.foster.price.services.IFeedPriceDtlService;

/**
 * 饲料定价单明细action层
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-27 上午10:42:41
 */
public class FeedPriceDtlAction extends BaseAction<FeedPriceDtl, IFeedPriceDtlService> {
	
	/**
	 * 模板下载
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-4 下午02:49:36
	 */
	public String downloadTemplate()throws Exception{
		stream = service.downloadTemplate(ServletActionContext.getServletContext().getRealPath("/"));
		docFileName= ExcelUtil.getFileName("饲料定价单.xlsx");
		return DOWN;
	}
	
	
	/**
	 * 导入定价单
	 * @Description:TODO
	 * @param doc
	 * @param company
	 * @param feedFac
	 * @param startDate
	 * @param objects
	 * @return
	 * @throws Exception
	 * List<FeedPriceDtl>
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-4 下午03:00:46
	 */
	public String importFile()throws Exception{
		elist = service.operateFile(doc,e.getFeedPrice().getCompany(),docFileName);
		result.put("Rows", elist);
		result.put("Total", elist.size());
		return JSON;
	}
	
	/**
	 * 复制新增饲料定价单
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-18 下午03:28:09
	 */
	public String loadForCopy()throws Exception{
		
		//elist = service.selectAll(e);
		elist = service.selectAllForCopy(e);
		result.put("Rows", elist);
		//result.put("Total", elist.size());
		return JSON;
	}
	
	
}
