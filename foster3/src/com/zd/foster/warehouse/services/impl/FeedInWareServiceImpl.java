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
import com.zd.foster.base.entity.Driver;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.entity.TransportCo;
import com.zd.foster.base.services.IDriverService;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.base.services.ITransportCoService;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.breed.services.IBatchService;
import com.zd.foster.contract.entity.Contract;
import com.zd.foster.contract.services.IContractService;
import com.zd.foster.dto.FeedAnalysis;
import com.zd.foster.material.entity.Feed;
import com.zd.foster.material.services.IFeedService;
import com.zd.foster.warehouse.dao.IFeedInWareDao;
import com.zd.foster.warehouse.entity.FeedInWare;
import com.zd.foster.warehouse.entity.FeedInWareDtl;
import com.zd.foster.warehouse.entity.FeedWarehouse;
import com.zd.foster.warehouse.services.IFeedInWareDtlService;
import com.zd.foster.warehouse.services.IFeedInWareService;
import com.zd.foster.warehouse.services.IFeedWarehouseService;
/**
 * 类名：  FeedInWareServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-7-26上午11:29:04
 * @version 1.0
 * 
 */
public class FeedInWareServiceImpl extends BaseServiceImpl<FeedInWare, IFeedInWareDao> implements
		IFeedInWareService {
	@Resource
	private IFeedInWareDtlService feedInWareDtlService;
	@Resource
	private IFeedWarehouseService feedWarehouseService;
	@Resource
	private IFeedService feedService;
	@Resource
	private ITransportCoService transportCoService;
	@Resource
	private IDriverService driverService;
	@Resource
	private IFarmerService farmerService;
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
	public void selectAll(FeedInWare entity, Pager<FeedInWare> page) throws Exception {
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
			Iterator<FeedInWare> it=page.getResult().iterator();
			while(it.hasNext()){
				FeedInWare e=it.next();
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
	 * 功能:保存入库单
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#save(com.zd.epa.base.BaseEntity)
	 */
	public Object save(FeedInWare entity) throws Exception{
		//1验证表头
		//1.1表头是否为空
		if(entity == null)
			throw new SystemException("对象不能为空");
		//1.2代养户是否为空
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
			throw new SystemException("入库时间不能为空！");
		//2验证明细
		List<FeedInWareDtl> fiwdList = entity.getDetails();
		//2.1明细是否为空
		if(fiwdList == null || fiwdList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<fiwdList.size();i++){
			FeedInWareDtl fiwd = fiwdList.get(i);
			//2.2.1验证数量是否为空，是否为0
			if(fiwd.getQuantity()==null || "".equals(fiwd.getQuantity()))
				buff.append("第"+(i+1)+"行数量不能为空<br>");
			else{
				//转化为数值
				double d = Double.parseDouble(fiwd.getQuantity());
				if(d==0){
					buff.append("第"+(i+1)+"行数值不允许为0<br>");
				}
			}
			//2.2.2检查副单位数量是否是"",如果是设为null
			if("".equals(fiwd.getSubQuantity()))
				fiwd.setSubQuantity(null);
			fiwd.setFeedInWare(entity);
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		entity.setCheckStatus("0");
		entity.setIsBalance("N");
		Object obj = super.save(entity);
		feedInWareDtlService.save(fiwdList);
		return obj;
	}
	
	/**
	 * 
	 * 功能:修改入库单
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#update(com.zd.epa.base.BaseEntity)
	 */
	public void update(FeedInWare entity)throws Exception{
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
		List<FeedInWareDtl> fiwdList = entity.getDetails();
		//2.1明细是否为空
		if(fiwdList == null || fiwdList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<fiwdList.size();i++){
			FeedInWareDtl fiwd = fiwdList.get(i);
			//2.2.1验证数量是否为空，是否为0
			if(fiwd.getQuantity()==null || "".equals(fiwd.getQuantity()))
				buff.append("第"+(i+1)+"行数量不能为空<br>");
			else{
				//转化为数值
				double d = Double.parseDouble(fiwd.getQuantity());
				if(d==0){
					buff.append("第"+(i+1)+"行数值不允许为0<br>");
				}
			}
			//2.2.2检查副单位数量是否是"",如果是设为null
			if("".equals(fiwd.getSubQuantity()))
				fiwd.setSubQuantity(null);
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		// 保存表头
		// 1 、获取数据库里的对象
		FeedInWare e = super.selectById(entity.getId());
		// 2、将页面数据赋给数据库对象
		e.setFarmer(entity.getFarmer());
		e.setBatch(entity.getBatch());
		e.setRegistDate(entity.getRegistDate());
		e.setTransportCo(entity.getTransportCo());
		e.setDriver(entity.getDriver());
		e.setInvoiceCode(entity.getInvoiceCode());
		e.setRemark(entity.getRemark());
		//保存明细
		//1、删除明细
		Map<String, String> _m = entity.getTempStack();
		if (null != _m && null != _m.get("deleteIds") && !"".equals(_m.get("deleteIds"))  ) {
			String[] str = _m.get("deleteIds").split(",");
			if(str != null){
				for(String id : str)
					feedInWareDtlService.deleteById(Integer.parseInt(id));
			}
		}
		//2. 新增/修改明细
		List<FeedInWareDtl> updateSwd = new ArrayList<FeedInWareDtl>(); 
		List<FeedInWareDtl> newSwd = new ArrayList<FeedInWareDtl>(); 
		for(FeedInWareDtl p : fiwdList){
			if(p.getId()==null){
				p.setFeedInWare(e);
				newSwd.add(p);
			}
			if(p.getId()!=null){
				updateSwd.add(p);
			}
		}
		//修改	
		for(FeedInWareDtl p : updateSwd){
			FeedInWareDtl d = feedInWareDtlService.selectById(p.getId());
			d.setQuantity(p.getQuantity());
			d.setSubQuantity(p.getSubQuantity()==""?null:p.getSubQuantity());
		}
		updateSwd.clear();
		//添加
		feedInWareDtlService.save(newSwd);
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
		sqlMap.put("feedInWare.id", "in", PapUtil.arrayToSQLStr((String[])PK));
		feedInWareDtlService.delete(sqlMap);
		return dao.deleteByIds(PK);
	}
	
	/**
	 * 
	 * 功能:审核
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
			FeedInWare fiw = super.selectById(s);
			//1.*添加验证：该批次的合同是否处于养殖状态？
			Contract ct=fiw.getBatch().getContract();
			if(ct!=null && ct.getStatus()!=null ){
				if("LOST".equals(ct.getStatus().getDcode())){
					sb.append("单据【"+s+"】【"+fiw.getFarmer().getName()+"】第【"+fiw.getBatch().getBatchNumber()+"】批对应的合同已经终止<br>");
					continue;
				}
			}
			
			//2.获取明细并遍历明细
			List<FeedInWareDtl> fiwdList = feedInWareDtlService.selectBySingletAll("feedInWare.id", s);
			if(fiw==null ||fiwdList==null || fiwdList.size()==0){
				sb.append("单据【"+s+"】对象为空或明细不存在<br>");
				continue;
			}
			List<FeedWarehouse> fwAllList = new ArrayList<FeedWarehouse>();
			for(FeedInWareDtl fiwd : fiwdList){
				//判断入库明细是正还是负
				boolean flag=false;
				if(ArithUtil.comparison(fiwd.getQuantity(), "0")==1)
					flag=true;
				//2.1查找代养户是否有该饲料库存/**修改：按批次添加库存*****/
				sqlMap.put("farmer.id", "=", fiw.getFarmer().getId());
				sqlMap.put("feed.id", "=", fiwd.getFeed().getId());
				sqlMap.put("batch.id", "=", String.valueOf(fiw.getBatch().getId()));
				List<FeedWarehouse> fwList = feedWarehouseService.selectHQL(sqlMap);
				sqlMap.clear();
				//2.2.1库存无该饲料
				if(fwList==null || fwList.size()==0){
					if(!flag){
						sb.append("单据【"+s+"】明细【"+fiw.getFarmer().getName()+"第"+fiw.getBatch().getBatchNumber()+"批"+fiwd.getFeed().getName()+"】无库存<br>");
						continue ;
					}
					FeedWarehouse fw = new FeedWarehouse();
					fw.setCompany(fiw.getCompany());
					fw.setFarmer(fiw.getFarmer());
					fw.setBatch(fiw.getBatch());
					fw.setFeed(fiwd.getFeed());
					fw.setQuantity(fiwd.getQuantity());
					fw.setSubQuantity(fiwd.getSubQuantity());
					fwAllList.add(fw);
				}else{
					//2.2.2库存有该饲料库存
					FeedWarehouse fw = fwList.get(0);
					if(fw != null){
						//原始数据
						String q_old = fw.getQuantity();
						//入库单数据
						String q_new = fiwd.getQuantity();
						if(q_old==null || "".equals(q_old))
							q_old = "0";
						if(q_new==null || "".equals(q_new))
							q_new = "0";
						
						String num = ArithUtil.add(q_old, q_new);
						if(ArithUtil.comparison(num, "0")<0){
							sb.append("单据【"+s+"】明细【"+fw.getFeed().getName()+"】库存不够<br>");
							continue ;
						}
						fw.setQuantity(num);
						//添加副单位数量
						Feed feed=feedService.selectById(fiwd.getFeed().getId());
						String ratio=feed.getRatio();
						if(ratio!=null && !"".equals(ratio))
							fw.setSubQuantity(ArithUtil.div(num, ratio, 2));
					}
				}
			}
			//审核单据 
			fiw.setCheckDate(PapUtil.date(new Date()));
			fiw.setCheckStatus("1");
			fiw.setCheckUser(u.getUserRealName());
			//保存新的饲料库存
			if(fwAllList !=null && fwAllList.size()>0)
				feedWarehouseService.save(fwAllList);
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
		FeedInWare fiw = super.selectById(id);
		if(fiw.getIsBalance().equals("Y"))
			throw new SystemException("单据【"+id+"】已经结算<br>");
		Contract ct=fiw.getBatch().getContract();
		if(ct!=null && ct.getStatus()!=null ){
			if("LOST".equals(ct.getStatus().getDcode()))
				throw new SystemException("单据【"+id+"】对应的合同已经终止<br>");
		}
		
		//2.获取明细并遍历
		List<FeedInWareDtl> fiwdList = feedInWareDtlService.selectBySingletAll("feedInWare.id", id);
		if(fiw==null ||fiwdList==null || fiwdList.size()==0)
			throw new SystemException("单据【"+id+"】对象为空或明细不存在");
		StringBuffer sb = new StringBuffer();
		List<FeedWarehouse> fwAllList = new ArrayList<FeedWarehouse>();
		for(FeedInWareDtl fiwd : fiwdList){
			//判断入库明细是正还是负
			boolean flag=false;
			if(ArithUtil.comparison(fiwd.getQuantity(), "0")==1)
				flag=true;
			//2.1查询饲料的库存/**修改：按批次查询库存*****/
			sqlMap.put("farmer.id", "=", fiw.getFarmer().getId());
			sqlMap.put("feed.id", "=", fiwd.getFeed().getId());
			sqlMap.put("batch.id", "=", String.valueOf(fiw.getBatch().getId()));
			List<FeedWarehouse> fwList = feedWarehouseService.selectHQL(sqlMap);
			sqlMap.clear();
			//2.2验证是否有该饲料库存（负数不需要验证,相当于入库，增加库存）
			if(fwList==null || fwList.size()==0){
				if(flag){
					sb.append("单据【"+id+"】中饲料【"+fiwd.getFeed().getName()+"】已经不存在<br>");
					continue;
				}else{
					FeedWarehouse fw = new FeedWarehouse();
					fw.setCompany(fiw.getCompany());
					fw.setFarmer(fiw.getFarmer());
					fw.setBatch(fiw.getBatch());
					fw.setFeed(fiwd.getFeed());
					fw.setQuantity(ArithUtil.sub("0", fiwd.getQuantity()));
					fw.setSubQuantity(ArithUtil.sub("0", fiwd.getSubQuantity()));
					fwAllList.add(fw);
				}
			}
			//2.3验证该饲料库存是否足够撤销
			FeedWarehouse fw = fwList.get(0);
			String fwNum= fw.getQuantity();
			if(ArithUtil.comparison(fwNum, fiwd.getQuantity()) == -1){
				sb.append("单据【"+id+"】中物料【"+fiwd.getFeed().getName()+"】已经使用<br>");
				continue;
			}
			//2.3对库存进行调整
			//2.3.1.调整数量
			String newNum = ArithUtil.sub(fwNum, fiwd.getQuantity());
			fw.setQuantity(newNum);
			//2.3.2添加副单位数量
			Feed feed=feedService.selectById(fiwd.getFeed().getId());
			String ratio=feed.getRatio();
			if(ratio!=null && !"".equals(ratio))
				fw.setSubQuantity(ArithUtil.div(newNum, ratio, 2));
		}
		if(sb.length()>0)
			throw new SystemException(sb.toString());
		//保存新的饲料库存
		if(fwAllList !=null && fwAllList.size()>0)
			feedWarehouseService.save(fwAllList);
		fiw.setCheckDate(null);
		fiw.setCheckStatus("0");
		fiw.setCheckUser(null);
	}

	/**
	 * 
	 * 功能:返回对象设置运输公司名称和司机名称
	 * 重写:wxb
	 * 2017-7-31
	 * @see com.zd.epa.base.BaseServiceImpl#selectById(java.io.Serializable)
	 */
	public <ID extends Serializable> FeedInWare selectById(ID PK)
			throws Exception {
		FeedInWare e=super.selectById(PK);
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
			fileInput = new FileInputStream(realPath + "/WEB-INF/template/" + "feedinware.xlsx");
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new SystemException("系统找不到指定路径下的模板文件！");
		}
		return fileInput;
		
	}
	/**
	 * 导入领料单
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-5 下午03:26:07
	 */
	public List<FeedInWare> operateFile(File file, Company company, Object... objects)throws Exception{
		if(company==null || company.getId()==null || "".equals(company.getId()))
			throw new SystemException("请选择公司");
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		
		List<FeedInWare> feedInWares = new ArrayList<FeedInWare>();
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
						String registDate = ExcelUtil.checkCellValue(row.getCell(2), i + 1, "领料日期", true, sb);
						String driver = ExcelUtil.checkCellValue(row.getCell(3), i + 1, "司机", true, sb);
						String feed = ExcelUtil.checkCellValue(row.getCell(4), i + 1, "饲料", true, sb);
						String quantity = ExcelUtil.checkCellValue(row.getCell(5), i + 1, "重量(KG)", true, sb);
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
						//验证司机
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("name", "=", driver);
						List<Driver> drivers = driverService.selectHQL(sqlMap);
						sqlMap.clear();
						if(drivers==null || drivers.isEmpty())
							throw new SystemException("不存在司机【"+driver+"】");
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
						FeedInWareDtl fiwd = new FeedInWareDtl();
						fiwd.setFeed(fed);
						fiwd.setQuantity(quantity);
						String subquan = ArithUtil.div(quantity, fed.getRatio(), 2);
						fiwd.setSubQuantity(subquan);
						List<FeedInWareDtl> feedInWareDtls = new ArrayList<FeedInWareDtl>();
						feedInWareDtls.add(fiwd);
						//放入map
						String key = farmer+","+batch+","+registDate+","+driver;
						if(map.containsKey(key)){
							List<FeedInWareDtl> fwds = (List<FeedInWareDtl>) map.get(key);
							fwds.add(fiwd);
						}else{
							map.put(key, feedInWareDtls);
						}
						
					}
				}
				if(sb.length() > 0)
					throw new SystemException(sb.toString());
				if(map.isEmpty())
					throw new SystemException("无可用数据导入");
				//封装领料单
				for(Map.Entry<String, Object> entry : map.entrySet() ){
					String key = entry.getKey();
					List<FeedInWareDtl> wareDtls = (List<FeedInWareDtl>) entry.getValue();
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
						//司机
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("name", "=", fiw[3]);
						List<Driver> drivers = driverService.selectHQL(sqlMap);
						sqlMap.clear();
						Driver driver = drivers.get(0);
						
						FeedInWare feedInWare = new FeedInWare();
						feedInWare.setRegistDate(fiw[2]);
						feedInWare.setFarmer(fmr);
						feedInWare.setBatch(bat);
						feedInWare.setDriver(driver.getId());
						feedInWare.setCompany(company);
						feedInWare.setDetails(wareDtls);
						
						feedInWares.add(feedInWare);
					}
				}
				save(feedInWares);
				//审核
				String ids = "";
				for(FeedInWare c : feedInWares){
					String id = c.getId();
					ids = ids+id+",";
				}
				String[] idArray = ids.split(",");
				check(idArray);
			}else
				throw new SystemException("无可用数据导入");
		}
		return feedInWares;
	}
	
	/**
	 * 饲料耗用报表
	 * @param feedInWare
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<FeedAnalysis> selectFeedInWareByPage(FeedInWare feedInWare,Pager<FeedInWare> page)throws Exception{
		
		//先生成查询语句
		String sqlin = "select farmer.name as farmerName,batch.batchnumber as batchnumber,t.feedinware,t.feed,feed.name as feedName,feed.packform,feed.spec,t.quantity,t.subquantity,f.registdate as registdate,f.batch,d.name as driverName,c.feedfac,cf.name as cfname,f.remark,farmer.id,1 from fs_feedinwaredtl t "+
					   "join fs_feedinware f on (f.id=t.feedinware) "+
					   "left join fs_feed feed on(feed.id=t.feed) "+
					   "left join fs_driver d on(d.id=f.driver) "+
					   "left join fs_farmer farmer on(farmer.id = f.farmer) "+
					   "left join fs_batch batch on(batch.id=f.batch) "+ 
					   "left join fs_contract c on (c.id=batch.contract) "+
					   "left join fs_feedfac cf on (c.feedfac = cf.id) "+
					   "where f.checkstatus='1'";
		
		//先生成查询语句
		String sqlout = "select farmer.name as farmerName,batch.batchnumber as batchnumber,t.feedtransfer,t.feed,feed.name as feedName,feed.packform,feed.spec,t.quantity,t.subquantity,f.registdate as registdate,f.outbatch,d.name as driverName,c.feedfac,cf.name as cfname,f.remark,farmer.id,2 from fs_feedtransferdtl t "+
						"join fs_feedtransfer f on (f.id= t.feedtransfer) "+
						"left join fs_feed feed on(feed.id=t.feed) "+
						"left join fs_driver d on(d.id=f.driver) "+
						"left join fs_farmer farmer on(farmer.id = f.outFarmer) "+
						"left join fs_batch batch on(batch.id=f.outBatch) "+
						"left join fs_contract c on (c.id=batch.contract) "+
						"left join fs_feedfac cf on (c.feedfac = cf.id) "+
						"where f.checkstatus='1'";
		
		//查找定价单
		String priceSql="select t.price "+
						  "from fs_feedpricedtl t "+
						  "join fs_feedprice f on (f.id = t.feedprice) "+
						 "where t.feed='###' "+
						   "and f.feedfac='###' "+
						   "and f.checkstatus = '1' "+
						   "and f.startdate <= '###' "+
						 "order by f.startdate desc";
		//查找运输定价单
		String friSql = "select t.packageprice,t.bulkprice "+
						   "from fs_freightdtl t "+
						   "join fs_freight f on (f.id = t.freight) "+
						   "where f.checkstatus = '1' "+
						   "and t.farmer = '###' "+
						   "and t.feedfac = '###' "+
						   "and f.registdate <='###' "+
						   "order by f.registdate desc";
		
		//获取最后一次饲料入库时间
		String maxDateSql= "select max(f.registdate) from fs_feedinwaredtl t left join fs_feedinware f on(f.id=t.feedinware) where f.batch='###' and f.checkstatus='1' and f.registdate<='###' and t.feed='###' and t.quantity>0";
								
		//计算合计
		String inallSql = "select farmer.id as farmerId,farmer.name as farmerName,f.batch,batch.batchnumber as batchnumber,t.feed,feed.name as feedName,feed.packform,feed.spec,fc.name as fcname,sum(t.quantity),sum(t.subquantity),1 from fs_feedinwaredtl t "+
	                      "join fs_feedinware f on (f.id=t.feedinware) "+
	                      "left join fs_feed feed on(feed.id=t.feed) "+
	                      "left join fs_farmer farmer on(farmer.id = f.farmer) "+
	                      "left join fs_batch batch on(batch.id=f.batch) "+
	                      "left join fs_contract c on (batch.contract = c.id) "+
	                      "left join fs_feedfac fc on (c.feedfac = fc.id) "+
	                      "where f.checkstatus='1'";
		
		String tallSql = "select farmer.id as farmerId,farmer.name as farmerName,f.outbatch,batch.batchnumber as batchnumber,t.feed,feed.name as feedName, feed.packform,feed.spec,fc.name as fcname,sum(t.quantity),sum(t.subquantity),2 from fs_feedtransferdtl t "+
						  "join fs_feedtransfer f on (f.id = t.feedtransfer) "+
						  "left join fs_feed feed on(feed.id=t.feed) "+
						  "left join fs_farmer farmer on(farmer.id = f.outfarmer) "+
						  "left join fs_batch batch on(batch.id=f.outbatch) "+
						  "left join fs_contract c on (batch.contract = c.id) "+
	                      "left join fs_feedfac fc on (c.feedfac = fc.id) "+
						  "where f.checkstatus='1'";
		//合计
		String allin = "select sum(t.quantity) from fs_feedinwaredtl t join fs_feedinware f on (f.id=t.feedinware) where f.checkstatus='1'";
		String allout = "select sum(t.quantity) from fs_feedtransferdtl t join fs_feedtransfer f on (f.id = t.feedtransfer) where f.checkstatus='1'";
		
		String type = "1";
		if(feedInWare != null){
			type = feedInWare.getCheckStatus();
			Company company = feedInWare.getCompany();
			if(company != null && company.getId() != null && !"".equals(company.getId())){
				sqlin +=" and f.company='"+company.getId()+"'";
				sqlout += "  and f.company='"+company.getId()+"'";
				
				inallSql += "  and f.company='"+company.getId()+"'";
				tallSql += "  and f.company='"+company.getId()+"'";
				
				allin += " and f.company='"+company.getId()+"'";
				allout += " and f.company='"+company.getId()+"'";
			}else
				throw new SystemException("请选择养殖公司");
			
			Map<String,String> map = feedInWare.getTempStack();
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
			Farmer f = feedInWare.getFarmer();
			if(f != null && f.getId() != null && !"".equals(f.getId())){
				sqlin +=" and f.farmer='"+f.getId()+"'";
				sqlout += " and f.outfarmer='"+f.getId()+"'";
				
				inallSql +=" and f.farmer='"+f.getId()+"'";
				tallSql += " and f.outfarmer='"+f.getId()+"'";
				
				allin += " and f.farmer='"+f.getId()+"'";
				allout += " and f.outfarmer='"+f.getId()+"'";
				
			}
			Batch b = feedInWare.getBatch();
			if(b != null && b.getId() != null && !"".equals(b.getId())){
				sqlin +=" and f.batch='"+b.getId()+"'";
				sqlout += " and f.outbatch='"+b.getId()+"'";
				
				inallSql += " and f.batch='"+b.getId()+"'";
				tallSql += " and f.outbatch='"+b.getId()+"'";
				
				allin += " and f.batch='"+b.getId()+"'";
				allout += " and f.outbatch='"+b.getId()+"'";
			}
		}
		
	    //执行查询
		Connection connection = null;
		List<FeedAnalysis> plist = new ArrayList<FeedAnalysis>();
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
				sb.append(") order by feedfac,farmerName,batchnumber,registdate desc");
				
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
						String billId = Objs[i][2]==null?"":Objs[i][2].toString();
						String feedId = Objs[i][3]==null?"":Objs[i][3].toString();
						String pack = Objs[i][5]==null?"":Objs[i][5].toString();
						String quantity = Objs[i][7]==null?"0":Objs[i][7].toString();
						String subQuantity = Objs[i][8]==null?"0":Objs[i][8].toString();
						String registdate = Objs[i][9]==null?"":Objs[i][9].toString();
						String batchId = Objs[i][10]==null?"":Objs[i][10].toString();
						String dirver = Objs[i][11]==null?"":Objs[i][11].toString();
						String feedFac = Objs[i][12]==null?"":Objs[i][12].toString();
						String feedFacName = Objs[i][13]==null?"":Objs[i][13].toString();
						String farmerId = Objs[i][15]==null?"":Objs[i][15].toString();
						String flag = Objs[i][16]==null?"":Objs[i][16].toString();
						
						FeedAnalysis fa = new FeedAnalysis();
						fa.setFarmer(farmerName);
						fa.setBatch(Objs[i][1]==null?"":Objs[i][1].toString());
						fa.setBill(billId);
						fa.setFeed(Objs[i][4]==null?"":Objs[i][4].toString());
						fa.setPack(pack);
						fa.setStandard(Objs[i][6]==null?"":Objs[i][6].toString());
						fa.setFeedFac(feedFacName);
						
						String _quantity = ArithUtil.div(quantity, "1000",2);//转化为吨
						fa.setWeight(_quantity);
						if("2".equals(pack)){
							fa.setQuantity(quantity);
							fa.setSubQuantity(subQuantity);
							if("2".equals(flag)){
								fa.setQuantity("-"+quantity);
								fa.setSubQuantity("-"+subQuantity);
								fa.setWeight("-"+_quantity);
							}
						}else{
							fa.setQuantity(quantity);
							fa.setSubQuantity(quantity);
							if("2".equals(flag)){
								fa.setQuantity("-"+quantity);
								fa.setSubQuantity("-"+quantity);
								fa.setWeight("-"+_quantity);
							}
						}
						
						fa.setFeedDate(registdate);
						fa.setDriver(dirver);
						fa.setMark(Objs[i][14]==null?"":Objs[i][14].toString());
						
						//计算单价以及费用
						if(PapUtil.checkDouble(quantity)){
							if(ArithUtil.comparison(quantity, "0")==1 && "1".equals(flag)){
								//查找该饲料的定价
								String _pSql= priceSql.replaceFirst("###", feedId).replaceFirst("###",feedFac).replaceFirst("###", registdate);
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
								if(price==null || "".equals(price)){
									price="0";
								}
								String money=ArithUtil.mul(price, quantity,2);
								
								if("2".equals(pack)){
									if(fa.getSubQuantity() != null && PapUtil.checkDouble(fa.getSubQuantity()));
										fa.setPrice(ArithUtil.div(money,fa.getSubQuantity(),2));
								}else
									fa.setPrice(price);
								fa.setAmount(money);
							}else if(ArithUtil.comparison(quantity, "0")==-1 || "2".equals(flag)){
								String _maxDateSql= maxDateSql.replaceFirst("###", batchId).replaceFirst("###", registdate).replaceFirst("###", feedId);
								Object[][] obj = jdbc.Querry(_maxDateSql, connection);
								String date = "";
								if(obj != null && obj.length>0)
									date = obj[0][0]==null?"":obj[0][0].toString();
								if("".equals(date))
									date = registdate;
								
								//查找该饲料的定价
								String _pSql= priceSql.replaceFirst("###", feedId).replaceFirst("###",feedFac).replaceFirst("###", date);
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
								if(price==null || "".equals(price)){
									price="0";
								}
								String money=ArithUtil.mul(price, ArithUtil.abs(quantity),2);
							
								if("2".equals(pack)){
									if(fa.getSubQuantity() != null && PapUtil.checkDouble(fa.getSubQuantity()))
										fa.setPrice(ArithUtil.div(money, fa.getSubQuantity(),2));
								}else
									fa.setPrice(price);
								fa.setAmount("-"+money);
							}
						}
						//计算运费
						if(ArithUtil.comparison(quantity, "0")>0 && "1".equals(flag) && dirver != null && !"".equals(dirver)){
							//求单价
							String _fSql = friSql.replaceFirst("###", farmerId).replaceFirst("###",feedFac).replaceFirst("###", registdate);
							//--1.1获取定价
							Object[][] _priceobj = jdbc.Querry(_fSql, connection);
							
							String packePrice = "";
							String freePrice ="";
							if(_priceobj != null && _priceobj.length>0){
								for(int t=0;t<_priceobj.length;t++){
									packePrice = _priceobj[t][0]==null?"":_priceobj[t][0].toString();
									freePrice = _priceobj[t][1]==null?"":_priceobj[t][1].toString();
									if(packePrice==null || "".equals(packePrice) || freePrice==null || "".equals(freePrice))
										continue;
									else
										break;
								}
							}
							if(packePrice==null || "".equals(packePrice) || freePrice==null || "".equals(freePrice)){
								packePrice="0";
								freePrice="0";
							}
							
							String money="0";
							String price="0";
							//计算费用
							if("1".equals(pack)){
								price = freePrice;
							}else if("2".equals(pack)){
								price = packePrice;
							}
							
							money = ArithUtil.mul(price, _quantity);
							
							
							fa.setFreight(price);
							fa.setAllFreight(money);
						}
						plist.add(fa);
					}
				}
			}
			if("2".equals(type)){
				
				inallSql += " group by farmer.id,farmer.name,f.batch,batch.batchnumber,t.feed,feed.name,feed.packform,feed.spec,fc.name";
				tallSql += " group by farmer.id,farmer.name,f.outbatch,batch.batchnumber,t.feed,feed.name,feed.packform,feed.spec,fc.name";
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
				sb2.append(") order by fcname,farmerId,batchnumber desc");
				
				String feedsql = "select * from (select p.*,rownum as rn from (" + sb2.toString() +") p where rownum<= "+page.getPageNo()*page.getPageSize()+") v where v.rn> "+(page.getPageNo()-1)*page.getPageSize()+"";
				
				Object[][] Objcount = jdbc.Querry(countSql, connection);
				Object[][] Objs = jdbc.Querry(feedsql, connection);
				//关闭连接
				page.setTotalCount(Objcount[0][0]==null?0:Integer.parseInt(Objcount[0][0].toString()));
				//分解并封装对象
				//封装对象
				if(Objs != null && Objs.length>0){
					for(int i=0;i<Objs.length;i++){
						
						String farmerName = Objs[i][1]==null?"":Objs[i][1].toString();
						String batchNumber = Objs[i][3]==null?"":Objs[i][3].toString();
						String feedName = Objs[i][5]==null?"":Objs[i][5].toString();
						String pack = Objs[i][6]==null?"":Objs[i][6].toString();
						String standard = Objs[i][7]==null?"":Objs[i][7].toString();
						String feedFacName = Objs[i][8]==null?"":Objs[i][8].toString();
						String quantity = Objs[i][9]==null?"0":Objs[i][9].toString();
						String subQuantity = Objs[i][10]==null?"0":Objs[i][10].toString();
						String flag = Objs[i][11]==null?"":Objs[i][11].toString();
						
						FeedAnalysis fa = new FeedAnalysis();
						fa.setFarmer(farmerName);
						fa.setBatch(batchNumber);
						fa.setFeed(feedName);
						fa.setPack(pack);
						fa.setStandard(standard);
						fa.setFeedFac(feedFacName);
						if("2".equals(pack)){
							fa.setQuantity(quantity);
							fa.setSubQuantity(subQuantity);
							if("2".equals(flag)){
								fa.setQuantity("-"+quantity);
								fa.setSubQuantity("-"+subQuantity);
							}
						}else{
							fa.setQuantity(quantity);
							fa.setSubQuantity(quantity);
							if("2".equals(flag)){
								fa.setQuantity("-"+quantity);
								fa.setSubQuantity("-"+quantity);
							}
						}
						plist.add(fa);
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
					FeedAnalysis fa = new FeedAnalysis();
					fa.setFeed("合计:");
					fa.setQuantity(allInNum);
					
					plist.add(fa);
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
	private List<FeedAnalysis> expFeedInWareByPage(FeedInWare feedInWare)throws Exception{
		
		//先生成查询语句
		String sqlin = "select farmer.name as farmerName,batch.batchnumber as batchnumber,t.feedinware,t.feed,feed.name as feedName,feed.packform,feed.spec,t.quantity,t.subquantity,f.registdate as registdate,f.batch,d.name as driverName,c.feedfac,cf.name as cfname,f.remark,farmer.id,1 from fs_feedinwaredtl t "+
					   "join fs_feedinware f on (f.id=t.feedinware) "+
					   "left join fs_feed feed on(feed.id=t.feed) "+
					   "left join fs_driver d on(d.id=f.driver) "+
					   "left join fs_farmer farmer on(farmer.id = f.farmer) "+
					   "left join fs_batch batch on(batch.id=f.batch) "+ 
					   "left join fs_contract c on (c.id=batch.contract) "+
					   "left join fs_feedfac cf on (c.feedfac = cf.id) "+
					   "where f.checkstatus='1'";
		
		//先生成查询语句
		String sqlout = "select farmer.name as farmerName,batch.batchnumber as batchnumber,t.feedtransfer,t.feed,feed.name as feedName,feed.packform,feed.spec,t.quantity,t.subquantity,f.registdate as registdate,f.outbatch,d.name as driverName,c.feedfac,cf.name as cfname,f.remark,farmer.id,2 from fs_feedtransferdtl t "+
						"join fs_feedtransfer f on (f.id= t.feedtransfer) "+
						"left join fs_feed feed on(feed.id=t.feed) "+
						"left join fs_driver d on(d.id=f.driver) "+
						"left join fs_farmer farmer on(farmer.id = f.outFarmer) "+
						"left join fs_batch batch on(batch.id=f.outBatch) "+
						"left join fs_contract c on (c.id=batch.contract) "+
						"left join fs_feedfac cf on (c.feedfac = cf.id) "+
						"where f.checkstatus='1'";
		
		//查找定价单
		String priceSql="select t.price "+
						  "from fs_feedpricedtl t "+
						  "join fs_feedprice f on (f.id = t.feedprice) "+
						 "where t.feed='###' "+
						   "and f.feedfac='###' "+
						   "and f.checkstatus = '1' "+
						   "and f.startdate <= '###' "+
						 "order by f.startdate desc";
		//查找运输定价单
		String friSql = "select t.packageprice,t.bulkprice "+
						   "from fs_freightdtl t "+
						   "join fs_freight f on (f.id = t.freight) "+
						   "where f.checkstatus = '1' "+
						   "and t.farmer = '###' "+
						   "and t.feedfac = '###' "+
						   "and f.registdate <='###' "+
						   "order by f.registdate desc";
		
		//获取最后一次饲料入库时间
		String maxDateSql= "select max(f.registdate) from fs_feedinwaredtl t left join fs_feedinware f on(f.id=t.feedinware) where f.batch='###' and f.checkstatus='1' and f.registdate<='###' and t.feed='###' and t.quantity>0";
								
		//计算合计
		String inallSql = "select farmer.id as farmerId,farmer.name as farmerName,f.batch,batch.batchnumber as batchnumber,t.feed,feed.name as feedName,feed.packform,feed.spec,fc.name as fcname,sum(t.quantity),sum(t.subquantity),1 from fs_feedinwaredtl t "+
	                      "join fs_feedinware f on (f.id=t.feedinware) "+
	                      "left join fs_feed feed on(feed.id=t.feed) "+
	                      "left join fs_farmer farmer on(farmer.id = f.farmer) "+
	                      "left join fs_batch batch on(batch.id=f.batch) "+
	                      "left join fs_contract c on (batch.contract = c.id) "+
	                      "left join fs_feedfac fc on (c.feedfac = fc.id) "+
	                      "where f.checkstatus='1'";
		
		String tallSql = "select farmer.id as farmerId,farmer.name as farmerName,f.outbatch,batch.batchnumber as batchnumber,t.feed,feed.name as feedName, feed.packform,feed.spec,fc.name as fcname,sum(t.quantity),sum(t.subquantity),2 from fs_feedtransferdtl t "+
						  "join fs_feedtransfer f on (f.id = t.feedtransfer) "+
						  "left join fs_feed feed on(feed.id=t.feed) "+
						  "left join fs_farmer farmer on(farmer.id = f.outfarmer) "+
						  "left join fs_batch batch on(batch.id=f.outbatch) "+
						  "left join fs_contract c on (batch.contract = c.id) "+
	                      "left join fs_feedfac fc on (c.feedfac = fc.id) "+
						  "where f.checkstatus='1'";
		//合计
		String allin = "select sum(t.quantity) from fs_feedinwaredtl t join fs_feedinware f on (f.id=t.feedinware) where f.checkstatus='1'";
		String allout = "select sum(t.quantity) from fs_feedtransferdtl t join fs_feedtransfer f on (f.id = t.feedtransfer) where f.checkstatus='1'";
		
		String type = "1";
		if(feedInWare != null){
			type = feedInWare.getCheckStatus();
			Company company = feedInWare.getCompany();
			if(company != null && company.getId() != null && !"".equals(company.getId())){
				sqlin +=" and f.company='"+company.getId()+"'";
				sqlout += "  and f.company='"+company.getId()+"'";
				
				inallSql += "  and f.company='"+company.getId()+"'";
				tallSql += "  and f.company='"+company.getId()+"'";
				
				allin += " and f.company='"+company.getId()+"'";
				allout += " and f.company='"+company.getId()+"'";
			}
			Map<String,String> map = feedInWare.getTempStack();
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
			Farmer f = feedInWare.getFarmer();
			if(f != null && f.getId() != null && !"".equals(f.getId())){
				sqlin +=" and f.farmer='"+f.getId()+"'";
				sqlout += " and f.outfarmer='"+f.getId()+"'";
				
				inallSql +=" and f.farmer='"+f.getId()+"'";
				tallSql += " and f.outfarmer='"+f.getId()+"'";
				
				allin += " and f.farmer='"+f.getId()+"'";
				allout += " and f.outfarmer='"+f.getId()+"'";
				
			}
			Batch b = feedInWare.getBatch();
			if(b != null && b.getId() != null && !"".equals(b.getId())){
				sqlin +=" and f.batch='"+b.getId()+"'";
				sqlout += " and f.outbatch='"+b.getId()+"'";
				
				inallSql += " and f.batch='"+b.getId()+"'";
				tallSql += " and f.outbatch='"+b.getId()+"'";
				
				allin += " and f.batch='"+b.getId()+"'";
				allout += " and f.outbatch='"+b.getId()+"'";
			}
		}
		
	    //执行查询
		Connection connection = null;
		List<FeedAnalysis> plist = new ArrayList<FeedAnalysis>();
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
				sb.append(") order by feedfac,farmerName,batchnumber,registdate desc");
				
				String feedsql = sb.toString();
				Object[][] Objs = jdbc.Querry(feedsql, connection);
				//分解并封装对象
				if(Objs != null && Objs.length>0){
					for(int i=0;i<Objs.length;i++){
						String farmerName = Objs[i][0]==null?"":Objs[i][0].toString();
						String billId = Objs[i][2]==null?"":Objs[i][2].toString();
						String feedId = Objs[i][3]==null?"":Objs[i][3].toString();
						String pack = Objs[i][5]==null?"":Objs[i][5].toString();
						String quantity = Objs[i][7]==null?"0":Objs[i][7].toString();
						String subQuantity = Objs[i][8]==null?"0":Objs[i][8].toString();
						String registdate = Objs[i][9]==null?"":Objs[i][9].toString();
						String batchId = Objs[i][10]==null?"":Objs[i][10].toString();
						String dirver = Objs[i][11]==null?"":Objs[i][11].toString();
						String feedFac = Objs[i][12]==null?"":Objs[i][12].toString();
						String feedFacName = Objs[i][13]==null?"":Objs[i][13].toString();
						String farmerId = Objs[i][15]==null?"":Objs[i][15].toString();
						String flag = Objs[i][16]==null?"":Objs[i][16].toString();
						
						FeedAnalysis fa = new FeedAnalysis();
						fa.setFarmer(farmerName);
						fa.setBatch(Objs[i][1]==null?"":Objs[i][1].toString());
						fa.setBill(billId);
						fa.setFeed(Objs[i][4]==null?"":Objs[i][4].toString());
						fa.setPack(pack);
						fa.setStandard(Objs[i][6]==null?"":Objs[i][6].toString());
						fa.setFeedFac(feedFacName);
						
						String _quantity = ArithUtil.div(quantity, "1000",2);//转化为吨
						fa.setWeight(_quantity);
						if("2".equals(pack)){
							fa.setQuantity(quantity);
							fa.setSubQuantity(subQuantity);
							if("2".equals(flag)){
								fa.setQuantity("-"+quantity);
								fa.setSubQuantity("-"+subQuantity);
								fa.setWeight("-"+_quantity);
							}
						}else{
							fa.setQuantity(quantity);
							fa.setSubQuantity(quantity);
							if("2".equals(flag)){
								fa.setQuantity("-"+quantity);
								fa.setSubQuantity("-"+quantity);
								fa.setWeight("-"+_quantity);
							}
						}
						
						fa.setFeedDate(registdate);
						fa.setDriver(dirver);
						fa.setMark(Objs[i][14]==null?"":Objs[i][14].toString());
						
						//计算单价以及费用
						if(PapUtil.checkDouble(quantity)){
							if(ArithUtil.comparison(quantity, "0")==1 && "1".equals(flag)){
								//查找该饲料的定价
								String _pSql= priceSql.replaceFirst("###", feedId).replaceFirst("###",feedFac).replaceFirst("###", registdate);
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
								if(price==null || "".equals(price)){
									price="0";
								}
								String money=ArithUtil.mul(price, quantity,2);
								
								if("2".equals(pack)){
									if(fa.getSubQuantity() != null && PapUtil.checkDouble(fa.getSubQuantity()));
										fa.setPrice(ArithUtil.div(money,fa.getSubQuantity(),2));
								}else
									fa.setPrice(price);
								fa.setAmount(money);
							}else if(ArithUtil.comparison(quantity, "0")==-1 || "2".equals(flag)){
								String _maxDateSql= maxDateSql.replaceFirst("###", batchId).replaceFirst("###", registdate).replaceFirst("###", feedId);
								Object[][] obj = jdbc.Querry(_maxDateSql, connection);
								String date = "";
								if(obj != null && obj.length>0)
									date = obj[0][0]==null?"":obj[0][0].toString();
								if("".equals(date))
									date = registdate;
								
								//查找该饲料的定价
								String _pSql= priceSql.replaceFirst("###", feedId).replaceFirst("###",feedFac).replaceFirst("###", date);
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
								if(price==null || "".equals(price)){
									price="0";
								}
								String money=ArithUtil.mul(price, ArithUtil.abs(quantity),2);
							
								if("2".equals(pack)){
									if(fa.getSubQuantity() != null && PapUtil.checkDouble(fa.getSubQuantity()))
										fa.setPrice(ArithUtil.div(money, fa.getSubQuantity(),2));
								}else
									fa.setPrice(price);
								fa.setAmount("-"+money);
							}
						}
						//计算运费
						if(ArithUtil.comparison(quantity, "0")>0  && "1".equals(flag) && dirver != null && !"".equals(dirver)){
							//求单价
							String _fSql = friSql.replaceFirst("###", farmerId).replaceFirst("###",feedFac).replaceFirst("###", registdate);
							//--1.1获取定价
							Object[][] _priceobj = jdbc.Querry(_fSql, connection);
							
							String packePrice = "";
							String freePrice ="";
							if(_priceobj != null && _priceobj.length>0){
								for(int t=0;t<_priceobj.length;t++){
									packePrice = _priceobj[t][0]==null?"":_priceobj[t][0].toString();
									freePrice = _priceobj[t][1]==null?"":_priceobj[t][1].toString();
									if(packePrice==null || "".equals(packePrice) || freePrice==null || "".equals(freePrice))
										continue;
									else
										break;
								}
							}
							if(packePrice==null || "".equals(packePrice) || freePrice==null || "".equals(freePrice)){
								packePrice="0";
								freePrice="0";
							}
							
							String money="0";
							String price="0";
							//计算费用
							if("1".equals(pack)){
								price = freePrice;
							}else if("2".equals(pack)){
								price = packePrice;
							}
							money = ArithUtil.mul(price, _quantity);
							fa.setWeight(_quantity);
							fa.setFreight(price);
							fa.setAllFreight(money);
						}
						plist.add(fa);
					}
				}
			}
			if("2".equals(type)){
				
				inallSql += " group by farmer.id,farmer.name,f.batch,batch.batchnumber,t.feed,feed.name,feed.packform,feed.spec,fc.name";
				tallSql += " group by farmer.id,farmer.name,f.outbatch,batch.batchnumber,t.feed,feed.name,feed.packform,feed.spec,fc.name";
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
				sb2.append(") order by fcname,farmerId,batchnumber desc");
				
				String feedsql = sb2.toString();
				
				Object[][] Objs = jdbc.Querry(feedsql, connection);
				//分解并封装对象
				if(Objs != null && Objs.length>0){
					for(int i=0;i<Objs.length;i++){
						
						String farmerName = Objs[i][1]==null?"":Objs[i][1].toString();
						String batchNumber = Objs[i][3]==null?"":Objs[i][3].toString();
						String feedName = Objs[i][5]==null?"":Objs[i][5].toString();
						String pack = Objs[i][6]==null?"":Objs[i][6].toString();
						String standard = Objs[i][7]==null?"":Objs[i][7].toString();
						String feedFacName = Objs[i][8]==null?"":Objs[i][8].toString();
						String quantity = Objs[i][9]==null?"0":Objs[i][9].toString();
						String subQuantity = Objs[i][10]==null?"0":Objs[i][10].toString();
						String flag = Objs[i][11]==null?"":Objs[i][11].toString();
						
						FeedAnalysis fa = new FeedAnalysis();
						fa.setFarmer(farmerName);
						fa.setBatch(batchNumber);
						fa.setFeed(feedName);
						fa.setPack(pack);
						fa.setStandard(standard);
						fa.setFeedFac(feedFacName);
						if("2".equals(pack)){
							fa.setQuantity(quantity);
							fa.setSubQuantity(subQuantity);
							if("2".equals(flag)){
								fa.setQuantity("-"+quantity);
								fa.setSubQuantity("-"+subQuantity);
							}
						}else{
							fa.setQuantity(quantity);
							fa.setSubQuantity(quantity);
							if("2".equals(flag)){
								fa.setQuantity("-"+quantity);
								fa.setSubQuantity("-"+quantity);
							}
						}
						plist.add(fa);
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
					FeedAnalysis fa = new FeedAnalysis();
					fa.setFeed("合计:");
					fa.setQuantity(allInNum);
					
					plist.add(fa);
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
	public InputStream exportFeedInWareDetail(FeedInWare feedInWare)throws Exception{
		
		if(feedInWare == null)
			return null;
		String isall = feedInWare.getCheckStatus();
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		String sheetName= "饲料耗用报表";
		if("2".equals(isall))
			sheetName= "饲料耗用合计报表";
		HSSFSheet sheet1 = workbook.createSheet(sheetName);
		// 设置表格默认列宽度为15个字节
		sheet1.setDefaultColumnWidth(20);
		//设置表头样式
		HSSFCellStyle headerStyle = ExcelUtil.getHeaderStyle(workbook);
		//设置表格样式
		HSSFCellStyle cellStyle = ExcelUtil.getCellStyle(workbook);
		String expField = feedInWare.getExportFields();
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
		List<FeedAnalysis> datas = expFeedInWareByPage(feedInWare);
		if (null != datas && datas.size() > 0) {
			for (FeedAnalysis b: datas) {
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

	/**
	 * 日报饲料入库
	 * @Description:TODO
	 * @param bat
	 * @param date
	 * @param map
	 * @throws Exception
	 * void
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-22 上午10:04:57
	 */
	public void selectFeedInDay(Batch bat, String date, Map<String, Object> map)throws Exception{
		String total = "0";
		String countSql = "select ft.name,sum(t.quantity) from FS_FEEDINWAREDTL t,fs_feedinware fw,fs_feed f,fs_feedtype ft " +
				"where t.feedinware=fw.id and t.feed=f.id and f.feedtype=ft.id " +
				"and fw.batch='"+bat.getId()+"' and fw.registdate<='"+date+"' and fw.checkstatus='1' " +
				"group by ft.name";

		Connection con = null;
		try {
			con = jdbc.getConnection();
			Object[][] rows = jdbc.doQuerry(countSql, con);
			if (null != rows && rows.length > 1){
				for(int i=1;i<rows.length;i++){
					map.put(rows[i][0]+"_in", ArithUtil.scale(rows[i][1].toString(),2));
					total = ArithUtil.add(total, rows[i][1].toString(),2);
				}
				map.put("all_in", total);
			}
		} finally {
			jdbc.destroy(con);
		}
		
	}
	
	/**
	 * 日报饲料入库总计
	 * @Description:TODO
	 * @param company
	 * @param date
	 * @param totalMap
	 * @throws Exception
	 * void
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-22 下午03:07:30
	 */
	public void selectAllFeedInDay(Batch entity, String date,Map<String, Object> totalMap)throws Exception{
		String total = "0";
		String countSql = "select ft.name,sum(t.quantity) from FS_FEEDINWAREDTL t,fs_feedinware fw,fs_feed f,fs_feedtype ft ";
		String whereSql = "where t.feedinware=fw.id and t.feed=f.id and f.feedtype=ft.id " +
				"and fw.company='"+entity.getCompany().getId()+"' and fw.registdate<='"+date+"' and fw.checkstatus='1' " ;
		String groupSql = "group by ft.name";
		
		if(entity.getFarmer()!=null && entity.getFarmer().getId()!=null && !"".equals(entity.getFarmer().getId()))
			whereSql += "and fw.farmer='"+entity.getFarmer().getId()+"' ";
		if( entity.getId()!=null && !"".equals(entity.getId()))
			whereSql += "and fw.batch='"+entity.getId()+"' ";
		
		countSql = countSql + whereSql + groupSql;
		
		Connection con = null;
		try {
			con = jdbc.getConnection();
			Object[][] rows = jdbc.doQuerry(countSql, con);
			if (null != rows && rows.length > 1){
				for(int i=1;i<rows.length;i++){
					totalMap.put(rows[i][0]+"_in", ArithUtil.scale(rows[i][1].toString(),2));
					total = ArithUtil.add(total, rows[i][1].toString(),2);
				}
				totalMap.put("all_in", total);
			}
		} finally {
			jdbc.destroy(con);
		}
	}

	
}
