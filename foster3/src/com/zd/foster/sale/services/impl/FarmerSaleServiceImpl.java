package com.zd.foster.sale.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
import com.zd.epa.bussobj.services.IBussinessEleDetailService;
import com.zd.epa.exception.SystemException;
import com.zd.epa.permission.entity.Users;
import com.zd.epa.utils.ArithUtil;
import com.zd.epa.utils.ExcelUtil;
import com.zd.epa.utils.Pager;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.epa.utils.SysContainer;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.CustVender;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.services.ICustVenderService;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.breed.services.IBatchService;
import com.zd.foster.contract.entity.Contract;
import com.zd.foster.contract.entity.ContractPigPriced;
import com.zd.foster.contract.services.IContractPigPricedService;
import com.zd.foster.contract.services.IContractService;
import com.zd.foster.sale.dao.IFarmerSaleDao;
import com.zd.foster.sale.entity.CompanySale;
import com.zd.foster.sale.entity.FarmerSale;
import com.zd.foster.sale.entity.FarmerSaleDtl;
import com.zd.foster.sale.entity.OutPigsty;
import com.zd.foster.sale.services.ICompanySaleService;
import com.zd.foster.sale.services.IFarmerSaleDtlService;
import com.zd.foster.sale.services.IFarmerSaleService;
import com.zd.foster.sale.services.IOutPigstyService;

/**
 * 类名：  FarmerSaleServiceImpl
 * 功能：
 * @author DZL
 * @date 2017-7-19下午02:14:10
 * @version 1.0
 * 
 */
public class FarmerSaleServiceImpl extends BaseServiceImpl<FarmerSale, IFarmerSaleDao> implements IFarmerSaleService {

	@Resource
	private IBatchService batchService;
	@Resource
	private IFarmerService farmerService;
	@Resource
	private IFarmerSaleDtlService farmerSaleDtlService;
	@Resource
	private ICompanySaleService companySaleService;
	@Resource
	private IOutPigstyService outPigstyService;
	@Resource
	private IBussinessEleDetailService bussinessEleDetailService;
	@Resource
	private ICustVenderService custVenderService;
	@Resource
	private IContractService contractService;
	@Resource
	private IContractPigPricedService contractPigPricedService;
	
	
	/**
	 * 功能:分页查询
	 * 重写:DZL
	 * 2017-7-27
	 */
	@Override
	public void selectAll(FarmerSale entity, Pager<FarmerSale> page) throws Exception {
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
	 * 保存合同
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Mar 22, 2013 10:36:54 AM<br/>
	 * @param entity 被保存的实体
	 * @return <br/>
	 * @see com.zhongpin.pap.services.IBaseService#save(com.zhongpin.pap.entity.BaseEntity)
	 */
	public Object save(FarmerSale entity) throws Exception{
		if(entity == null)
			throw new SystemException("对象不允许为空");
		if(entity.getFarmer()==null || entity.getFarmer().getId()==null || "".equals(entity.getFarmer().getId()))
			throw new SystemException("所属代养户不允许为空");
		if(entity.getBatch()==null || entity.getBatch().getId()==null || "".equals(entity.getBatch().getId()))
			throw new SystemException("养殖批次不允许为空");
		//生猪头数，重量不能够为空或为0 金额不能为空
		if(entity.getTotalQuan()==null || "".equals(entity.getTotalQuan()))
			throw new SystemException("销售总头数不允许为空");
		if(entity.getTotalWeight()==null || "".equals(entity.getTotalWeight()))
			throw new SystemException("销售总重量不允许为空");
		if(entity.getTotalMoney()==null || "".equals(entity.getTotalMoney()))
			throw new SystemException("销售总金额不允许为空");
		if(ArithUtil.comparison(entity.getTotalQuan(), "0")<1)
			throw new SystemException("销售总头数不允许为0或负数");
		if(ArithUtil.comparison(entity.getTotalWeight(), "0")<1)
			throw new SystemException("销售总重量不允许为0或负数");
		//公司销售信息验证
		if(entity.getQuantity()==null || "".equals(entity.getQuantity()))
			throw new SystemException("公司销售头数不允许为空");
		if(entity.getWeight()==null || "".equals(entity.getWeight()))
			throw new SystemException("公司销售重量不允许为空");
		if(entity.getAmount()==null || "".equals(entity.getAmount()))
			throw new SystemException("公司销售金额不允许为空");
		
		if(ArithUtil.comparison(entity.getQuantity(), "0")<1)
			throw new SystemException("公司销售头数不允许为0或负数");
		if(ArithUtil.comparison(entity.getWeight(), "0")<1)
			throw new SystemException("公司销售重量不允许为0或负数");
//		if(ArithUtil.comparison(entity.getAmount(), "0")<1)
//			throw new SystemException("公司销售金额不允许为0或负数");
		
		//验证出栏单不能为空，并且出栏单只能使用一次
		if(entity.getOutPigsty() == null || entity.getOutPigsty().getId()==null || "".equals(entity.getOutPigsty().getId()))
			throw new SystemException("请选择出栏单");
		else{
			List<FarmerSale> fsale = super.selectBySingletAll("outPigsty.id", entity.getOutPigsty().getId());
			if(fsale != null && !fsale.isEmpty()){
				String v = "";
				for(int i=0;i<fsale.size();i++){
					v += fsale.get(i).getId()+",";
				}
				throw new SystemException("该出栏单已经在单据【"+v+"】中使用");
			}
			//验证头数
			OutPigsty op = outPigstyService.selectById(entity.getOutPigsty().getId());
			if(op==null)
				throw new SystemException("未找到出栏单");
			if(!"1".equals(op.getCheckStatus()))
				throw new SystemException("出栏单未审核");
			String opNum = op.getQuantity();
			if(opNum==null || !PapUtil.checkDouble(opNum))
				throw new SystemException("出栏头数有误");
			else if(ArithUtil.comparison(opNum, entity.getTotalQuan()) != 0)
				throw new SystemException("出栏头数与销售头数不一致");
		}
		
		//验证销售总头数必须小于再养彼此当前生猪总头数
		//1.获取批次对象
		Batch batch = batchService.selectById(entity.getBatch().getId());
		Contract c = batch.getContract();
		String code = "";
		if(c != null && c.getStatus() != null)
			code = c.getStatus().getDcode();
		if(!"BREED".equals(code))
			throw new SystemException("合同不是养殖状态，不允许销售");
		
		//获取公司
		Farmer farmer = farmerService.selectById(entity.getFarmer().getId());
		entity.setCompany(farmer.getCompany());
		
		//获取明细
		List<FarmerSaleDtl> fdtl = entity.getDetails();
		if(fdtl == null || fdtl.isEmpty())
			throw new SystemException("销售明细不允许为空");
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<fdtl.size();i++){
			FarmerSaleDtl fd = fdtl.get(i);
			//验证 单价不能为空，并且是数字
			if(fd.getActualPrice()==null || !PapUtil.checkDouble(fd.getActualPrice())){
				sb.append("第【"+(i+1)+"】交易价不能为空并必须为数值<br>");
				continue;
			}
			//验证头数不能为空，数量不能为0或负数
			if(fd.getQuantity()==null || !PapUtil.isInt(fd.getQuantity())){
				sb.append("第【"+(i+1)+"】销售头数不能为空并必须为数值<br>");
				continue;
			}
			if(ArithUtil.comparison(fd.getQuantity(), "0")<1){
				sb.append("第【"+(i+1)+"】销售头数不能为0或负值<br>");
				continue;
			}
			//验证重量不能为空，或0负数
			if(fd.getWeight()==null || !PapUtil.checkDouble(fd.getWeight())){
				sb.append("第【"+(i+1)+"】销售重量不能为空并必须为数值<br>");
				continue;
			}
			if(ArithUtil.comparison(fd.getWeight(), "0")<1){
				sb.append("第【"+(i+1)+"】销售重量不能为0或负值<br>");
				continue;
			}
			//验证金额不能为空
			if(fd.getAmount()==null || !PapUtil.checkDouble(fd.getAmount())){
				sb.append("第【"+(i+1)+"】销售金额不能为空并必须为数值<br>");
				continue;
			}
			fd.setFarmerSale(entity);
		}
		if(sb.length() > 0)
			throw new SystemException(sb.toString());
		
		//保存
		entity.setIsBalance("N");
		entity.setCheckStatus("0");
		Object obj = super.save(entity);
		farmerSaleDtlService.save(fdtl);
		return obj;
	}
	/**
	 * 修改
	 * DZL
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public int updateHql(FarmerSale entity)throws Exception{
		if(entity == null)
			throw new SystemException("对象不允许为空");
		if(entity.getFarmer()==null || entity.getFarmer().getId()==null || "".equals(entity.getFarmer().getId()))
			throw new SystemException("所属代养户不允许为空");
		if(entity.getBatch()==null || entity.getBatch().getId()==null || "".equals(entity.getBatch().getId()))
			throw new SystemException("养殖批次不允许为空");
		//生猪头数，重量不能够为空或为0 金额不能为空
		if(entity.getTotalQuan()==null || "".equals(entity.getTotalQuan()))
			throw new SystemException("销售总头数不允许为空");
		if(entity.getTotalWeight()==null || "".equals(entity.getTotalWeight()))
			throw new SystemException("销售总重量不允许为空");
		if(entity.getTotalMoney()==null || "".equals(entity.getTotalMoney()))
			throw new SystemException("销售总金额不允许为空");
		if(ArithUtil.comparison(entity.getTotalQuan(), "0")<1)
			throw new SystemException("销售总头数不允许为0或负数");
		if(ArithUtil.comparison(entity.getTotalWeight(), "0")<1)
			throw new SystemException("销售总重量不允许为0或负数");
		
		//公司销售信息验证
		if(entity.getQuantity()==null || "".equals(entity.getQuantity()))
			throw new SystemException("公司销售头数不允许为空");
		if(entity.getWeight()==null || "".equals(entity.getWeight()))
			throw new SystemException("公司销售重量不允许为空");
		if(entity.getAmount()==null || "".equals(entity.getAmount()))
			throw new SystemException("公司销售金额不允许为空");
		
		if(ArithUtil.comparison(entity.getQuantity(), "0")<1)
			throw new SystemException("公司销售头数不允许为0或负数");
		if(ArithUtil.comparison(entity.getWeight(), "0")<1)
			throw new SystemException("公司销售重量不允许为0或负数");
		if(ArithUtil.comparison(entity.getAmount(), "0")<1)
			throw new SystemException("公司销售金额不允许为0或负数");
		
		//验证出栏单不能为空，并且出栏单只能使用一次
		if(entity.getOutPigsty() == null || entity.getOutPigsty().getId()==null || "".equals(entity.getOutPigsty().getId()))
			throw new SystemException("请选择出栏单");
		else{
			
			SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
			sqlMap.put("outPigsty.id", "=", entity.getOutPigsty().getId());
			sqlMap.put("id", "<>", entity.getId());
			List<FarmerSale> fsale = super.selectHQL(sqlMap);
			if(fsale != null && !fsale.isEmpty()){
				String v = "";
				for(int i=0;i<fsale.size();i++){
					v += fsale.get(i).getId()+",";
				}
				throw new SystemException("该出栏单已经在单据【"+v+"】中使用");
			}
			//验证头数
			OutPigsty op = outPigstyService.selectById(entity.getOutPigsty().getId());
			if(op==null)
				throw new SystemException("未找到出栏单");
			if(!"1".equals(op.getCheckStatus()))
				throw new SystemException("出栏单未审核");
			String opNum = op.getQuantity();
			if(opNum==null || !PapUtil.checkDouble(opNum))
				throw new SystemException("出栏头数有误");
			else if(ArithUtil.comparison(opNum, entity.getTotalQuan()) != 0)
				throw new SystemException("出栏头数与销售头数不一致");
		}
		
		//验证销售总头数必须小于再养彼此当前生猪总头数
		//1.获取批次对象
		Batch batch = batchService.selectById(entity.getBatch().getId());
		Contract c = batch.getContract();
		String code = "";
		if(c != null && c.getStatus() != null)
			code = c.getStatus().getDcode();
		if(!"BREED".equals(code))
			throw new SystemException("合同不是养殖状态，不允许销售");
		
		//获取公司
		Farmer farmer = farmerService.selectById(entity.getFarmer().getId());
		entity.setCompany(farmer.getCompany());
		
		//获取明细
		List<FarmerSaleDtl> fdtl = entity.getDetails();
		if(fdtl == null || fdtl.isEmpty())
			throw new SystemException("销售明细不允许为空");
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<fdtl.size();i++){
			FarmerSaleDtl fd = fdtl.get(i);
			//验证 单价不能为空，并且是数字
			if(fd.getActualPrice()==null || !PapUtil.checkDouble(fd.getActualPrice())){
				sb.append("第【"+(i+1)+"】交易价不能为空并必须为数值\n");
				continue;
			}
			//验证头数不能为空，数量不能为0或负数
			if(fd.getQuantity()==null || !PapUtil.isInt(fd.getQuantity())){
				sb.append("第【"+(i+1)+"】销售头数不能为空并必须为数值\n");
				continue;
			}
			if(ArithUtil.comparison(fd.getQuantity(), "0")<1){
				sb.append("第【"+(i+1)+"】销售头数不能为0或负值\n");
				continue;
			}
			//验证重量不能为空，或0负数
			if(fd.getWeight()==null || !PapUtil.checkDouble(fd.getWeight())){
				sb.append("第【"+(i+1)+"】销售重量不能为空并必须为数值\n");
				continue;
			}
			if(ArithUtil.comparison(fd.getWeight(), "0")<1){
				sb.append("第【"+(i+1)+"】销售重量不能为0或负值\n");
				continue;
			}
			//验证金额不能为空
			if(fd.getAmount()==null || !PapUtil.checkDouble(fd.getAmount())){
				sb.append("第【"+(i+1)+"】销售金额不能为空并必须为数值\n");
				continue;
			}
		}
		if(sb.length() > 0)
			throw new SystemException(sb.toString());
		//修改表头
		FarmerSale fs = super.selectById(entity.getId());
		fs.setBatch(batch);
		fs.setFarmer(farmer);
		fs.setCompany(farmer.getCompany());
		fs.setRegistDate(entity.getRegistDate());
		fs.setTotalQuan(entity.getTotalQuan());
		fs.setTotalWeight(entity.getTotalWeight());
		fs.setTotalMoney(entity.getTotalMoney());
		
		fs.setBuyer(entity.getBuyer());
		fs.setQuantity(entity.getQuantity());
		fs.setWeight(entity.getWeight());
		fs.setAmount(entity.getAmount());
		fs.setTcost(entity.getTcost());
		
		fs.setOutPigsty(entity.getOutPigsty());
		
		// 删除明细
		Map<String, String> _m = entity.getTempStack();
		//------------------------=============处理饲料定价
		if (null != _m && null != _m.get("deteleIds") && !"".equals(_m.get("deteleIds"))  ) {
			String[] str = _m.get("deteleIds").split(",");
			if(str != null){
				for(String id : str)
					farmerSaleDtlService.deleteById(Integer.parseInt(id));
			}
		}
		List<FarmerSaleDtl> updateSwd = new ArrayList<FarmerSaleDtl>(); 
		List<FarmerSaleDtl> newSwd = new ArrayList<FarmerSaleDtl>(); 
		for(FarmerSaleDtl _f : fdtl){
			if(_f.getId()==null){
				_f.setFarmerSale(fs);
				newSwd.add(_f);
			}else{
				updateSwd.add(_f);
			}
		}
		//2.修改已存在的明细各项值
		for(FarmerSaleDtl fp : updateSwd){
			FarmerSaleDtl _fp = farmerSaleDtlService.selectById(fp.getId());
			_fp.setActualPrice(fp.getActualPrice());
			_fp.setQuantity(fp.getQuantity());
			_fp.setWeight(fp.getWeight());
			_fp.setAmount(fp.getAmount());
		}
		//3.保存新添加的明细
		farmerSaleDtlService.save(newSwd);
			
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
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("farmerSale.id", "in", PapUtil.arrayToSQLStr((String[])PK));
		farmerSaleDtlService.delete(sqlMap);
		return super.deleteByIds(PK);
	}
	
	/**
	 * 功能：审核<br/>
	 *
	 * @author DZL 审核销售单审核 第一 验证批次是不是已经结算还有合同是不是已经完成，合同必须是养殖状态，
	 *             批次未结算才能进行审核 第二 验证生猪销售头数与批次当前头数，第三 审核完成后，批次头数相减，并更新批次上销售头数
	 * @version 2015-3-9 下午05:28:16 <br/>
	 */
	public void check(String[] idArr)throws Exception{
		if(idArr == null || idArr.length==0)
			throw new SystemException("未发现要审核的单据");
		Users u = SysContainer.get();
		StringBuffer sb = new StringBuffer();
		for(String s : idArr){
			FarmerSale f = super.selectById(s);
			if(f == null){
				sb.append("编号为【"+s+"】的销售单不存在\n");
				continue;
			}else{
				//验证合同状态以及批次状态
				Batch batch = f.getBatch();
				Contract c = batch.getContract();
				String code ="";
				if(c != null && c.getStatus() != null)
					code = c.getStatus().getDcode();
				if(!"BREED".equals(code)){
					sb.append("编号【"+batch.getBatchNumber()+"】的合同不是养殖状态，不允许审核\n");
					continue;
				}
				if("Y".equals(batch.getIsBalance())){
					sb.append("批次【"+batch.getBatchNumber()+"】生猪已经进行过结算\n");
					continue;
				}
				//验证出栏单不能为空，并且出栏单只能使用一次
				if(f.getOutPigsty() == null || f.getOutPigsty().getId()==null || "".equals(f.getOutPigsty().getId())){
					sb.append("请设置出栏单\n");
					continue;
				}else{
					SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
					sqlMap.put("outPigsty.id", "=", f.getOutPigsty().getId());
					sqlMap.put("id", "<>", f.getId());
					List<FarmerSale> fsale = super.selectHQL(sqlMap);
					if(fsale != null && !fsale.isEmpty()){
						String v = "";
						for(int i=0;i<fsale.size();i++){
							v += fsale.get(i).getId()+",";
						}
						throw new SystemException("该出栏单已经在单据【"+v+"】中使用");
					}
					
					//验证头数
					OutPigsty op = outPigstyService.selectById(f.getOutPigsty().getId());
					if(op==null){
						sb.append("未找到出栏单\n");
						continue;
					}else if(!"1".equals(op.getCheckStatus())){
						sb.append("出栏单未审核\n");
						continue;
					}
					String opNum = op.getQuantity();
					if(opNum==null || !PapUtil.checkDouble(opNum)){
						sb.append("出栏头数有误\n");
						continue;
					}else if(ArithUtil.comparison(opNum, f.getTotalQuan()) != 0){
						sb.append("出栏头数与销售头数不一致\n");
						continue;
					}
					//回填出栏单
					op.setFarmerSale(s);
				}
				
				//变更状态
				//改变审核状态
				f.setCheckStatus("1");
				f.setCheckUser(u.getUserRealName());
				f.setCheckDate(PapUtil.date(new Date()));
				
				//生成公司销售单
				CompanySale cs = companySaleService.selectBySinglet("farmerSale", s);
				if(cs != null){
					cs.setBatch(batch);
					cs.setFarmer(f.getFarmer());
					cs.setCompany(f.getCompany());
					cs.setRegistDate(f.getRegistDate());
					cs.setQuantity(f.getQuantity());
					cs.setWeight(f.getWeight());
					cs.setAmount(f.getAmount());
					cs.setBuyer(f.getBuyer());
					cs.setTcost(f.getTcost());
					cs.setFarmerSale(s);
					cs.setIsBalance("N");
					
					cs.setCheckDate(PapUtil.date(new Date()));
					cs.setCheckStatus("1");
					cs.setCheckUser(u.getUserRealName());
					
				}else{
					cs = new CompanySale();
					cs.setBatch(batch);
					cs.setFarmer(f.getFarmer());
					cs.setCompany(f.getCompany());
					cs.setRegistDate(f.getRegistDate());
					cs.setQuantity(f.getQuantity());
					cs.setWeight(f.getWeight());
					cs.setAmount(f.getAmount());
					cs.setBuyer(f.getBuyer());
					cs.setTcost(f.getTcost());
					cs.setFarmerSale(s);
					cs.setIsBalance("N");
					
					cs.setCheckDate(PapUtil.date(new Date()));
					cs.setCheckStatus("1");
					cs.setCheckUser(u.getUserRealName());
					
					companySaleService.saveAuto(cs);
				}
			}
		}
		if(sb.length()>0)
			throw new SystemException(sb.toString());
	}
	/**
	 * 功能：撤销<br/>
	 *
	 * @author DZL 撤销时需要验证合同是不是已经完成，批次是不是已经结算，第二，跟新批次当前头数 销售头数
	 * @version 2015-3-9 下午05:28:16 <br/>
	 */
	public void cancelCheck(String id)throws Exception{
		if(id==null || "".equals(id))
			throw new SystemException("未发现要撤销的销售单据");
		FarmerSale f = super.selectById(id);
		if(f == null)
			throw new SystemException("编号为【"+id+"】的销售单不存在");
		//验证单据自身已经结算不允许撤销
		if("Y".equals(f.getIsBalance()))
			throw new SystemException("编号为【"+id+"】的销售单已经结算不允许撤销");
		//验证合同状态以及批次状态
		Batch batch = f.getBatch();
		Contract c = batch.getContract();
		String code ="";
		if(c != null && c.getStatus() != null)
			code = c.getStatus().getDcode();
		if(!"BREED".equals(code))
			throw new SystemException("编号【"+batch.getBatchNumber()+"】的合同不是养殖状态，不允许撤销\n");
		if("Y".equals(batch.getIsBalance()))
			throw new SystemException("批次【"+batch.getBatchNumber()+"】生猪已经进行过结算\n");
		
		//删除公司销售单
		companySaleService.deleteBySingletAll("farmerSale", id);
		//去除掉回填到出栏单上的编码
		OutPigsty op = f.getOutPigsty();
		if(op != null)
			op.setFarmerSale(null);
		
		//变更状态
		//改变审核状态
		f.setCheckStatus("0");
		f.setCheckUser(null);
		f.setCheckDate(null);
	}
	
	
	/**
	 * 通过ids查找对象
	 * @Description:TODO
	 * @param id
	 * @return
	 * @throws Exception
	 * FarmerSale
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-30 上午09:51:16
	 */
	public FarmerSale selectByIds(String id)throws Exception{
		String[] ids = id.split(",");
		FarmerSale fs = new FarmerSale();
		String quan = "0";
		String weight = "0";
		if(ids!=null && ids.length>0){
			for(String pk : ids){
				FarmerSale fse = selectById(pk);
				if(fse!=null){
					quan = ArithUtil.add(quan, fse.getTotalQuan(),0);
					weight = ArithUtil.add(weight, fse.getTotalWeight());
				}
			}
		}
		
		fs.setTotalQuan(quan);
		fs.setTotalWeight(weight);
		
		return fs;
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
			fileInput = new FileInputStream(realPath + "/WEB-INF/template/" + "farmersale.xlsx");
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new SystemException("系统找不到指定路径下的模板文件！");
		}
		return fileInput;
		
	}
	
	/**
	 * 导入销售单
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-5 下午03:26:07
	 */
	public List<FarmerSale> operateFile(File file, Company company, Object... objects)throws Exception{
		if(company==null || company.getId()==null || "".equals(company.getId()))
			throw new SystemException("请选择公司");
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		
		List<FarmerSale> farmerSales = new ArrayList<FarmerSale>();
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
						//验证是否为空
						String farmer = ExcelUtil.checkCellValue(row.getCell(0), i + 1, "代养户", true, sb);
						String batch = ExcelUtil.checkCellValue(row.getCell(1), i + 1, "批次", true, sb);
						String registDate = ExcelUtil.checkCellValue(row.getCell(2), i + 1, "销售日期", true, sb);
						String outType = ExcelUtil.checkCellValue(row.getCell(3), i + 1, "销售类型", true, sb);
						String totalQuan = ExcelUtil.checkCellValue(row.getCell(4), i + 1, "出栏头数", true, sb);
						String totalWeight = ExcelUtil.checkCellValue(row.getCell(5), i + 1, "出栏重量(KG)", true, sb);
						String pigLevel = ExcelUtil.checkCellValue(row.getCell(6), i + 1, "销售级别", true, sb);
						String totalMoney = ExcelUtil.checkCellValue(row.getCell(7), i + 1, "出栏金额(元)", true, sb);
						String buyer = ExcelUtil.checkCellValue(row.getCell(8), i + 1, "收购商", true, sb);
						String quantity = ExcelUtil.checkCellValue(row.getCell(9), i + 1, "到厂头数", true, sb);
						String weight = ExcelUtil.checkCellValue(row.getCell(10), i + 1, "到厂重量(KG)", true, sb);
						String tcost = ExcelUtil.checkCellValue(row.getCell(11), i + 1, "运输费用(元)", true, sb);
						String amount = ExcelUtil.checkCellValue(row.getCell(12), i + 1, "销售金额(元)", true, sb);
						//验证
						//验证代养户
						Farmer far = null;
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("name", "=", farmer);
						List<Farmer> farmers = farmerService.selectHQL(sqlMap);
						sqlMap.clear();
						if(farmers==null || farmers.isEmpty())
							throw new SystemException("不存在代养户【"+farmer+"】");
						else
							far = farmers.get(0);
						//验证批次
						Batch bat = null;
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("farmer.name", "=", farmer);
						sqlMap.put("batchNumber", "=", batch);
						List<Batch> batchs = batchService.selectHQL(sqlMap);
						sqlMap.clear();
						if(batchs==null || batchs.isEmpty())
							throw new SystemException("不存在代养户【"+farmer+"】批次【"+batch+"】");
						else
							bat = batchs.get(0);
						//销售级别
						BussinessEleDetail pigl = null;
						sqlMap.put("value", "=", pigLevel);
						List<BussinessEleDetail> pigls = bussinessEleDetailService.selectHQL(sqlMap);
						sqlMap.clear();
						if(pigls==null || pigls.isEmpty())
							throw new SystemException("不存在销售级别【"+pigLevel+"】");
						else
							pigl = pigls.get(0);
						//收购商
						CustVender cv = null;
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("name", "=", buyer);
						sqlMap.put("custVenderType.dcode", "=", "PIGDEALER");
						List<CustVender> buyers = custVenderService.selectHQL(sqlMap);
						sqlMap.clear();
						if(buyers==null || buyers.isEmpty())
							throw new SystemException("不存在收购商【"+buyer+"】");
						else
							cv = buyers.get(0);
						
						//封装出栏单
						OutPigsty outPig = new OutPigsty();
						outPig.setBatch(bat);
						outPig.setFarmer(far);
						outPig.setCompany(company);
						outPig.setRegistDate(registDate);
						outPig.setQuantity(totalQuan);
						outPig.setWeight(totalWeight);
						if("正常".equals(outType))
							outPig.setSaleType("Q");
						if("淘汰".equals(outType))
							outPig.setSaleType("E");
						
						String outid = null;
						try{
							outid=(String) outPigstyService.save(outPig);
						}catch(Exception e){
							throw new SystemException("第"+(i+1)+"行"+e.getMessage());
						}
						String[] outIds = {outid};
						try{
							outPigstyService.check(outIds);
						}catch(Exception e){
							throw new SystemException("第"+(i+1)+"行"+e.getMessage());
						}
						
						//封装代养户销售单
						FarmerSale fs = new FarmerSale();
						fs.setBatch(bat);
						fs.setFarmer(far);
						fs.setCompany(company);
						fs.setRegistDate(registDate);
						fs.setTotalQuan(totalQuan);
						fs.setTotalWeight(ArithUtil.scale(totalWeight, 2));
						fs.setTotalMoney(totalMoney);
						fs.setQuantity(quantity);
						fs.setWeight(ArithUtil.scale(weight, 2));
						fs.setAmount(ArithUtil.scale(amount, 2));
						fs.setBuyer(cv);
						fs.setTcost(ArithUtil.scale(tcost, 2));
						fs.setOutPigsty(outPig);
						
						//封装代养户销售单明细
						FarmerSaleDtl fsd = new FarmerSaleDtl();
						fsd.setPigLevel(pigl);
						
						Contract contractv = null;
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("id", "=", bat.getContract().getId());
						List<Contract> contracts = contractService.selectHQL(sqlMap);
						sqlMap.clear();
						if(contracts==null || contracts.isEmpty())
							throw new SystemException("未找到批次【"+bat.getId()+"】对应的合同【"+bat.getContract().getId()+"】");
						else
							contractv = contracts.get(0);
						ContractPigPriced cpp = null;
						sqlMap.put("contract.id", "=", contractv.getId());
						sqlMap.put("pigLevel.dcode", "=", pigl.getDcode());
						List<ContractPigPriced> cpps = contractPigPricedService.selectHQL(sqlMap);
						sqlMap.clear();
						if(cpps==null || cpps.isEmpty())
							throw new SystemException("未找到合同【"+contractv.getId()+"】的【"+pigl.getValue()+"】单价");
						else
							cpp = cpps.get(0);
						
						fsd.setContPrice(cpp.getPrice());
						String price = ArithUtil.div(totalMoney, totalWeight, 2);
						fsd.setActualPrice(price);
						fsd.setQuantity(totalQuan);
						fsd.setWeight(totalWeight);
						fsd.setAmount(totalMoney);
						List<FarmerSaleDtl> farmerSaleDtls = new ArrayList<FarmerSaleDtl>();
						farmerSaleDtls.add(fsd);
						
						fs.setDetails(farmerSaleDtls);
						farmerSales.add(fs);
						
						save(fs);
						String[] fsid = {fs.getId()};
						try{
							check(fsid);
						}catch(Exception e){
							throw new SystemException("第"+(i+1)+"行"+e.getMessage());
						}
					}
				}
				if(sb.length() > 0)
					throw new SystemException(sb.toString());
				
			}else
				throw new SystemException("无可用数据导入");
		}
		return farmerSales;
	}
	
	
	
	
	
	
	
	
}
