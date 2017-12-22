
package com.zd.foster.material.services.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.bussobj.entity.BussinessEleDetail;
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.material.dao.IFeedTypeDao;
import com.zd.foster.material.entity.FeedType;
import com.zd.foster.material.services.IFeedService;
import com.zd.foster.material.services.IFeedTypeService;
import com.zd.foster.utils.FosterUtil;

/**
 * 饲料类型服务鞥实现
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-23 下午03:24:11
 */
public class FeedTypeServiceImpl extends BaseServiceImpl<FeedType, IFeedTypeDao> implements IFeedTypeService {

	@Resource
	private IFeedService feedService;
	
	/**
	 * 保存
	 * 小丁
	 * 17-07-19
	 */
	public Object save(FeedType entity) throws Exception{
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		if(entity==null)
			throw new SystemException("对象不允许为空");
		if(entity.getCompany()==null || entity.getCompany().getId()==null || "".equals(entity.getCompany().getId()))
			throw new SystemException("养殖公司不允许为空");
		if(entity.getName()==null || "".equals(entity.getName()))
			throw new SystemException("名称不允许为空");
		else{
			sqlMap.put("name", "=", entity.getName());
			sqlMap.put("company.id", "=", entity.getCompany().getId());
			int i =selectTotalRows(sqlMap);
			sqlMap.clear();
			
			if(i>0)
				throw new SystemException("名称不允许重复");
		}
		
		if(entity.getCode()==null || "".equals(entity.getCode()))
			throw new SystemException("编码不允许为空");
		else{
			//1.验证编码
			if(!FosterUtil.idCode(entity.getCode()))
				throw new SystemException("编码应为十位内的数字和字母！");
			
			sqlMap.put("code", "=", entity.getCode());
			sqlMap.put("company.id", "=", entity.getCompany().getId());
			int i =selectTotalRows(sqlMap);
			sqlMap.clear();
			
			if(i>0)
				throw new SystemException("编码不允许重复");
		}
		
		if(entity.getFeedStand()==null || "".equals(entity.getFeedStand()))
			throw new SystemException("耗料标准不允许为空");
		else{
			if(!PapUtil.isNum(entity.getFeedStand()))
				throw new SystemException("耗料标准请输入数字");
		}
		
		entity.setMaterialType(new BussinessEleDetail("feed"));
		//保存对象
		return dao.insert(entity);
	}
	
	/**
	 * 修改
	 * 小丁
	 * 17-07-19
	 */
	public int updateHql(FeedType entity)throws Exception{
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		if(entity == null)
			throw new SystemException("对象不允许为空");
		if(entity.getCompany()==null || entity.getCompany().getId()==null || "".equals(entity.getCompany().getId()))
			throw new SystemException("养殖公司不允许为空");
		if(entity.getName()==null || "".equals(entity.getName()))
			throw new SystemException("名称不允许为空");
		else{
			sqlMap.put("name", "=", entity.getName());
			sqlMap.put("id", "<>", entity.getId());
			sqlMap.put("company.id", "=", entity.getCompany().getId());
			int i =selectTotalRows(sqlMap);
			sqlMap.clear();
			
			if(i>0)
				throw new SystemException("名称不允许重复");
		}
		
		if(entity.getCode()==null || "".equals(entity.getCode()))
			throw new SystemException("编码不允许为空");
		else{
			//1.验证编码
			if(!FosterUtil.idCode(entity.getCode()))
				throw new SystemException("编码应为十位内的数字和字母！");
			
			sqlMap.put("code", "=", entity.getCode());
			sqlMap.put("id", "<>", entity.getId());
			sqlMap.put("company.id", "=", entity.getCompany().getId());
			int i =selectTotalRows(sqlMap);
			sqlMap.clear();
			
			if(i>0)
				throw new SystemException("编码不允许重复");
		}
		if(entity.getFeedStand()==null || "".equals(entity.getFeedStand()))
			throw new SystemException("耗料标准不允许为空");
		else{
			if(!PapUtil.isNum(entity.getFeedStand()))
				throw new SystemException("耗料标准请输入数字");
		}
		//验证饲料里使用、******************************

		
		entity.setMaterialType(new BussinessEleDetail("feed"));
		super.update(entity);
		return 1;
	}
	
	/**
	 * 单个对象批量删除
	 * 功能：<br/>
	 * @author 小丁
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		for(ID id : PK){
			//删除饲料类型：验证饲料里使用
			int n = feedService.selectTotalRows("feedType.id", id);
			if(n>0)
				throw new SystemException("此饲料阶段正在被使用，不允许删除");
			
		}
		
		return dao.deleteByIds(PK);
	}
	
	
	/**
	 * 查找公司饲料类型名字
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-21 上午11:14:28
	 */
	public String[] selectByCor(FeedType entity)throws Exception{
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("company.id", "=", entity.getMap().get("company"));
		sqlMap.put("name", "order by", "asc");
		List<FeedType> fts = selectHQL(sqlMap);
		
		String names = "";
		for(FeedType ft : fts){
			names += ft.getName()+",";
		}
		
		String[] name = names.split(",");
		return name;
	}
	
	
	
	
	
	
	
	
}
