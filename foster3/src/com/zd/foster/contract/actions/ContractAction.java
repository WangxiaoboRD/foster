package com.zd.foster.contract.actions;

import java.io.File;

import org.apache.struts2.ServletActionContext;
import com.zd.epa.base.BaseAction;
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.ExcelUtil;
import com.zd.epa.utils.TypeUtil;
import com.zd.foster.contract.entity.Contract;
import com.zd.foster.contract.services.IContractService;


/**
 * 类名：  ContractAction
 * 功能：
 * @author 杜中良
 * @date 2017-7-19下午02:18:34
 * @version 1.0
 * 
 */
public class ContractAction extends BaseAction<Contract, IContractService> {
	
	/**
	 * 复制新增
	 * dzl
	 * @return
	 * @throws Exception
	 */
	public String loadCopyById()throws Exception{
		//e = service.selectById(etype,id);
		//e = service.selectById(id);
		Integer _id = TypeUtil.getIdType(id,e.getClass());
		e = _id==null?service.selectById(id):service.selectById(_id);
		return "copy";
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
	 * 单据时效
	 * @return
	 * @throws Exception
	 */
	public String unEffect()throws Exception{	
		service.enableEffect(id);
		message = "1";		
		text(message);
		return NONE;
	}
	
	/**
	 * 单据时效
	 * @return
	 * @throws Exception
	 */
	public String unStopEffect()throws Exception{	
		service.enableStopEffect(id);
		message = "1";		
		text(message);
		return NONE;
	}
	
	/**
	 * 单据恢复
	 * @return
	 * @throws Exception
	 */
	public String onEffect()throws Exception{	
		service.onEffect(id);
		message = "1";		
		text(message);
		return NONE;
	}
	
	/**
	 * 简单对象删除
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 12:00:20 PM <br/>
	 */
	public String delete()throws Exception{	
		String path = ServletActionContext.getServletContext().getRealPath("/WEB-INF/contract") ;
		int k =service.deleteByIds(ids.split(","),path);
		message = k+"";		
		text(message);
		return NONE;
	}
	
	/**
	 * 功能：上传<br/>
	 *
	 * @author DZL
	 * @version 2014-6-19 上午10:08:38 <br/>
	 */
	public String upload()throws Exception{
		if(doc==null)
			throw new SystemException("请选择文件");
		//上传附件
		String path = ServletActionContext.getServletContext().getRealPath("/WEB-INF/contract") ;
		boolean b = service.upload(doc, path, docFileName, id);
		if(b)
			text("1");
		else
			text("0");
		return JSON;
	}
	
	/**
	 * 功能：上传<br/>
	 *
	 * @author DZL
	 * @version 2014-6-19 上午10:08:38 <br/>
	 */
	public String download()throws Exception{
		//下载附件
		String path = ServletActionContext.getServletContext().getRealPath("/WEB-INF/contract");
//		String[] fname = new String[1];
//		stream = service.down(path,id,fname);
		//docFileName= fname[0];
		//获取文件全名称
		Contract c = service.selectById(id);
		id = null;
		String _fileName = c.getFarmer().getName()+c.getCode();
		File file = new File(path);
		File[] files = file.listFiles();
		if(files != null && files.length>0){
			for(File f : files){
				if(f.isFile()){
					//获取文件名比较文件名
					String oldname = f.getName();
					//取出后缀
					String[] _strname = oldname.split("\\.");
					if(_strname != null && _strname.length>0 && (_strname[0].equals(_fileName))){
						_fileName = oldname;
						break;
					}
				}
			}
		}
		stream=ServletActionContext.getServletContext().getResourceAsStream("/WEB-INF/contract/" + _fileName);
		if(stream==null)
			throw new SystemException("未找到合同附件");
		docFileName= ExcelUtil.getFileName(_fileName);
		return DOWN;
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
		docFileName= ExcelUtil.getFileName("合同导入模板.xlsx");
		return DOWN;
	}
	
	/**
	 * 导入合同
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
