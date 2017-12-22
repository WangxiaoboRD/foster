
package com.zd.foster.base.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.base.dao.IFeedFacDao;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.entity.FeedFac;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.base.services.IFeedFacService;
import com.zd.foster.contract.services.IContractService;
import com.zd.foster.price.services.IFeedPriceService;
import com.zd.foster.utils.FosterUtil;

/**
 * 类名：  FeedFacServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-7-19下午03:10:37
 * @version 1.0
 * 
 */
public class FeedFacServiceImpl extends BaseServiceImpl<FeedFac, IFeedFacDao> implements
		IFeedFacService {
	
	@Resource
	private IContractService contractService;
	@Resource
	private IFeedPriceService feedPriceService;
	@Resource
	private IFarmerService farmerService;
	
	/**
	 * 新增
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-11 下午03:44:33
	 */
	@Override
	public Object save(FeedFac entity) throws Exception {
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		//4.验证养殖公司是否为空
		if(entity.getCompany()==null || entity.getCompany().getId()==null || "".equals(entity.getCompany().getId()))
			throw new SystemException("养殖公司不能为空！");
		//1.验证编码
		if(!FosterUtil.idCode(entity.getCode()))
			throw new SystemException("编码应为十位内的数字和字母！");
		//2.验证FeedFac编码是否重复
		sqlMap.put("company.id", "=", entity.getCompany().getId());
		sqlMap.put("code", "=", entity.getCode());
		int n =selectTotalRows(sqlMap);
		sqlMap.clear();
		
		if(n>0)
			throw new SystemException("饲料厂编码重复！");
		//3.验证FeedFac名称是否重复
		sqlMap.put("company.id", "=", entity.getCompany().getId());
		sqlMap.put("name", "=", entity.getName());
		int i =selectTotalRows(sqlMap);
		sqlMap.clear();
		
		if(i>0)
			throw new SystemException("饲料厂名称重复！");
		
		return super.save(entity);
	}

	@Override
	public <ID extends Serializable> int deleteByIds(ID[] PK) throws Exception {
		for(ID id : PK){
			//1.验证饲料厂是否被合同、饲料定价单引用
			int cnNum = contractService.selectTotalRows("feedFac.id", id);
			if(cnNum>0)
				throw new SystemException("此饲料厂正在被使用，不允许删除");
			
			int fpNum = feedPriceService.selectTotalRows("feedFac.id", id);
			if(fpNum>0)
				throw new SystemException("此饲料厂正在被使用，不允许删除");
		}
		
		return super.deleteByIds(PK);
	}
	/**
	 * 修改
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-11 下午03:44:51
	 */
	@Override
	public int updateHql(FeedFac entity) throws Exception {
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		
		//4.验证养殖公司是否为空
		if(entity.getCompany()==null || entity.getCompany().getId()==null || "".equals(entity.getCompany().getId()))
			throw new SystemException("养殖公司不能为空！");
		//1.验证编码
		if(!FosterUtil.idCode(entity.getCode()))
			throw new SystemException("编码应为十位内的数字和字母！");
		//2.验证FeedFac编码除自己以外是否重复
		sqlMap.put("company.id", "=", entity.getCompany().getId());
		sqlMap.put("code", "=", entity.getCode());
		sqlMap.put("id", "<>", entity.getId());
		int codeNum = super.selectTotalRows(sqlMap);
		sqlMap.clear();
		if(codeNum>0)
			throw new SystemException("饲料厂编码重复！");
		//3.验证FeedFac名称除自己以外是否重复
		sqlMap.put("company.id", "=", entity.getCompany().getId());
		sqlMap.put("name", "=", entity.getName());
		sqlMap.put("id", "<>", entity.getId());
		int nameNum = super.selectTotalRows(sqlMap);
		if(nameNum>0)
			throw new SystemException("饲料厂名称重复！");
		
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
	public List<FeedFac> selectAllByFarmerId(FeedFac entity)throws Exception{
		List<FeedFac> flist = new ArrayList<FeedFac>();
		if(entity!=null && entity.getCompany()!=null && entity.getCompany().getId()!=null && !"".equals(entity.getCompany().getId())){
			Farmer f = farmerService.selectById(entity.getCompany().getId());
			Map<String, Object> _m = entity.getMap();
			_m.put("e.company.id", f.getCompany().getId());
			flist = super.selectAll(entity);
		}
		return flist;
	}

}
