package com.zd.foster.contract.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.bussobj.entity.BussinessEleDetail;
import com.zd.epa.exception.SystemException;
import com.zd.epa.permission.entity.Users;
import com.zd.epa.sysconfig.entity.SysParam;
import com.zd.epa.sysconfig.utils.SysConfigContext;
import com.zd.epa.utils.ArithUtil;
import com.zd.epa.utils.ExcelUtil;
import com.zd.epa.utils.Pager;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.epa.utils.SysContainer;
import com.zd.foster.balance.services.ICompanyBalanceService;
import com.zd.foster.balance.services.IFarmerBalanceService;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.DevelopMan;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.entity.FeedFac;
import com.zd.foster.base.entity.TechBatch;
import com.zd.foster.base.entity.Technician;
import com.zd.foster.base.entity.Variety;
import com.zd.foster.base.services.IDevelopManService;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.base.services.IFeedFacService;
import com.zd.foster.base.services.ITechBatchService;
import com.zd.foster.base.services.ITechnicianService;
import com.zd.foster.base.services.IVarietyService;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.breed.services.IBatchService;
import com.zd.foster.breed.services.IDeathBillService;
import com.zd.foster.breed.services.IFeedBillService;
import com.zd.foster.breed.services.IMaterialBillService;
import com.zd.foster.breed.services.IMedicalBillService;
import com.zd.foster.breed.services.IPigPurchaseService;
import com.zd.foster.contract.dao.IContractDao;
import com.zd.foster.contract.entity.Contract;
import com.zd.foster.contract.entity.ContractFeedPriced;
import com.zd.foster.contract.entity.ContractPigPriced;
import com.zd.foster.contract.services.IContractFeedPricedService;
import com.zd.foster.contract.services.IContractPigPricedService;
import com.zd.foster.contract.services.IContractService;
import com.zd.foster.material.entity.Feed;
import com.zd.foster.material.services.IFeedService;
import com.zd.foster.sale.entity.FarmerSale;
import com.zd.foster.sale.services.IFarmerSaleService;
import com.zd.foster.sale.services.IOutPigstyService;
import com.zd.foster.utils.FosterUtil;
import com.zd.foster.warehouse.entity.DrugWarehouseFar;
import com.zd.foster.warehouse.entity.FeedWarehouse;
import com.zd.foster.warehouse.services.IDrugBillService;
import com.zd.foster.warehouse.services.IDrugWarehouseFarService;
import com.zd.foster.warehouse.services.IFeedInWareService;
import com.zd.foster.warehouse.services.IFeedWarehouseService;

/**
 * 类名：  ContractServiceImpl
 * 功能：
 * @author DZL
 * @date 2017-7-19下午02:14:10
 * @version 1.0
 * 
 */
public class ContractServiceImpl extends BaseServiceImpl<Contract, IContractDao> implements IContractService {

	@Resource
	private IContractFeedPricedService contractFeedPricedService;
	@Resource
	private IContractPigPricedService contractPigPricedService;
	@Resource
	private IFarmerService farmerService;
	@Resource
	private IBatchService batchService;
	@Resource
	private IFeedWarehouseService feedWarehouseService;
	@Resource
	private IFeedBillService feedBillService;
	@Resource
	private IMaterialBillService materialBillService;
	@Resource
	private IMedicalBillService medicalBillService;
	@Resource
	private IPigPurchaseService pigPurchaseService;
	@Resource
	private IFeedInWareService feedInWareService;
	@Resource
	private IDeathBillService deathBillService;
	//@Resource
	//private IEliminateBillService eliminateBillService;
	@Resource
	private IFarmerSaleService farmerSaleService;
	@Resource
	private IOutPigstyService outPigstyService;
	@Resource
	private ICompanyBalanceService companyBalanceService;
	@Resource
	private IFarmerBalanceService farmerBalanceService;
	@Resource
	private IFeedFacService feedFacService;
	@Resource
	private IVarietyService varietyService;
	@Resource
	private IFeedService feedService;
	@Resource
	private IDrugBillService drugBillService;
	@Resource
	private IDrugWarehouseFarService drugWarehouseFarService;
	@Resource
	private IDevelopManService developManService;
	@Resource
	private ITechnicianService technicianService;
	@Resource
	private ITechBatchService techBatchService;
	
	/**
	 * 功能:分页查询
	 * 重写:DZL
	 * 2017-7-27
	 */
	@Override
	public void selectAll(Contract entity, Pager<Contract> page) throws Exception {
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
		
		List<Contract> clist = page.getResult();
		for(Contract c : clist){
			if(c.getBatch() != null && !"".equals(c.getBatch())){
				Batch b = batchService.selectById(Integer.parseInt(c.getBatch()));
				if(b != null)
					c.setBatchNumber(b.getBatchNumber());
			}
		}
	}
	
	/**
	 * 保存合同
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Mar 22, 2013 10:36:54 AM<br/>
	 * @param entity 被保存的实体
	 * @return <br/>
	 * @see com.zhongpin.pap.services.IBaseService#save(com.zhongpin.pap.entity.BaseEntity)
	 */
	public Object save(Contract entity) throws Exception{
		if(entity == null)
			throw new SystemException("对象不允许为空");
		if(entity.getCode()==null || "".equals(entity.getCode()))
			throw new SystemException("合同编码不允许为空");
		if(!FosterUtil.idCode(entity.getCode()))
			throw new SystemException("编码应为十位内的数字和字母！");
		if(entity.getVariety()==null || entity.getVariety().getId()==null || "".equals(entity.getVariety().getId()))
			throw new SystemException("养殖品种不允许为空");
		if(entity.getFarmer()==null || entity.getFarmer().getId()==null || "".equals(entity.getFarmer().getId()))
			throw new SystemException("所属代养户不允许为空");
		if(entity.getFeedFac()==null || entity.getFeedFac().getId()==null || "".equals(entity.getFeedFac().getId()))
			throw new SystemException("绑定饲料厂不允许为空");
		if(entity.getPigletQuan()==null || "".equals(entity.getPigletQuan()))
			throw new SystemException("养殖头数不能为空");
		if(entity.getPigletPrice()==null || "".equals(entity.getPigletPrice()))
			throw new SystemException("猪苗单价不能为空");
		if(entity.getTechnician()==null || entity.getTechnician().getId()==null ||  "".equals(entity.getTechnician().getId()))
			throw new SystemException("技术员不能为空");
		if(entity.getDevelopMan()==null || entity.getDevelopMan().getId()==null || "".equals(entity.getDevelopMan().getId()))
			throw new SystemException("开发员不能为空");
		
		//验证数量
		if(!PapUtil.checkDouble(entity.getPigletQuan()) || ArithUtil.comparison(entity.getPigletQuan(), "0")<1)
			throw new SystemException("养殖头数必须为数值且必须大于0");
		if(!PapUtil.checkDouble(entity.getPigletPrice()) || ArithUtil.comparison(entity.getPigletPrice(), "0")<1)
			throw new SystemException("猪苗单价必须为数值且必须大于0");
		
		if(entity.getStandPigletWeight() !=null && !"".equals(entity.getStandPigletWeight())){
			if(!PapUtil.checkDouble(entity.getStandPigletWeight()))
				throw new SystemException("猪苗标重必须为数值");
			if(ArithUtil.comparison(entity.getStandPigletWeight(),"0")==-1)
				throw new SystemException("猪苗标重不允许为负数");
		}
		if(entity.getStandSaleWeight() !=null && !"".equals(entity.getStandSaleWeight())){
			if(!PapUtil.checkDouble(entity.getStandSaleWeight()))
				throw new SystemException("出栏标重必须为数值");
			if(ArithUtil.comparison(entity.getStandSaleWeight(),"0")==-1)
				throw new SystemException("出栏标重不允许为负数");
		}
		if(entity.getMarketPrice() !=null && !"".equals(entity.getMarketPrice())){
			if(!PapUtil.checkDouble(entity.getMarketPrice()))
				throw new SystemException("市场销售价必须为数值");
			if(ArithUtil.comparison(entity.getMarketPrice(),"0")==-1)
				throw new SystemException("市场销售价不允许为负数");
		}
		if(entity.getAllowDiff() !=null && !"".equals(entity.getAllowDiff())){
			if(!PapUtil.checkDouble(entity.getAllowDiff()))
				throw new SystemException("允许偏差值必须输入数字");
			if(ArithUtil.comparison(entity.getAllowDiff(),"0")==-1)
				throw new SystemException("允许偏差值不允许为负数");
		}
		if(entity.getFirstDiff()!=null && !"".equals(entity.getFirstDiff())){
			if(!PapUtil.checkDouble(entity.getFirstDiff()))
				throw new SystemException("一级偏差值必须输入数字");
			if(ArithUtil.comparison(entity.getFirstDiff(),"0")==-1)
				throw new SystemException("一级偏差值不允许为负数");
		}
		if(entity.getFirstPrice()!=null && !"".equals(entity.getFirstPrice())){
			if(!PapUtil.checkDouble(entity.getFirstPrice()))
				throw new SystemException("一级扣款价必须输入数字");
			if(ArithUtil.comparison(entity.getFirstPrice(),"0")==-1)
				throw new SystemException("一级扣款价不允许为负数");
		}
		if(entity.getOverFirstPrice()!=null && !"".equals(entity.getOverFirstPrice())){
			
			if(!PapUtil.checkDouble(entity.getOverFirstPrice()))
				throw new SystemException("超过一级扣款价必须输入数字");
			if(ArithUtil.comparison(entity.getOverFirstPrice(),"0")==-1)
				throw new SystemException("超过一级扣款价不允许为负数");
		}
		
		//获取公司
		Farmer farmer = farmerService.selectById(entity.getFarmer().getId());
		String scode ="";
		if(farmer != null && farmer.getStage()!=null)
			scode = farmer.getStage().getDcode();
		if(!"COOPERATION".equals(scode))
			throw new SystemException("代养户必须是合作状态");
		
		entity.setCompany(farmer.getCompany());
		//验证该代养农户是不是还存在正没有终止的合同
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
//		sqlMap.put("farmer.id", "=", entity.getFarmer().getId());
//		sqlMap.put("status.dcode", "in", "'EFFECT','BREED'");
//		int _num = super.selectTotalRows(sqlMap);
//		sqlMap.clear();
//		if(_num>0)
//			throw new SystemException("代养户存在未终止的合同");
		//同一个养殖公司合同编码不允许重复
		sqlMap.put("company.id", "=", entity.getCompany().getId());
		sqlMap.put("code", "=", entity.getCode());
		
		int num = super.selectTotalRows(sqlMap);
		sqlMap.clear();
		if(num>0)
			throw new SystemException("合同编号已经存在");
		//验证猪苗单价不能为非数字
		if(!PapUtil.checkDouble(entity.getPigletPrice())){
			throw new SystemException("猪苗单价必须是数字类型");
		}
		//验证生猪头数必须是正整数
		if(!PapUtil.isInt(entity.getPigletQuan()))
			throw new SystemException("养殖头数必须是整数");
		
		List<ContractFeedPriced> flist = entity.getFeedPriced();
		List<ContractPigPriced> plist = entity.getPigPriced();
		if(flist==null || flist.isEmpty())
			throw new SystemException("不允许饲料定价为空");
		if(plist==null || plist.isEmpty())
			throw new SystemException("不允许销售定价为空");
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<flist.size();i++){
			ContractFeedPriced f = flist.get(i);
			if(f.getPrice()==null || "".equals(f.getPrice())){
				sb.append("饲料定价第【"+(i+1)+"】行单价为空");
				continue;
			}else if(!PapUtil.checkDouble(f.getPrice()) || ArithUtil.comparison(f.getPrice(), "0")<1){
				sb.append("饲料定价第【"+(i+1)+"】行单价必须为数字且大于0");
				continue;
			}
			f.setContract(entity);
		}
		for(int i=0;i<plist.size();i++){
			ContractPigPriced p = plist.get(i);
			BussinessEleDetail b = p.getPigLevel();
			String code = "";
			if(b != null)
				code = b.getDcode();
			if(!"O".equals(code)){
				if(p.getPrice()==null || "".equals(p.getPrice())){
					sb.append("销售定价第【"+(i+1)+"】行单价为空");
					continue;
				}else if(!PapUtil.checkDouble(p.getPrice()) || ArithUtil.comparison(p.getPrice(), "0")<1){
					sb.append("销售定价第【"+(i+1)+"】行单价必须为数字且大于0");
					continue;
				}
			}else{
				if(p.getPrice()!=null && !"".equals(p.getPrice())){
					if(!PapUtil.checkDouble(p.getPrice())){
						sb.append("销售定价第【"+(i+1)+"】行单价必须为数字");
						continue;
					}
				}
			}
			p.setContract(entity);
		}
		if(sb.length() > 0)
			throw new SystemException(sb.toString());
		//保存
		entity.setCheckStatus("0");
		Object obj = super.save(entity);
		contractFeedPricedService.save(flist);
		contractPigPricedService.save(plist);
		return obj;
	}
	/**
	 * 修改
	 * DZL
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public int updateHql(Contract entity)throws Exception{
		if(entity == null)
			throw new SystemException("对象不允许为空");
		if(entity.getCode()==null || "".equals(entity.getCode()))
			throw new SystemException("合同编码不允许为空");
		if(!FosterUtil.idCode(entity.getCode()))
			throw new SystemException("编码应为十位内的数字和字母！");
		if(entity.getVariety()==null || entity.getVariety().getId()==null || "".equals(entity.getVariety().getId()))
			throw new SystemException("养殖品种不允许为空");
		if(entity.getFarmer()==null || entity.getFarmer().getId()==null || "".equals(entity.getFarmer().getId()))
			throw new SystemException("所属代养户不允许为空");
		if(entity.getFeedFac()==null || entity.getFeedFac().getId()==null || "".equals(entity.getFeedFac().getId()))
			throw new SystemException("绑定饲料厂不允许为空");
		if(entity.getPigletQuan()==null || "".equals(entity.getPigletQuan()))
			throw new SystemException("养殖头数不能为空");
		if(entity.getPigletPrice()==null || "".equals(entity.getPigletPrice()))
			throw new SystemException("猪苗单价不能为空");
		if(entity.getTechnician()==null || entity.getTechnician().getId()==null ||  "".equals(entity.getTechnician().getId()))
			throw new SystemException("技术员不能为空");
		if(entity.getDevelopMan()==null || entity.getDevelopMan().getId()==null || "".equals(entity.getDevelopMan().getId()))
			throw new SystemException("开发员不能为空");
		
		//验证数量
		if(!PapUtil.checkDouble(entity.getPigletQuan()) || ArithUtil.comparison(entity.getPigletQuan(), "0")<1)
			throw new SystemException("养殖头数必须为数值且必须大于0");
		if(!PapUtil.checkDouble(entity.getPigletPrice()) || ArithUtil.comparison(entity.getPigletPrice(), "0")<1)
			throw new SystemException("猪苗单价必须为数值且必须大于0");
		
		if(entity.getStandPigletWeight() !=null && !"".equals(entity.getStandPigletWeight())){
			if(!PapUtil.checkDouble(entity.getStandPigletWeight()))
				throw new SystemException("猪苗标重必须为数值");
			if(ArithUtil.comparison(entity.getStandPigletWeight(),"0")==-1)
				throw new SystemException("猪苗标重不允许为负数");
		}
		if(entity.getStandSaleWeight() !=null && !"".equals(entity.getStandSaleWeight())){
			if(!PapUtil.checkDouble(entity.getStandSaleWeight()))
				throw new SystemException("出栏标重必须为数值");
			if(ArithUtil.comparison(entity.getStandSaleWeight(),"0")==-1)
				throw new SystemException("出栏标重不允许为负数");
		}
		if(entity.getMarketPrice() !=null && !"".equals(entity.getMarketPrice())){
			if(!PapUtil.checkDouble(entity.getMarketPrice()))
				throw new SystemException("市场销售价必须为数值");
			if(ArithUtil.comparison(entity.getMarketPrice(),"0")==-1)
				throw new SystemException("市场销售价不允许为负数");
		}
		if(entity.getAllowDiff() !=null && !"".equals(entity.getAllowDiff())){
			if(!PapUtil.checkDouble(entity.getAllowDiff()))
				throw new SystemException("允许偏差值必须输入数字");
			if(ArithUtil.comparison(entity.getAllowDiff(),"0")==-1)
				throw new SystemException("允许偏差值不允许为负数");
		}
		if(entity.getFirstDiff()!=null && !"".equals(entity.getFirstDiff())){
			if(!PapUtil.checkDouble(entity.getFirstDiff()))
				throw new SystemException("一级偏差值必须输入数字");
			if(ArithUtil.comparison(entity.getFirstDiff(),"0")==-1)
				throw new SystemException("一级偏差值不允许为负数");
		}
		if(entity.getFirstPrice()!=null && !"".equals(entity.getFirstPrice())){
			if(!PapUtil.checkDouble(entity.getFirstPrice()))
				throw new SystemException("一级扣款价必须输入数字");
			if(ArithUtil.comparison(entity.getFirstPrice(),"0")==-1)
				throw new SystemException("一级扣款价不允许为负数");
		}
		if(entity.getOverFirstPrice()!=null && !"".equals(entity.getOverFirstPrice())){
			
			if(!PapUtil.checkDouble(entity.getOverFirstPrice()))
				throw new SystemException("超过一级扣款价必须输入数字");
			if(ArithUtil.comparison(entity.getOverFirstPrice(),"0")==-1)
				throw new SystemException("超过一级扣款价不允许为负数");
		}
		
		//获取公司
		Farmer farmer = farmerService.selectById(entity.getFarmer().getId());
		String scode ="";
		if(farmer != null && farmer.getStage()!=null)
			scode = farmer.getStage().getDcode();
		if(!"COOPERATION".equals(scode))
			throw new SystemException("代养户必须是合作状态");
		
		entity.setCompany(farmer.getCompany());
		
		//同一个养殖公司合同编码不允许重复
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		sqlMap.put("company.id", "=", entity.getCompany().getId());
		sqlMap.put("code", "=", entity.getCode());
		sqlMap.put("id", "<>", entity.getId());
		
		int num = super.selectTotalRows(sqlMap);
		sqlMap.clear();
		if(num>0)
			throw new SystemException("合同编号已经存在");
		//验证猪苗单价不能为非数字
		if(!PapUtil.checkDouble(entity.getPigletPrice())){
			throw new SystemException("猪苗单价必须是数字类型");
		}
		//验证生猪头数必须是正整数
		if(!PapUtil.isInt(entity.getPigletQuan()))
			throw new SystemException("养殖头数必须是整数");
		
		List<ContractFeedPriced> flist = entity.getFeedPriced();
		List<ContractPigPriced> plist = entity.getPigPriced();
		if(flist==null || flist.isEmpty())
			throw new SystemException("不允许饲料定价为空");
		if(plist==null || plist.isEmpty())
			throw new SystemException("不允许销售定价为空");
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<flist.size();i++){
			ContractFeedPriced f = flist.get(i);
			if(f.getPrice()==null || "".equals(f.getPrice())){
				sb.append("饲料定价第【"+(i+1)+"】行单价为空");
				continue;
			}else if(!PapUtil.checkDouble(f.getPrice()) || ArithUtil.comparison(f.getPrice(), "0")<1){
				sb.append("饲料定价第【"+(i+1)+"】行单价必须为数字且大于0");
				continue;
			}
		}
		for(int i=0;i<plist.size();i++){
			ContractPigPriced p = plist.get(i);
			BussinessEleDetail b = p.getPigLevel();
			String code = "";
			if(b != null)
				code = b.getDcode();
			if(!"O".equals(code)){
				if(p.getPrice()==null || "".equals(p.getPrice())){
					sb.append("销售定价第【"+(i+1)+"】行单价为空");
					continue;
				}else if(!PapUtil.checkDouble(p.getPrice()) || ArithUtil.comparison(p.getPrice(), "0")<0){
					sb.append("销售定价第【"+(i+1)+"】行单价必须为数字且不能为负数");
					continue;
				}
			}else{
				if(p.getPrice()!=null && !"".equals(p.getPrice())){
					if(!PapUtil.checkDouble(p.getPrice())){
						sb.append("销售定价第【"+(i+1)+"】行单价必须为数字");
						continue;
					}
				}
			}
		}
		if(sb.length() > 0)
			throw new SystemException(sb.toString());
		//修改表头
		Contract ct = super.selectById(entity.getId());
		
		Technician toldTech = ct.getTechnician();
		
		ct.setCode(entity.getCode());
		ct.setRegistDate(entity.getRegistDate());
		ct.setVariety(entity.getVariety());
		ct.setPigletQuan(entity.getPigletQuan());
		ct.setPigletPrice(entity.getPigletPrice());
		ct.setFarmer(entity.getFarmer());
		ct.setCompany(entity.getCompany());
		ct.setFeedFac(entity.getFeedFac());
		ct.setTechnician(entity.getTechnician());
		ct.setDevelopMan(entity.getDevelopMan());
		
		ct.setStandPigletWeight(entity.getStandPigletWeight());
		ct.setStandSaleWeight(entity.getStandSaleWeight());
		ct.setMarketPrice(entity.getMarketPrice());
		ct.setAllowDiff(entity.getAllowDiff());
		ct.setFirstDiff(entity.getFirstDiff());
		ct.setFirstPrice(entity.getFirstPrice());
		ct.setOverFirstPrice(entity.getOverFirstPrice());
		
		
		
		// 删除明细
		Map<String, String> _m = entity.getTempStack();
		//------------------------=============处理饲料定价
		if (null != _m && null != _m.get("feedDeteleIDs") && !"".equals(_m.get("feedDeteleIDs"))  ) {
			String[] str = _m.get("feedDeteleIDs").split(",");
			if(str != null){
				for(String id : str)
					contractFeedPricedService.deleteById(Integer.parseInt(id));
			}
		}
		List<ContractFeedPriced> updateSwd = new ArrayList<ContractFeedPriced>(); 
		List<ContractFeedPriced> newSwd = new ArrayList<ContractFeedPriced>(); 
		for(ContractFeedPriced _f : flist){
			if(_f.getId()==null){
				_f.setContract(ct);
				newSwd.add(_f);
			}else{
				updateSwd.add(_f);
			}
		}
		//2.修改已存在的明细各项值
		for(ContractFeedPriced fp : updateSwd){
			ContractFeedPriced _fp = contractFeedPricedService.selectById(fp.getId());
			_fp.setPrice(fp.getPrice());
		}
		//3.保存新添加的明细
		contractFeedPricedService.save(newSwd);
		//------------------===================处理销售定价
		if (null != _m && null != _m.get("pigDeteleIDs") && !"".equals(_m.get("pigDeteleIDs"))  ) {
			String[] str = _m.get("pigDeteleIDs").split(",");
			if(str != null){
				for(String id : str)
					contractPigPricedService.deleteById(Integer.parseInt(id));
			}
		}
		List<ContractPigPriced> updatePig = new ArrayList<ContractPigPriced>(); 
		List<ContractPigPriced> newPig = new ArrayList<ContractPigPriced>(); 
		for(ContractPigPriced _p : plist){
			if(_p.getId()==null){
				_p.setContract(ct);
				newPig.add(_p);
			}else{
				updatePig.add(_p);
			}
		}
		//2.修改已存在的明细各项值
		for(ContractPigPriced fp : updatePig){
			ContractPigPriced _fp = contractPigPricedService.selectById(fp.getId());
			_fp.setPrice(fp.getPrice());
		}
		//3.保存新添加的明细
		contractPigPricedService.save(newPig);
		//若已经有批次信息，修改
		Batch b = batchService.selectBySinglet("contract.id", entity.getId());
		if(b!=null && "1".equals(ct.getCheckStatus())){
			if("Y".equals(b.getIsBalance()))
				throw new SystemException("该合同对用的批次已经结算，不允许修改");
			
			b.setContQuan(entity.getPigletQuan());
			b.setDevelopMan(entity.getDevelopMan());
			b.setFeedFac(entity.getFeedFac());
			//判断技术员是不是做了修改
			if(!(entity.getTechnician().getId().toString()).equals(toldTech.getId().toString())){
				//1.更新技术员与批次列表中第一项的技术员
				sqlMap.put("batch.id", "=", b.getId()+"");
				sqlMap.put("id", "order by", "asc");
				
				List<TechBatch> tlist = techBatchService.selectHQL(sqlMap);
				sqlMap.clear();
				if(tlist != null && !tlist.isEmpty()){
					TechBatch tb = tlist.get(0);
					tb.setTechnician(entity.getTechnician());
				}
				//2.判断原始技术员与批次上的技术员是不是一样 并且列表中是不是只有一条 如果符合更新批次上技术员
				Technician _told = b.getTechnician();
				String a = _told.getId()==null?"":_told.getId().toString();
				if(tlist.size()==1 && (toldTech.getId().toString()).equals(a))
					b.setTechnician(entity.getTechnician());
			}
		}
		
		return 1;
	}
	
	/**
	 * 单个对象批量删除
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 10:12:07 AM <br/>
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK,String path)throws Exception{
		if(PK==null || PK.length==0)
			throw new SystemException("请选择删除对象");
		StringBuffer sb = new StringBuffer();
		for(ID id : PK){
			//生成文件名称
			Contract c = super.selectById(id);
			String _fileName = "";
			if(c != null){
				_fileName = c.getFarmer().getName()+c.getCode();
			}else{
				throw new SystemException("合同信息不存在");
			}
			Batch batch = batchService.selectBySinglet("contract.id", c.getId());
			if(batch != null){
				//验证合同中的所有单据是不是还存在
				//喂料单是不是存在
				int n1 = feedBillService.selectTotalRows("batch.id", batch.getId());
				if(n1>0)
					sb.append("合同【"+id+"】存在饲料耗用单\n\r");
				int n2 = materialBillService.selectTotalRows("batch.id", batch.getId());
				if(n2>0)
					sb.append("合同【"+id+"】存在物料单\n\r");
				int n3 = medicalBillService.selectTotalRows("batch.id", batch.getId());
				if(n3>0)
					sb.append("合同【"+id+"】存在药品耗用单\n\r");
				int n4 = pigPurchaseService.selectTotalRows("batch.id", batch.getId());
				if(n4>0)
					sb.append("合同【"+id+"】存在猪苗采购单\n\r");
				int n5 = feedInWareService.selectTotalRows("batch.id", batch.getId());
				if(n5>0)
					sb.append("合同【"+id+"】存在领料单\n\r");
				int n6 = deathBillService.selectTotalRows("batch.id", batch.getId());
				if(n6>0)
					sb.append("合同【"+id+"】存在死亡单\n\r");
				//int n7 = eliminateBillService.selectTotalRows("batch.id", batch.getId());
				//if(n7>0)
				//	sb.append("合同【"+id+"】存在淘汰单\n\r");
				int n8 = farmerSaleService.selectTotalRows("batch.id", batch.getId());
				if(n8>0)
					sb.append("合同【"+id+"】存在销售单\n\r");
				int n9 = outPigstyService.selectTotalRows("batch.id", batch.getId());
				if(n9>0)
					sb.append("合同【"+id+"】存在出栏单\n\r");
				int n10 = companyBalanceService.selectTotalRows("batch.id", batch.getId());
				if(n10>0)
					sb.append("合同【"+id+"】存在公司结算单\n\r");
				int n11 = farmerBalanceService.selectTotalRows("batch.id", batch.getId());
				if(n11>0)
					sb.append("合同【"+id+"】存在代养结算单\n\r");
				int n12 = drugBillService.selectTotalRows("batch.id", batch.getId());
				if(n12>0)
					sb.append("合同【"+id+"】存在领药单\n\r");
			}
			//删除批次
			batchService.deleteBySingletAll("contract.id", c.getId());
			//删除附件
			//删除文件名相同的文件
			File file = new File(path);
			File[] files = file.listFiles();
			if(files != null && files.length>0){
				for(File f : files){
					if(f.isFile()){
						//获取文件名比较文件名
						String oldname = f.getName();
						//取出后缀
						String[] _strname = oldname.split("\\.");
						if(_strname != null && _strname.length>0 && (_strname[0].equals(_fileName))){
							//删除文件
							f.delete();
						}
					}
				}
			}
		}
		if(sb.length()>0)
			throw new SystemException(sb.toString());
		
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("contract.id", "in", PapUtil.arrayToSQLStr((String[])PK));
		contractPigPricedService.delete(sqlMap);
		contractFeedPricedService.delete(sqlMap);
		
		return super.deleteByIds(PK);
	}
	
	/**
	 * 功能：审核<br/>
	 *
	 * @author DZL 审核以后第一 合同状态变更为生效状态，第二，生成批次对象
	 * @version 2015-3-9 下午05:28:16 <br/>
	 */
	public void check(String[] idArr)throws Exception{
		if(idArr == null || idArr.length==0)
			throw new SystemException("未发现要审核的单据");
		Users u = SysContainer.get();
		StringBuffer sb = new StringBuffer();
		for(String s : idArr){
			Contract c = super.selectById(s);
			if(c == null){
				sb.append("编号为【"+s+"】的合同不存在");
				continue;
			}else{
				//获取公司
				Farmer farmer = c.getFarmer();
				String scode ="";
				if(farmer != null && farmer.getStage()!=null)
					scode = farmer.getStage().getDcode();
				if(!"COOPERATION".equals(scode))
					throw new SystemException("代养户必须是合作状态");
				//设置技术员
				Technician tc = c.getTechnician();
				if(tc != null && tc.getId() != null && !"".equals(tc.getId())){
					farmer.setTechnician(tc);
				}
				
				//验证该代养农户是不是还存在正没有终止的合同
//				SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
//				sqlMap.put("farmer.id", "=", c.getFarmer().getId());
//				sqlMap.put("status.dcode", "in", "'EFFECT','BREED'");
//				int num = super.selectTotalRows(sqlMap);
//				sqlMap.clear();
//				if(num>0){
//					sb.append("编号为【"+s+"】的合同代养户存在为终止的合同");
//					continue;
//				}
				//合同生效
				c.setStatus(new BussinessEleDetail("EFFECT"));
				//生成批次对象
				//查询批次是不是存在
				Batch b = batchService.selectBySinglet("contract.id", c.getId());
				if(b==null){
					//获取该养殖户的所有批次数
					int num = batchService.selectTotalRows("farmer.id", farmer.getId());
					b = new Batch();
					b.setBatchNumber((num+1)+"");
					b.setContract(c);
					b.setFarmer(c.getFarmer());
					b.setCompany(c.getCompany());
					b.setIsBalance("N");
					b.setContQuan(c.getPigletQuan());
					b.setTechnician(c.getTechnician());
					b.setDevelopMan(c.getDevelopMan());
					b.setFeedFac(c.getFeedFac());
					batchService.save(b);
				}else{
					b.setContQuan(c.getPigletQuan());
					b.setTechnician(c.getTechnician());
					b.setDevelopMan(c.getDevelopMan());
					b.setFeedFac(c.getFeedFac());
				}
				//回填批次
				c.setBatch(b.getId()+"");
				
				//生成技术员批次列表
				TechBatch tb = new TechBatch();
				tb.setCompany(c.getCompany());
				tb.setFarmer(c.getFarmer());
				tb.setBatch(b);
				tb.setTechnician(c.getTechnician());
				tb.setStartDate(c.getRegistDate());
				
				techBatchService.save(tb);
				
				//变更状态
				//改变审核状态
				c.setCheckStatus("1");
				c.setCheckUser(u.getUserRealName());
				c.setCheckDate(PapUtil.date(new Date()));
			}
		}
		if(sb.length()>0)
			throw new SystemException(sb.toString());
	}
	/**
	 * 功能：撤销<br/>
	 *
	 * @author DZL 撤销时需要验证合同状态是不是已经不是 生效状态，不是生效状态的不允许撤销，如果是是的话，需要修改合同状态为空，并删除对应的批次
	 * @version 2015-3-9 下午05:28:16 <br/>
	 */
	public void cancelCheck(String id)throws Exception{
		if(id==null || "".equals(id))
			throw new SystemException("未发现要撤销的合同");
		Contract c = super.selectById(id);
		if(c == null)
			throw new SystemException("编号为【"+id+"】的合同不存在");
		//验证合同状态
		BussinessEleDetail b = c.getStatus();
		String status = "";
		if(b != null)
			status = b.getDcode();
		if(!"EFFECT".equals(status))
			throw new SystemException("合同已经进行养殖不允许撤销");
		//验证有没有审核的 饲料入库单 药品医疗单，有没有其他物料领料单 有的话不允许撤销
		Batch batch = batchService.selectBySinglet("contract.id", c.getId()); 
		
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("batch.id", "=", batch.getId());
		sqlMap.put("checkStatus", "=", "1");
		
		int num = feedBillService.selectTotalRows(sqlMap);
		if(num>0)
			throw new SystemException("有合同对应的饲料耗用单，不允许撤销");
		int _num = materialBillService.selectTotalRows(sqlMap);
		if(_num>0)
			throw new SystemException("有合同对应的其他物料单，不允许撤销");
		int num_ = medicalBillService.selectTotalRows(sqlMap);
		if(num_>0)
			throw new SystemException("有合同对应的其药品耗用单，不允许撤销");
		int feedInNum = feedInWareService.selectTotalRows(sqlMap);
		if(feedInNum>0)
			throw new SystemException("有合同对应的领料单，不允许撤销");
		int drugNum = drugBillService.selectTotalRows(sqlMap);
		if(drugNum>0)
			throw new SystemException("有合同对应药品领药单，不允许撤销");
		
		sqlMap=null;
		//变更状态
		c.setStatus(null);
		
		
		//删除关于该技术员与批次的列表
		techBatchService.deleteBySingletAll("batch.id", batch.getId());
		//清空批次中数据
		batch.setQuantity(null);
		batch.setDeathQuan(null);
		batch.setOtherDeathQuan(null);
		batch.setEliminateQuan(null);
		batch.setSaleQuan(null);
		batch.setPigletQuan(null);
		batch.setFcr(null);
		batch.setOutDate(null);
		batch.setInDate(null);
		batch.setIsBalance("N");
		batch.setFeedFac(null);
		batch.setContQuan(null);
		batch.setTechnician(null);
		batch.setDevelopMan(null);

		//去除回填批次号
		c.setBatch(null);
		//代养户技术员
		Farmer farmer = batch.getFarmer();
		if(farmer != null)
			farmer.setTechnician(null);
		
		//改变审核状态
		c.setCheckStatus("0");
		c.setCheckDate(null);
		c.setCheckUser(null);
	}
	
	/**
	 * 终止合同
	 * @param id
	 * @throws Exception
	 */
	public void enableEffect(String id)throws Exception{
		if(id==null || "".equals(id))
			throw new SystemException("未发现终止的合同");
		Contract c = super.selectById(id);
		if(c == null)
			throw new SystemException("编号为【"+id+"】的合同不存在");
		//验证合同状态
		BussinessEleDetail b = c.getStatus();
		String status = "";
		if(b != null)
			status = b.getDcode();
		if(!"BREED".equals(status))
			throw new SystemException("该合同不是养殖状态无法终止");
		
		//查看改合同下是不是还有未审核的单据
		Batch batch = batchService.selectBySinglet("contract.id", c.getId()); 
		SqlMap<String,String,Object> sqlMap = new SqlMap<String,String,Object>();
		
		sqlMap.put("batch.id", "=", batch.getId());
		sqlMap.put("checkStatus", "=", "0");
		
		int feedBillNum = feedBillService.selectTotalRows(sqlMap);
		if(feedBillNum>0)
			throw new SystemException("有该合同对应的饲料耗用单未审核，不允许终止合同");
		int materialBillNum = materialBillService.selectTotalRows(sqlMap);
		if(materialBillNum>0)
			throw new SystemException("有该合同对应的其他物料单未审核，不允许终止合同");
		int medicalBillNum = medicalBillService.selectTotalRows(sqlMap);
		if(medicalBillNum>0)
			throw new SystemException("有该合同对应的药品耗用单未审核，不允许终止合同");
		int drugBillNum = drugBillService.selectTotalRows(sqlMap);
		if(drugBillNum>0)
			throw new SystemException("有该合同对应的药品领药单未审核，不允许终止合同");
		int pigNum = pigPurchaseService.selectTotalRows(sqlMap);
		if(pigNum>0)
			throw new SystemException("有该合同对应的生猪登记单未审核，不允许终止合同");
		int feedInNum = feedInWareService.selectTotalRows(sqlMap);
		if(feedInNum>0)
			throw new SystemException("有该合同对应的饲料入库单未审核，不允许终止合同");
		int deadNum = deathBillService.selectTotalRows(sqlMap);
		if(deadNum>0)
			throw new SystemException("有该合同对应的死亡单未审核，不允许终止合同");
		int outNum = outPigstyService.selectTotalRows(sqlMap);
		if(outNum>0)
			throw new SystemException("有该合同对应的出栏单未审核，不允许终止合同");
		int saleNum = farmerSaleService.selectTotalRows(sqlMap);
		if(saleNum>0)
			throw new SystemException("有该合同对应的销售单未审核，不允许终止合同");
		sqlMap.clear();
		
		//验证库存是不是为空，
		List<FeedWarehouse> fhList = feedWarehouseService.selectBySingletAll("batch.id",batch.getId());
		boolean flag=false;
		for(FeedWarehouse f : fhList){
			String v = "0";
			if(f.getQuantity()!=null && !"".equals(f.getQuantity()))
				v = f.getQuantity();
			if(ArithUtil.comparison(v, "0") != 0)
				flag = true;
		}
		if(flag)
			throw new SystemException("库存在还有饲料未使用，合同不允许终止");
		
		//验证客户药品库存是不是为空
		List<DrugWarehouseFar> drList = drugWarehouseFarService.selectBySingletAll("batch.id",batch.getId());
		flag=false;
		for(DrugWarehouseFar dr : drList){
			String v = "0";
			if(dr.getQuantity()!=null && !"".equals(dr.getQuantity()))
				v = dr.getQuantity();
			if(ArithUtil.comparison(v, "0") != 0)
				flag = true;
		}
		if(flag)
			throw new SystemException("库存在还有未使用的药品，合同不允许终止");
		
		//验证批次中的生猪头数是不是为0
		if(batch != null && ArithUtil.comparison(batch.getQuantity(), "0") != 0)
			throw new SystemException("该批次生猪还有未出栏的生猪，合同不允许终止");
		//验证销售头数
		//1.获取销售总头数
		sqlMap.put("batch.id", "=", batch.getId());
		sqlMap.put("checkStatus", "=", "1");
		List<FarmerSale> flist = farmerSaleService.selectHQL(sqlMap);
		sqlMap.clear();
		String saleHeadNum="0";
		if(flist != null && !flist.isEmpty()){
			for(FarmerSale fs : flist)
				saleHeadNum = ArithUtil.add(saleHeadNum,fs.getTotalQuan());
		}
		if(ArithUtil.comparison(saleHeadNum, batch.getSaleQuan()) != 0)
			throw new SystemException("该批次生猪还有已出栏未销售的生猪，合同不允许终止");
		//---------------------------------------------------------------------------------------
		//变更状态
		c.setStatus(new BussinessEleDetail("LOST"));
		//设置终止时间
		c.setEndDate(PapUtil.notFullDate(new Date()));
		//更新养殖户已养殖批次
		Farmer f = c.getFarmer();
		String batchNumber = "0";
		if(f != null && f.getBatchNum() != null && PapUtil.checkDouble(f.getBatchNum()))
			batchNumber = f.getBatchNum();
		f.setBatchNum(ArithUtil.add(batchNumber, "1",0));
		
		//跟新最后一个技术员的结束时间
		sqlMap.put("batch.id", "=", batch.getId());
		sqlMap.put("endDate", "is null", "");
		
		List<TechBatch> tlist = techBatchService.selectHQL(sqlMap);
		sqlMap.clear();
		if(tlist != null && !tlist.isEmpty()){
			TechBatch tb = tlist.get(0);
			tb.setEndDate(PapUtil.notFullDate(new Date()));
		}
	}
	
	/**
	 * 中止合同
	 * @param id
	 * @throws Exception
	 */
	public void enableStopEffect(String id)throws Exception{
		if(id==null || "".equals(id))
			throw new SystemException("未发现中止的合同");
		Contract c = super.selectById(id);
		if(c == null)
			throw new SystemException("编号为【"+id+"】的合同不存在");
		//验证合同状态
		BussinessEleDetail b = c.getStatus();
		String status = "";
		if(b != null)
			status = b.getDcode();
		if(!"EFFECT".equals(status))
			throw new SystemException("该合同不是生效状态无法中止");
		
		//验证库存是不是为空，批次中的生猪头数是不是为0
		Batch batch = batchService.selectBySinglet("contract.id", c.getId()); 
		List<FeedWarehouse> fhList = feedWarehouseService.selectBySingletAll("batch.id",batch.getId());
		boolean flag=false;
		for(FeedWarehouse f : fhList){
			String v = "0";
			if(f.getQuantity()!=null && !"".equals(f.getQuantity()))
				v = f.getQuantity();
			if(ArithUtil.comparison(v, "0") != 0)
				flag = true;
		}
		if(flag)
			throw new SystemException("库存在还有饲料未使用，合同不允许中止");
		
		//验证客户药品库存是不是为空
		List<DrugWarehouseFar> drList = drugWarehouseFarService.selectBySingletAll("batch.id",batch.getId());
		flag=false;
		for(DrugWarehouseFar dr : drList){
			String v = "0";
			if(dr.getQuantity()!=null && !"".equals(dr.getQuantity()))
				v = dr.getQuantity();
			if(ArithUtil.comparison(v, "0") != 0)
				flag = true;
		}
		if(flag)
			throw new SystemException("库存在还有未使用的药品，合同不允许终止");
		
		//变更状态
		c.setStatus(new BussinessEleDetail("STOP"));
		//设置终止时间
		c.setEndDate(PapUtil.notFullDate(new Date()));
		
		//删除关于该技术员与批次的列表
		techBatchService.deleteBySingletAll("batch.id", batch.getId());
		//删除批次
		batchService.deleteBySingletAll("contract.id", c.getId());
		
		//代养户技术员
		Farmer farmer = batch.getFarmer();
		if(farmer != null)
			farmer.setTechnician(null);
	}
	
	/**
	 * 终止还原
	 * @param id
	 * @throws Exception
	 */
	public void onEffect(String id)throws Exception{
		if(id==null || "".equals(id))
			throw new SystemException("未发现要恢复的合同");
		Contract c = super.selectById(id);
		if(c == null)
			throw new SystemException("编号为【"+id+"】的合同不存在");
		//验证合同状态
		BussinessEleDetail b = c.getStatus();
		String status = "";
		if(b != null)
			status = b.getDcode();
		
		//更新养殖户已养殖批次
		Farmer f = c.getFarmer();
		String scode ="";
		if(f != null && f.getStage()!=null)
			scode = f.getStage().getDcode();
		
		if("LOST".equals(status)){
			//验证改合同对应的批次是不是已经结算，已经结算的合同不能恢复
			Batch batch = batchService.selectBySinglet("contract.id", c.getId());
			if(batch != null && "Y".equals(batch.getIsBalance()))
				throw new SystemException("合同对应批次的生猪已经结算，不允许恢复");
			if(!"COOPERATION".equals(scode))
				throw new SystemException("代养户必须是合作状态");
			//变更状态
			c.setStatus(new BussinessEleDetail("BREED"));
			//设置终止时间
			c.setEndDate(null);
			
			String batchNumber = "0";
			if(f != null && f.getBatchNum() != null && PapUtil.checkDouble(f.getBatchNum()))
				batchNumber = f.getBatchNum();
			if(ArithUtil.comparison(batchNumber, "0")==1)
				f.setBatchNum(ArithUtil.sub(batchNumber, "1",0));
			
			//将技术员与批次列表中最后一个技术员的结束时间 清除
			SqlMap<String,String,Object> sqlMap = new SqlMap<String,String,Object>();
			sqlMap.put("batch.id", "=", c.getBatch());
			sqlMap.put("id", "order by", "desc");
			
			List<TechBatch> tlist = techBatchService.selectHQL(sqlMap);
			sqlMap.clear();
			if(tlist != null && !tlist.isEmpty()){
				TechBatch tb = tlist.get(0);
				tb.setEndDate(null);
			}
			
			
		}else if("STOP".equals(status)){
			if(!"COOPERATION".equals(scode))
				throw new SystemException("代养户非合作状态不允许恢复");
			//变更状态
			c.setStatus(new BussinessEleDetail("EFFECT"));
			//设置终止时间
			c.setEndDate(null);
			//生成批次
			int _num = batchService.selectTotalRows("contract.id", c.getId());
			if(_num<=0){
				//获取该养殖户的所有批次数
				int num = batchService.selectTotalRows("farmer.id", f.getId());
				Batch bch = new Batch();
				bch.setBatchNumber((num+1)+"");
				bch.setContract(c);
				bch.setFarmer(c.getFarmer());
				bch.setCompany(c.getCompany());
				bch.setIsBalance("N");
				bch.setContQuan(c.getPigletQuan());
				bch.setTechnician(c.getTechnician());
				bch.setDevelopMan(c.getDevelopMan());
				bch.setFeedFac(c.getFeedFac());
				batchService.save(bch);
				
				c.setBatch(bch.getId()+"");
				
				//生成技术员批次列表
				TechBatch tb = new TechBatch();
				tb.setCompany(c.getCompany());
				tb.setFarmer(c.getFarmer());
				tb.setBatch(bch);
				tb.setTechnician(c.getTechnician());
				tb.setStartDate(c.getRegistDate());
				
				techBatchService.save(tb);
			}
			f.setTechnician(c.getTechnician());
		}else
			throw new SystemException("要恢复的合同不是终止或中止状态");
	}
	
	/**
	 * 上传合同
	 * @param id
	 * @throws Exception
	 */
	public boolean upload(File doc,String path,String fileName,String id)throws Exception{
		// 获得系统参数附件文件大小设置
		SysParam sysParam = SysConfigContext.getParam("ContractAnnex_Size");
		if (null == sysParam)
			throw new SystemException("合同附件大小[ContractAnnex_Size]在系统参数中未进行配置，获取失败！");
		
		String value = sysParam.getValue();
		if (null != value && !"".equals(value)) {
			int limitSize = Integer.parseInt(value);
			long v = limitSize*1024*1204;
			long fileSize = doc.length();
			if (fileSize > v) {
				throw new SystemException("合同附件大小不能超过" + value + "M，上传失败！");
			}
		}
		//获取原始文件的后缀名
		String suffix = "rar";
		if(fileName != null && !"".equals(fileName)){
			String[] str = fileName.split("\\.");
			suffix=str[1];
		}
		//生成文件名称
		Contract c = super.selectById(id);
		String _fileName = "";
		if(c != null){
			_fileName = c.getFarmer().getName()+c.getCode();
		}else{
			throw new SystemException("合同信息不存在");
		}
		//删除文件名相同的文件
		File file = new File(path);
		File[] files = file.listFiles();
		if(files != null && files.length>0){
			for(File f : files){
				if(f.isFile()){
					//获取文件名比较文件名
					String oldname = f.getName();
					//取出后缀
					String[] _strname = oldname.split("\\.");
					if(_strname != null && _strname.length>0 && (_strname[0].equals(_fileName))){
						//删除文件
						f.delete();
					}
				}
			}
		}
        //输出流  
        OutputStream os = new FileOutputStream(new File(path,_fileName+"."+suffix));  
        //输入流  
        InputStream is = new FileInputStream(doc);  
          
        byte[] buf = new byte[1024];  
        int length = 0 ;  
          
	    while(-1 != (length = is.read(buf))) {  
	    	os.write(buf, 0, length) ;  
	    }
	    is.close();  
	    os.close();  
		//文件上传完成以后，更改合同文件上传状态
	    c.setIsAnnex("Y");
		return true;
	}
	
	/**
	 * 下载合同
	 * @param id
	 * @throws Exception
	 */
	public InputStream down(String path,String id,String[] fname)throws Exception{
		//生成文件名称
		Contract c = super.selectById(id);
		if(c == null)
			return null;
		String _fileName = c.getFarmer().getName()+c.getCode();
		InputStream is = null;
		try{
			//获得文件
			File file = new File(path);
			File[] files = file.listFiles();
			File _file = null;
			if(files != null && files.length>0){
				for(File f : files){
					if(f.isFile()){
						//获取文件名比较文件名
						String oldname = f.getName();
						//取出后缀
						String[] _strname = oldname.split("\\.");
						if(_strname != null && _strname.length>0 && (_strname[0].equals(_fileName))){
							_file = f;
							_fileName = oldname;
							break;
						}
					}
				}
			}
	        //输入流  
			if(_file == null)
				return null;
			fname[0]=_fileName;
	        is = new FileInputStream(_file);
		}finally{
			is.close();
		}
        return is;
	}
	
	/**
	 * 导入模板下载
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-5 下午03:24:15
	 */
	public InputStream downloadTemplate(String realPath)throws Exception{
		InputStream fileInput = null;
		try {
			fileInput = new FileInputStream(realPath + "/WEB-INF/template/" + "contract.xlsx");
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new SystemException("系统找不到指定路径下的模板文件！");
		}
		return fileInput;
		
	}
	
	/**
	 * 导入合同
	 * @Description:TODO
	 * @param file
	 * @param company
	 * @param objects
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-9 下午04:03:58
	 */
	public List<Contract> operateFile(File file, Company company, Object... objects)throws Exception{
		if(company==null || company.getId()==null || "".equals(company.getId()))
			throw new SystemException("请选择公司");
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		
		//验证七种饲料
		List<Feed> feeds = null;
		//验证代乳宝
		Feed drb = new Feed();
		sqlMap.put("company.id", "=", company.getId());
		sqlMap.put("name", "=", "DRB");
		feeds = feedService.selectHQL(sqlMap);
		sqlMap.clear();
		if(feeds==null || feeds.isEmpty())
			throw new SystemException("不存在饲料【DRB】");
		else{
			drb = feeds.get(0);
		}
		feeds.clear();
		//验证乳猪宝
		Feed rzb = new Feed();
		sqlMap.put("company.id", "=", company.getId());
		sqlMap.put("name", "=", "RZB");
		feeds = feedService.selectHQL(sqlMap);
		sqlMap.clear();
		if(feeds==null || feeds.isEmpty())
			throw new SystemException("不存在饲料【RZB】");
		else{
			rzb = feeds.get(0);
		}
		feeds.clear();
		//验证仔猪宝
		Feed zzb = new Feed();
		sqlMap.put("company.id", "=", company.getId());
		sqlMap.put("name", "=", "ZZB");
		feeds = feedService.selectHQL(sqlMap);
		sqlMap.clear();
		if(feeds==null || feeds.isEmpty())
			throw new SystemException("不存在饲料【ZZB】");
		else{
			zzb = feeds.get(0);
		}
		feeds.clear();
		//验证551
		Feed _551 = new Feed();
		sqlMap.put("company.id", "=", company.getId());
		sqlMap.put("name", "=", "551");
		feeds = feedService.selectHQL(sqlMap);
		sqlMap.clear();
		if(feeds==null || feeds.isEmpty())
			throw new SystemException("不存在饲料【551】");
		else{
			_551 = feeds.get(0);
		}
		feeds.clear();
		//验证552
		Feed _552 = new Feed();
		sqlMap.put("company.id", "=", company.getId());
		sqlMap.put("name", "=", "552");
		feeds = feedService.selectHQL(sqlMap);
		sqlMap.clear();
		if(feeds==null || feeds.isEmpty())
			throw new SystemException("不存在饲料【552】");
		else{
			_552 = feeds.get(0);
		}
		feeds.clear();
		//验证553
		Feed _553 = new Feed();
		sqlMap.put("company.id", "=", company.getId());
		sqlMap.put("name", "=", "553");
		feeds = feedService.selectHQL(sqlMap);
		sqlMap.clear();
		if(feeds==null || feeds.isEmpty())
			throw new SystemException("不存在饲料【553】");
		else{
			_553 = feeds.get(0);
		}
		feeds.clear();
		//验证554
		Feed _554 = new Feed();
		sqlMap.put("company.id", "=", company.getId());
		sqlMap.put("name", "=", "554");
		feeds = feedService.selectHQL(sqlMap);
		sqlMap.clear();
		if(feeds==null || feeds.isEmpty())
			throw new SystemException("不存在饲料【554】");
		else{
			_554 = feeds.get(0);
		}
		feeds.clear();
		//验证代乳宝
		Feed drbs = new Feed();
		sqlMap.put("company.id", "=", company.getId());
		sqlMap.put("name", "=", "DRB(S)");
		feeds = feedService.selectHQL(sqlMap);
		sqlMap.clear();
		if(feeds==null || feeds.isEmpty())
			throw new SystemException("不存在饲料【DRB(S)】");
		else{
			drbs = feeds.get(0);
		}
		feeds.clear();
		//验证乳猪宝
		Feed rzbs = new Feed();
		sqlMap.put("company.id", "=", company.getId());
		sqlMap.put("name", "=", "RZB(S)");
		feeds = feedService.selectHQL(sqlMap);
		sqlMap.clear();
		if(feeds==null || feeds.isEmpty())
			throw new SystemException("不存在饲料【RZB(S)】");
		else{
			rzbs = feeds.get(0);
		}
		feeds.clear();
		//验证仔猪宝
		Feed zzbs = new Feed();
		sqlMap.put("company.id", "=", company.getId());
		sqlMap.put("name", "=", "ZZB(S)");
		feeds = feedService.selectHQL(sqlMap);
		sqlMap.clear();
		if(feeds==null || feeds.isEmpty())
			throw new SystemException("不存在饲料【ZZB(S)】");
		else{
			zzbs = feeds.get(0);
		}
		feeds.clear();
		//验证551
		Feed _551s = new Feed();
		sqlMap.put("company.id", "=", company.getId());
		sqlMap.put("name", "=", "551(S)");
		feeds = feedService.selectHQL(sqlMap);
		sqlMap.clear();
		if(feeds==null || feeds.isEmpty())
			throw new SystemException("不存在饲料【551(S)】");
		else{
			_551s = feeds.get(0);
		}
		feeds.clear();
		//验证552
		Feed _552s = new Feed();
		sqlMap.put("company.id", "=", company.getId());
		sqlMap.put("name", "=", "552(S)");
		feeds = feedService.selectHQL(sqlMap);
		sqlMap.clear();
		if(feeds==null || feeds.isEmpty())
			throw new SystemException("不存在饲料【552(S)】");
		else{
			_552s = feeds.get(0);
		}
		feeds.clear();
		//验证553
		Feed _553s = new Feed();
		sqlMap.put("company.id", "=", company.getId());
		sqlMap.put("name", "=", "553(S)");
		feeds = feedService.selectHQL(sqlMap);
		sqlMap.clear();
		if(feeds==null || feeds.isEmpty())
			throw new SystemException("不存在饲料【553(S)】");
		else{
			_553s = feeds.get(0);
		}
		feeds.clear();
		//验证554
		Feed _554s = new Feed();
		sqlMap.put("company.id", "=", company.getId());
		sqlMap.put("name", "=", "554(S)");
		feeds = feedService.selectHQL(sqlMap);
		sqlMap.clear();
		if(feeds==null || feeds.isEmpty())
			throw new SystemException("不存在饲料【554(S)】");
		else{
			_554s = feeds.get(0);
		}
		feeds.clear();
		
		List<Contract> contracts = new ArrayList<Contract>();
		Workbook workbook = ExcelUtil.buildWorkbook(file, (String)objects[0]);
		if (null != workbook) {
			Sheet sheet = workbook.getSheetAt(0); // 获得第一个Excel Sheet
			int lastRowNum = sheet.getLastRowNum(); // 最后行号，默认为索引号，即从0开始到当前行号-1，如excel有10条数据，firstRowNum=0，而lastRowNum=9
			if (lastRowNum >0) {
				int firstRowNum = sheet.getFirstRowNum();
				StringBuffer sb = new StringBuffer(); // 存储校验非法描述
				for (int i = firstRowNum + 1; i <= lastRowNum; i++) {
					Row row = sheet.getRow(i);
					if (null != row) {
						//1.验证是否为空
						//表头
						String farmer = ExcelUtil.checkCellValue(row.getCell(0), i + 1, "代养户", true, sb);
						String code = ExcelUtil.checkCellValue(row.getCell(1), i + 1, "合同编码", true, sb);
						String registDate = ExcelUtil.checkCellValue(row.getCell(2), i + 1, "签订日期", true, sb);
						String feedFac = ExcelUtil.checkCellValue(row.getCell(3), i + 1, "饲料厂", true, sb);
						String variety = ExcelUtil.checkCellValue(row.getCell(4), i + 1, "养殖品种", true, sb);
						String pigletQuan = ExcelUtil.checkCellValue(row.getCell(5), i + 1, "养殖数量", true, sb);
						String pigletPrice = ExcelUtil.checkCellValue(row.getCell(6), i + 1, "猪苗单价", true, sb);
						String standPigletWeight = ExcelUtil.checkCellValue(row.getCell(7), i + 1, "猪苗标重", true, sb);
						String standSaleWeight = ExcelUtil.checkCellValue(row.getCell(8), i + 1, "出栏标重", true, sb);
						String marketPrice = ExcelUtil.checkCellValue(row.getCell(9), i + 1, "销售市价", true, sb);
						String allowDiff = ExcelUtil.checkCellValue(row.getCell(10), i + 1, "允许料肉比偏差", true, sb);
						String firstPrice = ExcelUtil.checkCellValue(row.getCell(11), i + 1, "超出允许偏差值扣款价(元/吨)", true, sb);
						String firstDiff = ExcelUtil.checkCellValue(row.getCell(12), i + 1, "料肉比一级偏差", true, sb);
						String overFirstPrice = ExcelUtil.checkCellValue(row.getCell(13), i + 1, "超出一级偏差值扣款价(元/吨)", true, sb);
						//饲料价格明细
						String drbPrice = ExcelUtil.checkCellValue(row.getCell(14), i + 1, "代乳宝(元/KG)", true, sb);
						String rzbPrice = ExcelUtil.checkCellValue(row.getCell(15), i + 1, "乳猪宝(元/KG)", true, sb);
						String zzbPrice = ExcelUtil.checkCellValue(row.getCell(16), i + 1, "仔猪宝(元/KG)", true, sb);
						String p551 = ExcelUtil.checkCellValue(row.getCell(17), i + 1, "551(元/KG)", true, sb);
						String p552 = ExcelUtil.checkCellValue(row.getCell(18), i + 1, "552(元/KG)", true, sb);
						String p553 = ExcelUtil.checkCellValue(row.getCell(19), i + 1, "553(元/KG)", true, sb);
						String p554 = ExcelUtil.checkCellValue(row.getCell(20), i + 1, "554(元/KG)", true, sb);
						//销售价格明细
						String zp = ExcelUtil.checkCellValue(row.getCell(21), i + 1, "正品(元/KG)", true, sb);
						String cyj = ExcelUtil.checkCellValue(row.getCell(22), i + 1, "次一级(元/KG)", true, sb);
						String cej = ExcelUtil.checkCellValue(row.getCell(23), i + 1, "次二级(元/KG)", false, sb);
						String jwp = ExcelUtil.checkCellValue(row.getCell(24), i + 1, "级外品(元/KG)", false, sb);
						
						String developer= ExcelUtil.checkCellValue(row.getCell(25), i + 1, "开发员", true, sb);
						String technican= ExcelUtil.checkCellValue(row.getCell(26), i + 1, "技术员", true, sb);
						
						//验证表头
						//验证代养户
						Farmer fam = new Farmer();
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("name", "=", farmer);
						List<Farmer> farmers = farmerService.selectHQL(sqlMap);
						sqlMap.clear();
						if(farmers==null || farmers.isEmpty()){
							sb.append(new SystemException("第"+(i+1)+"行不存在代养户【"+farmer+"】"));
							continue;
						}else{
							fam = farmers.get(0);
						}
						//验证饲料厂
						FeedFac fef = new FeedFac();
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("name", "=", feedFac);
						List<FeedFac> feedFacs = feedFacService.selectHQL(sqlMap);
						sqlMap.clear();
						if(feedFacs==null || feedFacs.isEmpty()){
							sb.append(new SystemException("第"+(i+1)+"行不存在饲料厂【"+feedFac+"】"));
							continue;
						}else{
							fef = feedFacs.get(0);
						}
						//验证品种
						Variety vv = new Variety();
						sqlMap.put("name", "=", variety);
						List<Variety> varieties = varietyService.selectHQL(sqlMap);
						sqlMap.clear();
						if(varieties==null || varieties.isEmpty()){
							sb.append(new SystemException("第"+(i+1)+"行不存在品种【"+variety+"】"));
							continue;
						}else{
							vv = varieties.get(0);
						}
						//验证开发员
						DevelopMan dm=new DevelopMan();
						String hql_dm="from DevelopMan e where e.name='"+developer+"'and e.company.id='"+company.getId()+"'";
						List<DevelopMan> dList=developManService.selectByHQL(hql_dm);
						if(dList==null || dList.size()==0){
							sb.append(new SystemException("第"+(i+1)+"行不存在开发员【"+developer+"】<br/>"));
							continue;
						}else
							dm=dList.get(0);
						//验证技术员
						Technician tc=new Technician();
						String hql_tc="from Technician e where e.name='"+technican+"'and e.company.id='"+company.getId()+"'";
						List<Technician> tList=technicianService.selectByHQL(hql_tc);
						if(tList==null || tList.size()==0){
							sb.append(new SystemException("第"+(i+1)+"行不存在技术员【"+technican+"】<br/>"));
							continue;
						}else
							tc=tList.get(0);
						
						//封装表头
						Contract contract = new Contract();
						contract.setCode(code);
						contract.setRegistDate(registDate);
						contract.setVariety(vv);
						contract.setPigletQuan(pigletQuan);
						contract.setPigletPrice(pigletPrice);
						contract.setFarmer(fam);
						contract.setCompany(company);
						contract.setFeedFac(fef);
						contract.setStandPigletWeight(standPigletWeight);
						contract.setStandSaleWeight(standSaleWeight);
						contract.setMarketPrice(marketPrice);
						contract.setAllowDiff(allowDiff);
						contract.setFirstDiff(firstDiff);
						contract.setFirstPrice(firstPrice);
						contract.setOverFirstPrice(overFirstPrice);
						contract.setDevelopMan(dm);
						contract.setTechnician(tc);
						
						//封装饲料价格明细
						List<ContractFeedPriced> contractFeedPriceds = new ArrayList<ContractFeedPriced>();
						ContractFeedPriced cfp_drb = new ContractFeedPriced();
						cfp_drb.setFeed(drb);
						cfp_drb.setPrice(drbPrice);
						contractFeedPriceds.add(cfp_drb);
						ContractFeedPriced cfp_rzb = new ContractFeedPriced();
						cfp_rzb.setFeed(rzb);
						cfp_rzb.setPrice(rzbPrice);
						contractFeedPriceds.add(cfp_rzb);
						ContractFeedPriced cfp_zzb = new ContractFeedPriced();
						cfp_zzb.setFeed(zzb);
						cfp_zzb.setPrice(zzbPrice);
						contractFeedPriceds.add(cfp_zzb);
						ContractFeedPriced cfp_551 = new ContractFeedPriced();
						cfp_551.setFeed(_551);
						cfp_551.setPrice(p551);
						contractFeedPriceds.add(cfp_551);
						ContractFeedPriced cfp_552 = new ContractFeedPriced();
						cfp_552.setFeed(_552);
						cfp_552.setPrice(p552);
						contractFeedPriceds.add(cfp_552);
						ContractFeedPriced cfp_553 = new ContractFeedPriced();
						cfp_553.setFeed(_553);
						cfp_553.setPrice(p553);
						contractFeedPriceds.add(cfp_553);
						ContractFeedPriced cfp_554 = new ContractFeedPriced();
						cfp_554.setFeed(_554);
						cfp_554.setPrice(p554);
						contractFeedPriceds.add(cfp_554);
						
						ContractFeedPriced cfp_drbs = new ContractFeedPriced();
						cfp_drbs.setFeed(drbs);
						cfp_drbs.setPrice(drbPrice);
						contractFeedPriceds.add(cfp_drbs);
						ContractFeedPriced cfp_rzbs = new ContractFeedPriced();
						cfp_rzbs.setFeed(rzbs);
						cfp_rzbs.setPrice(rzbPrice);
						contractFeedPriceds.add(cfp_rzbs);
						ContractFeedPriced cfp_zzbs = new ContractFeedPriced();
						cfp_zzbs.setFeed(zzbs);
						cfp_zzbs.setPrice(zzbPrice);
						contractFeedPriceds.add(cfp_zzbs);
						ContractFeedPriced cfp_551s = new ContractFeedPriced();
						cfp_551s.setFeed(_551s);
						cfp_551s.setPrice(p551);
						contractFeedPriceds.add(cfp_551s);
						ContractFeedPriced cfp_552s = new ContractFeedPriced();
						cfp_552s.setFeed(_552s);
						cfp_552s.setPrice(p552);
						contractFeedPriceds.add(cfp_552s);
						ContractFeedPriced cfp_553s = new ContractFeedPriced();
						cfp_553s.setFeed(_553s);
						cfp_553s.setPrice(p553);
						contractFeedPriceds.add(cfp_553s);
						ContractFeedPriced cfp_554s = new ContractFeedPriced();
						cfp_554s.setFeed(_554s);
						cfp_554s.setPrice(p554);
						contractFeedPriceds.add(cfp_554s);
						
						//封装销售定价明细
						List<ContractPigPriced> contractPigPriceds = new ArrayList<ContractPigPriced>();
						ContractPigPriced cpp_a = new ContractPigPriced();
						cpp_a.setPigLevel(new BussinessEleDetail("A"));
						cpp_a.setPrice(zp);
						contractPigPriceds.add(cpp_a);
						ContractPigPriced cpp_b = new ContractPigPriced();
						cpp_b.setPigLevel(new BussinessEleDetail("B"));
						cpp_b.setPrice(cyj);
						contractPigPriceds.add(cpp_b);
						ContractPigPriced cpp_c = new ContractPigPriced();
						cpp_c.setPigLevel(new BussinessEleDetail("C"));
						cpp_c.setPrice(cej);
						contractPigPriceds.add(cpp_c);
						ContractPigPriced cpp_o = new ContractPigPriced();
						cpp_o.setPigLevel(new BussinessEleDetail("O"));
						cpp_o.setPrice(jwp);
						contractPigPriceds.add(cpp_o);
						
						contract.setFeedPriced(contractFeedPriceds);
						contract.setPigPriced(contractPigPriceds);
						
						contracts.add(contract);
					}
				}
				if(sb.length() > 0)
					throw new SystemException(sb.toString());
				if(contracts.isEmpty())
					throw new SystemException("无可用数据导入");
				save(contracts);
				//审核
				String ids = "";
				for(Contract c : contracts){
					String id = c.getId();
					ids = ids+id+",";
				}
				String[] idArray = ids.split(",");
				check(idArray);
			}else
				throw new SystemException("无可用数据导入");
		}
		return contracts;
	}
}
