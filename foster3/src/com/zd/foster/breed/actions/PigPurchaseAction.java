package com.zd.foster.breed.actions;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.ExcelUtil;
import com.zd.epa.utils.PapUtil;
import com.zd.foster.breed.entity.PigPurchase;
import com.zd.foster.breed.services.IPigPurchaseService;
import com.zd.foster.dto.DeathAnalysis;

/**
 * 类名：  PigPurchaseAction
 * 功能：
 * @author wxb
 * @date 2017-8-1下午04:26:18
 * @version 1.0
 * 
 */
public class PigPurchaseAction extends BaseAction<PigPurchase, IPigPurchaseService> {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * 功能:审核
	 * @author:wxb
	 * @data:2017-8-3上午09:06:26
	 * @file:PigPurchaseAction.java
	 * @return
	 * @throws Exception
	 */
	public String check()throws Exception{
		String[] idArr=ids.split(",");
		service.check(idArr);
		message=idArr.length+"";
		text(message);
		return NONE;
	}
	/**
	 * 
	 * 功能:撤销
	 * @author:wxb
	 * @data:2017-8-3上午09:06:42
	 * @file:PigPurchaseAction.java
	 * @return
	 * @throws Exception
	 */
	public String cancelCheck()throws Exception{
		service.cancelCheck(id);
		message="1";
		text(message);
		return NONE;
	}
	
	/**
	 * 
	 * 功能:回执
	 * @author:wxb
	 * @data:2017-9-10下午08:09:01
	 * @file:PigPurchaseAction.java
	 * @return
	 * @throws Exception
	 */
	public String receipt()throws Exception{
		String[] idArr=ids.split(",");
		service.receipt(idArr);
		text("1");
		return NONE;
		
	}
	/**
	 * 
	 * 功能:撤销回执
	 * @author:wxb
	 * @data:2017-9-10下午08:09:06
	 * @file:PigPurchaseAction.java
	 * @return
	 * @throws Exception
	 */
	public String cancelReceipt()throws Exception{
		service.cancelReceipt(id);
		text("1");
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
		docFileName= ExcelUtil.getFileName("猪苗登记导入模板.xlsx");
		return DOWN;
	}
	
	/**
	 * 导入猪苗登记
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
	 * 查询猪苗登记报表
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-17 上午10:34:18
	 */
	public String loadPigPurchaseByPage()throws Exception{
		
		List<PigPurchase> dlist = service.selectPigPurchaseByPage(e,pageBean);
		result.put("Rows", dlist);
		result.put("Total", pageBean.getTotalCount());
		result.put("pageBean", pageBean);
		return JSON;
	}
	
	/**
	 * 导出猪苗登记报表
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-17 下午04:19:36
	 */
	public String exportPigPurchase()throws Exception{
		stream = service.exportPigPurchase(e);
		docFileName= PapUtil.getFileName("猪苗登记报表.xls");
		return DOWN;
	}
	
	
}
