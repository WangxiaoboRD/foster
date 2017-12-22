
package com.zd.foster.material.services.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import java.io.Serializable;
import com.zd.epa.base.BaseServiceImpl;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.breed.services.IFeedBillDtlService;
import com.zd.foster.contract.services.IContractFeedPricedService;
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.Pager;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.material.dao.IFeedDao;
import com.zd.foster.material.entity.Feed;
import com.zd.foster.material.services.IFeedService;
import com.zd.foster.price.services.IFeedPriceDtlService;
import com.zd.foster.utils.FosterUtil;
import com.zd.foster.warehouse.services.IFeedInWareDtlService;
import com.zd.foster.warehouse.services.IFeedOutWareDtlService;
import com.zd.foster.warehouse.services.IFeedWarehouseService;

/**
 * 饲料服务鞥实现
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-23 下午03:24:11
 */
public class FeedServiceImpl extends BaseServiceImpl<Feed, IFeedDao> implements IFeedService {
	
	@Resource
	private IFeedInWareDtlService feedInWareDtlService;
	@Resource
	private IFeedOutWareDtlService feedOutWareDtlService;
	@Resource
	private IFeedWarehouseService feedWarehouseService;
	@Resource
	private IFeedPriceDtlService feedPriceDtlService;
	@Resource
	private IContractFeedPricedService contractFeedPricedService;
	@Resource
	private IFeedBillDtlService feedBillDtlService;
	
	/**
	 * 保存
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-25 下午02:19:09
	 */
	public Object save(Feed entity) throws Exception {
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		
		if(entity==null)
			throw new SystemException("对象不允许为空");
		if(entity.getCompany()==null || entity.getCompany().getId()==null || "".equals(entity.getCompany().getId()))
			throw new SystemException("养殖公司不允许为空");
		if(entity.getFeedType()==null || entity.getFeedType().getId()==null || "".equals(entity.getFeedType().getId()))
			throw new SystemException("饲料阶段不允许为空");
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
			//1.验证编码
			if(!FosterUtil.idCode(entity.getCode()))
				throw new SystemException("编码应为十位内的数字和字母！");
			
			sqlMap.put("company.id", "=", entity.getCompany().getId());
			sqlMap.put("code", "=", entity.getCode());
			int i =selectTotalRows(sqlMap);
			sqlMap.clear();
			
			if(i>0)
				throw new SystemException("编码不允许重复");
		}
		
		if(entity.getUnit()==null || entity.getUnit().getDcode()==null || "".equals(entity.getUnit().getDcode()))
			throw new SystemException("单位不允许为空");
		if(entity.getSubUnit()==null || entity.getSubUnit().getDcode()==null || "".equals(entity.getSubUnit().getDcode()))
			throw new SystemException("副单位不允许为空");
		
		if(!PapUtil.isNum(entity.getRatio()))
			throw new SystemException("换算比例必须输入数字");
		double d = Double.parseDouble(entity.getRatio());
		if(d <= 0)
			throw new SystemException("换算比例不允许为非正数");
		
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
	 * @time:2017-7-25 下午02:29:16
	 */
	public int updateHql(Feed entity)throws Exception{
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		
		if(entity.getCode()==null || "".equals(entity.getCode()))
			throw new SystemException("编码不允许为空");
		else{
			//1.验证编码
			if(!FosterUtil.idCode(entity.getCode()))
				throw new SystemException("编码应为十位内的数字和字母！");
			
			sqlMap.put("company.id", "=", entity.getCompany().getId());
			sqlMap.put("code", "=", entity.getCode());
			sqlMap.put("id", "<>", entity.getId());
			int i =selectTotalRows(sqlMap);
			sqlMap.clear();
			
			if(i>0)
				throw new SystemException("编码不允许重复");
		}
		if(entity.getUnit()==null || entity.getUnit().getDcode()==null || "".equals(entity.getUnit().getDcode()))
			throw new SystemException("单位不允许为空");
		if(entity.getSubUnit()==null || entity.getSubUnit().getDcode()==null || "".equals(entity.getSubUnit().getDcode()))
			throw new SystemException("副单位不允许为空");
		
		if(!PapUtil.isNum(entity.getRatio()))
			throw new SystemException("换算比例必须输入数字");
		double d = Double.parseDouble(entity.getRatio());
		if(d <= 0)
			throw new SystemException("换算比例不允许为非正数");
		
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
	 * @time:2017-7-25 下午02:41:38
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		
		for(ID id : PK){
			//删除技术员：验证单据上上的饲料：饲料入库单明细、饲料出库单明细、饲料仓库、饲料定价单明细、合同饲料定价、喂料单明细
			int inwn = feedInWareDtlService.selectTotalRows("feed.id", id);
			if(inwn>0)
				throw new SystemException("此饲料正在被使用，不允许删除");
			
			int outwn = feedOutWareDtlService.selectTotalRows("feed.id", id);
			if(outwn>0)
				throw new SystemException("此饲料正在被使用，不允许删除");
			
			int wn = feedWarehouseService.selectTotalRows("feed.id", id);
			if(wn>0)
				throw new SystemException("此饲料正在被使用，不允许删除");
			
			int pn = feedPriceDtlService.selectTotalRows("feed.id", id);
			if(pn>0)
				throw new SystemException("此饲料正在被使用，不允许删除");
			
			int cn = contractFeedPricedService.selectTotalRows("feed.id", id);
			if(cn>0)
				throw new SystemException("此饲料正在被使用，不允许删除");
			
			int fbn = feedBillDtlService.selectTotalRows("feed.id", id);
			if(fbn>0)
				throw new SystemException("此饲料正在被使用，不允许删除");
			
		}
		
		return dao.deleteByIds(PK);
	}
	@Resource
	private IFarmerService farmerService;
	/**
	 * 按照页面搜索条件分页查询
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 5:57:37 PM <br/>
	 */
	public List<Feed> selectAll(Feed entity)throws Exception{
		List<Feed> flist = new ArrayList<Feed>();
		if(entity == null)
			return flist;
		if(entity.getCompany()==null || entity.getCompany().getId()==null || "".equals(entity.getCompany().getId()))
			return flist;
		String code = entity.getCompany().getId();
		//获取真正的公司
		Farmer f = farmerService.selectById(code);
		//获取所有该公司的饲料
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		sqlMap.put("company.id", "=", f.getCompany().getId());
		sqlMap.put("feedClass.spareField1", "order by", "asc");
		flist = super.selectHQL(sqlMap);
		return flist;
	}
	
	
	/**
	 * 加载饲料带最近的定价
	 * @Description:TODO
	 * @param e
	 * @param pageBean
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-18 下午02:30:09
	 */
	public void selectAllAndOldPrice(Feed e, Pager<Feed> pageBean) throws Exception{
		//selectAll(e,pageBean);
		
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		if(e.getCompany()!=null && !"".equals(e.getCompany().getId()))
			sqlMap.put("company.id", "=", e.getCompany().getId());
		if(e.getName()!=null && !"".equals(e.getName()))
			sqlMap.put("name", "=", e.getName());
		if(e.getCode()!=null && !"".equals(e.getCode()))
			sqlMap.put("code", "=", e.getCode());
		if(e.getFeedType()!=null && e.getFeedType().getId()!=null && !"".equals(e.getFeedType().getId()))
			sqlMap.put("feedType.id", "=", e.getFeedType().getId());
		sqlMap.put("name", "order by", "asc");
		selectHQL(sqlMap, pageBean);
		sqlMap.clear();
		List<Feed> feeds = pageBean.getResult();
		for(Feed f : feeds){
			//
			String price = feedPriceDtlService.lastFeedPrice(f);
			f.setLastPrice(price);
		}
		
	}
	
	
}
