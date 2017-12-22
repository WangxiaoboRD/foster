
package com.zd.foster.material.actions;

import org.apache.struts2.ServletActionContext;

import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.ExcelUtil;
import com.zd.foster.material.entity.Drug;
import com.zd.foster.material.services.IDrugService;

/**
 * 药品action层
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-26 上午10:06:36
 */
public class DrugAction extends BaseAction<Drug, IDrugService> {
	
	/**
	 * 加载带定价
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-18 下午02:08:46
	 */
	@Override
	public String loadByPage()throws Exception{
		
		//service.selectAll(e,pageBean);
		service.selectAllAndOldPrice(e,pageBean);
		result.put("Rows", pageBean.getResult());
		result.put("Total", pageBean.getTotalCount());
		pageBean.setResult(null);
		result.put("pageBean", pageBean);
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
		docFileName= ExcelUtil.getFileName("药品导入模板.xlsx");
		return DOWN;
	}
	
	/**
	 * 导入药品
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
	 * 多个编码加载药品
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-18 下午02:08:46
	 */
	public String loadByCodes()throws Exception{
		
		elist = service.selectByCodes(e);
		result.put("Rows", elist);
		result.put("Total", elist.size());
		return JSON;
	}
	
}
