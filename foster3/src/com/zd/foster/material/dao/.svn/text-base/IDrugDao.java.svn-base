
package com.zd.foster.material.dao;

import java.util.List;

import com.zd.epa.base.IBaseDao;
import com.zd.epa.utils.SqlMap;
import com.zd.foster.material.entity.Drug;
import com.zd.foster.warehouse.entity.DrugWarehouse;

/**
 * 药品dao接口层
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-26 上午09:27:40
 */
public interface IDrugDao extends IBaseDao<Drug> {

	/**
	 * 独特的排序
	 * @param sqlMap
	 * @return
	 */
	List<Drug> selectHQLSingle(SqlMap<String,String,Object> sqlMap)throws Exception;
}
