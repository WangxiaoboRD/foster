
package com.zd.foster.base.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.SmsUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.base.dao.IDevelopManDao;
import com.zd.foster.base.entity.DevelopMan;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.entity.FeedFac;
import com.zd.foster.base.entity.Technician;
import com.zd.foster.base.services.IDevelopManService;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.breed.services.IDeathBillService;
import com.zd.foster.breed.services.IEliminateBillService;
import com.zd.foster.breed.services.IPigPurchaseService;
import com.zd.foster.contract.services.IContractService;
import com.zd.foster.utils.FosterUtil;

/**
 * 类名：  DevelopManServiceImpl
 * 功能：
 * @author DZL
 * @date 2017-7-19下午02:14:10
 * @version 1.0
 * 
 */
public class DevelopManServiceImpl extends BaseServiceImpl<DevelopMan, IDevelopManDao> implements IDevelopManService {

	@Resource
	private IFarmerService farmerService;
	@Resource
	private IContractService contractService;
	
	/**
	 * 保存
	 * DZL
	 * 17-07-19
	 */
	public Object save(DevelopMan entity) throws Exception{
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		
		//1.验证编码
		if(!FosterUtil.idCode(entity.getCode()))
			throw new SystemException("编码应为十位内的数字和字母！");
		if(entity==null)
			throw new SystemException("对象不允许为空");
		if(entity.getCompany()==null || entity.getCompany().getId()==null || "".equals(entity.getCompany().getId()))
			throw new SystemException("养殖公司不允许为空");
		if(entity.getName()==null || "".equals(entity.getName()))
			throw new SystemException("姓名不允许为空");
		else{
			sqlMap.put("company.id", "=", entity.getCompany().getId());
			sqlMap.put("name", "=", entity.getName());
			int i =selectTotalRows(sqlMap);
			sqlMap.clear();
			
			if(i>0)
				throw new SystemException("姓名不允许重复");
		}
		if(entity.getCode()==null || "".equals(entity.getCode()))
			throw new SystemException("编码不允许为空");
		else{
			sqlMap.put("company.id", "=", entity.getCompany().getId());
			sqlMap.put("code", "=", entity.getCode());
			int i =selectTotalRows(sqlMap);
			sqlMap.clear();
			
			if(i>0)
				throw new SystemException("编码不允许重复");
		}
		
		if(entity.getPhone()!=null && !"".equals(entity.getPhone()))
			if(!SmsUtil.isMobileNo(entity.getPhone()))
				throw new SystemException("电话号码错误");
			
		//保存对象
		return dao.insert(entity);
	}
	
	/**
	 * 修改开发员
	 * DZL
	 * 17-07-19
	 */
	public int updateHql(DevelopMan entity)throws Exception{
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		if(entity == null)
			throw new SystemException("对象不允许为空");
		
		//1.验证编码
		if(!FosterUtil.idCode(entity.getCode()))
			throw new SystemException("编码应为十位内的数字和字母！");
		if(entity.getCompany()==null || entity.getCompany().getId()==null || "".equals(entity.getCompany().getId()))
			throw new SystemException("养殖公司不允许为空");
		if(entity.getName()==null || "".equals(entity.getName()))
			throw new SystemException("姓名不允许为空");
		else{
			sqlMap.put("company.id", "=", entity.getCompany().getId());
			sqlMap.put("name", "=", entity.getName());
			sqlMap.put("id", "<>", entity.getId());
			int i =selectTotalRows(sqlMap);
			sqlMap.clear();
			
			if(i>0)
				throw new SystemException("姓名不允许重复");
		}
		
		if(entity.getCode()==null || "".equals(entity.getCode()))
			throw new SystemException("编码不允许为空");
		else{
			sqlMap.put("company.id", "=", entity.getCompany().getId());
			sqlMap.put("code", "=", entity.getCode());
			sqlMap.put("id", "<>", entity.getId());
			int i =selectTotalRows(sqlMap);
			sqlMap.clear();
			
			if(i>0)
				throw new SystemException("编码不允许重复");
		}
		
		if(entity.getPhone()!=null && !"".equals(entity.getPhone()))
			if(!SmsUtil.isMobileNo(entity.getPhone()))
				throw new SystemException("电话号码错误");
		
		return super.updateHql(entity);
	}
	
	/**
	 * 单个对象批量删除
	 * 功能：<br/>
	 * @author 小丁
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		for(ID id : PK){
			//删除开发员：验证开发员被合同引用
			int cnNum = contractService.selectTotalRows("developMan.id", id);
			if(cnNum>0)
				throw new SystemException("开发员正在被使用，不允许删除");
		}
		
		return dao.deleteByIds(PK);
	}
	
	/**
	 *查询 按照代养编码
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 5:57:37 PM <br/>
	 */
	public List<DevelopMan> selectAllByFarmerId(DevelopMan entity)throws Exception{
		List<DevelopMan> flist = new ArrayList<DevelopMan>();
		if(entity!=null && entity.getCompany()!=null && entity.getCompany().getId()!=null && !"".equals(entity.getCompany().getId())){
			Farmer f = farmerService.selectById(entity.getCompany().getId());
			Map<String, Object> _m = entity.getMap();
			_m.put("e.company.id", f.getCompany().getId());
			flist = super.selectAll(entity);
		}
		return flist;
	}
}
