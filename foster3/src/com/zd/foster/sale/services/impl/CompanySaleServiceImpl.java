package com.zd.foster.sale.services.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.permission.entity.Users;
import com.zd.epa.utils.ArithUtil;
import com.zd.epa.utils.Pager;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.epa.utils.SysContainer;
import com.zd.foster.balance.services.ICompanyBalanceService;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.breed.services.IBatchService;
import com.zd.foster.contract.entity.Contract;
import com.zd.foster.sale.dao.ICompanySaleDao;
import com.zd.foster.sale.entity.CompanySale;
import com.zd.foster.sale.entity.FarmerSale;
import com.zd.foster.sale.services.ICompanySaleService;

/**
 * 类名：  CompanySaleServiceImpl
 * 功能：
 * @author DZL
 * @date 2017-7-19下午02:14:10
 * @version 1.0
 * 
 */
public class CompanySaleServiceImpl extends BaseServiceImpl<CompanySale, ICompanySaleDao> implements ICompanySaleService {
	
	@Resource
	private IBatchService batchService;
	@Resource
	private IFarmerService farmerService;
	@Resource
	private ICompanyBalanceService companyBalanceService;
	
	/**
	 * 功能:分页查询
	 * 重写:DZL
	 * 2017-7-27
	 */
	@Override
	public void selectAll(CompanySale entity, Pager<CompanySale> page) throws Exception {
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
	/**
	 * 保存公司销售单
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Mar 22, 2013 10:36:54 AM<br/>
	 * @param entity 被保存的实体
	 * @return <br/>
	 * @see com.zhongpin.pap.services.IBaseService#save(com.zhongpin.pap.entity.BaseEntity)
	 */
	public Object save(CompanySale entity) throws Exception{
		if(entity == null)
			throw new SystemException("对象不允许为空");
		if(entity.getFarmer()==null || entity.getFarmer().getId()==null || "".equals(entity.getFarmer().getId()))
			throw new SystemException("所属代养户不允许为空");
		if(entity.getBatch()==null || entity.getBatch().getId()==null || "".equals(entity.getBatch().getId()))
			throw new SystemException("养殖批次不允许为空");
		//生猪头数，重量不能够为空或为0 金额不能为空
		if(entity.getQuantity()==null || "".equals(entity.getQuantity()))
			throw new SystemException("销售头数不允许为空");
		if(entity.getWeight()==null || "".equals(entity.getWeight()))
			throw new SystemException("销售重量不允许为空");
		if(entity.getAmount()==null || "".equals(entity.getAmount()))
			throw new SystemException("销售金额不允许为空");
		if(entity.getTcost()==null || "".equals(entity.getTcost()))
			throw new SystemException("运输费不允许为空");
		if(ArithUtil.comparison(entity.getQuantity(), "0")<1)
			throw new SystemException("销售头数不允许为0或负数");
		if(ArithUtil.comparison(entity.getWeight(), "0")<1)
			throw new SystemException("销售重量不允许为0或负数");
//		if(ArithUtil.comparison(entity.getAmount(), "0")<1)
//			throw new SystemException("销售金额不允许为0或负数");
		if(ArithUtil.comparison(entity.getTcost(), "0")==-1)
			throw new SystemException("运输费用不允许为负数");
		
		//验证销售总头数必须小于再养彼此当前生猪总头数
		//1.获取批次对象
		Batch batch = batchService.selectById(entity.getBatch().getId());
		Contract c = batch.getContract();
		String code ="";
		if(c != null && c.getStatus() != null)
			code = c.getStatus().getDcode();
		if(!"BREED".equals(code) && !"LOST".equals(code))
			throw new SystemException("编号【"+batch.getBatchNumber()+"】的合同不是养殖状态或终结状态，不允许销售\n");
		
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		//验证一下 该批次生猪有没有进行公司结算
		sqlMap.put("batch.id", "=", batch.getId()+"");
		sqlMap.put("checkStatus", "=", "1");
		
		int n = companyBalanceService.selectTotalRows(sqlMap);
		sqlMap.clear();
		if(n>0)
			throw new SystemException("批次【"+batch.getBatchNumber()+"】已经进行公司结算");
		
		if(batch == null || batch.getSaleQuan()==null || !PapUtil.checkDouble(batch.getSaleQuan()))
			throw new SystemException("批次上生猪销售头数数值不对");
		if(ArithUtil.comparison(batch.getSaleQuan(), entity.getQuantity())==-1)
			throw new SystemException("该公司销售单销售头数大于该批次中生猪销售总头数");
		
		//获取公司
		Farmer farmer = farmerService.selectById(entity.getFarmer().getId());
		entity.setCompany(farmer.getCompany());
		
		//保存
		entity.setIsBalance("N");
		entity.setCheckStatus("0");
		Object obj = super.save(entity);
		return obj;
	}
	/**
	 * 修改
	 * DZL
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public int updateHql(CompanySale entity)throws Exception{
		if(entity == null)
			throw new SystemException("对象不允许为空");
		if(entity.getFarmer()==null || entity.getFarmer().getId()==null || "".equals(entity.getFarmer().getId()))
			throw new SystemException("所属代养户不允许为空");
		if(entity.getBatch()==null || entity.getBatch().getId()==null || "".equals(entity.getBatch().getId()))
			throw new SystemException("养殖批次不允许为空");
		//生猪头数，重量不能够为空或为0 金额不能为空
		if(entity.getQuantity()==null || "".equals(entity.getQuantity()))
			throw new SystemException("销售头数不允许为空");
		if(entity.getWeight()==null || "".equals(entity.getWeight()))
			throw new SystemException("销售重量不允许为空");
		if(entity.getAmount()==null || "".equals(entity.getAmount()))
			throw new SystemException("销售金额不允许为空");
		if(entity.getTcost()==null || "".equals(entity.getTcost()))
			throw new SystemException("运输费不允许为空");
		if(ArithUtil.comparison(entity.getQuantity(), "0")<1)
			throw new SystemException("销售头数不允许为0或负数");
		if(ArithUtil.comparison(entity.getWeight(), "0")<1)
			throw new SystemException("销售重量不允许为0或负数");
		if(ArithUtil.comparison(entity.getAmount(), "0")<1)
			throw new SystemException("销售金额不允许为0或负数");
		if(ArithUtil.comparison(entity.getTcost(), "0")==-1)
			throw new SystemException("运输费用不允许为负数");
		
		//验证销售总头数必须小于再养彼此当前生猪总头数
		//1.获取批次对象
		Batch batch = batchService.selectById(entity.getBatch().getId());
		Contract c = batch.getContract();
		String code ="";
		if(c != null && c.getStatus() != null)
			code = c.getStatus().getDcode();
		if(!"BREED".equals(code) && !"LOST".equals(code))
			throw new SystemException("编号【"+batch.getBatchNumber()+"】的合同不是养殖状态或终结状态，不允许销售\n");
		
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		//验证一下 该批次生猪有没有进行公司结算
		sqlMap.put("batch.id", "=", batch.getId()+"");
		sqlMap.put("checkStatus", "=", "1");
		
		int n = companyBalanceService.selectTotalRows(sqlMap);
		sqlMap.clear();
		if(n>0)
			throw new SystemException("批次【"+batch.getBatchNumber()+"】已经进行公司结算");
		
		
		if(batch == null || batch.getSaleQuan()==null || !PapUtil.checkDouble(batch.getSaleQuan()))
			throw new SystemException("批次上生猪销售头数数值不对");
		if(ArithUtil.comparison(batch.getSaleQuan(), entity.getQuantity())==-1)
			throw new SystemException("该公司销售单销售头数大于该批次中生猪销售总头数");
		
		//获取公司
		Farmer farmer = farmerService.selectById(entity.getFarmer().getId());
		entity.setCompany(farmer.getCompany());
		//修改表头
		CompanySale cs = super.selectById(entity.getId());
		cs.setBatch(batch);
		cs.setFarmer(farmer);
		cs.setCompany(farmer.getCompany());
		cs.setRegistDate(entity.getRegistDate());
		cs.setQuantity(entity.getQuantity());
		cs.setWeight(entity.getWeight());
		cs.setAmount(entity.getAmount());
		cs.setBuyer(entity.getBuyer());
		cs.setTcost(entity.getTcost());
			
		return 1;
	}
	
	/**
	 * 单个对象批量删除
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 10:12:07 AM <br/>
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		if(PK==null || PK.length==0)
			throw new SystemException("请选择删除对象");
		return super.deleteByIds(PK);
	}
	
	/**
	 * 功能：审核<br/>
	 *
	 * @author DZL 审核时 要验证该 批次的生猪还没有进行公司结算，已结算的不允许进行审核，第二 验证总的该批次已经审核过的销售单上的总头数，不能大于批次上的销售总头数
	 * @version 2015-3-9 下午05:28:16 <br/>
	 */
	public void check(String[] idArr)throws Exception{
		if(idArr == null || idArr.length==0)
			throw new SystemException("未发现要审核的单据");
		Users u = SysContainer.get();
		StringBuffer sb = new StringBuffer();
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		for(String s : idArr){
			CompanySale cs = super.selectById(s);
			Batch batch = cs.getBatch();
			if(cs == null){
				sb.append("编号为【"+s+"】的公司销售单不存在\n");
				continue;
			}else{
				Contract c = batch.getContract();
				String code ="";
				if(c != null && c.getStatus() != null)
					code = c.getStatus().getDcode();
				if(!"BREED".equals(code) && !"LOST".equals(code))
					throw new SystemException("编号【"+batch.getBatchNumber()+"】的合同不是养殖状态或终结状态，不允许审核\n");
				
				//验证一下 该批次生猪有没有进行公司结算
				sqlMap.put("batch.id", "=", batch.getId()+"");
				sqlMap.put("checkStatus", "=", "1");
				
				int n = companyBalanceService.selectTotalRows(sqlMap);
				sqlMap.clear();
				if(n>0){
					sb.append("批次【"+batch.getBatchNumber()+"】已经进行公司结算\n");
					continue;
				}
				
				//验证批次销售头数 与 所有该公司的公司销售单上的销售总头数大小
				//查询所有该批次的公司销售单 已经审核的
				sqlMap.put("batch.id", "=", batch.getId()+"");
				sqlMap.put("checkStatus", "=", "1");
				List<CompanySale> sclist = super.selectHQL(sqlMap);
				sqlMap.clear();
				String num = cs.getQuantity();
				if(num==null || !PapUtil.checkDouble(num))
					num = "0";
				if(sclist != null && !sclist.isEmpty()){
					for(CompanySale _cs : sclist){
						if(_cs != null && _cs.getQuantity() != null && PapUtil.checkDouble(_cs.getQuantity()))
							num = ArithUtil.add(num, _cs.getQuantity());
					}
				}
				//验证头数大小
				String saleNum = batch.getSaleQuan();
				if(saleNum==null || !PapUtil.checkDouble(saleNum)){
					sb.append("批次【"+batch.getBatchNumber()+"】销售头数必须位数值\n");
					continue;
				}
				if(ArithUtil.comparison(saleNum,num)==-1){
					sb.append("批次【"+batch.getBatchNumber()+"】公司销售单总销售头数大于批次上的销售头数\n");
					continue;
				}
				
				//变更状态
				//改变审核状态
				cs.setCheckStatus("1");
				cs.setCheckUser(u.getUserRealName());
				cs.setCheckDate(PapUtil.date(new Date()));
			}
		}
		if(sb.length()>0)
			throw new SystemException(sb.toString());
	}
	/**
	 * 功能：撤销<br/>
	 *
	 * @author DZL 撤销时需要验证 该批次生猪的公司结算是不是已经完成，已完成的不允许撤销
	 * @version 2015-3-9 下午05:28:16 <br/>
	 */
	public void cancelCheck(String id)throws Exception{
		if(id==null || "".equals(id))
			throw new SystemException("未发现要撤销的销售单据");
		CompanySale cs = super.selectById(id);
		if(cs == null)
			throw new SystemException("编号为【"+id+"】的销售单不存在");
		Batch batch = cs.getBatch();
		Contract c = batch.getContract();
		String code ="";
		if(c != null && c.getStatus() != null)
			code = c.getStatus().getDcode();
		if(!"BREED".equals(code) && !"LOST".equals(code))
			throw new SystemException("编号【"+batch.getBatchNumber()+"】的合同不是养殖状态或终结状态，不允许撤销\n");
		
		//验证一下 该批次生猪有没有进行公司结算
		if("Y".equals(cs.getIsBalance()))
			throw new SystemException("单据已经进行结算，不允许撤销");
		//变更状态
		//改变审核状态
		cs.setCheckStatus("0");
		cs.setCheckUser(null);
		cs.setCheckDate(null);
	}
	
	/**
	 * 自动保存 由代养销售
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Object saveAuto(CompanySale entity) throws Exception{
		if(entity == null)
			throw new SystemException("对象不允许为空");
		if(entity.getFarmer()==null || entity.getFarmer().getId()==null || "".equals(entity.getFarmer().getId()))
			throw new SystemException("所属代养户不允许为空");
		if(entity.getBatch()==null || entity.getBatch().getId()==null || "".equals(entity.getBatch().getId()))
			throw new SystemException("养殖批次不允许为空");
		//生猪头数，重量不能够为空或为0 金额不能为空
		if(entity.getQuantity()==null || "".equals(entity.getQuantity()))
			throw new SystemException("销售头数不允许为空");
		if(entity.getWeight()==null || "".equals(entity.getWeight()))
			throw new SystemException("销售重量不允许为空");
		if(entity.getAmount()==null || "".equals(entity.getAmount()))
			throw new SystemException("销售金额不允许为空");
		if(entity.getTcost()==null || "".equals(entity.getTcost()))
			throw new SystemException("运输费不允许为空");
		if(ArithUtil.comparison(entity.getQuantity(), "0")<1)
			throw new SystemException("销售头数不允许为0或负数");
		if(ArithUtil.comparison(entity.getWeight(), "0")<1)
			throw new SystemException("销售重量不允许为0或负数");
//		if(ArithUtil.comparison(entity.getAmount(), "0")<1)
//			throw new SystemException("销售金额不允许为0或负数");
		if(ArithUtil.comparison(entity.getTcost(), "0")==-1)
			throw new SystemException("运输费用不允许为负数");
		
		return super.save(entity);
	}
}
