
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
import com.zd.foster.balance.services.ICompanyOtherCostService;
import com.zd.foster.balance.services.IFarmerOtherCostService;
import com.zd.foster.price.dao.IMaterialPriceDao;
import com.zd.foster.price.entity.MaterialPrice;
import com.zd.foster.price.entity.MaterialPriceDtl;
import com.zd.foster.price.services.IMaterialPriceDtlService;
import com.zd.foster.price.services.IMaterialPriceService;

/**
 * 物料定价单服务实现层
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-31 上午10:24:13
 */
public class MaterialPriceServiceImpl extends BaseServiceImpl<MaterialPrice, IMaterialPriceDao> implements IMaterialPriceService {
	@Resource
	private IMaterialPriceDtlService materialPriceDtlService;
	@Resource
	private ICompanyOtherCostService companyOtherCostService;
	@Resource
	private IFarmerOtherCostService farmerOtherCostService;
	
	/**
	 * 物料定价单保存
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-31 上午10:26:12
	 */
	public Object save(MaterialPrice entity) throws Exception{
		if(entity==null )
			throw new SystemException("对象不能为空");
		
		List<MaterialPriceDtl> pdlist = entity.getDetails();
		if(pdlist==null || pdlist.size()==0)
			throw new SystemException("单据明细不能为空");
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("company.id", "=", entity.getCompany().getId());
		sqlMap.put("startDate", "=", entity.getStartDate());
		int n = selectTotalRows(sqlMap);
		if(n>0)
			throw new SystemException("【"+entity.getStartDate()+"】物料定价单已经存在");
		
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<pdlist.size();i++){
			MaterialPriceDtl pd = pdlist.get(i);
			
			if(pd.getPrice()==null || "".equals(pd.getPrice()))
				buff.append("第【"+(i+1)+"】行购进单价不能为空<br/>");
			else{
				if(!PapUtil.checkDouble(pd.getPrice()))
					buff.append("第【"+(i+1)+"】行购进单价数值不对<br/>");
				double d = Double.parseDouble(pd.getPrice());
				if(d < 0)
					buff.append("第【"+(i+1)+"】行购进单价不允许为负数<br/>");
			}
			
			if(pd.getSalePrice()==null || "".equals(pd.getSalePrice()))
				buff.append("第【"+(i+1)+"】行销售单价不能为空<br/>");
			else{
				if(!PapUtil.checkDouble(pd.getSalePrice()))
					buff.append("第【"+(i+1)+"】行销售单价数值不对<br/>");
				double d = Double.parseDouble(pd.getSalePrice());
				if(d < 0)
					buff.append("第【"+(i+1)+"】行销售单价不允许为负数<br/>");
			}
			
			pd.setMaterialPrice(entity);
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		
		entity.setCheckStatus("0");
		Object obj = super.save(entity);
		materialPriceDtlService.save(pdlist);
		
		return obj;
	}
	
	/**
	 * 修改药品定价单
	 * @Description:TODO
	 * @param entity
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-29 下午03:14:09
	 */
	public void update(MaterialPrice entity)throws Exception{
	
		if(entity==null )
			throw new SystemException("对象不能为空");
		
		List<MaterialPriceDtl> pdlist = entity.getDetails();
		if(pdlist==null || pdlist.size()==0)
			throw new SystemException("单据明细不能为空");
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("company.id", "=", entity.getCompany().getId());
		sqlMap.put("startDate", "=", entity.getStartDate());
		sqlMap.put("id", "<>", entity.getId());
		int n = selectTotalRows(sqlMap);
		if(n>0)
			throw new SystemException("【"+entity.getStartDate()+"】物料定价单已经存在");
		
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<pdlist.size();i++){
			MaterialPriceDtl pd = pdlist.get(i);
			
			if(pd.getPrice()==null || "".equals(pd.getPrice()))
				buff.append("第【"+(i+1)+"】行购进单价不能为空<br/>");
			else{
				if(!PapUtil.checkDouble(pd.getPrice()))
					buff.append("第【"+(i+1)+"】行购进单价数值不对<br/>");
				double d = Double.parseDouble(pd.getPrice());
				if(d < 0)
					buff.append("第【"+(i+1)+"】行购进单价不允许为负数<br/>");
			}
			
			if(pd.getSalePrice()==null || "".equals(pd.getSalePrice()))
				buff.append("第【"+(i+1)+"】行销售单价不能为空<br/>");
			else{
				if(!PapUtil.checkDouble(pd.getSalePrice()))
					buff.append("第【"+(i+1)+"】行销售单价数值不对<br/>");
				double d = Double.parseDouble(pd.getSalePrice());
				if(d < 0)
					buff.append("第【"+(i+1)+"】行销售单价不允许为负数<br/>");
			}
			
			pd.setMaterialPrice(entity);
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
	
		//修改表头
		MaterialPrice e = super.selectById(entity.getId());
		e.setStartDate(entity.getStartDate());
		e.setRemark(entity.getRemark());
		//删除的明细
		Map<String, String> _m = entity.getTempStack();
		if (null != _m && null != _m.get("deleteIds") && !"".equals(_m.get("deleteIds"))  ) {
			String[] str = _m.get("deleteIds").split(",");
			if(str != null){
				for(String id : str)
					materialPriceDtlService.deleteById(Integer.parseInt(id));
			}
		}
		// 新增和修改明细
		//1.根据明细id判断此明细是新增的还是原有的
		List<MaterialPriceDtl> updateSwd = new ArrayList<MaterialPriceDtl>(); 
		List<MaterialPriceDtl> newSwd = new ArrayList<MaterialPriceDtl>(); 
		for(MaterialPriceDtl p : pdlist){
			if(p.getId()==null){
				p.setMaterialPrice(entity);
				newSwd.add(p);
			}
			if(p.getId()!=null){
				updateSwd.add(p);
			}
		}
		//2.修改的明细
		for(MaterialPriceDtl p : updateSwd){
			MaterialPriceDtl f = materialPriceDtlService.selectById(p.getId());
			f.setPrice(p.getPrice());
			f.setSalePrice(p.getSalePrice());
		}
		updateSwd.clear();
		//3.保存新添加的明细
		materialPriceDtlService.save(newSwd);
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
	 * @time:2017-7-29 下午03:31:04
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		
		if(PK==null || PK.length==0)
			throw new SystemException("请选择删除对象");
		
		for(ID id : PK){
			//验证公司账单明细、代养账单明细
			int cn = companyOtherCostService.selectTotalRows("materialPrice.id", id);
			if(cn>0)
				throw new SystemException("此定价单正在被使用，不允许删除");
			
			int fn = farmerOtherCostService.selectTotalRows("materialPrice", id);
			if(fn>0)
				throw new SystemException("此定价单正在被使用，不允许删除");
		}
		
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("materialPrice.id", "in", PapUtil.arrayToSQLStr((String[])PK));
		materialPriceDtlService.delete(sqlMap);
		return dao.deleteByIds(PK);
	}
	
	/**
	 * 审核
	 * @Description:TODO
	 * @param idArr
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-29 下午03:42:47
	 */
	public void check(String[] idArr)throws Exception{
		if(idArr == null || idArr.length == 0)
			throw new SystemException("请选择单据");
		
		Users u = SysContainer.get();
		for(String s : idArr){
			MaterialPrice f = super.selectById(s);
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
	 * @time:2017-7-29 下午03:43:16
	 */
	public void cancelCheck(String id)throws Exception{
		if(id == null || "".equals(id))
			throw new SystemException("请选择单据");
		
		MaterialPrice f = super.selectById(id);
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
	public void selectAll(MaterialPrice entity, Pager<MaterialPrice> page) throws Exception {
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
