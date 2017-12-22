package com.zd.foster.sale.services;

import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.foster.sale.entity.OutPigsty;

/**
 * 类名：  IOutPigstyService
 * 功能：
 * @author DZL
 * @date 2017-7-19下午02:13:02
 * @version 1.0
 * 
 */
public interface IOutPigstyService extends IBaseService<OutPigsty> {

	/**
	 * 获取对象 按照没有设置
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	List<OutPigsty> selectEntity(OutPigsty entity)throws Exception;
	
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
	 * 功能：通过ID获取对象<br/>
	 *
	 * @author dzl
	 * @version 2015-3-9 下午05:28:16 <br/>
	 */
	OutPigsty selectId(String id)throws Exception;
}
