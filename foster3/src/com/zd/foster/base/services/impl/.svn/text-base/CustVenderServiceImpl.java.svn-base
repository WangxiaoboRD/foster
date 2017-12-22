
package com.zd.foster.base.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.base.dao.ICustVenderDao;
import com.zd.foster.base.entity.CustVender;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.services.ICustVenderService;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.breed.services.IPigPurchaseService;
import com.zd.foster.sale.services.ICompanySaleService;
import com.zd.foster.utils.FosterUtil;

/**
 * 类名：  CustVenderServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-7-19下午03:36:17
 * @version 1.0
 * 
 */
public class CustVenderServiceImpl extends BaseServiceImpl<CustVender, ICustVenderDao> implements
		ICustVenderService {
	
	@Resource
	ICompanySaleService companySaleService;
	@Resource
	IPigPurchaseService pigPurchaseService;
	@Resource
	private IFarmerService farmerService;
	
	/**
	 * save
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-11 下午03:47:12
	 */
	@Override
	public Object save(CustVender entity) throws Exception {
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		//4.验证代养户养殖公司是否为空
		if(entity.getCompany()==null || entity.getCompany().getId()==null || "".equals(entity.getCompany().getId()))
			throw new SystemException("养殖公司不能为空");
		//1.验证编码
		if(!FosterUtil.idCode(entity.getCode()))
			throw new SystemException("编码应为十位内的数字和字母！");
		//2.验证CustVender编码是否重复
		sqlMap.put("company.id", "=", entity.getCompany().getId());
		sqlMap.put("code", "=", entity.getCode());
		int n =selectTotalRows(sqlMap);
		sqlMap.clear();
		
		if(n>0)
			throw new SystemException("客户编码重复！");
		//3.验证CustVender名称是否重复
		sqlMap.put("company.id", "=", entity.getCompany().getId());
		sqlMap.put("name", "=", entity.getName());
		sqlMap.put("custVenderType.dcode", "=", "DRUGDEALER");
		int i =selectTotalRows(sqlMap);
		sqlMap.clear();
		
		if(i>0)
			throw new SystemException("同一类型客户名称重复！");
		
		return super.save(entity);
	}

	@Override
	public <ID extends Serializable> int deleteByIds(ID[] PK) throws Exception {
		for(ID id : PK){
			//1.验证客户是否被养殖公司销售单、猪苗登记单引用
			int csNum = companySaleService.selectTotalRows("buyer.id", id);
			if(csNum>0)
				throw new SystemException("此客户正在被使用，不允许删除");
			
			int puNum = pigPurchaseService.selectTotalRows("pigletSupplier.id", id);
			if(puNum>0)
				throw new SystemException("此客户正在被使用，不允许删除");
		}
		
		return super.deleteByIds(PK);
	}
	/**
	 * modify
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-11 下午03:47:28
	 */
	@Override
	public int updateHql(CustVender entity) throws Exception {
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		//1.验证编码
		if(!FosterUtil.idCode(entity.getCode()))
			throw new SystemException("编码应为十位内的数字和字母！");
		//2.验证CustVender编码除自己以外是否重复
		sqlMap.put("company.id", "=", entity.getCompany().getId());
		sqlMap.put("code", "=", entity.getCode());
		sqlMap.put("id", "<>", entity.getId());
		int codeNum = super.selectTotalRows(sqlMap);
		sqlMap.clear();
		if(codeNum>0)
			throw new SystemException("客户编码重复！");
		//3.验证CustVender名称除自己以外是否重复
		sqlMap.put("company.id", "=", entity.getCompany().getId());
		sqlMap.put("name", "=", entity.getName());
		sqlMap.put("id", "<>", entity.getId());
		int nameNum = super.selectTotalRows(sqlMap);
		if(nameNum>0)
			throw new SystemException("客户名字重复！");
		super.update(entity);
		return 1;
	}

	/**
	 *查询 按照代养编码
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 5:57:37 PM <br/>
	 */
	public List<CustVender> selectAllByFarmerId(CustVender entity)throws Exception{
		List<CustVender> flist = new ArrayList<CustVender>();
		if(entity!=null && entity.getCompany()!=null && entity.getCompany().getId()!=null && !"".equals(entity.getCompany().getId())){
			Farmer f = farmerService.selectById(entity.getCompany().getId());
			Map<String, Object> _m = entity.getMap();
			_m.put("e.company.id", f.getCompany().getId());
			flist = super.selectAll(entity);
		}
		return flist;
	}

	/**
	 * 
	 * 功能:根据代养户查询
	 * 重写:wxb
	 * 2017-9-11
	 * @see com.zd.foster.base.services.ICustVenderService#selectAllByFarmer(com.zd.foster.base.entity.CustVender)
	 */
	@Override
	public List<CustVender> selectAllByFarmer(CustVender entity)
			throws Exception {
		Farmer f=farmerService.selectById(entity.getCode());
		String company=f.getCompany().getId();
		String type=null;
		if(entity.getCustVenderType()!=null && !"".equals(entity.getCustVenderType()) && entity.getCustVenderType().getDcode()!=null)
			type=entity.getCustVenderType().getDcode();
		String hql="from CustVender e where e.company='"+company+"'";
		if(type!=null)
			hql+="and e.custVenderType.dcode='"+type+"'";
		return super.selectByHQL(hql);
	}
	
}
