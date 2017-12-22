package com.zd.foster.sale.actions;

import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.TypeUtil;
import com.zd.foster.sale.entity.OutPigsty;
import com.zd.foster.sale.services.IOutPigstyService;


/**
 * 类名：  OutPigstyAction
 * 功能：
 * @author 杜中良
 * @date 2017-7-19下午02:18:34
 * @version 1.0
 * 
 */
public class OutPigstyAction extends BaseAction<OutPigsty, IOutPigstyService> {
	
	//获取对象
	public String loadEntity()throws Exception{
		elist = service.selectEntity(e);
		result.put("Rows", elist);
		//result.put("Total", elist.size());
		return JSON;
	}
	
	/**
	 * 单据审核
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
	 * 单据撤销
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
	 * 获取对象
	 * @return
	 * @throws Exception
	 */
	public String loadById()throws Exception{
		e = service.selectId(id);
		result.put("e", e);
		return JSON;
	}
}
