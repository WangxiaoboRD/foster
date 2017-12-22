
package com.zd.foster.material.services.impl;

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
import com.zd.epa.utils.Pager;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.base.entity.Company;
import com.zd.foster.breed.services.IMedicalBillDtlService;
import com.zd.foster.material.dao.IDrugDao;
import com.zd.foster.material.entity.Drug;
import com.zd.foster.material.services.IDrugService;
import com.zd.foster.price.services.IDrugPriceDtlService;
import com.zd.foster.utils.FosterUtil;
import com.zd.foster.warehouse.services.IDrugInWareDtlService;
import com.zd.foster.warehouse.services.IDrugOutWareDtlService;
import com.zd.foster.warehouse.services.IDrugWarehouseService;

/**
 * 药品服务实现层
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-26 上午09:32:58
 */
public class DrugServiceImpl extends BaseServiceImpl<Drug, IDrugDao> implements IDrugService {
	
	@Resource
	private IDrugInWareDtlService drugInWareDtlService;
	@Resource
	private IDrugOutWareDtlService drugOutWareDtlService;
	@Resource
	private IDrugWarehouseService drugWarehouseService;
	@Resource
	private IDrugPriceDtlService drugPriceDtlService;
	@Resource
	private IMedicalBillDtlService medicalBillDtlService;
	@Resource
	private IBussinessEleDetailService bussinessEleDetailService;
	
	/**
	 * 保存
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-25 下午02:19:09
	 */
	public Object save(Drug entity) throws Exception {
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		
		if(entity==null)
			throw new SystemException("对象不允许为空");
		if(entity.getCompany()==null || entity.getCompany().getId()==null || "".equals(entity.getCompany().getId()))
			throw new SystemException("养殖公司不允许为空");
		
		if(entity.getName()==null || "".equals(entity.getName()))
			throw new SystemException("名称不允许为空");
//		else{
//			sqlMap.put("company.id", "=", entity.getCompany().getId());
//			sqlMap.put("name", "=", entity.getName());
//			int i =selectTotalRows(sqlMap);
//			sqlMap.clear();
//			
//			if(i>0)
//				throw new SystemException("名称不允许重复");
//		}
		if(entity.getCode()==null || "".equals(entity.getCode()))
			throw new SystemException("编码不允许为空");
		else{
			//1.验证编码
			if(!FosterUtil.idCode(entity.getCode()))
				throw new SystemException("编码应为十位内的数字和字母！");
			
			sqlMap.put("company.id", "=", entity.getCompany().getId());
			sqlMap.put("code", "=", entity.getCode());
			int i =selectTotalRows(sqlMap);
			sqlMap.clear();
			
			if(i>0)
				throw new SystemException("编码不允许重复");
		}
		
		if(entity.getUnit()==null || entity.getUnit().getDcode()==null || "".equals(entity.getUnit().getDcode()))
			throw new SystemException("单位不允许为空");
		if(entity.getSubUnit()==null || entity.getSubUnit().getDcode()==null || "".equals(entity.getSubUnit().getDcode()))
			throw new SystemException("副单位不允许为空");
		
		if(!PapUtil.isNum(entity.getRatio()))
			throw new SystemException("换算比例必须输入数字");
		double d = Double.parseDouble(entity.getRatio());
		if(d <= 0)
			throw new SystemException("换算比例不允许为非正数");
		
		entity.setMaterialType(new BussinessEleDetail("drug"));
		
		//保存对象
		return dao.insert(entity);
	}
	/**
	 * 修改
	 * @Description:TODO
	 * @param entity
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-25 下午02:29:16
	 */
	public int updateHql(Drug entity)throws Exception{
		if(entity.getUnit()==null || entity.getUnit().getDcode()==null || "".equals(entity.getUnit().getDcode()))
			throw new SystemException("单位不允许为空");
		if(entity.getSubUnit()==null || entity.getSubUnit().getDcode()==null || "".equals(entity.getSubUnit().getDcode()))
			throw new SystemException("副单位不允许为空");
		
		if(!PapUtil.isNum(entity.getRatio()))
			throw new SystemException("换算比例必须输入数字");
		double d = Double.parseDouble(entity.getRatio());
		if(d <= 0)
			throw new SystemException("换算比例不允许为非正数");
		
		return super.updateHql(entity);
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
	 * @time:2017-7-25 下午02:41:38
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		
		for(ID id : PK){
			//删除：验证单据上上的药品:药品入库单明细、药品出库单明细、药品仓库、药品定价单明细、医疗单明细
			int inwn = drugInWareDtlService.selectTotalRows("drug.id", id);
			if(inwn>0)
				throw new SystemException("此药品正在被使用，不允许删除");
			
			int outwn = drugOutWareDtlService.selectTotalRows("drug.id", id);
			if(outwn>0)
				throw new SystemException("此药品正在被使用，不允许删除");
		
			int wn = drugWarehouseService.selectTotalRows("drug.id", id);
			if(wn>0)
				throw new SystemException("此药品正在被使用，不允许删除");
			
			int pn = drugPriceDtlService.selectTotalRows("drug.id", id);
			if(pn>0)
				throw new SystemException("此药品正在被使用，不允许删除");
			
			int mn = medicalBillDtlService.selectTotalRows("drug.id", id);
			if(mn>0)
				throw new SystemException("此药品正在被使用，不允许删除");
		}
		
		return dao.deleteByIds(PK);
	}
	
	/**
	 * 加载带最近的定价
	 * @Description:TODO
	 * @param e
	 * @param pageBean
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-18 下午02:30:09
	 */
	public void selectAllAndOldPrice(Drug e, Pager<Drug> pageBean)throws Exception{
		selectAll(e,pageBean);
		List<Drug> drugs = pageBean.getResult();
		for(Drug f : drugs){
			//把定价放在里
			String price = drugPriceDtlService.lastDrugPrice(f);
			f.setLastPrice(price);
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
			fileInput = new FileInputStream(realPath + "/WEB-INF/template/" + "drug.xlsx");
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new SystemException("系统找不到指定路径下的模板文件！");
		}
		return fileInput;
	}
	
	/**
	 * 导入药品
	 * @Description:TODO
	 * @param file
	 * @param company
	 * @param objects
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-6 上午09:10:34
	 */
	public List<Drug> operateFile(File file, Company company, Object... objects)throws Exception{
		if(company==null || company.getId()==null || "".equals(company.getId()))
			throw new SystemException("请选择公司");
		List<Drug> details = new ArrayList<Drug>();
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
						String code = ExcelUtil.checkCellValue(row.getCell(0), i + 1, "药品编码", true, sb);
						String sysCode = ExcelUtil.checkCellValue(row.getCell(1), i + 1, "系统编码", false, sb);
						String name = ExcelUtil.checkCellValue(row.getCell(2), i + 1, "药品名称", true, sb);
						String drugType = ExcelUtil.checkCellValue(row.getCell(3), i + 1, "药品类型", true, sb);
						String spec = ExcelUtil.checkCellValue(row.getCell(4), i + 1, "药品规格", true, sb);
						String unit = ExcelUtil.checkCellValue(row.getCell(5), i + 1, "单位", true, sb);
						String supplier = ExcelUtil.checkCellValue(row.getCell(6), i + 1, "供应商", true, sb);
						//验证药品类型
						if( (!drugType.equals("0")) && (!drugType.equals("1")) ){
							sb.append(new SystemException("第"+(i+1)+"行药品类型请输入0和1"));
							continue;
						}
						//验证单位
						BussinessEleDetail units = new BussinessEleDetail();
						List<BussinessEleDetail> bussinessEleDetails = bussinessEleDetailService.selectBySingletAll("value", unit);
						if(bussinessEleDetails==null || bussinessEleDetails.isEmpty()){
							sb.append(new SystemException("第"+(i+1)+"行单位系统不存在"));
							continue;
						}else{
							units = bussinessEleDetails.get(0);
						}
						//封装
						Drug drug = new Drug();
						drug.setCompany(company);
						drug.setCode(code);
						drug.setSysCode(sysCode);
						drug.setName(name);
						drug.setDrugType(Integer.parseInt(drugType));
						drug.setSpec(spec);
						drug.setUnit(units);
						drug.setSubUnit(units);
						drug.setRatio("1");
						drug.setSupplier(supplier);
						
						details.add(drug);
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
	
	/**
	 * 根据code加载药品
	 * @Description:TODO
	 * @param e
	 * @return
	 * @throws Exception
	 * List<Drug>
	 * @exception:
	 * @author: 小丁
	 * @time:2017-10-18 上午09:23:20
	 */
	public List<Drug> selectByCodes(Drug entity)throws Exception{
		List<Drug> drugs = new ArrayList<Drug>();
		SqlMap<String,String,Object> sqlmap =new SqlMap<String,String,Object>();
		
		sqlmap.put("company.id", "=", entity.getCompany().getId());
		if(entity.getDrugType()!=null){
			sqlmap.put("drugType", "=", entity.getDrugType());
		}
		if(entity.getName()!=null && !"".equals(entity.getName())){
			sqlmap.put("name", "=", entity.getName());
		}
		if(entity.getCode()!=null && !"".equals(entity.getCode())){
			String codes = entity.getCode().replaceAll(" ", "").replaceAll("，", ",").replaceAll("[',']+", ",").replaceAll("\\.",",");
			String[] strCode=codes.split(",");
			
//			List<String> codeList = new ArrayList<String>();
//			for(String code : strCode){
//				if(!codeList.contains(code))
//					codeList.add(code);
//			}
//			
//			for(String code : codeList){
//				sqlmap.put("code", "=", code);
//				List<Drug> drugList = selectHQL(sqlmap);
//				sqlmap.remove("code");
//				
//				drugs.addAll(drugList);
//			}
			
			sqlmap.put("code","in",PapUtil.arrayToSQLStr(strCode));
		}
		//排序
		sqlmap.put("code","order by","asc");
		drugs = dao.selectHQLSingle(sqlmap);
		return drugs;
	}
	
}
