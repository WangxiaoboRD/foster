
package com.zd.foster.balance.services.impl;

import java.util.List;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.balance.dao.IFarmerSaleIncomeDao;
import com.zd.foster.balance.entity.FarmerSaleIncome;
import com.zd.foster.balance.services.IFarmerSaleIncomeService;

/**
 * 类名：  FarmerSaleIncomeServiceImpl
 * 功能：
 * @author dzl
 * @date 2017-7-19下午02:54:38
 * @version 1.0
 * 
 */
public class FarmerSaleIncomeServiceImpl extends BaseServiceImpl<FarmerSaleIncome, IFarmerSaleIncomeDao> implements IFarmerSaleIncomeService {

	/**
	 * 查询对象
	 */
	public List<FarmerSaleIncome> selectFarmerSaleIncome(FarmerSaleIncome entity)throws Exception{
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		sqlMap.put("pigLevel.dcode", "order by", "asc");
		return super.selectHQL(entity, sqlMap);
	}
}
