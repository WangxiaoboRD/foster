
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
import com.zd.foster.base.entity.FeedFac;
import com.zd.foster.base.services.IFeedFacService;
import com.zd.foster.material.entity.Feed;
import com.zd.foster.material.services.IFeedService;
import com.zd.foster.price.dao.IFeedPriceDtlDao;
import com.zd.foster.price.entity.FeedPrice;
import com.zd.foster.price.entity.FeedPriceDtl;
import com.zd.foster.price.entity.FreightDtl;
import com.zd.foster.price.services.IFeedPriceDtlService;
import com.zd.foster.price.services.IFeedPriceService;

/**
 * 饲料定价单明细服务实现层
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-27 上午10:41:16
 */
public class FeedPriceDtlServiceImpl extends BaseServiceImpl<FeedPriceDtl, IFeedPriceDtlDao> implements IFeedPriceDtlService {
	@Resource
	private IFeedService feedService;
	@Resource
	private IFeedPriceService feedPriceService;
	@Resource
	private IFeedFacService feedFacService;
	
	/**
	 * 模板下载
	 * @Description:TODO
	 * @param path
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-4 下午02:50:34
	 */
	@Override
	public InputStream downloadTemplate(String path) throws Exception {
		InputStream fileInput = null;
		try {
			fileInput = new FileInputStream(path + "/WEB-INF/template/" + "feedPrice.xlsx");
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
	 * @param feedFac
	 * @param startDate
	 * @param objects
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-4 下午03:21:59
	 */
	public List<FeedPriceDtl> operateFile(File file, Company company, Object... objects) throws Exception{
		
		if(company==null || company.getId()==null || "".equals(company.getId()))
			throw new SystemException("请选择公司");
		
		SqlMap<String,String,String> sqlMap=new SqlMap<String, String, String>();
		List<FeedPriceDtl> details = new ArrayList<FeedPriceDtl>();
		Map<String,Object> map = new HashMap<String, Object>();

		Workbook workbook = ExcelUtil.buildWorkbook(file, (String)objects[0]);
		if (null != workbook) {
			Sheet sheet = workbook.getSheetAt(0); // 获得第一个Excel Sheet
			int lastRowNum = sheet.getLastRowNum(); // 最后行号，默认为索引号，即从0开始到当前行号-1，如excel有10条数据，firstRowNum=0，而lastRowNum=9
			if (lastRowNum >0) {
				int firstRowNum = sheet.getFirstRowNum();
				StringBuffer sb = new StringBuffer(); // 存储校验非法描述
				List<Feed> feeds = new ArrayList<Feed>();
				List<FeedFac> feedFacs = new ArrayList<FeedFac>();
				for (int i = firstRowNum + 1; i <= lastRowNum; i++) {
					Row row = sheet.getRow(i);
					if (null != row) {
						//1.验证是否为空
						String startDate = ExcelUtil.checkCellValue(row.getCell(0), i + 1, "执行日期", true, sb);
						String feedFacName = ExcelUtil.checkCellValue(row.getCell(1), i + 1, "饲料厂", true, sb);
						String feedName = ExcelUtil.checkCellValue(row.getCell(2), i + 1, "饲料", true, sb);
						String price = ExcelUtil.checkCellValue(row.getCell(3), i + 1, "单价(元)", true, sb);	
						//2.验证是否存在
						//饲料
						Feed feed = null;
						sqlMap.put("company.id", "=", company.getId());
						sqlMap.put("name", "=", feedName);
						feeds = feedService.selectHQL(sqlMap);
						sqlMap.clear();
						if(feeds == null || feeds.isEmpty() ){
							sb.append("第"+(i+1)+"行饲料不存在！");
							continue;
						}else{
							feed = feeds.get(0);
						}
						feeds.clear();	
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
						//3.封装明细
						FeedPriceDtl fd = new FeedPriceDtl();
						fd.setFeed(feed);
						fd.setPrice(ArithUtil.scale(price, 4));
						details.add(fd);
						List<FeedPriceDtl> feedPriceDtls = new ArrayList<FeedPriceDtl>();
						feedPriceDtls.add(fd);
						//放入map
						String key = startDate+","+feedFacName;
						if(map.containsKey(key)){
							List<FeedPriceDtl> fpds = (List<FeedPriceDtl>) map.get(key);
							fpds.add(fd);
						}else{
							map.put(key, feedPriceDtls);
						}
					}
				}
				if(sb.length() > 0)
					throw new SystemException(sb.toString());
				if(map.isEmpty())
					throw new SystemException("无可用数据导入");
				//封装定价单
				for(Map.Entry<String, Object> entry : map.entrySet() ){
					String key = entry.getKey();
					List<FeedPriceDtl> fds = (List<FeedPriceDtl>) entry.getValue();
					if(fds != null && fds.size()>0){
						String[] fiw=key.split(",");
						
						sqlMap.put("name", "=", fiw[1]);
						sqlMap.put("company.id", "=", company.getId());
						List<FeedFac> facs= feedFacService.selectHQL(sqlMap);
						sqlMap.clear();
						FeedFac ffc = facs.get(0);
						
						//封装表头
						FeedPrice feedPrice=new FeedPrice();
						feedPrice.setCompany(company);
						feedPrice.setFeedFac(ffc);
						feedPrice.setStartDate(fiw[0]);
						feedPrice.setDetails(fds);
						
						feedPriceService.save(feedPrice);
						String[] ids = {feedPrice.getId()};
						feedPriceService.check(ids);
					}
				}
			}else
				throw new SystemException("无可用数据导入");
		}
		
		return details;
	}
	
	/**
	 * 找饲料最近的定价
	 * @Description:TODO
	 * @param f
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-18 下午03:21:34
	 */
	public String lastFeedPrice(Feed f) throws Exception{
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		String price = "0";
		
		//查找最近的定价
		sqlMap.put("feed.id", "=", f.getId());
		sqlMap.put("feedPrice.checkStatus", "=", 1);
		sqlMap.put("feedPrice.startDate","order by","desc");
		List<FeedPriceDtl> feedPriceDtls = selectTopValue(sqlMap, 1);
		sqlMap.clear();
		
		//把定价放在ratio里
		if(feedPriceDtls.size()>0 && !feedPriceDtls.isEmpty())
			price = feedPriceDtls.get(0).getPrice();
		
		return price;
	}
	
	/**
	 * 复制新增饲料定价单
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-18 下午03:28:09
	 */
	public List<FeedPriceDtl> selectAllForCopy(FeedPriceDtl e) throws Exception{
		
		List<FeedPriceDtl> dtls = selectAll(e);
		List<FeedPriceDtl> feedPriceDtls = new ArrayList<FeedPriceDtl>();
		for(FeedPriceDtl fd : dtls){
			FeedPriceDtl fpd = new FeedPriceDtl();
			fpd = TypeUtil.copy(fd, fpd);
			fpd.setOldPrice(fpd.getPrice());
			feedPriceDtls.add(fpd);
		}
		
		return feedPriceDtls;
	}

	
}
