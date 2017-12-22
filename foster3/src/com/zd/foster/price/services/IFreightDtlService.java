
package com.zd.foster.price.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.foster.base.entity.Company;
import com.zd.foster.price.entity.FreightDtl;

/**
 * 运费定价单明细服务层接口
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-31 下午03:52:35
 */
public interface IFreightDtlService extends IBaseService<FreightDtl> {
	/**
	 * 导入模板下载
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-3 上午10:12:19
	 */
	InputStream downloadTemplate(String realPath) throws Exception;
	/**
	 * 导入运费定价单
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-3 上午10:24:42
	 */
	List<FreightDtl> operateFile(File doc, Company company, Object... objects) throws Exception;
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
	List<FreightDtl> selectAllForCopy(FreightDtl e)throws Exception;

}
