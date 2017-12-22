package com.zd.foster.contract.services;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.foster.base.entity.Company;
import com.zd.foster.contract.entity.Contract;
import com.zd.foster.material.entity.Drug;

/**
 * 类名：  IContractService
 * 功能：
 * @author DZL
 * @date 2017-7-19下午02:13:02
 * @version 1.0
 * 
 */
public interface IContractService extends IBaseService<Contract> {

	/**
	 * 功能：审核<br/>
	 *
	 * @author dzl
	 * @version 2015-3-9 下午05:28:16 <br/>
	 */
	void check(String[] idArr)throws Exception;
	/**
	 * 功能：撤销<br/>
	 *
	 * @author dzl
	 * @version 2015-3-9 下午05:28:16 <br/>
	 */
	void cancelCheck(String id)throws Exception;
	/**
	 * 终止合同
	 * @param id
	 * @throws Exception
	 */
	void enableEffect(String id)throws Exception;
	
	/**
	 * 中止合同
	 * @param id
	 * @throws Exception
	 */
	void enableStopEffect(String id)throws Exception;
	
	/**
	 * 终止还原
	 * @param id
	 * @throws Exception
	 */
	void onEffect(String id)throws Exception;
	
	/**
	 * 上传合同
	 * @param id
	 * @throws Exception
	 */
	boolean upload(File doc,String path,String fileName,String id)throws Exception;
	
	/**
	 * 下载合同
	 * @param id
	 * @throws Exception
	 */
	InputStream down(String path,String id,String[] fname)throws Exception;
	
	/**
	 * 删除
	 * @param <ID>
	 * @param PK
	 * @param path
	 * @return
	 * @throws Exception
	 */
	<ID extends Serializable> int deleteByIds(ID[] PK,String path)throws Exception;
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
	InputStream downloadTemplate(String realPath)throws Exception;
	/**
	 * 导入合同
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-5 下午03:26:07
	 */
	List<Contract> operateFile(File doc, Company company, Object... objects)throws Exception;
	
}
