
package com.zd.foster.price.actions;

import org.apache.struts2.ServletActionContext;

import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.ExcelUtil;
import com.zd.foster.price.entity.DrugPriceDtl;
import com.zd.foster.price.services.IDrugPriceDtlService;

/**
 * 药品定价单明细action层
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-28 下午04:11:28
 */
public class DrugPriceDtlAction extends BaseAction<DrugPriceDtl, IDrugPriceDtlService> {
	
	/**
	 * 模板下载
	 * @Description:TODO
	 * @param realPath
	 * @return
	 * @throws Exception
	 * InputStream
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-4 下午03:49:25
	 */
	public String downloadTemplate()throws Exception{
		stream = service.downloadTemplate(ServletActionContext.getServletContext().getRealPath("/"));
		docFileName= ExcelUtil.getFileName("药品定价单.xlsx");
		return DOWN;
	}
	
	/**
	 * 导入定价单
	 * @Description:TODO
	 * @param doc
	 * @param company
	 * @param startDate
	 * @param objects
	 * @return
	 * @throws Exception
	 * List<DrugPriceDtl>
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-5 上午10:30:20
	 */
	public String importFile()throws Exception{
		elist = service.operateFile(doc,e.getDrugPrice().getCompany(),docFileName);
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
