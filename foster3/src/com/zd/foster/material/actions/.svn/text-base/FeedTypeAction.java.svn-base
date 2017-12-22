
package com.zd.foster.material.actions;

import com.zd.epa.base.BaseAction;
import com.zd.foster.material.entity.FeedType;
import com.zd.foster.material.services.IFeedTypeService;

/**
 * 饲料类型action层
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-23 下午03:26:44
 */
public class FeedTypeAction extends BaseAction<FeedType, IFeedTypeService> {
	
	/**
	 * 根据公司查找饲料类型名字
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: 小丁
	 * @time:2017-9-21 上午11:10:21
	 */
	public String loadByCor()throws Exception{
		
		String[] names = service.selectByCor(e);
		result.put("Rows", names);
		//result.put("Total", elist.size());
		return JSON;
	}
	
}
