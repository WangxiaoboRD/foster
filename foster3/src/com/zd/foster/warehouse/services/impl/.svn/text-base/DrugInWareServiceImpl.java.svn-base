/**
 * 功能:
 * @author:wxb
 * @data:2017-7-26下午03:34:11
 * @file:DrugInWareServiceImpl.java
 */
package com.zd.foster.warehouse.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.permission.entity.Users;
import com.zd.epa.utils.ArithUtil;
import com.zd.epa.utils.ExcelUtil;
import com.zd.epa.utils.Pager;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.epa.utils.SysContainer;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.FeedFac;
import com.zd.foster.base.services.IFeedFacService;
import com.zd.foster.material.entity.Drug;
import com.zd.foster.material.services.IDrugService;
import com.zd.foster.warehouse.dao.IDrugInWareDao;
import com.zd.foster.warehouse.entity.DrugInWare;
import com.zd.foster.warehouse.entity.DrugInWareDtl;
import com.zd.foster.warehouse.entity.DrugWarehouse;
import com.zd.foster.warehouse.services.IDrugInWareDtlService;
import com.zd.foster.warehouse.services.IDrugInWareService;
import com.zd.foster.warehouse.services.IDrugWarehouseService;


/**
 * 类名：  DrugInWareServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-7-26下午03:34:11
 * @version 1.0
 * 
 */
public class DrugInWareServiceImpl extends BaseServiceImpl<DrugInWare, IDrugInWareDao> implements
		IDrugInWareService {
	@Resource
	private IDrugInWareDtlService drugInWareDtlService;
	@Resource
	private IDrugWarehouseService drugWarehouseService;
	@Resource
	private IDrugService drugService;
	@Resource
	private IFeedFacService feedFacService;
	/**
	 * 
	 * 功能:分页查询
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#selectAll(com.zd.epa.base.BaseEntity, com.zd.epa.utils.Pager)
	 */
	@Override
	public void selectAll(DrugInWare entity, Pager<DrugInWare> page) throws Exception {
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
	 * 功能:保存入库单
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#save(com.zd.epa.base.BaseEntity)
	 */
	public Object save(DrugInWare entity) throws Exception{
		//1验证表头
		//1.1表头是否为空
		if(entity == null)
			throw new SystemException("对象不能为空");
		//1.2养殖公司是否为空
		if(entity.getCompany()==null || entity.getCompany().getId()==null || "".equals(entity.getCompany().getId()))
			throw new SystemException("养殖公司不能为空！");
		//1.2饲料厂
		if(entity.getFeedFac()==null || entity.getFeedFac().getId()==null || "".equals(entity.getFeedFac().getId()))
			throw new SystemException("饲料厂不能为空！");
		//1.5入库时间是否为空
		if(entity.getRegistDate()==null || "".equals(entity.getRegistDate()))
			throw new SystemException("入库时间不能为空！");
		//2验证明细
		List<DrugInWareDtl> diwdList = entity.getDetails();
		//2.1明细是否为空
		if(diwdList == null || diwdList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		Iterator<DrugInWareDtl> it = diwdList.iterator();
		while(it.hasNext()){
			DrugInWareDtl diwd = it.next();
			//2.2.1验证数量是否为空，是否为0
			if(diwd.getQuantity()==null || !PapUtil.checkDouble(diwd.getQuantity()) || ArithUtil.comparison(diwd.getQuantity(), "0")==0)
				it.remove();
			//2.2.2检查副单位数量是否是"",如果是设为null
			if("".equals(diwd.getSubQuantity()))
				diwd.setSubQuantity(null);
			diwd.setDrugInWare(entity);
		}
		entity.setCheckStatus("0");
		Object obj = super.save(entity);
		drugInWareDtlService.save(diwdList);
		return obj;
	}
	
	/**
	 * 
	 * 功能:修改入库单
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#update(com.zd.epa.base.BaseEntity)
	 */
	public void update(DrugInWare entity)throws Exception{
		//1验证表头
		//1.1表头是否为空
		if(entity == null)
			throw new SystemException("对象不能为空");
		//1.5入库时间是否为空
		if(entity.getRegistDate()==null || "".equals(entity.getRegistDate()))
			throw new SystemException("入库时间不能为空！");
		//1.2饲料厂
		if(entity.getFeedFac()==null || entity.getFeedFac().getId()==null || "".equals(entity.getFeedFac().getId()))
			throw new SystemException("饲料厂不能为空！");
		//2验证明细
		List<DrugInWareDtl> diwdList = entity.getDetails();
		//2.1明细是否为空
		if(diwdList == null || diwdList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		Iterator<DrugInWareDtl> it = diwdList.iterator();
		while(it.hasNext()){
			DrugInWareDtl diwd = it.next();
			//2.2.1验证数量是否为空，是否为0
			if(diwd.getQuantity()==null || !PapUtil.checkDouble(diwd.getQuantity()) || ArithUtil.comparison(diwd.getQuantity(), "0")==0)
				it.remove();
			//2.2.2检查副单位数量是否是"",如果是设为null
			if("".equals(diwd.getSubQuantity()))
				diwd.setSubQuantity(null);
			diwd.setDrugInWare(entity);
		}
		// 保存表头
		// 1 、获取数据库里的对象
		DrugInWare e = super.selectById(entity.getId());
		// 2、将页面数据赋给数据库对象
		e.setRegistDate(entity.getRegistDate());
		e.setRemark(entity.getRemark());
		//保存明细
		//1、删除明细
		Map<String, String> _m = entity.getTempStack();
		if (null != _m && null != _m.get("deleteIds") && !"".equals(_m.get("deleteIds"))  ) {
			String[] str = _m.get("deleteIds").split(",");
			if(str != null){
				for(String id : str)
					drugInWareDtlService.deleteById(Integer.parseInt(id));
			}
		}
		//2. 新增/修改明细
		List<DrugInWareDtl> updateSwd = new ArrayList<DrugInWareDtl>(); 
		List<DrugInWareDtl> newSwd = new ArrayList<DrugInWareDtl>(); 
		for(DrugInWareDtl p : diwdList){
			if(p.getId()==null){
				p.setDrugInWare(e);
				newSwd.add(p);
			}
			if(p.getId()!=null){
				updateSwd.add(p);
			}
		}
		//修改	
		for(DrugInWareDtl p : updateSwd){
			DrugInWareDtl d = drugInWareDtlService.selectById(p.getId());
			d.setQuantity(p.getQuantity());
			d.setSubQuantity(p.getSubQuantity()==""?null:p.getSubQuantity());
		}
		updateSwd.clear();
		//添加
		drugInWareDtlService.save(newSwd);
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
		sqlMap.put("drugInWare.id", "in", PapUtil.arrayToSQLStr((String[])PK));
		drugInWareDtlService.delete(sqlMap);
		return dao.deleteByIds(PK);
	}
	
	/**
	 * 
	 * 功能:审核
	 * 重写:wxb
	 * 2017-7-28
	 * @see com.zd.foster.warehouse.services.IDrugInWareService#check(java.lang.String[])
	 */
	public void check(String[] idArr)throws Exception{
		if(idArr == null || idArr.length == 0)
			throw new SystemException("请选择单据");
		Users u = SysContainer.get();
		StringBuffer sb = new StringBuffer();
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		for(String s : idArr){
			//1.获取对象
			DrugInWare diw = super.selectById(s);
			//2.获取明细并遍历明细
			List<DrugInWareDtl> diwdList = drugInWareDtlService.selectBySingletAll("drugInWare.id", s);
			if(diw==null ||diwdList==null || diwdList.size()==0)
				sb.append("单据【"+s+"】对象为空或明细不存在<br>");
			List<DrugWarehouse> dwAllList = new ArrayList<DrugWarehouse>();
			//获取饲料厂
			FeedFac feedFac = diw.getFeedFac();
			
			for(DrugInWareDtl diwd : diwdList){
				//判断入库明细是正还是负
				boolean flag=false;
				if(ArithUtil.comparison(diwd.getQuantity(), "0")==1)
					flag=true;
				//2.1查找养殖公司是否有该药品库存
				sqlMap.put("company.id", "=", diw.getCompany().getId());
				sqlMap.put("feedFac.id", "=", feedFac.getId());
				sqlMap.put("drug.id", "=", diwd.getDrug().getId());
				List<DrugWarehouse> dwList = drugWarehouseService.selectHQL(sqlMap);
				sqlMap.clear();
				//2.2.1库存无该药品
				if(dwList==null || dwList.size()==0){
					if(!flag){
						sb.append("单据【"+s+"】明细【"+diwd.getDrug()+"】无库存<br>");
						continue ;
					}
					DrugWarehouse dw = new DrugWarehouse();
					dw.setIsDrug("0");
					dw.setCompany(diw.getCompany());
					dw.setDrug(diwd.getDrug());
					dw.setQuantity(diwd.getQuantity());
					dw.setSubQuantity(diwd.getSubQuantity());
					dw.setFeedFac(feedFac);
					dwAllList.add(dw);
				}else{
					//2.2.2库存有该药品库存
					DrugWarehouse dw = dwList.get(0);
					if(dw != null){
						//原始数据
						String q_old = dw.getQuantity();
						//入库单数据
						String q_new = diwd.getQuantity();
						if(q_old==null || "".equals(q_old))
							q_old = "0";
						//更改库存数量
						String num = ArithUtil.add(q_old, q_new);
						if(ArithUtil.comparison(num, "0")<0){
							sb.append("单据【"+s+"】明细【"+dw.getDrug().getName()+"】库存不够<br>");
							continue ;
						}
						dw.setQuantity(num);
						//更改副单位数量
						Drug drug=drugService.selectById(diwd.getDrug().getId());
						String ratio=drug.getRatio();
						if(ratio!=null && !"".equals(ratio))
							dw.setSubQuantity(ArithUtil.div(num, ratio, 2));
					}
				}
			}
			//审核单据 
			diw.setCheckDate(PapUtil.date(new Date()));
			diw.setCheckStatus("1");
			diw.setCheckUser(u.getUserRealName());
			//保存药品库存
			if(dwAllList !=null && dwAllList.size()>0)
				drugWarehouseService.save(dwAllList);
		}
		if(sb.length()>0)
			throw new SystemException(sb.toString());
		
	}

	/**
	 * 
	 * 功能:撤销
	 * 重写:wxb
	 * 2017-7-28
	 * @see com.zd.foster.warehouse.services.IDrugInWareService#cancelCheck(java.lang.String)
	 */
	@Override
	public void cancelCheck(String id) throws Exception {
		if(id == null || "".equals(id))
			throw new SystemException("请选择单据");
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		//1.获取对象
		DrugInWare diw = super.selectById(id);
		//2.获取明细并遍历
		List<DrugInWareDtl> diwdList = drugInWareDtlService.selectBySingletAll("drugInWare.id", id);
		if(diw==null ||diwdList==null || diwdList.size()==0)
			throw new SystemException("单据【"+id+"】对象为空或明细不存在");
		StringBuffer sb = new StringBuffer();
		List<DrugWarehouse> dwAllList = new ArrayList<DrugWarehouse>();
		for(DrugInWareDtl diwd : diwdList){
			//判断入库明细是正还是负
			boolean flag=false;
			if(ArithUtil.comparison(diwd.getQuantity(), "0")==1)
				flag=true;
			//2.1查询药品的库存
			sqlMap.put("company.id", "=", diw.getCompany().getId());
			sqlMap.put("drug.id", "=", diwd.getDrug().getId());
			List<DrugWarehouse> dwList = drugWarehouseService.selectHQL(sqlMap);
			sqlMap.clear();
			//2.2验证是否有该药品库存
			if(dwList==null || dwList.size()==0){
				if(flag){
					sb.append("单据【"+id+"】中药品【"+diwd.getDrug().getName()+"】已经不存在<br>");
					continue;
				}else{
					DrugWarehouse dw = new DrugWarehouse();
					dw.setIsDrug("0");
					dw.setCompany(diw.getCompany());
					dw.setDrug(diwd.getDrug());
					dw.setQuantity(ArithUtil.abs(diwd.getQuantity()));
					dw.setSubQuantity(ArithUtil.abs(diwd.getSubQuantity()));
					dwAllList.add(dw);
				}
			}else{
				//2.3验证该药品库存是否足够撤销
				DrugWarehouse dw = dwList.get(0);
				String dwNum= dw.getQuantity();
				if(ArithUtil.comparison(dwNum, diwd.getQuantity()) == -1){
					sb.append("单据【"+id+"】中药品【"+diwd.getDrug().getName()+"】已经使用<br>");
					continue;
				}
				//2.3对库存进行调整
				//2.3.1.调整数量
				String newNum = ArithUtil.sub(dwNum, diwd.getQuantity());
				dw.setQuantity(newNum);
				//2.3.2添加副单位数量
				Drug drug=drugService.selectById(diwd.getDrug().getId());
				String ratio=drug.getRatio();
				if(ratio!=null && !"".equals(ratio))
					dw.setSubQuantity(ArithUtil.div(newNum, ratio, 2));
			}
		}
		if(sb.length()>0)
			throw new SystemException(sb.toString());
		//保存药品库存
		if(dwAllList !=null && dwAllList.size()>0)
			drugWarehouseService.save(dwAllList);
		diw.setCheckDate(null);
		diw.setCheckStatus("0");
		diw.setCheckUser(null);
	}

	/**
	 * 
	 * 功能:下载模板
	 * 重写:wxb
	 * 2017-9-13
	 * @see com.zd.foster.warehouse.services.IDrugInWareService#downloadTemplate(java.lang.String)
	 */
	@Override
	public InputStream downloadTemplate(String realPath) throws Exception {
		InputStream fileInput = null;
		try {
			fileInput = new FileInputStream(realPath + "/WEB-INF/template/" + "drugInWare.xlsx");
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new SystemException("系统找不到指定路径下的模板文件！");
		}
		return fileInput;

	}

	/**
	 * 
	 * 功能:导入领药单
	 * 重写:wxb
	 * 2017-9-13
	 * @see com.zd.foster.warehouse.services.IDrugInWareService#operateFile(java.io.File, com.zd.foster.base.entity.Company, java.lang.Object[])
	 */
	@Override
	public List<DrugInWare> operateFile(File doc, Company company,
			Object... objects) throws Exception {
		if(company==null || company.getId()==null || "".equals(company.getId()))
			throw new SystemException("请选择公司");
		List<DrugInWare> details = new ArrayList<DrugInWare>();
		Workbook workbook = ExcelUtil.buildWorkbook(doc, (String)objects[0]);
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		Map<String,List<DrugInWareDtl>> dtlMap = new HashMap<String,List<DrugInWareDtl>>();
		Map<String,DrugInWare> map = new HashMap<String,DrugInWare>();
		if (null != workbook) {
			Sheet sheet = workbook.getSheetAt(0); // 获得第一个Excel Sheet
			int lastRowNum = sheet.getLastRowNum(); // 最后行号，默认为索引号，即从0开始到当前行号-1，如excel有10条数据，firstRowNum=0，而lastRowNum=9
			if (lastRowNum >0) {
				int firstRowNum = sheet.getFirstRowNum();
				StringBuffer sb = new StringBuffer(); // 存储校验非法描述
				for (int i = firstRowNum + 1; i <= lastRowNum; i++) {
					Row row = sheet.getRow(i);
					if (null != row) {
						String registDate = ExcelUtil.checkCellValue(row.getCell(0), i + 1, "入库日期", true, sb);
						String drug = ExcelUtil.checkCellValue(row.getCell(1), i + 1, "药品编码", true, sb);
						String quantity = ExcelUtil.checkCellValue(row.getCell(2), i + 1, "数量", true, sb);
						String remark = ExcelUtil.checkCellValue(row.getCell(3), i + 1, "备注", false, sb);
						//增加饲料厂
						String feedFac=ExcelUtil.checkCellValue(row.getCell(4), i + 1, "饲料厂", false, sb);
						//验证药品
						Drug dg = new Drug();
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("code", "=", drug);
						List<Drug> dList = drugService.selectHQL(sqlMap);
						sqlMap.clear();
						if(dList==null || dList.isEmpty()){
							sb.append(new SystemException("不存在药品【"+drug+"】"));
							continue;
						}else
							dg = dList.get(0);
						//验证饲料厂
						FeedFac ff=new FeedFac();
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("name", "=", feedFac);
						List<FeedFac> feedFacs=feedFacService.selectHQL(sqlMap);
						sqlMap.clear();
						if(feedFacs==null || feedFacs.isEmpty()){
							sb.append( new SystemException("不存在饲料厂【"+feedFac+"】"));
							continue;
						}else
							ff=feedFacs.get(0);
						
						//构造key
						String key=registDate+","+feedFac;
						if(remark!=null)
							key=key+","+remark;
						//验证key在map中是否已经存在
						Set<String> dSet=map.keySet();
						if(dSet==null || dSet.size()==0 || !dSet.contains(key)){
							//不存在：直接创建表头对象
							DrugInWare diw=new DrugInWare();
							diw.setRegistDate(registDate);
							diw.setCompany(company);
//							//饲料厂
							diw.setFeedFac(ff);
							if(remark!=null)
								diw.setRemark(remark);
							//明细集合
							List<DrugInWareDtl> diwdList = new ArrayList<DrugInWareDtl>();
							//封装明细对象
							DrugInWareDtl diwd = new DrugInWareDtl();
							diwd.setDrugInWare(diw);
							diwd.setDrug(dg);
							diwd.setQuantity(quantity);
							String subquan = ArithUtil.div(quantity, dg.getRatio(), 2);
							diwd.setSubQuantity(subquan);
							//明细添加到明细集合
							diwdList.add(diwd);
							dtlMap.put(key, diwdList);
							map.put(key, diw);
						}else{
							//取出表头和明细集合
							DrugInWare diw=map.get(key);
							List<DrugInWareDtl> diwdList=dtlMap.get(key);
							//封装明细对象
							DrugInWareDtl diwd = new DrugInWareDtl();
							diwd.setDrugInWare(diw);
							diwd.setDrug(dg);
							diwd.setQuantity(quantity);
							String subquan = ArithUtil.div(quantity, dg.getRatio(), 2);
							diwd.setSubQuantity(subquan);
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
					DrugInWare e=map.get(s);
					details.add(e);
					List<DrugInWareDtl> ed=dtlMap.get(s);
					e.setDetails(ed);
					Object obj=this.save(e);
					String[] idArray=new String[]{(String)obj};
					this.check(idArray);
				}
			}else
				throw new SystemException("无可用数据导入");
		}else
			throw new SystemException("无可用数据导入");
		return details;
	}
	
}
