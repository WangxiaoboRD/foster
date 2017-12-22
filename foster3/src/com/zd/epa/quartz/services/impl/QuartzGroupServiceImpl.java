/**
 * 文件名：@QuartzGroupServiceImpl.java <br/>
 * 包名：com.zhongpin.pap.quartz.services.impl <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.zd.epa.quartz.services.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sun.xml.internal.bind.v2.model.core.ID;
import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.quartz.dao.IQuartzGroupDao;
import com.zd.epa.quartz.entity.QuartzGroup;
import com.zd.epa.quartz.entity.QuartzJob;
import com.zd.epa.quartz.services.IQuartzGroupService;
import com.zd.epa.quartz.services.IQuartzJobService;
import com.zd.epa.utils.SqlMap;

/**
 * 类名：QuartzGroupServiceImpl  <br />
 *
 * 功能：调度作业组业务逻辑层实现
 *
 * @author 孟雪勤 <br />
 * 创建时间：2014-1-4 下午05:23:27  <br />
 * @version 2014-1-4
 */
public class QuartzGroupServiceImpl extends BaseServiceImpl<QuartzGroup, IQuartzGroupDao> implements IQuartzGroupService {
	
	/** 调度作业业务注册 */
	@Autowired
	private IQuartzJobService quartzJobService;

	/**
	 * 功能: 删除<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2014-1-4 下午05:48:35<br/>
	 * 
	 * @param <ID>
	 * @param PK
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#deleteByIds(ID[])
	 */
	@Override
	public <ID extends Serializable> int deleteByIds(ID[] PK) throws Exception {
		// 若被调度作业引用则不允许删除
		SqlMap<String, String, String> sqlMap = new SqlMap<String, String, String>();
		sqlMap.put("quartzGroup.id", "in", Arrays.toString(PK).replaceAll("[\\[\\]\\s]", ""));
		List<QuartzJob> quartzJobs = quartzJobService.selectHQL(sqlMap);
		if (null != quartzJobs && quartzJobs.size() > 0) {
			throw new SystemException("所删除的作业组已被调度作业引用，不允许删除！");
		}
		
		return dao.deleteByIds(PK);
	}
	
}
