
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
import com.zd.foster.base.dao.ITechnicianDao;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.entity.Technician;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.base.services.ITechnicianService;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.breed.services.IBatchService;
import com.zd.foster.breed.services.IDeathBillService;
import com.zd.foster.breed.services.IPigPurchaseService;
import com.zd.foster.contract.services.IContractService;
import com.zd.foster.utils.FosterUtil;

/**
 * 类名：  TechnicianServiceImpl
 * 功能：
 * @author 小丁
 * @date 2017-7-19下午02:14:10
 * @version 1.0
 * 
 */
public class TechnicianServiceImpl extends BaseServiceImpl<Technician, ITechnicianDao> implements ITechnicianService {

	@Resource
	private IContractService contractService;
	@Resource
	private IDeathBillService deathBillService;
	@Resource
	private IPigPurchaseService pigPurchaseService;
	@Resource
	private IBatchService batchService;
	@Resource
	private IFarmerService farmerService;
	
	
	/**
	 * 技术员保存
	 * 小丁
	 * 17-07-19
	 */
	public Object save(Technician entity) throws Exception{
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
	 * 修改技术员
	 * 小丁
	 * 17-07-19
	 */
	public int updateHql(Technician entity)throws Exception{
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
			//删除技术员：验证技术员被合同、死亡单、批次、猪苗登记单引用
			int fmNum = contractService.selectTotalRows("technician.id", id);
			if(fmNum>0)
				throw new SystemException("此技术员正在被使用，不允许删除");
			
			int dhNum = deathBillService.selectTotalRows("technician.id", id);
			if(dhNum>0)
				throw new SystemException("此技术员正在被使用，不允许删除");
			
			int elNum = batchService.selectTotalRows("technician.id", id);
			if(elNum>0)
				throw new SystemException("此技术员正在被使用，不允许删除");
			
			int puNum = pigPurchaseService.selectTotalRows("technician.id", id);
			if(puNum>0)
				throw new SystemException("此技术员正在被使用，不允许删除");
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
	public List<Technician> selectAllByFarmerId(Technician entity)throws Exception{
		List<Technician> flist = new ArrayList<Technician>();
		if(entity!=null && entity.getCompany()!=null && entity.getCompany().getId()!=null && !"".equals(entity.getCompany().getId())){
			Farmer f = farmerService.selectById(entity.getCompany().getId());
			Map<String, Object> _m = entity.getMap();
			_m.put("e.company.id", f.getCompany().getId());
			flist = super.selectAll(entity);
		}
		return flist;
	}
	
	/**
	 *查询 按照batch编码
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 5:57:37 PM <br/>
	 */
	public List<Technician> selectAllByBatchId(Technician entity)throws Exception{
		List<Technician> flist = new ArrayList<Technician>();
		if(entity!=null && entity.getCompany()!=null && entity.getCompany().getId()!=null && !"".equals(entity.getCompany().getId())){
			Batch f = batchService.selectById(Integer.parseInt(entity.getCompany().getId()));
			Map<String, Object> _m = entity.getMap();
			_m.put("e.company.id", f.getCompany().getId());
			flist = super.selectAll(entity);
		}
		return flist;
	}
	
	/**
	 * 检索技术员
	 * @Description:TODO
	 * @param e
	 * @param key
	 * @return
	 * @throws Exception
	 * List<Technician>
	 * @exception:
	 * @author: 小丁
	 * @time:2017-10-12 下午01:30:39
	 */
	public List<Technician> selectByName(Technician entity, String key)throws Exception{
		SqlMap<String,String,Object> sqlMap=new SqlMap<String,String,Object>();
		List<Technician> blist=new ArrayList<Technician>();
		
		if(entity.getCompany()!=null && !"".equals(entity.getCompany().getId()))
			sqlMap.put("company.id", "=", entity.getCompany().getId());
		
		if(key == null || "".equals(key)){
			if(sqlMap==null || sqlMap.isEmpty())
				blist = super.selectAll();
			else
				blist=super.selectHQL(sqlMap);
		}else{
			sqlMap.put("name", "like",key+"%");
			blist = super.selectTopValue(sqlMap, 20);
		}
		sqlMap.clear();
		return blist;
	}
	
}
