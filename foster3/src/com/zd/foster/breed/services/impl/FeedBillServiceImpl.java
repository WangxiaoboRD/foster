package com.zd.foster.breed.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
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
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.breed.dao.IFeedBillDao;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.breed.entity.FeedBill;
import com.zd.foster.breed.entity.FeedBillDtl;
import com.zd.foster.breed.services.IBatchService;
import com.zd.foster.breed.services.IFeedBillDtlService;
import com.zd.foster.breed.services.IFeedBillService;
import com.zd.foster.contract.entity.Contract;
import com.zd.foster.material.entity.Feed;
import com.zd.foster.material.services.IFeedService;
import com.zd.foster.warehouse.entity.FeedWarehouse;
import com.zd.foster.warehouse.services.IFeedWarehouseService;

/**
 * 类名：  FeedBillServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-7-27上午08:58:32
 * @version 1.0
 * 
 */
public class FeedBillServiceImpl extends BaseServiceImpl<FeedBill, IFeedBillDao> implements
		IFeedBillService {
	@Resource
	private IFeedBillDtlService feedBillDtlService;
	@Resource
	private IFeedWarehouseService feedWarehouseService;
	@Resource
	private IFeedService feedService;
	@Resource
	private JDBCWrapperx jdbc;
	@Resource
	private IFarmerService farmerService;
	@Resource
	private IBatchService batchService;
	
	/**
	 * 
	 * 功能:分页查询
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#selectAll(com.zd.epa.base.BaseEntity, com.zd.epa.utils.Pager)
	 */
	@Override
	public void selectAll(FeedBill entity, Pager<FeedBill> page) throws Exception {
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
	 * 功能:保存喂料单
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#save(com.zd.epa.base.BaseEntity)
	 */
	public Object save(FeedBill entity) throws Exception{
		//1验证表头
		//1.1表头是否为空
		if(entity == null)
			throw new SystemException("对象不能为空");
//		//1.2养殖公司是否为空
//		if(entity.getCompany()==null || entity.getCompany().getId()==null || "".equals(entity.getCompany().getId()))
//			throw new SystemException("养殖公司不能为空！");
		//1.3代养户是否为空
		if(entity.getFarmer()==null || entity.getFarmer().getId()==null || "".equals(entity.getFarmer().getId()))
			throw new SystemException("代养户不能为空！");
		//1.3获取养殖公司
		Farmer farmer = farmerService.selectById(entity.getFarmer().getId());
		entity.setCompany(farmer.getCompany());
		//1.4批次是否为空
		if(entity.getBatch()==null || entity.getBatch().getId()==null || "".equals(entity.getBatch().getId()))
			throw new SystemException("批次不能为空！");
		//1.5入库时间是否为空
		if(entity.getRegistDate()==null || "".equals(entity.getRegistDate()))
			throw new SystemException("喂料时间不能为空！");
		//2验证明细
		List<FeedBillDtl> fbdList = entity.getDetails();
		//2.1明细是否为空
		if(fbdList == null || fbdList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<fbdList.size();i++){
			FeedBillDtl fbd = fbdList.get(i);
			//2.2.1验证数量是否为空，是否为非正数
			if(fbd.getQuantity()==null || "".equals(fbd.getQuantity()))
				buff.append("第"+(i+1)+"行数量不能为空<br>");
			else if(ArithUtil.comparison(fbd.getQuantity(), "0")==0)
					buff.append("第"+(i+1)+"行数值必须为正数！<br>");
			//2.2.2检查副单位数量是否是"",如果是设为null
			if("".equals(fbd.getSubQuantity()))
				fbd.setSubQuantity(null);
			fbd.setFeedBill(entity);
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		entity.setCheckStatus("0");
		entity.setIsBalance("N");
		Object obj = super.save(entity);
		feedBillDtlService.save(fbdList);
		return obj;
	}
	
	/**
	 * 
	 * 功能:修改喂料单
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#update(com.zd.epa.base.BaseEntity)
	 */
	public void update(FeedBill entity)throws Exception{
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
		//1.5入库时间是否为空
		if(entity.getRegistDate()==null || "".equals(entity.getRegistDate()))
			throw new SystemException("入库时间不能为空！");
		//2验证明细
		List<FeedBillDtl> fbdList = entity.getDetails();
		//2.1明细是否为空
		if(fbdList == null || fbdList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<fbdList.size();i++){
			FeedBillDtl fbd = fbdList.get(i);
			//2.2.1验证数量是否为空，是否为非正
			if(fbd.getQuantity()==null || "".equals(fbd.getQuantity()))
				buff.append("第"+(i+1)+"行数量不能为空<br>");
			else if(ArithUtil.comparison(fbd.getQuantity(), "0")==0)
				buff.append("第"+(i+1)+"行数值必须为正数！<br>");
			//2.2.2检查副单位数量是否是"",如果是设为null
			if("".equals(fbd.getSubQuantity()))
				fbd.setSubQuantity(null);
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		// 保存表头
		// 1 、获取数据库里的对象
		FeedBill e = super.selectById(entity.getId());
		// 2、将页面数据赋给数据库对象
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
					feedBillDtlService.deleteById(Integer.parseInt(id));
			}
		}
		//2. 新增/修改明细
		List<FeedBillDtl> updateSwd = new ArrayList<FeedBillDtl>(); 
		List<FeedBillDtl> newSwd = new ArrayList<FeedBillDtl>(); 
		for(FeedBillDtl p : fbdList){
			if(p.getId()==null){
				p.setFeedBill(e);
				newSwd.add(p);
			}
			if(p.getId()!=null){
				updateSwd.add(p);
			}
		}
		//修改	
		for(FeedBillDtl p : updateSwd){
			FeedBillDtl ed = feedBillDtlService.selectById(p.getId());
			ed.setQuantity(p.getQuantity());
			ed.setSubQuantity(p.getSubQuantity()==""?null:p.getSubQuantity());
		}
		updateSwd.clear();
		//添加
		feedBillDtlService.save(newSwd);
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
		sqlMap.put("feedBill.id", "in", PapUtil.arrayToSQLStr((String[])PK));
		feedBillDtlService.delete(sqlMap);
		return dao.deleteByIds(PK);
	}
	
	/**
	 * 
	 * 功能:审核
	 * @author:wxb
	 * @data:2017-7-31下午04:43:02
	 * @file:FeedBillServiceImpl.java
	 * @param idArr
	 * @throws Exception
	 */
	public void check(String[] idArr)throws Exception{
		if(idArr == null || idArr.length == 0)
			throw new SystemException("请选择单据");
		Users u = SysContainer.get();
		StringBuffer sb = new StringBuffer();
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		for(String s : idArr){
			//1.获取对象
			FeedBill e = super.selectById(s);

			if(e.getIsBalance().equals("Y"))
				throw new SystemException("单据【"+s+"】已经结算<br>");
			//获取批次
			Batch b = e.getBatch();
			if(b==null){
				sb.append("单据"+s+"批次不存在<br>");
				continue;
			}
			//验证合同是不是已经终止 中止 只有养殖状态才能用
			String dcode = "";
			Contract co = b.getContract();
			if(co != null && co.getStatus() != null)
				dcode = co.getStatus().getDcode();
			if(!"BREED".equals(dcode)){
				sb.append("单据"+s+"合同不在养殖状态<br>");
				continue;
			}
			//2.获取明细并遍历明细
			List<FeedBillDtl> edList = feedBillDtlService.selectBySingletAll("feedBill.id", s);
			if(e==null ||edList==null || edList.size()==0)
				sb.append("单据【"+s+"】对象为空或明细不存在<br>");
			//2.**建立饲料出库单表头
//			FeedOutWare fow=new FeedOutWare();
//			List<FeedOutWareDtl> fowdList=new ArrayList<FeedOutWareDtl>();
			for(FeedBillDtl ed : edList){
				//2.1查询代养户该饲料库存(/***修改：库存带批次**/)
				sqlMap.put("farmer.id", "=", e.getFarmer().getId());
				sqlMap.put("feed.id", "=", ed.getFeed().getId());
				sqlMap.put("batch.id", "=", e.getBatch().getId()+"");
				List<FeedWarehouse> fwList = feedWarehouseService.selectHQL(sqlMap);
				sqlMap.clear();
				//2.2判断库存是否存在
				if(fwList==null || fwList.size()==0){
					sb.append("单据【"+s+"】饲料【"+ed.getFeed().getId()+"】没有库存<br>");
					continue;
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
							sb.append("单据【"+s+"】"+e.getFarmer().getName()+"第【"+e.getBatch().getBatchNumber()+"】批饲料【"+fw.getFeed().getName()+"】库存不够<br>");
							continue;
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
					}
				}
			}
//			//3.1保存饲料出库单表头和明细
//			fow.setCompany(e.getCompany());
//			fow.setFarmer(e.getFarmer());
//			fow.setBatch(e.getBatch());
//			fow.setRegistDate(e.getRegistDate());
//			fow.setFeedBill(e);
//			fow.setCheckDate(PapUtil.date(new Date()));
//			fow.setCheckStatus("1");
//			fow.setCheckUser(u.getUserRealName());
//			feedOutWareService.save(fow);
//			feedOutWareDtlService.save(fowdList);
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
	 * 2017-7-31
	 * @see com.zd.foster.breed.services.IFeedBillService#cancelCheck(java.lang.String)
	 */
	@Override
	public void cancelCheck(String id) throws Exception {
		if(id == null || "".equals(id))
			throw new SystemException("请选择单据");
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		//1.获取对象
		FeedBill e = super.selectById(id);
		if(e.getIsBalance().equals("Y"))
			throw new SystemException("单据【"+id+"】已经结算<br>");

		//获取批次
		Batch b = e.getBatch();
		if(b==null)
			throw new SystemException("单据"+id+"批次不存在<br>");
		//验证合同是不是已经终止 中止 只有养殖状态才能用
		String dcode = "";
		Contract co = b.getContract();
		if(co != null && co.getStatus() != null)
			dcode = co.getStatus().getDcode();
		if(!"BREED".equals(dcode))
			throw new SystemException("单据"+id+"合同不在养殖状态<br>");
		
		//2.获取明细并遍历
		List<FeedBillDtl> edList = feedBillDtlService.selectBySingletAll("feedBill.id", id);
		if(e==null ||edList==null || edList.size()==0)
			throw new SystemException("单据【"+id+"】对象为空或明细不存在");
		List<FeedWarehouse> fwAllList = new ArrayList<FeedWarehouse>();
		for(FeedBillDtl ed : edList){
			//2.1查询饲料的库存(/***修改：库存带批次**/)
			sqlMap.put("farmer.id", "=", e.getFarmer().getId());
			sqlMap.put("feed.id", "=", ed.getFeed().getId());
			sqlMap.put("batch.id", "=", e.getBatch().getId()+"");
			List<FeedWarehouse> fwList = feedWarehouseService.selectHQL(sqlMap);
			sqlMap.clear();
			//2.2没有库存新建库存
			if(fwList==null || fwList.size()==0){
				FeedWarehouse fw = new FeedWarehouse();
				fw.setCompany(e.getCompany());
				fw.setFarmer(e.getFarmer());
				fw.setBatch(e.getBatch());
				fw.setFeed(ed.getFeed());
				fw.setQuantity(ed.getQuantity());
				fw.setSubQuantity(ed.getSubQuantity());
				fwAllList.add(fw);
			}else{
				//2.3有库存则增加库存
				FeedWarehouse fw=fwList.get(0);
				//2.3.1.调整数量
				String newNum = ArithUtil.add(fw.getQuantity(), ed.getQuantity());
				fw.setQuantity(newNum);
				//2.3.2添加副单位数量
				Feed feed=feedService.selectById(ed.getFeed().getId());
				String ratio=feed.getRatio();
				if(ratio!=null && !"".equals(ratio))
					fw.setSubQuantity(ArithUtil.div(newNum, ratio, 2));
			}
		}
		//3.保存新的饲料库存
		if(fwAllList !=null && fwAllList.size()>0)
			feedWarehouseService.save(fwAllList);
//		//4.删除饲料出库单
//		FeedOutWare fow=feedOutWareService.selectByHQLSingle("from FeedOutWare e where e.feedBill.id='"+id+"'");
//		feedOutWareService.deleteById(fow.getId());
		//5.撤销审核状态
		e.setCheckDate(null);
		e.setCheckStatus("0");
		e.setCheckUser(null);
	}

	@Override
	public Map<String, Object> selectFeedAnalysisByPage(FeedBill entity,Pager<FeedBill> page) throws Exception {
		//1.确定养殖公司，代养户，批次，批次开始日期，批次开始日龄；
		//2.循环查询（日龄不超过175）
		//3.每次查询该日期的总喂料量，喂料类别，计算累计喂料量/另查询该日期标准喂料类别，喂料量，累计喂料量
		//4.如果当日查询不到，算下次喂料单的平均值
		
		Map<String, Object> result = new HashMap<String, Object>(); // 返回结果集
		int totalRows = 0; // 统计总行数
		//1.确定查询值/公司/代养户/开始日期/开始日龄
		String company=null,farmer=null,beginDate=null,beginDayAge=null;
		Integer batch=null;
		if(entity.getCompany()!=null && entity.getCompany().getId()!=null && !"".equals(entity.getCompany().getId()))
			company=entity.getCompany().getId();
		else
			throw new SystemException("请选择养殖公司！");
		if(entity.getFarmer()!=null && entity.getFarmer().getId()!=null && !"".equals(entity.getFarmer().getId()))
			farmer=entity.getFarmer().getId();
		else
			throw new SystemException("请选择代养户！");
		if(entity.getBatch()!=null && entity.getBatch().getId()!=null && !"".equals(entity.getBatch().getId()))
			batch=entity.getBatch().getId();
		else
			throw new SystemException("请选择养殖批次！");
		//查询该批次的中文字段
		String batchSql="select c.name as companyName,f.name as farmerName,t.batchnumber from FS_BATCH t"+
			" join fs_company c on c.id=t.company"+
			" join fs_farmer f on f.id=t.farmer"+
			" where t.id=?";
		//查询猪苗采购确定初始日期和日龄
		String purchaseSql="select min(t.registdate),trunc(avg(t.days)) from FS_PIGPURCHASE t where t.batch=?";
		//实际每日喂料量
		String acDayFeedSql="select sum(t.quantity)from fs_feedbilldtl t"+
			" join fs_feedbill fb on fb.id=t.feedbill"+
			" where fb.batch=?"+
			" and fb.registdate=?"+
			" and fb.checkstatus=1";
//		//判断饲料类型是否唯一（唯一查询饲料类型，不唯一显示混合）
//		String jdFeedTypeSql="select count(rownum) from("+
//			" select f.feedtype from fs_feedbilldtl t"+
//			" join fs_feedbill fb on fb.id=t.feedbill"+
//			" join fs_feed f on f.id=t.feed"+
//			" where fb.batch=?"+
//			" and fb.registdate=?"+
//			" group by f.feedtype)";
		//查询饲料类型（得到复合字符串）
		String acFeedTypeSql="select wm_concat(ft.name) from FS_FEEDBILLDTL t"+
			" join fs_feedbill fb on fb.id=t.feedbill"+
			" join fs_feed f on f.id=t.feed"+
			" join fs_feedtype ft on ft.id=f.feedtype"+
			" where fb.batch=?"+
			" and fb.registdate=?";
		//实际累计喂料量
		String acTolFeedSql="select sum(t.quantity)from fs_feedbilldtl t"+
			" join fs_feedbill fb on fb.id=t.feedbill"+
			" where fb.batch=?"+
			" and fb.registdate<=?"+
			" and fb.checkstatus=1";
		//标准喂料量
		String sdFeedSql="select ft.name,t.feedweight from FS_GROWSTANDDTL t"+
			" join fs_growstand g on g.id=t.growstand"+
			" join fs_feedtype ft on ft.id=t.feedtype"+
			" where g.company=?"+
			" and t.days=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Map<String, Object>> objs = new ArrayList<Map<String, Object>>();
		try{
			conn=jdbc.getConnection();
			//2.查询小猪入库单，确定批次开始日期，批次开始日龄（第一次入库日期为开始日期，平均日龄为开始日龄）
			String[] arr1=this.startFeed(conn, purchaseSql, batch);
			if(arr1!=null){
				beginDate=arr1[0];
				beginDayAge=arr1[1];
			}else
				throw new SystemException("该批次还没有购进猪苗！");
			//3.公司名称，代养户名称，批次名称
			String companyName=null,farmerName=null,batchNumber=null;
			String[] arr2=this.startFeed(conn, batchSql, batch);
			if(arr2!=null){
				companyName=arr2[0];
				farmerName=arr2[1];
				batchNumber=arr2[2];
			}
			//4.分页查询,确定分页查询的开始日期和结束日期
			int startRow = (page.getPageNo()-1)*page.getPageSize(); // 分页开始行
			int endRow = startRow + page.getPageSize(); // 分页结束行
			//5.遍历饲养的每一天(<175)
			//是否是单独一天的喂料量
			boolean flag=true;
			//上次喂料日期/上次喂料日龄
			String lastHavDate=PapUtil.getDayNoTime(-1, beginDate),lastHavDays=ArithUtil.sub(beginDayAge, "1", 0);
			//上次喂料时饲养天数/累计未喂料天数(显示内)/i从1开始循环/最后一次喂料的i值/累计未喂料天数（总天数）
			int lastHavFeedDays=0,noFeedDays=0,i=0,lastI=0,totalNoFeedDays=0;
			while(i<175){
				//查询日期
				String searchDate=PapUtil.getDayNoTime(i, beginDate);
				//查询日龄
				String searchDays=ArithUtil.add(beginDayAge, i+"", 0);
				//饲养天数
				int feedDays=i+1;
				//每日喂料量
				String acDayFeedQty=acDayFeedQty(conn, acDayFeedSql, batch, searchDate);
				//需要显示的分页内容
				if(i>=startRow && i<endRow){
					if(acDayFeedQty!=null){
						if(flag){
							//查询值为一天的喂料量
							Map<String, Object> map = new HashMap<String, Object>();
							String[] arr=sdFeed(conn, sdFeedSql, company, searchDays);
							String acFeedType=acDayFeedQty(conn, acFeedTypeSql, batch, searchDate);
							String actotalFeedQty=acDayFeedQty(conn, acTolFeedSql, batch, searchDate);
							map.put("companyName", companyName);
							map.put("farmerName", farmerName);
							map.put("batchNumber", batchNumber);
							map.put("feedDate", searchDate);
							map.put("feedDays", feedDays);
							map.put("dayAge", searchDays);
							map.put("sdFeedType", arr[0]);
							map.put("sdFeedQty", arr[1]);
							map.put("acFeedType", acFeedType);
							map.put("acFeedQty", acDayFeedQty);
							map.put("acTolFeedQty", actotalFeedQty);
							objs.add(map);
						}else{
							//查询值为多天的喂料量，求平均值
							//给空缺的天数都附上值
							String avgFeedQty=ArithUtil.div(acDayFeedQty,(totalNoFeedDays+1)+"" , 2);
							String acFeedType=acDayFeedQty(conn, acFeedTypeSql, batch, searchDate);
							String actotalFeedQty=acDayFeedQty(conn, acTolFeedSql, batch, searchDate);
							//从上次喂料后一天开始循环
							for(int j=0;j<=noFeedDays;j++){
								Map<String, Object> map = new HashMap<String, Object>();
								String[] arr=sdFeed(conn, sdFeedSql, company, ArithUtil.add((j+1)+"", lastHavDays, 0));
								String _actotalFeedQty=ArithUtil.sub(actotalFeedQty, ArithUtil.mul(avgFeedQty, (noFeedDays-j)+""), 2);
								map.put("companyName", companyName);
								map.put("farmerName", farmerName);
								map.put("batchNumber", batchNumber);
								map.put("feedDate", PapUtil.getDayNoTime(j+1+(totalNoFeedDays-noFeedDays), lastHavDate));
								map.put("feedDays", lastHavFeedDays+j+1+(totalNoFeedDays-noFeedDays));
								map.put("dayAge", ArithUtil.add((j+1+totalNoFeedDays-noFeedDays)+"", lastHavDays, 0));
								map.put("sdFeedType", arr[0]);
								map.put("sdFeedQty", arr[1]);
								map.put("acFeedQty", avgFeedQty);
								map.put("acFeedType", acFeedType);
								map.put("acTolFeedQty", _actotalFeedQty);
								objs.add(map);
							}
						}
						//记下喂料的日期,日龄，饲养天数/重置未喂料天数和最后一次喂料i
						lastHavDate=searchDate;
						lastHavDays=searchDays;
						lastHavFeedDays=feedDays;
						noFeedDays=0;
						totalNoFeedDays=0;
						lastI=i;
					}else{
						flag=false;
						noFeedDays++;
						totalNoFeedDays++;
					}
				}else{
					//i不在需要显示的范围
					if(noFeedDays>0){
						//查询页没有显示完,后面又有喂料记录
						if(acDayFeedQty!=null){
							//查询值为多天的喂料量，求平均值
							//给空缺的天数都附上值
							String avgFeedQty=ArithUtil.div(acDayFeedQty,(totalNoFeedDays+1)+"" , 2);
							String acFeedType=acDayFeedQty(conn, acFeedTypeSql, batch, searchDate);
							String actotalFeedQty=acDayFeedQty(conn, acTolFeedSql, batch, searchDate);
							//从上次喂料后一天开始循环
							for(int j=0;j<noFeedDays;j++){
								Map<String, Object> map = new HashMap<String, Object>();
								String[] arr=sdFeed(conn, sdFeedSql, company, ArithUtil.add((j+1)+"", lastHavDays, 0));
								String _actotalFeedQty=ArithUtil.sub(actotalFeedQty, ArithUtil.mul(avgFeedQty, (totalNoFeedDays-j)+""), 2);
								map.put("companyName", companyName);
								map.put("farmerName", farmerName);
								map.put("batchNumber", batchNumber);
								map.put("feedDate", PapUtil.getDayNoTime(j+1, lastHavDate));
								map.put("feedDays", lastHavFeedDays+j+1);
								map.put("dayAge", ArithUtil.add((j+1)+"", lastHavDays, 0));
								map.put("sdFeedType", arr[0]);
								map.put("sdFeedQty", arr[1]);
								map.put("acFeedQty", avgFeedQty);
								map.put("acFeedType", acFeedType);
								map.put("acTolFeedQty", _actotalFeedQty);
								objs.add(map);
							}
							noFeedDays=0;
							totalNoFeedDays=0;
							lastI=i;
						}else{
							totalNoFeedDays++;
						}
					}else{
						if(acDayFeedQty!=null){
							lastHavDate=searchDate;
							lastHavDays=searchDays;
							lastHavFeedDays=feedDays;
							lastI=i;
							totalNoFeedDays=0;
						}else
							totalNoFeedDays++;
					}
				}
				i++;
			}
			totalRows=lastI+1;
		}finally{
			if(rs!=null)
				rs.close();
			if(ps!=null)
				ps.close();
			jdbc.destroy(conn);
		}
		result.put("Rows", objs);
		result.put("Total", totalRows);
		return result;	
	}

	/**
	 * 
	 * 功能:每日喂料量/每日喂料类型
	 * @author:wxb
	 * @data:2017-9-1下午07:13:02
	 * @file:FeedBillServiceImpl.java
	 * @param conn
	 * @param sql
	 * @param ps
	 * @param parmeter
	 * @return
	 * @throws Exception
	 */
	private String acDayFeedQty(Connection conn,String sql,Integer batch,String searchDate)throws Exception{
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			ps=conn.prepareStatement(sql);
			ps.setInt(1, batch);
			ps.setString(2, searchDate);
			rs=ps.executeQuery();
		if(rs.next())
			return rs.getString(1);
		else
			return null;
		}finally{
			rs.close();
			ps.close();
		}
	}
	/**
	 * 
	 * 功能:查询标准喂料量
	 * @author:wxb
	 * @data:2017-9-1下午07:53:14
	 * @file:FeedBillServiceImpl.java
	 * @param conn
	 * @param sql
	 * @param company
	 * @param days
	 * @return
	 * @throws Exception
	 */
	private String[] sdFeed(Connection conn,String sql,String company,String days)throws Exception{
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			ps=conn.prepareStatement(sql);
			ps.setString(1, company);
			ps.setString(2, days);
			rs=ps.executeQuery();
		if(rs.next()){
			String[] arr=new String[2];
			arr[0]=rs.getString(1);
			arr[1]=rs.getString(2);
			return arr;
		}else
			return null;
		}finally{
			rs.close();
			ps.close();
		}
		
	}
	/**
	 * 
	 * 功能:该批次开始日期和开始日龄/查询批次的公司名称，代养户名称，批次号
	 * @author:wxb
	 * @data:2017-9-2上午09:34:53
	 * @file:FeedBillServiceImpl.java
	 * @param conn
	 * @param sql
	 * @param batch
	 * @return
	 * @throws Exception
	 */
	private String[] startFeed(Connection conn,String sql,Integer batch)throws Exception{
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			ps=conn.prepareStatement(sql);
			ps.setInt(1, batch);
			rs=ps.executeQuery();
		if(rs.next()){
			int num=rs.getMetaData().getColumnCount();
			String[] arr=new String[num];
			for(int i=0;i<num;i++)
				arr[i]=rs.getString(i+1);
			return arr;
		}else
			return null;
		}finally{
			rs.close();
			ps.close();
		}
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
			fileInput = new FileInputStream(realPath + "/WEB-INF/template/" + "feedbill.xlsx");
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new SystemException("系统找不到指定路径下的模板文件！");
		}
		return fileInput;
		
	}

	/**
	 * 导入耗料单
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-5 下午03:26:07
	 */
	public List<FeedBill> operateFile(File file, Company company, Object... objects)throws Exception{
		if(company==null || company.getId()==null || "".equals(company.getId()))
			throw new SystemException("请选择公司");
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		
		List<FeedBill> feedBills = new ArrayList<FeedBill>();
		Map<String,Object> map = new HashMap<String, Object>();
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
						String registDate = ExcelUtil.checkCellValue(row.getCell(2), i + 1, "耗料日期", true, sb);
						String feed = ExcelUtil.checkCellValue(row.getCell(3), i + 1, "饲料", true, sb);
						String quantity = ExcelUtil.checkCellValue(row.getCell(4), i + 1, "重量(KG)", true, sb);
						//验证
						//验证代养户
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("name", "=", farmer);
						List<Farmer> farmers = farmerService.selectHQL(sqlMap);
						sqlMap.clear();
						if(farmers==null || farmers.isEmpty())
							throw new SystemException("不存在代养户【"+farmer+"】");
						//验证批次
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("farmer.name", "=", farmer);
						sqlMap.put("batchNumber", "=", batch);
						List<Batch> batchs = batchService.selectHQL(sqlMap);
						sqlMap.clear();
						if(batchs==null || batchs.isEmpty())
							throw new SystemException("不存在代养户【"+farmer+"】批次【"+batch+"】");
						//验证饲料
						Feed fed = new Feed();
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("name", "=", feed);
						List<Feed> feeds = feedService.selectHQL(sqlMap);
						sqlMap.clear();
						if(feeds==null || feeds.isEmpty())
							throw new SystemException("不存在饲料【"+feed+"】");
						else{
							fed = feeds.get(0);
						}
						//封装明细
						FeedBillDtl fbd = new FeedBillDtl();
						fbd.setFeed(fed);
						fbd.setQuantity(quantity);
						String subquan = ArithUtil.div(quantity, fed.getRatio(), 2);
						fbd.setSubQuantity(subquan);
						List<FeedBillDtl> feedBillDtls = new ArrayList<FeedBillDtl>();
						feedBillDtls.add(fbd);
						//放入map
						String key = farmer+","+batch+","+registDate;
						if(map.containsKey(key)){
							List<FeedBillDtl> fbds = (List<FeedBillDtl>) map.get(key);
							fbds.add(fbd);
						}else{
							map.put(key, feedBillDtls);
						}
					}
				}
				if(sb.length() > 0)
					throw new SystemException(sb.toString());
				if(map.isEmpty())
					throw new SystemException("无可用数据导入");
				//封装耗料单
				for(Map.Entry<String, Object> entry : map.entrySet() ){
					String key = entry.getKey();
					List<FeedBillDtl> wareDtls = (List<FeedBillDtl>) entry.getValue();
					if(wareDtls != null && wareDtls.size()>0){
						String[] fiw = key.split(",");
						//农户
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("name", "=", fiw[0]);
						List<Farmer> farmers = farmerService.selectHQL(sqlMap);
						sqlMap.clear();
						Farmer fmr = farmers.get(0);
						//批次
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("farmer.name", "=", fiw[0]);
						sqlMap.put("batchNumber", "=", fiw[1]);
						List<Batch> batchs = batchService.selectHQL(sqlMap);
						sqlMap.clear();
						Batch bat = batchs.get(0);
						
						FeedBill feedBill = new FeedBill();
						feedBill.setRegistDate(fiw[2]);
						feedBill.setFarmer(fmr);
						feedBill.setBatch(bat);
						feedBill.setCompany(company);
						feedBill.setDetails(wareDtls);
						
						feedBills.add(feedBill);
					}
				}
				save(feedBills);
				//审核
				String ids = "";
				for(FeedBill c : feedBills){
					String id = c.getId();
					ids = ids+id+",";
				}
				String[] idArray = ids.split(",");
				check(idArray);
			}else
				throw new SystemException("无可用数据导入");
		}
		return feedBills;
	}
	
	
	/**
	 * 查找日报饲料耗用
	 * @Description:TODO
	 * @param bat
	 * @param date
	 * @param feedTypes
	 * @param map
	 * void
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-22 上午09:10:19
	 */
	public void selectFeedDay(Batch bat, String date,Map<String, Object> map)throws Exception{
		String total = "0";
		String countSql = "select ft.name,sum(t.quantity) from FS_FEEDBILLDTL t,fs_feedbill fb,fs_feed feed,fs_feedtype ft " +
				"where t.feedbill=fb.id and fb.batch='"+bat.getId()+"' and t.feed=feed.id and feed.feedtype=ft.id " +
						"and fb.checkstatus='1'and fb.registdate<='"+date+"' group by ft.name";
		
		Connection con = null;
		try {
			con = jdbc.getConnection();
			Object[][] rows = jdbc.doQuerry(countSql, con);
			if (null != rows && rows.length > 1){
				for(int i=1;i<rows.length;i++){
					map.put(rows[i][0].toString(), ArithUtil.scale(rows[i][1].toString(),2) );
					total = ArithUtil.add(total, rows[i][1].toString(),2);
				}
				map.put("all", total);
			}
		} finally {
			jdbc.destroy(con);
		}
		
	}
	
	/**
	 * 日报饲料总耗用
	 * @Description:TODO
	 * @param company
	 * @param date
	 * @param totalMap
	 * @throws Exception
	 * void
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-22 下午02:53:04
	 */
	public void selectAllFeedDay(Batch entity, String date,Map<String, Object> totalMap)throws Exception{
		String total = "0";
		String countSql = "select ft.name,sum(t.quantity) from FS_FEEDBILLDTL t,fs_feedbill fb,fs_feed feed,fs_feedtype ft ";
		String whereSql = "where t.feedbill=fb.id and fb.company='"+entity.getCompany().getId()+"' and t.feed=feed.id " +
				"and feed.feedtype=ft.id and fb.checkstatus='1'and fb.registdate<='"+date+"' ";
		String groupSql = " group by ft.name";
		
		if(entity.getFarmer()!=null && entity.getFarmer().getId()!=null && !"".equals(entity.getFarmer().getId()))
			whereSql += "and fb.farmer='"+entity.getFarmer().getId()+"' ";
		if( entity.getId()!=null && !"".equals(entity.getId()))
			whereSql += "and fb.batch='"+entity.getId()+"' ";
		
		countSql = countSql + whereSql + groupSql;
		
		Connection con = null;
		try {
			con = jdbc.getConnection();
			Object[][] rows = jdbc.doQuerry(countSql, con);
			if (null != rows && rows.length > 1){
				for(int i=1;i<rows.length;i++){
					totalMap.put(rows[i][0].toString(), ArithUtil.scale(rows[i][1].toString(),2));
					total = ArithUtil.add(total, rows[i][1].toString(),2);
				}
				totalMap.put("all", total);
			}
		} finally {
			jdbc.destroy(con);
		}
	}
	/**
	 * 功能:保存表头信息
	 * @author:DZL
	 * @data:2017-7-31下午04:49:24
	 * @file:IFeedBillService.java
	 * @param id
	 * @throws Exception
	 */
	public Object saveHead(FeedBill f)throws Exception{
		return super.save(f);
	}
}
