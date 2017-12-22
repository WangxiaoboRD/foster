/**
 * 功能:
 * @author:wxb
 * @data:2017-9-8上午10:38:18
 * @file:DrugBillServiceImpl.java
 */
package com.zd.foster.warehouse.services.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.permission.entity.Users;
import com.zd.epa.utils.ArithUtil;
import com.zd.epa.utils.ExcelUtil;
import com.zd.epa.utils.JDBCWrapperx;
import com.zd.epa.utils.Pager;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.epa.utils.SysContainer;
import com.zd.epa.utils.TypeUtil;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.entity.FeedFac;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.breed.services.IBatchService;
import com.zd.foster.contract.entity.Contract;
import com.zd.foster.dto.MedicalAnalysis;
import com.zd.foster.material.entity.Drug;
import com.zd.foster.material.services.IDrugService;
import com.zd.foster.warehouse.dao.IDrugBillDao;
import com.zd.foster.warehouse.entity.DrugBill;
import com.zd.foster.warehouse.entity.DrugBillDtl;
import com.zd.foster.warehouse.entity.DrugOutWare;
import com.zd.foster.warehouse.entity.DrugOutWareDtl;
import com.zd.foster.warehouse.entity.DrugWarehouse;
import com.zd.foster.warehouse.entity.DrugWarehouseFar;
import com.zd.foster.warehouse.services.IDrugBillDtlService;
import com.zd.foster.warehouse.services.IDrugBillService;
import com.zd.foster.warehouse.services.IDrugOutWareDtlService;
import com.zd.foster.warehouse.services.IDrugOutWareService;
import com.zd.foster.warehouse.services.IDrugWarehouseFarService;
import com.zd.foster.warehouse.services.IDrugWarehouseService;


/**
 * 类名：  DrugBillServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-9-8上午10:38:18
 * @version 1.0
 * 
 */
public class DrugBillServiceImpl extends BaseServiceImpl<DrugBill, IDrugBillDao> implements
		IDrugBillService {
	@Resource
	private IFarmerService farmerService;
	@Resource
	private IDrugBillDtlService drugBillDtlService;
	@Resource
	private IDrugWarehouseService drugWarehouseService;
	@Resource
	private IDrugService drugService;
	@Resource
	private IDrugOutWareService drugOutWareService;
	@Resource
	private IDrugOutWareDtlService drugOutWareDtlService;
	@Resource
	private IDrugWarehouseFarService drugWarehouseFarService;
	@Resource
	private IBatchService batchService;
	@Resource
	private JDBCWrapperx jdbc;
	/**
	 * 
	 * 功能:分页查询
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#selectAll(com.zd.epa.base.BaseEntity, com.zd.epa.utils.Pager)
	 */
	@Override
	public void selectAll(DrugBill entity, Pager<DrugBill> page) throws Exception {
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
	 * 
	 * 功能:保存领药单
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#save(com.zd.epa.base.BaseEntity)
	 */
	public Object save(DrugBill entity) throws Exception{
		//1验证表头
		//1.1表头是否为空
		if(entity == null)
			throw new SystemException("对象不能为空");
		//1.2代养户是否为空
		if(entity.getFarmer()==null || entity.getFarmer().getId()==null || "".equals(entity.getFarmer().getId()))
			throw new SystemException("代养户不能为空！");
		//1.4批次是否为空
		if(entity.getBatch()==null || entity.getBatch().getId()==null || "".equals(entity.getBatch().getId()))
			throw new SystemException("批次不能为空！");
		//1.3获取养殖公司
		Batch batch = batchService.selectById(entity.getBatch().getId());
		entity.setCompany(batch.getCompany());
		//1.5领药时间是否为空
		if(entity.getRegistDate()==null || "".equals(entity.getRegistDate()))
			throw new SystemException("领药时间不能为空！");
		//2验证明细
		List<DrugBillDtl> fiwdList = entity.getDetails();
		//2.1明细是否为空
		if(fiwdList == null || fiwdList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		Iterator<DrugBillDtl> it = fiwdList.iterator();
		while(it.hasNext()){
			DrugBillDtl fiwd = it.next();
			//2.2.1验证数量是否为空，是否为0
			if(fiwd.getQuantity()==null || !PapUtil.checkDouble(fiwd.getQuantity()) || ArithUtil.comparison(fiwd.getQuantity(), "0")==0)
				it.remove();
			//2.2.2检查副单位数量是否是"",如果是设为null
			if("".equals(fiwd.getSubQuantity()))
				fiwd.setSubQuantity(null);
			fiwd.setDrugBill(entity);
		}
		entity.setFeedFac(batch.getFeedFac());
		entity.setCheckStatus("0");
		entity.setIsBalance("N");
		entity.setIsReceipt("N");
		Object obj = super.save(entity);
		drugBillDtlService.save(fiwdList);
		return obj;
	}
	
	/**
	 * 
	 * 功能:修改领药单
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#update(com.zd.epa.base.BaseEntity)
	 */
	public void update(DrugBill entity)throws Exception{
		//1验证表头
		//1.1表头是否为空
		if(entity == null)
			throw new SystemException("对象不能为空");
		//1.2养殖公司是否为空
		//1.3代养户是否为空
		if(entity.getFarmer()==null || entity.getFarmer().getId()==null || "".equals(entity.getFarmer().getId()))
			throw new SystemException("代养户不能为空！");
		//1.4批次是否为空
		if(entity.getBatch()==null || entity.getBatch().getId()==null || "".equals(entity.getBatch().getId()))
			throw new SystemException("批次不能为空！");
		//1.5领药时间是否为空
		if(entity.getRegistDate()==null || "".equals(entity.getRegistDate()))
			throw new SystemException("领药时间不能为空！");
		//2验证明细
		List<DrugBillDtl> fiwdList = entity.getDetails();
		//2.1明细是否为空
		if(fiwdList == null || fiwdList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		Iterator<DrugBillDtl> it = fiwdList.iterator();
		while(it.hasNext()){
			DrugBillDtl fiwd = it.next();
			//2.2.1验证数量是否为空，是否为0
			if(fiwd.getQuantity()==null || !PapUtil.checkDouble(fiwd.getQuantity()) || ArithUtil.comparison(fiwd.getQuantity(), "0")==0)
				it.remove();
			//2.2.2检查副单位数量是否是"",如果是设为null
			if("".equals(fiwd.getSubQuantity()))
				fiwd.setSubQuantity(null);
			fiwd.setDrugBill(entity);
		}
		
		Batch batch = batchService.selectById(entity.getBatch().getId());
		// 保存表头
		// 1 、获取数据库里的对象
		DrugBill e = super.selectById(entity.getId());
		// 2、将页面数据赋给数据库对象
		e.setFeedFac(batch.getFeedFac());
		e.setFarmer(entity.getFarmer());
		e.setBatch(entity.getBatch());
		e.setRegistDate(entity.getRegistDate());
		e.setRemark(entity.getRemark());
		//保存明细
		//1、删除明细
		Map<String, String> _m = entity.getTempStack();
		if (null != _m && null != _m.get("deleteIds") && !"".equals(_m.get("deleteIds"))  ) {
			String[] str = _m.get("deleteIds").split(",");
			if(str != null){
				for(String id : str)
					drugBillDtlService.deleteById(Integer.parseInt(id));
			}
		}
		//2. 新增/修改明细
		List<DrugBillDtl> updateSwd = new ArrayList<DrugBillDtl>(); 
		List<DrugBillDtl> newSwd = new ArrayList<DrugBillDtl>(); 
		for(DrugBillDtl p : fiwdList){
			if(p.getId()==null){
				p.setDrugBill(e);
				newSwd.add(p);
			}
			if(p.getId()!=null){
				updateSwd.add(p);
			}
		}
		//修改	
		for(DrugBillDtl p : updateSwd){
			DrugBillDtl d = drugBillDtlService.selectById(p.getId());
			d.setQuantity(p.getQuantity());
			d.setSubQuantity(p.getSubQuantity()==""?null:p.getSubQuantity());
		}
		updateSwd.clear();
		//添加
		drugBillDtlService.save(newSwd);
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
		sqlMap.put("drugBill.id", "in", PapUtil.arrayToSQLStr((String[])PK));
		drugBillDtlService.delete(sqlMap);
		return dao.deleteByIds(PK);
	}
	
	/**
	 * 
	 * 功能:审核(建立养殖公司出库单，扣养殖公司库存，增加代养户的库存)
	 * 重写:wxb
	 * 2017-7-28
	 * @see com.zd.foster.warehouse.services.IFeedInWareService#check(java.lang.String[])
	 */
	public void check(String[] idArr)throws Exception{
		if(idArr == null || idArr.length == 0)
			throw new SystemException("请选择单据");
		Users u = SysContainer.get();
		StringBuffer sb = new StringBuffer();
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		for(String s : idArr){
			//1.获取对象
			DrugBill e = super.selectById(s);
			//1.*添加验证：该批次的合同是否处于养殖状态？
			Contract ct=e.getBatch().getContract();
			if(ct!=null && ct.getStatus()!=null ){
				if("LOST".equals(ct.getStatus().getDcode())){
					sb.append("单据【"+s+"】【"+e.getFarmer().getName()+"】第【"+e.getBatch().getBatchNumber()+"】批对应的合同已经终止<br>");
					continue;
				}
			}
			
			FeedFac fc = ct.getFeedFac();
			if(fc == null){
				sb.append("单据【"+s+"】【"+e.getFarmer().getName()+"】第【"+e.getBatch().getBatchNumber()+"】饲料厂不存在<br>");
				continue;
			}
			
			//2.获取明细并遍历明细
			List<DrugBillDtl> fiwdList = drugBillDtlService.selectBySingletAll("drugBill.id", s);
			if(e==null ||fiwdList==null || fiwdList.size()==0){
				sb.append("单据【"+s+"】对象为空或明细不存在<br>");
				continue;
			}
			DrugOutWare dow=new DrugOutWare();
			List<DrugOutWareDtl> dowdList=new ArrayList<DrugOutWareDtl>();
			List<DrugWarehouseFar> dwfAllList = new ArrayList<DrugWarehouseFar>();
			for(DrugBillDtl ed : fiwdList){
				/*****增加正负判断****/
				boolean flag=false;
				if(ArithUtil.comparison(ed.getQuantity(), "0")==1)
					flag=true;
				if(!flag){
					//代养户库存不能为负
					sqlMap.put("batch.id", "=", e.getBatch().getId()+"");
					sqlMap.put("drug.id", "=", ed.getDrug().getId());
				
					List<DrugWarehouseFar> dwList =drugWarehouseFarService.selectHQL(sqlMap);
					sqlMap.clear();
					if(dwList==null || dwList.size()==0){
						sb.append("单据【"+s+"】药品【"+ed.getDrug().getId()+"】代养户没有库存<br>");
						continue;
					}else{
						DrugWarehouseFar dw = dwList.get(0);
						if(dw != null){
							//原始数据
							String q_old = dw.getQuantity();
							//新数据
							String q_new = ed.getQuantity();
							if(q_old==null || "".equals(q_old))
								q_old = "0";
							//2.3.1验证代养户库存是否足够出库
							String num = ArithUtil.add(q_old, q_new);
							if(ArithUtil.comparison(num, "0")==-1){
								sb.append("单据【"+s+"】药品【"+dw.getDrug().getCode()+"】代养户库存不够<br>");
								continue;
							}
						}
					}
				}
				//2.1查询养殖公司该药品库存
				sqlMap.put("company.id", "=", e.getCompany().getId());
				sqlMap.put("feedFac.id", "=", fc.getId());
				sqlMap.put("drug.id", "=", ed.getDrug().getId());
				List<DrugWarehouse> dwList = drugWarehouseService.selectHQL(sqlMap);
				sqlMap.clear();
				//2.2判断库存是否存在
				if(dwList==null || dwList.size()==0){
					sb.append("单据【"+s+"】药品【"+ed.getDrug().getCode()+"】公司没有库存<br>");
					continue;
				}else{
					//2.3该药品库存是否足够出库，足够则减库存,建药品出库单
					DrugWarehouse dw = dwList.get(0);
					if(dw != null){
						//原始数据
						String q_old = dw.getQuantity();
						//医疗单数据
						String q_new = ed.getQuantity();
						if(q_old==null || "".equals(q_old))
							q_old = "0";
						//2.3.1验证库存是否足够出库
						if(ArithUtil.comparison(q_old, q_new)<0){
							sb.append("单据【"+s+"】药品【"+dw.getDrug().getCode()+"】公司库存不够<br>");
							continue;
						}
						//2.3.2建立公司药品出库单明细
						DrugOutWareDtl dowd=new DrugOutWareDtl();
						dowd.setDrugOutWare(dow);
						dowd.setDrug(ed.getDrug());
						dowd.setQuantity(ed.getQuantity());
						dowd.setSubQuantity(ed.getSubQuantity());
						dowdList.add(dowd);
						//2.3.3扣库存
						String num = ArithUtil.sub(q_old, q_new,2);
						dw.setQuantity(num);
						//2.3.4更改副单位数量
						Drug drug=drugService.selectById(ed.getDrug().getId());
						String ratio=drug.getRatio();
						if(ratio!=null && !"".equals(ratio))
							dw.setSubQuantity(ArithUtil.div(num, ratio, 2));
						//2.3.5增加代养户库存
						sqlMap.put("batch.id", "=", e.getBatch().getId()+"");
						sqlMap.put("drug.id", "=", ed.getDrug().getId());
						List<DrugWarehouseFar> fwOutList = drugWarehouseFarService.selectHQL(sqlMap);
						sqlMap.clear();
						if(fwOutList==null || fwOutList.size()==0){
							//新增库存
							DrugWarehouseFar dwf=new DrugWarehouseFar();
							dwf.setCompany(e.getCompany());
							dwf.setFarmer(e.getFarmer());
							dwf.setBatch(e.getBatch());
							dwf.setDrug(ed.getDrug());
							dwf.setQuantity(ed.getQuantity());
							dwf.setSubQuantity(ed.getSubQuantity());
							dwfAllList.add(dwf);
						}else{
							//累加库存
							DrugWarehouseFar dwf = fwOutList.get(0);
							if(dwf != null){
								//原始数据
								String q_old2 = dwf.getQuantity();
								//入库单数据
								String q_new2 = ed.getQuantity();
								String num2 = ArithUtil.add(q_old2, q_new2);
								dwf.setQuantity(num2);
								//添加副单位数量
								Drug drug2=drugService.selectById(ed.getDrug().getId());
								String ratio2=drug2.getRatio();
								if(ratio2!=null && !"".equals(ratio2))
									dwf.setSubQuantity(ArithUtil.div(num2, ratio2, 2));
								drugWarehouseFarService.save(dwf);
							}
						}
					}
					
				}
			}
			//保存增加的代养户库存
			drugWarehouseFarService.save(dwfAllList);
			//3.1保存药品出库单表头和明细
			dow.setFeedFac(fc);
			dow.setCompany(e.getCompany());
			dow.setFarmer(e.getFarmer());
			dow.setBatch(e.getBatch());
			dow.setRegistDate(e.getRegistDate());
			dow.setDrugBill(e.getId());
			dow.setCheckDate(PapUtil.date(new Date()));
			dow.setCheckStatus("1");
			dow.setCheckUser(u.getUserRealName());
			drugOutWareService.save(dow);
			drugOutWareDtlService.save(dowdList);
			//4.审核单据 
			e.setCheckDate(PapUtil.date(new Date()));
			e.setCheckStatus("1");
			e.setCheckUser(u.getUserRealName());
		}
		if(sb.length()>0)
			throw new SystemException(sb.toString());
		
	}

	/**
	 * 功能:撤销
	 * 重写:wxb
	 * 2017-7-28
	 * @see com.zd.foster.warehouse.services.IFeedInWareService#cancelCheck(java.lang.String)
	 */
	@Override
	public void cancelCheck(String id) throws Exception {
		if(id == null || "".equals(id))
			throw new SystemException("请选择单据");
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		//1.获取对象
		DrugBill e = super.selectById(id);
		if(e.getIsBalance().equals("Y"))
			throw new SystemException("单据【"+id+"】已经结算<br>");
		
		Contract ct=e.getBatch().getContract();
		if(ct!=null && ct.getStatus()!=null ){
			if("LOST".equals(ct.getStatus().getDcode()))
				throw new SystemException("单据【"+id+"】对应的合同已经终止<br>");
		}
		
		FeedFac fc = ct.getFeedFac();
		if(fc == null)
			throw new SystemException("单据【"+id+"】没有饲料厂<br>");
		
		//2.获取明细并遍历
		List<DrugBillDtl> fiwdList = drugBillDtlService.selectBySingletAll("drugBill.id", id);
		if(e==null ||fiwdList==null || fiwdList.size()==0)
			throw new SystemException("单据【"+id+"】对象为空或明细不存在");
		List<DrugWarehouse> fwAllList = new ArrayList<DrugWarehouse>();
		for(DrugBillDtl ed : fiwdList){
			/*****增加正负判断****/
			boolean flag=false;
			if(ArithUtil.comparison(ed.getQuantity(), "0")==1)
				flag=true;
			//验证公司库存是否够撤销
			sqlMap.put("company.id", "=", e.getCompany().getId());
			sqlMap.put("drug.id", "=", ed.getDrug().getId());
			sqlMap.put("feedFac.id", "=", fc.getId());
			List<DrugWarehouse> dwList = drugWarehouseService.selectHQL(sqlMap);
			sqlMap.clear();
			
			if(!flag){
				//2.2判断库存是否存在
				if(dwList==null || dwList.size()==0){
					throw new SystemException("单据【"+id+"】药品【"+ed.getDrug().getId()+"】公司没有库存<br>");
				}else{
					//2.3该药品库存是否足够出库，足够则减库存,建药品出库单
					DrugWarehouse dw = dwList.get(0);
					if(dw != null){
						//原始数据
						String q_old = dw.getQuantity();
						//医疗单数据
						String q_new = ed.getQuantity();
						if(q_old==null || "".equals(q_old))
							q_old = "0";
						//2.3.1验证库存是否足够出库
						String num2 = ArithUtil.add(q_old, q_new);
						if(ArithUtil.comparison(num2, "0")<0){
							throw new SystemException("单据【"+id+"】药品【"+dw.getDrug().getName()+"】公司库存不够<br>");
						}
					}
				}
			}
			
			sqlMap.put("drug.id", "=", ed.getDrug().getId());
			sqlMap.put("batch.id", "=", e.getBatch().getId()+"");
			List<DrugWarehouseFar> fwList = drugWarehouseFarService.selectHQL(sqlMap);
			sqlMap.clear();
			if(fwList==null || fwList.size()==0){
				//2.1查询代养户药品库存是否存在
				throw new SystemException("单据【"+id+"】中饲料【"+ed.getDrug().getName()+"】代养户库存已经不存在<br>");
			}else{
				//2.2查询代养户药品库存是否足够撤销
				DrugWarehouseFar dwf = fwList.get(0);
				if(dwf != null){
					//原始数据
					String q_old = dwf.getQuantity();
					//入库单数据
					String q_new = ed.getQuantity();
					if(q_old==null || "".equals(q_old))
						q_old = "0";
					
					String num = ArithUtil.sub(q_old, q_new);
					if(ArithUtil.comparison(num, "0")<0)
						throw new SystemException("代养户库存不够撤销！");
					//2.2.1扣代养户库存
					dwf.setQuantity(num);
					//2.2.2更改副单位数量
					Drug feed=drugService.selectById(ed.getDrug().getId());
					String ratio=feed.getRatio();
					if(ratio!=null && !"".equals(ratio))
						dwf.setSubQuantity(ArithUtil.div(num, ratio, 2));
					drugWarehouseFarService.save(dwf);
					//2.2.3增加养殖公司的库存
					if(dwList==null || dwList.size()==0){
						//添加新的库存
						DrugWarehouse dw=new DrugWarehouse();
						dw.setFeedFac(fc);
						dw.setCompany(e.getCompany());
						dw.setDrug(ed.getDrug());
						dw.setIsDrug("0");
						dw.setQuantity(ed.getQuantity());
						dw.setSubQuantity(ed.getSubQuantity());
						fwAllList.add(dw);
					}else{
						DrugWarehouse dw = dwList.get(0);
						//原始数据
						String q_old2=dw.getQuantity()==null?"0":("".equals(dw.getQuantity())?"0":dw.getQuantity());
						String q_new2=ed.getQuantity()==null?"0":("".equals(ed.getQuantity())?"0":ed.getQuantity());
//						String q_old2 = dw.getQuantity();
//						//入库单数据
//						String q_new2 = ed.getQuantity();
//						if(q_old2==null || "".equals(q_old2))
//							q_old2 = "0";
//						if(q_new2==null || "".equals(q_new2))
//							q_new2 = "0";
						String num2 = ArithUtil.add(q_old2, q_new2);
						//2.2.1增加养殖公司的库存
						dw.setQuantity(num2);
						//2.2.2更改副单位数量
						Drug feed2=drugService.selectById(ed.getDrug().getId());
						String ratio2=feed2.getRatio();
						if(ratio2!=null && !"".equals(ratio2))
							dw.setSubQuantity(ArithUtil.div(num2, ratio2, 2));
						drugWarehouseService.save(dw);
					}
				}
			}
		}
		//保存养殖公司新的药品库存
		if(fwAllList !=null && fwAllList.size()>0)
			drugWarehouseService.save(fwAllList);
		//删除养殖公司药品出库单
		DrugOutWare dow=drugOutWareService.selectBySinglet("drugBill", e.getId());
		drugOutWareService.deleteById(dow.getId()); 
		e.setCheckDate(null);
		e.setCheckStatus("0");
		e.setCheckUser(null);
	}

	/**
	 * 
	 * 功能:回执
	 * 重写:wxb
	 * 2017-9-9
	 * @see com.zd.foster.warehouse.services.IDrugBillService#receipt(java.lang.String)
	 */
	@Override
	public void receipt(String[] idArr) throws Exception {
		for(String s:idArr){
			DrugBill e=super.selectById(s);
//			if(!e.getCheckStatus().equals("1"))
//				throw new SystemException("单据未审核！");
			e.setIsReceipt("Y");
		}
				
	}
	/**
	 * 
	 * 功能:撤销回执
	 * 重写:wxb
	 * 2017-9-9
	 * @see com.zd.foster.warehouse.services.IDrugBillService#cancelReceipt(java.lang.String)
	 */
	@Override
	public void cancelReceipt(String id) throws Exception {
		DrugBill e=super.selectById(id);
		e.setIsReceipt("N");
		
	}

	/**
	 * 
	 * 功能:下载模板
	 * 重写:wxb
	 * 2017-9-12
	 * @see com.zd.foster.warehouse.services.IDrugBillService#downloadTemplate(java.lang.String)
	 */
	@Override
	public InputStream downloadTemplate(String realPath) throws Exception {
		InputStream fileInput = null;
		try {
			fileInput = new FileInputStream(realPath + "/WEB-INF/template/" + "drugBill.xlsx");
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new SystemException("系统找不到指定路径下的模板文件！");
		}
		return fileInput;

	}

	@Override
	public List<DrugBill> operateFile(File doc, Company company,
			Object... objects) throws Exception {
		if(company==null || company.getId()==null || "".equals(company.getId()))
			throw new SystemException("请选择公司");
		List<DrugBill> details = new ArrayList<DrugBill>();
		Workbook workbook = ExcelUtil.buildWorkbook(doc, (String)objects[0]);
		Map<String,List<DrugBillDtl>> dtlMap = new HashMap<String,List<DrugBillDtl>>();
		Map<String,DrugBill> map = new HashMap<String,DrugBill>();
		if (null != workbook) {
			Sheet sheet = workbook.getSheetAt(0); // 获得第一个Excel Sheet
			int lastRowNum = sheet.getLastRowNum(); // 最后行号，默认为索引号，即从0开始到当前行号-1，如excel有10条数据，firstRowNum=0，而lastRowNum=9
			if (lastRowNum >0) {
				int firstRowNum = sheet.getFirstRowNum();
				StringBuffer sb = new StringBuffer(); // 存储校验非法描述
				for (int i = firstRowNum + 1; i <= lastRowNum; i++) {
					Row row = sheet.getRow(i);
					if (null != row) {
						String farmer = ExcelUtil.checkCellValue(row.getCell(0), i + 1, "代养户姓名", true, sb);
						String batch = ExcelUtil.checkCellValue(row.getCell(1), i + 1, "批次号", true, sb);
						String registDate = ExcelUtil.checkCellValue(row.getCell(2), i + 1, "领药日期", true, sb);
						String isReceipt = ExcelUtil.checkCellValue(row.getCell(3), i + 1, "是否回执", true, sb);
						String drug = ExcelUtil.checkCellValue(row.getCell(4), i + 1, "药品编码", true, sb);
						String quantity = ExcelUtil.checkCellValue(row.getCell(5), i + 1, "数量", true, sb);
//						//增加饲料厂
//						String feedFac= ExcelUtil.checkCellValue(row.getCell(6), i + 1, "饲料厂", true, sb);
						//验证代养户
						Farmer fr=new Farmer();
						String hql_farmer="from Farmer e where e.company.id='"+company.getId()+"'and e.name='"+farmer+"'";
						List<Farmer> fList=farmerService.selectByHQL(hql_farmer);
						if(fList==null || fList.isEmpty()){
							sb.append(new SystemException("不存在代养户【"+farmer+"】"));
							continue;
						}else
							fr=fList.get(0);
						//验证批次
						Batch bh=new Batch();
						String hql_batch="from Batch e where e.company.id='"+company.getId()+"'and e.farmer.name='"+farmer+"'and e.batchNumber='"+batch+"'";
						List<Batch> bList=batchService.selectByHQL(hql_batch);
						if(bList==null || bList.isEmpty()){
							sb.append(new SystemException("不存在代养户【"+farmer+"】批次【"+batch+"】"));
							continue;
						}else
							bh=bList.get(0);
						//验证药品
						Drug dg = new Drug();
						String hql_drug="from Drug e where e.company.id='"+company.getId()+"'and e.code='"+drug+"'";
						List<Drug> dList=drugService.selectByHQL(hql_drug);
						if(dList==null || dList.isEmpty()){
							sb.append(new SystemException("不存在药品【"+drug+"】"));
							continue;
						}else
							dg = dList.get(0);
//						//验证饲料厂
//						FeedFac ff=new FeedFac();
//						String hql_ff="from FeedFac e where e.company.id='"+company.getId()+"'and e.name='"+feedFac+"'";
//						List<FeedFac> feedFacs=feedFacService.selectByHQL(hql_ff);
//						if(feedFacs==null || feedFacs.isEmpty()){
//							sb.append( new SystemException("不存在饲料厂【"+feedFac+"】"));
//							continue;
//						}else
//							ff=feedFacs.get(0);
						//构造key
						String key=farmer+","+batch+","+registDate+","+isReceipt;
						//验证key在map中是否已经存在
						Set<String> dSet=map.keySet();
						if(dSet==null || dSet.size()==0 || !dSet.contains(key)){
							//不存在：直接创建表头对象
							DrugBill diw=this.getInstance(company, fr, bh, registDate);
//							DrugBill diw=new DrugBill();
//							diw.setRegistDate(registDate);
//							diw.setCompany(company);
//							diw.setFarmer(fr);
//							diw.setBatch(bh);
							//明细集合
							List<DrugBillDtl> diwdList = new ArrayList<DrugBillDtl>();
							//封装明细对象
							DrugBillDtl diwd=this.getDtlInstance(diw, dg, quantity);
//							DrugBillDtl diwd = new DrugBillDtl();
//							diwd.setDrugBill(diw);
//							diwd.setDrug(dg);
//							diwd.setQuantity(quantity);
//							String subquan = ArithUtil.div(quantity, dg.getRatio(), 2);
//							diwd.setSubQuantity(subquan);
							//明细添加到明细集合
							diwdList.add(diwd);
							dtlMap.put(key, diwdList);
							map.put(key, diw);
						}else{
							//取出表头和明细集合
							DrugBill diw=map.get(key);
							List<DrugBillDtl> diwdList=dtlMap.get(key);
							//封装明细对象
							DrugBillDtl diwd=this.getDtlInstance(diw, dg, quantity);
//							DrugBillDtl diwd = new DrugBillDtl();
//							diwd.setDrugBill(diw);
//							diwd.setDrug(dg);
//							diwd.setQuantity(quantity);
//							String subquan = ArithUtil.div(quantity, dg.getRatio(), 2);
//							diwd.setSubQuantity(subquan);
							//明细对象添加到明细集合
							diwdList.add(diwd);
						}
					}
				}
				if(sb.length() > 0)
					throw new SystemException(sb.toString());
				if(map.isEmpty())
					throw new SystemException("无可用数据导入");
				//遍历map保存单据,审核单据
				for(String s:map.keySet()){
					DrugBill e=map.get(s);
					details.add(e);
					List<DrugBillDtl> ed=dtlMap.get(s);
					e.setDetails(ed);
					Object obj=this.save(e);
					String[] idArray=new String[]{(String)obj};
					this.check(idArray);
					//判断是否需要回执
					String[] arr=s.split(",");
					if(arr[arr.length-1].equals("Y"))
						this.receipt(idArray);
				}
			}else
				throw new SystemException("无可用数据导入");
		}else
			throw new SystemException("无可用数据导入");
			
		return details;
	}	
	/**
	 * 
	 * 功能:封装明细对象
	 * @author:wxb
	 * @data:2017-9-12下午03:07:21
	 * @file:DrugBillServiceImpl.java
	 * @param db
	 * @param drug
	 * @param quantity
	 * @return
	 * @throws Exception
	 */
	private DrugBillDtl getDtlInstance(DrugBill db,Drug drug,String quantity)throws Exception{
		DrugBillDtl dbd = new DrugBillDtl();
		dbd.setDrugBill(db);
		dbd.setDrug(drug);
		dbd.setQuantity(ArithUtil.scale(quantity, 4));
		String subquan = ArithUtil.div(quantity, drug.getRatio(), 2);
		dbd.setSubQuantity(subquan);
		return dbd;
	}
	/**
	 * 
	 * 功能:封装表头对象
	 * @author:wxb
	 * @data:2017-9-12下午03:11:29
	 * @file:DrugBillServiceImpl.java
	 * @param company
	 * @param farmer
	 * @param batch
	 * @param registDate
	 * @return
	 * @throws Exception
	 */
	private DrugBill getInstance(Company company,Farmer farmer,Batch batch,String registDate)throws Exception{
		DrugBill db=new DrugBill();
		db.setRegistDate(registDate);
		db.setCompany(company);
		db.setFarmer(farmer);
		db.setBatch(batch);
		return db;
	}

	@Override
	public Map<String, Object> printDrugBill(String id) throws Exception {
		DrugBill e=super.selectById(id);
		if(e==null)
			throw new SystemException("未获取到打印信息");
		String date=e.getRegistDate();
		String hql_dt="from DrugBillDtl e where e.drugBill.id='"+id+"'";
		List<DrugBillDtl> dbdList=drugBillDtlService.selectByHQL(hql_dt);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for(int i=0;i<dbdList.size();i++){
			DrugBillDtl dbd=dbdList.get(0);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("num", (i+1));
			map.put("drugName", dbd.getDrug().getName());
			map.put("spec", dbd.getDrug().getSpec());
			map.put("unit", dbd.getDrug().getUnit().getValue());
			map.put("quantity", dbd.getQuantity());
			list.add(map);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("date", date);
		m.put("value", list);
		return m;
	}
	
	/**
	 * 药品耗用报表
	 * @param feedInWare
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<MedicalAnalysis> selectMedicalByPage(DrugBill drugBill,Pager<DrugBill> page)throws Exception{
		
		//先生成查询语句
		String sqlin = "select farmer.name as farmerName,batch.batchnumber as batchnumber,t.drugbill,t.drug,drug.code,drug.name as drugName,drug.drugtype,bu.value,t.quantity,f.registdate as registdate,f.batch,f.remark,farmer.id,fc.name as fcname,1 "+
					   "from fs_drugbilldtl t "+
					   "join fs_drugbill f on(f.id=t.drugbill) "+
					    "left join fs_drug drug on(drug.id=t.drug) "+
						"left join fs_farmer farmer on(farmer.id = f.farmer) "+
						"left join fs_batch batch on(batch.id=f.batch) "+
						"left join fs_contract c on (batch.contract = c.id) "+
	                    "left join fs_feedfac fc on (c.feedfac = fc.id) "+
						"left join base_bussinesseledetail bu on(bu.dcode=drug.unit) "+
						"where f.checkstatus='1'";
		
		//先生成查询语句
		String sqlout = "select farmer.name as farmerName,batch.batchnumber as batchnumber,t.drugtransfer,t.drug,drug.code,drug.name as drugName,drug.drugtype,bu.value,t.quantity,f.registdate as registdate,f.outbatch,f.remark,farmer.id,fc.name as fcname,2 from fs_drugtransferdtl t "+
						"join fs_drugtransfer f on (f.id=t.drugtransfer) "+
						"left join fs_drug drug on(drug.id=t.drug) "+
						"left join fs_farmer farmer on(farmer.id = f.outfarmer) "+
						"left join fs_batch batch on(batch.id=f.outbatch) "+
						"left join fs_contract c on (batch.contract = c.id) "+
	                    "left join fs_feedfac fc on (c.feedfac = fc.id) "+
						"left join base_bussinesseledetail bu on(bu.dcode=drug.unit) "+
						"where f.checkstatus='1'";
		
		//查找定价单
		String priceSql = "select t.price "+
						  "from fs_drugpricedtl t "+
						  "join fs_drugprice f on (f.id = t.drugprice) "+
						 "where t.drug='###' "+
						   "and f.checkstatus = '1' "+
						   "and f.startdate <= '###' "+
						   "order by f.startdate desc";
		
		
		//获取最后一次饲料入库时间
		String maxDateSql= "select max(f.registdate) from fs_drugbilldtl t left join fs_drugbill f on(f.id=t.drugbill) where f.batch='###' and f.checkstatus='1' and f.registdate<='###' and t.drug='###' and t.quantity>0";
								
		//计算合计
		String inallSql = "select farmer.name as farmerName,batch.batchnumber as batchnumber,t.drug,drug.code,drug.name as drugName,drug.drugtype,bu.value,fc.name as fcname,sum(t.quantity),1 "+
			              "from fs_drugbilldtl t "+
			              "join fs_drugbill f on(f.id=t.drugbill) "+
			              "left join fs_drug drug on(drug.id=t.drug) "+
			              "left join fs_farmer farmer on(farmer.id = f.farmer) "+
			              "left join fs_batch batch on(batch.id=f.batch) "+
			              "left join fs_contract c on (batch.contract = c.id) "+
		                  "left join fs_feedfac fc on (c.feedfac = fc.id) "+
			              "left join base_bussinesseledetail bu on(bu.dcode=drug.unit) "+
			              "where f.checkstatus='1'";
		
		String tallSql = "select farmer.name as farmerName,batch.batchnumber as batchnumber,t.drug,drug.code,drug.name as drugName,drug.drugtype,bu.value,fc.name as fcname,sum(t.quantity),2 from fs_drugtransferdtl t "+ 
			            "join fs_drugtransfer f on (f.id=t.drugtransfer) "+
			            "left join fs_drug drug on(drug.id=t.drug) "+
			            "left join fs_farmer farmer on(farmer.id = f.outfarmer) "+
			            "left join fs_batch batch on(batch.id=f.outbatch) "+
			            "left join fs_contract c on (batch.contract = c.id) "+
	                    "left join fs_feedfac fc on (c.feedfac = fc.id) "+
			            "left join base_bussinesseledetail bu on(bu.dcode=drug.unit) "+
			            "where f.checkstatus='1'";
		//合计
		String allin = "select sum(t.quantity) from fs_drugbilldtl t join fs_drugbill f on(f.id=t.drugbill) left join fs_drug drug on(drug.id=t.drug)  where f.checkstatus='1'";
		String allout = "select sum(t.quantity) from fs_drugtransferdtl t join fs_drugtransfer f on (f.id = t.drugtransfer) left join fs_drug drug on(drug.id=t.drug) where f.checkstatus='1'";
		
		String type = "1";
		if(drugBill != null){
			type = drugBill.getCheckStatus();
			Company company = drugBill.getCompany();
			if(company != null && company.getId() != null && !"".equals(company.getId())){
				sqlin +=" and f.company='"+company.getId()+"'";
				sqlout += "  and f.company='"+company.getId()+"'";
				
				inallSql += "  and f.company='"+company.getId()+"'";
				tallSql += "  and f.company='"+company.getId()+"'";
				
				allin += " and f.company='"+company.getId()+"'";
				allout += " and f.company='"+company.getId()+"'";
			}
			
			Map<String,String> map = drugBill.getTempStack();
			if(map != null){
				String startDate = map.get("startDate");
				String endDate = map.get("endDate");
				if(startDate != null && !"".equals(startDate)){
					if("1".equals(type)){
						sqlin +=" and f.registdate>='"+startDate+"'";
						sqlout += " and f.registdate>='"+startDate+"'";
					}
					if("2".equals(type)){
						inallSql +=" and f.registdate>='"+startDate+"'";
						tallSql += " and f.registdate>='"+startDate+"'";
						
						allin += " and f.registdate>='"+startDate+"'";
						allout += " and f.registdate>='"+startDate+"'";
					}
				}
				if(endDate != null && !"".equals(endDate)){
					if("1".equals(type)){
						sqlin +=" and f.registdate<='"+endDate+"'";
						sqlout += " and f.registdate<='"+endDate+"'";
					}
					if("2".equals(type)){
						inallSql +=" and f.registdate<='"+endDate+"'";
						tallSql += " and f.registdate<='"+endDate+"'";
						
						allin += " and f.registdate<='"+endDate+"'";
						allout += " and f.registdate<='"+endDate+"'";
					}
				}
			}
			Farmer f = drugBill.getFarmer();
			if(f != null && f.getId() != null && !"".equals(f.getId())){
				sqlin +=" and f.farmer='"+f.getId()+"'";
				sqlout += " and f.outfarmer='"+f.getId()+"'";
				
				inallSql +=" and f.farmer='"+f.getId()+"'";
				tallSql += " and f.outfarmer='"+f.getId()+"'";
				
				allin += " and f.farmer='"+f.getId()+"'";
				allout += " and f.outfarmer='"+f.getId()+"'";
				
			}
			Batch b = drugBill.getBatch();
			if(b != null && b.getId() != null && !"".equals(b.getId())){
				sqlin +=" and f.batch='"+b.getId()+"'";
				sqlout += " and f.outbatch='"+b.getId()+"'";
				
				inallSql += " and f.batch='"+b.getId()+"'";
				tallSql += " and f.outbatch='"+b.getId()+"'";
				
				allin += " and f.batch='"+b.getId()+"'";
				allout += " and f.outbatch='"+b.getId()+"'";
			}
			
			String drugType = drugBill.getIsBalance();
			if(drugType != null && !"".equals(drugType)){
				sqlin +=" and drug.drugtype='"+drugType+"'";
				sqlout += " and drug.drugtype='"+drugType+"'";
				
				inallSql += " and drug.drugtype='"+drugType+"'";
				tallSql += " and drug.drugtype='"+drugType+"'";
				
				allin += " and drug.drugtype='"+drugType+"'";
				allout += " and drug.drugtype='"+drugType+"'";
			}
		}
		
	    //执行查询
		Connection connection = null;
		List<MedicalAnalysis> plist = new ArrayList<MedicalAnalysis>();
		StringBuffer sbt = new StringBuffer();
		try{
			connection = jdbc.getConnection();
			if("1".equals(type)){
				//分页查询语句
				StringBuffer sb = new StringBuffer();
				sb.append("(");
				sb.append(sqlin);
				sb.append(")");
				sb.append(" union all ");
				sb.append("(");
				sb.append(sqlout);
				sb.append(")");
				
				String countSql="select count(1) from (" + sb.toString() +") ";
				
				sb.insert(0, "(");
				sb.append(") order by fcname,farmerName,batchnumber,registdate desc");
				
				String feedsql = "select * from (select p.*,rownum as rn from (" + sb.toString() +") p where rownum<= "+page.getPageNo()*page.getPageSize()+") v where v.rn> "+(page.getPageNo()-1)*page.getPageSize()+"";
				
				Object[][] Objcount = jdbc.Querry(countSql, connection);
				Object[][] Objs = jdbc.Querry(feedsql, connection);
				//关闭连接
				page.setTotalCount(Objcount[0][0]==null?0:Integer.parseInt(Objcount[0][0].toString()));
				//分解并封装对象
				//封装对象
				if(Objs != null && Objs.length>0){
					for(int i=0;i<Objs.length;i++){
						String farmerName = Objs[i][0]==null?"":Objs[i][0].toString();
						String batchnumber = Objs[i][1]==null?"":Objs[i][1].toString();
						String billId = Objs[i][2]==null?"":Objs[i][2].toString();
						String drugId = Objs[i][3]==null?"":Objs[i][3].toString();
						String drugCode = Objs[i][4]==null?"":Objs[i][4].toString();
						String drugName = Objs[i][5]==null?"":Objs[i][5].toString();
						String drugType = Objs[i][6]==null?"":Objs[i][6].toString();
						String unitName = Objs[i][7]==null?"":Objs[i][7].toString();
						String quantity = Objs[i][8]==null?"0":Objs[i][8].toString();
						String registdate = Objs[i][9]==null?"":Objs[i][9].toString();
						String batchId = Objs[i][10]==null?"":Objs[i][10].toString();
						String remark = Objs[i][11]==null?"":Objs[i][11].toString();
						String feedFacName = Objs[i][13]==null?"":Objs[i][13].toString();
						String flag = Objs[i][14]==null?"":Objs[i][14].toString();
						
						MedicalAnalysis me = new MedicalAnalysis();
						me.setFarmer(farmerName);
						me.setBatch(batchnumber);
						me.setBill(billId);
						me.setDrug(drugName);
						me.setDrugCode(drugCode);
						me.setUnit(unitName);
						me.setMedicalDate(registdate);
						me.setDrugType(drugType);
						me.setQuantity(quantity);
						me.setFeedFac(feedFacName);
						if("2".equals(flag))
							me.setQuantity("-"+quantity);
						me.setMark(remark);
						
						//计算单价以及费用
						if(PapUtil.checkDouble(quantity)){
							if(ArithUtil.comparison(quantity, "0")==1 && "1".equals(flag)){
								//查找该饲料的定价
								String _pSql= priceSql.replaceFirst("###", drugId).replaceFirst("###", registdate);
								//--1.1获取定价
								Object[][] _priceobj = jdbc.Querry(_pSql, connection);
								String price = "";
								if(_priceobj != null && _priceobj.length>0){
									for(int t=0;t<_priceobj.length;t++){
										price = _priceobj[t][0]==null?"":_priceobj[t][0].toString();
										if(price==null || "".equals(price))
											continue;
										else
											break;
									}
								}
								if(price==null || "".equals(price))
									price="0";
								
								String money=ArithUtil.mul(price, quantity,2);
								me.setPrice(price);
								me.setAmount(money);
								
							}else if(ArithUtil.comparison(quantity, "0")==-1 || "2".equals(flag)){
								String _maxDateSql= maxDateSql.replaceFirst("###", batchId).replaceFirst("###", registdate).replaceFirst("###", drugId);
								Object[][] obj = jdbc.Querry(_maxDateSql, connection);
								String date = "";
								if(obj != null && obj.length>0)
									date = obj[0][0]==null?"":obj[0][0].toString();
								if("".equals(date))
									date = registdate;
								
								//查找该饲料的定价
								String _pSql= priceSql.replaceFirst("###", drugId).replaceFirst("###", date);
								//--1.1获取定价
								Object[][] _priceobj = jdbc.Querry(_pSql, connection);
								String price = "";
								if(_priceobj != null && _priceobj.length>0){
									for(int t=0;t<_priceobj.length;t++){
										price = _priceobj[t][0]==null?"":_priceobj[t][0].toString();
										if(price==null || "".equals(price))
											continue;
										else
											break;
									}
								}
								if(price==null || "".equals(price))
									price="0";
								String money=ArithUtil.mul(price, ArithUtil.abs(quantity),2);
								me.setPrice(price);
								me.setAmount("-"+money);
							}
						}
						plist.add(me);
					}
				}
			}
			if("2".equals(type)){
				
				inallSql += " group by farmer.name,batch.batchnumber,t.drug,drug.code,drug.name,drug.drugtype,bu.value,fc.name";
				tallSql += " group by farmer.name,batch.batchnumber,t.drug,drug.code,drug.name,drug.drugtype,bu.value,fc.name";
				//分页查询语句
				StringBuffer sb2 = new StringBuffer();
				sb2.append("(");
				sb2.append(inallSql);
				sb2.append(")");
				sb2.append(" union all ");
				sb2.append("(");
				sb2.append(tallSql);
				sb2.append(")");
				
				String countSql="select count(1) from (" + sb2.toString() +") ";
				
				sb2.insert(0, "(");
				sb2.append(") order by fcname,farmerName,batchnumber desc");
				
				String feedsql = "select * from (select p.*,rownum as rn from (" + sb2.toString() +") p where rownum<= "+page.getPageNo()*page.getPageSize()+") v where v.rn> "+(page.getPageNo()-1)*page.getPageSize()+"";
				
				Object[][] Objcount = jdbc.Querry(countSql, connection);
				Object[][] Objs = jdbc.Querry(feedsql, connection);
				//关闭连接
				page.setTotalCount(Objcount[0][0]==null?0:Integer.parseInt(Objcount[0][0].toString()));
				//分解并封装对象
				//封装对象
				if(Objs != null && Objs.length>0){
					for(int i=0;i<Objs.length;i++){
						
						String farmerName = Objs[i][0]==null?"":Objs[i][0].toString();
						String batchNumber = Objs[i][1]==null?"":Objs[i][1].toString();
						String drugCode = Objs[i][3]==null?"":Objs[i][3].toString();
						String drugName = Objs[i][4]==null?"":Objs[i][4].toString();
						String drugType = Objs[i][5]==null?"":Objs[i][5].toString();
						String unitName = Objs[i][6]==null?"":Objs[i][6].toString();
						String feedFacName = Objs[i][7]==null?"":Objs[i][7].toString();
						String quantity = Objs[i][8]==null?"":Objs[i][8].toString();
						String flag = Objs[i][9]==null?"":Objs[i][9].toString();
						
						MedicalAnalysis me = new MedicalAnalysis();
						me.setFarmer(farmerName);
						me.setBatch(batchNumber);
						me.setDrug(drugName);
						me.setDrugCode(drugCode);
						me.setUnit(unitName);
						me.setDrugType(drugType);
						me.setQuantity(quantity);
						me.setFeedFac(feedFacName);
						if("2".equals(flag))
							me.setQuantity("-"+quantity);
						plist.add(me);
					}
					
					//合计
					Object[][] Obj1 = jdbc.Querry(allin, connection);
					Object[][] Obj2 = jdbc.Querry(allout, connection);
					
					String allInNum = "0";
					String allOutNum = "0";
					
					if(Obj1 != null && Obj1.length>0)
						allInNum = Obj1[0][0]==null?"0":Obj1[0][0].toString();
					if(Obj2 != null && Obj2.length>0)
						allOutNum = Obj2[0][0]==null?"0":Obj2[0][0].toString();
					
					allInNum = ArithUtil.sub(allInNum, allOutNum,2);
					MedicalAnalysis me = new MedicalAnalysis();
					me.setDrug("合计:");
					me.setQuantity(allInNum);
					
					plist.add(me);
				}
				
			}
			if(sbt.length()>0)
				throw new SystemException(sbt.toString());
			if(plist == null || plist.isEmpty())
				throw new SystemException("未获取数据信息");
		}finally{
			jdbc.destroy(connection);
		}
		return plist;
	}
	/**
	 * 饲料耗用报表 导出信息
	 * @param feedInWare
	 * @param page
	 * @return
	 * @throws Exception
	 */
	private List<MedicalAnalysis> expMedicalData(DrugBill drugBill)throws Exception{
		
		//先生成查询语句
		String sqlin = "select farmer.name as farmerName,batch.batchnumber as batchnumber,t.drugbill,t.drug,drug.code,drug.name as drugName,drug.drugtype,bu.value,t.quantity,f.registdate as registdate,f.batch,f.remark,farmer.id,fc.name as fcname,1 "+
					   "from fs_drugbilldtl t "+
					   "join fs_drugbill f on(f.id=t.drugbill) "+
					    "left join fs_drug drug on(drug.id=t.drug) "+
						"left join fs_farmer farmer on(farmer.id = f.farmer) "+
						"left join fs_batch batch on(batch.id=f.batch) "+
						"left join fs_contract c on (batch.contract = c.id) "+
	                    "left join fs_feedfac fc on (c.feedfac = fc.id) "+
						"left join base_bussinesseledetail bu on(bu.dcode=drug.unit) "+
						"where f.checkstatus='1'";
		
		//先生成查询语句
		String sqlout = "select farmer.name as farmerName,batch.batchnumber as batchnumber,t.drugtransfer,t.drug,drug.code,drug.name as drugName,drug.drugtype,bu.value,t.quantity,f.registdate as registdate,f.outbatch,f.remark,farmer.id,fc.name as fcname,2 from fs_drugtransferdtl t "+
						"join fs_drugtransfer f on (f.id=t.drugtransfer) "+
						"left join fs_drug drug on(drug.id=t.drug) "+
						"left join fs_farmer farmer on(farmer.id = f.outfarmer) "+
						"left join fs_batch batch on(batch.id=f.outbatch) "+
						"left join fs_contract c on (batch.contract = c.id) "+
	                    "left join fs_feedfac fc on (c.feedfac = fc.id) "+
						"left join base_bussinesseledetail bu on(bu.dcode=drug.unit) "+
						"where f.checkstatus='1'";
		
		//查找定价单
		String priceSql = "select t.price "+
						  "from fs_drugpricedtl t "+
						  "join fs_drugprice f on (f.id = t.drugprice) "+
						 "where t.drug='###' "+
						   "and f.checkstatus = '1' "+
						   "and f.startdate <= '###' "+
						   "order by f.startdate desc";
		
		
		//获取最后一次饲料入库时间
		String maxDateSql= "select max(f.registdate) from fs_drugbilldtl t left join fs_drugbill f on(f.id=t.drugbill) where f.batch='###' and f.checkstatus='1' and f.registdate<='###' and t.drug='###' and t.quantity>0";
								
		//计算合计
		String inallSql = "select farmer.name as farmerName,batch.batchnumber as batchnumber,t.drug,drug.code,drug.name as drugName,drug.drugtype,bu.value,fc.name as fcname,sum(t.quantity),1 "+
			              "from fs_drugbilldtl t "+
			              "join fs_drugbill f on(f.id=t.drugbill) "+
			              "left join fs_drug drug on(drug.id=t.drug) "+
			              "left join fs_farmer farmer on(farmer.id = f.farmer) "+
			              "left join fs_batch batch on(batch.id=f.batch) "+
			              "left join fs_contract c on (batch.contract = c.id) "+
		                  "left join fs_feedfac fc on (c.feedfac = fc.id) "+
			              "left join base_bussinesseledetail bu on(bu.dcode=drug.unit) "+
			              "where f.checkstatus='1'";
		
		String tallSql = "select farmer.name as farmerName,batch.batchnumber as batchnumber,t.drug,drug.code,drug.name as drugName,drug.drugtype,bu.value,fc.name as fcname,sum(t.quantity),2 from fs_drugtransferdtl t "+ 
			            "join fs_drugtransfer f on (f.id=t.drugtransfer) "+
			            "left join fs_drug drug on(drug.id=t.drug) "+
			            "left join fs_farmer farmer on(farmer.id = f.outfarmer) "+
			            "left join fs_batch batch on(batch.id=f.outbatch) "+
			            "left join fs_contract c on (batch.contract = c.id) "+
	                    "left join fs_feedfac fc on (c.feedfac = fc.id) "+
			            "left join base_bussinesseledetail bu on(bu.dcode=drug.unit) "+
			            "where f.checkstatus='1'";
		//合计
		String allin = "select sum(t.quantity) from fs_drugbilldtl t join fs_drugbill f on(f.id=t.drugbill) left join fs_drug drug on(drug.id=t.drug)  where f.checkstatus='1'";
		String allout = "select sum(t.quantity) from fs_drugtransferdtl t join fs_drugtransfer f on (f.id = t.drugtransfer) left join fs_drug drug on(drug.id=t.drug) where f.checkstatus='1'";
		
		String type = "1";
		if(drugBill != null){
			type = drugBill.getCheckStatus();
			Company company = drugBill.getCompany();
			if(company != null && company.getId() != null && !"".equals(company.getId())){
				sqlin +=" and f.company='"+company.getId()+"'";
				sqlout += "  and f.company='"+company.getId()+"'";
				
				inallSql += "  and f.company='"+company.getId()+"'";
				tallSql += "  and f.company='"+company.getId()+"'";
				
				allin += " and f.company='"+company.getId()+"'";
				allout += " and f.company='"+company.getId()+"'";
			}
			
			Map<String,String> map = drugBill.getTempStack();
			if(map != null){
				String startDate = map.get("startDate");
				String endDate = map.get("endDate");
				if(startDate != null && !"".equals(startDate)){
					if("1".equals(type)){
						sqlin +=" and f.registdate>='"+startDate+"'";
						sqlout += " and f.registdate>='"+startDate+"'";
					}
					if("2".equals(type)){
						inallSql +=" and f.registdate>='"+startDate+"'";
						tallSql += " and f.registdate>='"+startDate+"'";
						
						allin += " and f.registdate>='"+startDate+"'";
						allout += " and f.registdate>='"+startDate+"'";
					}
				}
				if(endDate != null && !"".equals(endDate)){
					if("1".equals(type)){
						sqlin +=" and f.registdate<='"+endDate+"'";
						sqlout += " and f.registdate<='"+endDate+"'";
					}
					if("2".equals(type)){
						inallSql +=" and f.registdate<='"+endDate+"'";
						tallSql += " and f.registdate<='"+endDate+"'";
						
						allin += " and f.registdate<='"+endDate+"'";
						allout += " and f.registdate<='"+endDate+"'";
					}
				}
			}
			Farmer f = drugBill.getFarmer();
			if(f != null && f.getId() != null && !"".equals(f.getId())){
				sqlin +=" and f.farmer='"+f.getId()+"'";
				sqlout += " and f.outfarmer='"+f.getId()+"'";
				
				inallSql +=" and f.farmer='"+f.getId()+"'";
				tallSql += " and f.outfarmer='"+f.getId()+"'";
				
				allin += " and f.farmer='"+f.getId()+"'";
				allout += " and f.outfarmer='"+f.getId()+"'";
				
			}
			Batch b = drugBill.getBatch();
			if(b != null && b.getId() != null && !"".equals(b.getId())){
				sqlin +=" and f.batch='"+b.getId()+"'";
				sqlout += " and f.outbatch='"+b.getId()+"'";
				
				inallSql += " and f.batch='"+b.getId()+"'";
				tallSql += " and f.outbatch='"+b.getId()+"'";
				
				allin += " and f.batch='"+b.getId()+"'";
				allout += " and f.outbatch='"+b.getId()+"'";
			}
			
			String drugType = drugBill.getIsBalance();
			if(drugType != null && !"".equals(drugType)){
				sqlin +=" and drug.drugtype='"+drugType+"'";
				sqlout += " and drug.drugtype='"+drugType+"'";
				
				inallSql += " and drug.drugtype='"+drugType+"'";
				tallSql += " and drug.drugtype='"+drugType+"'";
				
				allin += " and drug.drugtype='"+drugType+"'";
				allout += " and drug.drugtype='"+drugType+"'";
			}
		}
	    //执行查询
		Connection connection = null;
		List<MedicalAnalysis> plist = new ArrayList<MedicalAnalysis>();
		StringBuffer sbt = new StringBuffer();
		try{
			connection = jdbc.getConnection();
			if("1".equals(type)){
				//分页查询语句
				StringBuffer sb = new StringBuffer();
				sb.append("(");
				sb.append(sqlin);
				sb.append(")");
				sb.append(" union all ");
				sb.append("(");
				sb.append(sqlout);
				sb.append(")");
				sb.insert(0, "(");
				sb.append(") order by fcname,farmerName,batchnumber,registdate desc");
				
				String feedsql = sb.toString();
				Object[][] Objs = jdbc.Querry(feedsql, connection);
				//分解并封装对象
				if(Objs != null && Objs.length>0){
					for(int i=0;i<Objs.length;i++){
						String farmerName = Objs[i][0]==null?"":Objs[i][0].toString();
						String batchnumber = Objs[i][1]==null?"":Objs[i][1].toString();
						String billId = Objs[i][2]==null?"":Objs[i][2].toString();
						String drugId = Objs[i][3]==null?"":Objs[i][3].toString();
						String drugCode = Objs[i][4]==null?"":Objs[i][4].toString();
						String drugName = Objs[i][5]==null?"":Objs[i][5].toString();
						String drugType = Objs[i][6]==null?"":Objs[i][6].toString();
						String unitName = Objs[i][7]==null?"":Objs[i][7].toString();
						String quantity = Objs[i][8]==null?"0":Objs[i][8].toString();
						String registdate = Objs[i][9]==null?"":Objs[i][9].toString();
						String batchId = Objs[i][10]==null?"":Objs[i][10].toString();
						String remark = Objs[i][11]==null?"":Objs[i][11].toString();
						String feedFacName = Objs[i][13]==null?"":Objs[i][13].toString();
						String flag = Objs[i][14]==null?"":Objs[i][14].toString();
						
						MedicalAnalysis me = new MedicalAnalysis();
						me.setFarmer(farmerName);
						me.setBatch(batchnumber);
						me.setBill(billId);
						me.setDrug(drugName);
						me.setDrugCode(drugCode);
						me.setUnit(unitName);
						me.setMedicalDate(registdate);
						me.setDrugType(drugType);
						me.setQuantity(quantity);
						me.setFeedFac(feedFacName);
						if("2".equals(flag))
							me.setQuantity("-"+quantity);
						me.setMark(remark);
						//计算单价以及费用
						if(PapUtil.checkDouble(quantity)){
							if(ArithUtil.comparison(quantity, "0")==1 && "1".equals(flag)){
								//查找该饲料的定价
								String _pSql= priceSql.replaceFirst("###", drugId).replaceFirst("###", registdate);
								//--1.1获取定价
								Object[][] _priceobj = jdbc.Querry(_pSql, connection);
								String price = "";
								if(_priceobj != null && _priceobj.length>0){
									for(int t=0;t<_priceobj.length;t++){
										price = _priceobj[t][0]==null?"":_priceobj[t][0].toString();
										if(price==null || "".equals(price))
											continue;
										else
											break;
									}
								}
								if(price==null || "".equals(price))
									price="0";
								
								String money=ArithUtil.mul(price, quantity,2);
								me.setPrice(price);
								me.setAmount(money);
								
							}else if(ArithUtil.comparison(quantity, "0")==-1 || "2".equals(flag)){
								String _maxDateSql= maxDateSql.replaceFirst("###", batchId).replaceFirst("###", registdate).replaceFirst("###", drugId);
								Object[][] obj = jdbc.Querry(_maxDateSql, connection);
								String date = "";
								if(obj != null && obj.length>0)
									date = obj[0][0]==null?"":obj[0][0].toString();
								if("".equals(date))
									date = registdate;
								
								//查找该饲料的定价
								String _pSql= priceSql.replaceFirst("###", drugId).replaceFirst("###", date);
								//--1.1获取定价
								Object[][] _priceobj = jdbc.Querry(_pSql, connection);
								String price = "";
								if(_priceobj != null && _priceobj.length>0){
									for(int t=0;t<_priceobj.length;t++){
										price = _priceobj[t][0]==null?"":_priceobj[t][0].toString();
										if(price==null || "".equals(price))
											continue;
										else
											break;
									}
								}
								if(price==null || "".equals(price))
									price="0";
								String money=ArithUtil.mul(price, ArithUtil.abs(quantity),2);
								me.setPrice(price);
								me.setAmount("-"+money);
							}
						}
						plist.add(me);
					}
				}
			}
			if("2".equals(type)){
				
				inallSql += " group by farmer.name,batch.batchnumber,t.drug,drug.code,drug.name,drug.drugtype,bu.value,fc.name";
				tallSql += " group by farmer.name,batch.batchnumber,t.drug,drug.code,drug.name,drug.drugtype,bu.value,fc.name";
				//分页查询语句
				StringBuffer sb2 = new StringBuffer();
				sb2.append("(");
				sb2.append(inallSql);
				sb2.append(")");
				sb2.append(" union all ");
				sb2.append("(");
				sb2.append(tallSql);
				sb2.append(")");
				sb2.insert(0, "(");
				sb2.append(") order by fcname,farmerName,batchnumber desc");
				
				String feedsql =  sb2.toString() ;
				Object[][] Objs = jdbc.Querry(feedsql, connection);
				//分解并封装对象
				//封装对象
				if(Objs != null && Objs.length>0){
					for(int i=0;i<Objs.length;i++){
						
						String farmerName = Objs[i][0]==null?"":Objs[i][0].toString();
						String batchNumber = Objs[i][1]==null?"":Objs[i][1].toString();
						String drugCode = Objs[i][3]==null?"":Objs[i][3].toString();
						String drugName = Objs[i][4]==null?"":Objs[i][4].toString();
						String drugType = Objs[i][5]==null?"":Objs[i][5].toString();
						String unitName = Objs[i][6]==null?"":Objs[i][6].toString();
						String feedFacName = Objs[i][7]==null?"":Objs[i][7].toString();
						String quantity = Objs[i][8]==null?"":Objs[i][8].toString();
						String flag = Objs[i][9]==null?"":Objs[i][9].toString();
						
						MedicalAnalysis me = new MedicalAnalysis();
						me.setFarmer(farmerName);
						me.setBatch(batchNumber);
						me.setDrug(drugName);
						me.setDrugCode(drugCode);
						me.setUnit(unitName);
						me.setDrugType(drugType);
						me.setQuantity(quantity);
						me.setFeedFac(feedFacName);
						if("2".equals(flag))
							me.setQuantity("-"+quantity);
						plist.add(me);
					}
					//合计
					Object[][] Obj1 = jdbc.Querry(allin, connection);
					Object[][] Obj2 = jdbc.Querry(allout, connection);
					
					String allInNum = "0";
					String allOutNum = "0";
					
					if(Obj1 != null && Obj1.length>0)
						allInNum = Obj1[0][0]==null?"0":Obj1[0][0].toString();
					if(Obj2 != null && Obj2.length>0)
						allOutNum = Obj2[0][0]==null?"0":Obj2[0][0].toString();
					
					allInNum = ArithUtil.sub(allInNum, allOutNum,2);
					MedicalAnalysis me = new MedicalAnalysis();
					me.setDrug("合计:");
					me.setQuantity(allInNum);
					
					plist.add(me);
				}
			}
			if(sbt.length()>0)
				throw new SystemException(sbt.toString());
			if(plist == null || plist.isEmpty())
				throw new SystemException("未获取数据信息");
		}finally{
			jdbc.destroy(connection);
		}
		return plist;
	}
	
	/**
	 * 
	 * 功能： 导出进度追踪表<br/>
	 * @author:DZL<br/>
	 * @version:2016-12-2上午09:55:02
	 * @param @param e
	 * @param @return
	 * @param @throws Exception  
	 * @throws
	 * @return InputStream
	 * @see com.zd.pb.breed.services
	 */
	public InputStream exportMedical(DrugBill drugBill)throws Exception{
		
		if(drugBill == null)
			return null;
		String isall = drugBill.getCheckStatus();
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		String sheetName= "药品耗用报表";
		if("2".equals(isall))
			sheetName= "药品耗用合计报表";
		HSSFSheet sheet1 = workbook.createSheet(sheetName);
		// 设置表格默认列宽度为15个字节
		sheet1.setDefaultColumnWidth(20);
		//设置表头样式
		HSSFCellStyle headerStyle = ExcelUtil.getHeaderStyle(workbook);
		//设置表格样式
		HSSFCellStyle cellStyle = ExcelUtil.getCellStyle(workbook);
		String expField = drugBill.getExportFields();
		if(expField == null)
			return null;
		String[] fileds = expField.split(",");
		// 生成表头标题行
		List<String> fieldNames = new ArrayList<String>();
		int cellIndex1 = -1;
		for (String field : fileds) {
			String display = field.split(":")[1];
			fieldNames.add(field.split(":")[0]);
			ExcelUtil.setCellValue(sheet1, headerStyle, 0, ++cellIndex1, display);
		}
		// 生成数据行
		int index1 = 0;
		List<MedicalAnalysis> datas = expMedicalData(drugBill);
		if (null != datas && datas.size() > 0) {
			for (MedicalAnalysis b: datas) {
				index1 ++;
				HSSFRow row = sheet1.createRow(index1);
				for (int i = 0; i < fieldNames.size(); i ++) {
					HSSFCell cell = row.createCell(i);
					cell.setCellStyle(cellStyle);
					Object obj = TypeUtil.getFieldValue(b,fieldNames.get(i));
					ExcelUtil.setCellValue(cell, obj);
				}
			}
		}
		// 生成数据行
		ByteArrayOutputStream out = null;
		InputStream inputStream = null;
		try {
			out = new ByteArrayOutputStream();
			workbook.write(out);
			out.flush();
			byte[] outByte = out.toByteArray();
			inputStream = new ByteArrayInputStream(outByte, 0, outByte.length);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if (null != inputStream) {
					inputStream.close();
				}
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return inputStream;
	}
}
