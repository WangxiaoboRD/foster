package com.zd.epa.log.services.impl;

import java.util.Map;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.log.dao.ILoginLogDao;
import com.zd.epa.log.entity.LoginLog;
import com.zd.epa.log.services.ILoginLogService;
import com.zd.epa.utils.Pager;
import com.zd.epa.utils.SqlMap;

public class LoginLogServiceImpl extends BaseServiceImpl<LoginLog,ILoginLogDao> implements ILoginLogService{

	/**
	 * 功能: <br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2014-7-1 下午06:09:45<br/>
	 * 
	 * @param entity
	 * @param page
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#selectAll(com.zhongpin.pap.base.BaseEntity, com.zhongpin.pap.utils.Pager)
	 */
	public void selectAll(LoginLog entity, Pager<LoginLog> page) throws Exception {
		
		Map<String, String> map = entity.getTempStack();
		SqlMap<String,String,Object> sqlMap = new SqlMap<String,String,Object>();
		if(map != null && map.size()>0){
			
			String startDate = map.get("startDate");
			if(startDate != null && !"".equals(startDate)){
				startDate = startDate+" 00:00:00";
				sqlMap.put("loginDate", ">=", startDate);
			}
			String endDate = map.get("endDate");
			if(endDate != null && !"".equals(endDate)){
				endDate = endDate+" 23:59:59";
				sqlMap.put("loginDate", "<=", endDate);
			}
		}
		dao.selectByConditionHQL(entity, sqlMap, page);
	}
}
