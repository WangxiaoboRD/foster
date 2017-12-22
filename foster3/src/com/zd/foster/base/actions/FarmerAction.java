package com.zd.foster.base.actions;

import org.apache.struts2.ServletActionContext;

import com.zd.epa.base.BaseAction;
import com.zd.epa.utils.ExcelUtil;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.services.IFarmerService;

/**
 * 类名：  FarmerAction
 * 功能：
 * @author wxb
 * @date 2017-7-19下午02:56:58
 * @version 1.0
 * 
 */
public class FarmerAction extends BaseAction<Farmer, IFarmerService> {

	private static final long serialVersionUID = 1L;
	private String key; 
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * 通过拼音检索代养户
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-6 下午02:50:49
	 */
	public String loadByPinYin()throws Exception{
		elist = service.selectByPinyin(e,key);
		result.put("Rows", elist);
		//result.put("Total", elist.size());
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
		docFileName= ExcelUtil.getFileName("代养户导入模板.xlsx");
		return DOWN;
	}
	
	/**
	 * 导入代养户
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
	
	
}
