
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
import com.zd.foster.base.entity.Company;
import com.zd.foster.base.entity.Farmer;
import com.zd.foster.base.entity.FeedFac;
import com.zd.foster.base.services.IFarmerService;
import com.zd.foster.base.services.IFeedFacService;
import com.zd.foster.breed.entity.FeedBillDtl;
import com.zd.foster.price.dao.IFreightDtlDao;
import com.zd.foster.price.entity.Freight;
import com.zd.foster.price.entity.FreightDtl;
import com.zd.foster.price.services.IFreightDtlService;
import com.zd.foster.price.services.IFreightService;

/**
 * 运费定价单明细服务层实现
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-31 下午03:57:43
 */
public class FreightDtlServiceImpl extends BaseServiceImpl<FreightDtl, IFreightDtlDao> implements IFreightDtlService {
	@Resource
	private IFarmerService farmerService;
	@Resource
	private IFeedFacService feedFacService;
	@Resource
	private IFreightService freightService;
	
	
	/**
	 * 导入模板下载
	 * @Description:TODO
	 * @param path
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-3 上午10:16:17
	 */
	@Override
	public InputStream downloadTemplate(String path) throws Exception {
		InputStream fileInput = null;
		try {
			fileInput = new FileInputStream(path + "/WEB-INF/template/" + "freight.xlsx");
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new SystemException("系统找不到指定路径下的模板文件！");
		}
		return fileInput;
	}
	
	/**
	 * 导入运费定价单
	 * @Description:TODO
	 * @param file
	 * @param company
	 * @param objects
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-3 上午10:52:11
	 */
	public List<FreightDtl> operateFile(File file, Company company, Object... objects) throws Exception{
		if(company==null || company.getId()==null || "".equals(company.getId()))
			throw new SystemException("请选择公司");
		SqlMap<String,String,String> sqlMap=new SqlMap<String, String, String>();
		List<FreightDtl> details = new ArrayList<FreightDtl>();
		Map<String,Object> map = new HashMap<String, Object>();
		Workbook workbook = ExcelUtil.buildWorkbook(file, (String)objects[0]);
		if (null != workbook) {
			Sheet sheet = workbook.getSheetAt(0); // 获得第一个Excel Sheet
			int lastRowNum = sheet.getLastRowNum(); // 最后行号，默认为索引号，即从0开始到当前行号-1，如excel有10条数据，firstRowNum=0，而lastRowNum=9
			if (lastRowNum >0) {
				int firstRowNum = sheet.getFirstRowNum();
				StringBuffer sb = new StringBuffer(); // 存储校验非法描述
				List<Farmer> farmers = new ArrayList<Farmer>();
				List<FeedFac> feedFacs = new ArrayList<FeedFac>();
				for (int i = firstRowNum + 1; i <= lastRowNum; i++) {
					Row row = sheet.getRow(i);
					if (null != row) {
						//验证是否为空
						String registDate = ExcelUtil.checkCellValue(row.getCell(0), i + 1, "执行日期", true, sb);
						String farmerName = ExcelUtil.checkCellValue(row.getCell(1), i + 1, "代养户", true, sb);
						String feedFacName = ExcelUtil.checkCellValue(row.getCell(2), i + 1, "饲料厂", true, sb);
						String tipscontent = ExcelUtil.checkCellValue(row.getCell(3), i + 1, "距饲料厂距离(公里)", true, sb);
						String packagePrice = ExcelUtil.checkCellValue(row.getCell(4), i + 1, "袋装料运费单价(元/吨)", true, sb);
						String bulkPrice = ExcelUtil.checkCellValue(row.getCell(5), i + 1, "散装料运费单价(元/吨)", true, sb);
						//2.验证是否存在
						//代养户
						Farmer farmer=null;
						sqlMap.put("name", "=", farmerName);
						sqlMap.put("company.id", "=", company.getId());
						farmers= farmerService.selectHQL(sqlMap);
						sqlMap.clear();
						if(farmers==null || farmers.isEmpty()){
							sb.append("第"+(i+1)+"行代养户不存在！");
							continue;
						}else
							farmer=farmers.get(0);
						farmers.clear();
						//饲料厂
						FeedFac feedFac=null;
						sqlMap.put("name", "=", feedFacName);
						sqlMap.put("company.id", "=", company.getId());
						feedFacs= feedFacService.selectHQL(sqlMap);
						sqlMap.clear();
						if(feedFacs==null || feedFacs.isEmpty()){
							sb.append("第"+(i+1)+"行饲料厂不存在！");
							continue;
						}else
							feedFac=feedFacs.get(0);
						feedFacs.clear();
						
						//封装明细
						FreightDtl fd = new FreightDtl();
						fd.setFarmer(farmer);
						fd.setFeedFac(feedFac);
						fd.setTipscontent(tipscontent);
						fd.setPackagePrice(ArithUtil.scale(packagePrice, 4));
						fd.setBulkPrice(bulkPrice);
						details.add(fd);
						List<FreightDtl> freightDtls = new ArrayList<FreightDtl>();
						freightDtls.add(fd);
						//放入map
						String key = registDate;
						if(map.containsKey(key)){
							List<FreightDtl> fbds = (List<FreightDtl>) map.get(key);
							fbds.add(fd);
						}else{
							map.put(key, freightDtls);
						}
					}
				}
				if(sb.length() > 0)
					throw new SystemException(sb.toString());
				if(map.isEmpty())
					throw new SystemException("无可用数据导入");
				//封装运费定价单
				for(Map.Entry<String, Object> entry : map.entrySet() ){
					String key = entry.getKey();
					List<FreightDtl> fds = (List<FreightDtl>) entry.getValue();
					if(fds != null && fds.size()>0){
						Freight freight = new Freight();
						freight.setCompany(company);
						freight.setRegistDate(key);
						freight.setDetails(fds);
						
						freightService.save(freight);
						String[] ids = {freight.getId()};
						freightService.check(ids);
					}
				}
			}else
				throw new SystemException("无可用数据导入");
		}
		return details;
	}
	
	/**
	 * 复制新增
	 * @Description:TODO
	 * @param e
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-19 下午02:22:56
	 */
	public List<FreightDtl> selectAllForCopy(FreightDtl e)throws Exception{
		List<FreightDtl> dtls = selectAll(e);
		
		for(FreightDtl fd : dtls){
			fd.setOldBulkPrice(fd.getBulkPrice());
			fd.setOldPackagePrice(fd.getPackagePrice());
		}
		
		return dtls;
	}
}
