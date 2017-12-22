
package com.zd.foster.base.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.zd.epa.base.BaseServiceImpl;
import com.zd.epa.exception.SystemException;
import com.zd.epa.utils.ArithUtil;
import com.zd.epa.utils.ExcelUtil;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.base.dao.IFcrDao;
import com.zd.foster.base.entity.Fcr;
import com.zd.foster.base.services.IFcrService;

/**
 * fcr业务层
 * @Description:TODO
 * @author:小丁
 * @time:2017-9-6 下午06:55:25
 */
public class FcrServiceImpl extends BaseServiceImpl<Fcr, IFcrDao> implements IFcrService {

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
			fileInput = new FileInputStream(realPath + "/WEB-INF/template/" + "fcr.xlsx");
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new SystemException("系统找不到指定路径下的模板文件！");
		}
		return fileInput;
	}
	
	
	/**
	 * 导入料肉比
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
	public List<Fcr> operateFile(File file, Object... objects)throws Exception{
		
		List<Fcr> details = new ArrayList<Fcr>();
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
						String pigletWei = ExcelUtil.checkCellValue(row.getCell(0), i + 1, "猪苗重", true, sb);
						String pigWei = ExcelUtil.checkCellValue(row.getCell(1), i + 1, "出猪重", false, sb);
						String fcrs = ExcelUtil.checkCellValue(row.getCell(2), i + 1, "料肉比", true, sb);
						
						//封装
						Fcr fcr = new Fcr();
						fcr.setPigletWei(pigletWei);
						fcr.setPigWei(pigWei);
						fcr.setFcr(fcrs);
						
						details.add(fcr);
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
	 * 获得标准料肉比
	 * @Description:TODO
	 * @param pigletWei
	 * @param pigWei
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-7 上午10:22:30
	 */
	public String getFcr(String pigletWei,String pigWei)throws Exception{
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		
		Integer inWei = (int) Math.floor(Double.parseDouble(pigletWei));
		Integer inWei1 = inWei + 1;
		Integer outWei = (int) Math.floor(Double.parseDouble(pigWei));
		Integer outWei1 = outWei + 1;
		
		//参数
		String arg1 = null;
		String arg2 = null;
		String arg3 = null;
		String arg4 = null;
		
		sqlMap.put("pigletWei", "=", inWei);
		sqlMap.put("pigWei", "=", outWei);
		List<Fcr> fcr1 = selectHQL(sqlMap);
		sqlMap.clear();
		if(fcr1!=null && !fcr1.isEmpty()){
			arg1 = fcr1.get(0).getFcr();
		}else
			throw new SystemException("未找到【"+inWei+","+outWei+"】的标准料肉比");
		
		sqlMap.put("pigletWei", "=", inWei1);
		sqlMap.put("pigWei", "=", outWei);
		List<Fcr> fcr2 = selectHQL(sqlMap);
		sqlMap.clear();
		if(fcr2!=null && !fcr2.isEmpty()){
			arg2 = fcr2.get(0).getFcr();
		}else
			throw new SystemException("未找到【"+inWei1+","+outWei+"】的标准料肉比");
		
		sqlMap.put("pigletWei", "=", inWei);
		sqlMap.put("pigWei", "=", outWei1);
		List<Fcr> fcr3 = selectHQL(sqlMap);
		sqlMap.clear();
		if(fcr3!=null && !fcr3.isEmpty()){
			arg3 = fcr3.get(0).getFcr();
		}else
			throw new SystemException("未找到【"+inWei+","+outWei1+"】的标准料肉比");
		
		sqlMap.put("pigletWei", "=", inWei1);
		sqlMap.put("pigWei", "=", outWei1);
		List<Fcr> fcr4 = selectHQL(sqlMap);
		sqlMap.clear();
		if(fcr4!=null && !fcr4.isEmpty()){
			arg4 = fcr4.get(0).getFcr();
		}else
			throw new SystemException("未找到【"+inWei1+","+outWei1+"】的标准料肉比");
		
		String arg5 = null;
		String arg51 = ArithUtil.sub(arg2, arg1);
		String arg52 = ArithUtil.sub(pigletWei, inWei.toString());
		String arg53 = ArithUtil.mul(arg51, arg52);
		arg5 = ArithUtil.add(arg53, arg1);
		
		String arg6 = null;
		String arg61 = ArithUtil.sub(arg4, arg3);
		String arg62 = ArithUtil.sub(pigletWei, inWei.toString());
		String arg63 = ArithUtil.mul(arg61, arg62);
		arg6 = ArithUtil.add(arg63, arg3);
		
		String fcr = null;
		String fcr11 = ArithUtil.sub(arg6, arg5);
		String fcr22 = ArithUtil.sub(pigWei, outWei.toString());
		String fcr33 = ArithUtil.mul(fcr11, fcr22);
		fcr = ArithUtil.add(fcr33, arg5, 4);
		
		return fcr;
	}
}
