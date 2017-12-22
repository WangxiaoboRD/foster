
package com.zd.foster.price.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
import com.zd.epa.utils.ArithUtil;
import com.zd.epa.utils.ExcelUtil;
import com.zd.epa.utils.PapUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.epa.utils.SysContainer;
import com.zd.epa.utils.TypeUtil;
import com.zd.foster.base.entity.Company;
import com.zd.foster.material.entity.Drug;
import com.zd.foster.material.services.IDrugService;
import com.zd.foster.price.dao.IDrugPriceDtlDao;
import com.zd.foster.price.entity.DrugPrice;
import com.zd.foster.price.entity.DrugPriceDtl;
import com.zd.foster.price.entity.FeedPriceDtl;
import com.zd.foster.price.services.IDrugPriceDtlService;
import com.zd.foster.price.services.IDrugPriceService;

/**
 * 药品定价单明细服务实现层
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-28 下午04:08:18
 */
public class DrugPriceDtlServiceImpl extends BaseServiceImpl<DrugPriceDtl, IDrugPriceDtlDao> implements IDrugPriceDtlService {
	@Resource
	private IDrugService drugService;
	@Resource
	private IDrugPriceService drugPriceService;
	
	
	/**
	 * 模板下载
	 * @Description:TODO
	 * @param path
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-4 下午03:50:53
	 */
	@Override
	public InputStream downloadTemplate(String path) throws Exception {
		InputStream fileInput = null;
		try {
			fileInput = new FileInputStream(path + "/WEB-INF/template/" + "drugPrice.xlsx");
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new SystemException("系统找不到指定路径下的模板文件！");
		}
		return fileInput;
	}
	
	
	/**
	 * 导入定价单
	 * @Description:TODO
	 * @param file
	 * @param company
	 * @param startDate
	 * @param objects
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-5 上午10:42:38
	 */
	public List<DrugPriceDtl> operateFile(File file, Company company,
			Object... objects)throws Exception{
		
		if(company==null || company.getId()==null || "".equals(company.getId()))
			throw new SystemException("请选择公司");
		
		SqlMap<String,String,String> sqlMap=new SqlMap<String, String, String>();
		List<DrugPriceDtl> details = new ArrayList<DrugPriceDtl>();
		Map<String,Object> map = new HashMap<String, Object>();
		Workbook workbook = ExcelUtil.buildWorkbook(file, (String)objects[0]);
		if (null != workbook) {
			Sheet sheet = workbook.getSheetAt(0); // 获得第一个Excel Sheet
			int lastRowNum = sheet.getLastRowNum(); // 最后行号，默认为索引号，即从0开始到当前行号-1，如excel有10条数据，firstRowNum=0，而lastRowNum=9
			if (lastRowNum >0) {
				int firstRowNum = sheet.getFirstRowNum();
				StringBuffer sb = new StringBuffer(); // 存储校验非法描述
				List<Drug> drugs = new ArrayList<Drug>();
				for (int i = firstRowNum + 1; i <= lastRowNum; i++) {
					Row row = sheet.getRow(i);
					if (null != row) {
						//1.验证是否为空
						String startDate = ExcelUtil.checkCellValue(row.getCell(0), i + 1, "执行日期", true, sb);
						String drugCode = ExcelUtil.checkCellValue(row.getCell(1), i + 1, "药品编码", true, sb);
						String price = ExcelUtil.checkCellValue(row.getCell(2), i + 1, "单价(元)", true, sb);
						String saleprice = ExcelUtil.checkCellValue(row.getCell(3), i + 1, "销售单价", true, sb);
						//2.验证是否存在
						//药品
						Drug drug = null;
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("code", "=", drugCode);
						drugs = drugService.selectHQL(sqlMap);
						sqlMap.clear();
						if(drugs == null || drugs.isEmpty() ){
							sb.append("第"+(i+1)+"行药品不存在！");
							continue;
						}else{
							drug = drugs.get(0);
						}
						drugs.clear();	
						//3.封装明细
						DrugPriceDtl fd = new DrugPriceDtl();
						fd.setDrug(drug);
						fd.setPrice(ArithUtil.scale(price, 4));
						fd.setSalePrice(ArithUtil.scale(saleprice, 4));
						details.add(fd);
						List<DrugPriceDtl> dpds = new ArrayList<DrugPriceDtl>();
						dpds.add(fd);
						//放入map
						String key = startDate;
						if(map.containsKey(key)){
							List<DrugPriceDtl> fpds = (List<DrugPriceDtl>) map.get(key);
							fpds.add(fd);
						}else{
							map.put(key, dpds);
						}
					}
				}
				if(sb.length() > 0)
					throw new SystemException(sb.toString());
				if(details.isEmpty())
					throw new SystemException("无可用数据导入");
				//封装定价单
				for(Map.Entry<String, Object> entry : map.entrySet() ){
					String key = entry.getKey();
					List<DrugPriceDtl> fds = (List<DrugPriceDtl>) entry.getValue();
					if(fds != null && fds.size()>0){
						DrugPrice dp = new DrugPrice();
						dp.setStartDate(key);
						dp.setCompany(company);
						dp.setDetails(fds);
						
						drugPriceService.save(dp);
						String[] ids = {dp.getId()};
						drugPriceService.check(ids);
					}
				}
			}else
				throw new SystemException("无可用数据导入");
		}
		return details;
	}
	
	/**
	 * 查找药品最近的定价
	 * @Description:TODO
	 * @param f
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-18 下午05:07:31
	 */
	public String lastDrugPrice(Drug f) throws Exception{
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		String price = "0";
		
		//查找最近的定价
		sqlMap.put("drug.id", "=", f.getId());
		sqlMap.put("drugPrice.checkStatus", "=", 1);
		sqlMap.put("drugPrice.startDate","order by","desc");
		List<DrugPriceDtl> drugPriceDtls = selectTopValue(sqlMap, 1);
		sqlMap.clear();
		
		//把定价放在ratio里
		if(drugPriceDtls.size()>0 && !drugPriceDtls.isEmpty())
			price = drugPriceDtls.get(0).getPrice();
		
		return price;
	}
	
	
	/**
	 * 复制新增定价单
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-18 下午03:28:09
	 */
	public List<DrugPriceDtl> selectAllForCopy(DrugPriceDtl e)throws Exception{
		List<DrugPriceDtl> dtls = selectAll(e);
		List<DrugPriceDtl> drugPriceDtls = new ArrayList<DrugPriceDtl>();
		for(DrugPriceDtl fd : dtls){
			DrugPriceDtl fpd = new DrugPriceDtl();
			fpd = TypeUtil.copy(fd, fpd);
			fpd.setOldPrice(fpd.getPrice());
			drugPriceDtls.add(fpd);
		}
		
		return drugPriceDtls;
		
	}
	
	
}
