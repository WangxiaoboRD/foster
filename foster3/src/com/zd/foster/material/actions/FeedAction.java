
package com.zd.foster.material.actions;

import com.zd.epa.base.BaseAction;
import com.zd.foster.material.entity.Feed;
import com.zd.foster.material.services.IFeedService;

/**
 * 饲料action层
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-23 下午03:26:44
 */
public class FeedAction extends BaseAction<Feed, IFeedService> {
	
	/**
	 * 加载饲料类
	 * @return
	 * @throws Exception
	 */
	public String loadFeed()throws Exception{
		elist = service.selectAll(e);
		result.put("Rows", elist);
		//result.put("Total", elist.size());
		return JSON;
	}
	
	/**
	 * 加载饲料带定价
	 * @Description:TODO
	 * @return
	 * @throws Exception
	 * @exception:
	 * @author: 小丁
	 * @time:2017-8-18 下午02:08:46
	 */
	public String loadByPageOrderPrice()throws Exception{
		
		//service.selectAll(e,pageBean);
		service.selectAllAndOldPrice(e,pageBean);
		result.put("Rows", pageBean.getResult());
		result.put("Total", pageBean.getTotalCount());
		pageBean.setResult(null);
		result.put("pageBean", pageBean);
		return JSON;
	}
	
	
}
