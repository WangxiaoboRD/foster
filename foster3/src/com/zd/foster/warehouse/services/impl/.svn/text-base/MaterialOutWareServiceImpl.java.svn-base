/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午05:20:11
 * @file:MaterialOutWareServiceImpl.java
 */
package com.zd.foster.warehouse.services.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.warehouse.dao.IMaterialOutWareDao;
import com.zd.foster.warehouse.entity.MaterialOutWare;
import com.zd.foster.warehouse.services.IMaterialOutWareDtlService;
import com.zd.foster.warehouse.services.IMaterialOutWareService;

/**
 * 类名：  MaterialOutWareServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-7-26下午05:20:11
 * @version 1.0
 * 
 */
public class MaterialOutWareServiceImpl extends BaseServiceImpl<MaterialOutWare, IMaterialOutWareDao>
		implements IMaterialOutWareService {
	@Resource
	private IMaterialOutWareDtlService materialOutWareDtlService;
	/**
	 * 
	 * 功能:删除
	 * 重写:wxb
	 * 2017-7-28
	 * @see com.zd.epa.base.BaseServiceImpl#deleteByIds(ID[])
	 */
	public <ID extends Serializable> int deleteById(ID PK)throws Exception{
		if(PK==null )
			throw new SystemException("请选择删除对象");
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("materialOutWare.id", "=", PK);
		materialOutWareDtlService.delete(sqlMap);
		return dao.deleteById(PK);
	}

}
