
package com.zd.foster.price.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.permission.entity.Users;
import com.zd.epa.utils.Pager;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.epa.utils.SysContainer;
import com.zd.foster.balance.services.ICompanyFeedCostService;
import com.zd.foster.price.dao.IFeedPriceDao;
import com.zd.foster.price.entity.FeedPrice;
import com.zd.foster.price.entity.FeedPriceDtl;
import com.zd.foster.price.services.IFeedPriceDtlService;
import com.zd.foster.price.services.IFeedPriceService;

/**
 * 饲料定价单服务实现层
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-27 上午10:41:16
 */
public class FeedPriceServiceImpl extends BaseServiceImpl<FeedPrice, IFeedPriceDao> implements IFeedPriceService {
	@Resource
	private IFeedPriceDtlService feedPriceDtlService;
	@Resource
	private ICompanyFeedCostService companyFeedCostService;
	
	/**
	 * 保存饲料定价单
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-28 上午09:36:55
	 */
	public Object save(FeedPrice entity) throws Exception{
		if(entity==null )
			throw new SystemException("对象不能为空");
		
		List<FeedPriceDtl> pdlist = entity.getDetails();
		if(pdlist==null || pdlist.size()==0)
			throw new SystemException("单据明细不能为空");
		if(entity.getFeedFac()==null || entity.getFeedFac().getId()==null || "".equals(entity.getFeedFac().getId()))
			throw new SystemException("饲料厂不能为空");
		
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("company.id", "=", entity.getCompany().getId());
		sqlMap.put("feedFac.id", "=", entity.getFeedFac().getId());
		sqlMap.put("startDate", "=", entity.getStartDate());
		int n = selectTotalRows(sqlMap);
		if(n>0)
			throw new SystemException("【"+entity.getStartDate()+"】饲料定价单已经存在");
		
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<pdlist.size();i++){
			FeedPriceDtl pd = pdlist.get(i);
			
			if(pd.getPrice()==null || "".equals(pd.getPrice()))
				buff.append("第【"+(i+1)+"】行单价不能为空<br/>");
			else{
				if(!PapUtil.checkDouble(pd.getPrice()))
					buff.append("第【"+(i+1)+"】行单价数值不对<br/>");
				double d = Double.parseDouble(pd.getPrice());
				if(d < 0)
					buff.append("第【"+(i+1)+"】行单价不允许为负数<br/>");
			}
			
			pd.setFeedPrice(entity);
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		
		if(entity.getCheckStatus()==null || "".equals(entity.getCheckStatus()))
			entity.setCheckStatus("0");
		Object obj = super.save(entity);
		feedPriceDtlService.save(pdlist);
		
		return obj;
	}
	
	/**
	 * 修改饲料定价单
	 * @Description:TODO
	 * @param entity
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-28 上午11:15:46
	 */
	public void update(FeedPrice entity)throws Exception{
		if(entity==null )
			throw new SystemException("对象不能为空");
		
		List<FeedPriceDtl> pdlist = entity.getDetails();
		if(pdlist==null || pdlist.size()==0)
			throw new SystemException("单据明细不能为空");
		if(entity.getFeedFac()==null || entity.getFeedFac().getId()==null || "".equals(entity.getFeedFac().getId()))
			throw new SystemException("饲料厂不能为空");
		
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("company.id", "=", entity.getCompany().getId());
		sqlMap.put("feedFac.id", "=", entity.getFeedFac().getId());
		sqlMap.put("startDate", "=", entity.getStartDate());
		sqlMap.put("id", "<>", entity.getId());
		int n = selectTotalRows(sqlMap);
		if(n>0)
			throw new SystemException("【"+entity.getStartDate()+"】饲料定价单已经存在");
		
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<pdlist.size();i++){
			FeedPriceDtl pd = pdlist.get(i);
			
			if(pd.getPrice()==null || "".equals(pd.getPrice()))
				buff.append("第【"+(i+1)+"】行单价不能为空<br/>");
			else{
				if(!PapUtil.checkDouble(pd.getPrice()))
					buff.append("第【"+(i+1)+"】行单价数值不对<br/>");
				double d = Double.parseDouble(pd.getPrice());
				if(d < 0)
					buff.append("第【"+(i+1)+"】行单价不允许为负数<br/>");
			}
			
			pd.setFeedPrice(entity);
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		
		//修改表头
		FeedPrice e = super.selectById(entity.getId());
		e.setFeedFac(entity.getFeedFac());
		e.setStartDate(entity.getStartDate());
		e.setRemark(entity.getRemark());
		
		//删除的明细
		Map<String, String> _m = entity.getTempStack();
		if (null != _m && null != _m.get("deleteIds") && !"".equals(_m.get("deleteIds"))  ) {
			String[] str = _m.get("deleteIds").split(",");
			if(str != null){
				for(String id : str)
					feedPriceDtlService.deleteById(Integer.parseInt(id));
			}
		}
		// 新增和修改明细
		//1.根据明细id判断此明细是新增的还是原有的
		List<FeedPriceDtl> updateSwd = new ArrayList<FeedPriceDtl>(); 
		List<FeedPriceDtl> newSwd = new ArrayList<FeedPriceDtl>(); 
		for(FeedPriceDtl p : pdlist){
			if(p.getId()==null){
				p.setFeedPrice(entity);
				newSwd.add(p);
			}
			if(p.getId()!=null){
				updateSwd.add(p);
			}
		}
		//2.修改的明细
		for(FeedPriceDtl p : updateSwd){
			FeedPriceDtl f = feedPriceDtlService.selectById(p.getId());
			f.setFeed(p.getFeed());
			f.setPrice(p.getPrice());
		}
		updateSwd.clear();
		//3.保存新添加的明细
		feedPriceDtlService.save(newSwd);
	}
	
	/**
	 * 饲料定价单删除
	 * @Description:TODO
	 * @param <ID>
	 * @param PK
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-28 上午11:31:58
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		
		if(PK==null || PK.length==0)
			throw new SystemException("请选择删除对象");
		
		for(ID id : PK){
			//验证公司账单饲料明细
			int fn = companyFeedCostService.selectTotalRows("feedPrice.id", id);
			if(fn>0)
				throw new SystemException("此定价单正在被使用，不允许删除");
		}
		
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("feedPrice.id", "in", PapUtil.arrayToSQLStr((String[])PK));
		feedPriceDtlService.delete(sqlMap);
		return dao.deleteByIds(PK);
	}
	
	
	/**
	 * 饲料定价单审核
	 * @Description:TODO
	 * @param idArr
	 * @throws Exception
	 * void
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-28 上午11:38:38
	 */
	public void check(String[] idArr)throws Exception{
		if(idArr == null || idArr.length == 0)
			throw new SystemException("请选择单据");
		
		Users u = SysContainer.get();
		for(String s : idArr){
			FeedPrice f = super.selectById(s);
			//改变喂料单的审核状态
			f.setCheckStatus("1");
			f.setCheckUser(u.getUserRealName());
			f.setCheckDate(PapUtil.date(new Date()));
		}
	}
	
	/**
	 * 饲料定价单撤销
	 * @Description:TODO
	 * @param id
	 * @throws Exception
	 * void
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-28 上午11:41:42
	 */
	public void cancelCheck(String id)throws Exception{
		if(id == null || "".equals(id))
			throw new SystemException("请选择单据");
		
		FeedPrice f = super.selectById(id);
		//改变喂料单的审核状态
		f.setCheckStatus("0");
		f.setCheckUser("");
		f.setCheckDate("");
	}
	
	/**
	 * 查询
	 * @Description:TODO
	 * @param entity
	 * @param page
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-11 下午05:03:36
	 */
	@Override
	public void selectAll(FeedPrice entity, Pager<FeedPrice> page) throws Exception {
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		Map<String, String> ts = entity.getTempStack();
		if (null != ts) {
			//开始时间 
			String startDate = ts.get("startTime");
			if (null != startDate && !"".equals(startDate)) {
				sqlMap.put("startDate", ">=", startDate);
			}
			//结束时间
			String endDate = ts.get("endTime");
			if (null != endDate && !"".equals(endDate)) {
				sqlMap.put("startDate", "<=", endDate);
			}
		}
		dao.selectByConditionHQL(entity, sqlMap, page);
	}
	
	
	
	
	
	
	
	
}
