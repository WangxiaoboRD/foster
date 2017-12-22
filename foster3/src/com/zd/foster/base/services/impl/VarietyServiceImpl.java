
package com.zd.foster.base.services.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.base.dao.IVarietyDao;
import com.zd.foster.base.entity.Variety;
import com.zd.foster.base.services.IVarietyService;
import com.zd.foster.contract.services.IContractService;
import com.zd.foster.utils.FosterUtil;

/**
 * 类名：  VarietyServiceImpl
 * 功能：
 * @author 小丁
 * @date 2017-7-19下午02:14:10
 * @version 1.0
 * 
 */
public class VarietyServiceImpl extends BaseServiceImpl<Variety, IVarietyDao> implements IVarietyService {

	@Resource
	private IContractService contractService;
	
	/**
	 * 保存
	 * 小丁
	 * 17-07-19
	 */
	public Object save(Variety entity) throws Exception{
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		if(entity==null)
			throw new SystemException("对象不允许为空");
		//1.验证编码
		if(!FosterUtil.idCode(entity.getCode()))
			throw new SystemException("编码应为十位内的数字和字母！");
		if(entity.getName()==null || "".equals(entity.getName()))
			throw new SystemException("名称不允许为空");
		else{
			sqlMap.put("name", "=", entity.getName());
			int i =selectTotalRows(sqlMap);
			sqlMap.clear();
			
			if(i>0)
				throw new SystemException("名称不允许重复");
		}
		
		if(entity.getCode()==null || "".equals(entity.getCode()))
			throw new SystemException("编码不允许为空");
		else{
			sqlMap.put("code", "=", entity.getCode());
			int i =selectTotalRows(sqlMap);
			sqlMap.clear();
			
			if(i>0)
				throw new SystemException("编码不允许重复");
		}
		
		//保存对象
		return dao.insert(entity);
	}
	
	/**
	 * 修改
	 * 小丁
	 * 17-07-19
	 */
	public int updateHql(Variety entity)throws Exception{
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		if(entity == null)
			throw new SystemException("对象不允许为空");
		//1.验证编码
		if(!FosterUtil.idCode(entity.getCode()))
			throw new SystemException("编码应为十位内的数字和字母！");
		if(entity.getName()==null || "".equals(entity.getName()))
			throw new SystemException("名称不允许为空");
		else{
			sqlMap.put("name", "=", entity.getName());
			sqlMap.put("id", "<>", entity.getId());
			int i =selectTotalRows(sqlMap);
			sqlMap.clear();
			
			if(i>0)
				throw new SystemException("名称不允许重复");
		}
		
		if(entity.getCode()==null || "".equals(entity.getCode()))
			throw new SystemException("编码不允许为空");
		else{
			sqlMap.put("code", "=", entity.getCode());
			sqlMap.put("id", "<>", entity.getId());
			int i =selectTotalRows(sqlMap);
			sqlMap.clear();
			
			if(i>0)
				throw new SystemException("编码不允许重复");
		}
		
		return super.updateHql(entity);
	}
	
	/**
	 * 单个对象批量删除
	 * 功能：<br/>
	 * @author 小丁
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		for(ID id : PK){
			//删除品种：验证使用品种的地方：合同
			int cnNum = contractService.selectTotalRows("variety.id", id);
			if(cnNum>0)
				throw new SystemException("此品种正在被使用，不允许删除");
		}
		
		return dao.deleteByIds(PK);
	}
	
}
