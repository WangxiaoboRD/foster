
package com.zd.foster.base.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.bussobj.entity.BussinessEleDetail;
import com.zd.epa.bussobj.services.IBussinessEleDetailService;
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.ExcelUtil;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.base.dao.IFarmerDao;
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.contract.entity.Contract;
import com.zd.foster.contract.services.IContractService;
import com.zd.foster.price.services.IFreightDtlService;
import com.zd.foster.utils.FosterUtil;

/**
 * 类名：  FarmerServiceImpl
 * 功能：
 * @author wxb
 * @date 2017-7-19下午02:54:38
 * @version 1.0
 * 
 */
public class FarmerServiceImpl extends BaseServiceImpl<Farmer, IFarmerDao> implements IFarmerService {
	@Resource
	private IContractService contractService;
	@Resource
	private IFreightDtlService freightDtlService;
	@Resource
	private IBussinessEleDetailService bussinessEleDetailService;
	/**
	 * 新增
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-11 下午03:43:20
	 */
	@Override
	public Object save(Farmer entity) throws Exception {
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		//4.验证代养户养殖公司是否为空
		if(entity.getCompany()==null || entity.getCompany().getId()==null || "".equals(entity.getCompany().getId()))
			throw new SystemException("养殖公司不能为空");
		//1.验证编码
		if(!FosterUtil.idCode(entity.getCode()))
			throw new SystemException("编码应为十位内的数字和字母！");
		//2.验证Farmer编码是否重复
		sqlMap.put("company.id", "=", entity.getCompany().getId());
		sqlMap.put("code", "=", entity.getCode());
		int n =selectTotalRows(sqlMap);
		sqlMap.clear();
		
		if(n>0)
			throw new SystemException("代养户编码重复！");
		//3.验证Farmer名称是否重复（同一养殖公司不允许重复）
		sqlMap.put("company.id", "=", entity.getCompany().getId());
		sqlMap.put("name", "=", entity.getName());
		int i =selectTotalRows(sqlMap);
		sqlMap.clear();
		
		if(i>0)
			throw new SystemException("代养户姓名重复！");
		
		//5.验证代养户技术员是否为空
		//if(entity.getTechnician()==null || entity.getTechnician().getId()==null || "".equals(entity.getTechnician().getId()))
		//	throw new SystemException("技术员不能为空！");
		//6.验证身份证位数
		if(entity.getIdCard()!=null && !"".equals(entity.getIdCard())){
			String result = PapUtil.identitySimpleCheck(entity.getIdCard());
			if(!"OK".equals(result))
				throw new SystemException(result+"【"+entity.getIdCard()+"】");
		}
		//验证数字
		if(entity.getPhone()!=null && !"".equals(entity.getPhone())){
			//if(!SmsUtil.isMobileNo(entity.getPhone()))
				//throw new SystemException("电话号码错误");
		}
		if(entity.getPiggeryQuan()!=null && !"".equals(entity.getPiggeryQuan())){
			if(!PapUtil.isNum(entity.getPiggeryQuan()))
				throw new SystemException("猪舍栋数请输入数字");
			double d = Double.parseDouble(entity.getPiggeryQuan());
			if(d < 0)
				throw new SystemException("猪舍栋数不允许为负数");
		}
		if(entity.getBreedQuan()!=null && !"".equals(entity.getBreedQuan())){
			if(!PapUtil.isNum(entity.getBreedQuan()))
				throw new SystemException("可代养猪数量请输入数字");
			double d = Double.parseDouble(entity.getBreedQuan());
			if(d < 0)
				throw new SystemException("可代养数量不允许为负数");
		}
		if(entity.getBatchNum()!=null && !"".equals(entity.getBatchNum())){
			if(!PapUtil.isNum(entity.getBatchNum()))
				throw new SystemException("已代养批次请输入数字");
			double d = Double.parseDouble(entity.getBatchNum());
			if(d < 0)
				throw new SystemException("已代养批次不允许为负数");
		}
		if(entity.getFarmArea()!=null && !"".equals(entity.getFarmArea())){
			if(!PapUtil.isNum(entity.getFarmArea()))
				throw new SystemException("园区面积请输入数字");
			double d = Double.parseDouble(entity.getFarmArea());
			if(d < 0)
				throw new SystemException("园区面积不允许为负数");
		}
		//阶段不能为空
		if(entity.getStage()==null || entity.getStage().getDcode()==null || "".equals(entity.getStage().getDcode()))
			throw new SystemException("阶段不能为空！");
		
		String pinyin = PapUtil.getHeadChar(entity.getName());
		entity.setPinyin(pinyin);
		
		return super.save(entity);
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
	 * @time:2017-8-11 下午05:12:00
	 */
	@Override
	public <ID extends Serializable> int deleteByIds(ID[] PK) throws Exception {
		for(ID id : PK){
			//1.验证是否被合同、运费定价单引用
			int cnNum = contractService.selectTotalRows("farmer.id", id);
			if(cnNum>0)
				throw new SystemException("此代养户正在被使用，不允许删除");
			int fn = freightDtlService.selectTotalRows("farmer.id", id);
			if(fn>0)
				throw new SystemException("此代养户正在被使用，不允许删除");
		}
		
		return super.deleteByIds(PK);
	}
	/**
	 * 修改
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-11 下午03:43:05
	 */
	public int updateHql(Farmer entity) throws Exception {
		SqlMap<String,String,String> sqlMap = new SqlMap<String,String,String>();
		
		//1.验证编码
		if(!FosterUtil.idCode(entity.getCode()))
			throw new SystemException("编码应为十位内的数字和字母！");
		//2.验证Farmer编码是否重复
		sqlMap.put("company.id", "=", entity.getCompany().getId());
		sqlMap.put("code", "=", entity.getCode());
		sqlMap.put("id", "<>", entity.getId());
		int n =selectTotalRows(sqlMap);
		sqlMap.clear();
		
		if(n>0)
			throw new SystemException("代养户编码重复！");
		//3.验证Farmer名称是否重复（同一养殖公司不允许重复）
		sqlMap.put("company.id", "=", entity.getCompany().getId());
		sqlMap.put("name", "=", entity.getName());
		sqlMap.put("id", "<>", entity.getId());
		int i =selectTotalRows(sqlMap);
		sqlMap.clear();
		
		if(i>0)
			throw new SystemException("代养户姓名重复！");
		
		//5.验证代养户技术员是否为空
		//if(entity.getTechnician()==null || entity.getTechnician().getId()==null || "".equals(entity.getTechnician().getId()))
		//	throw new SystemException("技术员不能为空！");
		//6.验证身份证位数
		if(entity.getIdCard()!=null && !"".equals(entity.getIdCard())){
			String result = PapUtil.identitySimpleCheck(entity.getIdCard());
			if(!"OK".equals(result))
				throw new SystemException(result);
		}
		//验证数字
		if(entity.getPhone()!=null && !"".equals(entity.getPhone())){
			//if(!SmsUtil.isMobileNo(entity.getPhone()))
				//throw new SystemException("电话号码错误");
		}
		if(entity.getPiggeryQuan()!=null && !"".equals(entity.getPiggeryQuan())){
			if(!PapUtil.isNum(entity.getPiggeryQuan()))
				throw new SystemException("猪舍栋数请输入数字");
			double d = Double.parseDouble(entity.getPiggeryQuan());
			if(d < 0)
				throw new SystemException("猪舍栋数不允许为负数");
		}
		if(entity.getBreedQuan()!=null && !"".equals(entity.getBreedQuan())){
			if(!PapUtil.isNum(entity.getBreedQuan()))
				throw new SystemException("可代养猪数量请输入数字");
			double d = Double.parseDouble(entity.getBreedQuan());
			if(d < 0)
				throw new SystemException("可代养数量不允许为负数");
		}
		if(entity.getBatchNum()!=null && !"".equals(entity.getBatchNum())){
			if(!PapUtil.isNum(entity.getBatchNum()))
				throw new SystemException("已代养批次请输入数字");
			double d = Double.parseDouble(entity.getBatchNum());
			if(d < 0)
				throw new SystemException("已代养批次不允许为负数");
		}
		if(entity.getFarmArea()!=null && !"".equals(entity.getFarmArea())){
			if(!PapUtil.isNum(entity.getFarmArea()))
				throw new SystemException("园区面积请输入数字");
			double d = Double.parseDouble(entity.getFarmArea());
			if(d < 0)
				throw new SystemException("园区面积不允许为负数");
		}
		//阶段不能为空
		if(entity.getStage()==null || entity.getStage().getDcode()==null || "".equals(entity.getStage().getDcode()))
			throw new SystemException("阶段不能为空！");
		//验证修改阶段
		Farmer e=super.selectById(entity.getId());
		String old=e.getStage().getDcode();
		List<Contract> cList=contractService.selectByHQL("from Contract e where e.farmer.id='"+entity.getId()+"'");
		//修改值时，并且有合同时验证
		if(!entity.getStage().getDcode().equals(old) && cList!=null && cList.size()>0){
			boolean flag=false;
			for(Contract c:cList){
				if( (!c.getStatus().getDcode().equals("LOST")) && (!c.getStatus().getDcode().equals("STOP")) ){
					flag=true;
					break;
				}
			}
			//1.有生效，养殖的合同时不允许改
			if(flag)
				throw new SystemException("该代养户有生效或养殖的合同，不允许修改阶段！");
			//2.没有生效，养殖的合同，阶段只允许改成合作，停止合作
			else if(!(entity.getStage().getDcode().equals("COOPERATION")||entity.getStage().getDcode().equals("STOPCOOPER")))
				throw new SystemException("该代养户已经有合作的合同，只能改成合作或停止合作！");
		}
		e.setCode(entity.getCode());
		e.setName(entity.getName());
		e.setIdCard(entity.getIdCard());
		e.setHomeAddress(entity.getHomeAddress());
		e.setPhone(entity.getPhone());
		e.setFarmAddress(entity.getFarmAddress());
		e.setFarmArea(entity.getFarmArea());
		e.setPiggeryQuan(entity.getPiggeryQuan());
		e.setBreedQuan(entity.getBreedQuan());
		//e.setTechnician(entity.getTechnician());
		e.setStage(entity.getStage());
		e.setBatchNum(entity.getBatchNum());
		e.setCompany(entity.getCompany());
		String pinyin = PapUtil.getHeadChar(entity.getName());
		e.setPinyin(pinyin);
		e.setIsOwnBreed(entity.getIsOwnBreed());
//		super.update(entity);
		return 1;
	}
	
	/**
	 * 通过拼音检索代养户
	 * @Description:TODO
	 * @param e
	 * @param key
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-6 下午02:54:56
	 */
	public List<Farmer> selectByPinyin(Farmer entity, String key) throws Exception{
		SqlMap<String,String,Object> sqlMap=new SqlMap<String,String,Object>();
		List<Farmer> blist=new ArrayList<Farmer>();
		
		if(entity.getCompany()!=null && !"".equals(entity.getCompany().getId()))
			sqlMap.put("company.id", "=", entity.getCompany().getId());
		
		if(key == null || "".equals(key)){
			if(sqlMap==null || sqlMap.isEmpty())
				blist = super.selectAll();
			else
				blist=super.selectHQL(sqlMap);
		}else{
			//判断key是否包含中文
			boolean b=false;
			key = key.trim();
			for (int i = 0; i < key.length(); i++) {  
	            if (key.substring(i, i + 1).matches("[\\u4e00-\\u9fbb]+")) {  
	               b=true;
	               break;
	            }  
	        }
			if(b){
				sqlMap.put("name", "like",key+"%");
				blist = super.selectTopValue(sqlMap, 20);
			}else{
				sqlMap.put("pinyin", "like",key+"%");
				blist = super.selectTopValue(sqlMap, 20);
			}
		}
		sqlMap.clear();
		return blist;
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
			fileInput = new FileInputStream(realPath + "/WEB-INF/template/" + "farmer.xlsx");
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new SystemException("系统找不到指定路径下的模板文件！");
		}
		return fileInput;
	}
	
	/**
	 * 导入代养户
	 * @Description:TODO
	 * @param doc
	 * @param company
	 * @param objects
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-8 上午09:29:44
	 */
	public List<Farmer> operateFile(File file, Company company, Object... objects)throws Exception{
		if(company==null || company.getId()==null || "".equals(company.getId()))
			throw new SystemException("请选择公司");
		List<Farmer> details = new ArrayList<Farmer>();
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
						//1.验证是否为空
						String name = ExcelUtil.checkCellValue(row.getCell(0), i + 1, "姓名", true, sb);
						String code = ExcelUtil.checkCellValue(row.getCell(1), i + 1, "编码", true, sb);
						String idCard = ExcelUtil.checkCellValue(row.getCell(2), i + 1, "身份证号", false, sb);
						String phone = ExcelUtil.checkCellValue(row.getCell(3), i + 1, "电话", false, sb);
						String homeAddress = ExcelUtil.checkCellValue(row.getCell(4), i + 1, "家庭地址", false, sb);
						String farmArea = ExcelUtil.checkCellValue(row.getCell(5), i + 1, "园区面积(m²)", false, sb);
						String piggeryQuan = ExcelUtil.checkCellValue(row.getCell(6), i + 1, "猪舍栋数", false, sb);
						String farmAddress = ExcelUtil.checkCellValue(row.getCell(7), i + 1, "园区地址", false, sb);
						String breedQuan = ExcelUtil.checkCellValue(row.getCell(8), i + 1, "养殖规模", false, sb);
						String batchNum = ExcelUtil.checkCellValue(row.getCell(9), i + 1, "已养批次", false, sb);
						String stage = ExcelUtil.checkCellValue(row.getCell(10), i + 1, "阶段", true, sb);
						//String technician = ExcelUtil.checkCellValue(row.getCell(11), i + 1, "技术员", true, sb);
//						String developer = ExcelUtil.checkCellValue(row.getCell(12), i + 1, "开发人员", true, sb);
						
						//验证技术员

//						Technician tec = new Technician();
//						List<Technician> technicians = technicianService.selectBySingletAll("name", technician);
//						if(technicians==null || technicians.isEmpty())
//							sb.append("不存在技术员【"+technician+"】<br>");
//						else{
//							tec = technicians.get(0);
//						}

						//Technician tec = new Technician();
//						List<Technician> technicians = technicianService.selectBySingletAll("name", technician);
//						if(technicians==null || technicians.isEmpty())
//							throw new SystemException("不存在技术员【"+technician+"】");
//						else{
//							tec = technicians.get(0);
//						}

//						//验证开发员
//						String dpr_hql="from farmer e where e.name='"+developer+"'and e.company.id='"+company.getId()+"'";
//						DevelopMan dpr=developManService.selectByHQLSingle(dpr_hql);
//						if(dpr==null)
//							sb.append("不存在开发人员【"+developer+"】<br>");
						
						//验证阶段
						BussinessEleDetail stages = new BussinessEleDetail();
						List<BussinessEleDetail> bussinessEleDetails = bussinessEleDetailService.selectBySingletAll("value", stage);
						if(bussinessEleDetails==null || bussinessEleDetails.isEmpty())
							sb.append("阶段【"+stage+"】错误<br>");
						else{
							stages = bussinessEleDetails.get(0);
						}
						
						//封装
						Farmer farmer = new Farmer();
						farmer.setCompany(company);
						farmer.setName(name);
						farmer.setCode(code);
						farmer.setIdCard(idCard);
						farmer.setPhone(phone);
						farmer.setHomeAddress(homeAddress);
						farmer.setFarmArea(farmArea);
						farmer.setPiggeryQuan(piggeryQuan);
						farmer.setFarmAddress(farmAddress);
						farmer.setBreedQuan(breedQuan);
						farmer.setBatchNum(batchNum);
						farmer.setStage(stages);
						//farmer.setTechnician(tec);
						//farmer.setDevelopMan(dpr);
						
						details.add(farmer);
					}
				}
				if(sb.length() > 0)
					throw new SystemException(sb.toString());
				if(details.isEmpty())
					throw new SystemException("无可用数据导入");
				save(details);
			}else
				throw new SystemException("无可用数据导入");
		}
		return details;
		
	}
	
}
