package com.zd.foster.breed.actions;

import java.io.File;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.zd.epa.base.BaseAction;
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.ExcelUtil;
import com.zd.foster.breed.entity.DeathBill;
import com.zd.foster.breed.services.IDeathBillService;

/**
 * 类名：  DeathBillAction
 * 功能：
 * @author wxb
 * @date 2017-8-1下午08:29:24
 * @version 1.0
 * 
 */
public class DeathBillAction extends BaseAction<DeathBill, IDeathBillService> {

	private static final long serialVersionUID = 1L;
	
	private File[] files;
	
	private String[] fileNames;
	
	/**
	 * 
	 * 功能:审核
	 * @author:wxb
	 * @data:2017-8-6上午11:04:12
	 * @file:DeathBillAction.java
	 * @return
	 * @throws Exception
	 */
	public String check()throws Exception{
		String[] idArr=ids.split(",");
		service.check(idArr);
		text(idArr.length+"");
		return NONE;
	}
	/**
	 * 
	 * 功能:撤销
	 * @author:wxb
	 * @data:2017-8-6上午11:04:24
	 * @file:DeathBillAction.java
	 * @return
	 * @throws Exception
	 */
	public String cancelCheck()throws Exception{
		service.cancelCheck(id);
		text("1");
		return NONE;
	}
	
	/**
	 * 
	 * 功能:上传
	 * @author:wxb
	 * @data:2017-8-7下午05:45:06
	 * @file:DeathBillAction.java
	 * @return
	 * @throws Exception
	 */
	public String upload()throws Exception{
		if(files==null)
			throw new SystemException("请选择文件");
		//上传附件
		String path = ServletActionContext.getServletContext().getRealPath("/deathBill");
		boolean b = service.upload(files, path,fileNames,id);
		if(b)
			text("1");
		else
			text("0");
		return JSON;
	}

	/**
	 * 
	 * 功能:下载
	 * @author:wxb
	 * @data:2017-8-8上午11:39:52
	 * @file:DeathBillAction.java
	 * @return
	 * @throws Exception
	 */
	public String download()throws Exception{
		//下载附件
		String path = ServletActionContext.getServletContext().getRealPath("/WEB-INF/deathBill");
		String[] fname=new String[1];
		stream=service.down(path,id,fname);
//		//获取文件全名称
//		DeathBill c = service.selectById(id);
//		id = null;
//		String _fileName = c.getId();
//		File file = new File(path);
//		File[] files = file.listFiles();
//		if(files != null && files.length>0){
//			for(File f : files){
//				if(f.isFile()){
//					//获取文件名比较文件名
//					String oldname = f.getName();
//					//取出后缀
//					String[] _strname = oldname.split("\\.");
//					if(_strname != null && _strname.length>0 && (_strname[0].equals(_fileName))){
//						_fileName = oldname;
//						break;
//					}
//				}
//			}
//		}
//		stream=ServletActionContext.getServletContext().getResourceAsStream("/WEB-INF/deathBill/" + _fileName);
//		docFileName= ExcelUtil.getFileName(_fileName);
		if(fname[0]!=null)
			docFileName= ExcelUtil.getFileName(fname[0]);
		if(stream!=null)
			return DOWN;
		else
			throw new SystemException("未找到死亡单对应图片");
	}
	
	/**
	 * 
	 * 功能:确认回执
	 * @author:wxb
	 * @data:2017-9-9下午05:50:56
	 * @file:DeathBillAction.java
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
	 * @data:2017-9-9下午05:55:07
	 * @file:DeathBillAction.java
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
		docFileName= ExcelUtil.getFileName("死亡单导入模板.xlsx");
		return DOWN;
	}
	
	/**
	 * 导入死亡单
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
	 * 图片报表
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author:dzl     
	 * @time:2017-9-5 下午03:26:07
	 */
	public String loadByImg()throws Exception{
		String path = ServletActionContext.getServletContext().getRealPath("/deathBill");
		Map<String,String> map = service.selectByImg(e,path);
		result.put("Rows",map);
		return JSON;
	}
	
	public File[] getFiles() {
		return files;
	}
	public void setFiles(File[] files) {
		this.files = files;
	}
	public String[] getFileNames() {
		return fileNames;
	}
	public void setFileNames(String[] fileNames) {
		this.fileNames = fileNames;
	}
}
