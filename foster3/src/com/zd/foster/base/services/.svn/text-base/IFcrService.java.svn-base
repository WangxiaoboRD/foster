
package com.zd.foster.base.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.foster.base.entity.Fcr;

/**
 * fcr 服务层接口
 * @Description:TODO
 * @author:小丁
 * @time:2017-9-6 下午06:53:56
 */
public interface IFcrService extends IBaseService<Fcr> {

	InputStream downloadTemplate(String realPath)throws Exception;

	List<Fcr> operateFile(File doc, Object... objects)throws Exception;
	/**
	 * 获得标准料肉比
	 * @Description:TODO
	 * @param pigletWei
	 * @param pigWei
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-7 上午09:48:13
	 */
	String getFcr(String pigletWei,String pigWei)throws Exception;
}
