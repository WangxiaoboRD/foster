/**
 * 功能:
 * @author:wxb
 * @data:2017-9-5下午04:43:23
 * @file:FeedTransferService.java
 */
package com.zd.foster.warehouse.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import com.zd.foster.base.entity.Driver;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.entity.TransportCo;
import com.zd.foster.base.services.IDriverService;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.base.services.ITransportCoService;
import com.zd.foster.breed.entity.FeedBill;
import com.zd.foster.breed.entity.FeedBillDtl;
import com.zd.foster.breed.services.IFeedBillService;
import com.zd.foster.contract.entity.Contract;
import com.zd.foster.contract.services.IContractService;
import com.zd.foster.material.entity.Feed;
import com.zd.foster.material.services.IFeedService;
import com.zd.foster.warehouse.dao.IFeedTransferDao;
import com.zd.foster.warehouse.entity.FeedInWare;
import com.zd.foster.warehouse.entity.FeedInWareDtl;
import com.zd.foster.warehouse.entity.FeedOutWare;
import com.zd.foster.warehouse.entity.FeedOutWareDtl;
import com.zd.foster.warehouse.entity.FeedTransfer;
import com.zd.foster.warehouse.entity.FeedTransferDtl;
import com.zd.foster.warehouse.entity.FeedWarehouse;
import com.zd.foster.warehouse.services.IFeedInWareService;
import com.zd.foster.warehouse.services.IFeedTransferDtlService;
import com.zd.foster.warehouse.services.IFeedTransferService;
import com.zd.foster.warehouse.services.IFeedWarehouseService;

/**
 * 类名：  FeedTransferService
 * 功能：
 * @author wxb
 * @date 2017-9-5下午04:43:23
 * @version 1.0
 * 
 */
public class FeedTransferServiceImpl extends BaseServiceImpl<FeedTransfer, IFeedTransferDao> implements
		IFeedTransferService {
	@Resource
	private IFeedTransferDtlService feedTransferDtlService;
	@Resource
	private ITransportCoService transportCoService;
	@Resource
	private IDriverService driverService;
	@Resource
	private IContractService contractService;
	@Resource
	private IFeedWarehouseService feedWarehouseService;
	@Resource
	private IFeedService feedService;
	@Resource
	private IFeedInWareService feedInWareService;
	@Resource
	private IFarmerService farmerService;
	@Resource
	private IFeedBillService feedBillService;
	
	/**
	 * 
	 * 功能:分页查询
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#selectAll(com.zd.epa.base.BaseEntity, com.zd.epa.utils.Pager)
	 */
	@Override
	public void selectAll(FeedTransfer entity, Pager<FeedTransfer> page) throws Exception {
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
		//返回对象page中的result设置运输公司名称和司机名称
		if(page!=null && page.getResult().size()!=0){
			Iterator<FeedTransfer> it=page.getResult().iterator();
			while(it.hasNext()){
				FeedTransfer e=it.next();
				if(e.getTransportCo()!=null){
					TransportCo tc=transportCoService.selectById(e.getTransportCo());
					if(tc!=null)
						e.setTransportCoName(tc.getName());
				}
				if(e.getDriver()!=null && !"".equals(e.getDriver())){
					Driver driver=driverService.selectById( e.getDriver());
					if(driver!=null)
						e.setDriverName(driver.getName());
				}
			}
		}
	}
	
	/**
	 * 
	 * 功能:返回对象设置运输公司名称和司机名称
	 * 重写:wxb
	 * 2017-7-31
	 * @see com.zd.epa.base.BaseServiceImpl#selectById(java.io.Serializable)
	 */
	public <ID extends Serializable> FeedTransfer selectById(ID PK)
			throws Exception {
		FeedTransfer e=super.selectById(PK);
		if(e.getTransportCo()!=null && !"".equals(e.getTransportCo())){
			TransportCo tc=transportCoService.selectById(e.getTransportCo());
			if(tc!=null)
				e.setTransportCoName(tc.getName());
		}
		if(e.getDriver()!=null && !"".equals(e.getDriver())){
			Driver driver=driverService.selectById( e.getDriver());
			if(driver!=null)
				e.setDriverName(driver.getName());
		}
		return e;
	}
	
	/**
	 * 
	 * 功能:保存转接单
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#save(com.zd.epa.base.BaseEntity)
	 */
	public Object save(FeedTransfer entity) throws Exception{
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
		List<FeedTransferDtl> ftddList = entity.getDetails();
		//2.1明细是否为空
		if(ftddList == null || ftddList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<ftddList.size();i++){
			FeedTransferDtl ftd = ftddList.get(i);
			//2.2.1验证数量是否为空，是否为非正数
			if(ftd.getQuantity()==null || "".equals(ftd.getQuantity()))
				buff.append("第"+(i+1)+"行数量不能为空<br>");
			else if(ArithUtil.comparison(ftd.getQuantity(), "0")<=0)
					buff.append("第"+(i+1)+"行数值必须为正数！<br>");
			//2.2.2检查副单位数量是否是"",如果是设为null
			if("".equals(ftd.getSubQuantity()))
				ftd.setSubQuantity(null);
			ftd.setFeedTransfer(entity);
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		entity.setCheckStatus("0");
		entity.setIsBalance("N");
		Object obj = super.save(entity);
		feedTransferDtlService.save(ftddList);
		return obj;
	}

	/**
	 * 
	 * 功能:修改转接单
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#update(com.zd.epa.base.BaseEntity)
	 */
	public void update(FeedTransfer entity)throws Exception{
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
		List<FeedTransferDtl> ftdList = entity.getDetails();
		//2.1明细是否为空
		if(ftdList == null || ftdList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<ftdList.size();i++){
			FeedTransferDtl ftd = ftdList.get(i);
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
		FeedTransfer e = super.selectById(entity.getId());
		// 2、将页面数据赋给数据库对象
//		e.setOutFarmer(entity.getOutFarmer());
//		e.setOutBatch(entity.getOutBatch());
		e.setInFarmer(entity.getInFarmer());
		e.setInBatch(entity.getInBatch());
		e.setRegistDate(entity.getRegistDate());
		e.setRemark(entity.getRemark());
		e.setTransportCo(entity.getTransportCo());
		e.setDriver(entity.getDriver());
		//保存明细
		//1、删除明细
		Map<String, String> _m = entity.getTempStack();
		if (null != _m && null != _m.get("deleteIds") && !"".equals(_m.get("deleteIds"))  ) {
			String[] str = _m.get("deleteIds").split(",");
			if(str != null){
				for(String id : str)
					feedTransferDtlService.deleteById(Integer.parseInt(id));
			}
		}
		//2. 新增/修改明细
		List<FeedTransferDtl> updateSwd = new ArrayList<FeedTransferDtl>(); 
		List<FeedTransferDtl> newSwd = new ArrayList<FeedTransferDtl>(); 
		for(FeedTransferDtl p : ftdList){
			if(p.getId()==null){
				p.setFeedTransfer(e);
				newSwd.add(p);
			}
			if(p.getId()!=null){
				updateSwd.add(p);
			}
		}
		//修改	
		for(FeedTransferDtl p : updateSwd){
			FeedTransferDtl ed = feedTransferDtlService.selectById(p.getId());
			ed.setQuantity(p.getQuantity());
			ed.setSubQuantity(p.getSubQuantity()==""?null:p.getSubQuantity());
		}
		updateSwd.clear();
		//添加
		feedTransferDtlService.save(newSwd);
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
		sqlMap.put("feedTransfer.id", "in", PapUtil.arrayToSQLStr((String[])PK));
		feedTransferDtlService.delete(sqlMap);
		return dao.deleteByIds(PK);
	}

	/**
	 * 
	 * 功能:审核
	 * 重写:wxb
	 * 2017-9-8
	 * @see com.zd.foster.warehouse.services.IFeedTransferService#check(java.lang.String[])
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
			FeedTransfer e = super.selectById(s);
			//1.1验证单据转出代养户，转入代养户合同是否在养殖或生效状态
			Contract ct=e.getOutBatch().getContract();
			if(ct.getStatus()==null || !("BREED".equals(ct.getStatus().getDcode())||!"EFFECT".equals(ct.getStatus().getDcode()))){
				throw new SystemException("单据【"+s+"】转出代养户的合同不在养殖或生效状态<br>");
			}
			Contract ct2=e.getInBatch().getContract();
			if(ct2.getStatus()==null || !(ct2.getStatus().getDcode().equals("BREED")||!ct2.getStatus().getDcode().equals("EFFECT"))){
				throw new SystemException("单据【"+s+"】转入代养户的合同不在养殖或生效状态<br>");
			}
			//2.获取明细并遍历明细
			List<FeedTransferDtl> edList = feedTransferDtlService.selectBySingletAll("feedTransfer.id", s);
			if(e==null ||edList==null || edList.size()==0)
				throw new SystemException("单据【"+s+"】对象为空或明细不存在<br>");
			//转出代养户扣库存，转入代养户生成入库单，增加库存
			//2.建立转入代养户领料单表头，明细
//			FeedOutWare fow=new FeedOutWare();
//			List<FeedOutWareDtl> fowdList=new ArrayList<FeedOutWareDtl>();
			FeedInWare fiw=new FeedInWare();
			List<FeedInWareDtl> fiwdList=new ArrayList<FeedInWareDtl>();
			List<FeedWarehouse> fwAllList = new ArrayList<FeedWarehouse>();
			for(FeedTransferDtl ed : edList){
				//2.1查询转出代养户该饲料库存(/***修改：库存带批次**/)
				sqlMap.put("farmer.id", "=", e.getOutFarmer().getId());
				sqlMap.put("feed.id", "=", ed.getFeed().getId());
				sqlMap.put("batch.id", "=", e.getOutBatch().getId()+"");
				List<FeedWarehouse> fwList = feedWarehouseService.selectHQL(sqlMap);
				sqlMap.clear();
				//
				sqlMap.put("farmer.id", "=", e.getInFarmer().getId());
				sqlMap.put("feed.id", "=", ed.getFeed().getId());
				sqlMap.put("batch.id", "=", e.getInBatch().getId()+"");
				List<FeedWarehouse> fwInList = feedWarehouseService.selectHQL(sqlMap);
				sqlMap.clear();
				//************
				//1.验证该饲料领料单是否够转接
				String hql_sum="select sum(f.quantity) from FeedInWareDtl f  " +
						" where f.feedInWare.farmer.id='"+e.getOutFarmer().getId()+"'" +
						" and f.feedInWare.batch.id='"+e.getOutBatch().getId()+"'" +
						" and f.feed.id='"+ed.getFeed().getId()+"' ";
				Double insum=dao.selectByAggregateHQL(hql_sum);
				if(insum.equals(0)|| insum<Double.parseDouble(ed.getQuantity()))
					throw new SystemException("单据【"+s+"】饲料【"+ed.getFeed().getName()+"】库存不够<br>");
				//2.判断剩余饲料量是否够量，是否要额外增加负耗料
				boolean flag2=false;//够出库
				Double slc=null;
				String hql_s="select sum(f.quantity) from FeedWarehouse f  " +
						" where f.farmer.id='"+e.getOutFarmer().getId()+"'" +
						" and f.batch.id='"+e.getOutBatch().getId()+"'";
				Double ins=dao.selectByAggregateHQL(hql_s);
				if(ins<Double.parseDouble(ed.getQuantity())){
					flag2=true;
					slc=ins-Double.parseDouble(ed.getQuantity());
				}
				//3.判断是否正常转接，还是非正常转接；非正常通过生成耗料单变成正常转接单
				boolean flag=false;//非正常转接 
				String czQty=null;
				FeedBill fow=new FeedBill();
				List<FeedBillDtl> fowdList=new ArrayList<FeedBillDtl>();
				if(fwList==null || fwList.size()==0){
					flag=true;
					czQty=ed.getQuantity();
					if(flag2){
						//该饲料全负耗料，剩余饲料全出库
						String quantity=ArithUtil.mul(ed.getQuantity(), "-1");
						fhl(fowdList, fow, ed, quantity);
						fh2(fowdList, e, fow, ed, null, true);
						
						hlbt(fow, e, fowdList);
						Object obj=feedBillService.save(fow);
						feedBillService.check((String[])obj);
						
					}else{
						//该饲料全负耗料，剩余饲料接连出库(ed.getQuantity())
						String quantity=ArithUtil.mul(ed.getQuantity(), "-1");
						fhl(fowdList, fow, ed, quantity);
						fh2(fowdList, e, fow, ed, ed.getQuantity(), false);
						hlbt(fow, e, fowdList);
						Object obj=feedBillService.save(fow);
						feedBillService.check((String[])obj);
					}
				}else{
					FeedWarehouse fw = fwList.get(0);
					if(fw != null){
						//原始数据
						String q_old = fw.getQuantity();
						//入库单数据
						String q_new = ed.getQuantity();
						if(q_old==null || "".equals(q_old))
							q_old = "0";
						if(q_new==null || "".equals(q_new))
							q_new = "0";
						if(ArithUtil.comparison(q_old, q_new)<0){
							flag=true;
							czQty=ArithUtil.sub(q_new, q_old);
							if(flag2){
								//该饲料（czQty）负耗料，剩余饲料全出库
								fhl(fowdList, fow, ed, ArithUtil.mul(czQty, "-1"));
								fh2(fowdList, e, fow, ed, null, true);
								hlbt(fow, e, fowdList);
								Object obj=feedBillService.save(fow);
								String[] idArray=new String[]{(String)obj};
								feedBillService.check(idArray);
							}else{
								//该饲料(czQty)负耗料，剩余饲料接连出库(czQty)
								fhl(fowdList, fow, ed,ArithUtil.mul(czQty, "-1"));
								fh2(fowdList, e, fow, ed, czQty, false);
								hlbt(fow, e, fowdList);
								Object obj=feedBillService.save(fow);
								String[] idArray=new String[]{(String)obj};
								feedBillService.check(idArray);
							}
						}
					}
				}
				//4.以下正常转接	
				sqlMap.put("farmer.id", "=", e.getOutFarmer().getId());
				sqlMap.put("feed.id", "=", ed.getFeed().getId());
				sqlMap.put("batch.id", "=", e.getOutBatch().getId()+"");
				fwList = feedWarehouseService.selectHQL(sqlMap);
				sqlMap.clear();
				//***8***8********************************************************
				//2.2判断库存是否存在
				if(fwList==null || fwList.size()==0){
					throw new SystemException("单据【"+s+"】饲料【"+ed.getFeed().getId()+"】没有库存<br>");
				}else{
					//2.3该饲料库存是否足够出库，足够则减库存,建饲料出库单
					FeedWarehouse fw = fwList.get(0);
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
							throw new SystemException("单据【"+s+"】饲料【"+fw.getFeed().getName()+"】库存不够<br>");
						}
//						//2.3.2建立饲料出库单明细
//						FeedOutWareDtl fowd=new FeedOutWareDtl();
//						fowd.setFeedOutWare(fow);
//						fowd.setFeed(ed.getFeed());
//						fowd.setQuantity(ed.getQuantity());
//						fowd.setSubQuantity(ed.getSubQuantity());
//						fowdList.add(fowd);
						//2.3.3扣库存
						String num = ArithUtil.sub(q_old, q_new,2);
						fw.setQuantity(num);
						//2.3.4添加副单位数量
						Feed feed=feedService.selectById(ed.getFeed().getId());
						String ratio=feed.getRatio();
						if(ratio!=null && !"".equals(ratio))
							fw.setSubQuantity(ArithUtil.div(num, ratio, 2));
						feedWarehouseService.save(fw);
						//2.3.5验证转入代养户是否有该饲料库存,并添加或更改库存
						if(fwInList==null || fwInList.size()==0){
							FeedWarehouse fw2 = new FeedWarehouse();
							fw2.setCompany(e.getCompany());
							fw2.setFarmer(e.getInFarmer());
							fw2.setBatch(e.getInBatch());
							fw2.setFeed(ed.getFeed());
							fw2.setQuantity(ed.getQuantity());
							fw2.setSubQuantity(ed.getSubQuantity());
							fwAllList.add(fw2);
						}else{
							//2.2.2库存有该饲料库存
							FeedWarehouse fw2 = fwInList.get(0);
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
								Feed feed2=feedService.selectById(ed.getFeed().getId());
								String ratio2=feed2.getRatio();
								if(ratio2!=null && !"".equals(ratio2))
									fw2.setSubQuantity(ArithUtil.div(num2, ratio2, 2));
							}
							feedWarehouseService.save(fw2);
						}
						//2.3.5增加入库单明细
						FeedInWareDtl fiwd=new FeedInWareDtl();
						fiwd.setFeedInWare(fiw);
						fiwd.setFeed(ed.getFeed());
						fiwd.setQuantity(ed.getQuantity());
						fiwd.setSubQuantity(ed.getSubQuantity());
						fiwdList.add(fiwd);
					}
				}
			}
			if(fwAllList !=null && fwAllList.size()>0)
				feedWarehouseService.save(fwAllList);
//			//3.1保存饲料出库单表头和明细
//			fow.setCompany(e.getCompany());
//			fow.setFarmer(e.getOutFarmer());
//			fow.setBatch(e.getOutBatch());
//			fow.setRegistDate(e.getRegistDate());
//			fow.setFeedTransfer(s);
//			fow.setCheckDate(PapUtil.date(new Date()));
//			fow.setCheckStatus("1");
//			fow.setCheckUser(u.getUserRealName());
//			feedOutWareService.save(fow);
//			feedOutWareDtlService.save(fowdList);
			//3.2保存饲料入库单表头和明细
			fiw.setCompany(e.getCompany());
			fiw.setFarmer(e.getInFarmer());
			fiw.setBatch(e.getInBatch());
			fiw.setRegistDate(e.getRegistDate());
			fiw.setFeedTransfer(s);
			fiw.setTransportCo(e.getTransportCo());
			fiw.setDriver(e.getDriver());
			fiw.setCheckDate(PapUtil.date(new Date()));
			fiw.setCheckUser(u.getUserRealName());
			fiw.setDetails(fiwdList);
			feedInWareService.save(fiw);
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
	 * 功能:撤销
	 * 重写:wxb
	 * 2017-9-8
	 * @see com.zd.foster.warehouse.services.IFeedTransferService#cancelCheck(java.lang.String)
	 */
	@Override
	public void cancelCheck(String id) throws Exception {
		if(id == null || "".equals(id))
			throw new SystemException("请选择单据");
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		//1.获取对象
		FeedTransfer e = super.selectById(id);
		if(e.getIsBalance().equals("Y"))
			throw new SystemException("单据【"+id+"】已经结算<br>");
		Contract ct=e.getOutBatch().getContract();
		if(ct.getStatus().getDcode().equals("LOST"))
			throw new SystemException("单据【"+id+"】转出代养户合同已经终止<br>");
		Contract ct2=e.getInBatch().getContract();
		if(ct2.getStatus().getDcode().equals("LOST"))
			throw new SystemException("单据【"+id+"】转入代养户合同已经终止<br>");
		//2.获取明细并遍历
		List<FeedTransferDtl> edList = feedTransferDtlService.selectBySingletAll("feedTransfer.id", id);
		if(e==null ||edList==null || edList.size()==0)
			throw new SystemException("单据【"+id+"】对象为空或明细不存在");
		for(FeedTransferDtl ed : edList){
			//2.1查询饲料的库存(/***修改：库存带批次**/)
			sqlMap.put("farmer.id", "=", e.getInFarmer().getId());
			sqlMap.put("feed.id", "=", ed.getFeed().getId());
			sqlMap.put("batch.id", "=", e.getInBatch().getId()+"");
			List<FeedWarehouse> fwList = feedWarehouseService.selectHQL(sqlMap);
			sqlMap.clear();
			//
			sqlMap.put("farmer.id", "=", e.getOutFarmer().getId());
			sqlMap.put("feed.id", "=", ed.getFeed().getId());
			sqlMap.put("batch.id", "=", e.getOutBatch().getId()+"");
			List<FeedWarehouse> fwOutList = feedWarehouseService.selectHQL(sqlMap);
			sqlMap.clear();
			//2.2转入代养户无库存，不允许撤销
			if(fwList==null || fwList.size()==0){
				throw new SystemException("转入代养户库存不够撤销！");
			}else{
				FeedWarehouse fw = fwList.get(0);
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
					Feed feed=feedService.selectById(ed.getFeed().getId());
					String ratio=feed.getRatio();
					if(ratio!=null && !"".equals(ratio))
						fw.setSubQuantity(ArithUtil.div(num, ratio, 2));
					feedWarehouseService.save(fw);
					//转出代养户增加库存
					if(fwOutList==null || fwOutList.size()==0){
						FeedWarehouse fw2 = new FeedWarehouse();
						fw2.setCompany(e.getCompany());
						fw2.setFarmer(e.getInFarmer());
						fw2.setBatch(e.getInBatch());
						fw2.setFeed(ed.getFeed());
						fw2.setQuantity(ed.getQuantity());
						fw2.setSubQuantity(ed.getSubQuantity());
						feedWarehouseService.save(fw2);
					}else{
						//2.2.2库存有该饲料库存
						FeedWarehouse fw2 = fwOutList.get(0);
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
							Feed feed2=feedService.selectById(ed.getFeed().getId());
							String ratio2=feed2.getRatio();
							if(ratio2!=null && !"".equals(ratio2))
								fw2.setSubQuantity(ArithUtil.div(num2, ratio2, 2));
							feedWarehouseService.save(fw2);
						}
					}
				}
			}
		}
		//5.删除饲料入库单
		FeedInWare fiw=feedInWareService.selectByHQLSingle("from FeedInWare e where e.feedTransfer='"+id+"'");
		feedInWareService.deleteById(fiw.getId());
		//**************************
		//撤销喂料单
		List<FeedBill> fbList=feedBillService.selectByHQL("from FeedBill e where e.feedTransfer='"+e.getId()+"'");
		Iterator<FeedBill> it=fbList.iterator();
		while(it.hasNext()){
			String fbid=it.next().getId();
			feedBillService.cancelCheck(fbid);
			feedBillService.deleteById(fbid);
		}
		//*****************************
		//5.撤销审核状态
		e.setCheckDate(null);
		e.setCheckStatus("0");
		e.setCheckUser(null);
		
	}

	/**
	 * 
	 * 功能:耗料明细
	 * @author:wxb
	 * @data:2017-11-7下午04:08:54
	 * @file:FeedTransferServiceImpl.java
	 * @param fow
	 * @throws Exception
	 */
	private FeedBillDtl hlmx(FeedBill fow,String quantity,String subQuantity,Feed feed)throws Exception{
		FeedBillDtl fowd=new FeedBillDtl();
		fowd.setFeedBill(fow);
		fowd.setFeed(feed);
		fowd.setQuantity(quantity);
		fowd.setSubQuantity(subQuantity);
		return fowd;
	}
	/**
	 * 
	 * 功能:耗料单表头
	 * @author:wxb
	 * @data:2017-11-7下午03:57:53
	 * @file:FeedTransferServiceImpl.java
	 * @throws Exception
	 */
	private void hlbt(FeedBill fow,FeedTransfer e,List<FeedBillDtl> fowdList)throws Exception{
		fow.setCompany(e.getCompany());
		fow.setFarmer(e.getOutFarmer());
		fow.setBatch(e.getOutBatch());
		fow.setRegistDate(e.getRegistDate());
		fow.setRemark("转接自动匹配");
		fow.setFeedTransfer(e.getId());
		fow.setDetails(fowdList);
	}
	
	/**
	 * 
	 * 功能:该饲料负耗料
	 * @author:wxb
	 * @data:2017-11-7下午08:51:46
	 * @file:FeedTransferServiceImpl.java
	 * @param fowdList
	 * @param fow
	 * @param ed
	 * @throws Exception
	 */
	private void fhl(List<FeedBillDtl> fowdList,FeedBill fow, FeedTransferDtl ed,String quantity )throws Exception{
		Feed feed=feedService.selectById(ed.getFeed().getId());
		String ratio=feed.getRatio();
//		String quantity=ArithUtil.mul(ed.getQuantity(), "-1");
		FeedBillDtl fowd=hlmx(fow, quantity, ArithUtil.div(quantity, ratio, 2), ed.getFeed());
		fowdList.add(fowd);
	}
	private void fh2(List<FeedBillDtl> fowdList,FeedTransfer e,FeedBill fow, FeedTransferDtl ed,String shengyu,boolean flag)throws Exception{
		String hql_to=" from FeedWarehouse f  " +
			" where f.farmer.id='"+e.getOutFarmer().getId()+"'" +
			" and f.batch.id='"+e.getOutBatch().getId()+"'";
		List<FeedWarehouse> fwallList=feedWarehouseService.selectByHQL(hql_to);
		Feed feed=feedService.selectById(ed.getFeed().getId());
		String ratio=feed.getRatio();
		if(flag){
			//全部耗掉
			Iterator<FeedWarehouse> it=fwallList.iterator();
			while(it.hasNext()){
				FeedWarehouse fwh=it.next();
				if(fwh.getQuantity().equals("0") || ArithUtil.comparison(fwh.getQuantity(), "0")==0)
					continue;
				FeedBillDtl fowd1=hlmx(fow, fwh.getQuantity(), fwh.getSubQuantity(), fwh.getFeed());
				fowdList.add(fowd1);
			}
		}else{
			//先耗同类型饲料
			String hql_tong="from FeedWarehouse e where e.farmer.id='"+e.getOutFarmer().getId()+"'" +
					" and e.batch.id='"+e.getOutBatch().getId()+"'" +
					" and e.feed.feedType.id='"+ed.getFeed().getFeedType().getId()+"'" +
					" and e.feed.id<>'"+ed.getFeed().getId()+"'";
			FeedWarehouse fw3=feedWarehouseService.selectByHQLSingle(hql_tong);
			if(fw3!=null){
				String a=fw3.getQuantity();
				if(ArithUtil.comparison(shengyu, a)<=0){
					//已经够了
					FeedBillDtl fowd1=hlmx(fow, shengyu, ArithUtil.div(shengyu, ratio, 2), fw3.getFeed());
					fowdList.add(fowd1);
					return;
				}else{
					FeedBillDtl fowd1=hlmx(fow, a, ArithUtil.div(a, ratio, 2), fw3.getFeed());
					fowdList.add(fowd1);
				}
				shengyu=ArithUtil.sub(shengyu, a);
				String hql_2="from FeedWarehouse e where e.farmer.id='"+e.getOutFarmer().getId()+"'" +
					" and e.batch.id='"+e.getOutBatch().getId()+"'" +
					" and e.feed.feedType.id<>'"+ed.getFeed().getFeedType().getId()+"'";
				List<FeedWarehouse> fwallList2 =feedWarehouseService.selectByHQL(hql_2);
				Iterator<FeedWarehouse> it=fwallList2.iterator();
				while(it.hasNext()){
					FeedWarehouse fwh=it.next();
					if(fwh.getQuantity().equals("0") || ArithUtil.comparison(fwh.getQuantity(), "0")==0)
						continue;
					Feed feed2=feedService.selectById(fwh.getFeed().getId());
					String ratio2=feed2.getRatio();
					if(ArithUtil.comparison(shengyu, fwh.getQuantity())<=0){
						//出剩余
						FeedBillDtl fowd1=hlmx(fow, shengyu, ArithUtil.div(shengyu, ratio2, 2), fwh.getFeed());
						fowdList.add(fowd1);
						return;
					}else{
						//出所有
						FeedBillDtl fowd1=hlmx(fow, fwh.getQuantity(), fwh.getSubQuantity(), fwh.getFeed());
						fowdList.add(fowd1);
						shengyu=ArithUtil.sub(shengyu, fwh.getQuantity());
					}
				}
				}else{
					
					Iterator<FeedWarehouse> it=fwallList.iterator();
					while(it.hasNext()){
						FeedWarehouse fwh=it.next();
						if(fwh.getQuantity().equals("0") || ArithUtil.comparison(fwh.getQuantity(), "0")==0)
							continue;
						Feed feed2=feedService.selectById(fwh.getFeed().getId());
						String ratio2=feed2.getRatio();
						if(ArithUtil.comparison(shengyu, fwh.getQuantity())<=0){
							//出剩余
							FeedBillDtl fowd1=hlmx(fow, shengyu, ArithUtil.div(shengyu, ratio2, 2), fwh.getFeed());
							fowdList.add(fowd1);
							return;
						}else{
							//出所有
							FeedBillDtl fowd1=hlmx(fow, fwh.getQuantity(), fwh.getSubQuantity(), fwh.getFeed());
							fowdList.add(fowd1);
							shengyu=ArithUtil.sub(shengyu, fwh.getQuantity());
						}
					}
					
				}
			
		}
		
	}
	
}
