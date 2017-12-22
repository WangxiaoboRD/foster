package com.zd.foster.sale.services.impl;

import java.util.List;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.sale.dao.IFarmerSaleDtlDao;
import com.zd.foster.sale.entity.FarmerSaleDtl;
import com.zd.foster.sale.services.IFarmerSaleDtlService;

/**
 * 类名：  FarmerSaleDtlServiceImpl
 * 功能：
 * @author DZL
 * @date 2017-7-19下午02:14:10
 * @version 1.0
 * 
 */
public class FarmerSaleDtlServiceImpl extends BaseServiceImpl<FarmerSaleDtl, IFarmerSaleDtlDao> implements IFarmerSaleDtlService{

	/**
	 * 按照页面搜索条件分页查询
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 5:57:37 PM <br/>
	 */
	public List<FarmerSaleDtl> selectAll(FarmerSaleDtl entity)throws Exception{
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		sqlMap.put("pigLevel.dcode", "order by", "asc");
		List<FarmerSaleDtl> fsl = super.selectHQL(entity, sqlMap);
		return fsl;
	}
}
