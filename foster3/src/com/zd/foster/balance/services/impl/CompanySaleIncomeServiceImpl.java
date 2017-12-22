
package com.zd.foster.balance.services.impl;

import java.util.List;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.balance.dao.ICompanySaleIncomeDao;
import com.zd.foster.balance.entity.CompanyFreight;
import com.zd.foster.balance.entity.CompanySaleIncome;
import com.zd.foster.balance.services.ICompanySaleIncomeService;

/**
 * 公司账单销售明细业务层
 * @Description:TODO
 * @author:小丁
 * @time:2017-8-9 上午10:38:19
 */
public class CompanySaleIncomeServiceImpl extends BaseServiceImpl<CompanySaleIncome, ICompanySaleIncomeDao> implements ICompanySaleIncomeService {

	/**
	 * 按照页面搜索条件分页查询
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 5:57:37 PM <br/>
	 */
	public List<CompanySaleIncome> selectAll(CompanySaleIncome entity)throws Exception{
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		sqlMap.put("companySale.id", "order by", "asc");
		return super.selectHQL(entity, sqlMap);
	}
}
