
package com.zd.foster.material.services;

import com.zd.epa.base.IBaseService;
import com.zd.epa.utils.Pager;
import com.zd.foster.material.entity.Feed;

/**
 * 饲料服务层接口
 * @Description:TODO
 * @author:小丁
 * @time:2017-7-23 下午03:19:33
 */
public interface IFeedService extends IBaseService<Feed> {

	void selectAllAndOldPrice(Feed e, Pager<Feed> pageBean) throws Exception;

}
