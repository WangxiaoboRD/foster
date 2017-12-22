/**
 * 功能:
 * @author:wxb
 * @data:2017-7-27上午09:31:48
 * @file:MedicalBillServiceImpl.java
 */
package com.zd.foster.breed.services.impl;

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
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.breed.dao.IMedicalBillDao;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.breed.entity.MedicalBill;
import com.zd.foster.breed.entity.MedicalBillDtl;
import com.zd.foster.breed.services.IBatchService;
import com.zd.foster.breed.services.IMedicalBillDtlService;
import com.zd.foster.breed.services.IMedicalBillService;
import com.zd.foster.contract.entity.Contract;
import com.zd.foster.contract.services.IContractService;
import com.zd.foster.material.entity.Drug;
import com.zd.foster.material.services.IDrugService;
import com.zd.foster.warehouse.entity.DrugWarehouseFar;
import com.zd.foster.warehouse.services.IDrugOutWareDtlService;
import com.zd.foster.warehouse.services.IDrugOutWareService;
import com.zd.foster.warehouse.services.IDrugWarehouseFarService;
import com.zd.foster.warehouse.services.IDrugWarehouseService;

/**
 * 类名：  MedicalBillServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-7-27上午09:31:48
 * @version 1.0
 * 
 */
public class MedicalBillServiceImpl extends BaseServiceImpl<MedicalBill, IMedicalBillDao> implements
		IMedicalBillService {
	@Resource
	private IMedicalBillDtlService medicalBillDtlService; 
	@Resource
	private IDrugService drugService;
	@Resource
	private IContractService contractService;
	@Resource
	private IFarmerService farmerService;
	@Resource
	private IDrugWarehouseFarService drugWarehouseFarService;
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
	public void selectAll(MedicalBill entity, Pager<MedicalBill> page) throws Exception {
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
	 * 功能:保存医疗单
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#save(com.zd.epa.base.BaseEntity)
	 */
	public Object save(MedicalBill entity) throws Exception{
		//1验证表头
		//1.1表头是否为空
		if(entity == null)
			throw new SystemException("对象不能为空");
		//1.3代养户是否为空
		if(entity.getFarmer()==null || entity.getFarmer().getId()==null || "".equals(entity.getFarmer().getId()))
			throw new SystemException("代养户不能为空！");
		//1.3获取养殖公司
		Farmer farmer = farmerService.selectById(entity.getFarmer().getId());
		entity.setCompany(farmer.getCompany());
		//1.4批次是否为空
		if(entity.getBatch()==null || entity.getBatch().getId()==null || "".equals(entity.getBatch().getId()))
			throw new SystemException("批次不能为空！");
		//1.5耗药时间是否为空
		if(entity.getRegistDate()==null || "".equals(entity.getRegistDate()))
			throw new SystemException("耗药时间不能为空！");
		//2验证明细
		List<MedicalBillDtl> mbdList = entity.getDetails();
		//2.1明细是否为空
		if(mbdList == null || mbdList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<mbdList.size();i++){
			MedicalBillDtl mbd = mbdList.get(i);
			//2.2.1验证数量是否为空，是否为非正数
			if(mbd.getQuantity()==null || "".equals(mbd.getQuantity()))
				buff.append("第"+(i+1)+"行数量不能为空<br>");
//			else if(ArithUtil.comparison(mbd.getQuantity(), "0")<=0)
//					buff.append("第"+(i+1)+"行数值必须为正数！<br>");
			//2.2.2检查副单位数量是否是"",如果是设为null
			if("".equals(mbd.getSubQuantity()))
				mbd.setSubQuantity(null);
			mbd.setMedicalBill(entity);
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		entity.setCheckStatus("0");
		entity.setIsBalance("N");
		Object obj = super.save(entity);
		medicalBillDtlService.save(mbdList);
		return obj;
	}

	/**
	 * 
	 * 功能:修改喂料单
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#update(com.zd.epa.base.BaseEntity)
	 */
	public void update(MedicalBill entity)throws Exception{
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
		//1.5耗药时间是否为空
		if(entity.getRegistDate()==null || "".equals(entity.getRegistDate()))
			throw new SystemException("耗药时间不能为空！");
		//2验证明细
		List<MedicalBillDtl> mbdList = entity.getDetails();
		//2.1明细是否为空
		if(mbdList == null || mbdList.size()==0)
			throw new SystemException("单据明细不能为空");
		//2.2每条明细具体验证
		StringBuffer buff = new StringBuffer();
		for(int i=0;i<mbdList.size();i++){
			MedicalBillDtl mbd = mbdList.get(i);
			//2.2.1验证数量是否为空，是否为非正
			if(mbd.getQuantity()==null || "".equals(mbd.getQuantity()))
				buff.append("第"+(i+1)+"行数量不能为空<br>");
//			else if(ArithUtil.comparison(mbd.getQuantity(), "0")<=0)
//				buff.append("第"+(i+1)+"行数值必须为正数！<br>");
			//2.2.2检查副单位数量是否是"",如果是设为null
			if("".equals(mbd.getSubQuantity()))
				mbd.setSubQuantity(null);
		}
		if(buff.length() > 0)
			throw new SystemException(buff.toString());
		// 保存表头
		// 1 、获取数据库里的对象
		MedicalBill e = super.selectById(entity.getId());
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
					medicalBillDtlService.deleteById(Integer.parseInt(id));
			}
		}
		//2. 新增/修改明细
		List<MedicalBillDtl> updateSwd = new ArrayList<MedicalBillDtl>(); 
		List<MedicalBillDtl> newSwd = new ArrayList<MedicalBillDtl>(); 
		for(MedicalBillDtl p : mbdList){
			if(p.getId()==null){
				p.setMedicalBill(e);
				newSwd.add(p);
			}
			if(p.getId()!=null){
				updateSwd.add(p);
			}
		}
		//修改	
		for(MedicalBillDtl p : updateSwd){
			MedicalBillDtl ed = medicalBillDtlService.selectById(p.getId());
			ed.setQuantity(p.getQuantity());
			ed.setSubQuantity(p.getSubQuantity()==""?null:p.getSubQuantity());
			ed.setReason(p.getReason());
		}
		updateSwd.clear();
		//添加
		medicalBillDtlService.save(newSwd);
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
		sqlMap.put("medicalBill.id", "in", PapUtil.arrayToSQLStr((String[])PK));
		medicalBillDtlService.delete(sqlMap);
		return dao.deleteByIds(PK);
	}
	/**
	 * 
	 * 功能:审核耗药单,扣除代养户的药品库存
	 * 重写:wxb
	 * 2017-8-1
	 * @see com.zd.foster.breed.services.IMedicalBillService#check(java.lang.String[])
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
			MedicalBill e = super.selectById(s);
			
			Contract ct=e.getBatch().getContract();
			if(ct!=null && ct.getStatus()!=null ){
				if(!"BREED".equals(ct.getStatus().getDcode())){
					sb.append("单据【"+s+"】【"+e.getFarmer().getName()+"】第【"+e.getBatch().getBatchNumber()+"】批对应的合同不在养殖状态<br>");
					continue;
				}
			}
			
			//2.获取明细并遍历明细
			List<MedicalBillDtl> edList = medicalBillDtlService.selectBySingletAll("medicalBill.id", s);
			if(e==null ||edList==null || edList.size()==0)
				sb.append("单据【"+s+"】对象为空或明细不存在<br>");
			//2.**建立药品出库单表头
//			DrugOutWare dow=new DrugOutWare();
//			List<DrugOutWareDtl> dowdList=new ArrayList<DrugOutWareDtl>();
			for(MedicalBillDtl ed : edList){
				//2.1查询代养户该药品库存
				sqlMap.put("farmer.id", "=", e.getFarmer().getId());
				sqlMap.put("batch.id", "=", e.getBatch().getId()+"");
				sqlMap.put("drug.id", "=", ed.getDrug().getId());
				List<DrugWarehouseFar> dwList = drugWarehouseFarService.selectHQL(sqlMap);
				sqlMap.clear();
				//2.2判断库存是否存在
				if(dwList==null || dwList.size()==0){
					sb.append("单据【"+s+"】药品【"+ed.getDrug().getId()+"】代养户没有库存<br>");
					continue;
				}else{
					//2.3该药品库存是否足够出库，足够则减库存,建药品出库单
					DrugWarehouseFar dw = dwList.get(0);
					if(dw != null){
						//原始数据
						String q_old = dw.getQuantity();
						//医疗单数据
						String q_new = ed.getQuantity();
						if(q_old==null || "".equals(q_old))
							q_old = "0";
						if(q_new==null || "".equals(q_new))
							q_new = "0";
						//2.3.1验证库存是否足够出库
						if(ArithUtil.comparison(q_old, q_new)<0){
							sb.append("单据【"+s+"】药品【"+dw.getDrug().getName()+"】代养户库存不够<br>");
							continue;
						}
//						//2.3.2建立药品出库单明细
//						DrugOutWareDtl dowd=new DrugOutWareDtl();
//						dowd.setDrugOutWare(dow);
//						dowd.setDrug(ed.getDrug());
//						dowd.setQuantity(ed.getQuantity());
//						dowd.setSubQuantity(ed.getSubQuantity());
//						dowdList.add(dowd);
						//2.3.3扣库存
						String num = ArithUtil.sub(q_old, q_new,4);
						dw.setQuantity(num);
						//2.3.4添加副单位数量
						Drug drug=drugService.selectById(ed.getDrug().getId());
						String ratio=drug.getRatio();
						if(ratio!=null && !"".equals(ratio))
							dw.setSubQuantity(ArithUtil.div(num, ratio, 4));
					}
				}
			}
//			//3.1保存药品出库单表头和明细
//			dow.setCompany(e.getCompany());
//			dow.setFarmer(e.getFarmer());
//			dow.setRegistDate(e.getRegistDate());
//			dow.setMedicalBill(e);
//			dow.setCheckDate(PapUtil.date(new Date()));
//			dow.setCheckStatus("1");
//			dow.setCheckUser(u.getUserRealName());
//			drugOutWareService.save(dow);
//			drugOutWareDtlService.save(dowdList);
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
	 * 功能:撤销，恢复代养户的库存
	 * 重写:wxb
	 * 2017-8-1
	 * @see com.zd.foster.breed.services.IMedicalBillService#cancelCheck(java.lang.String)
	 */
	@Override
	public void cancelCheck(String id) throws Exception {
		if(id == null || "".equals(id))
			throw new SystemException("请选择单据");
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		//1.获取对象
		MedicalBill e = super.selectById(id);
		if(e.getIsBalance().equals("Y"))
			throw new SystemException("单据【"+id+"】已经结算<br>");
		
		Contract ct=e.getBatch().getContract();
		if(ct!=null && ct.getStatus()!=null ){
			if("LOST".equals(ct.getStatus().getDcode()))
				throw new SystemException("单据【"+id+"】对应的合同已经终止<br>");
		}
		
		//2.获取明细并遍历
		List<MedicalBillDtl> edList = medicalBillDtlService.selectBySingletAll("medicalBill.id", id);
		if(e==null ||edList==null || edList.size()==0)
			throw new SystemException("单据【"+id+"】对象为空或明细不存在");
		List<DrugWarehouseFar> dwAllList = new ArrayList<DrugWarehouseFar>();
		for(MedicalBillDtl ed : edList){
			//2.1查询药品的库存
			sqlMap.put("farmer.id", "=", e.getFarmer().getId());
			sqlMap.put("batch.id", "=", e.getBatch().getId()+"");
			sqlMap.put("drug.id", "=", ed.getDrug().getId());
			List<DrugWarehouseFar> dwList = drugWarehouseFarService.selectHQL(sqlMap);
			sqlMap.clear();
			//2.2没有库存新建库存
			if(dwList==null || dwList.size()==0){
				DrugWarehouseFar dw = new DrugWarehouseFar();
				dw.setCompany(e.getCompany());
				dw.setDrug(ed.getDrug());
				dw.setQuantity(ed.getQuantity());
				dw.setSubQuantity(ed.getSubQuantity());
				dwAllList.add(dw);
			}else{
				//2.3有库存则增加库存
				DrugWarehouseFar dw=dwList.get(0);
				//2.3.1.调整数量
				String newNum = ArithUtil.add(dw.getQuantity(), ed.getQuantity());
				dw.setQuantity(newNum);
				//2.3.2添加副单位数量
				Drug drug=drugService.selectById(ed.getDrug().getId());
				String ratio=drug.getRatio();
				if(ratio!=null && !"".equals(ratio))
					dw.setSubQuantity(ArithUtil.div(newNum, ratio, 2));
			}
		}
		//3.保存新的药品库存
		if(dwAllList !=null && dwAllList.size()>0)
			drugWarehouseFarService.save(dwAllList);
//		//4.删除药品出库单
//		DrugOutWare dow=drugOutWareService.selectByHQLSingle("from DrugOutWare e where e.medicalBill.id='"+id+"'");
//		drugOutWareService.deleteById(dow.getId());
		//5.撤销审核状态
		e.setCheckDate(null);
		e.setCheckStatus("0");
		e.setCheckUser(null);
	}

	/**
	 * 
	 * 功能:下载模板
	 * 重写:wxb
	 * 2017-9-12
	 * @see com.zd.foster.breed.services.IMedicalBillService#downloadTemplate(java.lang.String)
	 */
	@Override
	public InputStream downloadTemplate(String realPath) throws Exception {
		InputStream fileInput = null;
		try {
			fileInput = new FileInputStream(realPath + "/WEB-INF/template/" + "medicalBill.xlsx");
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new SystemException("系统找不到指定路径下的模板文件！");
		}
		return fileInput;
	}

	/**
	 * 
	 * 功能:导入药品耗用单
	 * 重写:wxb
	 * 2017-9-12
	 * @see com.zd.foster.breed.services.IMedicalBillService#operateFile(java.io.File, com.zd.foster.base.entity.Company, java.lang.Object[])
	 */
	@Override
	public List<MedicalBill> operateFile(File doc, Company company,
			Object... objects) throws Exception {
		if(company==null || company.getId()==null || "".equals(company.getId()))
			throw new SystemException("请选择公司");
		List<MedicalBill> details = new ArrayList<MedicalBill>();
		Workbook workbook = ExcelUtil.buildWorkbook(doc, (String)objects[0]);
		Map<String,List<MedicalBillDtl>> dtlMap = new HashMap<String,List<MedicalBillDtl>>();
		Map<String,MedicalBill> map = new HashMap<String,MedicalBill>();
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
						String registDate = ExcelUtil.checkCellValue(row.getCell(2), i + 1, "耗用日期", true, sb);
						String drug = ExcelUtil.checkCellValue(row.getCell(3), i + 1, "药品编码", true, sb);
						String quantity = ExcelUtil.checkCellValue(row.getCell(4), i + 1, "数量", true, sb);
						String reason = ExcelUtil.checkCellValue(row.getCell(5), i + 1, "原因", false, sb);
						quantity=ArithUtil.scale(quantity, 4);
						//验证代养户
						Farmer fr=new Farmer();
						String hql_farmer="from Farmer e where e.company.id='"+company.getId()+"'and e.name='"+farmer+"'";
						List<Farmer> fList=farmerService.selectByHQL(hql_farmer);
						if(fList==null || fList.isEmpty()){
							sb.append(new SystemException("第"+(i+1)+"行不存在代养户【"+farmer+"】"));
							continue;
						}else
							fr=fList.get(0);
						//验证批次
						Batch bh=new Batch();
						String hql_batch="from Batch e where e.company.id='"+company.getId()+"'and e.farmer.name='"+farmer+"'and e.batchNumber='"+batch+"'";
						List<Batch> bList=batchService.selectByHQL(hql_batch);
						if(bList==null || bList.isEmpty()){
							sb.append(new SystemException("第"+(i+1)+"行不存在代养户【"+farmer+"】批次【"+batch+"】"));
							continue;
						}else
							bh=bList.get(0);
						//验证药品
						Drug dg = new Drug();
						String hql_drug="from Drug e where e.company.id='"+company.getId()+"'and e.code='"+drug+"'";
						List<Drug> dList=drugService.selectByHQL(hql_drug);
						if(dList==null || dList.isEmpty()){
							sb.append(new SystemException("第"+(i+1)+"行不存在药品【"+drug+"】"));
							continue;
						}else
							dg = dList.get(0);
						//构造key
						String key=farmer+","+batch+","+registDate;
						//验证key在map中是否已经存在
						Set<String> dSet=map.keySet();
						if(dSet==null || dSet.size()==0 || !dSet.contains(key)){
							//不存在：直接创建表头对象
							MedicalBill diw=this.getInstance(company, fr, bh, registDate);
							//明细集合
							List<MedicalBillDtl> diwdList = new ArrayList<MedicalBillDtl>();
							//封装明细对象
							MedicalBillDtl diwd=this.getDtlInstance(diw, dg, quantity,reason);
							//明细添加到明细集合
							diwdList.add(diwd);
							dtlMap.put(key, diwdList);
							map.put(key, diw);
						}else{
							//取出表头和明细集合
							MedicalBill diw=map.get(key);
							List<MedicalBillDtl> diwdList=dtlMap.get(key);
							//封装明细对象
							MedicalBillDtl diwd=this.getDtlInstance(diw, dg, quantity,reason);
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
					MedicalBill e=map.get(s);
					details.add(e);
					List<MedicalBillDtl> ed=dtlMap.get(s);
					e.setDetails(ed);
					Object obj=null;
					try{
						obj=this.save(e);
					}catch(Exception ece){
						sb.append(new SystemException(e.getFarmer().getName()+e.getBatch().getBatchNumber()+"批次"+ece.getMessage()));
						continue;
					}
					String[] idArray=new String[]{(String)obj};
					try{
						this.check(idArray);
					}catch(Exception ece){
						sb.append(new SystemException(e.getFarmer().getName()+e.getBatch().getBatchNumber()+"批次"+ece.getMessage()));
					}
				}
				if(sb.length() > 0)
					throw new SystemException(sb.toString());
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
	 * @data:2017-9-12下午05:10:34
	 * @file:MedicalBillServiceImpl.java
	 * @param db
	 * @param drug
	 * @param quantity
	 * @return
	 * @throws Exception
	 */
	private MedicalBillDtl getDtlInstance(MedicalBill db,Drug drug,String quantity,String reason)throws Exception{
		MedicalBillDtl dbd = new MedicalBillDtl();
		dbd.setMedicalBill(db);
		dbd.setDrug(drug);
		dbd.setQuantity(quantity);
		String subquan = ArithUtil.div(quantity, drug.getRatio(), 2);
		dbd.setSubQuantity(subquan);
		dbd.setReason(reason);
		return dbd;
	}
	/**
	 * 
	 * 功能:封装表头对象
	 * @author:wxb
	 * @data:2017-9-12下午05:10:21
	 * @file:MedicalBillServiceImpl.java
	 * @param company
	 * @param farmer
	 * @param batch
	 * @param registDate
	 * @return
	 * @throws Exception
	 */
	private MedicalBill getInstance(Company company,Farmer farmer,Batch batch,String registDate)throws Exception{
		MedicalBill db=new MedicalBill();
		db.setRegistDate(registDate);
		db.setCompany(company);
		db.setFarmer(farmer);
		db.setBatch(batch);
		return db;
	}
	
	
	
}
