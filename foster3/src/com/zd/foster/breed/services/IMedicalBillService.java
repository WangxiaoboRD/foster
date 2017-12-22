/**
 * 功能:
 * @author:wxb
 * @data:2017-7-27上午09:30:32
 * @file:IMedicalBillService.java
 */
package com.zd.foster.breed.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.foster.base.entity.Company;
import com.zd.foster.breed.entity.MedicalBill;

/**
 * 类名：  IMedicalBillService
 * 功能：
 * @author wxb
 * @date 2017-7-27上午09:30:32
 * @version 1.0
 * 
 */
public interface IMedicalBillService extends IBaseService<MedicalBill> {
	
	public void check(String[] idArr)throws Exception;
	
	public void cancelCheck(String id)throws Exception;
	
	InputStream downloadTemplate(String realPath)throws Exception;

	List<MedicalBill> operateFile(File doc, Company company, Object... objects)throws Exception;

}
