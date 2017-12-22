
package com.zd.foster.warehouse.services.impl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
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
import com.zd.epa.utils.Pager;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.epa.utils.SysContainer;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.CustVender;
import com.zd.foster.base.entity.FeedFac;
import com.zd.foster.base.services.ICustVenderService;
import com.zd.foster.base.services.IFeedFacService;
import com.zd.foster.material.entity.Drug;
import com.zd.foster.material.services.IDrugService;
import com.zd.foster.warehouse.dao.IDrugAllocationDao;
import com.zd.foster.warehouse.entity.DrugAllocation;
import com.zd.foster.warehouse.entity.DrugAllocationDtl;
import com.zd.foster.warehouse.entity.DrugWarehouse;
import com.zd.foster.warehouse.services.IDrugAllocationDtlService;
import com.zd.foster.warehouse.services.IDrugAllocationService;
import com.zd.foster.warehouse.services.IDrugWarehouseService;

/**
 * 药品调拨单业务层
 * @Description:TODO
 * @author:小丁
 * @time:2017-9-14 下午05:20:48
 */
public class DrugAllocationServiceImpl extends BaseServiceImpl<DrugAllocation, IDrugAllocationDao> implements
		IDrugAllocationService {
	@Resource
	private IDrugAllocationDtlService drugAllocationDtlService;
	@Resource
	private IDrugWarehouseService drugWarehouseService;
	@Resource
	private IDrugService drugService;
	@Resource
	private ICustVenderService custVenderService;
	@Resource
	private IFeedFacService feedFacService;
	
	/**
	 * 保存
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-15 上午10:04:57
	 */
	public Object save(DrugAllocation entity) throws Exception{
		//1验证表头
		//1.1表头是否为空
		if(entity == null)
			throw new SystemException("对象不能为空");
		//1.2养殖公司是否为空
		if(entity.getCompany()==null || entity.getCompany().getId()==null || "".equals(entity.getCompany().getId()))
			throw new SystemException("养殖公司不能为空！");
		//1.3调拨类型是否为空
		if(entity.getAllotType()==null || "".equals(entity.getAllotType()))
			throw new SystemException("调拨类型不能为空！");
		//1.4客户是否为空
		if(entity.getCustv()==null || entity.getCustv().getId()==null || "".equals(entity.getCustv().getId()))
			throw new SystemException("客户不能为空！");
		//1.5时间是否为空
		if(entity.getRegistDate()==null || "".equals(entity.getRegistDate()))
			throw new SystemException("领药时间不能为空！");
		//2验证明细
		List<DrugAllocationDtl> fiwdList = entity.getDetails();
		//2.1明细是否为空
		if(fiwdList == null || fiwdList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<fiwdList.size();i++){
			DrugAllocationDtl fiwd = fiwdList.get(i);
			//2.2.1验证数量是否为空，是否为0
			if(fiwd.getQuantity()==null || "".equals(fiwd.getQuantity()))
				buff.append("第"+(i+1)+"行数量不能为空<br>");
			else if(ArithUtil.comparison(fiwd.getQuantity(), "0")<=0)
				buff.append("第"+(i+1)+"行数值必须为正数！<br>");
			
			fiwd.setDrugAllocation(entity);
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		entity.setCheckStatus("0");
		
		Object obj = super.save(entity);
		drugAllocationDtlService.save(fiwdList);
		return obj;
	}

	/**
	 * 修改
	 * @Description:TODO
	 * @param entity
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-15 上午10:49:59
	 */
	public void update(DrugAllocation entity)throws Exception{
		//1验证表头
		//1.1表头是否为空
		if(entity == null)
			throw new SystemException("对象不能为空");
		//1.5时间是否为空
		if(entity.getRegistDate()==null || "".equals(entity.getRegistDate()))
			throw new SystemException("时间不能为空！");
		//2验证明细
		List<DrugAllocationDtl> fiwdList = entity.getDetails();
		//2.1明细是否为空
		if(fiwdList == null || fiwdList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<fiwdList.size();i++){
			DrugAllocationDtl fiwd = fiwdList.get(i);
			//2.2.1验证数量是否为空，是否为0
			if(fiwd.getQuantity()==null || "".equals(fiwd.getQuantity()))
				buff.append("第"+(i+1)+"行数量不能为空<br>");
			else if(ArithUtil.comparison(fiwd.getQuantity(), "0")<=0)
				buff.append("第"+(i+1)+"行数值必须为正数！<br>");
			
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		// 保存表头
		// 1 、获取数据库里的对象
		DrugAllocation e = super.selectById(entity.getId());
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
					drugAllocationDtlService.deleteById(Integer.parseInt(id));
			}
		}
		//2. 新增/修改明细
		List<DrugAllocationDtl> updateSwd = new ArrayList<DrugAllocationDtl>(); 
		List<DrugAllocationDtl> newSwd = new ArrayList<DrugAllocationDtl>(); 
		for(DrugAllocationDtl p : fiwdList){
			if(p.getId()==null){
				p.setDrugAllocation(e);
				newSwd.add(p);
			}
			if(p.getId()!=null){
				updateSwd.add(p);
			}
		}
		//修改	
		for(DrugAllocationDtl p : updateSwd){
			DrugAllocationDtl d = drugAllocationDtlService.selectById(p.getId());
			d.setQuantity(p.getQuantity());
		}
		updateSwd.clear();
		//添加
		drugAllocationDtlService.save(newSwd);
	}
	
	/**
	 * 删除
	 * @Description:TODO
	 * @param <ID>
	 * @param PK
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-15 上午10:58:33
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		if(PK==null || PK.length==0)
			throw new SystemException("请选择删除对象");
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("drugAllocation.id", "in", PapUtil.arrayToSQLStr((String[])PK));
		drugAllocationDtlService.delete(sqlMap);
		return dao.deleteByIds(PK);
	}
	
	/**
	 * 
	 * 功能:分页查询
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#selectAll(com.zd.epa.base.BaseEntity, com.zd.epa.utils.Pager)
	 */
	@Override
	public void selectAll(DrugAllocation entity, Pager<DrugAllocation> page) throws Exception {
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
	 * 审核
	 * @Description:TODO
	 * @param idArr
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-15 上午11:45:03
	 */
	public void check(String[] idArr)throws Exception{
		if(idArr == null || idArr.length == 0)
			throw new SystemException("请选择单据");
		Users u = SysContainer.get();
		StringBuffer sb = new StringBuffer();
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		for(String s : idArr){
			//1.获取对象
			DrugAllocation e = super.selectById(s);
			//2.获取明细并遍历明细
			List<DrugAllocationDtl> fiwdList = drugAllocationDtlService.selectBySingletAll("drugAllocation.id", s);
			if(e==null ||fiwdList==null || fiwdList.size()==0){
				sb.append("单据【"+s+"】对象为空或明细不存在<br>");
				continue;
			}
			List<DrugWarehouse> dwAllList = new ArrayList<DrugWarehouse>();
			for(DrugAllocationDtl ed : fiwdList){
				//2.1查询养殖公司该药品库存
				sqlMap.put("company.id", "=", e.getCompany().getId());
				sqlMap.put("drug.id", "=", ed.getDrug().getId());
				//加上饲料厂
				sqlMap.put("feedFac.id", "=", e.getFeedFac().getId());
				List<DrugWarehouse> dwList = drugWarehouseService.selectHQL(sqlMap);
				sqlMap.clear();
				//调出时，减库存
				if("1".equals(e.getAllotType())){
					//2.2判断库存是否存在
					if(dwList==null || dwList.size()==0){
						sb.append("单据【"+s+"】药品【"+ed.getDrug().getId()+"】公司没有库存<br>");
						continue;
					}else{
						//2.3该药品库存是否足够出库，足够则减库存
						DrugWarehouse dw = dwList.get(0);
						if(dw != null){
							//原始数据
							String q_old = dw.getQuantity();
							//单数据
							String q_new = ed.getQuantity();
							if(q_old==null || "".equals(q_old))
								q_old = "0";
							if(q_new==null || "".equals(q_new))
								q_new = "0";
							//2.3.1验证库存是否足够出库
							if(ArithUtil.comparison(q_old, q_new)<0){
								sb.append("单据【"+s+"】药品【"+dw.getDrug().getName()+"】公司库存不够<br>");
								continue;
							}
							//2.3.3扣库存
							String num = ArithUtil.sub(q_old, q_new,2);
							dw.setQuantity(num);
							//2.3.4更改副单位数量
							Drug drug=drugService.selectById(ed.getDrug().getId());
							String ratio=drug.getRatio();
							if(ratio!=null && !"".equals(ratio))
								dw.setSubQuantity(ArithUtil.div(num, ratio, 2));
						}
					}
				}
				//调入时，加库存
				if("0".equals(e.getAllotType())){
					//2.2判断库存是否存在
					if(dwList==null || dwList.size()==0){
						DrugWarehouse dw = new DrugWarehouse();
						dw.setIsDrug("0");
						dw.setCompany(e.getCompany());
						dw.setDrug(ed.getDrug());
						dw.setQuantity(ed.getQuantity());
						//增加饲料厂
						dw.setFeedFac(e.getFeedFac());
						Drug drug=drugService.selectById(ed.getDrug().getId());
						String ratio=drug.getRatio();
						if(ratio!=null && !"".equals(ratio))
							dw.setSubQuantity(ArithUtil.div(ed.getQuantity(), ratio, 2));
						dwAllList.add(dw);
					}else{
						//2.2.2库存有该药品库存
						DrugWarehouse dw = dwList.get(0);
						if(dw != null){
							//原始数据
							String q_old = dw.getQuantity();
							//单数据
							String q_new = ed.getQuantity();
							if(q_old==null || "".equals(q_old))
								q_old = "0";
							if(q_new==null || "".equals(q_new))
								q_new = "0";
							//更改库存数量
							String num = ArithUtil.add(q_old, q_new);
							if(ArithUtil.comparison(num, "0")<0){
								sb.append("单据【"+s+"】明细【"+dw.getDrug().getName()+"】库存不够<br>");
								continue ;
							}
							dw.setQuantity(num);
							//更改副单位数量
							Drug drug=drugService.selectById(ed.getDrug().getId());
							String ratio=drug.getRatio();
							if(ratio!=null && !"".equals(ratio))
								dw.setSubQuantity(ArithUtil.div(num, ratio, 2));
						}
					}
				}
			}
			if(dwAllList!=null && dwAllList.size()>0)
				drugWarehouseService.save(dwAllList);
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
		DrugAllocation e = super.selectById(id);
		//2.获取明细并遍历
		List<DrugAllocationDtl> fiwdList = drugAllocationDtlService.selectBySingletAll("drugAllocation.id", id);
		if(e==null ||fiwdList==null || fiwdList.size()==0)
			throw new SystemException("单据【"+id+"】对象为空或明细不存在");
		for(DrugAllocationDtl ed : fiwdList){
			//单数据
			String q_new = ed.getQuantity();
			if(q_new==null || "".equals(q_new))
				q_new = "0";
			//养殖公司的库存
			sqlMap.put("drug.id", "=", ed.getDrug().getId());
			sqlMap.put("company.id", "=", e.getCompany().getId());
			sqlMap.put("feedFac.id", "=", e.getFeedFac().getId());
			List<DrugWarehouse> dwlist = drugWarehouseService.selectHQL(sqlMap);
			sqlMap.clear();
			if(dwlist!=null && dwlist.size()>0){
				DrugWarehouse dw = dwlist.get(0);
				//原始数据
				String q_old=dw.getQuantity()==null?"0":("".equals(dw.getQuantity())?"0":dw.getQuantity());
				String num = "0";
				//调出时，恢复，加库存
				if("1".equals(e.getAllotType())){
					num = ArithUtil.add(q_old, q_new);
				}
				//调入时，恢复，减库存
				if("0".equals(e.getAllotType())){
					num = ArithUtil.sub(q_old, q_new);
				}
				//2.2.1增加养殖公司的库存
				dw.setQuantity(num);
				//2.2.2更改副单位数量
				Drug feed=drugService.selectById(ed.getDrug().getId());
				String ratio=feed.getRatio();
				if(ratio!=null && !"".equals(ratio))
					dw.setSubQuantity(ArithUtil.div(num, ratio, 2));
			}
		}
		e.setCheckDate(null);
		e.setCheckStatus("0");
		e.setCheckUser(null);
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
			fileInput = new FileInputStream(realPath + "/WEB-INF/template/" + "drugallocation.xlsx");
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new SystemException("系统找不到指定路径下的模板文件！");
		}
		return fileInput;
		
	}
	
	/**
	 * 导入药品调拨单
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-5 下午03:26:07
	 */
	public List<DrugAllocation> operateFile(File file, Company company, Object... objects)throws Exception{
		if(company==null || company.getId()==null || "".equals(company.getId()))
			throw new SystemException("请选择公司");
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		
		List<DrugAllocation> drugAllocations = new ArrayList<DrugAllocation>();
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
						String custv = ExcelUtil.checkCellValue(row.getCell(0), i + 1, "客户", true, sb);
						String allotType = ExcelUtil.checkCellValue(row.getCell(1), i + 1, "调拨类型", true, sb);
						String registDate = ExcelUtil.checkCellValue(row.getCell(2), i + 1, "调拨日期", true, sb);
						String remark = ExcelUtil.checkCellValue(row.getCell(3), i + 1, "备注", false, sb);
						String drug = ExcelUtil.checkCellValue(row.getCell(4), i + 1, "药品", true, sb);
						String quantity = ExcelUtil.checkCellValue(row.getCell(5), i + 1, "数量", true, sb);
						//添加饲料厂
						String feedFac=ExcelUtil.checkCellValue(row.getCell(6), i + 1, "饲料厂", true, sb);
						//验证
						//验证客户
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("name", "=", custv);
						List<CustVender> custVenders = custVenderService.selectHQL(sqlMap);
						sqlMap.clear();
						if(custVenders==null || custVenders.isEmpty())
							throw new SystemException("不存在客户户【"+custv+"】");
						//验证药品
						Drug dg = new Drug();
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("code", "=", drug);
						List<Drug> drugs = drugService.selectHQL(sqlMap);
						sqlMap.clear();
						if(drugs==null || drugs.isEmpty())
							throw new SystemException("不存在药品编码【"+drug+"】");
						else{
							dg = drugs.get(0);
						}
						//验证饲料厂
						FeedFac ff=new FeedFac();
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("name", "=", feedFac);
						List<FeedFac> feedFacs=feedFacService.selectHQL(sqlMap);
						sqlMap.clear();
						if(feedFacs==null || feedFacs.isEmpty())
							throw new SystemException("不存在饲料厂【"+feedFac+"】");
						else
							ff=feedFacs.get(0);
						//封装明细
						DrugAllocationDtl fiwd = new DrugAllocationDtl();
						fiwd.setDrug(dg);
						fiwd.setQuantity(ArithUtil.scale(quantity, 2));
						List<DrugAllocationDtl> drugAllocationDtls = new ArrayList<DrugAllocationDtl>();
						drugAllocationDtls.add(fiwd);
						//放入map
						String key=custv+","+allotType+","+registDate+","+feedFac;
						if(remark!=null)
							key = key+","+remark;
						if(map.containsKey(key)){
							List<DrugAllocationDtl> fwds = (List<DrugAllocationDtl>) map.get(key);
							fwds.add(fiwd);
						}else{
							map.put(key, drugAllocationDtls);
						}
					}
				}
				if(sb.length() > 0)
					throw new SystemException(sb.toString());
				if(map.isEmpty())
					throw new SystemException("无可用数据导入");
				//封装调拨单
				for(Map.Entry<String, Object> entry : map.entrySet() ){
					String key = entry.getKey();
					List<DrugAllocationDtl> wareDtls = (List<DrugAllocationDtl>) entry.getValue();
					if(wareDtls != null && wareDtls.size()>0){
						String[] fiw = key.split(",");
						//客户
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("name", "=", fiw[0]);
						List<CustVender> custVenders = custVenderService.selectHQL(sqlMap);
						sqlMap.clear();
						CustVender cv = custVenders.get(0);
						//饲料厂
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("name", "=", fiw[3]);
						List<FeedFac> feedFacs = feedFacService.selectHQL(sqlMap);
						sqlMap.clear();
						FeedFac ff = feedFacs.get(0);
						
						DrugAllocation da = new DrugAllocation();
						da.setCompany(company);
						da.setCustv(cv);
						da.setAllotType(fiw[1]);
						da.setRegistDate(fiw[2]);
						da.setFeedFac(ff);
						
						if(fiw.length==4)
							da.setRemark(fiw[4]);
						da.setDetails(wareDtls);
						
						drugAllocations.add(da);
					}
				}
				save(drugAllocations);
				//审核
				String ids = "";
				for(DrugAllocation c : drugAllocations){
					String id = c.getId();
					ids = ids+id+",";
				}
				String[] idArray = ids.split(",");
				check(idArray);
			}else
				throw new SystemException("无可用数据导入");
		}
		
		return drugAllocations;
	}
	
	
	
	
}
