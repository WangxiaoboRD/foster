/**
 * 功能:
 * @author:wxb
 * @data:2017-8-1下午08:33:14
 * @file:DeathBillDtlAction.java
 */
package com.zd.foster.breed.actions;

import java.util.List;

import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.PapUtil;
import com.zd.foster.breed.entity.DeathBillDtl;
import com.zd.foster.breed.services.IDeathBillDtlService;
import com.zd.foster.dto.DeathAnalysis;

/**
 * 类名：  DeathBillDtlAction
 * 功能：
 * @author wxb
 * @date 2017-8-1下午08:33:14
 * @version 1.0
 * 
 */
public class DeathBillDtlAction extends BaseAction<DeathBillDtl, IDeathBillDtlService> {
	private static final long serialVersionUID = 1L;

	
	/**
	 * 查询死亡报表
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-17 上午10:34:18
	 */
	public String loadDeathAnalysisByPage()throws Exception{
		
		List<DeathAnalysis> dlist = service.selectDeathAnalysisByPage(e,pageBean);
		result.put("Rows", dlist);
		result.put("Total", pageBean.getTotalCount());
		result.put("pageBean", pageBean);
		return JSON;
	}
	
	/**
	 * 导出死亡报表
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-17 下午04:19:36
	 */
	public String exportDeathAnalysis()throws Exception{
		stream = service.exportBook(e);
		docFileName= PapUtil.getFileName("死亡报表.xls");
		return DOWN;
	}
	
}
