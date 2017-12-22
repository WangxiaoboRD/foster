package com.zd.foster.breed.services.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
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
import com.zd.epa.bussobj.entity.BussinessEleDetail;
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
import com.zd.foster.base.entity.CustVender;
import com.zd.foster.base.entity.Driver;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.entity.FeedFac;
import com.zd.foster.base.services.ICustVenderService;
import com.zd.foster.base.services.IDriverService;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.breed.dao.IPigPurchaseDao;
import com.zd.foster.breed.entity.Batch;
import com.zd.foster.breed.entity.FeedBill;
import com.zd.foster.breed.entity.MaterialBill;
import com.zd.foster.breed.entity.MedicalBill;
import com.zd.foster.breed.entity.PigPurchase;
import com.zd.foster.breed.services.IBatchService;
import com.zd.foster.breed.services.IFeedBillService;
import com.zd.foster.breed.services.IMaterialBillService;
import com.zd.foster.breed.services.IMedicalBillService;
import com.zd.foster.breed.services.IPigPurchaseService;
import com.zd.foster.contract.entity.Contract;
import com.zd.foster.contract.services.IContractService;

/**
 * 类名：  PigPurchaseServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-8-1下午04:25:28
 * @version 1.0
 * 
 */
public class PigPurchaseServiceImpl extends BaseServiceImpl<PigPurchase, IPigPurchaseDao> implements
		IPigPurchaseService {
	@Resource
	private IContractService contractService;
	@Resource
	private IBatchService batchService;
	@Resource
	private IFeedBillService feedBillService;
	@Resource
	private IMedicalBillService medicalBillService;
	@Resource
	private IMaterialBillService materialBillService;
	@Resource
	private IFarmerService farmerService;
	@Resource
	private JDBCWrapperx jdbc;
	@Resource
	private IDriverService driverService ;
	@Resource
	private ICustVenderService custVenderService;
	
	/**
	 * 
	 * 功能:分页查询
	 * 重写:wxb
	 * 2017-7-27
	 * @see com.zd.epa.base.BaseServiceImpl#selectAll(com.zd.epa.base.BaseEntity, com.zd.epa.utils.Pager)
	 */
	@Override
	public void selectAll(PigPurchase entity, Pager<PigPurchase> page) throws Exception {
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
	 * 功能:保存猪苗登记单
	 * 重写:wxb
	 * 2017-8-2
	 * @see com.zd.epa.base.BaseServiceImpl#save(com.zd.epa.base.BaseEntity)
	 */
	@Override
	public Object save(PigPurchase entity) throws Exception {
		//2.验证代养户不为空
		if(entity.getFarmer()==null || entity.getFarmer().getId()==null || "".equals(entity.getFarmer().getId()))
			throw new SystemException("代养户不能为空");
		//1.3获取养殖公司
		Farmer farmer = farmerService.selectById(entity.getFarmer().getId());
		entity.setCompany(farmer.getCompany());
		//3.验证批次不为空
		if(entity.getBatch()==null || entity.getBatch().getId()==null || "".equals(entity.getBatch().getId()))
			throw new SystemException("批次不能为空");
		//4.登记时间不为空
		if(entity.getRegistDate()==null || "".equals(entity.getRegistDate()))
			throw new SystemException("登记时间不能为空");
		else{
			Batch batch = batchService.selectById(entity.getBatch().getId());
			String sdate = batch.getContract().getRegistDate();
			int i = PapUtil.compareDate(sdate, entity.getRegistDate());
			if(i<0)
				throw new SystemException("【"+entity.getFarmer().getName()+"第"+entity.getBatch().getBatchNumber()+"批】猪苗登记时间不能在合同起始日期前");
		}
		//5.数量不为空
		if(entity.getQuantity()==null || "".equals(entity.getQuantity()))
			throw new SystemException("登记数量不能为空");
		else if(ArithUtil.comparison(entity.getQuantity(), "0")<=0)
			throw new SystemException("登记数量必须为正整数");
		//6.重量不为空
		if(entity.getWeight()==null || "".equals(entity.getWeight()))
			throw new SystemException("登记重量不能为空");
		else if(ArithUtil.comparison(entity.getWeight(), "0")<0)
			throw new SystemException("登记重量必须为非负数");
		//7.日龄不为空
		if(entity.getDays()==null || "".equals(entity.getDays()))
			throw new SystemException("日龄不能为空");
		else if(ArithUtil.comparison(entity.getDays(), "0")<=0)
			throw new SystemException("日龄必须为正整数");
		//赠送头数
		if(entity.getGiveQuantity() !=null && !"".equals(entity.getGiveQuantity())){
			if(!PapUtil.isInt(entity.getGiveQuantity()))
				throw new SystemException("赠送头数必须为正整数");
		}else{
			entity.setGiveQuantity("0");
		}
		//8.成本不为空
		if(entity.getCost()==null || "".equals(entity.getCost()))
			throw new SystemException("成本不能为空");
		else if(ArithUtil.comparison(entity.getCost(), "0")<0)
			throw new SystemException("成本必须为非负数");
		//9.司机不填置为空
		if(entity.getDriver()==null ||entity.getDriver().getId()==null || "".equals(entity.getDriver().getId()))
			throw new SystemException("司机不能为空");
		//10.运输公司不填置为空
		if(entity.getTransportCo()==null ||entity.getTransportCo().getId()==null || "".equals(entity.getTransportCo().getId()))
			entity.setTransportCo(null);
		//11.供应商不填置为空
		if(entity.getPigletSupplier()==null ||entity.getPigletSupplier().getId()==null || "".equals(entity.getPigletSupplier().getId()))
			entity.setPigletSupplier(null);
		//12.运费不能为负
		if(entity.getFreight()==null || "".equals(entity.getFreight()))
			throw new SystemException("运费不能为空");
		else if(ArithUtil.comparison(entity.getFreight(), "0")<0)
			throw new SystemException("运费必须为非负数");
		//13.成本单价不能为负
		if(entity.getPrice()!=null && !"".equals(entity.getPrice()))
			if(ArithUtil.comparison(entity.getPrice(), "0")<0)
				throw new SystemException("成本单价必须为非负数");
		entity.setCheckStatus("0");
		entity.setIsBalance("N");
		entity.setIsReceipt("N");
		return super.save(entity);
	}
	
	/**
	 * 
	 * 功能:修改猪苗登记单
	 * 重写:wxb
	 * 2017-8-2
	 * @see com.zd.epa.base.BaseServiceImpl#updateHql(com.zd.epa.base.BaseEntity)
	 */
	@Override
	public void update(PigPurchase entity) throws Exception {
		//2.验证代养户不为空
		if(entity.getFarmer()==null || entity.getFarmer().getId()==null || "".equals(entity.getFarmer().getId()))
			throw new SystemException("代养户不能为空");
		//3.验证批次不为空
		if(entity.getBatch()==null || entity.getBatch().getId()==null || "".equals(entity.getBatch().getId()))
			throw new SystemException("批次不能为空");
		//4.登记时间不为空
		if(entity.getRegistDate()==null || "".equals(entity.getRegistDate()))
			throw new SystemException("登记时间不能为空");
		else{
			Batch batch = batchService.selectById(entity.getBatch().getId());
			String sdate = batch.getContract().getRegistDate();
			int i = PapUtil.compareDate(sdate, entity.getRegistDate());
			if(i<0)
				throw new SystemException("登记时间不能在合同起始日期前");
		}
		//5.数量不为空
		if(entity.getQuantity()==null || "".equals(entity.getQuantity()))
			throw new SystemException("登记数量不能为空");
		else if(ArithUtil.comparison(entity.getQuantity(), "0")<=0)
			throw new SystemException("登记数量必须为正整数");
		//6.重量不为空
		if(entity.getWeight()==null || "".equals(entity.getWeight()))
			throw new SystemException("登记重量不能为空");
		else if(ArithUtil.comparison(entity.getWeight(), "0")<0)
			throw new SystemException("登记重量必须为非负数");
		//7.日龄不为空
		if(entity.getDays()==null || "".equals(entity.getDays()))
			throw new SystemException("日龄不能为空");
		else if(ArithUtil.comparison(entity.getDays(), "0")<=0)
			throw new SystemException("日龄必须为正整数");
		//赠送头数
		if(entity.getGiveQuantity() !=null && !"".equals(entity.getGiveQuantity())){
			if(!PapUtil.isInt(entity.getGiveQuantity()))
				throw new SystemException("赠送头数必须为正整数");
		}else
			entity.setGiveQuantity("0");
		//8.成本不为空
		if(entity.getCost()==null || "".equals(entity.getCost()))
			throw new SystemException("成本不能为空");
		else if(ArithUtil.comparison(entity.getCost(), "0")<0)
			throw new SystemException("成本必须为非负数");
		//9.司机不填置为空
		if(entity.getDriver()==null ||entity.getDriver().getId()==null || "".equals(entity.getDriver().getId()))
			entity.setDriver(null);
		//10.运输公司不填置为空
		if(entity.getTransportCo()==null ||entity.getTransportCo().getId()==null || "".equals(entity.getTransportCo().getId()))
			entity.setTransportCo(null);
		//11.供应商不填置为空
		if(entity.getPigletSupplier()==null ||entity.getPigletSupplier().getId()==null || "".equals(entity.getPigletSupplier().getId()))
			entity.setPigletSupplier(null);
		//12.运费不能为负
		if(entity.getFreight()==null || "".equals(entity.getFreight()))
			throw new SystemException("运费不能为空");
		else if(ArithUtil.comparison(entity.getFreight(), "0")<0)
			throw new SystemException("运费必须为非负数");
		//13.成本单价不能为负
		if(entity.getPrice()!=null && !"".equals(entity.getPrice()))
			if(ArithUtil.comparison(entity.getPrice(), "0")<0)
				throw new SystemException("成本单价必须为非负数");
		//-----------
		PigPurchase e=super.selectById(entity.getId());
		e.setBatch(entity.getBatch());
		e.setRegistDate(entity.getRegistDate());
		e.setPigletSupplier(entity.getPigletSupplier());
		e.setTransportCo(entity.getTransportCo());
		e.setDriver(entity.getDriver());
		e.setQuantity(entity.getQuantity());
		e.setTechnician(entity.getTechnician());
		e.setGiveQuantity(entity.getGiveQuantity());
		e.setCarNum(entity.getCarNum());
		e.setWeight(entity.getWeight());
		e.setFreight(entity.getFreight());
		e.setPrice(entity.getPrice());
		e.setCost(entity.getCost());
		e.setDays(entity.getDays());
		e.setRemark(entity.getRemark());
		//-----------
	}
	
	/**
	 * 
	 * 功能:审核
	 * 重写:wxb
	 * 2017-8-3
	 * @see com.zd.foster.breed.services.IPigPurchaseService#check(java.lang.String[])
	 */
	@Override
	public void check(String[] idArr) throws Exception {
		if(idArr == null || idArr.length == 0)
			throw new SystemException("请选择单据");
		Users u = SysContainer.get();
		StringBuffer sb = new StringBuffer();
		for(String s : idArr){
			PigPurchase e=super.selectById(s);
			//1.合同状态变为养殖状态
			Contract ct=e.getBatch().getContract();
			if(ct!=null && ct.getStatus()!=null ){
				if("LOST".equals(ct.getStatus().getDcode())){
					sb.append("单据【"+s+"】【"+e.getFarmer().getName()+"】第【"+e.getBatch().getBatchNumber()+"】批对应的合同不在养殖或生效状态<br>");
					continue;
				}
			}
			
			ct.setStatus(new BussinessEleDetail("BREED"));
			//2.批次更新信息
			Batch batch=batchService.selectById(e.getBatch().getId());
			String oldQuantity=batch.getQuantity()==null?"0":batch.getQuantity();
			String oldPigletQuan=batch.getPigletQuan()==null?"0":batch.getPigletQuan();
			
			String allNewNum = ArithUtil.add(oldQuantity, e.getQuantity(), 0);
			String inAllNum = ArithUtil.add(oldPigletQuan, e.getQuantity(), 0);
			if(e.getGiveQuantity() != null && PapUtil.checkDouble(e.getGiveQuantity())){
				allNewNum = ArithUtil.add(allNewNum, e.getGiveQuantity(), 0);
				inAllNum = ArithUtil.add(inAllNum, e.getGiveQuantity(), 0);
			}
			batch.setQuantity(allNewNum);
			batch.setPigletQuan(inAllNum);
			
			//计算预计出栏时间
			if(batch.getOutDate()==null || "".equals(batch.getOutDate())){
				String days = e.getDays();
				if(days != null && PapUtil.checkDouble(days)){
					String deptDays = ArithUtil.sub("170", days,0);
					//根据进猪日期与天数计算出栏日期
					String d = PapUtil.getDayNoTime(Integer.parseInt(deptDays), e.getRegistDate());
					batch.setOutDate(d);
				}
			}
			if(batch.getInDate()==null || "".equals(batch.getInDate()))
				batch.setInDate(e.getRegistDate());
			else{
				if(PapUtil.compareDate(batch.getInDate(), e.getRegistDate())==-1)
					batch.setInDate(e.getRegistDate());
				
			}
			//3.改变审核信息
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
	 * 2017-8-3
	 * @see com.zd.foster.breed.services.IPigPurchaseService#cancelCheck(java.lang.String)
	 */
	@Override
	public void cancelCheck(String id) throws Exception {
		if(id == null || "".equals(id))
			throw new SystemException("请选择单据");
		PigPurchase e = super.selectById(id);
		if(e.getIsBalance().equals("Y"))
			throw new SystemException("单据【"+id+"】已经结算<br>");
		
		Contract ct=e.getBatch().getContract();
		if(ct!=null && ct.getStatus()!=null ){
			if("LOST".equals(ct.getStatus().getDcode()))
				throw new SystemException("单据【"+id+"】对应的合同已经终止<br>");
		}
		
		StringBuffer sb = new StringBuffer();
		Batch batch=batchService.selectById(e.getBatch().getId());
		List<PigPurchase> ppList=super.selectByHQL("from PigPurchase e where e.batch.id="+e.getBatch().getId()+" and e.checkStatus=1");
		if(ppList.size()>1){
			//1.1有多批猪苗登记
			String nowQuantity=ArithUtil.sub(batch.getQuantity(), e.getQuantity(), 0);
			String pigletQuantity = ArithUtil.sub(batch.getPigletQuan(), e.getQuantity(), 0);
			if(e.getGiveQuantity() != null && PapUtil.checkDouble(e.getGiveQuantity())){
				nowQuantity = ArithUtil.sub(nowQuantity, e.getGiveQuantity(), 0);
				pigletQuantity = ArithUtil.sub(pigletQuantity, e.getGiveQuantity(), 0);
			}
			if(ArithUtil.comparison(nowQuantity, "0")<0)
				throw new SystemException("现存头数不够撤销！");
			if(ArithUtil.comparison(pigletQuantity, "0")<0)
				throw new SystemException("进猪头数不够撤销！");
			batch.setQuantity(nowQuantity);
			batch.setPigletQuan(pigletQuantity);
		}else{
			//2.1只登记一批猪苗，头数是否相符
			if(e.getGiveQuantity()==null || "".equals(e.getGiveQuantity()))
				e.setGiveQuantity("0");
			if(ArithUtil.comparison(batch.getQuantity(), ArithUtil.add(e.getQuantity(), e.getGiveQuantity()))!=0)
				throw new SystemException("现存头数与登记头数不一致,先撤销 销售单，死亡单！");
			else{
				//2.2是否有喂料单，医疗单，领用单
				List<FeedBill> fbList=feedBillService.selectByHQL("from FeedBill e where e.batch.id="+e.getBatch().getId()+" and e.checkStatus=1");
				List<MedicalBill> mbList=medicalBillService.selectByHQL("from MedicalBill e where e.batch.id="+e.getBatch().getId()+" and e.checkStatus=1");
				List<MaterialBill> mabList=materialBillService.selectByHQL("from MaterialBill e where e.batch.id="+e.getBatch().getId()+" and e.checkStatus=1");
				boolean flag=true;
				if(batch.getEliminateQuan()!=null)
					if(ArithUtil.comparison(batch.getEliminateQuan(),"0")>0){
						sb.append("先撤销淘汰单！<br>");
						flag=false;
					}
				if(fbList!=null && fbList.size()>0){
					sb.append("先撤销喂料单！<br>");
					flag=false;
				}
				if(mbList!=null && mbList.size()>0){
					sb.append("先撤销医疗单！<br>");
					flag=false;
				}
				if(mabList!=null && mabList.size()>0){
					sb.append("先撤销领用单！<br>");
					flag=false;
				}
				if(!flag)
					throw new SystemException(sb.toString());
				//2.3批次信息更改
				batch.setQuantity(null);
				batch.setPigletQuan(null);
				batch.setOutDate(null);
				batch.setInDate(null);
				//2.4合同状态更改
				//Contract ct1=contractService.selectByHQLSingle("from Contract e where e.batch.id='"+e.getBatch().getId()+"'");
				ct.setStatus(new BussinessEleDetail("EFFECT"));
			}
		}
		//撤销审核状态
		e.setCheckDate(null);
		e.setCheckStatus("0");
		e.setCheckUser(null);
	}
	
	/**
	 * 
	 * 功能:回执
	 * 重写:wxb
	 * 2017-9-10
	 * @see com.zd.foster.breed.services.IPigPurchaseService#receipt(java.lang.String)
	 */
	@Override
	public void receipt(String[] idArr) throws Exception {
		for(String s:idArr){
			PigPurchase c = super.selectById(s);
			c.setIsReceipt("Y");
		}
	}
	
	/**
	 * 
	 * 功能:撤销回执
	 * 重写:wxb
	 * 2017-9-10
	 * @see com.zd.foster.breed.services.IPigPurchaseService#cancelReceipt(java.lang.String)
	 */
	@Override
	public void cancelReceipt(String id) throws Exception {
		PigPurchase c = super.selectById(id);
		c.setIsReceipt("N");
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
			fileInput = new FileInputStream(realPath + "/WEB-INF/template/" + "pigpurchase.xlsx");
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new SystemException("系统找不到指定路径下的模板文件！");
		}
		return fileInput;
		
	}
	
	/**
	 * 导入猪苗登记
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-5 下午03:26:07
	 */
	public List<PigPurchase> operateFile(File file, Company company, Object... objects)throws Exception{
		if(company==null || company.getId()==null || "".equals(company.getId()))
			throw new SystemException("请选择公司");
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		
		List<PigPurchase> pigPurchases = new ArrayList<PigPurchase>();
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
						String registDate = ExcelUtil.checkCellValue(row.getCell(0), i + 1, "登记日期", true, sb);
						String farmer = ExcelUtil.checkCellValue(row.getCell(1), i + 1, "代养户", true, sb);
						String batch = ExcelUtil.checkCellValue(row.getCell(2), i + 1, "批次", true, sb);
						String quantity = ExcelUtil.checkCellValue(row.getCell(3), i + 1, "头数", true, sb);
						String driver = ExcelUtil.checkCellValue(row.getCell(4), i + 1, "司机", true, sb);
						String pigletSupplier = ExcelUtil.checkCellValue(row.getCell(5), i + 1, "供应商", true, sb);
						String weight = ExcelUtil.checkCellValue(row.getCell(6), i + 1, "重量(KG)", true, sb);
						String freight = ExcelUtil.checkCellValue(row.getCell(7), i + 1, "运费(元)", true, sb);
						String cost = ExcelUtil.checkCellValue(row.getCell(8), i + 1, "成本(元)", true, sb);
						String days = ExcelUtil.checkCellValue(row.getCell(9), i + 1, "日龄", true, sb);
						String remark = ExcelUtil.checkCellValue(row.getCell(10), i + 1, "备注", false, sb);
						String price = ExcelUtil.checkCellValue(row.getCell(11), i + 1, "单价", false, sb);
						String give = ExcelUtil.checkCellValue(row.getCell(12), i + 1, "赠送头数", false, sb);
						//验证
						//验证代养户
						Farmer fam = new Farmer();
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("name", "=", farmer);
						List<Farmer> farmers = farmerService.selectHQL(sqlMap);
						sqlMap.clear();
						if(farmers==null || farmers.isEmpty())
							throw new SystemException("不存在代养户【"+farmer+"】");
						else{
							fam = farmers.get(0);
						}
						//验证批次
						Batch bat = new Batch();
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("farmer.name", "=", farmer);
						sqlMap.put("batchNumber", "=", batch);
						List<Batch> batchs = batchService.selectHQL(sqlMap);
						sqlMap.clear();
						if(batchs==null || batchs.isEmpty())
							throw new SystemException("不存在代养户【"+farmer+"】批次【"+batch+"】");
						else{
							bat = batchs.get(0);
						}
						//验证司机
						Driver dri = new Driver();
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("name", "=", driver);
						List<Driver> drivers = driverService.selectHQL(sqlMap);
						sqlMap.clear();
						if(drivers==null || drivers.isEmpty())
							throw new SystemException("不存在司机【"+driver+"】");
						else{
							dri = drivers.get(0);
						}
						//验证供应商
						CustVender cv = new CustVender();
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("name", "=", pigletSupplier);
						List<CustVender> custVenders = custVenderService.selectHQL(sqlMap);
						sqlMap.clear();
						if(custVenders==null || custVenders.isEmpty())
							throw new SystemException("不存在供应商【"+pigletSupplier+"】");
						else{
							cv = custVenders.get(0);
						}
						
						//封装
						PigPurchase ppu = new PigPurchase();
						ppu.setRegistDate(registDate);
						ppu.setFarmer(fam);
						ppu.setBatch(bat);
						ppu.setPigletSupplier(cv);
						ppu.setQuantity(quantity);
						ppu.setWeight(ArithUtil.scale(weight, 2));
						ppu.setFreight(freight);
						ppu.setCost(ArithUtil.scale(cost, 2));
						ppu.setDays(days);
						ppu.setDriver(dri);
						ppu.setCompany(company);
						ppu.setRemark(remark);
						ppu.setPrice(price);
						ppu.setGiveQuantity(give);
						
						pigPurchases.add(ppu);
					}
				}
				if(sb.length() > 0)
					throw new SystemException(sb.toString());
				if(pigPurchases.isEmpty())
					throw new SystemException("无可用数据导入");
				save(pigPurchases);
				//审核
				String ids = "";
				for(PigPurchase c : pigPurchases){
					String id = c.getId();
					ids = ids+id+",";
				}
				String[] idArray = ids.split(",");
				check(idArray);
			}else
				throw new SystemException("无可用数据导入");
		}
		return pigPurchases;
	}
	
	
	/**
	 * 查询猪苗登记报表
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-17 上午10:34:18
	 */
	public List<PigPurchase> selectPigPurchaseByPage(PigPurchase entity,Pager<PigPurchase> page)throws Exception{
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		
		if(entity.getCompany()!=null && entity.getCompany().getId()!=null && !"".equals(entity.getCompany().getId()))
			sqlMap.put("company.id", "=", entity.getCompany().getId());
		else
			throw new SystemException("请选择养殖公司");
		if(entity.getFarmer()!=null && entity.getFarmer().getId()!=null && !"".equals(entity.getFarmer().getId()))
			sqlMap.put("farmer.id", "=", entity.getFarmer().getId());
		if(entity.getBatch()!=null && entity.getBatch().getId()!=null && !"".equals(entity.getBatch().getId()))
			sqlMap.put("batch.id", "=", entity.getBatch().getId());
		
		if(entity.getIsReceipt()!=null && !"".equals(entity.getIsReceipt()))
			sqlMap.put("isReceipt", "=", entity.getIsReceipt());
		if(entity.getPigletSupplier()!=null && entity.getPigletSupplier().getId()!=null && !"".equals(entity.getPigletSupplier().getId()))
			sqlMap.put("pigletSupplier.id", "=", entity.getPigletSupplier().getId());
		
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
		sqlMap.put("batch.contract.feedFac.id,id", "order by", "desc");
		//设置排序字节id
		//page.setSortName("id");
		//page.setSortorder("desc");
		selectHQL(sqlMap, page);
		List<PigPurchase> dbds = page.getResult();
		if(dbds != null && dbds.size()>0){
			for(PigPurchase dbd : dbds){
				dbd.setAveWeight(ArithUtil.div(dbd.getWeight(), dbd.getQuantity(),2));
				dbd.setActPrice(ArithUtil.div(dbd.getCost(), dbd.getQuantity(),2));
			}
		}
		//合计
		String totalWeight = "0";
		String totalQuan = "0";
		String totalCost = "0";
		String totalFre = "0";
		
		String[] total = selectPigPurchaseRown(entity);
		totalQuan = total[0];
		totalWeight = total[1];
		totalCost = total[2];
		totalFre = total[3];
		
//		List<PigPurchase> purchases = selectHQL(sqlMap);
//		if(purchases != null && purchases.size()>0){
//			for(PigPurchase pp : purchases){
//				totalWeight = ArithUtil.add(totalWeight, pp.getWeight(),2);
//				totalQuan = ArithUtil.add(totalQuan, pp.getQuantity());
//				totalCost = ArithUtil.add(totalCost, pp.getCost(),2);
//				totalFre = ArithUtil.add(totalFre, pp.getFreight(),2);
//			}
//		}
		
		PigPurchase da = new PigPurchase();
		da.setRegistDate("合计");
		da.setQuantity(totalQuan);
		da.setWeight(totalWeight);
		da.setCost(totalCost);
		da.setFreight(totalFre);
		
		dbds.add(da);
		
		return dbds;
	}
	
	/**
	 * 查询猪苗登记总头数与总金额
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * float[]
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-20 上午09:12:26
	 */
	public String[] selectPigPurchaseRown(PigPurchase entity)throws Exception{
		String totalWeight = "0";
		String totalQuan = "0";
		String totalCost = "0";
		String totalFre = "0";
		
		String fromSql = " from FS_PIGPURCHASE t ";
		String whereSql = " where 1=1 ";
		// 查询条件
		if(entity.getCompany()!=null && entity.getCompany().getId()!=null && !"".equals(entity.getCompany().getId()))
			whereSql += " and t.company= '"+entity.getCompany().getId()+"' ";
		if(entity.getFarmer()!=null && entity.getFarmer().getId()!=null && !"".equals(entity.getFarmer().getId()))
			whereSql += " and t.farmer= '"+entity.getFarmer().getId()+"' ";
		if(entity.getBatch()!=null && entity.getBatch().getId()!=null && !"".equals(entity.getBatch().getId()))
			whereSql += " and t.batch= '"+entity.getBatch().getId()+"' ";
		
		if(entity.getIsReceipt()!=null && !"".equals(entity.getIsReceipt()))
			whereSql += " and t.isreceipt= '"+entity.getIsReceipt()+"' ";
		if(entity.getPigletSupplier()!=null && entity.getPigletSupplier().getId()!=null && !"".equals(entity.getPigletSupplier().getId()))
			whereSql += " and t.pigletsupplier= '"+entity.getPigletSupplier().getId()+"' ";
		
		Map<String, String> ts = entity.getTempStack();
		if (null != ts) {
			//开始时间 
			String startDate = ts.get("startTime");
			if (null != startDate && !"".equals(startDate)) {
				whereSql += " and t.registdate>='" + startDate + "'";
			}
			//结束时间
			String endDate = ts.get("endTime");
			if (null != endDate && !"".equals(endDate)) {
				whereSql += " and t.registdate<='" + endDate + "'";
			}
		}
		
		String countSql = "select sum(t.quantity),sum(t.weight),sum(t.cost),sum(t.freight) "
			+ fromSql.toString() + whereSql.toString(); // 统计总条数sql语句
		
		Connection con = null;
		try {
			con = jdbc.getConnection();
			Object[][] rows = jdbc.doQuerry(countSql, con);
			if (null != rows && rows.length > 1){
				totalQuan = ArithUtil.scale(rows[1][0].toString(), 0);
				totalWeight = ArithUtil.scale(rows[1][1].toString(),2);
				totalCost = ArithUtil.scale(rows[1][2].toString(),2);
				totalFre = ArithUtil.scale(rows[1][3].toString(),2);
			}
		} finally {
			jdbc.destroy(con);
		}
			
		String[] total = {totalQuan,totalWeight,totalCost,totalFre};
		return total;
	}
	
	/**
	 * 导出猪苗登记报表
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-17 下午04:19:36
	 */
	public InputStream exportPigPurchase(PigPurchase entity)throws Exception{
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("猪苗登记报表");
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		//设置表头样式
		HSSFCellStyle headerStyle = ExcelUtil.getHeaderStyle(workbook);
		//设置表格样式
		HSSFCellStyle cellStyle = ExcelUtil.getCellStyle(workbook);
		String exportFields = "年,月,日期,区域,代养户,批次,数量,重量,均重,日龄,单价,实际单价,金额,来源场,运费,司机,回执状态,备注";
		
		String[] fields = exportFields.split(",");
		// 生成表头标题行
		int cellIndex = -1;
		for (String field : fields) {
			ExcelUtil.setCellValue(sheet, headerStyle, 0, ++cellIndex, field);
		}
		// 生成数据行
		int index = 0;
		List<PigPurchase> datas = selectPigPurchase(entity);
		if (null != datas && datas.size() > 0) {
			for (PigPurchase obj : datas) {
				index ++;
				HSSFRow row = sheet.createRow(index);
				
				String rdate = obj.getRegistDate();
				String[] fiw = rdate.split("-");
				HSSFCell cell = row.createCell(0);
				cell.setCellStyle(cellStyle);
				HSSFCell cell1 = row.createCell(1);
				cell1.setCellStyle(cellStyle);
				if(fiw.length>2){
					ExcelUtil.setCellValue(cell, fiw[0]);
					ExcelUtil.setCellValue(cell1, fiw[1]);
				}
				
				HSSFCell cell2 = row.createCell(2);
				cell2.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell2, obj.getRegistDate());
				
				HSSFCell cell3 = row.createCell(3);
				cell3.setCellStyle(cellStyle);
				
				
				Batch b = obj.getBatch();
				if(b != null){
					Contract c = b.getContract();
					if(c != null){
						FeedFac f = c.getFeedFac();
						ExcelUtil.setCellValue(cell3, f==null?"":f.getName());
					}
				}
				
				HSSFCell cell4 = row.createCell(4);
				cell4.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell4, obj.getFarmer()==null?"":obj.getFarmer().getName());
				
				HSSFCell cell5 = row.createCell(5);
				cell5.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell5, obj.getBatch()==null?"":obj.getBatch().getBatchNumber());
				
				HSSFCell cell6 = row.createCell(6);
				cell6.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell6, obj.getQuantity());
				
				HSSFCell cell7 = row.createCell(7);
				cell7.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell7, obj.getWeight());
				
				HSSFCell cell8 = row.createCell(8);
				cell8.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell8, obj.getAveWeight());
				
				HSSFCell cell9 = row.createCell(9);
				cell9.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell9, obj.getDays());
				
				HSSFCell cell10 = row.createCell(10);
				cell10.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell10, obj.getPrice());
				
				HSSFCell cell11 = row.createCell(11);
				cell11.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell11, obj.getActPrice());
				
				HSSFCell cell12 = row.createCell(12);
				cell12.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell12, obj.getCost());
				
				HSSFCell cell13 = row.createCell(13);
				cell13.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell13, obj.getPigletSupplier()==null?"":obj.getPigletSupplier().getName());
				
				HSSFCell cell14 = row.createCell(14);
				cell14.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell14, obj.getFreight());
				
				HSSFCell cell15 = row.createCell(15);
				cell15.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell15, obj.getDriver()==null?"":obj.getDriver().getName());
				
				HSSFCell cell16 = row.createCell(16);
				cell16.setCellStyle(cellStyle);
				String hz = obj.getIsReceipt();
				if("Y".equals(hz))
					ExcelUtil.setCellValue(cell16, "已回执");
				if("N".equals(hz))
					ExcelUtil.setCellValue(cell16, "未回执");
				
				HSSFCell cell17 = row.createCell(17);
				cell17.setCellStyle(cellStyle);
				ExcelUtil.setCellValue(cell17, obj.getRemark());
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
	 * 查询猪苗登记报表不分页
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * List<PigPurchase>
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-19 下午02:33:35
	 */
	private List<PigPurchase> selectPigPurchase(PigPurchase entity)throws Exception {
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		
		if(entity.getCompany()!=null && entity.getCompany().getId()!=null && !"".equals(entity.getCompany().getId()))
			sqlMap.put("company.id", "=", entity.getCompany().getId());
		if(entity.getFarmer()!=null && entity.getFarmer().getId()!=null && !"".equals(entity.getFarmer().getId()))
			sqlMap.put("farmer.id", "=", entity.getFarmer().getId());
		if(entity.getBatch()!=null && entity.getBatch().getId()!=null && !"".equals(entity.getBatch().getId()))
			sqlMap.put("batch.id", "=", entity.getBatch().getId());
		
		if(entity.getIsReceipt()!=null && !"".equals(entity.getIsReceipt()))
			sqlMap.put("isReceipt", "=", entity.getIsReceipt());
		if(entity.getPigletSupplier()!=null && entity.getPigletSupplier().getId()!=null && !"".equals(entity.getPigletSupplier().getId()))
			sqlMap.put("pigletSupplier.id", "=", entity.getPigletSupplier().getId());
		
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
		sqlMap.put("batch.contract.feedFac.id,id", "order by", "desc");
		//合计
		String totalWeight = "0";
		String totalQuan = "0";
		String totalCost = "0";
		String totalFre = "0";
		List<PigPurchase> purchases = selectHQL(sqlMap);
		if(purchases != null && purchases.size()>0){
			for(PigPurchase pp : purchases){
				pp.setAveWeight(ArithUtil.div(pp.getWeight(), pp.getQuantity(),2));
				pp.setActPrice(ArithUtil.div(pp.getCost(), pp.getQuantity(),2));
				
				totalWeight = ArithUtil.add(totalWeight, pp.getWeight(),2);
				totalQuan = ArithUtil.add(totalQuan, pp.getQuantity());
				totalCost = ArithUtil.add(totalCost, pp.getCost(),2);
				totalFre = ArithUtil.add(totalFre, pp.getFreight(),2);
			}
		}
		
		PigPurchase da = new PigPurchase();
		da.setRegistDate("合计");
		da.setQuantity(totalQuan);
		da.setWeight(totalWeight);
		da.setCost(totalCost);
		da.setFreight(totalFre);
		
		purchases.add(da);
		
		return purchases;
	}
	
	/**
	 * 日报表查找固定字段
	 * @Description:TODO
	 * @param bat
	 * @param date
	 * @param map
	 * @throws Exception
	 * void
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-22 上午08:34:14
	 */
	public void selectPigDay(Batch bat, String date, Map<String, Object> map)throws Exception{
		List<PigPurchase> pps = selectBySingletAll("batch.id", bat.getId());
		String inDate = null;
		if(pps!=null && !pps.isEmpty())
			inDate = pps.get(0).getRegistDate();
		else
			throw new SystemException("未找到批次【"+bat.getBatchNumber()+"】的进猪日期");
		if(bat != null){
			map.put("feedFac", bat.getContract().getFeedFac().getName());
			map.put("company", bat.getCompany().getName());
			map.put("farmer", bat.getFarmer().getName());
			map.put("batch", bat.getBatchNumber());
		}
		map.put("sdate", date);
		map.put("inDate", inDate);
		
		int breedDay = (int) PapUtil.dayNum(inDate, date);
		int day =(int) Double.parseDouble(pps.get(0).getDays());
		day = day + breedDay;
			
		int weeks = (int) Math.ceil(day/7.0);
		map.put("breedDays", breedDay);
		map.put("weeks", weeks);
	}
	
	
	
}
