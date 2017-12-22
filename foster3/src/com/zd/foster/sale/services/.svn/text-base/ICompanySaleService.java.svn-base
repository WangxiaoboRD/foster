package com.zd.foster.sale.services;

import com.zd.epa.base.IBaseService;
import com.zd.foster.sale.entity.CompanySale;

/**
 * 类名：  ICompanySaleService
 * 功能：
 * @author DZL
 * @date 2017-7-19下午02:13:02
 * @version 1.0
 * 
 */
public interface ICompanySaleService extends IBaseService<CompanySale> {
	/**
	 * 功能：审核<br/>
	 *
	 * @author dzl
	 * @version 2015-3-9 下午05:28:16 <br/>
	 */
	void check(String[] idArr)throws Exception;
	/**
	 * 功能：撤销<br/>
	 *
	 * @author dzl
	 * @version 2015-3-9 下午05:28:16 <br/>
	 */
	void cancelCheck(String id)throws Exception;
	
	/**
	 * 自动保存 由代养销售
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	Object saveAuto(CompanySale entity) throws Exception;
}
