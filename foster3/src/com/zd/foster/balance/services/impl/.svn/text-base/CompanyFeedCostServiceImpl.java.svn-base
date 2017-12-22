
package com.zd.foster.balance.services.impl;

import java.util.List;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.balance.dao.ICompanyFeedCostDao;
import com.zd.foster.balance.entity.CompanyFeedCost;
import com.zd.foster.balance.services.ICompanyFeedCostService;

/**
 * 公司账单饲料明细业务层
 * @Description:TODO
 * @author:小丁
 * @time:2017-8-7 下午02:45:22
 */
public class CompanyFeedCostServiceImpl extends BaseServiceImpl<CompanyFeedCost, ICompanyFeedCostDao> implements ICompanyFeedCostService {
	
	/**
	 * 按照页面搜索条件分页查询
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 5:57:37 PM <br/>
	 */
	public List<CompanyFeedCost> selectAll(CompanyFeedCost entity)throws Exception{
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		sqlMap.put("feedInWare.id", "order by", "asc");
		return super.selectHQL(entity, sqlMap);
	}
}
