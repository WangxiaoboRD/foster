
package com.zd.foster.base.services.impl;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.PapUtil;
import com.zd.foster.base.dao.ISysConfigDao;
import com.zd.foster.base.entity.SysConfig;
import com.zd.foster.base.services.ISysConfigService;

/**
 * 类名：  SYSConfigServiceImpl
 * 功能：
 * @author 小丁
 * @date 2017-7-19下午02:14:10
 * @version 1.0
 * 
 */
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfig, ISysConfigDao> implements ISysConfigService {

	
	/**
	 * 保存
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-20 下午02:52:22
	 */
	public Object save(SysConfig entity) throws Exception{
		if(entity==null)
			throw new SystemException("对象不允许为空");
		if(entity.getCompany()==null || entity.getCompany().getId()==null || "".equals(entity.getCompany().getId()))
			throw new SystemException("养殖公司不允许为空");
		
		if(entity.getCoefficient()==null || "".equals(entity.getCoefficient()))
			throw new SystemException("药品价格系数不能为空");
		else{
			if(!PapUtil.isNum(entity.getCoefficient()))
				throw new SystemException("药品价格系数必须输入数字");
			double d = Double.parseDouble(entity.getCoefficient());
			if(d < 0)
				throw new SystemException("药品价格系数不允许为负数");
		}
		
		int i = selectTotalRows("company.id", entity.getCompany().getId());
		if(i>0)
			throw new SystemException("养殖公司系统参数已经存在");
		
		//保存对象
		return dao.insert(entity);
	}
	
	/**
	 * 修改
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 65373
	 * @time:2017-7-20 下午03:15:55
	 */
	public int updateHql(SysConfig entity)throws Exception{
		
		if(entity==null)
			throw new SystemException("对象不允许为空");
		if(entity.getCompany()==null || entity.getCompany().getId()==null || "".equals(entity.getCompany().getId()))
			throw new SystemException("养殖公司不允许为空");
		
		if(entity.getCoefficient()==null || "".equals(entity.getCoefficient()))
			throw new SystemException("药品价格系数不能为空");
		else{
			if(!PapUtil.isNum(entity.getCoefficient()))
				throw new SystemException("药品价格系数必须输入数字");
			double d = Double.parseDouble(entity.getCoefficient());
			if(d < 0)
				throw new SystemException("药品价格系数不允许为负数");
		}
		return super.updateHql(entity);
	}
	
	
	
	
	
	
	
	
	
}
