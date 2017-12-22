
package com.zd.foster.material.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.epa.utils.Pager;
import com.zd.foster.base.entity.Company;
import com.zd.foster.material.entity.Drug;

/**
 * 药品服务层接口
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-26 上午09:29:40
 */
public interface IDrugService extends IBaseService<Drug> {

	void selectAllAndOldPrice(Drug e, Pager<Drug> pageBean)throws Exception;

	InputStream downloadTemplate(String realPath)throws Exception;

	List<Drug> operateFile(File doc, Company company, Object... objects)throws Exception;
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
	List<Drug> selectByCodes(Drug e)throws Exception;

}
