
package com.zd.foster.price.services;

import com.zd.epa.base.IBaseService;
import com.zd.foster.price.entity.MaterialPrice;

/**
 * 其他物料定价单服务层接口
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-31 上午09:11:39
 */
public interface IMaterialPriceService extends IBaseService<MaterialPrice> {
	/**
	 * 审核
	 * @Description:TODO
	 * @param idArr
	 * @throws Exception
	 * void
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-29 下午03:34:03
	 */
	void check(String[] idArr) throws Exception;
	/**
	 * 撤销
	 * @Description:TODO
	 * @param id
	 * @throws Exception
	 * void
	 * @exception:
	 * @author: 小丁
	 * @time:2017-7-29 下午03:34:09
	 */
	void cancelCheck(String id) throws Exception;
	
	

}
