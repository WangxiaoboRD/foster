
package com.zd.foster.base.actions;

import org.apache.struts2.ServletActionContext;

import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.ExcelUtil;
import com.zd.foster.base.entity.Fcr;
import com.zd.foster.base.services.IFcrService;

/**
 * fcr action层
 * @Description:TODO
 * @author:小丁
 * @time:2017-9-6 下午06:56:13
 */
public class FcrAction extends BaseAction<Fcr, IFcrService> {
	
	
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
		docFileName= ExcelUtil.getFileName("料肉比导入模板.xlsx");
		return DOWN;
	}
	
	/**
	 * 导入料肉比
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-5 下午03:26:07
	 */
	public String importFile()throws Exception{
		elist = service.operateFile(doc,docFileName);
		result.put("Rows", elist);
		result.put("Total", elist.size());
		return JSON;
	}
	

}
