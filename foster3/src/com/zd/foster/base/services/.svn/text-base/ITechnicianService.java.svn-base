
package com.zd.foster.base.services;

import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.foster.base.entity.DevelopMan;
import com.zd.foster.base.entity.Technician;

/**
 * 类名：  ITechnicianService
 * 功能：
 * @author 小丁
 * @date 2017-7-19下午02:13:02
 * @version 1.0
 * 
 */
public interface ITechnicianService extends IBaseService<Technician> {

	/**
	 *查询 按照代养编码
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 5:57:37 PM <br/>
	 */
	List<Technician> selectAllByFarmerId(Technician entity)throws Exception;
	
	/**
	 *查询 按照batch编码
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 5:57:37 PM <br/>
	 */
	List<Technician> selectAllByBatchId(Technician entity)throws Exception;
	/**
	 * 检索技术员
	 * @Description:TODO
	 * @param e
	 * @param key
	 * @return
	 * @throws Exception
	 * List<Technician>
	 * @exception:
	 * @author: 小丁
	 * @time:2017-10-12 下午01:30:39
	 */
	List<Technician> selectByName(Technician e, String key)throws Exception;
}
