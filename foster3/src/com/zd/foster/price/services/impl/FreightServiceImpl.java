
package com.zd.foster.price.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.permission.entity.Users;
import com.zd.epa.utils.Pager;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.epa.utils.SysContainer;
import com.zd.foster.price.dao.IFreightDao;
import com.zd.foster.price.entity.Freight;
import com.zd.foster.price.entity.FreightDtl;
import com.zd.foster.price.services.IFreightDtlService;
import com.zd.foster.price.services.IFreightService;

/**
 * 运费定价单服务实现层
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-31 下午03:36:26
 */
public class FreightServiceImpl extends BaseServiceImpl<Freight, IFreightDao> implements IFreightService {
	@Resource
	private IFreightDtlService freightDtlService;
	
	/**
	 * 保存
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-1 上午10:31:22
	 */
	public Object save(Freight entity) throws Exception{
		if(entity==null )
			throw new SystemException("对象不能为空");
		
		List<FreightDtl> pdlist = entity.getDetails();
		if(pdlist==null || pdlist.size()==0)
			throw new SystemException("单据明细不能为空");
		
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("company.id", "=", entity.getCompany().getId());
		sqlMap.put("registDate", "=", entity.getRegistDate());
		int n = selectTotalRows(sqlMap);
		sqlMap.clear();
		if(n>0)
			throw new SystemException("【"+entity.getRegistDate()+"】运费定价单已经存在");
		
		StringBuffer buff = new StringBuffer();
		Set<Integer> set = new HashSet<Integer>();
		for(int i=0;i<pdlist.size();i++){
			FreightDtl pd = pdlist.get(i);
			//饲料厂不能为空
			if(pd.getFeedFac()==null || pd.getFeedFac().getId()==null || "".equals(pd.getFeedFac().getId()))
				buff.append("第【"+(i+1)+"】行饲料厂不能为空<br/>");
			//俩单价不能为负数
			if(pd.getPackagePrice()==null || "".equals(pd.getPackagePrice()))
				buff.append("第【"+(i+1)+"】行袋装料单价不能为空<br/>");
			else{
				if(!PapUtil.checkDouble(pd.getPackagePrice()))
					buff.append("第【"+(i+1)+"】行袋装料单价数值不对<br/>");
				double d = Double.parseDouble(pd.getPackagePrice());
				if(d < 0)
					buff.append("第【"+(i+1)+"】行袋装料单价不允许为负数<br/>");
			}
			if(pd.getBulkPrice()==null || "".equals(pd.getBulkPrice()))
				buff.append("第【"+(i+1)+"】行散装料单价不能为空<br/>");
			else{
				if(!PapUtil.checkDouble(pd.getBulkPrice()))
					buff.append("第【"+(i+1)+"】行散装料单价数值不对<br/>");
				double d = Double.parseDouble(pd.getBulkPrice());
				if(d < 0)
					buff.append("第【"+(i+1)+"】行散装料单价不允许为负数<br/>");
			}
			//验证同一代养户同一饲料厂不能有两条
			if(pd.getFeedFac()!=null&&pd.getFeedFac().getId()!=null&&!"".equals(pd.getFeedFac().getId())){
				for(int j=(i+1);j<pdlist.size();j++){
					FreightDtl pj = pdlist.get(j);
					if(pd.getFarmer().getId().equals(pj.getFarmer().getId())){
						if(pd.getFeedFac().getId().equals(pj.getFeedFac().getId()))
							set.add(j+1);
					}
				}
			}
			
			pd.setFreight(entity);
		}
		if(set.size()>0)
			buff.append("第"+set.toString()+"行明细重复<br/>");
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		
		if(entity.getCheckStatus()==null || "".equals(entity.getCheckStatus()))
			entity.setCheckStatus("0");
		Object obj = super.save(entity);
		freightDtlService.save(pdlist);
		
		return obj;
	}
	
	/**
	 * 修改
	 * @Description:TODO
	 * @param entity
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-1 上午11:05:15
	 */
	public void update(Freight entity)throws Exception{
	
		if(entity==null )
			throw new SystemException("对象不能为空");
		
		List<FreightDtl> pdlist = entity.getDetails();
		if(pdlist==null || pdlist.size()==0)
			throw new SystemException("单据明细不能为空");
		
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("company.id", "=", entity.getCompany().getId());
		sqlMap.put("registDate", "=", entity.getRegistDate());
		sqlMap.put("id", "<>", entity.getId());
		int n = selectTotalRows(sqlMap);
		if(n>0)
			throw new SystemException("【"+entity.getRegistDate()+"】运费定价单已经存在");
		
		StringBuffer buff = new StringBuffer();
		Set<Integer> set = new HashSet<Integer>();
		for(int i=0;i<pdlist.size();i++){
			FreightDtl pd = pdlist.get(i);
			
			//饲料厂不能为空
			if(pd.getFeedFac()==null || pd.getFeedFac().getId()==null || "".equals(pd.getFeedFac().getId()))
				buff.append("第【"+(i+1)+"】行饲料厂不能为空<br/>");
			//俩单价不能为负数
			if(pd.getPackagePrice()==null || "".equals(pd.getPackagePrice()))
				buff.append("第【"+(i+1)+"】行袋装料单价不能为空<br/>");
			else{
				if(!PapUtil.checkDouble(pd.getPackagePrice()))
					buff.append("第【"+(i+1)+"】行袋装料单价数值不对<br/>");
				double d = Double.parseDouble(pd.getPackagePrice());
				if(d < 0)
					buff.append("第【"+(i+1)+"】行袋装料单价不允许为负数<br/>");
			}
			if(pd.getBulkPrice()==null || "".equals(pd.getBulkPrice()))
				buff.append("第【"+(i+1)+"】行散装料单价不能为空<br/>");
			else{
				if(!PapUtil.checkDouble(pd.getBulkPrice()))
					buff.append("第【"+(i+1)+"】行散装料单价数值不对<br/>");
				double d = Double.parseDouble(pd.getBulkPrice());
				if(d < 0)
					buff.append("第【"+(i+1)+"】行散装料单价不允许为负数<br/>");
			}
			//验证同一代养户同一饲料厂不能有两条
			if(pd.getFeedFac()!=null&&pd.getFeedFac().getId()!=null&&!"".equals(pd.getFeedFac().getId())){
				for(int j=(i+1);j<pdlist.size();j++){
					FreightDtl pj = pdlist.get(j);
					if(pd.getFarmer().getId().equals(pj.getFarmer().getId())){
						if(pd.getFeedFac().getId().equals(pj.getFeedFac().getId()))
							set.add(j+1);
					}
				}
			}
			
			pd.setFreight(entity);
		}
		if(set.size()>0)
			buff.append("第"+set.toString()+"行明细重复<br/>");
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
	
		//修改表头
		Freight e = super.selectById(entity.getId());
		e.setRegistDate(entity.getRegistDate());
		e.setRemark(entity.getRemark());
		//删除的明细
		Map<String, String> _m = entity.getTempStack();
		if (null != _m && null != _m.get("deleteIds") && !"".equals(_m.get("deleteIds"))  ) {
			String[] str = _m.get("deleteIds").split(",");
			if(str != null){
				for(String id : str)
					freightDtlService.deleteById(Integer.parseInt(id));
			}
		}
		// 新增和修改明细
		//1.根据明细id判断此明细是新增的还是原有的
		List<FreightDtl> updateSwd = new ArrayList<FreightDtl>(); 
		List<FreightDtl> newSwd = new ArrayList<FreightDtl>(); 
		for(FreightDtl p : pdlist){
			if(p.getId()==null){
				p.setFreight(entity);
				newSwd.add(p);
			}
			if(p.getId()!=null){
				updateSwd.add(p);
			}
		}
		//2.修改的明细
		for(FreightDtl p : updateSwd){
			FreightDtl f = freightDtlService.selectById(p.getId());
			f.setFeedFac(p.getFeedFac());
			f.setTipscontent(p.getTipscontent());
			f.setPackagePrice(p.getPackagePrice());
			f.setBulkPrice(p.getBulkPrice());
		}
		updateSwd.clear();
		//3.保存新添加的明细
		freightDtlService.save(newSwd);
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
	 * @time:2017-8-1 上午11:07:49
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		
		if(PK==null || PK.length==0)
			throw new SystemException("请选择删除对象");
		
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("freight.id", "in", PapUtil.arrayToSQLStr((String[])PK));
		freightDtlService.delete(sqlMap);
		return dao.deleteByIds(PK);
	}
	
	/**
	 * 审核
	 * @Description:TODO
	 * @param idArr
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-1 上午11:12:48
	 */
	public void check(String[] idArr)throws Exception{
		if(idArr == null || idArr.length == 0)
			throw new SystemException("请选择单据");
		
		Users u = SysContainer.get();
		for(String s : idArr){
			Freight f = super.selectById(s);
			//改变喂料单的审核状态
			f.setCheckStatus("1");
			f.setCheckUser(u.getUserRealName());
			f.setCheckDate(PapUtil.date(new Date()));
		}
	}
	
	/**
	 * 撤销
	 * @Description:TODO
	 * @param id
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-1 上午11:13:15
	 */
	public void cancelCheck(String id)throws Exception{
		if(id == null || "".equals(id))
			throw new SystemException("请选择单据");
		
		Freight f = super.selectById(id);
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
	 * @time:2017-8-18 上午09:50:23
	 */
	@Override
	public void selectAll(Freight entity, Pager<Freight> page) throws Exception {
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		Map<String, String> ts = entity.getTempStack();
		if (null != ts) {
			//开始时间 
			String startDate = ts.get("startTime");
			if (null != startDate && !"".equals(startDate)) {
				sqlMap.put("registDate", ">=", startDate);
			}
			//结束时间
			String endDate = ts.get("endTime");
			if (null != endDate && !"".equals(endDate)) {
				sqlMap.put("registDate", "<=", endDate);
			}
		}
		dao.selectByConditionHQL(entity, sqlMap, page);
	}
	
	
	
	
	
	
	
	
	
	
	
}
