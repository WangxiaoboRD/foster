package com.zd.foster.contract.services.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.bussobj.entity.BussinessEleDetail;
import com.zd.epa.bussobj.services.IBussinessEleDetailService;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.breed.services.IBatchService;
import com.zd.foster.contract.dao.IContractPigPricedDao;
import com.zd.foster.contract.entity.Contract;
import com.zd.foster.contract.entity.ContractPigPriced;
import com.zd.foster.contract.services.IContractPigPricedService;

/**
 * 类名：  ContractPigPricedServiceImpl
 * 功能：
 * @author DZL
 * @date 2017-7-19下午02:14:10
 * @version 1.0
 * 
 */
public class ContractPigPricedServiceImpl extends BaseServiceImpl<ContractPigPriced, IContractPigPricedDao> implements IContractPigPricedService{

	@Resource
	private IBussinessEleDetailService bussinessEleDetailService;
	@Resource
	private IBatchService batchService;
	
	/**
	 * 按照页面搜索条件分页查询
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 5:57:37 PM <br/>
	 */
	public List<ContractPigPriced> selectAllByBatch(ContractPigPriced entity)throws Exception{
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		//获取合同编号
		Contract b = entity.getContract();
		if(b != null){
			Batch batch = batchService.selectById(Integer.parseInt(b.getId()));
			Contract c = batch.getContract();
			sqlMap.put("contract.id", "=", c.getId());
			sqlMap.put("pigLevel.dcode", "order by", "asc");
			List<ContractPigPriced> cpl = super.selectHQL(sqlMap);
			return cpl;
		}
		return null;
	}
	
	/**
	 * 按照页面搜索条件分页查询
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 5:57:37 PM <br/>
	 */
	public List<ContractPigPriced> selectAll(ContractPigPriced entity)throws Exception{
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		sqlMap.put("pigLevel.dcode", "order by", "asc");
		List<ContractPigPriced> cpl = super.selectHQL(entity,sqlMap);
		return cpl;
	}
	
	/**
	 * 获得销售级别
	 * 功能：<br/>
	 * @author DZL
	 * @version 2015-3-14 下午03:33:38 <br/>
	 * @throws Exception 
	 */
	public List<ContractPigPriced> loadDetails(ContractPigPriced entity) throws Exception{
		
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		sqlMap.put("bussinessEle.ecode", "=", "SALELEVEL");
		sqlMap.put("dcode", "order by", "asc");
		List<BussinessEleDetail> blist = bussinessEleDetailService.selectHQL(sqlMap);
		List<ContractPigPriced> plist = new ArrayList<ContractPigPriced>();
		for(BussinessEleDetail b:blist){
			ContractPigPriced cped = new ContractPigPriced();
			cped.setPigLevel(b);
			plist.add(cped);
		}
		return plist;
	}
}
