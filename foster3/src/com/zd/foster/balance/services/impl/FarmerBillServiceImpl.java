
package com.zd.foster.balance.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.ArithUtil;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.epa.utils.TypeUtil;
import com.zd.foster.balance.dao.IFarmerBillDao;
import com.zd.foster.balance.entity.FarmerBill;
import com.zd.foster.balance.entity.FarmerFeedCost;
import com.zd.foster.balance.entity.FarmerRewardCost;
import com.zd.foster.balance.entity.FarmerSaleIncome;
import com.zd.foster.balance.services.ICompanyBalanceService;
import com.zd.foster.balance.services.IFarmerBillService;
import com.zd.foster.balance.services.IFarmerFeedCostService;
import com.zd.foster.balance.services.IFarmerRewardCostService;
import com.zd.foster.balance.services.IFarmerSaleIncomeService;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.sale.entity.CompanySale;

/**
 * 类名：  FarmerBillServiceImpl
 * 功能：
 * @author dzl
 * @date 2017-7-19下午02:54:38
 * @version 1.0
 * 
 */
public class FarmerBillServiceImpl extends BaseServiceImpl<FarmerBill, IFarmerBillDao> implements IFarmerBillService {

	@Resource
	private ICompanyBalanceService companyBalanceService;
	@Resource
	private IFarmerSaleIncomeService farmerSaleIncomeService;
	@Resource
	private IFarmerFeedCostService farmerFeedCostService;
	@Resource
	private IFarmerRewardCostService farmerRewardCostService;
	
	/**
	 * 修改
	 * DZL
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public int updateHql(FarmerBill entity)throws Exception{
		if(entity == null)
			throw new SystemException("对象不允许为空");
		if(entity.getAdjustMen()==null || "".equals(entity.getAdjustMen()))
			throw new SystemException("费用调整人不允许为空");
		if(entity.getAdjustReason()==null || "".equals(entity.getAdjustReason()))
			throw new SystemException("费用调整原因不允许为空");
		String adjustCost = entity.getAdjustCost();
		if(adjustCost==null || !PapUtil.checkDouble(adjustCost))
			throw new SystemException("调整费用不能为空且必须为数值类型");
		//验证一旦公司已经结算过，不允许进行调整
		FarmerBill fb = super.selectById(entity.getId());
		Batch batch = fb.getBatch();
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		sqlMap.put("batch.id", "=", batch.getId()+"");
		sqlMap.put("checkStatus", "=", "1");
		int num = companyBalanceService.selectTotalRows(sqlMap);
		sqlMap.clear();
		if(num>0)
			throw new SystemException("该账单对应的公司结算已经完成，不允许调整");
		//开始调整
		//1.先将原始数据减去 还原数据
		String oldv =  fb.getAdjustCost();
		String oldFarmerCost = fb.getFarmerCost();
		String oldBreedCost = fb.getAllBreedCost();
		if(oldv != null && PapUtil.checkDouble(oldv) && ArithUtil.comparison(oldv, "0")!=0){
			//先将数据还原
			//1-1 先将代养费 减去该费用 
			oldFarmerCost = ArithUtil.sub(oldFarmerCost, oldv);
			//1-2 养殖费加上
			oldBreedCost = ArithUtil.add(oldBreedCost, oldv);
		}
		//调整数据
		oldFarmerCost = ArithUtil.add(oldFarmerCost,adjustCost);
		oldBreedCost = ArithUtil.sub(oldBreedCost, adjustCost);
		//跟新对象
		fb.setAdjustCost(adjustCost);
		fb.setFarmerCost(oldFarmerCost);
		fb.setAllBreedCost(oldBreedCost);
		fb.setAdjustMen(entity.getAdjustMen());
		fb.setAdjustReason(entity.getAdjustReason());
		return 1;
	}
	
	/**
	 * 打印内容获取
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version 2015-11-22 下午05:52:59 <br/>
	 */
	public List<FarmerBill> print(String ids)throws Exception{
		List<FarmerBill> fbList = null;
		if(ids != null && !"".equals(ids)){
			fbList = new ArrayList<FarmerBill>();
			String[] idArray = ids.split(",");
			for(String id : idArray){
				FarmerBill fb = super.selectById(id);
				
				Batch b = fb.getBatch();
				
				List<FarmerSaleIncome> saleList = farmerSaleIncomeService.selectBySingletAll("batch.id", b.getId());
				List<FarmerFeedCost> feedList = farmerFeedCostService.selectBySingletAll("batch.id", b.getId());
				List<FarmerRewardCost> rewardList = farmerRewardCostService.selectBySingletAll("batch.id", b.getId());
				
				if(rewardList != null && !rewardList.isEmpty()){
					Iterator<FarmerRewardCost> it = rewardList.iterator();
					FarmerRewardCost fs = new FarmerRewardCost();
					String v = "0";
					while(it.hasNext()){
						FarmerRewardCost x = it.next();
					    if("FCRREWARD".equals(x.getReward().getDcode())){
					    	if(fs.getId()==null)
					    		fs = TypeUtil.copy(x, fs);
					    	v = ArithUtil.add(v, x.getMoney());
					    	it.remove();
					    }
					    fs.setMoney(v);
					}
					rewardList.add(fs);
				}
				
				fb.setSaleList(saleList);
				fb.setFeedList(feedList);
				fb.setRewardList(rewardList);
				
				String price = b.getContract().getPigletPrice();
				fb.setInPigPrice(price);
				fbList.add(fb);
			}
		}
		return fbList;
	}
}
