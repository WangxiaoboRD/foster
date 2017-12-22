/**
 * 功能:
 * @author:wxb
 * @data:2017-9-8下午01:34:30
 * @file:DrugTransferServiceImpl.java
 */
package com.zd.foster.warehouse.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
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
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.contract.entity.Contract;
import com.zd.foster.contract.services.IContractService;
import com.zd.foster.material.entity.Drug;
import com.zd.foster.material.services.IDrugService;
import com.zd.foster.warehouse.dao.IDrugTransferDao;
import com.zd.foster.warehouse.entity.DrugBill;
import com.zd.foster.warehouse.entity.DrugBillDtl;
import com.zd.foster.warehouse.entity.DrugTransfer;
import com.zd.foster.warehouse.entity.DrugTransferDtl;
import com.zd.foster.warehouse.entity.DrugWarehouseFar;
import com.zd.foster.warehouse.services.IDrugBillService;
import com.zd.foster.warehouse.services.IDrugTransferDtlService;
import com.zd.foster.warehouse.services.IDrugTransferService;
import com.zd.foster.warehouse.services.IDrugWarehouseFarService;

/**
 * 类名：  DrugTransferServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-9-8下午01:34:30
 * @version 1.0
 * 
 */
public class DrugTransferServiceImpl extends BaseServiceImpl<DrugTransfer, IDrugTransferDao> implements
		IDrugTransferService {
	@Resource
	private IDrugTransferDtlService drugTransferDtlService;
	@Resource
	private IContractService contractService;
	@Resource
	private IDrugWarehouseFarService drugWarehouseFarService;
	@Resource
	private IDrugService drugService;
	@Resource
	private IDrugBillService drugBillService;
	@Resource
	private IFarmerService farmerService;
	/**
	 * 
	 * 功能:分页查询
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#selectAll(com.zd.epa.base.BaseEntity, com.zd.epa.utils.Pager)
	 */
	@Override
	public void selectAll(DrugTransfer entity, Pager<DrugTransfer> page) throws Exception {
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		Map<String, String> ts = entity.getTempStack();
		if (null != ts) {
			//开始时间 
			String startDate = ts.get("startTime");
			if (null != startDate && !"".equals(startDate)) {
				sqlMap.put("registDate", ">=", startDate+" 00:00:00");
			}
			//结束时间
			String endDate = ts.get("endTime");
			if (null != endDate && !"".equals(endDate)) {
				sqlMap.put("registDate", "<=", endDate+" 23:59:59");
			}
		}
		dao.selectByConditionHQL(entity, sqlMap, page);
	}
	
	
	/**
	 * 
	 * 功能:保存转接单
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#save(com.zd.epa.base.BaseEntity)
	 */
	public Object save(DrugTransfer entity) throws Exception{
		//1验证表头
		//1.1表头是否为空
		if(entity == null)
			throw new SystemException("对象不能为空");
		//1.3转出代养户是否为空
		if(entity.getOutFarmer()==null || entity.getOutFarmer().getId()==null || "".equals(entity.getOutFarmer().getId()))
			throw new SystemException("转出代养户不能为空！");
		//1.3获取养殖公司
		Farmer farmer = farmerService.selectById(entity.getOutFarmer().getId());
		entity.setCompany(farmer.getCompany());
		//1.4转出批次是否为空
		if(entity.getOutBatch()==null || entity.getOutBatch().getId()==null || "".equals(entity.getOutBatch().getId()))
			throw new SystemException("转出批次不能为空！");
		//1.5转接时间是否为空
		if(entity.getRegistDate()==null || "".equals(entity.getRegistDate()))
			throw new SystemException("转接时间不能为空！");
		//1.6转入代养户是否为空
		if(entity.getInFarmer()==null || entity.getInFarmer().getId()==null || "".equals(entity.getInFarmer().getId()))
			throw new SystemException("转入代养户不能为空！");
		//1.7转入批次是否为空
		if(entity.getInBatch()==null || entity.getInBatch().getId()==null || "".equals(entity.getInBatch().getId()))
			throw new SystemException("转入批次不能为空！");
		//2验证明细
		List<DrugTransferDtl> ftddList = entity.getDetails();
		//2.1明细是否为空
		if(ftddList == null || ftddList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<ftddList.size();i++){
			DrugTransferDtl ftd = ftddList.get(i);
			//2.2.1验证数量是否为空，是否为非正数
			if(ftd.getQuantity()==null || "".equals(ftd.getQuantity()))
				buff.append("第"+(i+1)+"行数量不能为空<br>");
			else if(ArithUtil.comparison(ftd.getQuantity(), "0")<=0)
					buff.append("第"+(i+1)+"行数值必须为正数！<br>");
			//2.2.2检查副单位数量是否是"",如果是设为null
			if("".equals(ftd.getSubQuantity()))
				ftd.setSubQuantity(null);
			ftd.setDrugTransfer(entity);
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		entity.setCheckStatus("0");
		entity.setIsBalance("N");
		Object obj = super.save(entity);
		drugTransferDtlService.save(ftddList);
		return obj;
	}

	/**
	 * 
	 * 功能:修改转接单
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#update(com.zd.epa.base.BaseEntity)
	 */
	public void update(DrugTransfer entity)throws Exception{
		//1验证表头
		//1.1表头是否为空
		if(entity == null)
			throw new SystemException("对象不能为空");
		//1.2养殖公司是否为空
		//1.3转出代养户是否为空
		if(entity.getOutFarmer()==null || entity.getOutFarmer().getId()==null || "".equals(entity.getOutFarmer().getId()))
			throw new SystemException("转出代养户不能为空！");
		//1.4转出批次是否为空
		if(entity.getOutBatch()==null || entity.getOutBatch().getId()==null || "".equals(entity.getOutBatch().getId()))
			throw new SystemException("转出批次不能为空！");
		//1.5转接时间是否为空
		if(entity.getRegistDate()==null || "".equals(entity.getRegistDate()))
			throw new SystemException("入库时间不能为空！");
		//1.6转入代养户是否为空
		if(entity.getInFarmer()==null || entity.getInFarmer().getId()==null || "".equals(entity.getInFarmer().getId()))
			throw new SystemException("转入代养户不能为空！");
		//1.7转入批次是否为空
		if(entity.getInBatch()==null || entity.getInBatch().getId()==null || "".equals(entity.getInBatch().getId()))
			throw new SystemException("转入批次不能为空！");
		//2验证明细
		List<DrugTransferDtl> ftdList = entity.getDetails();
		//2.1明细是否为空
		if(ftdList == null || ftdList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<ftdList.size();i++){
			DrugTransferDtl ftd = ftdList.get(i);
			//2.2.1验证数量是否为空，是否为非正
			if(ftd.getQuantity()==null || "".equals(ftd.getQuantity()))
				buff.append("第"+(i+1)+"行数量不能为空<br>");
			else if(ArithUtil.comparison(ftd.getQuantity(), "0")<=0)
				buff.append("第"+(i+1)+"行数值必须为正数！<br>");
			//2.2.2检查副单位数量是否是"",如果是设为null
			if("".equals(ftd.getSubQuantity()))
				ftd.setSubQuantity(null);
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		// 保存表头
		// 1 、获取数据库里的对象
		DrugTransfer e = super.selectById(entity.getId());
		// 2、将页面数据赋给数据库对象
		e.setInFarmer(entity.getInFarmer());
		e.setInBatch(entity.getInBatch());
		e.setRegistDate(entity.getRegistDate());
		e.setRemark(entity.getRemark());
		//保存明细
		//1、删除明细
		Map<String, String> _m = entity.getTempStack();
		if (null != _m && null != _m.get("deleteIds") && !"".equals(_m.get("deleteIds"))  ) {
			String[] str = _m.get("deleteIds").split(",");
			if(str != null){
				for(String id : str)
					drugTransferDtlService.deleteById(Integer.parseInt(id));
			}
		}
		//2. 新增/修改明细
		List<DrugTransferDtl> updateSwd = new ArrayList<DrugTransferDtl>(); 
		List<DrugTransferDtl> newSwd = new ArrayList<DrugTransferDtl>(); 
		for(DrugTransferDtl p : ftdList){
			if(p.getId()==null){
				p.setDrugTransfer(e);
				newSwd.add(p);
			}
			if(p.getId()!=null){
				updateSwd.add(p);
			}
		}
		//修改	
		for(DrugTransferDtl p : updateSwd){
			DrugTransferDtl ed = drugTransferDtlService.selectById(p.getId());
			ed.setQuantity(p.getQuantity());
			ed.setSubQuantity(p.getSubQuantity()==""?null:p.getSubQuantity());
		}
		updateSwd.clear();
		//添加
		drugTransferDtlService.save(newSwd);
	}
	
	/**
	 * 
	 * 功能:删除
	 * 重写:wxb
	 * 2017-7-28
	 * @see com.zd.epa.base.BaseServiceImpl#deleteByIds(ID[])
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		if(PK==null || PK.length==0)
			throw new SystemException("请选择删除对象");
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("drugTransfer.id", "in", PapUtil.arrayToSQLStr((String[])PK));
		drugTransferDtlService.delete(sqlMap);
		return dao.deleteByIds(PK);
	}


	/**
	 * 
	 * 功能:审核（转出代养户扣库存，转入代养户增加库存，新建转入代养户药品领用单）
	 * 重写:wxb
	 * 2017-9-9
	 * @see com.zd.foster.warehouse.services.IDrugTransferService#check(java.lang.String[])
	 */
	@Override
	public void check(String[] idArr) throws Exception {
		if(idArr == null || idArr.length == 0)
			throw new SystemException("请选择单据");
		Users u = SysContainer.get();
		StringBuffer sb = new StringBuffer();
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		for(String s : idArr){
			//1.获取对象
			DrugTransfer e = super.selectById(s);
			//1.1验证单据转出代养户，转入代养户合同是否在养殖或生效状态
			Contract ct=e.getOutBatch().getContract();
			if(ct.getStatus()==null || !(ct.getStatus().getDcode().equals("BREED")||ct.getStatus().getDcode().equals("EFFECT"))){
				throw new SystemException("单据【"+s+"】转出代养户的合同不在养殖或生效状态<br>");
			}
			Contract ct2=e.getInBatch().getContract();
			if(ct2.getStatus()==null || !(ct2.getStatus().getDcode().equals("BREED")||ct2.getStatus().getDcode().equals("EFFECT"))){
				throw new SystemException("单据【"+s+"】转入代养户的合同不在养殖或生效状态<br>");
			}
			//2.获取明细并遍历明细
			List<DrugTransferDtl> edList = drugTransferDtlService.selectBySingletAll("drugTransfer.id", s);
			if(e==null ||edList==null || edList.size()==0)
				throw new SystemException("单据【"+s+"】对象为空或明细不存在<br>");
			//转出代养户扣库存，转入代养户生成入库单，增加库存
			//2.建立转入代养户领料单表头，明细
			DrugBill fiw=new DrugBill();
			List<DrugBillDtl> fiwdList=new ArrayList<DrugBillDtl>();
			List<DrugWarehouseFar> fwAllList = new ArrayList<DrugWarehouseFar>();
			for(DrugTransferDtl ed : edList){
				//2.1查询转出代养户该药品库存(/***修改：库存带批次**/)
				sqlMap.put("farmer.id", "=", e.getOutFarmer().getId());
				sqlMap.put("drug.id", "=", ed.getDrug().getId());
				sqlMap.put("batch.id", "=", e.getOutBatch().getId()+"");
				List<DrugWarehouseFar> fwList = drugWarehouseFarService.selectHQL(sqlMap);
				sqlMap.clear();
				//
				sqlMap.put("farmer.id", "=", e.getInFarmer().getId());
				sqlMap.put("drug.id", "=", ed.getDrug().getId());
				sqlMap.put("batch.id", "=", e.getInBatch().getId()+"");
				List<DrugWarehouseFar> fwInList = drugWarehouseFarService.selectHQL(sqlMap);
				sqlMap.clear();
				//2.2判断库存是否存在
				if(fwList==null || fwList.size()==0){
					throw new SystemException("单据【"+s+"】药品【"+ed.getDrug().getId()+"】没有库存<br>");
				}else{
					//2.3该饲料库存是否足够出库，足够则减库存,建饲料出库单
					DrugWarehouseFar fw = fwList.get(0);
					if(fw != null){
						//原始数据
						String q_old = fw.getQuantity();
						//入库单数据
						String q_new = ed.getQuantity();
						if(q_old==null || "".equals(q_old))
							q_old = "0";
						if(q_new==null || "".equals(q_new))
							q_new = "0";
						//2.3.1验证库存是否足够出库
						if(ArithUtil.comparison(q_old, q_new)<0){
							throw new SystemException("单据【"+s+"】药品【"+fw.getDrug().getName()+"】库存不够<br>");
						}
						//2.3.3扣库存
						String num = ArithUtil.sub(q_old, q_new,2);
						fw.setQuantity(num);
						//2.3.4添加副单位数量
						Drug drug=drugService.selectById(ed.getDrug().getId());
						String ratio=drug.getRatio();
						if(ratio!=null && !"".equals(ratio))
							fw.setSubQuantity(ArithUtil.div(num, ratio, 2));
						drugWarehouseFarService.save(fw);
						//2.3.5验证转入代养户是否有该药品库存,并添加或更改库存
						if(fwInList==null || fwInList.size()==0){
							DrugWarehouseFar fw2 = new DrugWarehouseFar();
							fw2.setCompany(e.getCompany());
							fw2.setFarmer(e.getInFarmer());
							fw2.setBatch(e.getInBatch());
							fw2.setDrug(ed.getDrug());
							fw2.setQuantity(ed.getQuantity());
							fw2.setSubQuantity(ed.getSubQuantity());
							fwAllList.add(fw2);
						}else{
							//2.2.2库存有该药品库存
							DrugWarehouseFar fw2 = fwInList.get(0);
							if(fw2 != null){
								//原始数据
								String q_old2 = fw2.getQuantity();
								//入库单数据
								String q_new2 = ed.getQuantity();
								if(q_old2==null || "".equals(q_old2))
									q_old2 = "0";
								if(q_new2==null || "".equals(q_new2))
									q_new2 = "0";
								
								String num2 = ArithUtil.add(q_old2, q_new2);
								fw2.setQuantity(num2);
								//添加副单位数量
								Drug drug2=drugService.selectById(ed.getDrug().getId());
								String ratio2=drug2.getRatio();
								if(ratio2!=null && !"".equals(ratio2))
									fw2.setSubQuantity(ArithUtil.div(num2, ratio2, 2));
							}
							drugWarehouseFarService.save(fw2);
						}
						//2.3.5增加领药单明细
						DrugBillDtl fiwd=new DrugBillDtl();
						fiwd.setDrugBill(fiw);
						fiwd.setDrug(ed.getDrug());
						fiwd.setQuantity(ed.getQuantity());
						fiwd.setSubQuantity(ed.getSubQuantity());
						fiwdList.add(fiwd);
					}
				}
			}
			if(fwAllList !=null && fwAllList.size()>0)
				drugWarehouseFarService.save(fwAllList);
			//3.2保存领药单表头和明细
			fiw.setCompany(e.getCompany());
			fiw.setFarmer(e.getInFarmer());
			fiw.setBatch(e.getInBatch());
			fiw.setRegistDate(e.getRegistDate());
			fiw.setDrugTransfer(s);
			fiw.setCheckDate(PapUtil.date(new Date()));
			fiw.setCheckUser(u.getUserRealName());
			fiw.setDetails(fiwdList);
			drugBillService.save(fiw);
			fiw.setCheckStatus("1");
			//4.审核单据 
			e.setCheckDate(PapUtil.date(new Date()));
			e.setCheckStatus("1");
			e.setCheckUser(u.getUserRealName());
		}
		if(sb.length()>0)
			throw new SystemException(sb.toString());
	}
	/**
	 * 
	 * 功能:撤销（撤销转入代养户的库存，增加转出代养户的库存，删除转入代养户的领药单）
	 * 重写:wxb
	 * 2017-9-9
	 * @see com.zd.foster.warehouse.services.IDrugTransferService#cancel(java.lang.String)
	 */
	@Override
	public void cancelCheck(String id) throws Exception {
		if(id == null || "".equals(id))
			throw new SystemException("请选择单据");
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		//1.获取对象
		DrugTransfer e = super.selectById(id);
		if(e.getIsBalance().equals("Y"))
			throw new SystemException("单据【"+id+"】已经结算<br>");
		Contract ct=e.getOutBatch().getContract();
		if(ct.getStatus().getDcode().equals("LOST"))
			throw new SystemException("单据【"+id+"】转出代养户合同已经终止<br>");
		Contract ct2=e.getInBatch().getContract();
		if(ct2.getStatus().getDcode().equals("LOST"))
			throw new SystemException("单据【"+id+"】转入代养户合同已经终止<br>");
		//2.获取明细并遍历
		List<DrugTransferDtl> edList = drugTransferDtlService.selectBySingletAll("drugTransfer.id", id);
		if(e==null ||edList==null || edList.size()==0)
			throw new SystemException("单据【"+id+"】对象为空或明细不存在");
		for(DrugTransferDtl ed : edList){
			//2.1查询饲料的库存(/***修改：库存带批次**/)
			sqlMap.put("farmer.id", "=", e.getInFarmer().getId());
			sqlMap.put("drug.id", "=", ed.getDrug().getId());
			sqlMap.put("batch.id", "=", e.getInBatch().getId()+"");
			List<DrugWarehouseFar> fwList = drugWarehouseFarService.selectHQL(sqlMap);
			sqlMap.clear();
			//
			sqlMap.put("farmer.id", "=", e.getOutFarmer().getId());
			sqlMap.put("drug.id", "=", ed.getDrug().getId());
			sqlMap.put("batch.id", "=", e.getOutBatch().getId()+"");
			List<DrugWarehouseFar> fwOutList = drugWarehouseFarService.selectHQL(sqlMap);
			sqlMap.clear();
			//2.2转入代养户无库存，不允许撤销
			if(fwList==null || fwList.size()==0){
				throw new SystemException("转入代养户库存不够撤销！");
			}else{
				DrugWarehouseFar fw = fwList.get(0);
				if(fw != null){
					//原始数据
					String q_old = fw.getQuantity();
					//入库单数据
					String q_new = ed.getQuantity();
					if(q_old==null || "".equals(q_old))
						q_old = "0";
					if(q_new==null || "".equals(q_new))
						q_new = "0";
					
					String num = ArithUtil.sub(q_old, q_new);
					if(ArithUtil.comparison(num, "0")<0)
						throw new SystemException("转入代养户库存不够撤销！");
					fw.setQuantity(num);
					//添加副单位数量
					Drug drug=drugService.selectById(ed.getDrug().getId());
					String ratio=drug.getRatio();
					if(ratio!=null && !"".equals(ratio))
						fw.setSubQuantity(ArithUtil.div(num, ratio, 2));
					drugWarehouseFarService.save(fw);
					//转出代养户增加库存
					if(fwOutList==null || fwOutList.size()==0){
						DrugWarehouseFar fw2 = new DrugWarehouseFar();
						fw2.setCompany(e.getCompany());
						fw2.setFarmer(e.getInFarmer());
						fw2.setBatch(e.getInBatch());
						fw2.setDrug(ed.getDrug());
						fw2.setQuantity(ed.getQuantity());
						fw2.setSubQuantity(ed.getSubQuantity());
						drugWarehouseFarService.save(fw2);
					}else{
						//2.2.2库存有该饲料库存
						DrugWarehouseFar fw2 = fwOutList.get(0);
						if(fw2 != null){
							//原始数据
							String q_old2 = fw2.getQuantity();
							//入库单数据
							String q_new2 = ed.getQuantity();
							if(q_old2==null || "".equals(q_old2))
								q_old2 = "0";
							if(q_new2==null || "".equals(q_new2))
								q_new2 = "0";
							
							String num2 = ArithUtil.add(q_old2, q_new2);
							fw2.setQuantity(num2);
							//添加副单位数量
							Drug drug2=drugService.selectById(ed.getDrug().getId());
							String ratio2=drug2.getRatio();
							if(ratio2!=null && !"".equals(ratio2))
								fw2.setSubQuantity(ArithUtil.div(num2, ratio2, 2));
							drugWarehouseFarService.save(fw2);
						}
					}
				}
			}
		}
		//5.删除领药单
		DrugBill fiw=drugBillService.selectByHQLSingle("from DrugBill e where e.drugTransfer='"+id+"'");
		drugBillService.deleteById(fiw.getId());
		//5.撤销审核状态
		e.setCheckDate(null);
		e.setCheckStatus("0");
		e.setCheckUser(null);
	}


	
	
	


}
