/**
 * 功能:
 * @author:wxb
 * @data:2017-9-8上午10:40:32
 * @file:drugBillAction.java
 */
package com.zd.foster.warehouse.actions;

import java.util.List;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.ExcelUtil;
import com.zd.epa.utils.PapUtil;
import com.zd.foster.dto.MedicalAnalysis;
import com.zd.foster.warehouse.entity.DrugBill;
import com.zd.foster.warehouse.services.IDrugBillService;

/**
 * 类名：  drugBillAction
 * 功能：
 * @author wxb
 * @date 2017-9-8上午10:40:32
 * @version 1.0
 * 
 */
public class DrugBillAction extends BaseAction<DrugBill, IDrugBillService> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * 功能:审核
	 * @author:wxb
	 * @data:2017-7-28下午08:02:58
	 * @file:DrugInWareAction.java
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
	 * @data:2017-7-28下午08:03:08
	 * @file:DrugInWareAction.java
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
	 * 功能:回执
	 * @author:wxb
	 * @data:2017-9-9下午09:11:49
	 * @file:DrugBillAction.java
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
	 * @data:2017-9-9下午09:12:08
	 * @file:DrugBillAction.java
	 * @return
	 * @throws Exception
	 */
	public String cancelReceipt()throws Exception{
		service.cancelReceipt(id);
		text("1");
		return NONE;
	}
	
	
	/**
	 * 
	 * 功能:下载模板
	 * @author:wxb
	 * @data:2017-9-12下午01:43:44
	 * @file:DrugBillAction.java
	 * @return
	 * @throws Exception
	 */
	public String downloadTemplate()throws Exception{
		stream = service.downloadTemplate(ServletActionContext.getServletContext().getRealPath("/"));
		docFileName= ExcelUtil.getFileName("领药单导入模板.xlsx");
		return DOWN;
	}
	
	/**
	 * 
	 * 功能:导入领药单
	 * 重写:wxb
	 * 2017-9-12
	 * @see com.zd.epa.base.BaseAction#importFile()
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
	public String loadMedicalByPage()throws Exception{
		List<MedicalAnalysis> falist = service.selectMedicalByPage(e,pageBean);
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
	public String exportMedicalReport()throws Exception{
		stream = service.exportMedical(e);
		docFileName= PapUtil.getFileName("药品耗用报表.xls");
		return DOWN;
	}

	/**
	 * 
	 * 功能:打印领药单
	 * @author:wxb
	 * @data:2017-9-22下午05:19:45
	 * @file:DrugBillAction.java
	 * @return
	 * @throws Exception
	 */
	public String print()throws Exception{
		Map<String,Object> map = service.printDrugBill(id);
		result.put("Rows", map);
		return JSON;
	}

}
