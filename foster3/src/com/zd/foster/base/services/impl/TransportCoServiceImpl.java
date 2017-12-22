
package com.zd.foster.base.services.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.base.dao.ITransportCoDao;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.entity.TransportCo;
import com.zd.foster.base.services.IDriverService;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.base.services.ITransportCoService;
import com.zd.foster.breed.services.IPigPurchaseService;
import com.zd.foster.utils.FosterUtil;
import com.zd.foster.warehouse.services.IFeedInWareService;

/**
 * 类名：  TransportCoServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-7-19下午03:49:45
 * @version 1.0
 * 
 */
public class TransportCoServiceImpl extends BaseServiceImpl<TransportCo, ITransportCoDao> implements
		ITransportCoService {
	
	@Resource
	private IPigPurchaseService pigPurchaseService;
	@Resource
	private IFeedInWareService feedInWareService;
	@Resource
	private IDriverService driverService;
	@Resource
	private IFarmerService farmerService;
	
	
	/**
	 * 保存
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-24 下午03:33:06
	 */
	public Object save(TransportCo entity) throws Exception{
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		
		if(entity==null)
			throw new SystemException("对象不允许为空");
		if(entity.getCompany()==null || entity.getCompany().getId()==null || "".equals(entity.getCompany().getId()))
			throw new SystemException("养殖公司不允许为空");
		//1.验证编码
		if(!FosterUtil.idCode(entity.getCode()))
			throw new SystemException("编码应为十位内的数字和字母！");
		if(entity.getName()==null || "".equals(entity.getName()))
			throw new SystemException("名称不允许为空");
		else{
			sqlMap.put("company.id", "=", entity.getCompany().getId());
			sqlMap.put("name", "=", entity.getName());
			int i =selectTotalRows(sqlMap);
			sqlMap.clear();
			
			if(i>0)
				throw new SystemException("名称不允许重复");
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
	 * @author: 小丁
	 * @time:2017-7-24 下午03:34:01
	 */
	public int updateHql(TransportCo entity)throws Exception{
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		if(entity == null)
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
		
		return super.updateHql(entity);
	}
	/**
	 * 删除
	 * @Description:TODO
	 * @param <ID>
	 * @param PK
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-24 下午03:34:39
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		for(ID id : PK){
			//删除司机：验证运输公司是否被司机、猪苗登记单、饲料入库单引用
			int puNum = pigPurchaseService.selectTotalRows("transportCo.id", id);
			if(puNum>0)
				throw new SystemException("此运输公司正在被使用，不允许删除");
			
			int fiNum = feedInWareService.selectTotalRows("transportCo", id);
			if(fiNum>0)
				throw new SystemException("此运输公司正在被使用，不允许删除");
			
			int drNum = driverService.selectTotalRows("transportCo.id", id);
			if(drNum>0)
				throw new SystemException("此运输公司正在被使用，不允许删除");
			
		}
		
		return dao.deleteByIds(PK);
	}
	/**
	 * 
	 * 功能:根据代养户条件查询运输公司
	 * 重写:wxb
	 * 2017-9-6
	 * @see com.zd.foster.base.services.ITransportCoService#selectAllByFarmer(com.zd.foster.base.entity.TransportCo)
	 */
	@Override
	public List<TransportCo> selectAllByFarmer(TransportCo entity)
			throws Exception {
		Farmer f=farmerService.selectById(entity.getCode());
		String company=f.getCompany().getId();
		String hql="from TransportCo e where e.company='"+company+"'";
		return super.selectByHQL(hql);
	}
	
	
	
}
