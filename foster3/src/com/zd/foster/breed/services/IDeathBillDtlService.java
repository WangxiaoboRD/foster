/**
 * 功能:
 * @author:wxb
 * @data:2017-8-1下午08:31:44
 * @file:IDeathBillDtlService.java
 */
package com.zd.foster.breed.services;

import java.io.InputStream;
import java.util.List;

import com.zd.epa.base.IBaseService;
import com.zd.epa.utils.Pager;
import com.zd.foster.breed.entity.DeathBillDtl;
import com.zd.foster.dto.DeathAnalysis;

/**
 * 类名：  IDeathBillDtlService
 * 功能：
 * @author wxb
 * @date 2017-8-1下午08:31:44
 * @version 1.0
 * 
 */
public interface IDeathBillDtlService extends IBaseService<DeathBillDtl> {

	
	/**
	 * 查询死亡报表
	 * @Description:TODO
	 * @param e
	 * @param pageBean
	 * @return
	 * @throws Exception
	 * List<DeathAnalysis>
	 * @exception:
	 * @author: 小丁
	 * @param pageBean 
	 * @time:2017-9-17 上午10:35:32
	 */
	public List<DeathAnalysis> selectDeathAnalysisByPage(DeathBillDtl e, Pager<DeathBillDtl> pageBean)throws Exception;
	/**
	 * 导出死亡报表
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-17 下午04:19:36
	 */
	public InputStream exportBook(DeathBillDtl e)throws Exception;
	
}
