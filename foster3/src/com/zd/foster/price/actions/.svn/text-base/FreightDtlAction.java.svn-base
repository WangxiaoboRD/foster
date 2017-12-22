
package com.zd.foster.price.actions;

import org.apache.struts2.ServletActionContext;

import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.ExcelUtil;
import com.zd.foster.price.entity.FreightDtl;
import com.zd.foster.price.services.IFreightDtlService;

/**
 * 运费定价单明细action层
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-31 下午04:01:29
 */
public class FreightDtlAction extends BaseAction<FreightDtl, IFreightDtlService> {
	
	/**
	 * 导入模板下载
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-3 上午10:12:19
	 */
	public String downloadTemplate()throws Exception{
		stream = service.downloadTemplate(ServletActionContext.getServletContext().getRealPath("/"));
		docFileName= ExcelUtil.getFileName("运费定价单.xlsx");
		return DOWN;
	}
	
	/**
	 * 导入运费定价单
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-3 上午10:24:42
	 */
	public String importFile()throws Exception{
		elist = service.operateFile(doc,e.getFreight().getCompany(),docFileName);
		result.put("Rows", elist);
		result.put("Total", elist.size());
		return JSON;
	}
	
	/**
	 * 复制新增定价单
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
