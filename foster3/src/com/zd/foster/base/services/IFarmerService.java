package com.zd.foster.base.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.Farmer;

/**
 * 类名：  IFarmerService
 * 功能：
 * @author wxb
 * @date 2017-7-19下午02:53:47
 * @version 1.0
 * 
 */
public interface IFarmerService extends IBaseService<Farmer> {

	List<Farmer> selectByPinyin(Farmer e, String key) throws Exception;
	
	InputStream downloadTemplate(String realPath)throws Exception;

	List<Farmer> operateFile(File doc, Company company, Object... objects)throws Exception;
	
}
