
package com.zd.foster.base.services.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.SmsUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.base.dao.IDriverDao;
import com.zd.foster.base.entity.Driver;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.services.IDriverService;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.breed.services.IPigPurchaseService;
import com.zd.foster.utils.FosterUtil;
import com.zd.foster.warehouse.services.IFeedInWareService;

/**
 * 司机服务实现层
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-20 下午03:48:59
 */
public class DriverServiceImpl extends BaseServiceImpl<Driver, IDriverDao> implements IDriverService {
	@Resource
	private IPigPurchaseService pigPurchaseService;
	@Resource
	private IFeedInWareService feedInWareService;
	@Resource
	private IFarmerService farmerService;
	
	/**
	 * 司机保存
	 * 小丁
	 * 17-07-19
	 */
	public Object save(Driver entity) throws Exception{
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		
		if(entity==null)
			throw new SystemException("对象不允许为空");
		if(entity.getCompany()==null || entity.getCompany().getId()==null || "".equals(entity.getCompany().getId()))
			throw new SystemException("养殖公司不允许为空");
		//1.验证编码
		if(!FosterUtil.idCode(entity.getCode()))
			throw new SystemException("编码应为十位内的数字和字母！");
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
		if(entity.getTransportCo()==null || entity.getTransportCo().getId()==null || "".equals(entity.getTransportCo().getId()))
			entity.setTransportCo(null);
		//保存对象
		return dao.insert(entity);
	}
	
	/**
	 * 修改司机
	 * 小丁
	 * 17-07-19
	 */
	public int updateHql(Driver entity)throws Exception{
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
			//删除司机：验证单据上的司机是否被猪苗登记单、饲料入库单引用
			int puNum = pigPurchaseService.selectTotalRows("driver.id", id);
			if(puNum>0)
				throw new SystemException("此司机正在被使用，不允许删除");
			
			int fiNum = feedInWareService.selectTotalRows("driver", id);
			if(fiNum>0)
				throw new SystemException("此司机正在被使用，不允许删除");
		}
		
		return dao.deleteByIds(PK);
	}

	/**
	 * 
	 * 功能:根据代养户条件查询司机
	 * 重写:wxb
	 * 2017-9-6
	 * @see com.zd.foster.base.services.IDriverService#selectAllByFarmer(com.zd.foster.base.entity.Driver)
	 */
	@Override
	public List<Driver> selectAllByFarmer(Driver entity) throws Exception {
		Farmer f=farmerService.selectById(entity.getCode());
		String company=f.getCompany().getId();
		String transportCo=null;
		if(entity.getTransportCo()!=null && !"".equals(entity.getTransportCo()) && entity.getTransportCo().getId()!=null)
			transportCo=entity.getTransportCo().getId()+"";
		String hql="from Driver e where e.company='"+company+"'";
		if(transportCo!=null)
			hql+="and e.transportCo='"+transportCo+"'";
		return super.selectByHQL(hql);
	}
	
	
	
}
