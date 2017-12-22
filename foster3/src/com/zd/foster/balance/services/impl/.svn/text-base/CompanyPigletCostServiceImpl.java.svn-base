
package com.zd.foster.balance.services.impl;

import java.util.List;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.balance.dao.ICompanyPigletCostDao;
import com.zd.foster.balance.entity.CompanyPigletCost;
import com.zd.foster.balance.entity.CompanySaleIncome;
import com.zd.foster.balance.services.ICompanyPigletCostService;

/**
 * 公司账单猪苗明细业务层
 * @Description:TODO
 * @author:小丁
 * @time:2017-8-7 下午03:59:58
 */
public class CompanyPigletCostServiceImpl extends BaseServiceImpl<CompanyPigletCost, ICompanyPigletCostDao> implements ICompanyPigletCostService {
	/**
	 * 按照页面搜索条件分页查询
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 5:57:37 PM <br/>
	 */
	public List<CompanyPigletCost> selectAll(CompanyPigletCost entity)throws Exception{
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		sqlMap.put("pigPurchase.id", "order by", "asc");
		return super.selectHQL(entity, sqlMap);
	}
}
