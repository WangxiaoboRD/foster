
package com.zd.foster.price.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.foster.base.entity.Company;
import com.zd.foster.material.entity.Drug;
import com.zd.foster.price.entity.DrugPriceDtl;

/**
 * 药品定价单明细服务层接口
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-28 下午04:06:53
 */
public interface IDrugPriceDtlService extends IBaseService<DrugPriceDtl> {
	/**
	 * 模板下载
	 * @Description:TODO
	 * @param realPath
	 * @return
	 * @throws Exception
	 * InputStream
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-4 下午03:49:25
	 */
	InputStream downloadTemplate(String realPath) throws Exception;
	/**
	 * 导入定价单
	 * @Description:TODO
	 * @param doc
	 * @param company
	 * @param startDate
	 * @param objects
	 * @return
	 * @throws Exception
	 * List<DrugPriceDtl>
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-5 上午10:30:20
	 */
	List<DrugPriceDtl> operateFile(File doc, Company company,
			Object... objects)throws Exception;

	
	/**
	 * 找最近的定价
	 * @Description:TODO
	 * @param f
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-18 下午03:18:47
	 */
	String lastDrugPrice(Drug f) throws Exception;
	
	List<DrugPriceDtl> selectAllForCopy(DrugPriceDtl e)throws Exception;
	
}
